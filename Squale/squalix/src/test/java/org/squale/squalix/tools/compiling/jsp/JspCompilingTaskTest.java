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
package com.airfrance.squalix.tools.compiling.jsp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.tools.compiling.utility.FileManager;

/**
 * Test de la tâche de compilation des JSPs. UNIT_KO : Test ok mais non directement testable du fait du changement
 * temporaire dans la compilation pour avoir la version 1.5 de java. Le test passe si dans la classe de compilation Java
 * (JWSADAntCompiler) on revient au code précédent la particularité 1.5
 */
public class JspCompilingTaskTest
    extends SqualeTestCase
{

    /** Répertoire contenant les jsps */
    private static final String JSP_SOURCES_DIR = "data/samples/testWeb/WebContent/jsp";

    /** Répertoire exclu */
    private static final String EXLUDED_DIR = "pac#§^kage#1";

    /** Le view_path */
    public static final String VIEW_PATH = ".";

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** le projet à auditer */
    private ProjectBO mProject = new ProjectBO();

    /** l'audit */
    private AuditBO mAudit = new AuditBO();

    /** les tâches temporaires */
    private TaskData mData = new TaskData();

    /** constructeur par défaut */
    public JspCompilingTaskTest()
    {
        super();
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
        ListParameterBO listParam = new ListParameterBO();
        List list = new ArrayList();
        StringParameterBO stringParam = new StringParameterBO();
        stringParam.setValue( JSP_SOURCES_DIR );
        list.add( stringParam );
        listParam.setParameters( list );
        params.getParameters().put( ParametersConstants.JSP, listParam );
        // Teste avec le Dialect 1.4 de java
        StringParameterBO dialect = new StringParameterBO();
        dialect.setValue( ParametersConstants.JAVA1_4 );
        params.getParameters().put( ParametersConstants.DIALECT, dialect );
        // Chemin vers le répertoire Web
        StringParameterBO webapp = new StringParameterBO();
        webapp.setValue( "data/samples/testWeb/WebContent" );
        params.getParameters().put( ParametersConstants.WEB_APP, webapp );
        // Teste avec la version 1.4 du j2ee
        StringParameterBO j2ee = new StringParameterBO();
        j2ee.setValue( ParametersConstants.J2EE1_4 );
        params.getParameters().put( ParametersConstants.J2EE_VERSION, j2ee );
        // Prise en compte des répertoires exclus
        ListParameterBO listJspParam = new ListParameterBO();
        List jspList = new ArrayList();
        StringParameterBO stringJspParam = new StringParameterBO();
        stringJspParam.setValue( EXLUDED_DIR );
        jspList.add( stringJspParam );
        listJspParam.setParameters( jspList );
        params.getParameters().put( ParametersConstants.JSP_EXCLUDED_DIRS, listJspParam );
        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Teste l'exécution de la tâche
     * 
     * @throws Exception si erreur
     */
    public void testExecute()
        throws Exception
    {
        // Les paramètres temporaires
        // Le view path
        File viewFile = new File( VIEW_PATH );
        mData.putData( TaskData.VIEW_PATH, viewFile.getCanonicalPath() );
        // le répertoire contenant les .class du projet
        File classesDir = new File( "data/samples/bin_for_test" );
        List classesDirs = new ArrayList();
        classesDirs.add( classesDir.getCanonicalPath() );
        mData.putData( TaskData.CLASSES_DIRS, classesDirs );
        // le classpath
        String j2eeJar = new File( "../squalix/lib/compiling_ressources/java/1_4/j2ee.jar" ).getAbsolutePath();
        String jasperCompilerJar = new File( "../squalix/lib/jspcompiling/jasper-compiler.jar" ).getAbsolutePath();
        String jasperRuntimeJar = new File( "../squalix/lib/jspcompiling/jasper-runtime.jar" ).getAbsolutePath();
        String struts = new File( "../squaleWeb/WebContent/WEB-INF/lib/struts-1.1-p1.jar" ).getAbsolutePath();
        String classpath =
            classesDir.getCanonicalPath() + ";" + j2eeJar + ";" + jasperCompilerJar + ";" + jasperRuntimeJar + ";"
                + struts;
        mData.putData( TaskData.CLASSPATH, classpath );
        JspCompilingTask task = new JspCompilingTask();
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );
        // Le nombre de .class doit être égal au nombre de .jsp - le nombre de fichier
        // contenu dans le répertoire exclu.
        ArrayList jsps = FileManager.checkFileNumber( JSP_SOURCES_DIR, ".jsp" );
        ArrayList classes =
            FileManager.checkFileNumber( (String) task.getData().getData( TaskData.JSP_CLASSES_DIR ), ".class" );
        ArrayList jspsExcluded = FileManager.checkFileNumber( JSP_SOURCES_DIR + "/" + EXLUDED_DIR, ".jsp" );
        assertEquals( jsps.size() - jspsExcluded.size(), classes.size() );
    }

    /**
     * Test task execution with a missing parameter
     * 
     * @throws JrafDaoException if error
     */
    public void testExecuteWithoutParams()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        mProject.getParameters().getParameters().remove( ParametersConstants.J2EE_VERSION );
        ProjectParameterDAOImpl.getInstance().save( getSession(), mProject.getParameters() );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        getSession().commitTransactionWithoutClose();
        JspCompilingTask task = new JspCompilingTask();
        mData.putData( TaskData.CLASSPATH, "" );
        mData.putData( TaskData.VIEW_PATH, "" );
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertTrue( task.getStatus() == AbstractTask.FAILED );
    }

}
