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
package com.airfrance.squalix.tools.ckjm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;

/**
 * Test de la tâche ckjm
 */
public class CkjmTaskTest
    extends SqualeTestCase
{

    /** répertoire des .class */
    public static final String CLASSES_DIR = "bin/com/airfrance/squalix/tools/mccabe";

    /** répertoire des .java */
    public static final String SOURCES_DIR = "src";

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

    /**
     * Constructeur pour CkjmTaskTest.
     * 
     * @param arg0 nom
     */
    public CkjmTaskTest( String arg0 )
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
        ListParameterBO listParam = new ListParameterBO();
        List list = new ArrayList();
        StringParameterBO stringParam = new StringParameterBO();
        stringParam.setValue( SOURCES_DIR );
        list.add( stringParam );
        listParam.setParameters( list );
        params.getParameters().put( ParametersConstants.SOURCES, listParam );
        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Vérifie la correcte exécution de la tâche ckjm avec exclusions.
     * 
     * @throws JrafDaoException si erreur
     * @throws IOException si erreur
     */
    public void testRunWithExclude()
        throws JrafDaoException, IOException
    {
        // On crée les exclusion
        getSession().beginTransaction();
        MapParameterBO params = mProject.getParameters();
        // Les patterns à inclure
        ListParameterBO includedList = new ListParameterBO();
        ArrayList iList = new ArrayList();
        StringParameterBO stringInclude = new StringParameterBO();
        stringInclude.setValue( "**/All*.java" );
        iList.add( stringInclude );
        includedList.setParameters( iList );
        params.getParameters().put( ParametersConstants.INCLUDED_PATTERNS, includedList );

        // Les patterns à exclure
        ListParameterBO excludedList = new ListParameterBO();
        ArrayList eList = new ArrayList();
        StringParameterBO stringExclude = new StringParameterBO();
        stringExclude.setValue( "**/mccabe/McCabe/**" );
        eList.add( stringExclude );
        excludedList.setParameters( eList );
        params.getParameters().put( ParametersConstants.EXCLUDED_PATTERNS, excludedList );

        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        getSession().commitTransactionWithoutClose();

        CkjmTask task = new CkjmTask();
        File viewFile = new File( VIEW_PATH );
        mData.putData( TaskData.VIEW_PATH, viewFile.getCanonicalPath() );
        List classesDirs = new ArrayList();
        classesDirs.add( CLASSES_DIR );
        mData.putData( TaskData.CLASSES_DIRS, classesDirs );
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );
        try
        {
            // On charge les classe analysées:
            Collection children =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            ClassBO.class );
            File classesDir = new File( CLASSES_DIR );
            // il en reste 1 dans le répertoire (AllTests.java)
            assertEquals( 1, children.size() );
            assertTrue( children.size() > 0 );
            // On récupère les mesures pour la première classe:
            ClassBO aClass = (ClassBO) children.iterator().next();
            Collection measures =
                MeasureDAOImpl.getInstance().findWhere( getSession(), new Long( aClass.getId() ), task.getAuditId() );
            assertEquals( 1, measures.size() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Vérifie la correcte exécution de la tâche ckjm sans exclusion.
     * 
     * @throws JrafDaoException si erreur
     * @throws IOException si erreur
     */
    public void testRun()
        throws JrafDaoException, IOException
    {
        CkjmTask task = new CkjmTask();
        File viewFile = new File( VIEW_PATH );
        mData.putData( TaskData.VIEW_PATH, viewFile.getCanonicalPath() );
        List classesDirs = new ArrayList();
        classesDirs.add( CLASSES_DIR );
        mData.putData( TaskData.CLASSES_DIRS, classesDirs );
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );
        try
        {
            // On charge les classe analysées:
            Collection children =
                AbstractComponentDAOImpl.getInstance().findProjectChildren( getSession(), mProject, mAudit,
                                                                            ClassBO.class );
            File classesDir = new File( CLASSES_DIR );
            assertEquals( classesDir.list().length, children.size() );
            assertTrue( children.size() > 0 );
            // On récupère les mesures pour la première classe:
            ClassBO aClass = (ClassBO) children.iterator().next();
            Collection measures =
                MeasureDAOImpl.getInstance().findWhere( getSession(), new Long( aClass.getId() ), task.getAuditId() );
            assertEquals( 1, measures.size() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
