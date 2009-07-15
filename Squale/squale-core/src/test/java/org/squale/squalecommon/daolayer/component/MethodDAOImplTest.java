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
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 */
public class MethodDAOImplTest
    extends SqualeTestCase
{

    /**
     * Test de la méthode de récupération d'une méthode en fonction de son nom du nom de fichier et de l'id de l'audit
     */
    public void testFindMethodByName()
    {
        try
        {
            String method1Name = "Test";
            String method1FileName = "Test\file.java";
            getSession().beginTransaction();
            ApplicationBO application = getComponentFactory().createTestApplication();
            ProjectBO project = (ProjectBO) application.getChildren().iterator().next();
            PackageBO packageBo = getComponentFactory().createPackage( getSession(), project );
            ClassBO classBo = getComponentFactory().createClass( getSession(), packageBo );
            MethodBO method = getComponentFactory().createMethod( getSession(), classBo );
            method.setName( method1Name );
            method.setLongFileName( method1FileName );
            AuditBO audit = getComponentFactory().createAuditResult( application );
            getSession().commitTransactionWithoutClose();
            getSession().beginTransaction();
            MethodDAOImpl dao = MethodDAOImpl.getInstance();
            Collection methods = dao.findMethodeByName( getSession(), method1Name, method1FileName, audit.getId() );
            getSession().commitTransactionWithoutClose();
            assertTrue( methods.size() == 1 );
            MethodBO methodResult = (MethodBO) methods.iterator().next();
            assertEquals( method.getName(), method1Name );
            assertEquals( method.getLongFileName(), method1FileName );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
