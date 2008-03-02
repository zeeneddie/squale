package com.airfrance.squalecommon.daolayer.rule;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;

/**
 * 
 */
public class PracticeRuleAPDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static PracticeRuleAPDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new PracticeRuleAPDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private PracticeRuleAPDAOImpl()
    {
        initialize( PracticeRuleBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static PracticeRuleAPDAOImpl getInstance()
    {
        return instance;
    }
}
