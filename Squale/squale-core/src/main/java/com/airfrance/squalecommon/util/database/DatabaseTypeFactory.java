package com.airfrance.squalecommon.util.database;

import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.PersistenceProviderImpl;

/**
 * This class is the Factory of DatabaseType : According to the database use, the good DatabaseType is create.
 */
public final class DatabaseTypeFactory
{

    /**
     * The instance of DatabaseType.
     */
    private static DatabaseType databaseInstance;

    /**
     * Give the good instance of DatabaseType : OracleType, MySQLType. This depend of the database used.
     * 
     * @return The good instance of database type
     * @exception JrafDaoException error happened if the hibernate dialect used is not implement in Squale
     */
    public static DatabaseType getInstance()
        throws JrafDaoException
    {
        if ( databaseInstance == null )
        {
            newInstance();
        }

        return databaseInstance;
    }

    /**
     * Create the good new instance of DatabaseType according to the Database used.
     * 
     * @throws JrafDaoException This exception happened if the dialect used for configure hibernate is not implement in
     *             Squale
     */
    private static void newInstance()
        throws JrafDaoException
    {
        PersistenceProviderImpl session = (PersistenceProviderImpl) PersistenceHelper.getPersistenceProvider();
        SessionFactoryImpl hibSession = (SessionFactoryImpl) session.getSessions();
        Dialect dial = hibSession.getDialect();
        String dialectUsed = dial.toString();

        if ( dialectUsed.equals( "org.hibernate.dialect.MySQLDialect" ) )
        {
            databaseInstance = new MySQLType();
        }
        else if ( dialectUsed.equals( "org.hibernate.dialect.MySQLInnoDBDialect" ) )
        {
            databaseInstance = new MySQLType();
        }
        else if ( dialectUsed.equals( "org.hibernate.dialect.OracleDialect" ) )
        {
            databaseInstance = new OracleType();
        }
        else if ( dialectUsed.equals( "org.hibernate.dialect.Oracle9Dialect" ) )
        {
            databaseInstance = new OracleType();
        }
        else
        {
            throw new JrafDaoException( "hibernate dialect unknown, contact the Squale administrator" );

        }
    }

    /**
     * No argument constructor
     */
    private DatabaseTypeFactory()
    {

    }

}
