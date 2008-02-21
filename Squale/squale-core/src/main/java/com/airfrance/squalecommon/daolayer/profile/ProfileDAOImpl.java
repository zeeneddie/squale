/*
 * Créé le 6 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.profile;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;

/**
 * @author M400843
 *
 */
public class ProfileDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static ProfileDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static {
        instance = new ProfileDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private ProfileDAOImpl() {
        initialize(ProfileBO.class);
        if (null == LOG) {
            LOG = LogFactory.getLog(UserDAOImpl.class);
        }
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static ProfileDAOImpl getInstance() {
        return instance;
    }

    /**
     * Récupère ProfileBO associé à la clé passée en paramètre
     * @param pSession la session
     * @param pKey clé du profil
     * @return ProfileBO
     * @throws JrafDaoException exception Dao
     */
    public ProfileBO loadByKey(ISession pSession, String pKey) throws JrafDaoException{
        LOG.debug(DAOMessages.getString("dao.entry_method"));
        String whereClause = "where ";
        whereClause += getAlias() + ".name = '" + pKey + "'";
        
        ProfileBO profile = null;
        Collection col = findWhere(pSession, whereClause);
        if(col.size() >= 1) {
            profile = (ProfileBO) col.iterator().next();
            if(col.size() > 1 ) {
                String tab[] = {pKey};
                LOG.warn(DAOMessages.getString("profile.many.key", tab));
            }
        }

        LOG.debug(DAOMessages.getString("dao.exit_method"));
        return profile;
    }
}
