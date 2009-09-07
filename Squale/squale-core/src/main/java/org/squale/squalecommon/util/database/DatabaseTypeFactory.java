/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalecommon.util.database;

import org.hibernate.dialect.Dialect;
import org.hibernate.impl.SessionFactoryImpl;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.PersistenceProviderImpl;

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

        if ( dialectUsed.startsWith( "org.hibernate.dialect.MySQL" ) )
        {
            databaseInstance = new MySQLType();
        }
        else if ( dialectUsed.startsWith( "org.hibernate.dialect.Oracle" ) )
        {
            databaseInstance = new OracleType();
        }
        else if (dialectUsed.startsWith( "org.hibernate.dialect.HSQL" ))
        {
            databaseInstance = new HsqldbType();
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
