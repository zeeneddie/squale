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
package org.squale.squalix.tools.compiling.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;

/**
 * Test pour la tâche de compilation Java pour les projets déjà compilés
 */
public class JavaMockCompilingTaskTest
    extends SqualeTestCase
{

    /** répertoire des .class (relatif à la vue) */
    public static final String COMPILED_DIR = "bin_for_test";

    /**
     * le classpath du projet (relatif à la vue)
     */
    private static final String CLASSPATH = "bin_for_test";

    /**
     * Pseudo-chemin vers une vue.
     */
    private static final String VIEW_PATH = "data/samples/";

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** le projet à auditer */
    private ProjectBO mProject = new ProjectBO();

    /** l'audit */
    private AuditBO mAudit = new AuditBO();

    /** les tâches temporaires */
    private TaskData mData = new TaskData();

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
        // Les paramètres doivent contenir le chemin vers les .class
        // et le classpath
        MapParameterBO projectParams = new MapParameterBO();
        MapParameterBO taskParams = new MapParameterBO();
        StringParameterBO classpath = new StringParameterBO();
        classpath.setValue( CLASSPATH );
        taskParams.getParameters().put( ParametersConstants.CLASSPATH, classpath );
        // Dialect
        StringParameterBO dialectParam = new StringParameterBO();
        dialectParam.setValue( ParametersConstants.JAVA1_4 );
        ListParameterBO listParam = new ListParameterBO();
        // Traitement des sources compilées
        List list = new ArrayList();
        StringParameterBO stringParam = new StringParameterBO();
        stringParam.setValue( COMPILED_DIR );
        list.add( stringParam );
        listParam.setParameters( list );
        taskParams.getParameters().put( ParametersConstants.COMPILED_SOURCES_DIRS, listParam );
        projectParams.getParameters().put( ParametersConstants.COMPILED, taskParams );
        projectParams.getParameters().put( ParametersConstants.DIALECT, dialectParam );
        ProjectParameterDAOImpl.getInstance().create( getSession(), projectParams );
        mProject.setParameters( projectParams );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();
        File viewFile = new File( VIEW_PATH );
        mData.putData( TaskData.VIEW_PATH, viewFile.getCanonicalPath() );
    }

    /**
     * Teste l'exécution d la tâche
     */
    public void testExecute()
    {
        JavaMockCompilingTask task = new JavaMockCompilingTask();
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setData( mData );
        File test = new File( "config/compiling-config.xml" );
        String path = test.getAbsolutePath();
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );
        List classes_dirs = (List) mData.getData( TaskData.CLASSES_DIRS );
        assertNotNull( classes_dirs );
        assertTrue( classes_dirs.size() > 0 );
        assertTrue( ( (String) classes_dirs.get( 0 ) ).endsWith( "data\\compiledsources" ) );
        String classpath = (String) mData.getData( TaskData.CLASSPATH );
        assertNotNull( classpath );
        assertTrue( classpath.replaceAll( "\\\\", "/" ).matches(
                                                                 ".*"
                                                                     + ( (String) classes_dirs.get( 0 ) ).replaceAll(
                                                                                                                      "\\\\",
                                                                                                                      "/" )
                                                                     + ".*" ) );
    }

}
