package com.airfrance.squalecommon.daolayer.config;

import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.Profile_DisplayConfBO;

/**
 * DAO pour le lien profil-configuration
 */
public class Profile_DisplayConfDAOImpl extends AbstractDAOImpl {

    /**
     * Instance singleton
     */
    private static Profile_DisplayConfDAOImpl instance = null;

    /** initialisation du singleton */
    static {
        instance = new Profile_DisplayConfDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private Profile_DisplayConfDAOImpl() {
        initialize(Profile_DisplayConfBO.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static Profile_DisplayConfDAOImpl getInstance() {
        return instance;
    }
}
