/*
 * Créé le 19 juil. 05
 *
 */
package com.airfrance.squalecommon.daolayer.result.mccabe;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAMethodMetricsBO;

/**
 * @author M400843
 */
public class McCabeMethodMetricsDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static McCabeMethodMetricsDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new McCabeMethodMetricsDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private McCabeMethodMetricsDAOImpl()
    {
        initialize( McCabeQAMethodMetricsBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     * @deprecated
     */
    public static McCabeMethodMetricsDAOImpl getInstance()
    {
        return instance;
    }
}
