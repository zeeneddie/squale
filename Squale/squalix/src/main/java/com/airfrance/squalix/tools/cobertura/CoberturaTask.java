package com.airfrance.squalix.tools.cobertura;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura.AbstractCoberturaMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaClassMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaMethodMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaPackageMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.cobertura.CoberturaProjectMetricsBO;
import com.airfrance.squalix.tools.abstractgenerictask.AbstractGenericTask;
import com.airfrance.squalix.util.parser.JavaParser;
import com.airfrance.squalix.util.repository.ComponentRepository;

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
     */
    public void parseResults( List<File> results )
    {
        /* Logging to indicate that Squale has started CoberturaTask parsing */
        LOGGER.info( new String( CoberturaMessages.getMessage( "cobertura.task.beginsParsing", mAudit.getName() ) ) );
        /* Iterating the list to recover result file(s) */
        for ( Iterator<File> iterator = results.iterator(); iterator.hasNext(); )
        {
            /* Creating a File at each iteration */
            File file = (File) iterator.next();
            /* If the file exists */
            if ( file.exists() )
            {
                /* Parsing the content of if */
                parsedResults = coberturaParser.parse( file );
            }
            else
            {
                /* Logging a "no file found" error */
                LOGGER.error( new String( CoberturaMessages.getMessage( "cobertura.task.noFileFound", file.getName() ) ) );
            }
        }
        /* Persisting the values resulting from the parsing */
        this.persistResults();
    }

    /**
     * <p>
     * Method used to recover and push the results in DB
     * </p>
     */
    public void persistResults()
    {
        /* Instance of the repository which will be used */
        repository = new ComponentRepository( mProject, getSession() );
        try
        {
            /* Persisting project level values in the DB */
            parsedResults.setAudit( this.getAudit() );
            parsedResults.setComponent( this.getProject() );
            parsedResults.setTaskName( this.getName() );
            MeasureDAOImpl.getInstance().create( getSession(), parsedResults );
            /* Persisting the package level values */
            extractPackageMetrics( parsedResults );
        }
        catch ( JrafDaoException jrafExcep )
        {
            /* An exception here comes either from the "persisteComponent" method call or the "saveAll" method call */
            LOGGER.error( CoberturaMessages.getMessage( "cobertura.task.persistenceError", jrafExcep ) );
        }
    }

    /**
     * <p>
     * This method is used to extract and iterate the measurements of the package results
     * </p>
     * 
     * @param pProjectMetrics the project level result that contains the package level measurements
     */
    public void extractPackageMetrics( CoberturaProjectMetricsBO pProjectMetrics )
    {
        /* Getting the list of packages */
        List<AbstractCoberturaMetricsBO> packagesResults = pProjectMetrics.getPackages();
        /* Iterating to get single package metrics */
        for ( Iterator<AbstractCoberturaMetricsBO> iterator = packagesResults.iterator(); iterator.hasNext(); )
        {
            try
            {
                /* Getting a package */
                CoberturaPackageMetricsBO singlePackageMetrics = (CoberturaPackageMetricsBO) iterator.next();
                /* Setting the audit reference */
                singlePackageMetrics.setAudit( this.getAudit() );
                /* Setting the task reference */
                singlePackageMetrics.setTaskName( this.getName() );
                /* Creating the packageBO object thanks to the getPackage method of the JavaParser */
                PackageBO packageBO = jParser.getPackage( singlePackageMetrics.getName() );
                /* Persisting the values */
                singlePackageMetrics.setComponent( repository.persisteComponent( packageBO ) );
                /* Extracting and persisting the class level metrics */
                extractClassMetrics( (CoberturaPackageMetricsBO) singlePackageMetrics );
                MeasureDAOImpl.getInstance().create( getSession(), singlePackageMetrics );
            }
            catch ( JrafDaoException jrafExcep )
            {
                /* An exception here comes from the "persisteComponent" method call */
                LOGGER.error( CoberturaMessages.getMessage( "cobertura.task.persistenceError", jrafExcep.getMessage() ) );
            }
        }
    }

    /**
     * <p>
     * This method is used to extract and iterate the measurements of the class results
     * </p>
     * 
     * @param pPackageMetrics the package level result that contains the package level measurements
     */
    public void extractClassMetrics( CoberturaPackageMetricsBO pPackageMetrics )
    {
        /* Getting the list of classes */
        List<AbstractCoberturaMetricsBO> classesResults = pPackageMetrics.getClasses();
        /* Iterating to get single class metrics */
        for ( Iterator<AbstractCoberturaMetricsBO> iterator = classesResults.iterator(); iterator.hasNext(); )
        {
            try
            {
                /* Getting a class */
                CoberturaClassMetricsBO singleClassMetrics = (CoberturaClassMetricsBO) iterator.next();
                /* Setting the audit reference */
                singleClassMetrics.setAudit( this.getAudit() );
                /* Setting the task reference */
                singleClassMetrics.setTaskName( this.getName() );
                /* Creating the classBO object thanks to the getClass method of the JavaParser */
                ClassBO classBO = jParser.getClass( singleClassMetrics.getName() );
                /* Persisting the values */
                singleClassMetrics.setComponent( repository.persisteComponent( classBO ) );
                /* Extracting and persisting the class level metrics */
                extractMethodMetrics( (CoberturaClassMetricsBO) singleClassMetrics );
                MeasureDAOImpl.getInstance().create( getSession(), singleClassMetrics );
            }
            catch ( JrafDaoException jrafExcep )
            {
                /* An exception here comes from the "persisteComponent" method call */
                LOGGER.error( CoberturaMessages.getMessage( "cobertura.task.persistenceError", jrafExcep.getMessage() ) );
            }
        }
    }

    /**
     * <p>
     * This method is used to extract and iterate the measurements of method results
     * </p>
     * 
     * @param pClassMetrics the class level result that contains the package level measurements
     */
    public void extractMethodMetrics( CoberturaClassMetricsBO pClassMetrics )
    {
        /* Getting the list of methods */
        List<AbstractCoberturaMetricsBO> methodMetrics = pClassMetrics.getMethods();
        /* Iterating to get single method metrics */
        for ( Iterator<AbstractCoberturaMetricsBO> iterator = methodMetrics.iterator(); iterator.hasNext(); )
        {
            try
            {
                /* Getting a method */
                CoberturaMethodMetricsBO singleMethodMetrics = (CoberturaMethodMetricsBO) iterator.next();
                /*
                 * Checking if the method comes from an anonymous class or is an early init. If not executing the
                 * statements
                 */
                if ( !this.hasAnonymousOrigin( pClassMetrics ) && !this.isEarlyInit( singleMethodMetrics )
                    && !this.hasAnonymousOrigin( singleMethodMetrics ) )
                {
                    /* Setting the audit reference */
                    singleMethodMetrics.setAudit( this.getAudit() );
                    /* Setting the task reference */
                    singleMethodMetrics.setTaskName( this.getName() );
                    /* Recombining the name of the method and its signature */
                    String originalMethodName =
                        pClassMetrics.getName() + '.'
                            + jParser.getConstructorFromByte( singleMethodMetrics.getName(), pClassMetrics.getName() )
                            + '(' + jParser.getSignatureFromBytecode( singleMethodMetrics.getSignature() ) + ')';
                    /* Setting the name of the method to the original one */
                    singleMethodMetrics.setName( originalMethodName );
                    /* Creating the methodBO object thanks to the getMethod method of the JavaParser */
                    MethodBO methodBO = jParser.getMethod( singleMethodMetrics.getName(), "" );
                    /* Checking if the component already exists in the map of components */
                    if ( null != repository.getComponent( methodBO ) )
                    {
                        /* Persisting the values */
                        singleMethodMetrics.setComponent( repository.persisteComponent( methodBO ) );
                        MeasureDAOImpl.getInstance().findWhere( getSession(), singleMethodMetrics.getId(),
                                                                singleMethodMetrics.getAudit().getId() );
                        /* Pushing in DB */
                        MeasureDAOImpl.getInstance().create( getSession(), singleMethodMetrics );
                    }
                }
            }
            catch ( JrafDaoException jrafExcep )
            {
                /* An exception here comes from the "persisteComponent" method call */
                LOGGER.error( CoberturaMessages.getMessage( "cobertura.task.persistenceError", jrafExcep.getMessage() ) );
            }
        }
    }

    /**
     * <p>
     * Before aggregating metrics and pushing them in DB on must check if a method could be identified in case of
     * regular audits. As Squale purposes are to audit projects over a middle to long time period it is for now not
     * possible to integrate method from anonymous class.
     * </p>
     * <p>
     * This method checks if the audited method comes from an anonymous class or not so as to decide whether or not to
     * save data in DB.
     * </p>
     * 
     * @param pMetricBO the metrics that has to be checked
     * @return a boolean==true if the parameter comes from an anonymous origin such as an anonymous inner class
     */
    public boolean hasAnonymousOrigin( AbstractCoberturaMetricsBO pMetricBO )
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
     * @param pMetricBO the metric the name of which has to be tested
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