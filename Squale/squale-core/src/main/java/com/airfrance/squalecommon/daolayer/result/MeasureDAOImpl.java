package com.airfrance.squalecommon.daolayer.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.provider.persistence.hibernate.SessionImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.daolayer.DAOUtils;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * @author M400843
 */
public class MeasureDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static MeasureDAOImpl instance = null;

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog(MeasureDAOImpl.class);

    /** initialisation du singleton */
    static {
        instance = new MeasureDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private MeasureDAOImpl() {
        initialize(MeasureBO.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static MeasureDAOImpl getInstance() {
        return instance;
    }

    /**
     * Sauve une collection de mesures.<br />
     * L'id de l'audit lié à la mesure doit être valué.
     * 
     * @param pSession la session
     * @param pMeasures les mesures (résultats McCabe, ClearCase, ...)
     * @throws JrafDaoException si une erreur à lieu
     */
    public void saveAll(ISession pSession, Collection pMeasures) throws JrafDaoException {
        Iterator it = pMeasures.iterator();

        while (it.hasNext()) {
            Object element = it.next();
            if (element instanceof MeasureBO) {
                MeasureBO measure = (MeasureBO) element;
                // on affecte l'audit à tout les parents 
                AbstractComponentBO component = measure.getComponent();
                while (component != null) {
                    if (!component.containsAuditById(measure.getAudit().getId())) {
                        component.addAudit(measure.getAudit());
                    }
                    component = component.getParent();
                }
                AbstractComponentDAOImpl.getInstance().save(pSession, measure.getComponent());
                // Appel de la méthode héritée car la méthode
                // save est surchargée pour émettre un warning
                super.save(pSession, measure);
            } else {
                String tab[] = { element.toString()};
                LOG.error(DAOMessages.getString("measure.bad_object", tab));
            }
        }
    }

    /**
     * Cré la mesure en base (aprés vérification du lien entre composant et audit)
     * @param pSession la session
     * @param pMeasure la mesure
     * @throws JrafDaoException si une erreur à lieu ou si la mesure existe déjà
     */
    public void create(ISession pSession, MeasureBO pMeasure) throws JrafDaoException {
        // Obtention des iids du composant et de l'audit
        Long projectId = new Long(pMeasure.getComponent().getId());
        Long auditId = new Long(pMeasure.getAudit().getId());
        // Si la mesure existe déjà pour le composant et l'audit donné : erreur
        if (null != load(pSession, projectId, auditId, pMeasure.getClass())) {
            // Une seule mesure d'un type donné est autorisée pour un couple composant, audit
            String[] tab = { auditId.toString(), projectId.toString(), pMeasure.getClass().getName()};
            throw new JrafDaoException(DAOMessages.getString("measure.exception.many.audit_component_tr", tab));
        } else {
            // si le composant concerné par la mesure ne contient pas l'audit rattaché à la mesure:
            if (!pMeasure.getComponent().containsAuditById(pMeasure.getAudit().getId())) {
                pMeasure.getComponent().addAudit(pMeasure.getAudit());
                // on affecte l'audit à tout les parents 
                AbstractComponentBO component = pMeasure.getComponent().getParent();
                while (component != null) {
                    if (!component.getAudits().contains(pMeasure.getAudit())) {
                        component.addAudit(pMeasure.getAudit());
                    }
                    component = component.getParent();
                }
                // sauvegarde du composant (et de ses parents)
                AbstractComponentDAOImpl.getInstance().save(pSession, pMeasure.getComponent());
            }
            // sauvegarde de la mesure
            super.create(pSession, pMeasure);
        }
    }

    /**
     * Efface toutes les mesures liés à un composant (recursivement ou non)
     * @param pSession la session
     * @param pComponent le composant
     * @param pAudit l'audit, si <code>null</code> la suppression est effectuée pour tout les audits. 
     * @param pRecursive <code>true</code> pour supprimer les mesures de tout les composants enfants
     * @throws JrafDaoException si une erreur à lieu
     */
    public void removeWhereComponent(final ISession pSession, final AbstractComponentBO pComponent, final AuditBO pAudit, final boolean pRecursive) throws JrafDaoException {
        // Création de la clause where
        String whereClause = "where ";

        if (null != pAudit) {
            // si un audit est spécifié on l'ajoute dans la clause where
            whereClause += getAlias() + ".audit.id = " + pAudit.getId();
            whereClause += " AND ";
        }
        whereClause += getAlias() + ".component.id = ";
        // la clause ne contient pas l'id du composant qui est ajouté au moment du remove
        removeWhere(pSession, whereClause + pComponent.getId());

        // si la suppression doit etre récursive est que l'on a un composant 
        //qui peut avoir des fils (AbstractComplexComponentBO) 
        if (pRecursive && (pComponent instanceof AbstractComplexComponentBO)) {
            Iterator itChildren = ((AbstractComplexComponentBO) pComponent).getChildren().iterator();
            while (itChildren.hasNext()) {
                // pour chaque enfant on rappelle la méthode
                AbstractComponentBO child = (AbstractComponentBO) itChildren.next();
                removeWhereComponent(pSession, child, pAudit, pRecursive);
            }
        }
    }

    /**
     * Permet de récupérer les notes en fonction d'une liste de noms de TREs
     * 
     * @param pSession session Hibernate
     * @param pComponentID identifiant du composant
     * @param pAuditID identifiant de l'audit
     * @param pTrClasses classes des TR
     * @return collection des valeurs ordonnés par rapport a la liste des TREs
     * @throws JrafDaoException exception DAO
     */
    public Collection findWhere(ISession pSession, Long pComponentID, Long pAuditID, Collection pTrClasses) throws JrafDaoException {
        List marks = new ArrayList();
        Iterator it = pTrClasses.iterator();
        while (it.hasNext()) {
            Class trClass = (Class) it.next();
            marks.add(load(pSession, pComponentID, pAuditID, trClass));
        }
        return marks;
    }

    /**
     * Retourne la liste des TOP (valeur les plus elevé)
     * @param pSession Session Jraf
     * @param pProjectId Id du projet
     * @param pComponentClass Class du composant à récuoperer (MethodBO ou ClassBO)
     * @param pAuditId Id de l'audit courant
     * @param pTreKey Tre à recuperer / trié
     * @param pMax nombre maxi de valeur remonté
     * @return Liste de ComponentBO / Integer correspondant aux tops
     * @throws JrafDaoException si pb de BD
     */
    public Collection findTop(ISession pSession, long pProjectId, Class pComponentClass, long pAuditId, String pTreKey, Integer pMax) throws JrafDaoException {
        SessionImpl sessionHibernate = (SessionImpl) pSession;
        List results = null;
        try {
            // remonte les tops "pMax" en une seule requete pour des raisons de perf 
            Query q =
                sessionHibernate
                    .getSession()
                    .createQuery(
                        "select c,metric.value from "
                        + pComponentClass.getName()
                        + " c,"
                        + "NumberMetricBO metric"
                        + " where metric.measure.audit.id="
                        + pAuditId
                        + " and metric.measure.component.id=c.id"
                        + " and metric.measure.class="
                        + Mapping.getMetricClass(pTreKey).getName()
                        + " and metric.name = '"
                        + pTreKey.substring(pTreKey.lastIndexOf('.')+1)
                        + "'"
                        + " and "
                        + pAuditId
                        + " in elements(c.audits)"
                        +" and c.project.id=" + pProjectId + " order by metric.value desc").setMaxResults(pMax.intValue());
            results = q.list();
        } catch (HibernateException e) {
            throw new JrafDaoException(e);
        }

        return results;
    }

    /**
     * Retourne les valeur distinct de mesure de type Integer données par leur TRE
     * @param pSession Session Jraf
     * @param pProjectId Id du projet
     * @param pAuditId Id de l'audit courant
     * @param pTreKey Liste des Tre à recuperer / trié
     * @return Liste de masures distinctes (Collection de Object[])
     * @throws JrafDaoException si pb de BD
     */
    public Collection findDistinct(ISession pSession, long pProjectId, long pAuditId, String[] pTreKey) throws JrafDaoException {
        SessionImpl sessionHibernate = (SessionImpl) pSession;
        List results = null;
        String from = "";
        String distinct = "";
        String where = "";
        // Creation de la requete
        for (int i = 0; i < pTreKey.length; i++) {
            //selecte sur n metric
            // => construction des selects, from, et where de la query  
            if (i > 0) {
                distinct += ",";
            }
            distinct += "metric" + i + ".value";
            from += ",IntegerMetricBO metric" + i;
            where += " and metric"
                + i
                + ".measure.audit.id="
                + pAuditId
                + " and metric"
                + i
                + ".measure.component.id=c.id"
                + " and metric"
                + i
                + ".measure.class="
                + Mapping.getMetricClass(pTreKey[i]).getName()
                + " and metric"
                + i
                + ".name = '"
                + pTreKey[i].substring(pTreKey[i].lastIndexOf('.')+1)
                + "'";
        }
        // execution de la requete
        try {
            // remonte les valeurs distinctes en une seule requete pour des raisons de perf 
            Query q =
                sessionHibernate.getSession().createQuery("select " + distinct + ", count(*), min(c.id) from " 
                // On ne recherche pas le type du composant afin d'optimiser la requête car la recherche
                // est déjà faite par le type de la métrique.
                + AbstractComponentBO.class.getName() + " c" + from + " where (c.project.id =" + pProjectId + " or c.id="+ pProjectId + ")"
                + where+" group by " +distinct);
            results = q.list();
        } catch (HibernateException e) {
            throw new JrafDaoException(e);
        }

        return results;
    }

    /**
     * Permet de récupérer la note en fonction d'un audit
     * @param pSession session Hiebrnate
     * @param pComponentID identifiant du composant
     * @param pAuditID identifiant de l'audit
     * @param pTreClass classe du TRE
     * @return une mesure ou <code>null</code>
     * @throws JrafDaoException exception DAO
     */
    public MeasureBO load(ISession pSession, Long pComponentID, Long pAuditID, Class pTreClass) throws JrafDaoException {
        String whereClause = "where ";
        whereClause += getAlias() + ".component.id = '" + pComponentID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";

        MeasureBO measure = null;
        LOG.trace("whereClause : " + whereClause);

        int index = pTreClass.getName().lastIndexOf(".");
        String className = pTreClass.getName().substring(index + 1);

        String requete = "from " + className + " as " + getAlias() + " " + whereClause;

        Collection col = find(pSession, requete);

        if (col.size() >= 1) {
            measure = (MeasureBO) col.iterator().next();
            if (col.size() > 1) {
                String tab[] = { pAuditID.toString(), pComponentID.toString(), pTreClass.getName()};
                LOG.warn(DAOMessages.getString("measure.many.audit_component_tr", tab));
            }
        }
        return measure;
    }

    /**
     * Récupération des mesures sur un audit
     * @param pSession session Hiebrnate
     * @param pComponentID identifiant du composant
     * @param pAuditID identifiant de l'audit
     * @return ensemble des mesures à l'exclusion des
     * transgressions
     * @throws JrafDaoException exception DAO
     */
    public Collection findWhere(ISession pSession, Long pComponentID, Long pAuditID) throws JrafDaoException {
        String whereClause = "where ";
        whereClause += getAlias() + ".component.id = '" + pComponentID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
        // Un nom de subclass correspondant à une transgression doit finir par 'Transgression'
        // TODO : Il faudra sûrement considérer les transgressions comme des mesures à part...
        whereClause += " and " + getAlias() + ".class not like '%Transgression'";
        MeasureBO measure = null;
        LOG.trace("whereClause : " + whereClause);

        Collection col = findWhere(pSession, whereClause);

        return col;
    }
    /**
     * @see com.airfrance.jraf.spi.persistence.IPersistenceDAO#create(com.airfrance.jraf.spi.persistence.ISession, java.lang.Object)
     * @deprecated utiliser create (cette methode ne fait aucun test d'intégrité)
     */
    public void create(ISession pSession, Object pObj) throws JrafDaoException {
        String message = DAOMessages.getString("dao.forbiden_method");
        LOG.warn(message);
        throw new JrafDaoException(message);
    }

    /**
     * @see com.airfrance.jraf.spi.persistence.IPersistenceDAO#save(com.airfrance.jraf.spi.persistence.ISession, java.lang.Object)
     * @deprecated utiliser saveAll (cette methode ne fait aucun test d'intégrité)
     */
    public void save(ISession pSession, Object pObj) throws JrafDaoException {
        String message = DAOMessages.getString("dao.forbiden_method");
        LOG.warn(message);
        throw new JrafDaoException(message);
    }

    /**
     * @param pSession la session
     * @param pApplicationId l'id de l'application dont on veut le ROI.
     * Si il est à "-1" alors on veut le résultat pour toutes les applications
     * @param pStart la date du premier audit à récupèrer
     * @return la liste des mesures de ROI pour une application (ou toutes les applications)
     * réalisées à partir de <code>pStart</code>
     * @throws JrafDaoException si erreur
     */
    public Collection getRoiMeasures(ISession pSession, long pApplicationId, Date pStart) throws JrafDaoException {
        String date = DAOUtils.makeQueryDate(pStart);
        String whereClause = " where ";
        whereClause += getAlias() + ".class='Roi'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.date >= " + date;
        whereClause += " and ";
        whereClause += "(" + getAlias() + ".audit.historicalDate is null";
        whereClause += " or ";
        whereClause += getAlias() + ".audit.historicalDate >= " + date + ")";
        if (-1 != pApplicationId) {
            // Si on veut les résultats que pour une application
            whereClause += " and ";
            whereClause += getAlias() + ".component.id=" + pApplicationId;
        }
        whereClause += " order by " + getAlias() + " .audit.date asc";
        LOG.info("Mesures ROI : " + whereClause);
        return findWhere(pSession, whereClause);
    }
}
