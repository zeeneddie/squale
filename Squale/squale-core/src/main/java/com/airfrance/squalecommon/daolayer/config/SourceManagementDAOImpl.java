package com.airfrance.squalecommon.daolayer.config;

import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;

/**
 * DAO pour SourceManagementBO
 */
public class SourceManagementDAOImpl extends AbstractDAOImpl {

    /**
     * Instance singleton
     */
    private static SourceManagementDAOImpl instance = null;

    /** initialisation du singleton */
    static {
        instance = new SourceManagementDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private SourceManagementDAOImpl() {
        initialize(SourceManagementBO.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static SourceManagementDAOImpl getInstance() {
        return instance;
    }

    /**
     * Supprime tous les récupérateurs de sources
     * ainsi que toutes les tâches associées
     * 
     * @param pSession la session hibernate
     * @throws JrafDaoException si une erreur survient
     */
    public void removeAllManagers(ISession pSession) throws JrafDaoException {
        Collection param = super.findAll(pSession);
        Iterator it = param.iterator();
        while (it.hasNext()) {
            super.remove(pSession, (SourceManagementBO) it.next());
        }
    }

    /**
     * Supprimer les sourcemanagements qui ne sont pas dans la collection
     * 
     * @param pSession la session hibernate
     * @param pManagers les sourcemanagements qui doivent être présents en base
     * @throws JrafDaoException si erreur
     */
    public void removeOthers(ISession pSession, Collection pManagers) throws JrafDaoException {
        StringBuffer whereClause = new StringBuffer("where ");
        whereClause.append(getAlias());
        whereClause.append(".name not in(");
        Iterator managersIt = pManagers.iterator();
        // On parcours les profils qui ne doivent pas être renvoyés
        boolean first = true;
        while (managersIt.hasNext()) {
            if (first) {
                first = false;
            } else {
                whereClause.append(", ");
            }
            whereClause.append("'" + ((SourceManagementBO) managersIt.next()).getName() + "'");
        }
        whereClause.append(")");
        Collection results = findWhere(pSession, whereClause.toString());
        Iterator it = results.iterator();
        // Suppression de chaque source manager
        SourceManagementBO managerBO = null;
        while (it.hasNext()) {
            managerBO = (SourceManagementBO) it.next();
            remove(pSession, managerBO);
        }
    }

    /**
     * Renvoit les sources managers présents en base mais non présents dans la liste
     * donnée en paramètre
     * 
     * @param pSession la session courante
     * @param pManagers la liste des sources managers
     * @return la liste des SourceManagementBO
     * @throws JrafDaoException si erreur
     */
    public Collection findOthers(ISession pSession, Collection pManagers) throws JrafDaoException {
        StringBuffer whereClause = new StringBuffer("where ");
        whereClause.append(getAlias());
        whereClause.append(".name not in(");
        Iterator managersIt = pManagers.iterator();
        // Pracours des sourcemanagements qui ne doivent pas être renvoyés
        boolean first = true;
        while (managersIt.hasNext()) {
            if (first) {
                first = false;
            } else {
                whereClause.append(", ");
            }
            whereClause.append("'" + ((SourceManagementBO) managersIt.next()).getName() + "'");
        }
        whereClause.append(")");
        Collection results = findWhere(pSession, whereClause.toString());
        return results;
    }

    /**
     * Retourne le source management dont le nom est pName
     * 
     * @param pSession la session hibernate
     * @param pName le nom du manager
     * @return le source management si il existe, null sinon
     * @throws JrafDaoException si erreur
     */
    public SourceManagementBO findWhereName(ISession pSession, String pName) throws JrafDaoException {
        SourceManagementBO result = null;
        StringBuffer whereClause = new StringBuffer("where ");
        whereClause.append(getAlias());
        whereClause.append(".name = '");
        whereClause.append(pName);
        whereClause.append("'");
        Collection results = this.findWhere(pSession, whereClause.toString());
        Iterator it = results.iterator();
        // Il ne doit y avoir qu'un résultat:
        if (it.hasNext()) {
            result = (SourceManagementBO) it.next();
        }
        return result;
    }

    /**
     * Obtention des source managers
     * @param pSession session
     * @return source managers triés par nom
     * @throws JrafDaoException si erreur
     */
    public Collection findSourceManagemements(ISession pSession) throws JrafDaoException {
        String whereClause = "";
        whereClause += "order by " + getAlias() + ".name";
        Collection col = findWhere(pSession, whereClause);
        return col;
    }
}
