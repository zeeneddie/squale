package com.airfrance.squalix.tools.mccabe;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.core.TaskException;

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
         * @see com.airfrance.squalix.tools.mccabe.AbstractMcCabeTask#createProjectConfigurationFile(com.airfrance.squalix.tools.mccabe.McCabePCFFile)
         */
        protected void createProjectConfigurationFile( McCabePCFFile pFile )
            throws Exception
        {
        }

        /**
         * (non-Javadoc)
         * 
         * @see com.airfrance.squalix.tools.mccabe.AbstractMcCabeTask#createReport(java.lang.String)
         */
        protected void createReport( String pReport )
            throws Exception
        {
        }

        /**
         * (non-Javadoc)
         * 
         * @see com.airfrance.squalix.tools.mccabe.CppMcCabeTask#doCompilation(java.io.File)
         */
        protected void doCompilation()
            throws TaskException
        {
        }

        /**
         * (non-Javadoc)
         * 
         * @see com.airfrance.squalix.tools.mccabe.AbstractMcCabeTask#parseSource()
         */
        protected int parseSource()
            throws Exception
        {
            return 0;
        }

        /**
         * (non-Javadoc)
         * 
         * @see com.airfrance.squalix.tools.mccabe.AbstractMcCabeTask#computeReportFileName(java.lang.String)
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
