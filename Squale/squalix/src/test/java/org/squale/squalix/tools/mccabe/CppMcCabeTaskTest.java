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
package org.squale.squalix.tools.mccabe;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import org.squale.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.TaskException;

/**
 * Test de la tâche McCabe pour le C++ Le test consiste à vérifier le traitement de données générées par McCabe. Ces
 * données ont été générées à partir du programme de test disponible dans le répertoire data/Project4McCabeCppTest Ces
 * donénes ont été archivées dans le répertoire data/mccabe/cpp
 */
public class CppMcCabeTaskTest
    extends SqualeTestCase
{
    /** Données de la tâche */
    private TaskData mTaskData;

    /** Audit */
    private AuditBO mAudit;

    /** Projet */
    private ProjectBO mProject;

    /** Application */
    private ApplicationBO mAppli;

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        // Création des données nécessaires au traitement
        mAppli = getComponentFactory().createApplication( getSession() );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        ProjectProfileBO profileBO = getComponentFactory().createProjectProfile( getSession() );
        profileBO.setName( "cpp" );
        ProjectProfileDAOImpl.getInstance().save( getSession(), profileBO );
        // Création des paramètres
        MapParameterBO projectParams = new MapParameterBO();
        StringParameterBO dialect = new StringParameterBO();
        dialect.setValue( "Forte" );
        projectParams.getParameters().put( ParametersConstants.DIALECT, dialect );
        MapParameterBO cppParams = new MapParameterBO();
        StringParameterBO cppScript = new StringParameterBO();
        cppScript.setValue( "test" );
        cppParams.getParameters().put( ParametersConstants.CPP_SCRIPTFILE, cppScript );
        projectParams.getParameters().put( ParametersConstants.CPP, cppParams );
        ListParameterBO srcs = new ListParameterBO();
        projectParams.getParameters().put( ParametersConstants.SOURCES, srcs );
        StringParameterBO srcDir = new StringParameterBO();
        srcs.getParameters().add( srcDir );
        srcDir.setValue( "/vobs/squale/src/squalixTest/data/Project4McCabeCppTest" );
        ProjectParameterDAOImpl.getInstance().create( getSession(), projectParams );
        mProject = getComponentFactory().createProject( getSession(), mAppli, grid, profileBO, null, projectParams );
        mProject.setParameters( projectParams );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );
        getSession().commitTransactionWithoutClose();

        // Données liées à la tâche
        mTaskData = new TaskData();
        mTaskData.putData( TaskData.VIEW_PATH,
                           "/app/SQUALE/dev/data/cc_snapshot/squale_v2_0_act_quicktest_testcpp_squaledev" );
    }

    /**
     * Test de la méthode execute
     */
    public void testExecute()
    {
        CppMcCabeTaskStub task = new CppMcCabeTaskStub();
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setData( mTaskData );
        try
        {
            task.run();
            assertEquals( AbstractTask.TERMINATED, task.getStatus() );
            // On récupère la liste des composants créés
            Collection comps = AbstractComponentDAOImpl.getInstance().findAll( getSession() );
            assertNotNull( comps );
            // On récupère la liste des mesure créées
            List measures = MeasureDAOImpl.getInstance().findAll( getSession() );
            assertNotNull( measures );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Stub de la tâche CppMcCabe
     */
    class CppMcCabeTaskStub
        extends CppMcCabeTask
    {
        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#createProjectConfigurationFile(org.squale.squalix.tools.mccabe.McCabePCFFile)
         */
        protected void createProjectConfigurationFile( McCabePCFFile pFile )
            throws Exception
        {
        }

        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#createReport(java.lang.String)
         */
        protected void createReport( String pReport )
            throws Exception
        {
        }

        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.CppMcCabeTask#doCompilation(java.io.File)
         */
        protected void doCompilation()
            throws TaskException
        {
        }

        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#parseSource()
         */
        protected int parseSource()
            throws Exception
        {
            return 0;
        }

        /**
         * (non-Javadoc)
         * 
         * @see org.squale.squalix.tools.mccabe.AbstractMcCabeTask#computeReportFileName(java.lang.String)
         */
        protected String computeReportFileName( String pReport )
            throws IOException
        {
            String result = null;
            if ( pReport.startsWith( "CLASSES" ) )
            {
                result = "data/mccabe/cpp/CLASSES.CSV";
            }
            else if ( pReport.startsWith( "METHODES" ) )
            {
                result = "data/mccabe/cpp/METHODES.CSV";
            }
            return result;
        }

    }
}
