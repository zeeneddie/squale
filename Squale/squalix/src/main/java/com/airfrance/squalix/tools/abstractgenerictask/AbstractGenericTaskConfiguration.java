package com.airfrance.squalix.tools.abstractgenerictask;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.StringUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.codehaus.plexus.util.cli.Commandline.Argument;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalix.core.TaskException;

/**
 * <p>
 * This class allows instantiation of <b>configuration object(s)</b> related to an
 * <code>{@link AbstractGenericTask}</code>.
 * </p>
 * <p>
 * User inputs are first stored in a map and then pushed in database. Instance of <code>{@link ProjectBO}</code> owns
 * {@link MapParameterBO} type objects which store the inputed data giving them automatically a key recovered from the
 * parameterConstant.
 * </p>
 * <p>
 * One could input required configuration parameters in a JSP. For more information regarding required informations so
 * as to run a task inheriting from the <code>{@link AbstractGenericTask}</code> please refer to the Javadoc of it or
 * please visit the main site and the Squale Developer Center.
 * </p>
 * 
 * @see <a href="http://www.squale.org/squale-developer-site/index.html">Squale Developer Center</a>
 * @see <a href="http://www.squale.org">Squale Welcom</a>
 */
public class AbstractGenericTaskConfiguration
{

    /** Constant value of a wild-card for pattern building */
    private static final String WILD_CARD = "*";

    /** Instance of the logger */
    private static final Log LOGGER = LogFactory.getLog( AbstractGenericTaskConfiguration.class );

    /** Default constructor */
    public AbstractGenericTaskConfiguration()
    {
    }

    /**
     * <p>
     * This method extracts the parameters that have to be passed while executing the tool. A {@link ListParameterBO} is
     * passed in as parameter of this method so as to extract the value of the user input.<br />
     * The data (i.e the parameters) are always stored at index 0 as they come from a textarea. Please see the
     * add_project_generictask_conf.jsp for more info.
     * </p>
     * 
     * @param pList the parameters stored in a {@link ListParameterBO}
     * @return the value of the parameters inputed by the user
     */
    public String extractParameters( ListParameterBO pList )
    {
        /* Getting the parameters at index 0 */
        StringParameterBO cmd = (StringParameterBO) pList.getParameters().get( 0 );
        /* Object that will be returned */
        String cmdToReturn = cmd.getValue();

        return cmdToReturn;
    }

    /**
     * <p>
     * This method iterates a <b>{@link ListParameterBO}</b> and extracts each value as a String.
     * </p>
     * 
     * @param pList the resultFiles location stored in a {@link ListParameterBO}
     * @return resultsFiles location in an array of String
     */
    public String[] getResultFilesLocation( ListParameterBO pList )
    {
        /* Getting the resultFiles location */
        List<StringParameterBO> resultList = pList.getParameters();

        /* Array of resultFile(s) location that will be returned */
        String[] includeResults = new String[resultList.size()];

        /* Iterating over the list */
        int i = 0;
        for ( Iterator<StringParameterBO> it = resultList.iterator(); it.hasNext(); )
        {
            String tmpResultLocation = it.next().getValue();
            includeResults[i] = tmpResultLocation;
            i++;
        }
        return includeResults;
    }

    /**
     * <p>
     * This method process the user inputs so as to define the resultFile(s) location(s) and the way they will be
     * retrieved and/or instantiated.
     * </p>
     * 
     * @param pList an array containing the resultFiles the audit has to compute
     * @param pViewPath the viewPath of the project
     * @return a {@link DirectoryScanner} enriched with files/pattern to include and a base directory
     */
    public DirectoryScanner prepareResultProcessing( ListParameterBO pList, String pViewPath )
    {
        /* A list to store resultFiles location */
        List<StringParameterBO> list;
        /* Array storing the pattern of files that are to include */
        String[] includes = null;
        /* The returned directory scanner */
        DirectoryScanner ds = new DirectoryScanner();
        /* The path part of the entry */
        String resultFilesPathPart = "";
        /* The name part of the entry */
        String resultFilesNamePart = "";

        /* If the passed in parameter is null */
        if ( null == pList )
        {
            /* Creating the error message */
            String errorMessage = AbstractGenericTaskMessages.getMessage( "agtc.error.noResultFilesLocation" );
            LOGGER.error( errorMessage );

            /* Throwing an exception, the task must be cancelled in the related result processing method */
            new TaskException( errorMessage );
        }
        else
        {
            /* Temporary array to include file location and/or pattern */
            list = pList.getParameters();
            Object[] fileToInclude = list.toArray();
            includes = new String[list.size()];

            /* Most important is to check the kind of input (result file names, pattern,directories...) */
            for ( int i = 0; i < fileToInclude.length; i++ )
            {
                /* First : Getting the value of each object */
                String resultFilesEntry = ( (StringParameterBO) fileToInclude[i] ).getValue();
                /* Trying to get a path or a pattern if any */
                resultFilesPathPart = FileUtils.getPath( resultFilesEntry );
                /* Trying to get a result file name or a pattern if any */
                resultFilesNamePart = FileUtils.filename( resultFilesEntry );
                /* Including the name part of the entry in the included files */
                includes[i] = resultFilesNamePart;
            }
            /* Setting the base directory (resolved path = path part of the entry + viewPath of the project */
            ds.setBasedir( FileUtils.catPath( pViewPath, resultFilesPathPart ) );
            ds.setIncludes( includes );
        }
        return ds;
    }

    /**
     * <p>
     * This method detects if the user has used a <b>wild-card(s)</b> in his input(s) or not.
     * </p>
     * 
     * @param pValue : the String value of the object that has to be tested
     * @return true if the user has used a wild-card(s)
     */
    public boolean isPattern( String pValue )
    {
        boolean containsWildcard = false;
        if ( pValue.contains( WILD_CARD ) )
        {
            containsWildcard = true;
        }
        return containsWildcard;
    }

    /**
     * <p>
     * This method prepare the tool that has to be run (if any).
     * </p>
     * 
     * @param pToolLocation the {@link StringParameterBO} storing the tool location could be null
     * @param pWorkingDir the {@link StringParameterBO} storing the working directory could be null
     * @param pParameters the parameters inputed by the user could be null
     * @param pViewPath the path to the view could be null
     * @return instance of <b>{@link Commandline}</b> enriched with the command that will be executed
     */
    public Commandline prepareToolExecution( StringParameterBO pToolLocation, StringParameterBO pWorkingDir,
                                             String pParameters, String pViewPath )
    {
        /* This commandLine object is going to be returned */
        Commandline cmdLine = null;
        
        if ( null == pToolLocation )
        {
            /*
             * If the toolLocation is null it could be possible that a task has been launched or is to launch with Ant
             * or Maven. Thus the task is not cancelled but a warn message is logged so as to inform the user.
             */
            String warnMessage = AbstractGenericTaskMessages.getMessage( "agtc.warn.noTool" );
            LOGGER.warn( warnMessage );
        }
        
        /* Instance of the commandLine tool */
        cmdLine = new Commandline();
 
        /* The commands / arguments to be executed */
        Argument args = new Argument();
        args.setLine( pParameters );

        /* Adding to the instance of CommandLine */
        cmdLine.addArg( args );

        /* Setting the executable commands */
        cmdLine.setExecutable( pToolLocation.getValue() );

        if ( !StringUtils.isBlank( pWorkingDir.getValue() ) )
        {
            /* Getting the working directory if not blank i.e inputed by the user */
            String resolvedWorkingDir = FileUtils.catPath( pViewPath, pWorkingDir.getValue() );
            /* Setting the execution directory */
            cmdLine.setWorkingDirectory( resolvedWorkingDir );
        }
        else
        {
            /* if blank setting to the default one */
            cmdLine.setWorkingDirectory( pViewPath );
        }
        return cmdLine;
    }
}
