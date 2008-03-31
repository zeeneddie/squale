/*
 * Créé le 2 août 05, par M400832.
 */
package com.airfrance.squalix.tools.compiling.java;

import java.util.ArrayList;
import java.util.List;

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
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.tools.compiling.utility.FileManager;

/**
 * Il faut penser à modifier les clés javac.executable.1_5 et java.executable.1_5 dans le fichier compiling.properties
 * pour indiquer le chemin vers java et javac en local.
 */
public class JCompilingTaskTest
    extends SqualeTestCase
{

    /**
     * Projet.
     */
    private ProjectBO mProject = null;

    /**
     * Tâche de compilation JAVA.
     */
    private JCompilingTask mTask = null;

    /**
     * Répertoire racine contenant la vue.
     */
    private String mRootDir;

    /**
     * Répertoire destination des .class
     */
    private String mDestDir;

    /**
     * Extension à matcher.
     */
    private static final String CLASS_EXTENSION = ".class";

    /**
     * Extension à matcher.
     */
    private static final String JAVA_EXTENSION = ".java";

    /**
     * Constructeur.
     * 
     * @param pArg argument.
     */
    public JCompilingTaskTest( String pArg )
    {
        super( pArg );
    }

    /**
     * Set-up.
     * 
     * @throws Exception en cas d'échec
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        /* on crée le projet */
        ApplicationBO appli = getComponentFactory().createApplication( getSession() );
        mProject = getComponentFactory().createProject( getSession(), appli, null );
        mProject.setParameters( new MapParameterBO() );
        AuditBO audit = getComponentFactory().createAudit( getSession(), mProject );

        /* on crée la chaîne de compilation */
        ListParameterBO projectList = new ListParameterBO();

        StringParameterBO projet1 = new StringParameterBO();
        StringParameterBO projet2 = new StringParameterBO();
        StringParameterBO projet3 = new StringParameterBO();
        projet1.setValue( "testBatch" );
        projet2.setValue( "testCommon" );
        projet3.setValue( "testWeb" );

        projectList.getParameters().add( projet1 ); // Premier projet, localisation et séparateurs
        projectList.getParameters().add( projet2 ); // 2ieme projet
        projectList.getParameters().add( projet3 );

        /* répertoire contenant la vue snapshot */
        mRootDir = "./data/samples/";
        String mSrcDir = "testBatch";
        String mSrcDir2 = "testWeb";
        /* répertoire destination des .class */
        mDestDir = "./data/samples/bin/";
        TaskData data = new TaskData();
        // Teste avec le Dialect 1.4 de java
        StringParameterBO dialect = new StringParameterBO();
        dialect.setValue( ParametersConstants.JAVA1_4 );

        mTask = new JCompilingTask();

        ListParameterBO listSrcBO = new ListParameterBO();
        StringParameterBO stringSrcBO1 = new StringParameterBO();
        stringSrcBO1.setValue( projet1.getValue() + "/" );
        StringParameterBO stringSrcBO3 = new StringParameterBO();
        stringSrcBO3.setValue( projet3.getValue() + "/" );
        listSrcBO.getParameters().add( stringSrcBO3 );

        data.putData( TaskData.VIEW_PATH, mRootDir );
        mTask.setData( data );

        /* on crée la hashmap de paramètres */
        mProject.getParameters().getParameters().put( ParametersConstants.WSAD, projectList );
        mProject.getParameters().getParameters().put( ParametersConstants.DIALECT, dialect );
        mProject.getParameters().getParameters().put( ParametersConstants.SOURCES, listSrcBO );

        // On compile avec javac et pas avec le plugin eclipse
        MapParameterBO eclipse = new MapParameterBO();
        StringParameterBO isEclipse = new StringParameterBO();
        isEclipse.setValue( "false" );
        eclipse.getParameters().put( ParametersConstants.ECLIPSE_COMPILATION, isEclipse );
        mProject.getParameters().getParameters().put( ParametersConstants.ECLIPSE, eclipse );

        // on sauve la modification sur les params
        ProjectParameterDAOImpl.getInstance().create( getSession(), mProject.getParameters() );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        mTask.setProjectId( new Long( mProject.getId() ) );
        mTask.setAuditId( new Long( audit.getId() ) );
        mTask.setApplicationId( new Long( appli.getId() ) );
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Lance le test.
     * 
     * @throws JrafDaoException en cas de problèmes avec la gestion de la base
     */
    public void testCompilation()
        throws JrafDaoException
    {
        ArrayList classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        FileManager.removeFiles( classFileList );
        classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        assertEquals( classFileList.size(), 0 );
        mTask.run();
        classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        ArrayList javaFileList = FileManager.checkFileNumber( mRootDir, JAVA_EXTENSION );
        assertTrue( javaFileList.size() != 0 );
        // Il y a 10 fichiers java donnés mais avec les dépendances
        // et les classes internes ça fait en fait 12 fichiers compilés
        final int nbFiles = 10;
        assertEquals( javaFileList.size(), nbFiles );
        final int nbCompiledFiles = 13;
        assertEquals( nbCompiledFiles, classFileList.size() );
    }

    /**
     * Lance le test avec exclusion.
     * 
     * @throws JrafDaoException en cas de problèmes avec la gestion de la base
     */
    public void testCompilationWithExclusion()
        throws JrafDaoException
    { // On exclu des répertoires de la compilation
        // On exclu testExcluded
        ListParameterBO listExcluded = new ListParameterBO();
        List list = new ArrayList();
        StringParameterBO dir1 = new StringParameterBO();
        dir1.setValue( "testWeb/JavaSource/testExcluded" );
        list.add( dir1 );
        listExcluded.setParameters( list );
        mProject.getParameters().getParameters().put( ParametersConstants.EXCLUDED_DIRS, listExcluded );
        getSession().beginTransaction();
        // on sauve la modification sur les params et le projet
        // après l'ajout des répertoires exclus
        ProjectParameterDAOImpl.getInstance().save( getSession(), mProject.getParameters() );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();

        ArrayList classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        FileManager.removeFiles( classFileList );
        classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        assertEquals( classFileList.size(), 0 );
        mTask.run();
        classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        ArrayList javaFileList = FileManager.checkFileNumber( mRootDir, JAVA_EXTENSION );
        assertTrue( javaFileList.size() != 0 );
        // Il y a 12 fichiers java donnés
        // plus que 13 fichiers compilés car il manque la classe du répertoire testExcluded
        final int nbFiles = 12;
        assertEquals( nbFiles, javaFileList.size() );
        final int nbCompiledFiles = 13;
        assertEquals( nbCompiledFiles, classFileList.size() );
    }

    /**
     * Test la compilation avec des projets crées sous RSA
     * 
     * @throws JrafDaoException si erreur JRAF
     */
    public void testRSACompilation()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        // On ajoute les paramètres RSA
        MapParameterBO params = mProject.getParameters();
        params.getParameters().remove( ParametersConstants.WSAD );
        /* on crée la chaîne de compilation */
        ListParameterBO projectsList = new ListParameterBO();
        ListParameterBO firstList = new ListParameterBO();
        StringParameterBO firstWs = new StringParameterBO();
        firstWs.setValue( "testBatch" );
        firstList.getParameters().add( firstWs );
        projectsList.getParameters().add( firstList ); // Premier projet, localisation et séparateurs
        ListParameterBO secondList = new ListParameterBO();
        StringParameterBO secondWs = new StringParameterBO();
        secondWs.setValue( "testCommon" );
        secondList.getParameters().add( secondWs );
        projectsList.getParameters().add( secondList ); // 2ieme projet
        ListParameterBO thirdList = new ListParameterBO();
        StringParameterBO thirdWs = new StringParameterBO();
        thirdWs.setValue( "TestWebEAR" );
        thirdList.getParameters().add( thirdWs );
        projectsList.getParameters().add( thirdList ); // EAR
        ListParameterBO fourthList = new ListParameterBO();
        StringParameterBO fourthWs = new StringParameterBO();
        fourthWs.setValue( "testWeb" );
        fourthList.getParameters().add( fourthWs );
        StringParameterBO ear = new StringParameterBO();
        ear.setValue( "TestWebEAR" );
        fourthList.getParameters().add( ear );
        StringParameterBO manifest = new StringParameterBO();
        manifest.setValue( "WebContent/META-INF/MANIFEST.mf" );
        fourthList.getParameters().add( manifest );
        projectsList.getParameters().add( fourthList ); // le projet
        params.getParameters().put( ParametersConstants.RSA, projectsList );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        getSession().commitTransactionWithoutClose();
        mDestDir = "./data/QuickTestRSA/bin/";
        TaskData data = new TaskData();
        data.putData( TaskData.VIEW_PATH, "./data/QuickTestRSA/" );
        mTask.setData( data );
        testCompilation();
    }

    /**
     * Lance le test dans le cas d'un projet RCP avec un bundle eclipse particulier.
     * 
     * @throws JrafDaoException en cas de problèmes avec la gestion de la base
     */
    public void testRCPCompilation()
        throws JrafDaoException
    {
        // On change le view path
        TaskData data = new TaskData();
        data.putData( TaskData.VIEW_PATH, "./data/" );
        mTask.setData( data );

        // On modifie la liste des projets
        ListParameterBO projectList =
            (ListParameterBO) mProject.getParameters().getParameters().get( ParametersConstants.WSAD );
        projectList.getParameters().removeAll( projectList.getParameters() );
        StringParameterBO projet = new StringParameterBO();
        projet.setValue( "Project4RCPCompilationTest" );
        projectList.getParameters().add( projet );
        mProject.getParameters().getParameters().put( ParametersConstants.WSAD, projectList );

        // On ajoute le bundle
        StringParameterBO bundle = new StringParameterBO();
        bundle.setValue( "Project4RCPCompilationTest/bundle.zip" );
        mProject.getParameters().getParameters().put( ParametersConstants.BUNDLE_PATH, bundle );
        getSession().beginTransaction();

        // On change le répertoire des sources
        mRootDir = "./data/Project4RCPCompilationTest/src";
        // On change le répertoire de destination des .class
        mDestDir = "./data/bin";

        // on sauve la modification sur les params
        ProjectParameterDAOImpl.getInstance().save( getSession(), mProject.getParameters() );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();

        ArrayList classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        FileManager.removeFiles( classFileList );
        classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        assertEquals( classFileList.size(), 0 );
        mTask.run();
        classFileList = FileManager.checkFileNumber( mDestDir, CLASS_EXTENSION );
        ArrayList javaFileList = FileManager.checkFileNumber( mRootDir, JAVA_EXTENSION );
        assertTrue( javaFileList.size() != 0 );
        // Il y a 1 fichier java
        final int nbFiles = 1;
        assertEquals( javaFileList.size(), nbFiles );
        final int nbCompiledFiles = 1;
        assertEquals( nbCompiledFiles, classFileList.size() );

        // On teste la récupération des erreurs lorsqu'un plugin n'est pas trouvé
        mTask.setStatus( AbstractTask.NOT_ATTEMPTED );
        bundle.setValue( "Project4RCPCompilationTest/src" );
        mProject.getParameters().getParameters().put( ParametersConstants.BUNDLE_PATH, bundle );
        getSession().beginTransaction();
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
        getSession().commitTransactionWithoutClose();
        mTask.run();
        assertEquals( AbstractTask.FAILED, mTask.getStatus() );
    }
}
