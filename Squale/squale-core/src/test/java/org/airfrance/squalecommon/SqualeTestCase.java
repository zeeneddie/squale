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
package com.airfrance.squalecommon;

import java.sql.Connection;
import java.sql.Statement;

import junit.framework.TestCase;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.operation.DatabaseOperation;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.SessionImpl;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;

/**
 * Test unitaire de squale Cette classe permet d'initialiser la couche JRAF pour la bonne exécution des tests unitaires
 */
abstract public class SqualeTestCase
    extends TestCase
{
    /** Session */
    private ISession mSession;

    /** Fabrique de composant */
    private ComponentFactory mComponentFactory;

    static
    {
        // Initialisation de la couche JRAF
        JRafConfigurator.initialize();
    }

    /**
     * 
     */
    public SqualeTestCase()
    {
        super();
    }

    /**
     * @param arg0 nom
     */
    public SqualeTestCase( String arg0 )
    {
        super( arg0 );
        JRafConfigurator.initialize();
    }

    /**
     * @return fabrique
     */
    public ComponentFactory getComponentFactory()
    {
        return mComponentFactory;
    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();

        IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

        // Initialisation
        ISession session = null; // session Hibernate
        session = PERSISTENTPROVIDER.getSession();
        session.beginTransaction();
        Connection conn = ( (SessionImpl) session ).getSession().connection();
        Statement stm = conn.createStatement();
        stm.execute( "SET REFERENTIAL_INTEGRITY FALSE" );
        IDatabaseConnection dc = new DatabaseConnection( ( (SessionImpl) session ).getSession().connection() );
        DatabaseOperation.DELETE_ALL.execute( dc, dc.createDataSet() );
        stm.execute( "SET REFERENTIAL_INTEGRITY TRUE" );
        session.commitTransaction();
        session.closeSession();

        // Utilisation d'une autre session
        mSession = PERSISTENTPROVIDER.getSession();
        mComponentFactory = new ComponentFactory( mSession );
    }

    /**
     * Obtention de la session
     * 
     * @return session
     */
    protected ISession getSession()
    {
        return mSession;
    }

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    protected void tearDown()
        throws Exception
    {
        super.tearDown();
        getSession().closeSession();
    }

}
