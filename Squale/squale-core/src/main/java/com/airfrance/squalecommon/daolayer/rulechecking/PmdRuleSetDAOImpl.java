package com.airfrance.squalecommon.daolayer.rulechecking;

import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.pmd.PmdRuleSetBO;

/**
 * DAO pour le ruleset PMD
 */
public class PmdRuleSetDAOImpl extends AbstractDAOImpl {

    /**
     * Instance singleton
     */
    private static PmdRuleSetDAOImpl instance = null;

    /**
     *  initialisation du singleton 
     *  
     **/
    static {
        instance = new PmdRuleSetDAOImpl();
    }

    /**
     * Constructeur privé
     * @throws JrafDaoException
     */
    private PmdRuleSetDAOImpl() {
        initialize(PmdRuleSetBO.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static PmdRuleSetDAOImpl getInstance() {
        return instance;
    }

    /**
     * Création d'une version de configuration PMD 
     * 
     * @param pSession session
     * @param pRuleSet jeu de règles
     * @return PmdRuleSetBO l'objet créé dans la base ou null si l'unicité
     * n'est pas respectée
     * @throws JrafDaoException exception JRAF
     */

    public PmdRuleSetBO createPmdRuleSet(ISession pSession, PmdRuleSetBO pRuleSet) throws JrafDaoException {
        create(pSession, pRuleSet);
        return pRuleSet;
    }

    /**
     * Récupération d'un ruleset à partir du nom et de la version
     * @param pSession session
     * @param pName nom
     * @param pLanguage version
     * @return ruleset ou null si non trouvé
     * @throws JrafDaoException si erreur
     */
    public PmdRuleSetBO findRuleSet(ISession pSession, String pName, String pLanguage) throws JrafDaoException {
        PmdRuleSetBO result = null;
        StringBuffer whereClause = new StringBuffer();
        whereClause.append("where ");
        PmdRuleSetBO version = null;
        whereClause.append(getAlias()).append(".name = '").append(pName).append("'");
        whereClause.append(" and ").append(getAlias()).append(".language= '").append(pLanguage).append("'");
        whereClause.append(" order by ").append(getAlias()).append(".dateOfUpdate DESC");
        Collection col = findWhere(pSession, whereClause.toString());
        if (col.size() > 0) {
            // On renvoie le premier, normalement la taille de la collection
            // est de 1
            result = (PmdRuleSetBO) col.iterator().next();
        }
        return result;
    }

    /**
     * Destruction de rulesets
     * @param pSession session
     * @param pRuleSetsId ids des rulesets
     * @throws JrafDaoException si erreur
     */
    public void removePmdRuleSets(ISession pSession, Collection pRuleSetsId) throws JrafDaoException {
        StringBuffer whereClause = new StringBuffer("where ");
        whereClause.append(getAlias());
        whereClause.append(".id in(");
        Iterator ruleSetsIdIt = pRuleSetsId.iterator();
        boolean comma = false;
        // Parcours des ids de ruleset pour construire la liste dans la requête
        while (ruleSetsIdIt.hasNext()) {
            if (comma) {
                whereClause.append(", ");
            } else {
                comma = true;
            }
            whereClause.append(ruleSetsIdIt.next());
        }
        whereClause.append(")");
        Iterator ruleSetsIt = findWhere(pSession, whereClause.toString()).iterator();
        // Suppression de chaque jeu de règles
        while (ruleSetsIt.hasNext()) {
            remove(pSession, ruleSetsIt.next());
        }
    }
}
