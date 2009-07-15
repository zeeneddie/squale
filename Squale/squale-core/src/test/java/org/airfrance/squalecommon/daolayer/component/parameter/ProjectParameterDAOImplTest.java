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
package com.airfrance.squalecommon.daolayer.component.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/** 
 */
public class ProjectParameterDAOImplTest
    extends SqualeTestCase
{

    /**
     * Teste l'insertion dans la base
     */
    public void testInsert()
    {
        ProjectParameterDAOImpl ProjectParameterDAO = null;
        ProjectParameterBO projectParamBO = null;
        try
        {
            ProjectParameterDAO = ProjectParameterDAOImpl.getInstance();
            MapParameterBO mapParams = new MapParameterBO();
            StringParameterBO strParam = new StringParameterBO();
            strParam.setValue( "value" );
            ProjectParameterDAO.create( getSession(), strParam );
            assertEquals( 1, ProjectParameterDAO.count( getSession() ).intValue() );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * test basic pour la création d'objet
     */
    public void testCreate()
    {
        try
        {
            ProjectParameterDAOImpl dao = ProjectParameterDAOImpl.getInstance();
            StringParameterBO str = new StringParameterBO();
            str.setValue( "VALUE" );
            dao.create( getSession(), str );
            ListParameterBO lst = new ListParameterBO();
            lst.getParameters().add( str );
            dao.create( getSession(), lst );
            lst = new ListParameterBO();
            lst.getParameters().add( new StringParameterBO() );
            dao.create( getSession(), lst );
            MapParameterBO mapBO = new MapParameterBO();
            mapBO.getParameters().put( "Test map", lst );
            dao.create( getSession(), mapBO );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Teste en profondeur la récupération correcte des paramètres d'un projectBO.
     */
    public void testRetrieve()
    {
        try
        {
            // Remplissage de la structure
            MapParameterBO mapParamBO = new MapParameterBO();
            ListParameterBO listParamBO1 = new ListParameterBO();
            ListParameterBO listParamBO2 = new ListParameterBO();
            ArrayList liste1 = new ArrayList( 0 );
            ArrayList liste2 = new ArrayList( 0 );
            StringParameterBO stringParamBO1 = new StringParameterBO();
            StringParameterBO stringParamBO2 = new StringParameterBO();
            StringParameterBO stringParamBO3 = new StringParameterBO();
            StringParameterBO stringParamBO4 = new StringParameterBO();
            StringParameterBO stringParamBO5 = new StringParameterBO();
            StringParameterBO stringParamBO6 = new StringParameterBO();
            stringParamBO1.setValue( " BO1" );
            stringParamBO2.setValue( " BO2" );
            stringParamBO3.setValue( " BO3" );
            stringParamBO4.setValue( " BO4" );
            stringParamBO5.setValue( " BO5" );
            stringParamBO6.setValue( " BO6" );
            liste1.add( stringParamBO2 );
            liste1.add( stringParamBO5 );
            liste2.add( stringParamBO4 );
            liste2.add( stringParamBO3 );
            listParamBO1.setParameters( liste1 );
            listParamBO2.setParameters( liste2 );
            Map m = new HashMap();
            m.put( "liste1", listParamBO1 );
            m.put( "string1", stringParamBO1 );
            m.put( "liste2", listParamBO2 );
            m.put( "string2", stringParamBO2 );
            mapParamBO.setParameters( m );
            // Fin de remplissage de la structure
            // Enregistrement du ProjectBO dans la base
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            ApplicationBO appBO = getComponentFactory().createApplication( getSession() );
            ProjectBO projectBO =
                getComponentFactory().createProject( getSession(), appBO,
                                                     getComponentFactory().createGrid( getSession() ) );
            ProjectParameterDAOImpl.getInstance().create( getSession(), mapParamBO );
            projectBO.setParameters( mapParamBO );
            ProjectDAOImpl.getInstance().save( getSession(), projectBO );
            // Récupération du projectBO depuis la base
            ProjectBO projectBO2 = (ProjectBO) projectDAO.load( getSession(), new Long( projectBO.getId() ) );
            projectDAO.refresh( getSession(), projectBO );
            // test en profondeur sur l'ensemble de l'objet.
            assertEquals( projectBO, projectBO2 );
            assertEquals( projectBO.getParameters(), projectBO2.getParameters() );
            assertEquals( projectBO.getParameters().getParameters(), projectBO2.getParameters().getParameters() );
            assertEquals( projectBO.getParameters().getParameters().get( "liste1" ),
                          projectBO2.getParameters().getParameters().get( "liste1" ) );
            assertEquals( projectBO.getParameters().getParameters().get( "liste2" ),
                          projectBO2.getParameters().getParameters().get( "liste2" ) );
            assertEquals( projectBO.getParameters().getParameters().get( "string1" ),
                          projectBO2.getParameters().getParameters().get( "string1" ) );
            assertEquals( projectBO.getParameters().getParameters().get( "string2" ),
                          projectBO2.getParameters().getParameters().get( "string2" ) );
            assertEquals(
                          ( (ListParameterBO) projectBO.getParameters().getParameters().get( "liste1" ) ).getParameters().get(
                                                                                                                               0 ),
                          ( (ListParameterBO) projectBO2.getParameters().getParameters().get( "liste1" ) ).getParameters().get(
                                                                                                                                0 ) );
            assertEquals(
                          ( (ListParameterBO) projectBO.getParameters().getParameters().get( "liste2" ) ).getParameters().get(
                                                                                                                               0 ),
                          ( (ListParameterBO) projectBO2.getParameters().getParameters().get( "liste2" ) ).getParameters().get(
                                                                                                                                0 ) );
            assertEquals(
                          ( (ListParameterBO) projectBO.getParameters().getParameters().get( "liste1" ) ).getParameters().get(
                                                                                                                               1 ),
                          ( (ListParameterBO) projectBO2.getParameters().getParameters().get( "liste1" ) ).getParameters().get(
                                                                                                                                1 ) );
            assertEquals(
                          ( (ListParameterBO) projectBO.getParameters().getParameters().get( "liste2" ) ).getParameters().get(
                                                                                                                               1 ),
                          ( (ListParameterBO) projectBO2.getParameters().getParameters().get( "liste2" ) ).getParameters().get(
                                                                                                                                1 ) );
            assertEquals(
                          ( (StringParameterBO) ( (ListParameterBO) projectBO.getParameters().getParameters().get(
                                                                                                                   "liste1" ) ).getParameters().get(
                                                                                                                                                     0 ) ).getValue(),
                          ( (StringParameterBO) ( (ListParameterBO) projectBO2.getParameters().getParameters().get(
                                                                                                                    "liste1" ) ).getParameters().get(
                                                                                                                                                      0 ) ).getValue() );
            assertEquals(
                          ( (StringParameterBO) ( (ListParameterBO) projectBO.getParameters().getParameters().get(
                                                                                                                   "liste1" ) ).getParameters().get(
                                                                                                                                                     1 ) ).getValue(),
                          ( (StringParameterBO) ( (ListParameterBO) projectBO2.getParameters().getParameters().get(
                                                                                                                    "liste1" ) ).getParameters().get(
                                                                                                                                                      1 ) ).getValue() );
            assertEquals(
                          ( (StringParameterBO) ( (ListParameterBO) projectBO.getParameters().getParameters().get(
                                                                                                                   "liste2" ) ).getParameters().get(
                                                                                                                                                     0 ) ).getValue(),
                          ( (StringParameterBO) ( (ListParameterBO) projectBO2.getParameters().getParameters().get(
                                                                                                                    "liste2" ) ).getParameters().get(
                                                                                                                                                      0 ) ).getValue() );
            assertEquals(
                          ( (StringParameterBO) ( (ListParameterBO) projectBO.getParameters().getParameters().get(
                                                                                                                   "liste2" ) ).getParameters().get(
                                                                                                                                                     1 ) ).getValue(),
                          ( (StringParameterBO) ( (ListParameterBO) projectBO2.getParameters().getParameters().get(
                                                                                                                    "liste2" ) ).getParameters().get(
                                                                                                                                                      1 ) ).getValue() );

        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}