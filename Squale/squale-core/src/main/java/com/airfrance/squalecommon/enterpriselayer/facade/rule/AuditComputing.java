/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.SessionImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditGridDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MarkDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.result.QualityResultDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditDisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.Profile_DisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.CriteriumResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.util.manualmark.TimeLimitationParser;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * Calcul d'un audit Le calcul d'un audit s'appuie sur des règles décrites dans la grille qualité, celle-ci est
 * rattachée à un projet. Les règles sont exprimées en fonction de métriques obtenues par des outils comme mccabe par
 * exemple.
 */
public final class AuditComputing
{
    /** Log */
    private static Log LOG = LogFactory.getLog( AuditComputing.class );

    /**
     * Define private constructor for utility class
     */
    private AuditComputing()
    {
    }

    /**
     * Calcul d'un audit Le calcul d'un audit se fait à partir des pratiques pour remonter vers les facteurs
     * 
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @return true If the last mark for a manual practice is out of date
     * @throws JrafDaoException si erreur
     */
    public static boolean computeAuditResult( ISession pSession, ProjectBO pProject, AuditBO pAudit )
        throws JrafDaoException
    {
        boolean warning=false;
        try
        {
            LOG.info( RuleMessages.getString( "computation.start" ) );
            // Décomposition de la grille en facteurs, criteres, pratiques
            Map factors = new Hashtable();
            Map criteria = new Hashtable();
            Map practices = new Hashtable();
            
            // Les maps contiennent en clef la règle et en valeur le résultat calculé
            flattenQualitfyGrid( pSession, pProject, pAudit, factors, criteria, practices );

            // Le traitement se fait dans l'ordre hiérarchique inverse
            // celà permet d'éviter les calculs multiples pour des facteurs
            // ou des critères plusieurs fois présents

            // Traitement des pratiques
            warning = computePracticesResults( pSession, pProject, pAudit, practices );
            // Traitement des criteres
            computeCriteriaResults( pSession, criteria, practices );
            // Traitement des facteurs
            computeFactorsResults( pSession, factors, criteria, practices );
            // Ajout de la grille qualité
            AuditGridBO auditGrid = new AuditGridBO();
            auditGrid.setGrid( pProject.getQualityGrid() );
            auditGrid.setProject( pProject );
            auditGrid.setAudit( pAudit );
            AuditGridDAOImpl.getInstance().create( pSession, auditGrid );
            // Ajout à l'audit
            pAudit.addAuditGrid( auditGrid );
            // Ajout des configurations
            Collection confs = pProject.getProfile().getProfileDisplayConfs();
            for ( Iterator it = confs.iterator(); it.hasNext(); )
            {
                AuditDisplayConfBO auditConf = new AuditDisplayConfBO();
                auditConf.setDisplayConf( ( (Profile_DisplayConfBO) it.next() ).getDisplayConf() );
                auditConf.setProject( pProject );
                auditConf.setAudit( pAudit );
                AuditDisplayConfDAOImpl.getInstance().create( pSession, auditConf );
                // Ajout à l'audit
                pAudit.addAuditDisplayConf( auditConf );
            }
            AuditDAOImpl.getInstance().save( pSession, pAudit );
        }
        finally
        {
            LOG.info( RuleMessages.getString( "computation.end" ) );
        }
        return warning;
    }

    /**
     * Mise à plat d'une grille qualité La structure hiérarchique de la grille qualité est mise à plat sous la forme de
     * 3 maps qui contiennent les règles en fonction de leur typologie
     * 
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @param pFactors map des facteurs
     * @param pCriteria map des critères
     * @param pPractices map des pratiques
     * @throws JrafDaoException si erreur
     */
    private static void flattenQualitfyGrid( ISession pSession, ProjectBO pProject, AuditBO pAudit, Map pFactors,
                                             Map pCriteria, Map pPractices )
        throws JrafDaoException
    {
        // Le parcours consiste à construire 3 maps avec les facteurs, criteres et pratiques
        // Ce partionnement permet le traitement plus efficace d'au audit dans le cas où des critères
        // ou des pratiques apparaissent de façon multiple
        // On place aussi dans ces map, en tant que valeur le résultat associé, par exemple
        // la map des facteurs contient les FactorRuleBO et les FactorResultBO
        Iterator factors = pProject.getQualityGrid().getFactors().iterator();
        // Parcours des facteurs
        while ( factors.hasNext() )
        {
            FactorRuleBO factor = (FactorRuleBO) factors.next();
            // Création du résultat associé
            FactorResultBO fResult = new FactorResultBO();
            fResult.setRule( factor );
            fResult.setProject( pProject );
            fResult.setAudit( pAudit );
            pFactors.put( factor, fResult );
            QualityResultDAOImpl.getInstance().create( pSession, fResult );
            Iterator criteria = factor.getCriteria().keySet().iterator();
            // Parcours des critères
            while ( criteria.hasNext() )
            {
                CriteriumRuleBO criterium = (CriteriumRuleBO) criteria.next();
                // Un critère peut être présent dans plusieurs facteurs
                // On ne le rajoute que si nécessaire
                if ( pCriteria.get( criterium ) == null )
                {
                    // Création du résultat associé
                    CriteriumResultBO cResult = new CriteriumResultBO();
                    cResult.setRule( criterium );
                    cResult.setProject( pProject );
                    cResult.setAudit( pAudit );
                    QualityResultDAOImpl.getInstance().create( pSession, cResult );
                    // Ajout dans la map
                    pCriteria.put( criterium, cResult );
                }
                Iterator practices = criterium.getPractices().keySet().iterator();
                // Parcours des pratiques
                while ( practices.hasNext() )
                {
                    PracticeRuleBO practice = (PracticeRuleBO) practices.next();
                    // Une pratique peut être présente dans plusieurs critères
                    // On ne le rajoute que si nécessaire
                    if ( pPractices.get( practice ) == null )
                    {
                        // Création du résultat associé
                        PracticeResultBO pResult = new PracticeResultBO();
                        pResult.setRule( practice );
                        pResult.setProject( pProject );
                        pResult.setAudit( pAudit );
                        QualityResultDAOImpl.getInstance().create( pSession, pResult );
                        // Ajout dans la map
                        pPractices.put( practice, pResult );
                    }
                }
            }
        }
    }

    /**
     * Calcul des facteurs Chaque facteur est calculé en fonction des critères qui le composent
     * 
     * @param pSession session
     * @param pFactors facteurs
     * @param pCriteria critères
     * @param pPractices pratiques
     * @throws JrafDaoException si erreur
     */
    private static void computeFactorsResults( ISession pSession, Map pFactors, Map pCriteria, Map pPractices )
        throws JrafDaoException
    {
        // On parcourt chaque facteur et on calcule son résultat
        Iterator factors = pFactors.keySet().iterator();
        while ( factors.hasNext() )
        {
            // Récupération du facteur et du résultat
            FactorRuleBO factor = (FactorRuleBO) factors.next();
            LOG.info( RuleMessages.getString( "computation.factor", new Object[] { factor.getName() } ) );
            FactorResultBO result = (FactorResultBO) pFactors.get( factor );
            // Le calcul se fait par une moyenne simple sur les critères
            // calculés si au moins 2 criteres ont été calculés
            int nbCriteria = 0; // Nombre de criteres calculés
            float sumOfCriteriaWeight = 0;
            float criteriaSum = 0; // Note cumulée
            // Parcours des criteres du facteur
            Iterator it = factor.getCriteria().keySet().iterator();
            // Pour chaque critère, on récupère les valeurs calculées
            while ( it.hasNext() )
            {
                CriteriumResultBO criteriumResult = (CriteriumResultBO) pCriteria.get( it.next() );
                // Nota : criteriumResult ne peut pas être null
                if ( -1 != criteriumResult.getMeanMark() )
                {
                    float criteriaWeight =
                        ( (Float) factor.getCriteria().get( criteriumResult.getRule() ) ).floatValue();
                    criteriaSum += criteriumResult.getMeanMark() * criteriaWeight;
                    sumOfCriteriaWeight += criteriaWeight;
                    nbCriteria++;
                }
            }
            // Calcul seulement s'il existe au moins deux résultats
            if ( nbCriteria > 1 )
            {
                // calcul de la note du facteur
                result.setMeanMark( criteriaSum / sumOfCriteriaWeight );
                QualityResultDAOImpl.getInstance().save( pSession, result );
            }
            else
            {
                LOG.debug( RuleMessages.getString( "nocomputation", new Object[] { factor.getName() } ) );
            }
        }
    }

    /**
     * Calcul des critères Chaque critère est calculé en fonction des résultats de ses pratiques
     * 
     * @param pSession session
     * @param pCriteria critères
     * @param pPractices pratiques
     * @throws JrafDaoException si erreur
     */
    private static void computeCriteriaResults( ISession pSession, Map pCriteria, Map pPractices )
        throws JrafDaoException
    {
        // On parcourt chaque critère et on calcule son résultat
        Iterator criteria = pCriteria.keySet().iterator();
        while ( criteria.hasNext() )
        {
            // Récupération du critère et de son résultat
            CriteriumRuleBO criterium = (CriteriumRuleBO) criteria.next();
            LOG.info( RuleMessages.getString( "computation.criterium", new Object[] { criterium.getName() } ) );
            CriteriumResultBO result = (CriteriumResultBO) pCriteria.get( criterium );
            // La moyenne se fait par un moyenne simple sur les pratiques
            // calculés si au moins deux pratiques ont été calculées
            int nbPractices = 0;
            float sumOfPracticesWeight = 0;
            float practicesSum = 0; // Note cumulée
            // Parcours des pratiques du critère
            Iterator it = criterium.getPractices().keySet().iterator();
            // Pour chaque critère, on récupère les valeurs calculées
            while ( it.hasNext() )
            {
                PracticeResultBO praticeResult = (PracticeResultBO) pPractices.get( it.next() );
                // Nota : criteriumResult ne peut pas être null
                if ( -1 != praticeResult.getMeanMark() )
                {
                    float practiceWeight =
                        ( (Float) criterium.getPractices().get( praticeResult.getRule() ) ).floatValue();
                    practicesSum += praticeResult.getMeanMark() * practiceWeight;
                    sumOfPracticesWeight += practiceWeight;
                    nbPractices++;
                }
            }
            // Calcul seulement s'il existe au moins deux résultats
            if ( nbPractices > 1 )
            {
                // calcul de la note du facteur
                result.setMeanMark( practicesSum / sumOfPracticesWeight );
                QualityResultDAOImpl.getInstance().save( pSession, result );
            }
            else
            {
                LOG.debug( RuleMessages.getString( "nocomputation", new Object[] { criterium.getName() } ) );
            }
        }
    }

    /**
     * Calcul des pratiques Chaque pratique est calculée, son résultat est écrit en base de données
     * 
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @param pPractices pratiques
     * @return true If the last mark for a manual practice is out of date
     * @throws JrafDaoException si erreur
     */
    private static boolean computePracticesResults( ISession pSession, ProjectBO pProject, AuditBO pAudit, Map pPractices )
        throws JrafDaoException
    {
        boolean warning = false;
        // Les pratiques sont triées par par type
        // de composant pour eviter les appels multiples de recuperation des
        // composants du projet
        Map kindPractices = splitPracticesByKind( pPractices );
        // On traite séparement les pratiques de niveau projet
        computeProjectPractices( pSession, pProject, pAudit, (Collection) kindPractices.remove( "project" ), pPractices );
        // Processing of the manual practices
        warning=computeManualPractices( pSession, pProject, (Collection) kindPractices.remove( "" ), pPractices );
        Iterator kinds = kindPractices.keySet().iterator();
        while ( kinds.hasNext() )
        {
            String kind = (String) kinds.next();
            LOG.info( RuleMessages.getString( "computation.kind", new Object[] { kind } ) );
            // sauvegarde du mode actuel de flush
            Session hibernateSession = ( (SessionImpl) pSession ).getSession();
            FlushMode oldFlushMode = hibernateSession.getFlushMode();
            // On ne traite pas les pratiques qui n'ont pas de composant associé
            // (elles n'ont pas de formule)
            if ( kind.length() > 0 )
            {
                try
                {
                    // flush de la session
                    hibernateSession.flush();
                    // le flush automatique est désactivé pendant tout le calcul de chaque notes
                    hibernateSession.setFlushMode( FlushMode.NEVER );

                    // Recupération des enfants de bons niveaux...
                    Collection children =
                        AbstractComponentDAOImpl.getInstance().findProjectChildren(
                                                                                    pSession,
                                                                                    pProject,
                                                                                    pAudit,
                                                                                    Mapping.getComponentClass( "component."
                                                                                        + kind ) );
                    Iterator practices = ( (Collection) kindPractices.get( kind ) ).iterator();
                    while ( practices.hasNext() )
                    {
                        PracticeRuleBO practice = (PracticeRuleBO) practices.next();
                        LOG.info( RuleMessages.getString( "computation.practice", new Object[] { practice.getName() } ) );
                        PracticeResultBO practiceResult = (PracticeResultBO) pPractices.get( practice );
                        // Vérification de la formule
                        FormulaInterpreter interpreter = new FormulaInterpreter();
                        try
                        {
                            // Vérification de la syntaxe de la formule
                            // un exception est levée si elle est incorrecte
                            interpreter.checkSyntax( practice.getFormula() );
                            // Calcul de notes pour chaque enfant
                            Collection marks =
                                computePracticeResults( pSession, pAudit, interpreter, practice, practiceResult,
                                                        children );
                            // Calcul de la note globale de la pratique
                            computePracticeMark( practice, practiceResult, marks );
                        }
                        catch ( FormulaException fe )
                        {
                            // Si une erreur se produit sur le calcul d'une formule
                            // on stoppe le calcul pour cette pratique
                            LOG.error( RuleMessages.getString( "formula.error", new Object[] { practice.getName() } ),
                                       fe );
                        }
                        catch ( WeightFunctionException wfe )
                        {
                            // Si une erreur se produit sur le calcul d'une formule
                            // on stoppe le calcul pour cette pratique
                            LOG.error( RuleMessages.getString( "function.error", new Object[] {
                                practice.getWeightFunction(), practice.getName() } ), wfe );
                        }

                        // Sauvegarde des résultats de la pratique
                        QualityResultDAOImpl.getInstance().save( pSession, practiceResult );
                    }
                }
                catch ( HibernateException e )
                {
                    // Cette erreur est liée à la gestion du flushmode, dans ce cas on se contente de remonter
                    // une exception JRAF
                    throw new JrafDaoException( e );
                }
                finally
                {
                    // Remise du mode de flush au mode précedent
                    hibernateSession.setFlushMode( oldFlushMode );
                }
            }
        }
        return warning;
    }

    /**
     * Traitement des pratiques de niveau projet Les pratiques calculées au niveau d'un projet sont traitées de façon
     * spécifique, la formule donne une note directement sans avoir à collecter des notes pour chaque composant (comme
     * c'est le cas au niveau de classes ou des méthodes)
     * 
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @param pProjectPractices pratiques de niveau projet
     * @param pPractices pratiques
     * @throws JrafDaoException si erreur
     */
    private static void computeProjectPractices( ISession pSession, ProjectBO pProject, AuditBO pAudit,
                                                 Collection pProjectPractices, Map pPractices )
        throws JrafDaoException
    {
        if ( pProjectPractices != null )
        {
            try
            {
                Iterator practices = pProjectPractices.iterator();
                while ( practices.hasNext() )
                {
                    PracticeRuleBO practice = (PracticeRuleBO) practices.next();
                    LOG.info( RuleMessages.getString( "computation.practice", new Object[] { practice.getName() } ) );
                    PracticeResultBO practiceResult = (PracticeResultBO) pPractices.get( practice );
                    // Vérification de la formule
                    FormulaInterpreter interpreter = new FormulaInterpreter();
                    try
                    {
                        // Vérification de la syntaxe de la formule
                        // un exception est levée si elle est incorrecte
                        interpreter.checkSyntax( practice.getFormula() );
                        // Calcul de la note globale de la pratique
                        computeProjectPracticeMark( pSession, pProject, pAudit, interpreter, practice, practiceResult );
                    }
                    catch ( FormulaException e )
                    {
                        // Si une erreur se produit sur le calcul d'une formule
                        // on stoppe le calcul pour cette pratique
                        LOG.error( RuleMessages.getString( "formula.error", new Object[] { practice.getName() } ), e );
                    }
                    // Sauvegarde des résultats de la pratique
                    QualityResultDAOImpl.getInstance().save( pSession, practiceResult );
                }
            }
            catch ( HibernateException e )
            {
                // Cette erreur est liée à la gestion du flushmode, dans ce cas on se contente de remonter
                // une exception JRAF
                throw new JrafDaoException( e );
            }
        }

    }

    /**
     * Calcul de la note sur un projet Le calcul de la note sur un projet se fait directement au moyen de la formule
     * associée
     * 
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @param pInterpreter interprète
     * @param pPractice pratique
     * @param pPracticeResult résultat
     * @throws FormulaException si erreur
     * @throws JrafDaoException si erreur
     */
    private static void computeProjectPracticeMark( ISession pSession, ProjectBO pProject, AuditBO pAudit,
                                                    FormulaInterpreter pInterpreter, PracticeRuleBO pPractice,
                                                    PracticeResultBO pPracticeResult )
        throws JrafDaoException, FormulaException
    {
        // Récupération des types de mesure traités par la formule
        AbstractFormulaBO formula = pPractice.getFormula();
        String[] measureKinds = new String[formula.getMeasureKinds().size()];
        formula.getMeasureKinds().toArray( measureKinds );
        MeasureBO[] measures = new MeasureBO[formula.getMeasureKinds().size()];
        Long projectId = new Long( pProject.getId() );
        // Récupération de chaque mesure
        boolean measuresNotNull = true;
        Long auditId = new Long( pAudit.getId() );
        for ( int i = 0; i < measureKinds.length; i++ )
        {
            MeasureBO measure =
                (MeasureBO) MeasureDAOImpl.getInstance().load(
                                                               pSession,
                                                               projectId,
                                                               auditId,
                                                               Mapping.getMeasureClass( measureKinds[i] + "."
                                                                   + formula.getComponentLevel() ) );
            measures[i] = measure;
            // Une mesure peut être absente, ce qui serait lié à un problème de création des mesures
            // dans la phase qui précède le calcul des notes
            if ( measure == null )
            {
                measuresNotNull = false;
                // On indique le type de mesure qui n'est pas trouvé ainsi que le composant concerné
                LOG.warn( RuleMessages.getString( "missingmeasure",
                                                  new Object[] { measureKinds[i], projectId, auditId } ) );
            }
        }
        // On calcul la note seulement si toutes les mesures requises sont bien présentes
        if ( measuresNotNull )
        {
            // Calcul de la note
            // L'exception dans le calcul est remontée telle quelle
            Number value = pInterpreter.evaluate( pPractice.getFormula(), measures );
            // On place une note -1 si la formule n'a pu être calculée
            if ( value != null )
            {
                pPracticeResult.setMeanMark( value.floatValue() );
            }
        }
    }

    /**
     * Calcul de la note d'une pratique
     * 
     * @param pPractice pratique
     * @param pPracticeResult résultats de la pratique
     * @param pMarks les notes des composants impliqués
     * @throws WeightFunctionException si erreur
     */
    private static void computePracticeMark( PracticeRuleBO pPractice, PracticeResultBO pPracticeResult,
                                             Collection pMarks )
        throws WeightFunctionException
    {
        /*
         * Formule : meanMark = weigh^-1(1/N sum(weight(note(component)))) Cela revient à faire la moyenne des notes
         * auxquelles on applique la fonction la fonction de pondération weight puis à appliquer la fonction inverse
         */
        // Moyenne des notes : 1/N * sum(weight(note(component))
        float num = 0;
        float denum = 0;
        float average = -1;
        // Interpreteur python
        WeightFunctionInterpreter interpreter = new WeightFunctionInterpreter();
        for ( Iterator marksIt = pMarks.iterator(); marksIt.hasNext(); )
        {
            // note(component)
            float mark = ( (MarkBO) marksIt.next() ).getValue();
            // Si la note sur le composant a été calculé
            if ( mark != MarkBO.NOT_NOTED_VALUE )
            {
                // weigth(note(component))
                float weightMark = interpreter.exec( pPractice.getWeightFunction(), mark );
                num += weightMark;
                denum++;
            }

        }
        if ( denum != 0 )
        {
            // Moyenne
            average = num / denum;
            // weight^-1(average)
            Float meanMark = inverse( interpreter, pPractice.getWeightFunction(), average );
            pPracticeResult.setMeanMark( meanMark.floatValue() );
        }
        else
        {
            LOG.debug( RuleMessages.getString( "nocomputation", new Object[] { pPractice.getName() } ) );
        }
    }

    /**
     * Fait le calcul de la fonction inverse.
     * 
     * @param pInterpreter l'interpréteur pour une fonction de pondération
     * @param pFunction la fonction de pondération
     * @param pY : pFunction(x) = pY
     * @return x tel que f(x) = pY
     * @throws WeightFunctionException si erreur
     */
    private static Float inverse( WeightFunctionInterpreter pInterpreter, String pFunction, float pY )
        throws WeightFunctionException
    {
        // précision de x : 10^-3
        final double signi = 3;
        final double pow = 10;
        double epsilon = Math.pow( pow, -signi ) / 2;
        // x est compris entre 0 et 3
        final float minMark = 0;
        final float maxMark = 3;
        float x1 = minMark, x2 = ( minMark + maxMark ) / 2, x3 = maxMark;
        float fX1 = pInterpreter.exec( pFunction, x1 ), fX2 = pInterpreter.exec( pFunction, x2 );
        float fX3 = pInterpreter.exec( pFunction, x3 );
        float old = x1;
        // Tant que x n'est pas avec la précision voulu
        while ( Math.abs( x2 - old ) > epsilon )
        {
            old = x2;
            // si y est entre fX1 et fX2
            if ( ( fX1 <= pY && fX2 > pY ) || ( fX1 >= pY && fX2 < pY ) )
            {
                // On se recale entre x1 et x2
                x3 = x2;
                fX3 = fX2;
            }
            else
            {
                // On se recale entre x2 et x3
                x1 = x2;
                fX1 = fX2;
            }
            // nouvelle valeur cible par dichotomie
            x2 = ( x1 + x3 ) / 2;
            fX2 = pInterpreter.exec( pFunction, x2 );
        }
        // Arrondie au nombre de chiffres significatifs
        final double round = 0.5;
        double num = ( x2 * Math.pow( pow, signi ) + round );
        return new Float( (double) ( (int) ( x2 * Math.pow( pow, signi ) + round ) ) / Math.pow( pow, signi ) );
    }

    /**
     * Séparation des pratiques par type de composant
     * 
     * @param pPractices pratiques
     * @return map de la forme (string,collection) où string donne le type de composant et collection la liste des
     *         pratiques sur ce type de composant (string peut être "")
     */
    private static Map splitPracticesByKind( Map pPractices )
    {
        Hashtable result = new Hashtable();
        Iterator practices = pPractices.keySet().iterator();
        while ( practices.hasNext() )
        {
            PracticeRuleBO practice = (PracticeRuleBO) practices.next();
            // Les pratiques sans formule sont regroupées
            // avec un kind vide
            String kind = "";
            if ( practice.getFormula() != null )
            {
                kind = practice.getFormula().getComponentLevel();
            }
            Collection practicesKind = (Collection) result.get( kind );
            if ( practicesKind == null )
            {
                practicesKind = new ArrayList();
                result.put( kind, practicesKind );
            }
            practicesKind.add( practice );
        }
        return result;
    }

    /**
     * Calcul des pratiques sur les composants
     * 
     * @param pSession session
     * @param pAudit audit
     * @param pInterpreter interpréteur
     * @param pPractice pratique
     * @param pPracticeResult résultat
     * @param pChildren composants fils
     * @return les notes
     * @throws FormulaException si erreur
     * @throws JrafDaoException si erreur
     */

    private static Collection computePracticeResults( ISession pSession, AuditBO pAudit,
                                                      FormulaInterpreter pInterpreter, PracticeRuleBO pPractice,
                                                      PracticeResultBO pPracticeResult, Collection pChildren )
        throws FormulaException, JrafDaoException
    {
        // Reinitialisation des répartitions
        pPracticeResult.resetRepartitions();
        // Récupération des types de mesure traités par la formule
        AbstractFormulaBO formula = pPractice.getFormula();
        String[] measureKinds = new String[formula.getMeasureKinds().size()];
        formula.getMeasureKinds().toArray( measureKinds );
        MeasureBO[] measures = new MeasureBO[formula.getMeasureKinds().size()];
        Long componentId = null;
        // Traitement de la note pour chaque composant
        Iterator it = pChildren.iterator();
        // On stocke les notes
        ArrayList marks = new ArrayList();
        while ( it.hasNext() )
        {
            AbstractComponentBO child = (AbstractComponentBO) it.next();
            componentId = new Long( child.getId() );
            // Récupération de chaque mesure
            boolean measuresNotNull = true;
            for ( int i = 0; i < measureKinds.length; i++ )
            {
                Long auditId = new Long( pAudit.getId() );
                MeasureBO measure =
                    (MeasureBO) MeasureDAOImpl.getInstance().load(
                                                                   pSession,
                                                                   componentId,
                                                                   auditId,
                                                                   Mapping.getMeasureClass( measureKinds[i] + "."
                                                                       + formula.getComponentLevel() ) );
                measures[i] = measure;
                // Une mesure peut être absente, ce qui serait lié à un problème de création des mesures
                // dans la phase qui précède le calcul des notes
                if ( measure == null )
                {
                    measuresNotNull = false;
                    // On indique le type de mesure qui n'est pas trouvé ainsi que le composant concerné
                    LOG.warn( RuleMessages.getString( "missingmeasure", new Object[] { measureKinds[i], componentId,
                        auditId } ) );
                }
            }
            // On calcul la note seulement si toutes les mesures requises sont bien présentes
            if ( measuresNotNull )
            {
                MarkBO mark = computeMark( pSession, pInterpreter, measures, pPractice, pPracticeResult, child );
                marks.add( mark );
            }
        }
        return marks;
    }

    /**
     * Calcul d'une note sur un composant
     * 
     * @param pSession session
     * @param pInterpreter interprète
     * @param pMeasures mesures
     * @param pPractice pratique
     * @param pPracticeResult résultat de pratique
     * @param pChild composant
     * @return la note
     * @throws FormulaException si erreur
     * @throws JrafDaoException si erreur
     */

    private static MarkBO computeMark( ISession pSession, FormulaInterpreter pInterpreter, MeasureBO[] pMeasures,
                                       PracticeRuleBO pPractice, PracticeResultBO pPracticeResult,
                                       AbstractComponentBO pChild )
        throws FormulaException, JrafDaoException
    {
        MarkBO mark = new MarkBO();
        mark.setComponent( pChild );
        mark.setPractice( pPracticeResult );
        // Calcul de la note
        // L'exception dans le calcul est remontée telle quelle
        Number value = pInterpreter.evaluate( pPractice.getFormula(), pMeasures );
        // On place une note -1 si la formule n'a pu être calculée
        if ( value == null )
        {
            mark.setValue( MarkBO.NOT_NOTED_VALUE );
        }
        else
        {
            mark.setValue( value.floatValue() );
        }
        // Mise à jour de la répartition des notes
        pPracticeResult.incrementRepartition( mark.getValue() );
        // Enregistrement de la note
        MarkDAOImpl.getInstance().save( pSession, mark );
        return mark;
    }

    /**
     * This method push into the base, for the current audit, the manual mark which are filled and still valid
     * 
     * @param session Hibernate session
     * @param project The project
     * @param manualPractices collection of practice without level (manual practice)
     * @param practicesMap map of all the practice.
     * @return true If the last mark for a manual practice is out of date
     * @throws JrafDaoException Exception happened during hibernate work
     */
    private static boolean computeManualPractices( ISession session, ProjectBO project, Collection manualPractices,
                                                Map practicesMap )
        throws JrafDaoException
    {
        boolean warning = false;
        if ( manualPractices != null )
        {
            try
            {
                QualityResultDAOImpl dao = QualityResultDAOImpl.getInstance();

                // For each manual practicewe try to set a mark for the audit
                Iterator<PracticeRuleBO> practicesIt = manualPractices.iterator();
                while ( practicesIt.hasNext() )
                {
                    PracticeRuleBO practice = practicesIt.next();
                    LOG.info( RuleMessages.getString( "computation.practice", new Object[] { practice.getName() } ) );

                    // search of the last manual mark inserted
                    PracticeResultBO manualPraticeResult =
                        dao.findLastManualMark( session, project.getId(), practice.getId() );

                    // If there is one manual mark inserted
                    if ( manualPraticeResult != null )
                    {
                        // recovering of the PracticeResultBO which will recorded
                        PracticeResultBO practiceResult = (PracticeResultBO) practicesMap.get( practice );

                        /*
                         * If the current date is before the date of creation of the manual mark plus the period of
                         * validity then we put the mark for the current audit.
                         */
                        if ( isMarkValid( practice, manualPraticeResult ) )
                        {
                            practiceResult.setMeanMark( manualPraticeResult.getMeanMark() );
                            dao.save( session, practiceResult );
                        }
                        else
                        {
                            warning=true;
                        }
                    }
                }
            }
            catch ( HibernateException e )
            {
                throw new JrafDaoException( e );
            }
        }
        return warning;
    }

    /**
     * This method determine whether the mark is valid (according to the period validity)
     * 
     * @param practice The practice for the current audit
     * @param manualPraticeResult The last manualPratice inserted
     * @return true if the the mark is valid
     */
    private static boolean isMarkValid( PracticeRuleBO practice, PracticeResultBO manualPraticeResult )
    {

        boolean valid = true;

        // Date of creation of the manual mark
        Date creationDate = manualPraticeResult.getCreationDate();

        // The validity period
        String validityPeriod = practice.getTimeLimitation();

        // Test the validity of the mark
        valid = TimeLimitationParser.isMarkValid( validityPeriod, creationDate );

        return valid;
    }
}
