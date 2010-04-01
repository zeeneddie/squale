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
package org.squale.squalix.tools.cobertura;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.AbstractCoberturaMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaClassMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaMethodMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaPackageMetricsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaProjectMetricsBO;
import org.squale.squalix.tools.abstractgenerictask.AbstractGenericTask;
import org.squale.squalix.util.parser.JavaParser;
import org.squale.squalix.util.repository.ComponentRepository;

/**
 * <p>
 * A coberturaTask object represents the concrete part of the task which is in charge of the code coverage metrics
 * computation and recovering.
 * </p>
 * <p>
 * It describes the degree to which the source code of a program has been tested. It is a form of testing that inspects
 * the code directly and is therefore a form of white box testing.<br />
 * Please refer to <a href="http://en.wikipedia.org/wiki/Code_coverage">the Wikipedia definition</a> for more
 * information.
 * </p>
 * <p>
 * The coberturaTask in Squale has to :
 * <ul>
 * <li>launch Cobertura tool if not launched</li>
 * <li>recover the result files</li>
 * <li>parse the result files</li>
 * <li>push the parsed results into the DB</li>
 * </ul>
 * </p>
 */
public class CoberturaTask
    extends AbstractGenericTask
{
    /**
     * Instance of MeasureDAO used during the processing
     */
    private static final MeasureDAOImpl MEASURE_DAO = MeasureDAOImpl.getInstance();

    /** Logger */
    private static final Log LOGGER = LogFactory.getLog( CoberturaTask.class );

    /** Cobertura Parser */
    private CoberturaParser coberturaParser;

    /** Cobertura result file */
    private CoberturaProjectMetricsBO parsedResults;

    /** Repository of component */
    private ComponentRepository repository;

    /** Java parser */
    private JavaParser jParser;

    /**
     * List of ClassMetrics
     */
    private List<List<AbstractCoberturaMetricsBO>> classesMetrics = new ArrayList<List<AbstractCoberturaMetricsBO>>();

    /**
     * List of sorted out methods
     */
    private List<CoberturaMethodMetricsBO> sortedMethods = new ArrayList<CoberturaMethodMetricsBO>();

    /** Default constructor */
    public CoberturaTask()
    {
        /*
         * All task default constructors must give a name to a "mName attribute" while instantiating. This allows for
         * example automatic logging by the super class.
         */
        mName = "CoberturaTask";
        coberturaParser = new CoberturaParser();
        jParser = new JavaParser( mProject );
    }

    /**
     * <p>
     * Each class which subclasses the {@link AbstractGenericTask} must implement the
     * {@link AbstractGenericTask#parseResults(List)}. This method is automatically called in the
     * {@link AbstractGenericTask#execute()} method.
     * </p>
     * 
     * @param results a {@link List} of {@link File}(s) that has to be parsed
     * @throws JrafDaoException is initially thrown by the persistResults method (if an error occurs while saving the
     *             value in DB)
     */
    public void parseResults( List<File> results )
        throws JrafDaoException
    {
        // Logging to indicate that Squale has started CoberturaTask parsing
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.beginsParsing", mProject.getName() ) ) );
        // Iterating the list to recover result file(s)
        for ( Iterator<File> iterator = results.iterator(); iterator.hasNext(); )
        {
            // Creating a File on each iteration
            File file = (File) iterator.next();
            // If the file exists
            if ( file.exists() )
            {
                LOGGER.info( new String(
                                         CoberturaMessages.getMessage( "cobertura.task.parsingFileName", file.getName() ) ) );
                // Parsing the content of if it
                parsedResults = coberturaParser.parse( file );
            }
            else
            {
                // Logging a "no file found" error and cancelling the task
                LOGGER.error( new String( CoberturaMessages.getMessage( "cobertura.task.noFileFound", file.getName() ) ) );
                mStatus = FAILED;
            }
        }
        // Logging to indicate end of parsing
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.parsingSuccessfull", mProject.getName() ) ) );
        this.extractProjectMetrics();
    }

    /**
     * <p>
     * Extract the metrics associated to each level (Package, class, method) so as to save collections
     * </p>
     * 
     * @throws JrafDaoException is thrown if an error occurs when manipulating the data
     */
    private void extractProjectMetrics()
        throws JrafDaoException
    {
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.startExtracting", mProject.getName() ) ) );
        // Instance of the repository which will be used
        repository = new ComponentRepository( mProject, getSession() );
        // Setting name, audit and component
        parsedResults.setAudit( this.getAudit() );
        parsedResults.setComponent( this.getProject() );
        parsedResults.setTaskName( this.getName() );
        // Persisting project level values
        MEASURE_DAO.create( getSession(), parsedResults );
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.projectLevelPushed", mProject.getName() ) ) );
        // Starting processing of package level metrics
        extractChildrenMetrics( parsedResults );
    }

    /**
     * <p>
     * This method is used to extract and iterate the measurements of the package results
     * </p>
     * 
     * @param pProjectMetrics the project level result that contains the package level measurements
     * @throws JrafDaoException is thrown if an error occurs when creating the values in DB or while persisting the
     *             values
     */
    public void extractChildrenMetrics( CoberturaProjectMetricsBO pProjectMetrics )
        throws JrafDaoException
    {
        LOGGER.info( new String(
                                 CoberturaMessages.getMessage( "cobertura.task.packageLevelStarted", mProject.getName() ) ) );
        // Getting the list of packages
        List<AbstractCoberturaMetricsBO> packagesResults = pProjectMetrics.getPackages();
        // Iterating to get single package metrics and set the value of audit and reference properties
        for ( Iterator<AbstractCoberturaMetricsBO> iterator = packagesResults.iterator(); iterator.hasNext(); )
        {
            // Getting a package
            CoberturaPackageMetricsBO singlePackageMetrics = (CoberturaPackageMetricsBO) iterator.next();
            // Creating the packageBO object thanks to the getPackage method of the JavaParser
            PackageBO packageBO = jParser.getPackage( singlePackageMetrics.getName() );
            // Setting the audit reference
            singlePackageMetrics.setAudit( this.getAudit() );
            // Setting the task reference
            singlePackageMetrics.setTaskName( this.getName() );
            // Setting the mComponent property
            singlePackageMetrics.setComponent( repository.persisteComponent( packageBO ) );
            // Adding the classes related to the packages to the list for post-processing
            classesMetrics.add( singlePackageMetrics.getClasses() );
        }
        // Persisting the package level values
        MEASURE_DAO.saveAll( getSession(), packagesResults );
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.packageLevelPushed", mProject.getName() ) ) );
        this.extractClassMetrics( classesMetrics );
    }

    /**
     * <p>
     * This method is used to extract and iterate the measurements of the class results
     * </p>
     * 
     * @param pClassesMetrics List of classes associated to the packages
     * @throws JrafDaoException is thrown if an error occurs when creating the values in DB or while persisting the
     *             values
     */
    public void extractClassMetrics( List<List<AbstractCoberturaMetricsBO>> pClassesMetrics )
        throws JrafDaoException
    {
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.classLevelStarted", mProject.getName() ) ) );
        // Getting the list of classes
        List<AbstractCoberturaMetricsBO> classesResults = new ArrayList<AbstractCoberturaMetricsBO>();
        // Getting the lists of classes one by one
        for ( List<AbstractCoberturaMetricsBO> classesList : pClassesMetrics )
        {
            for ( AbstractCoberturaMetricsBO classMetricsBO : classesList )
            {
                // Instance of classMetricsBO
                CoberturaClassMetricsBO singleClassMetrics = (CoberturaClassMetricsBO) classMetricsBO;
                // Creating the classBO object thanks to the getClass method of the JavaParser
                ClassBO classBO = jParser.getClass( singleClassMetrics.getName() );
                // Setting the audit reference
                singleClassMetrics.setAudit( this.getAudit() );
                // Setting the task reference
                singleClassMetrics.setTaskName( this.getName() );
                // Setting the mComponent property
                singleClassMetrics.setComponent( repository.persisteComponent( classBO ) );
                // Adding to the list
                classesResults.add( singleClassMetrics );
                // Getting the list of associated methods
                List<AbstractCoberturaMetricsBO> unsortedMethodMetrics = singleClassMetrics.getMethods();
                // Preparing the list of methods
                for ( AbstractCoberturaMetricsBO abstractCoberturaMetricsBO : unsortedMethodMetrics )
                {
                    // Instance of methodBO
                    CoberturaMethodMetricsBO singleMethodMetrics =
                        (CoberturaMethodMetricsBO) abstractCoberturaMetricsBO;
                    // Checking if the method comes from an anonymous class or is an early init. If not executing
                    // the statements
                    if ( !this.isIdentifiable( singleClassMetrics ) && !this.isEarlyInit( singleMethodMetrics )
                        && !this.isIdentifiable( singleMethodMetrics ) )
                    {
                        // Recombining the name of the method and its signature
                        String originalMethodName =
                            singleClassMetrics.getName()
                                + '.'
                                + jParser.getConstructorFromByte( singleMethodMetrics.getName(),
                                                                  singleClassMetrics.getName() ) + '('
                                + jParser.getSignatureFromBytecode( singleMethodMetrics.getSignature() ) + ')';
                        // Setting the name of the method to the original one
                        singleMethodMetrics.setName( originalMethodName );
                        sortedMethods.add( singleMethodMetrics );
                    }
                }
            }
        }
        // Persisting the package level values
        MEASURE_DAO.saveAll( getSession(), classesResults );
        this.extractMethodMetrics( sortedMethods );
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.classLevelPushed", mProject.getName() ) ) );
    }

    /**
     * <p>
     * This method is used to extract and iterate the measurements of method results
     * </p>
     * 
     * @param pMethodMetrics List of sorted out CoberturaMetricsBO
     * @throws JrafDaoException is thrown if an error occurs when creating the values in DB or while persisting the
     *             values
     */
    public void extractMethodMetrics( List<CoberturaMethodMetricsBO> pMethodMetrics )
        throws JrafDaoException
    {
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.methodLevelStarted", mProject.getName() ) ) );
        // Getting the list of methods
        List<CoberturaMethodMetricsBO> methodToPush = new ArrayList<CoberturaMethodMetricsBO>();
        // Getting the method
        for ( AbstractCoberturaMetricsBO abstractCoberturaMetricsBO : pMethodMetrics )
        {
            // Instance of methodBO
            CoberturaMethodMetricsBO singleMethodMetrics = (CoberturaMethodMetricsBO) abstractCoberturaMetricsBO;
            // Setting the audit reference */
            singleMethodMetrics.setAudit( this.getAudit() );
            // Setting the task reference */
            singleMethodMetrics.setTaskName( this.getName() );
            // Creating the methodBO object thanks to the getMethod method of the JavaParser
            MethodBO methodBO = jParser.getMethod( singleMethodMetrics.getName(), "" );
            // Setting the mComponent
            singleMethodMetrics.setComponent( repository.persisteComponent( methodBO ) );
            // Checking if the component already exists in the map of components
            if ( null != repository.getComponent( methodBO ) )
            {
                // Adding to the list used to collect the methods to push
                methodToPush.add( singleMethodMetrics );
            }
        }
        // Persisting the method level values
        MEASURE_DAO.saveAll( getSession(), methodToPush );
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.methodLevelPushed", mProject.getName() ) ) );
    }

    /**
     * <p>
     * Before aggregating metrics and pushing them in DB one must check if a method or a class could be identified in
     * case of regular audits. As Squale purposes are to audit projects over a middle to long time period it is for now
     * not possible to integrate method from anonymous class or static class calls.
     * </p>
     * <p>
     * This method checks if the audited method comes from an anonymous class or is a static class call so as to decide
     * whether or not to save data in DB.
     * </p>
     * 
     * @param pMetricBO the metrics that has to be checked
     * @return a boolean==true if the parameter comes from an anonymous origin such as an anonymous inner class
     */
    public boolean isIdentifiable( AbstractCoberturaMetricsBO pMetricBO )
    {
        boolean boo = false;
        /* if the name attribute of the parameter contains a $ symbol */
        if ( pMetricBO.getName().contains( "$" ) )
        {
            boo = true;
        }
        return boo;
    }

    /**
     * <p>
     * While compiling java source code sometimes early "inits" are done by the compiler so as to ensure that resources
     * are available (e.g for STATIC properties declared in a class).
     * </p>
     * <p>
     * In those cases the related byte-code is "'<'clinit'>'" (stands for "<'class init'>").
     * </p>
     * 
     * @param pMetricBO the metric name that has to be tested
     * @return a {@link Boolean}==true if the tested metric has an early init origin
     */
    public boolean isEarlyInit( AbstractCoberturaMetricsBO pMetricBO )
    {
        boolean boo = false;
        /* Checking if the pMetric name equals <clinit> */
        if ( pMetricBO.getName().equals( "<clinit>" ) )
        {
            boo = true;
        }
        return boo;
    }
}