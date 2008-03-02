package com.airfrance.squalix.tools.sourcecodeanalyser;

import java.io.File;

import junit.framework.TestCase;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.tools.compiling.utility.FileManager;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Test pour la récupération des sources via une arborescence de fichiers
 */
public class SourceCodeAnalyserTaskTest
    extends SqualeTestCase
{

    /** Le zip contenant les sources et les .class */
    public static final String ZIP_DIR = "data/Project4AnalyserTest/testCommon.zip";

    /** Le répertoire contenant les sources, les .class, le .classpath, le .project */
    public static final String ROOT_DIR = "data/samples/testCommon";

    /** l'application */
    private ApplicationBO mAppli = new ApplicationBO();

    /** le projet à auditer */
    private ProjectBO mProject = new ProjectBO();

    /** l'audit */
    private AuditBO mAudit = new AuditBO();

    /** Les paramètres temporaires */
    private TaskData mData = new TaskData();

    /**
     * Constructeur pour SourceCodeAnalyserTaskTest.
     * 
     * @param arg0 nom
     */
    public SourceCodeAnalyserTaskTest( String arg0 )
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
        MapParameterBO analyserParams = new MapParameterBO();
        StringParameterBO zipParam = new StringParameterBO();
        zipParam.setValue( ROOT_DIR );
        analyserParams.getParameters().put( ParametersConstants.PATH, zipParam );
        params.getParameters().put( ParametersConstants.ANALYSER, analyserParams );
        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();
    }

    /**
     * @param path le chemin du projet
     * @throws JrafDaoException si erreur
     */
    private void setParameters( String path )
        throws JrafDaoException
    {
        // Enregistrement du ProjectBO dans la base
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
        MapParameterBO params = new MapParameterBO();
        MapParameterBO analyserParams = new MapParameterBO();
        StringParameterBO zipParam = new StringParameterBO();
        zipParam.setValue( path );
        analyserParams.getParameters().put( ParametersConstants.PATH, zipParam );
        params.getParameters().put( ParametersConstants.ANALYSER, analyserParams );
        ProjectParameterDAOImpl.getInstance().create( getSession(), params );
        mProject.setParameters( params );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mAudit = getComponentFactory().createAudit( this.getSession(), mProject );
    }

    /**
     * On teste le cas d'un projet compressé
     * 
     * @throws JrafDaoException si erreur
     */
    public void testAnalyseWithZip()
        throws JrafDaoException
    {
        setParameters( ZIP_DIR );
        SourceCodeAnalyserTask task = new SourceCodeAnalyserTask();
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );
        File root = new File( "data/Project4AnalyserTest/source_code_analyser/" );
        File project = new File( root, "testCommon" );
        assertTrue( project.exists() );
        final int nbDir = 2;
        assertEquals( nbDir, FileManager.checkFileNumber( project.getAbsolutePath(), ".java" ).size() );
        FileUtility.deleteRecursively( root );
    }

    /**
     * On teste le cas d'un répertoire non compressé
     * 
     * @throws JrafDaoException si erreur
     */
    public void testAnalyseWithDir()
        throws JrafDaoException
    {
        setParameters( ROOT_DIR );
        SourceCodeAnalyserTask task = new SourceCodeAnalyserTask();
        task.setData( mData );
        task.setAuditId( new Long( mAudit.getId() ) );
        task.setProjectId( new Long( mProject.getId() ) );
        task.setApplicationId( new Long( mAppli.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.run();
        assertEquals( AbstractTask.TERMINATED, task.getStatus() );
        File root = new File( "data/Project4AnalyserTest/source_code_analyser/" );
        File project = new File( root, "testCommon" );
        assertTrue( project.exists() );
        final int nbDir = 5;
        assertEquals( nbDir, project.listFiles().length );
        FileUtility.deleteRecursively( root );
    }

}
