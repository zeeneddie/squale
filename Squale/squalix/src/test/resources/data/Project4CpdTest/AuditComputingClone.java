package com.airfrance.squalecommon.enterpriselayer.facade.rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.helper.LoggingHelper;
import com.airfrance.jraf.provider.persistence.hibernate.SessionImpl;
import com.airfrance.jraf.spi.logging.ILogger;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditGridDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MarkDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.result.QualityResultDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.CriteriumResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.AbstractFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.FactorRuleBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * Calcul d'un audit
 * Le calcul d'un audit s'appuie sur des règles décrites dans la grille qualité,
 * celle-ci est rattachée à un projet. Les règles sont exprimées en fonction de
 * métriques obtenues par des outils comme mccabe par exemple.
 */
public class AuditComputing {
    /** Log */
    private static ILogger LOG = LoggingHelper.getInstance(AuditComputing.class);

    /**
     * Calcul d'un audit
     * Le calcul d'un audit se fait à partir des pratiques pour remonter vers les
     * facteurs
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @throws JrafDaoException si erreur
     */
    public static void computeAuditResult(ISession pSession, ProjectBO pProject, AuditBO pAudit) throws JrafDaoException {
        try {
            LOG.info(RuleMessages.getString("computation.start"));
            // Décomposition de la grille en facteurs, criteres, pratiques
            Map factors = new Hashtable();
            Map criteria = new Hashtable();
            Map practices = new Hashtable();
            // Les maps contiennent en clef la règle et en valeur le résultat calculé
            flattenQualitfyGrid(pSession, pProject, pAudit, factors, criteria, practices);

            // Le traitement se fait dans l'ordre hiérarchique inverse
            // celà permet d'éviter les calculs multiples pour des facteurs
            // ou des critères plusieurs fois présents

            // Traitement des pratiques
            computePracticesResults(pSession, pProject, pAudit, practices);
            // Traitement des criteres
            computeCriteriaResults(pSession, criteria, practices);
            // Traitement des facteurs
            computeFactorsResults(pSession, factors, criteria, practices);
            // Ajout de la grille qualité
            AuditGridBO auditGrid = new AuditGridBO();
            auditGrid.setGrid(pProject.getQualityGrid());
            auditGrid.setProject(pProject);
            auditGrid.setAudit(pAudit);
            AuditGridDAOImpl.getInstance().create(pSession, auditGrid);
            // Ajout à l'audit
            pAudit.addAuditGrid(auditGrid);
            AuditDAOImpl.getInstance().save(pSession, pAudit);
        } finally {
            LOG.info(RuleMessages.getString("computation.end"));
        }
    }


    /**
     * Calcul des facteurs
     * Chaque facteur est calculé en fonction des critères qui le composent
     * @param pSession session
     * @param pFactors facteurs
     * @param pCriteria critères
     * @param pPractices pratiques
     * @throws JrafDaoException si erreur
     */
    private static void computeFactorsResults(ISession pSession, Map pFactors, Map pCriteria, Map pPractices) throws JrafDaoException {
        // On parcourt chaque facteur et on calcule son résultat
        Iterator factors = pFactors.keySet().iterator();
        while (factors.hasNext()) {
            // Récupération du facteur et du résultat
            FactorRuleBO factor = (FactorRuleBO) factors.next();
            LOG.info(RuleMessages.getString("computation.factor", new Object[] { factor.getName()}));
            FactorResultBO result = (FactorResultBO) pFactors.get(factor);
            // Le calcul se fait par une moyenne simple sur les critères
            // calculés si au moins 2 criteres ont été calculés
            int nbCriteria = 0; // Nombre de criteres calculés
            float criteriaSum = 0; // Note cumulée
            // Parcours des criteres du facteur
            Iterator it = factor.getCriteria().iterator();
            // Pour chaque critère, on récupère les valeurs calculées
            while (it.hasNext()) {
                CriteriumResultBO criteriumResult = (CriteriumResultBO) pCriteria.get(it.next());
                // Nota : criteriumResult ne peut pas être null
                if (-1 != criteriumResult.getMeanMark()) {
                    criteriaSum += criteriumResult.getMeanMark();
                    nbCriteria++;
                }
            }
            // Calcul seulement s'il existe au moins deux résultats
            if (nbCriteria > 1) {
                // calcul de la note du facteur
                result.setMeanMark(criteriaSum / nbCriteria);
                QualityResultDAOImpl.getInstance().save(pSession, result);
            } else {
                LOG.debug(RuleMessages.getString("nocomputation", new Object[] { factor.getName()}));
            }
        }
    }

    /**
     * Mise à plat d'une grille qualité
     * La structure hiérarchique de la grille qualité est mise à plat
     * sous la forme de 3 maps qui contiennent les règles en fonction de leur
     * typologie
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @param pFactors map des facteurs
     * @param pCriteria map des critères
     * @param pPractices map des pratiques
     * @throws JrafDaoException si erreur
     */
    private static void flattenQualitfyGrid(ISession pSession, ProjectBO pProject, AuditBO pAudit, Map pFactors, Map pCriteria, Map pPractices) throws JrafDaoException {
        // Le parcours consiste à construire 3 maps avec les facteurs, criteres et pratiques
        // Ce partionnement permet le traitement plus efficace d'au audit dans le cas où des critères
        // ou des pratiques apparaissent de façon multiple
        // On place aussi dans ces map, en tant que valeur le résultat associé, par exemple
        // la map des facteurs contient les FactorRuleBO et les FactorResultBO
        Iterator factors = pProject.getQualityGrid().getFactors().iterator();
        // Parcours des facteurs
        while (factors.hasNext()) {
            FactorRuleBO factor = (FactorRuleBO) factors.next();
            // Création du résultat associé
            FactorResultBO fResult = new FactorResultBO();
            fResult.setRule(factor);
            fResult.setProject(pProject);
            fResult.setAudit(pAudit);
            pFactors.put(factor, fResult);
            QualityResultDAOImpl.getInstance().create(pSession, fResult);
            Iterator criteria = factor.getCriteria().iterator();
            // Parcours des critères
            while (criteria.hasNext()) {
                CriteriumRuleBO criterium = (CriteriumRuleBO) criteria.next();
                // Un critère peut être présent dans plusieurs facteurs
                // On ne le rajoute que si nécessaire
                if (pCriteria.get(criterium) == null) {
                    // Création du résultat associé
                    CriteriumResultBO cResult = new CriteriumResultBO();
                    cResult.setRule(criterium);
                    cResult.setProject(pProject);
                    cResult.setAudit(pAudit);
                    QualityResultDAOImpl.getInstance().create(pSession, cResult);
                    // Ajout dans la map
                    pCriteria.put(criterium, cResult);
                }
                Iterator practices = criterium.getPractices().iterator();
                // Parcours des pratiques
                while (practices.hasNext()) {
                    PracticeRuleBO practice = (PracticeRuleBO) practices.next();
                    // Une pratique peut être présente dans plusieurs critères
                    // On ne le rajoute que si nécessaire
                    if (pPractices.get(practice) == null) {
                        // Création du résultat associé
                        PracticeResultBO pResult = new PracticeResultBO();
                        pResult.setRule(practice);
                        pResult.setProject(pProject);
                        pResult.setAudit(pAudit);
                        QualityResultDAOImpl.getInstance().create(pSession, pResult);
                        // Ajout dans la map
                        pPractices.put(practice, pResult);
                    }
                }
            }
        }
    }
    /**
     * Calcul des critères
     * Chaque critère est calculé en fonction des résultats de ses pratiques
     * @param pSession session
     * @param pCriteria critères
     * @param pPractices pratiques
     * @throws JrafDaoException si erreur
     */
    private static void computeCriteriaResults(ISession pSession, Map pCriteria, Map pPractices) throws JrafDaoException {
        // On parcourt chaque critère et on calcule son résultat
        Iterator criteria = pCriteria.keySet().iterator();
        while (criteria.hasNext()) {
            // Récupération du critère et de son résultat
            CriteriumRuleBO criterium = (CriteriumRuleBO) criteria.next();
            LOG.info(RuleMessages.getString("computation.criterium", new Object[] { criterium.getName()}));
            CriteriumResultBO result = (CriteriumResultBO) pCriteria.get(criterium);
            // La moyenne se fait par un moyenne simple sur les pratiques
            // calculés si au moins deux pratiques ont été calculées
            int nbPractices = 0; // Nombre de criteres calculés
            float practicesSum = 0; // Note cumulée
            // Parcours des pratiques du critère
            Iterator it = criterium.getPractices().iterator();
            // Pour chaque critère, on récupère les valeurs calculées
            while (it.hasNext()) {
                PracticeResultBO praticeResult = (PracticeResultBO) pPractices.get(it.next());
                // Nota : criteriumResult ne peut pas être null
                if (-1 != praticeResult.getMeanMark()) {
                    practicesSum += praticeResult.getMeanMark();
                    nbPractices++;
                }
            }
            // Calcul seulement s'il existe au moins deux résultats
            if (nbPractices > 1) {
                // calcul de la note du facteur
                result.setMeanMark(practicesSum / nbPractices);
                QualityResultDAOImpl.getInstance().save(pSession, result);
            } else {
                LOG.debug(RuleMessages.getString("nocomputation", new Object[] { criterium.getName()}));
            }
        }
    }


    /**
     * Traitement des pratiques de niveau projet
     * Les pratiques calculées au niveau d'un projet sont traitées de façon
     * spécifique, la formule donne une note directement sans avoir à collecter des notes
     * pour chaque composant (comme c'est le cas au niveau de classes ou des méthodes)
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @param pProjectPractices pratiques de niveau projet
     * @param pPractices pratiques
     * @throws JrafDaoException si erreur
     */
    private static void computeProjectPractices(ISession pSession, ProjectBO pProject, AuditBO pAudit, Collection pProjectPractices, Map pPractices) throws JrafDaoException {
        if (pProjectPractices != null) {
            try {
                Iterator practices = pProjectPractices.iterator();
                while (practices.hasNext()) {
                    PracticeRuleBO practice = (PracticeRuleBO) practices.next();
                    LOG.info(RuleMessages.getString("computation.practice", new Object[] { practice.getName()}));
                    PracticeResultBO practiceResult = (PracticeResultBO) pPractices.get(practice);
                    // Vérification de la formule
                    FormulaInterpreter interpreter = new FormulaInterpreter();
                    try {
                        // Vérification de la syntaxe de la formule
                        // un exception est levée si elle est incorrecte
                        interpreter.checkSyntax(practice.getFormula());
                        // Calcul de la note globale de la pratique
                        computeProjectPracticeMark(pSession, pProject, pAudit, interpreter, practice, practiceResult);
                    } catch (FormulaException e) {
                        // Si une erreur se produit sur le calcul d'une formule
                        // on stoppe le calcul pour cette pratique
                        LOG.error(RuleMessages.getString("formula.error", new Object[] { practice.getName()}), e);
                    }
                    // Sauvegarde des résultats de la pratique
                    QualityResultDAOImpl.getInstance().save(pSession, practiceResult);
                }
            } catch (HibernateException e) {
                // Cette erreur est liée à la gestion du flushmode, dans ce cas on se contente de remonter
                // une exception JRAF
                throw new JrafDaoException(e);
            }
        }

    }

    /**
     * Calcul des pratiques
     * Chaque pratique est calculée, son résultat est écrit en base de données
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @param pPractices pratiques
     * @throws JrafDaoException si erreur
     */
    private static void computePracticesResults(ISession pSession, ProjectBO pProject, AuditBO pAudit, Map pPractices) throws JrafDaoException {
        // Les pratiques sont triées par par type
        // de composant pour eviter les appels multiples de recuperation des
        // composants du projet
        Map kindPractices = splitPracticesByKind(pPractices);
        // On traite séparement les pratiques de niveau projet
        computeProjectPractices(pSession, pProject, pAudit, (Collection) kindPractices.remove("project"), pPractices);
        Iterator kinds = kindPractices.keySet().iterator();
        while (kinds.hasNext()) {
            String kind = (String) kinds.next();
            LOG.info(RuleMessages.getString("computation.kind", new Object[] { kind }));
            // sauvegarde du mode actuel de flush
            Session hibernateSession = ((SessionImpl) pSession).getSession();
            FlushMode oldFlushMode = hibernateSession.getFlushMode();
            // On ne traite pas les pratiques qui n'ont pas de composant associé
            // (elles n'ont pas de formule)
            if (kind.length() > 0) {
                try {
                    // flush de la session
                    hibernateSession.flush();
                    // le flush automatique est désactivé pendant tout le calcul de chaque notes
                    hibernateSession.setFlushMode(FlushMode.NEVER);

                    // Recupération des enfants de bons niveaux... 
                    Collection children = AbstractComponentDAOImpl.getInstance().findProjectChildren(pSession, pProject, pAudit, Mapping.getComponentClass("component." + kind));
                    Iterator practices = ((Collection) kindPractices.get(kind)).iterator();
                    while (practices.hasNext()) {
                        PracticeRuleBO practice = (PracticeRuleBO) practices.next();
                        LOG.info(RuleMessages.getString("computation.practice", new Object[] { practice.getName()}));
                        PracticeResultBO practiceResult = (PracticeResultBO) pPractices.get(practice);
                        // Vérification de la formule
                        FormulaInterpreter interpreter = new FormulaInterpreter();
                        try {
                            // Vérification de la syntaxe de la formule
                            // un exception est levée si elle est incorrecte
                            interpreter.checkSyntax(practice.getFormula());
                            // Calcul de notes pour chaque enfant
                            computePracticeResults(pSession, pAudit, interpreter, practice, practiceResult, children);
                            // Calcul de la note globale de la pratique
                            computePracticeMark(practice, practiceResult);
                        } catch (FormulaException e) {
                            // Si une erreur se produit sur le calcul d'une formule
                            // on stoppe le calcul pour cette pratique
                            LOG.error(RuleMessages.getString("formula.error", new Object[] { practice.getName()}), e);
                        }
                        // Sauvegarde des résultats de la pratique
                        QualityResultDAOImpl.getInstance().save(pSession, practiceResult);
                    }
                } catch (HibernateException e) {
                    // Cette erreur est liée à la gestion du flushmode, dans ce cas on se contente de remonter
                    // une exception JRAF
                    throw new JrafDaoException(e);
                } finally {
                    // Remise du mode de flush au mode précedent
                    hibernateSession.setFlushMode(oldFlushMode);
                }
            }
        }
    }
    /**
     * Calcul de la note sur un projet
     * Le calcul de la note sur un projet se fait directement au moyen de la formule associée
     * @param pSession session
     * @param pProject projet
     * @param pAudit audit
     * @param pInterpreter interprète
     * @param pPractice pratique
     * @param pPracticeResult résultat
     * @throws FormulaException si erreur
     * @throws JrafDaoException si erreur
     */
    private static void computeProjectPracticeMark(ISession pSession, ProjectBO pProject, AuditBO pAudit, FormulaInterpreter pInterpreter, PracticeRuleBO pPractice, PracticeResultBO pPracticeResult)
        throws JrafDaoException, FormulaException {
        // Récupération des types de mesure traités par la formule
        AbstractFormulaBO formula = pPractice.getFormula();
        String[] measureKinds = new String[formula.getMeasureKinds().size()];
        formula.getMeasureKinds().toArray(measureKinds);
        MeasureBO measures[] = new MeasureBO[formula.getMeasureKinds().size()];
        Long projectId = new Long(pProject.getId());
        // Récupération de chaque mesure
        boolean measuresNotNull = true;
        Long auditId = new Long(pAudit.getId());
        for (int i = 0; i < measureKinds.length; i++) {
            MeasureBO measure =
                (MeasureBO) MeasureDAOImpl.getInstance().load(pSession, projectId, auditId
                                                                , Mapping.getMeasureClass(measureKinds[i] + "." 
                                                                                            + formula.getComponentLevel()));
            measures[i] = measure;
            // Une mesure peut être absente, ce qui serait lié à un problème de création des mesures
            // dans la phase qui précède le calcul des notes
            if (measure == null) {
                measuresNotNull = false;
                // On indique le type de mesure qui n'est pas trouvé ainsi que le composant concerné
                LOG.warn(RuleMessages.getString("missingmeasure", new Object[] { measureKinds[i], projectId, auditId }));
            }
        }
        // On calcul la note seulement si toutes les mesures requises sont bien présentes
        if (measuresNotNull) {
            // Calcul de la note
            // L'exception dans le calcul est remontée telle quelle
            Number value = pInterpreter.evaluate(pPractice.getFormula(), measures);
            // On place une note -1 si la formule n'a pu être calculée
            if (value != null) {
                pPracticeResult.setMeanMark(value.floatValue());
            }
        }
    }

    /**
     * Séparation des pratiques par type de composant
     * @param pPractices pratiques
     * @return map de la forme (string,collection) où string donne le type de composant
     * et collection la liste des pratiques sur ce type de composant (string peut être "")
     */
    private static Map splitPracticesByKind(Map pPractices) {
        Hashtable result = new Hashtable();
        Iterator practices = pPractices.keySet().iterator();
        while (practices.hasNext()) {
            PracticeRuleBO practice = (PracticeRuleBO) practices.next();
            // Les pratiques sans formule sont regroupées
            // avec un kind vide
            String kind = "";
            if (practice.getFormula() != null) {
                kind = practice.getFormula().getComponentLevel();
            }
            Collection practicesKind = (Collection) result.get(kind);
            if (practicesKind == null) {
                practicesKind = new ArrayList();
                result.put(kind, practicesKind);
            }
            practicesKind.add(practice);
        }
        return result;
    }

    /**
     * Calcul de la note d'une pratique
     * @param pPractice pratique
     * @param pPracticeResult résultats de la pratique
     */
    private static void computePracticeMark(PracticeRuleBO pPractice, PracticeResultBO pPracticeResult) {
        /*
        * Formule : 
        *             0*nb0*pond0 + 1*nb1*pond1 + 2*nb2*pond2 + 3*nb3*pond3
        * meanMark = -------------------------------------------------------
        *                 nb0*pond0 + nb1*pond1 + nb2*pond2 + nb3*pond3
        */
        float num = 0;
        float denum = 0;
        Iterator coefs = pPractice.getCoefficients().iterator();
        int i = 0;
        while (coefs.hasNext()) {
            Float coef = (Float) coefs.next();
            num += i * pPracticeResult.getRepartition()[i].intValue() * coef.floatValue();
            denum += pPracticeResult.getRepartition()[i].intValue() * coef.floatValue();
            i++;
        }
        if (denum != 0) {
            pPracticeResult.setMeanMark(num / denum);
        } else {
            LOG.debug(RuleMessages.getString("nocomputation", new Object[] { pPractice.getName()}));
        }
    }
    /**
     * Calcul des pratiques sur les composants
     * @param pSession session
     * @param pAudit audit
     * @param pInterpreter interpréteur
     * @param pPractice pratique
     * @param pPracticeResult résultat
     * @param pChildren composants fils
     * @throws FormulaException si erreur
     * @throws JrafDaoException si erreur 
     */
    private static void computePracticeResults(ISession pSession, AuditBO pAudit, FormulaInterpreter pInterpreter, PracticeRuleBO pPractice, PracticeResultBO pPracticeResult, Collection pChildren)
        throws FormulaException, JrafDaoException {
        // Reinitialisation des répartitions
        pPracticeResult.resetRepartitions();
        // Récupération des types de mesure traités par la formule
        AbstractFormulaBO formula = pPractice.getFormula();
        String[] measureKinds = new String[formula.getMeasureKinds().size()];
        formula.getMeasureKinds().toArray(measureKinds);
        MeasureBO measures[] = new MeasureBO[formula.getMeasureKinds().size()];
        Long componentId = null;
        // Traitement de la note pour chaque composant
        Iterator it = pChildren.iterator();
        while (it.hasNext()) {
            AbstractComponentBO child = (AbstractComponentBO) it.next();
            componentId = new Long(child.getId());
            // Récupération de chaque mesure
            boolean measuresNotNull = true;
            for (int i = 0; i < measureKinds.length; i++) {
                Long auditId = new Long(pAudit.getId());
                MeasureBO measure =
                    (MeasureBO) MeasureDAOImpl.getInstance().load(
                        pSession,
                        componentId,
                        auditId,
                        Mapping.getMeasureClass(measureKinds[i] + "." + formula.getComponentLevel()));
                measures[i] = measure;
                // Une mesure peut être absente, ce qui serait lié à un problème de création des mesures
                // dans la phase qui précède le calcul des notes
                if (measure == null) {
                    measuresNotNull = false;
                    // On indique le type de mesure qui n'est pas trouvé ainsi que le composant concerné
                    LOG.warn(RuleMessages.getString("missingmeasure", new Object[] { measureKinds[i], componentId, auditId }));
                }
            }
            // On calcul la note seulement si toutes les mesures requises sont bien présentes
            if (measuresNotNull) {
                computeMark(pSession, pInterpreter, measures, pPractice, pPracticeResult, child);
            }
        }
    }

    /**
     * Calcul d'une note sur un composant
     * @param pSession session
     * @param pInterpreter interprète
     * @param pMeasures mesures
     * @param pPractice pratique
     * @param pPracticeResult résultat de pratique
     * @param pChild composant
     * @throws FormulaException si erreur
     * @throws JrafDaoException si erreur
     */
    private static void computeMark(ISession pSession, FormulaInterpreter pInterpreter, MeasureBO[] pMeasures, PracticeRuleBO pPractice, PracticeResultBO pPracticeResult, AbstractComponentBO pChild)
        throws FormulaException, JrafDaoException {
        MarkBO mark = new MarkBO();
        mark.setComponent(pChild);
        mark.setPractice(pPracticeResult);
        // Calcul de la note
        // L'exception dans le calcul est remontée telle quelle
        Number value = pInterpreter.evaluate(pPractice.getFormula(), pMeasures);
        // On place une note -1 si la formule n'a pu être calculée
        if (value == null) {
            mark.setValue(-1);
        } else {
            mark.setValue(value.intValue());
        }
        // Mise à jour de la répartition des notes
        if (mark.getValue() >= 0) {
            pPracticeResult.incrementRepartition(mark.getValue());
        }
        // Enregistrement de la note
        MarkDAOImpl.getInstance().save(pSession, mark);
    }

}
