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
package org.squale.squaleweb;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.operation.DatabaseOperation;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.SessionImpl;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.ComponentFactory;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squalecommon.util.initialisor.JRafConfigurator;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.UserForm;
import org.squale.squaleweb.transformer.LogonBeanTransformer;
import org.squale.squaleweb.transformer.UserTransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;
import org.squale.welcom.struts.util.WConstants;

import servletunit.struts.MockStrutsTestCase;

/**
 * Test unitaire en contexte WEB
 */
public class SqualeWebTestCase
    extends MockStrutsTestCase
{

    static
    {
        // Initialisation de la couche JRAF
        JRafConfigurator.initialize();
    }

    /** Session */
    private ISession mSession;

    /** Fabrique de composant */
    private ComponentFactory mComponentFactory;

    /**
     * @return fabrique
     */
    public ComponentFactory getComponentFactory()
    {
        return mComponentFactory;
    }

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
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
        setContextDirectory( new File( "../squaleWeb/WebContent" ) );
        setConfigFile( "../squaleWeb/WebContent/WEB-INF/config/struts-config.xml" );
    }

    /**
     * @param pMatricule matricule utilisateur
     * @param pAdmin indique s'il y a un privilège administrateur
     * @throws JrafEnterpriseException si erreur
     * @throws WTransformerException si erreur
     */
    protected void setupLogonBean( String pMatricule, boolean pAdmin )
        throws JrafEnterpriseException, WTransformerException
    {
        UserDTO user = new UserDTO();
        user.setMatricule( pMatricule );

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Login" );
        Object[] paramIn = { user, Boolean.valueOf( pAdmin ) };

        user = (UserDTO) ac.execute( "verifyUser", paramIn );
        UserForm userForm = (UserForm) WTransformerFactory.objToForm( UserTransformer.class, user );
        LogonBean logonBeanSecurity = new LogonBean();
        WTransformerFactory.formToObj( LogonBeanTransformer.class, userForm, new Object[] { logonBeanSecurity,
            Boolean.valueOf( pAdmin ) } );
        getRequest().getSession().setAttribute( WConstants.USER_KEY, logonBeanSecurity );
    }

    /**
     * Obtention de la session
     * 
     * @return session
     */
    protected ISession getJRAFSession()
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
        getJRAFSession().closeSession();
        super.tearDown();
    }

}
