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
package com.airfrance.squalecommon.enterpriselayer.facade.component;

import java.util.Collection;
import java.util.List;

import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Test de la facade component
 */
public class ComponentFacadeTest
    extends SqualeTestCase
{

    /**
     * provider de persistence
     */
    private static IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Teste la recuperation du ComponentDTO relatif a une application
     */
    public void testGetWithApplication()
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            ApplicationBO application = getComponentFactory().createApplication( session );
            // Initialisation des parametres de la methode
            ComponentDTO componentIn = new ComponentDTO();
            componentIn.setID( application.getId() );

            // Exceution de la méthode
            ComponentDTO out = ComponentFacade.get( componentIn );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Teste la recuperation des composants enfants (projets) relative a une application
     */
    public void testGetChildrenWithProject()
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            ApplicationBO application = getComponentFactory().createApplication( session );
            QualityGridBO grid = getComponentFactory().createGrid( session );
            ProjectBO project = getComponentFactory().createProject( session, application, grid );
            PackageBO pkg = getComponentFactory().createPackage( session, project );
            ClassBO cls = getComponentFactory().createClass( session, pkg );
            // Initialisation des parametres de la methode
            ComponentDTO componentIn = new ComponentDTO();
            componentIn.setID( project.getId() );
            Collection out = ComponentFacade.getChildren( componentIn, pkg.getType(), null, null );
            assertEquals( 1, out.size() );
            out = ComponentFacade.getChildren( componentIn, null, null, null );
            assertEquals( 1, out.size() );
            out = ComponentFacade.getChildren( componentIn, cls.getType(), null, null );
            assertEquals( 0, out.size() );

            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de getParentsComponent
     */
    public void testGetParentList()
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            ApplicationBO application = getComponentFactory().createApplication( session );
            QualityGridBO grid = getComponentFactory().createGrid( session );
            ProjectBO project = getComponentFactory().createProject( session, application, grid );
            PackageBO pkg = getComponentFactory().createPackage( session, project );
            ClassBO cls = getComponentFactory().createClass( session, pkg );
            MethodBO method = getComponentFactory().createMethod( session, cls );

            // Initialisation des parametres de la methode
            ComponentDTO componentIn = new ComponentDTO();
            componentIn.setID( cls.getId() );
            List out = ComponentFacade.getParentsComponent( componentIn, null );
            assertEquals( 1, out.size() );
            assertEquals( pkg.getId(), ( (ComponentDTO) out.get( 0 ) ).getID() );
            componentIn.setID( method.getId() );
            out = ComponentFacade.getParentsComponent( componentIn, null );
            assertEquals( 2, out.size() );
            assertEquals( pkg.getId(), ( (ComponentDTO) out.get( 0 ) ).getID() );
            assertEquals( cls.getId(), ( (ComponentDTO) out.get( 1 ) ).getID() );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
