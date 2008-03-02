/*
 * Créé le 19 juil. 05
 *
 */
package com.airfrance.squalecommon.daolayer.result.mccabe;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAClassMetricsBO;

/**
 * @author M400843
 */
public class McCabeClassMetricsDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static McCabeClassMetricsDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new McCabeClassMetricsDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private McCabeClassMetricsDAOImpl()
    {
        initialize( McCabeQAClassMetricsBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     * @deprecated
     */
    public static McCabeClassMetricsDAOImpl getInstance()
    {
        return instance;
    }
}
