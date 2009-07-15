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
package com.airfrance.squalix.tools.jdepend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jdepend.framework.JDepend;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalix.core.TaskData;

/**
 */
public class JDependTaskTest
    extends SqualeTestCase
{

    /** la tache JDepend */
    private JDependTask mTask;

    /** les paramètres à utiliser */
    private TaskData mDatas;

    /**
     * constructeur
     */
    public JDependTaskTest()
    {
        super();
    }

    /** la map de paramètres */
    private MapParameterBO mMap = new MapParameterBO();

    /** le projet */
    private ProjectBO mProject;

    /**
     * Set-up
     * 
     * @throws Exception en cas de problèmes (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();

        getSession().beginTransaction();
        ApplicationBO appli = getComponentFactory().createApplication( getSession() );
        mProject = getComponentFactory().createProject( getSession(), appli, null );
        AuditBO audit = getComponentFactory().createAudit( getSession(), mProject );
        getSession().commitTransactionWithoutClose();

        ListParameterBO listSrcs = new ListParameterBO();
        List list2 = new ArrayList();
        StringParameterBO dirSrc = new StringParameterBO();
        dirSrc.setValue( "testWeb" );
        list2.add( dirSrc );
        listSrcs.setParameters( list2 );
        mMap.getParameters().put( ParametersConstants.SOURCES, listSrcs );
        mProject.setParameters( mMap );
        mProject.setName( "testWeb" );

        getSession().beginTransaction();
        // on sauve la modification sur les params et le projet
        // après l'ajout des répertoires exclus
        ProjectParameterDAOImpl.getInstance().save( getSession(), mMap );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();

        mDatas = new TaskData();
        // pour un test plus conséquent
        // mDatas.putData(TaskData.CLASSES_DIR, "./bin/");
        // mDatas.putData(TaskData.CLASSES_DIR, "./data/Project4McCabeTest/bin/");
        List classesDirs = new ArrayList();
        classesDirs.add( "./data/samples/bin_for_test" );
        mDatas.putData( TaskData.CLASSES_DIRS, classesDirs );
        mDatas.putData( TaskData.VIEW_PATH, new File( "./data/samples/" ).getCanonicalPath() );

        mTask = new JDependTask();
        mTask.setData( mDatas );
        mTask.setApplicationId( new Long( appli.getId() ) );
        mTask.setProjectId( new Long( mProject.getId() ) );
        mTask.setAuditId( new Long( audit.getId() ) );
    }

    /**
     * Teste que le nombre de composants enregistré correspond au nombre de packages attendus
     */
    public void testMatch()
    {
        try
        {
            int nb = MeasureDAOImpl.getInstance().count( getSession() ).intValue();
            getSession().beginTransaction();
            mTask.run();
            getSession().commitTransactionWithoutClose();
            int nbAfter = MeasureDAOImpl.getInstance().count( getSession() ).intValue();
            // Avec le chemin qu'on passe en paramètre, 2 est la valeur attendue
            assertEquals( 2, nbAfter - nb );
            List col = MeasureDAOImpl.getInstance().findAll( getSession() );
            assertNotNull( col );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Teste que le nombre de composants enregistré correspond au nombre de packages attendus
     * 
     * @throws JrafDaoException en cas d'échec
     */
    public void testMatchWithExcludedDirs()
        throws JrafDaoException
    {
        ListParameterBO listExcluded = new ListParameterBO();
        List list = new ArrayList();
        StringParameterBO dir1 = new StringParameterBO();
        dir1.setValue( "JavaSource/testExcluded" );
        list.add( dir1 );
        listExcluded.setParameters( list );
        mMap.getParameters().put( ParametersConstants.EXCLUDED_DIRS, listExcluded );

        ListParameterBO listIncluded = new ListParameterBO();
        List listI = new ArrayList();
        StringParameterBO include1 = new StringParameterBO();
        include1.setValue( "**/*.java" );
        listI.add( include1 );
        listIncluded.setParameters( listI );
        mMap.getParameters().put( ParametersConstants.INCLUDED_PATTERNS, listIncluded );

        getSession().beginTransaction();
        // on sauve la modification sur les params et le projet
        // après l'ajout des répertoires exclus
        ProjectParameterDAOImpl.getInstance().save( getSession(), mMap );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();

        try
        {
            int nb = MeasureDAOImpl.getInstance().count( getSession() ).intValue();
            getSession().beginTransaction();
            mTask.run();
            getSession().commitTransactionWithoutClose();
            int nbAfter = MeasureDAOImpl.getInstance().count( getSession() ).intValue();
            // Avec le chemin qu'on passe en paramètre, 2 serait la valeur attendue
            // mais avec l'exclusion des répertoires on doit passer à 1
            assertEquals( 1, nbAfter - nb );
            List col = MeasureDAOImpl.getInstance().findAll( getSession() );
            assertNotNull( col );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * /** Vérifie que le cycle qui existe pour les packages donnés est bien répertorié
     */
    public void testAllPackages()
    {
        mTask.run();
        JDepend jDepend = mTask.getJDepend();
        assertEquals( "Cycles exist", true, jDepend.containsCycles() );
    }

}
