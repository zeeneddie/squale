package com.airfrance.squalecommon.daolayer.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;

/**
 * @author M400843
 *
 */
public class QualityResultDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static QualityResultDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static {
        instance = new QualityResultDAOImpl();
    }

    /**
     * Constructeur prive
     */
    private QualityResultDAOImpl() {
        initialize(QualityResultBO.class);
        LOG = LogFactory.getLog(MarkDAOImpl.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static QualityResultDAOImpl getInstance() {
        return instance;
    }

    /**
     * Supprime tous les résultats qualités liés à un projet
     * @param pSession la session
     * @param pProject le projet
     * @throws JrafDaoException si une erreur à lieu
     */
    public void removeWhereProject(ISession pSession, ProjectBO pProject) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = " + pProject.getId();

        removeWhere(pSession, whereClause);
        LOG.debug(DAOMessages.getString("dao.exit_method"));
    }

    /**
     * Permet de récupérer les notes en fonction d'une liste de composants
     * @param pSession session Hibernate
     * @param pProjectIDs liste des identifiants des composants
     * @param pAuditID identifiant de l'audit
     * @param pRuleId id de la règle qualité
     * @return liste des valeurs ordonnés par raopport a la liste des composants
     * @throws JrafDaoException exception DAO
     */
    public List findWhere(ISession pSession, List pProjectIDs, Long pAuditID, Long pRuleId) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        List marks = new ArrayList();
        Iterator it = pProjectIDs.iterator();
        while (it.hasNext()) {
            Long componentId = (Long) it.next();
            marks.add(load(pSession, componentId, pAuditID, pRuleId));
        }
        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return marks;
    }

    /**
     * Permet de récupérer les résultats de qualités en fonction d'une liste d'audits
     * @param pSession session Hibernate
     * @param pProjectID identifiant du composant
     * @param pAuditIDs liste des identifiants de l'audit
     * @param pRuleId id de la règle qualité
     * @return liste des valeurs ordonnés par raopport a la liste des composants
     * @throws JrafDaoException exception DAO
     */
    public List findWhere(ISession pSession, Long pProjectID, List pAuditIDs, Long pRuleId) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        List marks = new ArrayList();
        Iterator it = pAuditIDs.iterator();
        while (it.hasNext()) {
            Long auditId = (Long) it.next();
            marks.add(load(pSession, pProjectID, auditId, pRuleId));
        }
        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return marks;
    }

    /**
     * Permet de récupérer un résultat de qualité en fonction d'un d'audit, d'un composant et d'un TRE
     * @param pSession session Hibernate
     * @param pProjectID identifiant du composant
     * @param pAuditID identifiant de l'audit
     * @param pRuleId id du TRE
     * @return un résultat de qualité
     * @throws JrafDaoException exception DAO
     */
    public QualityResultBO load(ISession pSession, Long pProjectID, Long pAuditID, Long pRuleId) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        String whereClause = "where ";
        whereClause += getAlias() + ".rule.id = " + pRuleId;
        whereClause += " and ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";

        QualityResultBO result = null;
        Collection col = findWhere(pSession, whereClause);
        if (col.size() >= 1) {
            result = (QualityResultBO) col.iterator().next();
            if (col.size() > 1) {
                String tab[] = { pAuditID.toString(), pProjectID.toString(), pRuleId.toString()};
                LOG.warn(DAOMessages.getString("qualityresult.many.audit_project_tre", tab));
            }
        }

        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return result;
    }

    /**
     * Obtention des résultats sur les facteurs
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @return résultats associés
     * @throws JrafDaoException si erreur
     */
    public Collection findWhere(ISession pSession, Long pProjectID, Long pAuditID) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".rule.class='FactorRule'";
        whereClause += " order by " + getAlias() + ".rule.name";

        QualityResultBO result = null;
        Collection col = findWhere(pSession, whereClause);

        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return col;
    }

    /**
     * Obtention des résultats sur les pratiques
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @return résultats associés
     * @throws JrafDaoException si erreur
     */
    public Collection findPracticesWhere(ISession pSession, Long pProjectID, Long pAuditID) throws JrafDaoException {
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".rule.class='PracticeRule'";
        whereClause += " order by " + getAlias() + ".rule.name";

        QualityResultBO result = null;
        Collection col = findWhere(pSession, whereClause);

        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return col;
    }

    /**
     * Obtention des résultats d'un projet
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @param pRuleIDs ids des règles qualité
     * @return résultats du projet triés par nom de règle
     * @throws JrafDaoException si erreur
     */

    public Collection findWhere(ISession pSession, Long pProjectID, Long pAuditID, Collection pRuleIDs) throws JrafDaoException {
        // Initialisation
        Collection col = new ArrayList();
        // Protection du code car in () n'est pas valable
        if (pRuleIDs.size() > 0) {
            StringBuffer ruleIds = new StringBuffer();
            Iterator rules = pRuleIDs.iterator();
            while (rules.hasNext()) {
                if (ruleIds.length() > 0) {
                    ruleIds.append(',');
                }
                ruleIds.append(((Long) rules.next()).longValue());
            }
            String whereClause = "where ";
            whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
            whereClause += " and ";
            whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
            whereClause += " and ";
            whereClause += getAlias() + ".rule.id in (" + ruleIds.toString() + ") order by " + getAlias() + ".rule.name";
            // Requête dans la base
            col = findWhere(pSession, whereClause);
            // On complète avec des résultats nuls dans ce cas
            if (col.size() != pRuleIDs.size()) {
                col = adaptResults(pRuleIDs, col);
            }
        }
        return col;
    }

    /**
     * Obtention des résultats d'un projet
     * @param pSession session
     * @param pAcceptanceLevel le niveau (accepté, accepté avec réserves, refusés)
     * @param pSiteId l'id du site
     * @return résultats du projet triés par nom de règle
     * @throws JrafDaoException si erreur
     */
    public int countFactorsByAcceptanceLevelAndSite(ISession pSession, String pAcceptanceLevel, long pSiteId) throws JrafDaoException {
        int result = 0;
        String whereClause = "where " + getAlias() + ".class='FactorResult'";
        if (pAcceptanceLevel.equals(QualityResultBO.ACCEPTED)) {
            whereClause += " AND " + getAlias() + ".meanMark <= 3 AND " + getAlias() + ".meanMark >= 2 ";
        } else {
            if (pAcceptanceLevel.equals(QualityResultBO.RESERVED)) {
                whereClause += " AND " + getAlias() + ".meanMark < 2 AND " + getAlias() + ".meanMark >= 1 ";
            } else {
                if (pAcceptanceLevel.equals(QualityResultBO.REFUSED)) {
                    whereClause += " AND " + getAlias() + ".meanMark < 1 AND " + getAlias() + ".meanMark > 0 ";
                }
            }
        }
        if(pSiteId != 0) {
            whereClause += " AND " + getAlias()+ ".project.parent.serveurBO.serveurId='" + pSiteId + "'";
        }
        // On ne prend que les applications non supprimées et les audits réussis
        // ou partiels
        whereClause += " AND " + getAlias()+ ".project.parent.status!=" + ApplicationBO.DELETED;
        //whereClause += " AND " + getAlias()+ ".audit.id =(" + selectLastAudit + ")";
        whereClause += " AND (" + getAlias()+ ".audit.status=" + AuditBO.TERMINATED;
        whereClause += " or " + getAlias() + ".audit.status=" + AuditBO.PARTIAL + ")";
        // On tri pour récupérer les résultats selon le dernier audit réussi ou partiel
        // si il n'y a aucun audit réussi
        whereClause += " order by " + getAlias() + ".project.id, ";
        whereClause += getAlias() + ".audit.status asc, nvl(";
        whereClause += getAlias() + ".audit.historicalDate, ";
        whereClause += getAlias() + ".audit.date) desc";
        LOG.debug("countFactorsByAcceptanceLevelAndSite:" + whereClause);
        // Requête dans la base
        List results = findWhere(pSession, whereClause);
        long lastProjectId = -1;
        long lastAuditId = -1;
        for(int i = 0; i<results.size(); i++) {
            QualityResultBO cur = (QualityResultBO) results.get(i);
            if(cur.getProject().getId() != lastProjectId) {
                result++;
                lastAuditId = cur.getAudit().getId();
            } else if(cur.getAudit().getId() == lastAuditId) {
                result++;
            }
            lastProjectId = cur.getProject().getId();
        }
        return result;
    }

    /**
     * Adaptation des résultats
     * La liste des résultats peut être différente de celle des règles, dans ce
     * cas on crée des résultats vierges pour faire en sorte que la liste des résultats
     * concorde avec celle des règles
     * @param pRuleIDs liste des règles
     * @param pResults liste des résultats
     * @return liste des résultats adaptée
     */
    private Collection adaptResults(Collection pRuleIDs, Collection pResults) {
        Collection result = new ArrayList();
        Iterator rules = pRuleIDs.iterator();
        Iterator results = pResults.iterator();
        QualityResultBO currentResult = null;
        // Initialisation du résultat courant
        if (results.hasNext()) {
            currentResult = (QualityResultBO) results.next();
        } // Parcours de toutes les règles
        while (rules.hasNext()) {
            Long rule = (Long) rules.next();
            // Si il n'existe pas de résultat correspondant à la règle, on
            // en crée un qui soit vierge avec une note de -1
            if ((currentResult == null) || (rule.longValue() != currentResult.getRule().getId())) {
                QualityResultBO res = new FactorResultBO(-1.0f, null, null, null);
                result.add(res);
            } else { // Le résultat est en concordance avec la règle
                result.add(currentResult); // On passe au résultat suivant
                if (results.hasNext()) {
                    currentResult = (QualityResultBO) results.next();
                } else {
                    currentResult = null;
                }
            }
        }
        return result;
    }
}
