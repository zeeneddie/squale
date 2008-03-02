package com.airfrance.squalecommon.daolayer.config;

import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.StopTimeBO;

/**
 * DAO pour StopTimeBO
 */
public class StopTimeDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static StopTimeDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new StopTimeDAOImpl();
    }

    /**
     * Constructeur prive
     */
    private StopTimeDAOImpl()
    {
        initialize( StopTimeBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static StopTimeDAOImpl getInstance()
    {
        return instance;
    }
}
