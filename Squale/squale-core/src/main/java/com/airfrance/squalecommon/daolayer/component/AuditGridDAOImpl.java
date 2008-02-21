package com.airfrance.squalecommon.daolayer.component;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditGridBO;

/**
 * Interface avec la base de données pour les grilles d'audit
 *
 */
public class AuditGridDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static AuditGridDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static {
        instance = new AuditGridDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private AuditGridDAOImpl() {
        initialize(AuditGridBO.class);
        if (null == LOG) {
            LOG = LogFactory.getLog(AuditGridDAOImpl.class);
        }
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static AuditGridDAOImpl getInstance() {
        return instance;
    }

    /**
     * Obtention des grilles d'audit
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @return résultats associés
     * @throws JrafDaoException si erreur
     */
    public AuditGridBO findWhere(ISession pSession, Long pProjectID, Long pAuditID) throws JrafDaoException {
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";

        AuditGridBO result = null;
        Collection col = findWhere(pSession, whereClause);
        if (col.size()==1) {
            result = (AuditGridBO) col.iterator().next();
        }
        return result;
    }
    
    /**
     * Test d'utilisation d'une grille
     *
     * @param pSession session
     * @param pGridId grille
     * @return true
     * @throws JrafDaoException si erreur
     */
    public boolean isGridUsed(ISession pSession, Long pGridId) throws JrafDaoException {
        String whereClause = "where "+getAlias()+".grid.id="+pGridId;
        return findWhere(pSession, whereClause).size()>0;
    }
}
