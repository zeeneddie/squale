package com.airfrance.squalecommon.daolayer.result.rulechecking;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionItemBO;

/**
 * DAO pour les items des transgressions.
 */
public class RuleCheckingTransgressionItemDAOImpl extends AbstractDAOImpl {

    /** Le nombre limite d'items à retourner */
    private static final int LIMIT = 100;

    /** L'index de départ pour le retour d'items */
    private static final int START = 0;

    /**
     * Instance singleton
     */
    private static RuleCheckingTransgressionItemDAOImpl instance = null;

    /** initialisation du singleton */
    static {
        instance = new RuleCheckingTransgressionItemDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private RuleCheckingTransgressionItemDAOImpl() {
        initialize(RuleCheckingTransgressionItemBO.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static RuleCheckingTransgressionItemDAOImpl getInstance() {
        return instance;
    }

    /**
     * @param pSession la session
     * @param pMeasureId l'id de la mesure
     * @param pRuleId l'id de la règle
     * @return les 100 premiers items classés par nom du composant impliqué ayant la mesure 
     * d'id <code>pMeasureId</code> et la règle d'id <code>pRuleId</code>
     * @throws JrafDaoException si erreur
     */
    public Collection findWhereMeasureAndRule(ISession pSession, Long pMeasureId, Long pRuleId) throws JrafDaoException {
        String whereClause = "where ";
        whereClause += getAlias() + ".rule.id=" + pRuleId;
        whereClause += " and ";
        whereClause += getAlias() + ".id in ";
        whereClause += "(select rule.details.id from RuleCheckingTransgressionBO as rule where ";
        whereClause += "rule.id=" + pMeasureId + ")";
        Collection items = (Collection) findWhereScrollable(pSession, whereClause, LIMIT, START, false);
        return items;
    }

    /**
     * @param pSession la session
     * @param pProjectId l'id du projet
     * @param pAuditId l'id de l'audit
     * @param pClasses les mesures à chercher
     * @param pSeverity la sévérité de la règle associée
     * @param pCategory la catégorie de la règle
     * @param pLimit le nombre d'items à remonter
     * @return les transgressions correspondantes
     * @throws JrafDaoException si erreur
     */
    public Collection findWhereMeasureClass(ISession pSession, String pProjectId, String pAuditId, String[] pClasses, String pSeverity, String pCategory, Integer pLimit) throws JrafDaoException {
        Collection items = new ArrayList(0);
        // si il n'y a pas de mesures, on retourne une liste vide car in () ne fonctionne pas
        if (pClasses.length > 0) {
            String whereClause = getWhereMeasureClause(pProjectId, pAuditId, pClasses);
            whereClause += " and ";
            whereClause += getAlias() + ".rule.severity='" + pSeverity + "'";
            whereClause += " and ";
            whereClause += getAlias() + ".rule.category='" + pCategory + "'";
            items = (Collection) findWhereScrollable(pSession, whereClause, pLimit.intValue(), 0, false);
        }
        return items;
    }

    /**
     * @param pSession la session
     * @param pProjectId l'id du projet
     * @param pAuditId l'id de l'audit
     * @param pClasses les mesures à chercher
     * @return le nombre de transgressions correspondantes
     * @throws JrafDaoException si erreur
     */
    public int countWhereMeasures(ISession pSession, String pProjectId, String pAuditId, String[] pClasses) throws JrafDaoException {
        int result = 0;
        // Si pClasses est vide, on retourne 0
        if (pClasses.length > 0) {
            String whereClause = getWhereMeasureClause(pProjectId, pAuditId, pClasses);
            result = countWhere(pSession, whereClause).intValue();
        }
        return result;
    }

    /**
     * @param pProjectId l'id du projet
     * @param pAuditId l'id de l'audit
     * @param pClasses les mesures à chercher
     * @return la clause where pour récupérer les transgressions liées aux critères passés
     * en paramètre
     */
    private String getWhereMeasureClause(String pProjectId, String pAuditId, String[] pClasses) {
        String whereClause = "where ";
        whereClause += getAlias() + ".id in ";
        whereClause += "(select rule.details.id from RuleCheckingTransgressionBO as rule where ";
        whereClause += " rule.class in (" + pClasses[0];
        for (int i = 1; i < pClasses.length; i++) {
            whereClause += ", " + pClasses[i];
        }
        whereClause += ")";
        whereClause += " and rule.component.id=" + pProjectId;
        whereClause += " and rule.audit.id=" + pAuditId + ")";
        return whereClause;
    }
}
