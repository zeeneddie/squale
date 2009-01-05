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
package com.airfrance.squalix.tools.rsm;

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
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.util.parser.CppParser;
import com.airfrance.squalix.util.parser.JavaParser;

/**
 * Teste la peristance des résultats issus de RSM. UNIT_KO : Trop fonctionnel Il faut comprendre le code RSM pour
 * refaire ce test unitaire.
 */
public class RSMPersistorTest
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
     * Constructeur pour RSMPersistorTest.
     * 
     * @param arg0 nom
     */
    public RSMPersistorTest( String arg0 )
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
        ProjectProfileBO profile = getComponentFactory().createProjectProfile( getSession() );
        mProject = getComponentFactory().createProject( getSession(), mAppli, grid, profile, null, null );
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
        String classpath = "./data/samples/bin";
        mData.putData( TaskData.CLASSPATH, classpath );
        mData.putData( TaskData.VIEW_PATH, "./data/samples/" );
        getSession().commitTransactionWithoutClose();

    }

    /**
     * Test du parsing du rapport rsm pour un projet avec un profil java
     */
    public void testParseReportJava()
    {
        try
        {

            // Enregistrement préalable des méthodes qui sont présentes dans le fichier
            // afin que on les retrouve
            // Comportement du au fait que RSM doit etre exécuté après un autre outil stockant ces données (comme
            // McCabe)
            // en test unitaire il faut donc le faire à place
            createMethodJava( "setTestNumber",
                              "vobs/squale/src/squalixTest/data/samples/testCommon/test/TestCommon.java" );

            getSession().beginTransaction();
            // Parsing de code java
            RSMConfiguration config = new RSMConfiguration();
            mProject.getProfile().setName( "java" );
            config = RSMConfiguration.build( mProject, "config/RSM-config.xml", mData );

            RSMPersistor persistor =
                new RSMPersistor( config, mAudit, getSession(), mData, "JavaRSMTask", new JavaParser( mProject ) );
            persistor.parseReport( "data/rsm/sample/rapport_RSM_java.csv", mData );
            Collection methodsColl =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            MethodBO.class );
            Collection classesColl =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            ClassBO.class );
            getSession().commitTransactionWithoutClose();
            // Vérification des objets créés
            // On a une seule méthode qui a été préalablement persistée
            assertEquals( 1, methodsColl.size() );
            // Il y a 3 classes présentes, une qui a été créé avec la méthode qu'on a mis en base,
            // et 2 extraites du fichier
            assertEquals( 3, classesColl.size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test du parsing du rapport rsm pour un projet avec un profil cpp
     */
    public void testParseReportCpp()
    {
        try
        {

            // Enregistrement préalable des méthodes qui sont présentes dans le fichier
            // afin que on les retrouve
            // Comportement du au fait que RSM doit etre exécuté après un autre outil stockant ces données (comme
            // McCabe)
            // en test unitaire il faut donc le faire à place
            createMethodCpp( "hash_dict_tweaker",
                             "vobs/squale/src/squalixTest/data/Project4McCabeCppTest/include/hash_dict_tweaker.h" );

            getSession().beginTransaction();
            // Parsing de code cpp
            RSMConfiguration config = new RSMConfiguration();
            mProject.getProfile().setName( "cpp" );
            config = RSMConfiguration.build( mProject, "config/RSM-config.xml", mData );

            RSMPersistor persistor =
                new RSMPersistor( config, mAudit, getSession(), mData, "CppRSMTask", new CppParser( mProject ) );
            persistor.parseReport( "data/RSM/sample/rapport_RSM_Cpp.csv", mData );
            Collection methodsColl =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            MethodBO.class );
            Collection classesColl =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            ClassBO.class );
            getSession().commitTransactionWithoutClose();
            // Vérification des objets créés
            // une seule méthode créée en base
            assertEquals( 1, methodsColl.size() );
            // 4 classes dans le fichier, une crée avec la méthode
            assertEquals( 5, classesColl.size() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Créée une méthode java pour pouvoir tester la récupération
     * 
     * @param pMethodName le nom de la méthode
     * @param pFileName le nom du fichier dans lequel se trouve la méthode
     */
    private void createMethodJava( String pMethodName, String pFileName )
    {
        try
        {
            getSession().beginTransaction();
            ApplicationBO application = getComponentFactory().createApplicationWithSite( getSession(), "QVI" );
            ProjectBO project = getComponentFactory().createProject( getSession(), application, null );
            PackageBO packageBo = getComponentFactory().createPackage( getSession(), project );
            ClassBO classBo = getComponentFactory().createClass( getSession(), packageBo );
            MethodBO method = getComponentFactory().createMethod( getSession(), classBo );
            method.setName( pMethodName );
            method.setLongFileName( pFileName );
            AuditBO audit = getComponentFactory().createAuditResult( application );
            getSession().commitTransactionWithoutClose();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "can't create env" );
        }
    }

    /**
     * Créée une méthode java pour pouvoir tester la récupération
     * 
     * @param pMethodName le nom de la méthode
     * @param pFileName le nom du fichier dans lequel se trouve la méthode
     */
    private void createMethodCpp( String pMethodName, String pFileName )
    {
        try
        {
            getSession().beginTransaction();
            ApplicationBO application = getComponentFactory().createApplicationWithSite( getSession(), "QVI" );
            ProjectBO project = getComponentFactory().createProject( getSession(), application, null );
            ClassBO classBo = getComponentFactory().createCppClass( getSession(), project );
            MethodBO method = getComponentFactory().createMethod( getSession(), classBo );
            method.setName( pMethodName );
            method.setLongFileName( pFileName );
            AuditBO audit = getComponentFactory().createAuditResult( application );
            getSession().commitTransactionWithoutClose();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "can't create env" );
        }
    }

}
