package com.airfrance.squalecommon.daolayer.config.web;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.HomepageComponentBO;

/**
 * DAO for the component HomepageComponentBO
 *
 */
public final class HomepageComponentDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Singleton
     */
    private static HomepageComponentDAOImpl instance;

    /** Initialization of singleton */
    static
    {
        instance = new HomepageComponentDAOImpl();
    }

    /**
     * Private constructor
     * 
     * @throws JrafDaoException
     */
    private HomepageComponentDAOImpl()
    {
        initialize( HomepageComponentBO.class );
    }

    /**
     * Return the singleton of the DAO
     * 
     * @return the singleton of the DAO
     */
    public static HomepageComponentDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * This method recover the list of HomepageComponent linked to the user id give in parameter
     * 
     * @param session The hibernate session
     * @param userId Id of the user
     * @return The list of component
     * @throws JrafDaoException Exception during the serach in the database
     */
    public Collection findByUserId( ISession session, long userId )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".user.id = ' " );
        whereClause.append( userId );
        whereClause.append( " '" );
        Collection ret = findWhere( session, whereClause.toString() );
        return ret;
    }

}
