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
package org.squale.squalix.tools.javancss;

/*
 * Some parts of the code are inspired by the MOJO javancss plug-in : 
 * http://mojo.codehaus.org/javancss-maven-plugin/ 

 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javancss.Javancss;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComplexComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssClassMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssMethodMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssPackageMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.javancss.JavancssProjectMetricsBO;
import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.util.buildpath.BuildProjectPath;
import org.squale.squalix.util.file.FileUtility;
import org.squale.squalix.util.methodBO.MethodBOHelper;
import org.squale.squalix.util.parser.JavaParser;
import org.squale.squalix.util.repository.ComponentRepository;

/**
 * This Class is the task for the javancss tool.
 */
public class JavancssTask
    extends AbstractTask
{

    /** Logger */
    private static final Log LOGGER = LogFactory.getLog( JavancssTask.class );

    /** Javancss configuration */
    private JavancssConfig configuration;

    /** java extension file */
    public static final String[] JAVA_FILE_EXTENSION = { ".java" };

    /** java parser */
    private JavaParser parser;

    /** repository of component */
    private ComponentRepository repository;

    /** path to the project view */
    private String viewPath;

    /** list of sources */
    private List sources;

    /** list of sources file to parse (take into account inclusion and exclusion) */
    private List includedFileNames = new ArrayList( 0 );

    /** The javancss results */
    private JavancssResult parsingResult;

    /** Path of the .xml report */
    private String outputFilename;

    /** Number of classes */
    private int numberOfClasses;

    /** Map of classes results to persist */
    private Map<String, JavancssClassMetricsBO> classMap = new HashMap<String, JavancssClassMetricsBO>();

    /** Map of package results to persist */
    private Map<String, JavancssPackageMetricsBO> packageMap = new HashMap<String, JavancssPackageMetricsBO>();

    /** List of method results to persist */
    private List<JavancssMethodMetricsBO> methodList = new ArrayList<JavancssMethodMetricsBO>();

    /**
     * Defaults constructor
     */
    public JavancssTask()
    {
        mName = "JavancssTask";

    }

    /**
     * Method call by the task launcher. It execute the task : initialization, execution of javancss and record of the
     * result in the database
     * 
     * @throws TaskException All exception happen during the task
     */
    public void execute()
        throws TaskException
    {
        try
        {
            initialize();
            if ( mStatus == RUNNING )
            {
                LOGGER.info( JavancssMessages.getString( "javancss.initialize.end" ) );
                doJavaNcss();
            }
            if ( mStatus == RUNNING )
            {
                LOGGER.info( JavancssMessages.getString( "javancss.toolexecution.end" ) );
                persisteMeasures();
            }
        }
        catch ( ConfigurationException e )
        {
            throw new TaskException( e );
        }

        catch ( JrafDaoException e )
        {
            throw new TaskException( e );
        }

        finally
        {
            // Deletion of the file result
            if ( outputFilename != null )
            {
                File xmlResult = new File( outputFilename );
                if ( xmlResult.exists() )
                {
                    affectFileSystemSize( xmlResult, false );
                    xmlResult.delete();
                }
            }
        }
    }

    /**
     * Initialization of the Javanccs task. Create the parser and repository object. Get the configuration parameters
     * sources and viewpath. Create the list of classes to analyze (take into account inclusion and exclusion).
     * 
     * @throws ConfigurationException Exception happen if some element configuration (sources or viwpath) are missing.
     */
    private void initialize()
        throws ConfigurationException
    {
        parser = new JavaParser( mProject );
        repository = new ComponentRepository( mProject, getSession() );
        config();
        fillKnownClasses();
        sources =
            (List) ( (ListParameterBO) mProject.getParameters().getParameters().get( ParametersConstants.SOURCES ) ).getParameters();

        if ( sources == null )
        {
            String message =
                JavancssMessages.getString( "javancss.exception.configuration.sources_not_found" )
                    + ParametersConstants.SOURCES;
            initError( message );
            LOGGER.error( message );
            mStatus = CANCELLED;
        }
        else
        {
            viewPath = (String) mData.getData( TaskData.VIEW_PATH );
            if ( null == viewPath )
            {
                String message =
                    JavancssMessages.getString( "javancss.exception.configuration.viewpath_not_found" )
                        + TaskData.VIEW_PATH;
                initError( message );
                LOGGER.error( message );
                mStatus = CANCELLED;
            }
            else
            {
                includedFileNames =
                    FileUtility.getIncludedFiles(
                                                  viewPath,
                                                  BuildProjectPath.buildProjectPath( viewPath, sources ),
                                                  (ListParameterBO) mProject.getParameter( ParametersConstants.INCLUDED_PATTERNS ),
                                                  (ListParameterBO) mProject.getParameter( ParametersConstants.EXCLUDED_PATTERNS ),
                                                  (ListParameterBO) mProject.getParameter( ParametersConstants.EXCLUDED_DIRS ),
                                                  JAVA_FILE_EXTENSION );
            }
        }
    }

    /**
     * This method do the configuration of javancss. That means get back the path for the result file.
     * 
     * @throws ConfigurationException Exception happen if the xml configuration file is not found.
     */
    private void config()
        throws ConfigurationException
    {
        configuration = new JavancssConfig();
        try
        {
            configuration.parse( new FileInputStream( "config/javancss-config.xml" ) );
            outputFilename = configuration.getResultFilePath();
            File outputFile = new File( outputFilename );
            File ouputDirectory = outputFile.getParentFile();
            if ( !ouputDirectory.exists() )
            {
                ouputDirectory.mkdir();
            }
        }
        catch ( FileNotFoundException e )
        {
            String message = JavancssMessages.getString( "javancss.exception.configuration.xml_file_not_found" );
            initError( message );
            LOGGER.error( message );
            mStatus = CANCELLED;
        }
    }

    /**
     * This method use the repository for fill the mknownClasses of the parser
     */
    private void fillKnownClasses()
    {
        Set allClasses = ( (Map) repository.getClasses() ).keySet();
        Iterator it = allClasses.iterator();
        while ( it.hasNext() )
        {
            String pathClas = it.next().toString().replaceAll( "<=>", "." );
            parser.addKnownClass( pathClas );
        }
    }

    /**
     * This methods launch the tool javancss and do the parsing of the result file.
     * 
     * @throws TaskException Exception due to problem of result file creation/existence/reading
     */
    private void doJavaNcss()
        throws TaskException
    {

        String[] args = getCommandLineArgument();
        String rcsHeader = JavancssMessages.getString( "javancss.rcsheader" );
        Javancss lau = new Javancss( args, rcsHeader );
        JavancssParser par = new JavancssParser();
        try
        {
            InputStream inp = new FileInputStream( outputFilename );
            parsingResult = par.parsing( inp );
            parsingResult.getProjectMetrics().setLines( lau.getLOC() );
        }
        catch ( FileNotFoundException e )
        {
            String message = JavancssMessages.getString( "javancss.exception.result.file_not_exist" );
            throw new TaskException( message );
        }

    }

    /**
     * This method create the option command line to use with javancss. It returns a table of string.
     * 
     * @return The option command line to use with javancss
     */
    private String[] getCommandLineArgument()
    {
        List argumentList = new ArrayList();

        // creation of package level metrics
        argumentList.add( "-package" );

        // creation of classes level metrics
        argumentList.add( "-object" );

        // creation of methods level metrics
        argumentList.add( "-function" );

        // the kind of output is xml
        argumentList.add( "-xml" );

        // Creation of a file with the result
        argumentList.add( "-out" );

        // Path of the file to create
        argumentList.add( outputFilename );

        // Add of all the file to analyze
        for ( int i = 0; i < includedFileNames.size(); i++ )
        {
            argumentList.add( new File( (String) includedFileNames.get( i ) ).getPath() );
        }

        return (String[]) argumentList.toArray( new String[argumentList.size()] );
    }

    /**
     * This methods execute the recording of the result of the javancss tool in the database.
     * 
     * @throws TaskException exception due to bad result from findFileName
     * @throws JrafDaoException exception due to problem during the record in database
     */
    private void persisteMeasures()
        throws TaskException, JrafDaoException
    {

        // Completion of the package level BO and recording of new components
        ArrayList packageResults = parsingResult.getPackageResult();
        for ( int i = 0; i < packageResults.size(); i++ )
        {
            JavancssPackageMetricsBO packageMetrics = (JavancssPackageMetricsBO) packageResults.get( i );
            
            //Test if the package name is not "." (See issue #217)
            if ( !packageMetrics.getComponentName().equals( "." ) )
            {
                PackageBO packBO = parser.getPackage( packageMetrics.getComponentName() );
                packageMetrics.setAudit( getAudit() );
                packageMetrics.setComponent( repository.persisteComponent( packBO ) );
                packageMetrics.setTaskName( getName() );
                packageMap.put( repository.buildKey( packageMetrics.getComponent() ), packageMetrics );
            }
        }

        // Completion of classes level results and creation of new components
        completeClassesMeasures();

        // Completion of methods level results and creation of new components
        completeMethodsMeasures();

        MeasureDAOImpl dao = MeasureDAOImpl.getInstance();

        // Save method results
        dao.saveAll( getSession(), methodList );

        // Completion and recording of project level results
        JavancssProjectMetricsBO projectResults = parsingResult.getProjectMetrics();
        projectResults.setCommentsLines( projectResults.getJavadocsLines().intValue()
            + projectResults.getMultiCommentsLines().intValue() + projectResults.getSingleCommentsLines().intValue() );
        projectResults.setLoc( projectResults.getCommentsLines().intValue() + projectResults.getNcss().intValue() );
        projectResults.setClassNumber( numberOfClasses );
        projectResults.setMethodNumber( parsingResult.getMethodsResult().size() );
        projectResults.setAudit( getAudit() );
        projectResults.setComponent( getProject() );
        projectResults.setTaskName( getName() );

        dao.create( getSession(), projectResults );

        // Save package level results
        dao.saveAll( getSession(), packageMap.values() );

        // Save class level results
        dao.saveAll( getSession(), classMap.values() );

        LOGGER.info( JavancssMessages.getString( "javancss.persistence.end" ) );
    }

    /**
     * This method complete the classBO linked to the measure.
     * 
     * @throws TaskException Exception Happen during the search of the path of the class
     * @throws JrafDaoException Exception happen during the the persistence
     */
    private void completeClassesMeasures()
        throws TaskException, JrafDaoException

    {
        ArrayList classesResults = parsingResult.getClassesResult();
        List pathFile = FileUtility.cutPath( includedFileNames, viewPath );
        for ( int i = 0; i < classesResults.size(); i++ )
        {
            JavancssClassMetricsBO classesMetrics = (JavancssClassMetricsBO) classesResults.get( i );
            String name = classesMetrics.getComponentName();
            ClassBO classBO = parser.getClass( name );

            // Searching of the complete path of the class
            String nameToFind = name.replace( '.', File.separatorChar ) + JAVA_FILE_EXTENSION[0];
            List result = FileUtility.findFileName( pathFile, nameToFind );
            if ( result.size() <= 1 )
            {
                if ( result.size() == 1 )
                {
                    classBO.setFileName( (String) result.get( 0 ) );
                }
                else
                {
                    String message = JavancssMessages.getString( "javancss.info.findfilename.noMatch", nameToFind );
                    LOGGER.info( message );
                    initError( message, ErrorBO.CRITICITY_LOW );
                }
                parser.addKnownClass( name );
                classesMetrics.setAudit( getAudit() );
                classesMetrics.setComponent( repository.persisteComponent( classBO ) );
                classesMetrics.setTaskName( getName() );
                numberOfClasses = numberOfClasses + classesMetrics.getClasses().intValue() + 1;
                classMap.put( repository.buildKey( classesMetrics.getComponent() ), classesMetrics );
            }
            else
            {
                String message =
                    JavancssMessages.getString( "javancss.exception.findfilename.tooManyMatch", nameToFind );
                LOGGER.error( message );
                throw new TaskException( message );
            }
        }
    }

    /**
     * This method do the persistence of the method level metric for javancss results
     * 
     * @throws JrafDaoException Exception happen during the persistence
     * @throws TaskException exception happened during the persistence in the base
     */
    private void completeMethodsMeasures()
        throws JrafDaoException, TaskException
    {
        ArrayList methodsResults = parsingResult.getMethodsResult();

        Collection allMethods = repository.getMethods().values();
        for ( int i = 0; i < methodsResults.size(); i++ )
        {
            JavancssMethodMetricsBO methodMetrics = (JavancssMethodMetricsBO) methodsResults.get( i );
            String methPathName = methodMetrics.getComponentName();
            /*
             * If the method give by javancss is inside an anonymous class, the method results is not persist. But the
             * CCN of the method is take into account in maxVg and sumVg ; and this method is take into account in the
             * total number of methods
             */
            if ( !methPathName.contains( "$" ) )
            {
                ifMethodIsNotInsideAnonym( methPathName, methodMetrics, allMethods );
            }
            else
            {
                ifMethodIsInsideAnonym( methPathName, methodMetrics );
            }
        }
    }

    /**
     * This method do the persistence of the measure when the method linked to this measure is not inside an anonymous
     * class
     * 
     * @param methPathName The path name of the method link to the metric to persist
     * @param methodMetrics The metrics bo to persist
     * @param allMethods The repository of all existent methods
     * @throws JrafDaoException exception happened during the persistence in the base
     */
    private void ifMethodIsNotInsideAnonym( String methPathName, JavancssMethodMetricsBO methodMetrics,
                                            Collection allMethods )
        throws JrafDaoException
    {
        ArrayList methodMatch = new ArrayList();
        MethodBO methBO = parser.getMethod( methPathName, "" );
        MethodBO repoMethBO = (MethodBO) repository.getComponent( methBO );
        if ( repoMethBO == null )
        {
            // Searching of the complete path name of the classes which contain the method
            AbstractComplexComponentBO compoBo = (AbstractComplexComponentBO) methBO.getParent();
            while ( ( compoBo.getParent() instanceof ClassBO ) )
            {
                compoBo = compoBo.getParent();
            }
            ClassBO classBo = (ClassBO) repository.getComponent( compoBo );
            methBO.setLongFileName( classBo.getFileName() );
            ( (ClassBO) methBO.getParent() ).setFileName( classBo.getFileName() );

            // As javancss doesn't give the full name for arguments type, we search correspondence between the
            // method name give by javancss and those we have in the database.
            // If there is no match found, then we don't modify methBO, and we persist it.
            methodMatch = MethodBOHelper.searchMethodBO( methBO, allMethods, repository );

            if ( methodMatch.size() == 1 )
            {
                methBO = (MethodBO) methodMatch.get( 0 );
            }
            else if ( methodMatch.size() > 1 )
            {
                String message = JavancssMessages.getString( "javancss.info.method.manyMatch", methBO.getName() );
                LOGGER.info( message );
                initError( message, ErrorBO.CRITICITY_LOW );
            }
        }
        else
        {
            methBO = repoMethBO;
        }
        methodMetrics.setAudit( getAudit() );
        methodMetrics.setComponent( methBO );
        methodMetrics.setTaskName( getName() );
        int ccn = methodMetrics.getCcn().intValue();
        AbstractComplexComponentBO compo = methodMetrics.getComponent().getParent();

        computeClass( ccn, compo );

        methodMetrics.setComponent( repository.persisteComponent( methBO ) );
        methodList.add( methodMetrics );
    }

    /**
     * This method do the persistence of the measure when the method linked to the measure is inside an anonymous class
     * 
     * @param methPathName The path name of the method link to the metric to persist
     * @param methodMetrics The metrics BO to use
     */
    private void ifMethodIsInsideAnonym( String methPathName, JavancssMethodMetricsBO methodMetrics )
    {
        String pathName = methPathName;
        int ccn = methodMetrics.getCcn().intValue();
        // we cut the pathName of the anonymous class in order to get the class which contains the anonymous class
        pathName = pathName.substring( 0, pathName.lastIndexOf( '$' ) );
        ClassBO repoclazzBO = null;
        do
        {
            pathName = pathName.substring( 0, pathName.lastIndexOf( "." ) );
            ClassBO clazzBO = parser.getClass( pathName );
            repoclazzBO = (ClassBO) repository.getComponent( clazzBO );
        }
        while ( repoclazzBO == null && pathName.contains( "." ) );
        computeClass( ccn, repoclazzBO );
    }

    /**
     * Compute of the maxVg and sumVg for the class level result
     * 
     * @param component The JavancssMethodMetricsBO associate to the method parent of the class
     * @param ccn The cyclomatic complexity of the method
     */
    private void computeClass( int ccn, AbstractComplexComponentBO component )
    {
        AbstractComplexComponentBO compo = component;
        while ( !( compo.getParent() instanceof PackageBO ) && !( compo.getParent() instanceof ProjectBO) )
        {
            compo = compo.getParent();
        }

        JavancssClassMetricsBO metricClassCompo = classMap.get( repository.buildKey( compo ) );

        if ( metricClassCompo.getSumVg() == null )
        {
            metricClassCompo.setSumVg( ccn );
            metricClassCompo.setMaxVg( ccn );
        }
        else
        {
            metricClassCompo.setSumVg( metricClassCompo.getSumVg().intValue() + ccn );
            if ( ccn > metricClassCompo.getMaxVg().intValue() )
            {
                metricClassCompo.setMaxVg( ccn );
            }
        }
        computePackage( ccn, compo );
    }

    /**
     * Compute of the maxVg and sumVg for the package level result
     * 
     * @param ccn The ccn value to test
     * @param component The parent ClassBO
     */
    private void computePackage( int ccn, AbstractComplexComponentBO component )
    {
        if (component.getParent() instanceof PackageBO)
        {
            PackageBO compo = (PackageBO) component.getParent();
            JavancssPackageMetricsBO metricPackageCompo = packageMap.get( repository.buildKey( compo ) );
            if ( metricPackageCompo.getSumVg() == null )
            {
                metricPackageCompo.setSumVg( ccn );
                metricPackageCompo.setMaxVg( ccn );
            }
            else
            {
                metricPackageCompo.setSumVg( metricPackageCompo.getSumVg().intValue() + ccn );
                if ( ccn > metricPackageCompo.getMaxVg().intValue() )
                {
                    metricPackageCompo.setMaxVg( ccn );
                }
            }
        }
        computeProject( ccn );
    }

    /**
     * Compute of the maxVg and sumVg for the project level result
     * 
     * @param ccn The new value of ccn to test
     */
    private void computeProject( int ccn )
    {
        JavancssProjectMetricsBO projectMetricResults = parsingResult.getProjectMetrics();
        if ( projectMetricResults.getSumVg() == null )
        {
            projectMetricResults.setSumVg( ccn );
            projectMetricResults.setMaxVg( ccn );
        }
        else
        {
            projectMetricResults.setSumVg( projectMetricResults.getSumVg().intValue() + ccn );
            if ( ccn > projectMetricResults.getMaxVg().intValue() )
            {
                projectMetricResults.setMaxVg( ccn );
            }
        }
    }
}