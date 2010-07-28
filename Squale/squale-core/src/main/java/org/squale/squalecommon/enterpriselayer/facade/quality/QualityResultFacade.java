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
package org.squale.squalecommon.enterpriselayer.facade.quality;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import org.squale.squalecommon.daolayer.component.AuditGridDAOImpl;
import org.squale.squalecommon.daolayer.result.MarkDAOImpl;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.daolayer.result.PracticeResultDAOImpl;
import org.squale.squalecommon.daolayer.result.QualityResultDAOImpl;
import org.squale.squalecommon.daolayer.result.rulechecking.RuleCheckingTransgressionDAOImpl;
import org.squale.squalecommon.daolayer.result.rulechecking.RuleCheckingTransgressionItemDAOImpl;
import org.squale.squalecommon.daolayer.rule.QualityRuleDAOImpl;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.AuditGridDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.export.ActionPlanDTO;
import org.squale.squalecommon.datatransfertobject.remediation.ComponentCriticalityComparator;
import org.squale.squalecommon.datatransfertobject.remediation.ComponentCriticalityDTO;
import org.squale.squalecommon.datatransfertobject.remediation.PracticeCriticalityComparator;
import org.squale.squalecommon.datatransfertobject.remediation.PracticeCriticalityDTO;
import org.squale.squalecommon.datatransfertobject.result.MarkDTO;
import org.squale.squalecommon.datatransfertobject.result.PracticeEvolutionDTO;
import org.squale.squalecommon.datatransfertobject.result.QualityResultDTO;
import org.squale.squalecommon.datatransfertobject.result.ResultsDTO;
import org.squale.squalecommon.datatransfertobject.rule.AbstractFormulaDTO;
import org.squale.squalecommon.datatransfertobject.rule.PracticeRuleDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityRuleDTO;
import org.squale.squalecommon.datatransfertobject.rulechecking.RuleCheckingDTO;
import org.squale.squalecommon.datatransfertobject.transform.component.AuditGridTransform;
import org.squale.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import org.squale.squalecommon.datatransfertobject.transform.result.MarkTransform;
import org.squale.squalecommon.datatransfertobject.transform.result.PracticeEvolutionTransform;
import org.squale.squalecommon.datatransfertobject.transform.result.QualityResultTransform;
import org.squale.squalecommon.datatransfertobject.transform.result.ResultsTransform;
import org.squale.squalecommon.datatransfertobject.transform.rule.QualityRuleTransform;
import org.squale.squalecommon.datatransfertobject.transform.rulechecking.RuleCheckingItemTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditGridBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO;
import org.squale.squalecommon.enterpriselayer.facade.FacadeMessages;
import org.squale.squalecommon.enterpriselayer.facade.component.ComponentFacade;
import org.squale.squalecommon.enterpriselayer.facade.rule.QualityGridFacade;
import org.squale.squalecommon.util.ConstantRulesChecking;
import org.squale.squalecommon.util.mapping.Mapping;

/**
 * By m400841
 */
public final class QualityResultFacade
    implements IFacade
{

    /** log */
    private static Log LOG = LogFactory.getLog( QualityResultFacade.class );

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * The ruke prefix
     */
    private final static String RULE_PREFIX = "rule.";

    /**
     * Obtention des résultats qualité
     * 
     * @param pAuditDTOs audits
     * @param pProject projet
     * @param pRuleDTOs DTOs des règles qualité
     * @return résultats
     * @throws JrafEnterpriseException si erreur
     */
    public static ResultsDTO getQResults( Collection pAuditDTOs, ComponentDTO pProject, Collection pRuleDTOs )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = new ResultsDTO(); // retour de la facade
        List resultsBO = null; // retour des différentes DAOs

        // Initialisation de la session Hibernate
        ISession session = null;

        // Initialisation des parametres des differentes DAO
        Long componentID = new Long( pProject.getID() ); // identifiant de l'audit
        try
        {

            session = PERSISTENTPROVIDER.getSession();

            // ajout de la premiere liste dans ResultsDTO
            results.put( null, new ArrayList( pRuleDTOs ) );

            // Initialisation des DAOs
            QualityResultDAOImpl qualityResultDAO = QualityResultDAOImpl.getInstance();
            MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
            AbstractComponentDAOImpl componentDao = AbstractComponentDAOImpl.getInstance();

            // recuperation du composant objet metier pour en connaitre son type
            AbstractComplexComponentBO componentBO =
                (AbstractComplexComponentBO) componentDao.get( session, componentID );

            // Lancement n fois de la methode avec la liste des classes des TREs en parametre
            Iterator auditIterator = pAuditDTOs.iterator();
            AuditDTO auditTemp = null;
            Long auditID = null;

            // Conversion des RuleDTO en Id
            ArrayList ruleIds = new ArrayList();
            Iterator rulesIt = pRuleDTOs.iterator();
            while ( rulesIt.hasNext() )
            {
                QualityRuleDTO ruleDTO = (QualityRuleDTO) rulesIt.next();
                ruleIds.add( new Long( ruleDTO.getId() ) );
            }

            while ( auditIterator.hasNext() && results != null )
            {

                auditTemp = (AuditDTO) auditIterator.next();
                auditID = new Long( auditTemp.getID() );

                resultsBO = new ArrayList();

                resultsBO.addAll( ResultsTransform.bo2Dto( qualityResultDAO.findWhere( session, componentID, auditID,
                                                                                       ruleIds ) ) );
                results.put( auditTemp, resultsBO );
            }

        }
        catch ( JrafDaoException e )
        {
            results = null;
            LOG.error( QualityResultFacade.class.getName() + ".getQResultByTREAndAudit", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getQResltsByTREAndAudit" );
        }

        return results;
    }

    /**
     * Permet de récupérer une valeur pour un type de résultat, un composant et un audit donné
     * 
     * @param pAudit AuditDTO valide renseignant l'identifiant de l'audit
     * @param pComponent ComponentDTO valide contenant l'identifiant du composant
     * @param pKeyTRE clé du type élémentaire de résultat valide
     * @return ResultsDTO correspondant à une valeur
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB500F7
     */
    public static ResultsDTO getQResults( AuditDTO pAudit, ComponentDTO pComponent, Long pKeyTRE )
        throws JrafEnterpriseException
    {

        // initialisation
        ResultsDTO results = null; // retour de la facade
        Object daoResult = null; // retour de la dao

        // parametre de la dao
        // Class treClass = CommonMessages.getClass(pKeyTRE); // classe relative a la clé du TRE
        Long componentID = new Long( pComponent.getID() );
        Long auditID = new Long( pAudit.getID() );

        // Session hibernate et dao des composants
        ISession session = null;
        AbstractComponentDAOImpl abstractComponentDao = AbstractComponentDAOImpl.getInstance();

        try
        {

            session = PERSISTENTPROVIDER.getSession();

            AbstractComponentBO componentBO = (AbstractComponentBO) abstractComponentDao.get( session, componentID );

            // Traitement du cas ou le composant est different du projet pour les pratiques
            if ( !( componentBO instanceof ProjectBO ) )
            {
                MarkDAOImpl markDao = MarkDAOImpl.getInstance();
                daoResult = markDao.load( session, componentID, auditID, pKeyTRE );

                // Traitement des autres cas
            }
            else
            {
                QualityResultDAOImpl qualityResultDAO = QualityResultDAOImpl.getInstance();
                daoResult = qualityResultDAO.load( session, componentID, auditID, pKeyTRE );
            }

            // ajout des listes au ResultsDTO
            List resultList = new ArrayList();
            results = new ResultsDTO();
            resultList.add( daoResult );
            results.put( null, ResultsTransform.bo2Dto( resultList ) );

        }
        catch ( JrafDaoException e )
        {
            results = null;
            LOG.error( QualityResultFacade.class.getName() + ".getQResults", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getQReslts" );
        }

        return results;

    }

    /**
     * Permet de récupérer une liste de valeurs pour une liste de types de résultat, un type de composant et un audit
     * donné Format de ResultsDTO : 2 lignes : -- null en clé et liste des clés des TREs en valeur -- ComponentDTO en
     * clé et liste des résultats associées en valeur
     * 
     * @param pAudit AudtDTO renseignant l'ID de l'audit
     * @param pComponent ComponentDTO renseignant l'ID du composant
     * @return ResultsDTO
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB50114
     */
    public static ResultsDTO getAllQResults( AuditDTO pAudit, ComponentDTO pComponent )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = null; // retour de la facade
        List daoResults = new ArrayList(); // retour des differentes DAOs

        // Initialisation de la session Hibernate
        ISession session = null;

        // Initialisation des parametres de la DAO
        Long auditID = new Long( pAudit.getID() ); // identifiant de l'audit
        Long componentID = new Long( pComponent.getID() );
        // identifiant du composant
        List sortedTreKeys = new ArrayList();
        try
        {

            session = PERSISTENTPROVIDER.getSession();

            AbstractComponentDAOImpl componentDao = AbstractComponentDAOImpl.getInstance();
            AbstractComponentBO componentBO = (AbstractComponentBO) componentDao.get( session, componentID );

            // Traitement du cas de composants differents de projet et cles de pratiques
            if ( !( componentBO instanceof ProjectBO ) )
            {
                MarkDAOImpl markDao = MarkDAOImpl.getInstance();
                daoResults.addAll( markDao.findWhere( session, componentID, auditID ) );
                // Construction des clefs
                Iterator it = daoResults.iterator();
                while ( it.hasNext() )
                {
                    MarkBO mark = (MarkBO) it.next();
                    QualityRuleDTO dto = QualityRuleTransform.bo2Dto( mark.getPractice().getRule(), false );
                    sortedTreKeys.add( dto );
                }
            }
            else
            {
                // Traitement des autres cas
                QualityResultDAOImpl qualityResultDAO = QualityResultDAOImpl.getInstance();
                daoResults.addAll( qualityResultDAO.findWhere( session, componentID, auditID ) );

            }
            // ajout des listes au ResultsDTO
            results = new ResultsDTO();
            // Traitement des clefs
            results.put( null, sortedTreKeys );
            List resultsBO = ResultsTransform.bo2Dto( daoResults );
            results.put( pComponent, resultsBO );

        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".getQResultByTRE", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getQResltsByTRE" );
        }

        return results;
    }

    /**
     * Récupère les pratiques liées à un audit et à un projet
     * 
     * @param pProjectID l'id du projet
     * @param pAuditID l'id de l'audit
     * @return les pratiques
     * @throws JrafEnterpriseException exception JRAF
     */
    public static Collection findPracticesWhere( Long pProjectID, Long pAuditID )
        throws JrafEnterpriseException
    {
        // Initialisation de la session Hibernate
        ISession session = null;
        Collection resultsBO = new ArrayList( 0 );
        Collection resultsDto = new ArrayList( 0 );
        Collection resultsDtoAll = new ArrayList( 0 );
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            QualityResultDAOImpl dao = QualityResultDAOImpl.getInstance();
            resultsBO = dao.findPracticesWhere( session, pProjectID, pAuditID );
            // Transformation
            Iterator it = resultsBO.iterator();
            while ( it.hasNext() )
            {
                QualityRuleBO ruleBo = ( (QualityResultBO) it.next() ).getRule();
                QualityRuleDTO ruleDto = QualityRuleTransform.bo2Dto( ruleBo, true );
                resultsDto.add( ruleDto );
            }
            // ajoute les pratiques
            resultsDtoAll.add( resultsDto );
            // les notes associées
            resultsDtoAll.add( ResultsTransform.bo2Dto( resultsBO ) );
        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".findPracticesWhere", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".findPracticesWhere" );
        }

        return resultsDtoAll;
    }

    /**
     * Récupère les noms des pratiques liées à un audit et à un type de composant
     * 
     * @param pProjectID l'id du projet
     * @param pAuditID l'id de l'audit
     * @return les pratiques
     * @throws JrafEnterpriseException exception JRAF
     */
    public static Collection findPracticeNameWhere( Long pProjectID, Long pAuditID )
        throws JrafEnterpriseException
    {
        // Initialisation de la session Hibernate
        ISession session = null;
        Collection resultsBO = new ArrayList( 0 );
        Collection names = new ArrayList( 0 );
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            QualityResultDAOImpl dao = QualityResultDAOImpl.getInstance();
            resultsBO = dao.findPracticesWhere( session, pProjectID, pAuditID );
            for ( Iterator it = resultsBO.iterator(); it.hasNext(); )
            {
                QualityRuleBO ruleBo = ( (QualityResultBO) it.next() ).getRule();
                // On transforme directement le nom pour le web afin de pouvoir trier
                names.add( "rule." + ruleBo.getName() );
            }
        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".findPracticeNameWhere", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".findPracticeNameWhere" );
        }

        return names;
    }

    /**
     * Permet de récupérer des valeurs pour un type de résultat, un type de composant et plusieurs audits donnés
     * 
     * @param pKeyTRE clé du type elementaire de resultat
     * @param pProject ComponentDTO renseignant l'ID du composant
     * @param pAuditDTOs liste des DTOs des audits souhaités
     * @return ResultsDTO
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB50133
     */
    public static ResultsDTO getQResultsByAudit( List pAuditDTOs, Long pKeyTRE, ComponentDTO pProject )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = null; // retour de la facade
        List daoResults = new ArrayList(); // retour de qualityResultDAOImpl

        // Initialisation de la session Hibernate
        ISession session = null;

        // Initialisation des parametres de la DAO
        Long componentID = new Long( pProject.getID() );
        // identifiant de l'audit
        List auditIDs = new ArrayList(); // identifiant du composant

        // Recuperation sous forme de liste des identifiants des audits
        // On supprime avant les audits qui peuvent être nuls car l'audit précédent peut être nul
        pAuditDTOs.remove( null );
        Iterator auditIterator = pAuditDTOs.iterator();
        Long idTemp = null;
        while ( auditIterator.hasNext() )
        {
            // L'audit précédent peut être nul!
            AuditDTO currentAudit = (AuditDTO) auditIterator.next();
            idTemp = new Long( currentAudit.getID() );
            auditIDs.add( idTemp );
        }
        try
        {

            session = PERSISTENTPROVIDER.getSession();

            AbstractComponentDAOImpl componentDao = AbstractComponentDAOImpl.getInstance();
            AbstractComplexComponentBO componentBO =
                (AbstractComplexComponentBO) componentDao.get( session, componentID );

            QualityResultDAOImpl qualityResultDAO = QualityResultDAOImpl.getInstance();
            daoResults.addAll( qualityResultDAO.findWhere( session, componentID, auditIDs, pKeyTRE ) );
            // ajout des listes au ResultsDTO
            if ( daoResults.size() == auditIDs.size() )
            {
                results = new ResultsDTO();
                results.put( null, pAuditDTOs );
                List resultsBO = ResultsTransform.bo2Dto( daoResults );
                results.put( pProject, resultsBO );
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.qualityresultfacade.getbyaudit.listssizedifferent" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".getQResultByAudit", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getQResltsByAudit" );
        }

        return results;

    }

    /**
     * Permet de récupérer des valeurs pour une liste de types de résultat, une liste de types de composant et un audit
     * donnés
     * 
     * @param pAudit AuditDTO renseignant l'ID de l'audit
     * @param pComponentDTOs liste des DTOs des projets souhaités
     * @return ResultsDTO dans le format suivant -- Clé : null -- Valeur : { [clés de pratiques], [clés de resultats
     *         qualités] } -- Clé : ComponentDTO -- Valeur : liste de résultats (n fois)
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB50148
     */
    public static ResultsDTO getQResultsByFactorAndProject( List pComponentDTOs, AuditDTO pAudit )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = new ResultsDTO(); // retour de la facade
        List resultsBO = null; // transformation du retour des différentes DAOs

        // Initialisation de la session Hibernate
        ISession session = null;

        // Initialisation des parametres des differentes DAO
        Long auditID = new Long( pAudit.getID() ); // identifiant de l'audit

        try
        {

            // Initialisation de la session Hibernate
            session = PERSISTENTPROVIDER.getSession();

            // Lancement n fois de la methode avec la liste des des classes des TREs en parametre
            Iterator componentIterator = pComponentDTOs.iterator();
            ComponentDTO currentComponent = null;
            Long componentID = null;
            String componentType = null;

            // Initialisation des differentes Daos
            MarkDAOImpl markDao = MarkDAOImpl.getInstance();
            QualityResultDAOImpl qualityResultDao = QualityResultDAOImpl.getInstance();
            AbstractComponentDAOImpl componentDao = AbstractComponentDAOImpl.getInstance();
            AuditGridDAOImpl auditGridDao = AuditGridDAOImpl.getInstance();
            while ( componentIterator.hasNext() && results != null )
            {

                currentComponent = (ComponentDTO) componentIterator.next();
                componentID = new Long( currentComponent.getID() );
                componentType = currentComponent.getType();

                resultsBO = new ArrayList();

                AbstractComplexComponentBO currentComponentBO =
                    (AbstractComplexComponentBO) componentDao.get( session, componentID );

                resultsBO.addAll( ResultsTransform.bo2Dto( qualityResultDao.findWhere( session, componentID, auditID ) ) );
                AuditGridDTO auditGrid = getAuditGrid( session, currentComponent, pAudit );
                if ( auditGrid != null )
                {
                    // Ajout d'une nouvelle ligne dans le ResultsDTO
                    results.put( auditGrid, resultsBO );
                }
            }

        }
        catch ( JrafDaoException e )
        {
            results = null;
            LOG.error( QualityResultFacade.class.getName() + ".getQResultByTREAndComponent", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getQResltsByTREAndComponent" );
        }

        return results;
    }

    /**
     * Obtention de la grille qualité correspondant à un audit
     * 
     * @param pProject projet
     * @param pAudit audit
     * @return grille qualité rattachée à l'audit
     * @throws JrafEnterpriseException si erreur
     */
    public static AuditGridDTO getAuditGrid( ComponentDTO pProject, AuditDTO pAudit )
        throws JrafEnterpriseException
    {
        AuditGridDTO result = null;
        // Initialisation de la session Hibernate
        ISession session = null;

        try
        {
            // Initialisation de la session Hibernate
            session = PERSISTENTPROVIDER.getSession();
            result = getAuditGrid( session, pProject, pAudit );
        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".getAuditGrid", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getAuditGrid" );
        }
        return result;
    }

    /**
     * Obtention de la grille d'audit pour un projet et un audit La grille correspondante est cherchée dans la abse de
     * données, si elle n'existe pas une grille vierge est renvoyée
     * 
     * @param pSession session session
     * @param pProject projet projet
     * @param pAudit audit audit
     * @return grille d'audit correspondante, une grille vierge est renvoyée si la grille n'est pas trouvée dans la base
     * @throws JrafEnterpriseException si erreur
     * @throws JrafDaoException si erreur
     */
    private static AuditGridDTO getAuditGrid( ISession pSession, ComponentDTO pProject, AuditDTO pAudit )
        throws JrafEnterpriseException, JrafDaoException
    {
        AuditGridDTO result = null;
        Long auditID = new Long( pAudit.getID() ); // identifiant de l'audit
        AuditGridDAOImpl auditGridDao = AuditGridDAOImpl.getInstance();
        AuditGridBO auditGrid = auditGridDao.findWhere( pSession, new Long( pProject.getID() ), auditID );
        if ( auditGrid != null )
        {
            result = AuditGridTransform.bo2dto( auditGrid );
        }
        else
        {
            // On créé une grille vide
            result = new AuditGridDTO();
            result.setGrid( new QualityGridDTO() );
            result.getGrid().setFactors( new ArrayList() );
            result.setProject( pProject );
            result.setAudit( pAudit );
        }
        return result;
    }

    /**
     * Permet de récupérer des valeurs pour une liste de types de résultat, un type de composant et une liste d'audits
     * donnés
     * 
     * @dev-squale créer la même méthode que pour les projets avec en parametres une collection d'audits et une
     *             collection de TREs
     * @param pProject ComponentDTO renseignant l'ID du composant. Peut être <code>null</code> dans le cas de la
     *            comparaison de projets
     * @param pAuditDTOs liste des DTOs des audits souhaités
     * @return ResultsDTO
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFFB5015A
     */
    public static ResultsDTO getQResultsByTREAndAudit( List pAuditDTOs, ComponentDTO pProject )
        throws JrafEnterpriseException
    {

        // Initialisation
        ResultsDTO results = new ResultsDTO(); // retour de la facade
        List resultsBO = null; // retour des différentes DAOs

        // Initialisation de la session Hibernate
        ISession session = null;

        // Initialisation des parametres des differentes DAO
        Long componentID = new Long( pProject.getID() ); // identifiant de l'audit
        try
        {

            session = PERSISTENTPROVIDER.getSession();

            // Initialisation des DAOs
            QualityResultDAOImpl qualityResultDAO = QualityResultDAOImpl.getInstance();
            MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
            AbstractComponentDAOImpl componentDao = AbstractComponentDAOImpl.getInstance();
            AuditGridDAOImpl auditGridDao = AuditGridDAOImpl.getInstance();

            // recuperation du composant objet metier pour en connaitre son type
            AbstractComplexComponentBO componentBO =
                (AbstractComplexComponentBO) componentDao.get( session, componentID );
            ComponentDTO projectDTO = new ComponentDTO();
            projectDTO.setID( componentBO.getId() );
            // Lancement n fois de la methode avec la liste des des classes des TREs en parametre
            Iterator auditIterator = pAuditDTOs.iterator();
            AuditDTO auditTemp = null;
            Long auditID = null;

            while ( auditIterator.hasNext() && results != null )
            {

                auditTemp = (AuditDTO) auditIterator.next();
                if ( null != auditTemp )
                {
                    auditID = new Long( auditTemp.getID() );

                    resultsBO = new ArrayList();

                    resultsBO.addAll( ResultsTransform.bo2Dto( qualityResultDAO.findWhere( session, componentID,
                                                                                           auditID ) ) );
                    AuditGridDTO auditGrid = getAuditGrid( session, projectDTO, auditTemp );
                    results.put( auditGrid, resultsBO );
                }
            }

        }
        catch ( JrafDaoException e )
        {
            results = null;
            LOG.error( QualityResultFacade.class.getName() + ".getQResultByTREAndAudit", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getQResltsByTREAndAudit" );
        }

        return results;
    }

    /**
     * Permet de récupérer les notes associés a une pratique ainsi que le tableau de répartition
     * 
     * @param pAudits liste des AuditDTOs
     * @param pProject ComponentDTO relatif a un projet
     * @param pPratice pratique
     * @return ResultsDTO avec les deux Map renseignes
     * @throws JrafEnterpriseException exception Jraf
     */
    public static ResultsDTO getRepartition( List pAudits, ComponentDTO pProject, PracticeRuleDTO pPratice )
        throws JrafEnterpriseException
    {

        // Initialisation
        ISession session = null; // session Hibernate
        pAudits.remove( null );
        List auditIds = initAuditIds( pAudits ); // liste des identifiants des AuditDTO
        Long projectId = new Long( pProject.getID() );
        ResultsDTO results = null; // retour de la methode
        Map intRepartitionMap = new HashMap(); // Map de la repartition de composants sur une pratique pour des
        // intervalles de pas = 1
        Map floatRepartitionMap = new HashMap(); // Map de la repartition de composants sur une pratique pour des
        // intervalles de pas = 0.1
        List practiceBOs = null;

        // Initialisation des Daos
        QualityResultDAOImpl qualityResultDao = QualityResultDAOImpl.getInstance();

        try
        {

            session = PERSISTENTPROVIDER.getSession();
            Long treKey = new Long( pPratice.getId() );
            results = getQResultsByAudit( pAudits, treKey, pProject );

            // Chargement des pratiques pour recuperer les repartitions
            practiceBOs = qualityResultDao.findWhere( session, projectId, auditIds, treKey );

            // Mise des valeurs dans la Map repartitionMap
            if ( practiceBOs != null )
            {
                ListIterator practiceIterator = practiceBOs.listIterator();
                PracticeResultBO practiceResult = null;
                while ( practiceIterator.hasNext() )
                {

                    Object objTemp = practiceIterator.next();

                    if ( objTemp != null )
                    {
                        practiceResult = (PracticeResultBO) objTemp;
                        intRepartitionMap.put( pAudits.get( practiceIterator.nextIndex() - 1 ),
                                               practiceResult.getIntRepartition() );
                        floatRepartitionMap.put( pAudits.get( practiceIterator.nextIndex() - 1 ),
                                                 practiceResult.getFloatRepartition() );
                    }
                    else
                    {
                        intRepartitionMap.put( pAudits.get( practiceIterator.nextIndex() - 1 ), objTemp );
                        floatRepartitionMap.put( pAudits.get( practiceIterator.nextIndex() - 1 ), objTemp );
                    }
                }
            }

            // affectation de la map au ResultsDTO
            if ( results != null )
            {
                if ( intRepartitionMap.keySet().size() > 0 )
                {
                    results.setIntRepartitionPracticeMap( intRepartitionMap );
                }
                if ( floatRepartitionMap.keySet().size() > 0 )
                {
                    results.setFloatRepartitionPracticeMap( floatRepartitionMap );
                }
            }
            else
            {
                results = null;
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".getRepartiton", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getRepartition" );
        }

        return results;

    }

    /**
     * Permet de récupérer les notes associés a une pratique ainsi que les regles avec le nombre de fois que elle a été
     * transgressée
     * 
     * @param pAudits liste des AuditDTOs
     * @param pProject ComponentDTO relatif a un projet
     * @param pPratice pratique
     * @return ResultsDTO avec les deux Map renseignes ResultsMap : <br/>
     *         -- Clé : null -- Valeur : Liste d'AuditDTO <br/>
     *         -- Clé : ComponentDTO -- Valeurs : notes associées aux audits dans le même ordre que la liste d'audit de
     *         type java.lang.Float<br/>
     *         PracticeMap : n lignes de la forme suivante :<br/>
     *         -- Clé : AuditDTO -- Valeur : map de <RuleCheckingDTO,java.lang.Integer>
     * @throws JrafEnterpriseException exception Jraf
     */
    public static ResultsDTO getRuleChecking( List pAudits, ComponentDTO pProject, PracticeRuleDTO pPratice )
        throws JrafEnterpriseException
    {

        // Initialisation
        ISession session = null; // session Hibernate
        List auditIds = initAuditIds( pAudits ); // liste des identifiants des AuditDTO
        Long projectId = new Long( pProject.getID() );
        ResultsDTO results = null; // retour de la methode
        Map practiceMap = new HashMap(); // Map du nombre de violation sur une règle
        Collection measures = null;
        RuleCheckingTransgressionBO measure = null;

        // Initialisation des Daos
        RuleCheckingTransgressionDAOImpl measureDAO = RuleCheckingTransgressionDAOImpl.getInstance();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            Long treKey = new Long( pPratice.getId() );
            results = getQResultsByAudit( pAudits, treKey, pProject );
            ListIterator auditIdsIterator = auditIds.listIterator();
            Long currentID = null;
            // On construit la liste des types de mesures liées à la pratique
            // On récupère la pratique en base pour avoir la formule
            PracticeRuleBO ruleWithFormula =
                (PracticeRuleBO) QualityRuleDAOImpl.getInstance().get( session, new Long( pPratice.getId() ) );
            AbstractFormulaBO formula = ruleWithFormula.getFormula();
            Collection measuresKind = buildClassesMeasureKind( formula.getMeasureKinds(), formula.getComponentLevel() );
            while ( auditIdsIterator.hasNext() )
            {
                // recuperer les mesures Ruleschecking des différents audits
                currentID = (Long) ( auditIdsIterator.next() );
                measures = measureDAO.load( session, projectId, currentID );
                if ( measures.size() == 0 )
                {
                    LOG.error( QualityResultFacade.class.getName() + ".getRuleChecking"
                        + "mesures de rulechecking sont nulles" );
                }
                // Map contenant tous les résultats, celà permet de
                // fusionner des données issues de différents outils
                Map allResults = new HashMap();
                // On parcours les mesures jusqu'à pouvoir insérer les règles
                // dans la map des pratiques
                for ( Iterator it = measures.iterator(); it.hasNext(); )
                {
                    measure = (RuleCheckingTransgressionBO) it.next();
                    // Si la mesure est prise en compte dans le calcul de la note
                    // on ajoute les résultats associés
                    if ( measuresKind.contains( measure.getClass() ) )
                    {
                        Map resultsRulesChecking = makeRulesCheckingDTO( measure, pPratice.getName() );
                        // On insère les règles dans la map des pratiques
                        allResults.putAll( resultsRulesChecking );
                    }
                }
                AuditDTO auditDTO = (AuditDTO) pAudits.get( auditIdsIterator.nextIndex() - 1 );
                practiceMap.put( auditDTO, allResults );
            }

            // affectation de la map au ResultsDTO
            results.setIntRepartitionPracticeMap( practiceMap );
        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".getRuleChecking", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getRuleChecking" );
        }
        return results;

    }

    /**
     * @param pCollection la collection des types de mesures
     * @param pComponentLevel le type de composant
     * @return les classes correspondantes aux types des mesures
     */
    private static Collection buildClassesMeasureKind( Collection pCollection, String pComponentLevel )
    {
        Collection results = new ArrayList();
        for ( Iterator it = pCollection.iterator(); it.hasNext(); )
        {
            results.add( Mapping.getMeasureClass( (String) it.next() + "." + pComponentLevel ) );
        }
        return results;
    }

    /**
     * Récupère les détails d'une transgressions pour une règle donnée.
     * 
     * @param pMeasureID l'id de la mesure
     * @param pRuleID l'id de la règle
     * @return les détails de la mesure concernant la règle d'id <code>pRuleID</code>
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getRuleCheckingItems( Long pMeasureID, Long pRuleID )
        throws JrafEnterpriseException
    {
        // Initialisation
        ISession session = null; // session Hibernate
        Collection details = new ArrayList();
        // Initialisation des Daos
        RuleCheckingTransgressionItemDAOImpl itemDAO = RuleCheckingTransgressionItemDAOImpl.getInstance();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            details = itemDAO.findWhereMeasureAndRule( session, pMeasureID, pRuleID );
            details = RuleCheckingItemTransform.bo2Dto( details );
        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".getRuleCheckingItems", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getRuleCheckingItems" );
        }
        return details;
    }

    /**
     * @param pMeasure mesure rulechecking
     * @param pRuleCategory catégorie de al règle
     * @return Map les règles et leurs nombre d'occurence
     */
    private static Map makeRulesCheckingDTO( RuleCheckingTransgressionBO pMeasure, String pRuleCategory )
    {
        // TODO adapter le code pour le rendre générique
        // Déclarations
        Map eventNumberMap = new HashMap();
        boolean status = false;
        Object ob = null;
        Collection rules = null;
        IntegerMetricBO value = null;
        RuleBO rule = null;
        RuleCheckingDTO ruleCheckingDto = null;
        // on recupère la liste des transgressions(metrics)
        // qui correpondent à cette pratique
        ob = pMeasure.getRuleSet();
        // le ruleSet ne doit pas être null sinon il y a une erreur
        if ( ob != null )
        {
            Map rulesMap = ( (RuleSetBO) ob ).getRules();
            rules = rulesMap.values();
            // On parcours le ruleSet
            for ( Iterator itRule = rules.iterator(); itRule.hasNext(); )
            {
                rule = (RuleBO) itRule.next();
                // prendre que les catégorie des règle concernées
                StringBuffer category = new StringBuffer( "rule." );
                category.append( rule.getCategory() );
                if ( category.toString().equals( pRuleCategory ) )
                {
                    value = (IntegerMetricBO) pMeasure.getMetrics().get( rule.getCode() );
                    if ( value != null )
                    {
                        // On crée le dto
                        ruleCheckingDto = new RuleCheckingDTO();
                        ruleCheckingDto.setId( rule.getId() );
                        ruleCheckingDto.setSeverity( rule.getSeverity() );
                        ruleCheckingDto.setName( rule.getCode() );
                        ruleCheckingDto.setMeasureID( pMeasure.getId() );
                        eventNumberMap.put( ruleCheckingDto, value.getValue() );
                    }
                    else
                    {
                        status = true;
                        eventNumberMap = null;
                        LOG.error( QualityResultFacade.class.getName() + ".makeRulesCheckingDTO"
                            + "la metric de la mesure est null" );
                    }
                }
            }
        }
        else
        {
            LOG.error( QualityResultFacade.class.getName() + ".makeRulesCheckingDTO"
                + "RuleSet de Mesure rulechecking: " + pMeasure.getId() + "  est null" );
        }
        return eventNumberMap;
    }

    /**
     * Permet de recuperer une liste d'identifiants d'AuditDTO a partir des objets
     * 
     * @param pAuditDTOs liste d'AuditDTO
     * @return List liste des identifiants des AuditDTOs
     */
    private static List initAuditIds( List pAuditDTOs )
    {
        // Initialisation
        List auditIds = new ArrayList(); // retour de la methode
        Iterator auditIterator = pAuditDTOs.iterator();
        Long currentID = null;
        while ( auditIterator.hasNext() )
        {
            currentID = new Long( ( (AuditDTO) auditIterator.next() ).getID() );
            auditIds.add( currentID );
        }
        return auditIds;
    }

    /**
     * Constructeur vide
     * 
     * @roseuid 42CBFFB50179
     */
    private QualityResultFacade()
    {
    }

    /**
     * La table retournée est de la forme : <br/>
     * key : ComponentDTO value : ResultDTO représentant la liste des pratiques qui ont changées
     * 
     * @param firstAudit l'audit le plus récent
     * @param secondAudit l'audit le plus ancien
     * @param project le projet
     * @param filter le filtre à utiliser
     * @param pLimit le nombre max de résultats à récupérer
     * @throws JrafEnterpriseException si erreur
     * @return une table des pratiques qui ont changées entre <code>firstAudit</code> et <code>secondAudit</code> pour
     *         les composants de type <code>componentType</code>
     */
    public static Collection getChangedComponents( AuditDTO firstAudit, AuditDTO secondAudit, ComponentDTO project,
                                                   Object[] filter, Integer pLimit )
        throws JrafEnterpriseException
    {
        // Initialisation
        ISession session = null; // session Hibernate
        /* Map results = new HashMap(); // Map des résultats */
        Collection evolutionDto = new ArrayList();
        int cpt = pLimit.intValue();
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            // On récupère les composants qui n'existent plus
            MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
            Long auditId1 = new Long( firstAudit.getID() );
            Long auditId2 = new Long( secondAudit.getID() );
            Long projectId = new Long( project.getID() );

            if ( null == filter[PracticeEvolutionDTO.ONLY_UP_OR_DOWN_ID]
                && null == filter[PracticeEvolutionDTO.THRESHOLD_ID] )
            {
                // On récupère les changements et on les transforme
                // On recherche les composants qui on été supprimés
                Collection evolutionBO =
                    markDAO.findDeletedComponents( session, auditId1, auditId2, projectId, filter, cpt );
                evolutionDto = PracticeEvolutionTransform.markCollectionToDto( evolutionBO, false );
                cpt -= evolutionDto.size();
                if ( cpt > 0 )
                {
                    // On recherche les nouveaux composants
                    Collection newBO =
                        markDAO.findDeletedComponents( session, auditId2, auditId1, projectId, filter, cpt );
                    cpt -= newBO.size();
                    evolutionDto.addAll( PracticeEvolutionTransform.markCollectionToDto( newBO, true ) );
                }
                if ( cpt > 0 )
                {
                    // Puis les composants qui ont changé de note entre les deux audits
                    Collection changedTab =
                        markDAO.findChangedComponentWhere( session, auditId1, auditId2, projectId, filter, cpt );
                    Collection changedDto = PracticeEvolutionTransform.tabCollectionToDto( changedTab );
                    evolutionDto.addAll( changedDto );
                }
            }
            else
            {
                evolutionDto = getFilterComponents( session, auditId1, auditId2, projectId, filter, cpt );
            }
        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".getChangedComponents", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getChangedComponents" );
        }
        return evolutionDto; // results;
    }

    /**
     * @param session la session
     * @param auditId1 l'audit le plus récent
     * @param auditId2 l'audit le plus ancien
     * @param projectId le projet
     * @param filter le filtre
     * @param limit le nombre max de composants à récupérer
     * @throws JrafDaoException si erreur
     * @return une table des pratiques qui ont changées entre <code>firstAudit</code> et <code>secondAudit</code> pour
     *         les composants de type <code>componentType</code> et filtrés avec <code>filtre</code>
     */
    private static Collection getFilterComponents( ISession session, Long auditId1, Long auditId2, Long projectId,
                                                   Object[] filter, int limit )
        throws JrafDaoException
    {
        // On récupère les changements et on les transforme
        Collection evolutionDto = new ArrayList();
        MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
        if ( null != filter[PracticeEvolutionDTO.THRESHOLD_ID] )
        {
            Collection evolutionBO =
                markDAO.findDeletedComponents( session, auditId2, auditId1, projectId, filter, limit );
            evolutionDto = PracticeEvolutionTransform.markCollectionToDto( evolutionBO, true );
        }
        else
        {
            // Si on ne veut que les composants supprimés
            if ( PracticeEvolutionDTO.DELETED.equals( (String) filter[PracticeEvolutionDTO.ONLY_UP_OR_DOWN_ID] ) )
            {
                Collection evolutionBO =
                    markDAO.findDeletedComponents( session, auditId1, auditId2, projectId, filter, limit );
                evolutionDto = PracticeEvolutionTransform.markCollectionToDto( evolutionBO, true );
            }
            else
            {
                Collection evolutionTab =
                    markDAO.findChangedComponentWhere( session, auditId1, auditId2, projectId, filter, limit );
                evolutionDto = PracticeEvolutionTransform.tabCollectionToDto( evolutionTab );
            }
        }
        return evolutionDto;
    }

    /**
     * Récupère les pratiques à corriger pour le plan d'action (sous forme ActionPlanDTO)
     * 
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @param pHasLimit indique si il y a une limite du nombre de corrections à remonter
     * @return les pratiques refusées avec les premiers composants ayant la plus mauvaise note pour cette pratique (ou
     *         les transgressions)
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getWorstPractices( String pAuditId, String pProjectId, boolean pHasLimit )
        throws JrafEnterpriseException
    { // Initialisation
        // Initialisation
        ISession session = null; // session Hibernate
        Collection results = new ArrayList(); // le retour, on conserve l'ordre d'insertion des clés
        MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
        RuleCheckingTransgressionItemDAOImpl itemDAO = RuleCheckingTransgressionItemDAOImpl.getInstance();
        AuditDTO audit = new AuditDTO();
        audit.setID( Long.parseLong( pAuditId ) );
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            // On récupère les pratiques refusées pour ce projet et cet audit
            PracticeResultDAOImpl practiceDAO = PracticeResultDAOImpl.getInstance();
            Collection worstPractices = practiceDAO.findPracticesForActionPlan( session, pProjectId, pAuditId );
            if ( pHasLimit )
            {
                results = getCorrectionsForActionPlan( session, worstPractices, audit, pProjectId );
            }
            else
            {
                results = countCorrectionsForActionPlan( session, worstPractices, audit, pProjectId );
            }
        }
        catch ( JrafDaoException e )
        {
            LOG.error( QualityResultFacade.class.getName() + ".getWorstPractices", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getWorstPractices" );
        }
        return results;
    }

    /**
     * @param pSession la session
     * @param pPractices les pratiques
     * @param pAudit l'audit
     * @param pProjectId l'id du projet
     * @return une liste d'ActionPlanDTO répertoriant les pratiques et le nombre de composants ou transgressions à
     *         corriger
     * @throws JrafDaoException si erreur dao
     * @throws JrafEnterpriseException si erreur jraf
     */
    private static Collection countCorrectionsForActionPlan( ISession pSession, Collection pPractices, AuditDTO pAudit,
                                                             String pProjectId )
        throws JrafDaoException, JrafEnterpriseException
    {
        // Initialisation
        MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
        MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
        ActionPlanDTO actionPlan = null;
        Collection results = new ArrayList(); // le retour, on conserve l'ordre d'insertion des clés
        int nbcorrections = 0;
        // Pour chaque pratique, on transforme en DTO et on récupère le nombre des composants
        // ou transgressions à corriger
        for ( Iterator it = pPractices.iterator(); it.hasNext(); )
        {
            QualityResultBO practiceBO = (QualityResultBO) it.next();
            String ruleName = practiceBO.getRule().getName();
            // Transformation en DTO
            QualityResultDTO practiceDTO = QualityResultTransform.bo2Dto( practiceBO );
            PracticeRuleDTO rule = (PracticeRuleDTO) practiceDTO.getRule();
            if ( rule.isRuleChecking() )
            {
                // Récupération des types de mesure traités par la formule
                AbstractFormulaDTO formula = rule.getFormula();
                // Pour chaque type de mesure, on va récupérer le nom de sa classe
                String[] measureKinds = new String[formula.getMeasureKinds().size()];
                formula.getMeasureKinds().toArray( measureKinds );
                for ( int i = 0; i < measureKinds.length; i++ )
                {
                    Class currentClass = Mapping.getMeasureClass( measureKinds[i] + "." + formula.getComponentLevel() );
                    if ( currentClass.getSuperclass().equals( RuleCheckingTransgressionBO.class ) )
                    {
                        RuleCheckingTransgressionBO trans =
                            (RuleCheckingTransgressionBO) measureDAO.load( pSession,
                                                                           new Long( Long.parseLong( pProjectId ) ),
                                                                           new Long( pAudit.getID() ), currentClass );
                        nbcorrections +=
                            trans.getTotalInfoNumberForCategory( ruleName )
                                + trans.getTotalErrorNumberForCategory( ruleName )
                                + trans.getTotalWarningNumberForCategory( ruleName );
                    }
                }
            }
            else
            {
                // On récupère les métriques
                Collection treChildren = ComponentFacade.getTREChildren( new Long( rule.getId() ) );
                // On récupère les plus mauvais composants pour cette pratiques
                nbcorrections =
                    markDAO.countWorstWhere( pSession, new Long( practiceBO.getId() ), practiceDTO.getMeanMark() );
            }
            actionPlan = new ActionPlanDTO( practiceDTO, nbcorrections );
            results.add( actionPlan );
            nbcorrections = 0;
        }
        return results;
    }

    /**
     * @param pSession la session
     * @param pPractices les pratiques
     * @param pAudit l'audit
     * @param pProjectId l'id du projet
     * @return une liste d'ActionPlanDTO répertoriant les pratiques et les composants ou transgressions à corriger
     * @throws JrafDaoException si erreur dao
     * @throws JrafEnterpriseException si erreur jraf
     */
    private static Collection getCorrectionsForActionPlan( ISession pSession, Collection pPractices, AuditDTO pAudit,
                                                           String pProjectId )
        throws JrafDaoException, JrafEnterpriseException
    {
        // Initialisation
        final Integer limit = new Integer( 100 ); // On veut récupérer les 100 plus mauvais composants
        // On ne doit récupérer qu'au maximum 100 composants donc on va décrémenter nbCorrections
        // à chaque fois qu'on récupère des composants ou des transgressions
        int nbCorrections = limit.intValue();
        MarkDAOImpl markDAO = MarkDAOImpl.getInstance();
        RuleCheckingTransgressionItemDAOImpl itemDAO = RuleCheckingTransgressionItemDAOImpl.getInstance();
        ActionPlanDTO actionPlan = null;
        Collection results = new ArrayList(); // le retour, on conserve l'ordre d'insertion des clés
        // Pour chaque pratique, on transforme en DTO et on récupère les plus mauvais composants
        // ou transgressions jusqu'à 100
        for ( Iterator it = pPractices.iterator(); it.hasNext() && nbCorrections > 0; )
        {
            QualityResultBO practiceBO = (QualityResultBO) it.next();
            String ruleName = practiceBO.getRule().getName();
            // Transformation en DTO
            QualityResultDTO practiceDTO = QualityResultTransform.bo2Dto( practiceBO );
            PracticeRuleDTO rule = (PracticeRuleDTO) practiceDTO.getRule();
            if ( rule.isRuleChecking() )
            {
                // Récupération des types de mesure traités par la formule
                AbstractFormulaDTO formula = rule.getFormula();
                // Pour chaque type de mesure, on va récupérer le nom de sa classe
                String[] measureKinds = new String[formula.getMeasureKinds().size()];
                formula.getMeasureKinds().toArray( measureKinds );
                String[] measures = new String[measureKinds.length];
                for ( int i = 0; i < measureKinds.length; i++ )
                {
                    Class pTreClass = Mapping.getMeasureClass( measureKinds[i] + "." + formula.getComponentLevel() );
                    measures[i] = pTreClass.getName();
                }
                Collection items = new ArrayList();
                Collection itemsFound = new ArrayList();
                // On tri par sévérité
                for ( int i = 0; i < ConstantRulesChecking.SEVERITIES.length && nbCorrections > 0; i++ )
                {
                    itemsFound =
                        itemDAO.findWhereMeasureClass( pSession, pProjectId, "" + pAudit.getID(), measures,
                                                       ConstantRulesChecking.SEVERITIES[i], ruleName,
                                                       new Integer( nbCorrections ) );
                    items.addAll( itemsFound );
                    nbCorrections -= itemsFound.size();
                }
                actionPlan = new ActionPlanDTO( practiceDTO, null, RuleCheckingItemTransform.bo2Dto( items ) );

            }
            else
            {
                // On récupère les métriques
                Collection treChildren = ComponentFacade.getTREChildren( new Long( rule.getId() ) );
                // On récupère les plus mauvais composants pour cette pratiques
                Collection marks =
                    markDAO.findWorstWhere( pSession, new Long( practiceBO.getId() ), practiceDTO.getMeanMark(),
                                            new Integer( nbCorrections ) );
                nbCorrections -= marks.size();
                // On récupère les résultats associés à chaque composant
                ResultsDTO result =
                    MeasureFacade.getMeasuresByTREAndComponent( new Long( rule.getId() ), new ArrayList( treChildren ),
                                                                new ArrayList( getComponents( pSession, marks ) ),
                                                                pAudit );
                actionPlan = new ActionPlanDTO( practiceDTO, result, null );

            }
            results.add( actionPlan );
        }
        return results;
    }

    /**
     * @param pSession la session hibernate
     * @param pMarks les MarkBO
     * @return les composants associés sous forme DTO
     * @throws JrafDaoException si erreur
     */
    private static Collection getComponents( ISession pSession, Collection pMarks )
        throws JrafDaoException
    {
        AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
        List components = new ArrayList();
        if ( pMarks != null )
        {
            // et recupere le composant correspondant
            Iterator markIterator = pMarks.iterator();
            ComponentDTO currentComponent = null;
            if ( components == null )
            {
                components = new ArrayList();
            }
            while ( markIterator.hasNext() )
            {
                MarkBO mark = (MarkBO) markIterator.next();
                // On récupère le véritable composant (sans proxy) dans le cas où on a besoin d'initialiser le nom
                // du fichier par exemple pour l'export IDE
                AbstractComponentBO component =
                    (AbstractComponentBO) dao.load( pSession, new Long( mark.getComponent().getId() ) );
                currentComponent = ComponentTransform.bo2DtoWithFullName( component );
                components.add( currentComponent );
            }

        }
        else
        {
            LOG.error( FacadeMessages.getString( "facade.exception.componentfacade.get.marknull" ) );
        }
        return components;
    }

    /**
     * This method get back the last manual mark linked to the project and the practice rule give in argument
     * 
     * @param projectId Id of the project
     * @param practiceId Id of the practice rule
     * @return a QualityResultDTO which contains the manual mark
     * @throws JrafEnterpriseException Exception happened during the process
     */
    public static QualityResultDTO findManualQualityResult( long projectId, long practiceId )
        throws JrafEnterpriseException
    {
        ISession session = null;

        QualityResultBO manualPraticeResult = null;
        QualityResultDTO resultDto = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            QualityResultDAOImpl dao = QualityResultDAOImpl.getInstance();
            // Search of the last mark for the practice
            manualPraticeResult = dao.findLastManualMark( session, projectId, practiceId );
            if ( manualPraticeResult != null )
            {
                // Transform the BO into DTO
                resultDto = QualityResultTransform.bo2Dto( manualPraticeResult );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".findManualQualityResult" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".findManualQualityResult" );
        }
        return resultDto;

    }

    /**
     * This method get back the last manual mark linked to the project, the practice rule and the audit given in
     * argument
     * 
     * @param projectId ID of the project
     * @param practiceId ID of the practice rule
     * @param auditId ID of the audit
     * @return a QualityResultDTO wich contains the manual mark
     * @throws JrafEnterpriseException Exception happened during the process
     */
    public static QualityResultDTO findManualQualityResultByAudit( long projectId, long practiceId, long auditId )
        throws JrafEnterpriseException
    {
        ISession session = null;
        QualityResultBO manualPracticeResult = null;
        QualityResultDTO resultDto = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            QualityResultDAOImpl dao = QualityResultDAOImpl.getInstance();
            // Search of the last mark for the practice and the audit
            manualPracticeResult = dao.findLastManualMarkByAudit( session, projectId, practiceId, auditId );
            if ( manualPracticeResult != null )
            {
                // Transform BO into DTO
                resultDto = QualityResultTransform.bo2Dto( manualPracticeResult );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".findManualQualityResultByAudit" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".findManualQualityResultByAudit" );
        }
        return resultDto;
    }

    /**
     * This method record in DB mark for a manual practice
     * 
     * @param resultDto The result to record
     * @throws JrafEnterpriseException exception happen during the record
     */
    public static void createManualResult( QualityResultDTO resultDto )
        throws JrafEnterpriseException
    {
        ISession session = null;
        QualityResultDAOImpl dao = QualityResultDAOImpl.getInstance();
        //
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            session.beginTransaction();
            // Transform the DTO into BO
            QualityResultBO resultToSave = QualityResultTransform.simplifyDto2Bo( resultDto );
            // Save of the result
            dao.create( session, resultToSave );
            session.commitTransaction();
        }
        catch ( JrafPersistenceException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".createManualResult" );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".createManualResult" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".createManualResult" );
        }

    }

    /**
     * This method return the markDTO link to the audit, rule and project give in argument
     * 
     * @param projectId The id of the project
     * @param auditId The id of the audit
     * @param ruleId the id of the rule
     * @return a markDto or NULL if no mark is found
     * @throws JrafEnterpriseException Exception happen during the serach
     */
    public static MarkDTO getPracticeByAuditRuleProject( long projectId, long auditId, long ruleId )
        throws JrafEnterpriseException
    {
        ISession session = null;
        MarkDAOImpl dao = MarkDAOImpl.getInstance();
        MarkDTO dto = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            // Search of the markBO
            MarkBO bo = dao.load( session, projectId, auditId, ruleId );
            // Transform the markBO into markDTO
            if ( bo != null )
            {
                dto = MarkTransform.bo2Dto( bo );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".updateManualResult" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".updateManualResult" );
        }
        return dto;
    }

    /**
     * This method return the list of marks link to the given audit and the given rule
     * 
     * @param auditId The id of the audit
     * @param ruleId the id of the rule
     * @return a list of markDTO
     * @throws JrafEnterpriseException Exception happen during the serach
     */
    public static Collection<MarkDTO> getPracticeByAuditRule( long auditId, long ruleId )
        throws JrafEnterpriseException
    {
        ISession session = null;
        MarkDAOImpl dao = MarkDAOImpl.getInstance();
        Collection<MarkDTO> dtoCollection = new ArrayList<MarkDTO>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            // Search of the markBO
            Collection<MarkBO> boCollection = dao.load( session, auditId, ruleId );
            // Transform the markBO into markDTO
            dtoCollection = (Collection<MarkDTO>) MarkTransform.bo2Dto( boCollection );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".updateManualResult" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".updateManualResult" );
        }
        return dtoCollection;
    }

    /**
     * Returns raw data that will be used by the Distribution Map to display, for a specific practice, the marks of all
     * the components related to this practice, for the given audit and project. <br>
     * The raw data that is returned is a list of arrays, each array containing the following data:
     * <ul>
     * <li>0 - the component ID [long]</li>
     * <li>1 - the component name [String]</li>
     * <li>2 - the ID of the component's parent [long]</li>
     * <li>3 - the name of the component's parent [String]</li>
     * <li>4 - the mark of this component for the practice [float]</li>
     * </ul>
     * 
     * @param auditId the audit
     * @param projectId the project
     * @param practiceId the practice
     * @return a list of object arrays, each array corresponding to the data of a component related to the practice
     * @throws JrafEnterpriseException if the method fails to retrieve the data
     */
    public static List<Object[]> getMarkDistribution( long auditId, long projectId, long practiceId )
        throws JrafEnterpriseException
    {
        // Find the component level for this practice
        String componentLevel = QualityGridFacade.getComponentLevelForPractice( practiceId );

        // And look for the desired data
        ISession session = null;
        List<Object[]> result = new ArrayList<Object[]>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            result =
                QualityResultDAOImpl.getInstance().findMarkDistribution( session, auditId, projectId, practiceId,
                                                                         componentLevel );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".getMarkDistribution" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getMarkDistribution" );
        }

        return result;
    }

    /**
     * This method retrieves the factor linked to the audit and the application given in argument
     * 
     * @param auditId The audit id
     * @param applicationId The application id
     * @return The list of factor linked to the current audit
     * @throws JrafEnterpriseException Exception occurs during the search of the factors
     */
    public static List<QualityResultDTO> getFactor( long auditId, long applicationId )
        throws JrafEnterpriseException
    {
        ISession session = null;
        List<QualityResultBO> result = new ArrayList<QualityResultBO>();
        List<QualityResultDTO> dataToreturn = new ArrayList<QualityResultDTO>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            result = QualityResultDAOImpl.getInstance().findFactor( session, applicationId, auditId );
            QualityResultDTO resultDto = null;
            for ( QualityResultBO qualityResultBO : result )
            {
                resultDto = QualityResultTransform.bo2Dto( qualityResultBO );
                dataToreturn.add( resultDto );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".getMarkDistribution" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getMarkDistribution" );
        }

        return dataToreturn;
    }

    /**
     * Returns raw data that will be used by the Motion Chart. <br>
     * 
     * @param applicationId the id of the application that must be displayed in the Motion Chart
     * @return a MotionChartApplicationData object that can be used to iterate through the data
     * @throws JrafEnterpriseException if the method fails to get the data
     */
    public static MotionChartApplicationMetricData findMetricsForMotionChart( long applicationId )
        throws JrafEnterpriseException
    {
        ISession session = null;
        List<Object[]> result = new ArrayList<Object[]>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            result = QualityResultDAOImpl.getInstance().findMetricsForMotionChart( session, applicationId );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".findMetricsForMotionChart" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".findMetricsForMotionChart" );
        }

        return new MotionChartApplicationMetricData( result );
    }

    /**
     * Convenience class to iterate through the results used for the Motion Chart
     */
    public static class MotionChartApplicationMetricData
    {
        private List<Object[]> applicationData;

        public MotionChartApplicationMetricData( List<Object[]> data )
        {
            this.applicationData = data;
        }

        public Iterator<Object[]> iterator()
        {
            return applicationData.iterator();
        }

        public long getProjectId( Object[] currentData )
        {
            return (Long) currentData[0];
        }

        public String getProjectName( Object[] currentData )
        {
            return (String) currentData[1];
        }

        public long getAuditId( Object[] currentData )
        {
            return (Long) currentData[2];
        }

        public Date getAuditDate( Object[] currentData )
        {
            Date auditHistoricalDate = (Date) currentData[3];
            Date auditStartDate = (Date) currentData[4];
            Date auditDate = ( auditHistoricalDate == null ) ? auditStartDate : auditHistoricalDate;
            return auditDate;
        }

        public String getMetricName( Object[] currentData )
        {
            return (String) currentData[5];
        }

        public int getMetricValue( Object[] currentData )
        {
            return (Integer) ( (IntegerMetricBO) currentData[6] ).getValue();
        }
    }

    /**
     * This method searches for the audit and the module given in argument, all the components involved in the audit and
     * for each component its practice. This method return the list of component sorted by their criticality. And each
     * component contains the list of its pratice sorted by their criticality
     * 
     * @param session The hibernate session
     * @param auditId The id of the audit
     * @param moduleId The id of the module
     * @return The list of component sorted by there criticality. And each component contains it's list of practice
     *         sorted by their criticality
     * @throws JrafEnterpriseException exception occurs during the process
     */
    public static List<ComponentCriticalityDTO> getRemediationByCriticality( ISession session, long auditId,
                                                                             long moduleId )
        throws JrafEnterpriseException
    {
        List<ComponentCriticalityDTO> compoList = new ArrayList<ComponentCriticalityDTO>();
        try
        {
            MarkDAOImpl dao = MarkDAOImpl.getInstance();
            // We retrieve the list of component practice mark linked to the module and the audit
            final int componentColumn = 0;
            final int ruleIdColumn = 1;
            final int ruleNameColumn = 2;
            final int ruleCriticalityColumn = 3;
            final int ruleEffortColumn = 4;
            final int markValueColumn = 5;
            List<Object[]> markList = dao.getByAudit( session, auditId, moduleId );

            Map<Long, ComponentCriticalityDTO> map = new HashMap<Long, ComponentCriticalityDTO>();

            for ( Object[] mark : markList )
            {
                ComponentCriticalityDTO compo = null;
                AbstractComponentBO componentBO = (AbstractComponentBO) mark[componentColumn];
                // for each mark we test if the linked component already exist in the map. If yes we retrieve it, else
                // we create it.
                if ( !map.containsKey( componentBO.getId() ) )
                {
                    compo =
                        new ComponentCriticalityDTO( componentBO.getId(), componentBO.getName(),
                                                     componentBO.getFullName(), componentBO.getType() );
                    map.put( compo.getId(), compo );
                }
                else
                {
                    compo = map.get( componentBO.getId() );
                }

                // We create the new PraticeCriticalityDTO
                // In order to retrieve the real name of the rule defined in the message.xml file, we should prefix the
                // rule name by : "rule."
                PracticeCriticalityDTO practice =
                    new PracticeCriticalityDTO( (Long) mark[ruleIdColumn], RULE_PREFIX + (String) mark[ruleNameColumn],
                                                (Integer) mark[ruleCriticalityColumn],
                                                (Integer) mark[ruleEffortColumn], (Float) mark[markValueColumn] );
                // We compute the criticality of the current practice mark.
                practice.computePracticeComponentCriticality();
                // We add the PraticeCriticalityDTO the ComponentCriticalityDTO
                compo.addPracticeList( practice );
                // We add the criticality of the pratice to the criticality of the component
                compo.setCriticality( compo.getCriticality() + practice.getPracticeComponentCriticality() );
            }
            compoList = createList( map );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityResultFacade.class.getName() + ".getRemediationByCriticity" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityResultFacade.class.getName() + ".getRemediationByCriticity" );
        }
        return compoList;
    }

    /**
     * Transform the map values into sorted list
     * 
     * @param map the map values
     * @return sorted list
     */
    private static List<ComponentCriticalityDTO> createList( Map<Long, ComponentCriticalityDTO> map )
    {
        List<ComponentCriticalityDTO> compoList = new ArrayList<ComponentCriticalityDTO>();
        compoList = new ArrayList<ComponentCriticalityDTO>( map.values() );
        // For each ComponentCriticalityDTO, we sort its PracticeCriticality by their criticality
        Iterator<ComponentCriticalityDTO> compoIt = compoList.iterator();
        while ( compoIt.hasNext() )
        {
            ComponentCriticalityDTO componentCriticalityDTO = (ComponentCriticalityDTO) compoIt.next();
            if ( componentCriticalityDTO.isHasBadMark() )
            {
                Collections.sort( componentCriticalityDTO.getPracticeList(), new PracticeCriticalityComparator() );
            }
            else
            {
                compoIt.remove();
            }
        }
        // We sort the list of ComponentCriticityDTO by their criticality
        Collections.sort( compoList, new ComponentCriticalityComparator() );
        // write( compoList );
        return compoList;
    }

}
