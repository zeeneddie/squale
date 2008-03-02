/*
 * Créé le 19 juil. 05
 *
 */
package com.airfrance.squalecommon.daolayer.result.mccabe;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAProjectMetricsBO;

/**
 * @author M400843
 */
public class McCabeProjectMetricsDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static McCabeProjectMetricsDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new McCabeProjectMetricsDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private McCabeProjectMetricsDAOImpl()
    {
        initialize( McCabeQAProjectMetricsBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     * @deprecated
     */
    public static McCabeProjectMetricsDAOImpl getInstance()
    {
        return instance;
    }
}
