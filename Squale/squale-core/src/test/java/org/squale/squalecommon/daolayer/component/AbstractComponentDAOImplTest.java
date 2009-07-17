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
package org.squale.squalecommon.daolayer.component;

import java.util.Collection;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * @author M400843
 */
public class AbstractComponentDAOImplTest
    extends SqualeTestCase
{

    /**
     * Test de findProjectChildren
     */
    public void testFindProjectChildren()
    {
        try
        {
            ApplicationBO application = getComponentFactory().createTestApplication();
            ProjectBO project = (ProjectBO) application.getChildren().iterator().next();
            AuditBO audit = getComponentFactory().createAuditResult( application );
            AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
            Collection coll = dao.findProjectChildren( getSession(), project, audit, PackageBO.class );
            assertEquals( 1, coll.size() );
            coll = dao.findProjectChildren( getSession(), project, audit, ClassBO.class );
            assertEquals( 1, coll.size() );
            coll = dao.findProjectChildren( getSession(), project, audit, MethodBO.class );
            assertEquals( 1, coll.size() );
            // Cas particulier de l'application, le projet est renvoyé
            coll = dao.findProjectChildren( getSession(), project, audit, ApplicationBO.class );
            assertEquals( 1, coll.size() );
            assertEquals( project, coll.iterator().next() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de findProjectChildren
     */
    public void testFindChild()
    {
        try
        {
            getSession().beginTransaction();
            ApplicationBO application = getComponentFactory().createApplication( getSession() );
            QualityGridBO grid = getComponentFactory().createGrid( getSession() );
            ProjectBO project = getComponentFactory().createProject( getSession(), application, grid );
            PackageBO packageBO1 = getComponentFactory().createPackage( getSession(), project );
            PackageBO packageBO2 = getComponentFactory().createPackage( getSession(), "package2", packageBO1 );
            PackageBO packageBO3 = getComponentFactory().createPackage( getSession(), "package2", packageBO2 );
            getSession().commitTransactionWithoutClose();
            // On recherche le package package2 qui a comme parent packageBO1:
            AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
            PackageBO packageResult = (PackageBO) dao.findChild( getSession(), packageBO1, "package2" );
            assertTrue( packageResult.getId() > -1 );
            assertEquals( "package2", packageResult.getName() );
            // On recherche le package package2 qui a comme parent packageBO2:
            PackageBO packageResult2 = (PackageBO) dao.findChild( getSession(), packageBO2, "package2" );
            assertTrue( packageResult.getId() > -1 );
            assertEquals( "package2", packageResult.getName() );
            assertEquals( packageBO2.getId(), packageResult2.getParent().getId() );
            // On recherche un composant qui n'existe pas
            ClassBO classBO = (ClassBO) dao.findChild( getSession(), packageBO1, "class" );
            assertNull( classBO );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de findProjectChildren
     */
    public void testFindChildrenWhere()
    {
        try
        {
            ApplicationBO application = getComponentFactory().createTestApplication();
            ProjectBO project = (ProjectBO) application.getChildren().iterator().next();
            AuditBO audit = getComponentFactory().createAuditResult( application );
            AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
            Collection coll =
                dao.findChildrenWhere( getSession(), new Long( project.getId() ), new Long( audit.getId() ),
                                       ComponentType.PACKAGE, "" );
            assertEquals( 1, coll.size() );
            Collection col2 =
                dao.findChildrenWhere( getSession(), new Long( project.getId() ), new Long( audit.getId() ),
                                       ComponentType.PACKAGE, "test" );
            assertEquals( 0, col2.size() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
