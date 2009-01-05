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
package com.airfrance.squalix.tools.mccabe;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.util.parser.CppParser;
import com.airfrance.squalix.util.parser.JavaParser;

/**
 * Teste la peristance des résultats issus de McCabe.
 */
public class McCabePersistorTest
    extends SqualeTestCase
{

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** le projet à auditer */
    private ProjectBO mProject = new ProjectBO();

    /** l'audit */
    private AuditBO mAudit = new AuditBO();

    /** les tâches temporaires */
    private TaskData mData = new TaskData();

    /**
     * Constructeur pour McCabePersistorTest.
     * 
     * @param arg0 nom
     */
    public McCabePersistorTest( String arg0 )
    {
        super( arg0 );

    }

    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        mAppli = getComponentFactory().createApplication( getSession() );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        mProject = getComponentFactory().createProject( getSession(), mAppli, grid );
        // Enregistrement du ProjectBO dans la base
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
        MapParameterBO params = new MapParameterBO();
        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        ArrayList list = new ArrayList();
        StringParameterBO value = new StringParameterBO();
        value.setValue( "test" );
        list.add( value );
        ListParameterBO srcs = new ListParameterBO();
        srcs.setParameters( list );
        params.getParameters().put( ParametersConstants.SOURCES, srcs );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );
        mData.putData( TaskData.VIEW_PATH, "./Project4McCabeTest" );

    }

    /**
     * Test du parsing d'un rapport de classe java
     */
    public void testParseClassReportJava()
    {
        try
        {
            // Parsing de code java
            McCabeConfiguration config = new McCabeConfiguration();
            config.setProject( mProject );
            config.setSubWorkspace( new File( "data/results/mccabe" ) );

            OOMcCabePersistor persistor =
                new OOMcCabePersistor( new JavaParser( mProject ), config, mAudit, getSession(), mData, "JavaMcCabeTask",
                                     "csv.java.template.class" );
            persistor.parseClassReport( "data/mccabe/sample/java/CLASSES.csv" );
            // Vérification des objets créés
            Collection coll =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            ClassBO.class );
            assertEquals( 2, coll.size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de parsing d'un rapport de méthodes java
     */
    public void testParseMethodReportJava()
    {
        try
        {
            // Parsing de code java
            McCabeConfiguration config = new McCabeConfiguration();
            config.setProject( mProject );
            config.setSubWorkspace( new File( "data/results/mccabe" ) );

            OOMcCabePersistor persistor =
                new OOMcCabePersistor( new JavaParser( mProject ), config, mAudit, getSession(), mData, "JavaMcCabeTask",
                                     "csv.java.template.class" );
            persistor.parseMethodReport( "data/mccabe/sample/java/METHODES.csv", mData );
            Collection coll =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            MethodBO.class );
            // Vérification des objets créés
            assertEquals( 10, coll.size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de parsing d'un rapport de classes c++
     */
    public void testParseClassReportCpp()
    {
        try
        {
            // Parsing de code cpp
            McCabeConfiguration config = new McCabeConfiguration();
            config.setProject( mProject );
            config.setSubWorkspace( new File( "data/results/mccabe" ) );
            OOMcCabePersistor persistor =
                new OOMcCabePersistor( new CppParser( mProject ), config, mAudit, getSession(), mData, "CppMcCabeTask",
                                     "csv.template.class" );
            persistor.parseClassReport( "data/mccabe/sample/cpp/CLASSES.csv" );
            Collection coll =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            ClassBO.class );
            // Vérification des objets créés
            assertEquals( 4, coll.size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de parsing d'un rapport de méthodes c++
     */
    public void testParseMethodReportCpp()
    {
        try
        {
            // Parsing de code cpp
            McCabeConfiguration config = new McCabeConfiguration();
            config.setProject( mProject );
            config.setSubWorkspace( new File( "data/results/mccabe" ) );

            OOMcCabePersistor persistor =
                new OOMcCabePersistor( new CppParser( mProject ), config, mAudit, getSession(), mData, "CppMcCabeTask",
                                     "csv.template" );
            persistor.parseMethodReport( "data/mccabe/sample/cpp/METHODES.csv", mData );
            Collection coll =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            MethodBO.class );
            // Vérification des objets créés
            assertEquals( 21, coll.size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
