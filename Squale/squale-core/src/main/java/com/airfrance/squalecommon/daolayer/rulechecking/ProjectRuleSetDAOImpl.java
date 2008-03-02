package com.airfrance.squalecommon.daolayer.rulechecking;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;

/**
 * DAO pour les règles liées à un projet
 */
public class ProjectRuleSetDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static ProjectRuleSetDAOImpl instance = null;

    /**
     * initialisation du singleton
     */
    static
    {
        instance = new ProjectRuleSetDAOImpl();
    }

    /**
     * Constructeur privé
     * 
     * @throws JrafDaoException
     */
    private ProjectRuleSetDAOImpl()
    {
        initialize( ProjectRuleSetBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ProjectRuleSetDAOImpl getInstance()
    {
        return instance;
    }
}
