/*
 * Créé le 19 juil. 05
 *
 */
package com.airfrance.squalecommon.daolayer.result;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.CriteriumResultBO;

/**
 * @author M400843
 */
public class CriteriumResultDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static CriteriumResultDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new CriteriumResultDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private CriteriumResultDAOImpl()
    {
        initialize( CriteriumResultBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     * @deprecated
     */
    public static CriteriumResultDAOImpl getInstance()
    {
        return instance;
    }
}
