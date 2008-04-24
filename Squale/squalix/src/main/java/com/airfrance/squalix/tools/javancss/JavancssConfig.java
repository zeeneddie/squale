package com.airfrance.squalix.tools.javancss;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * This class get back the configuration for the Javancss task. This task is done by reading an xml file :
 * <code>javancss-config.xml</code>
 */
public class JavancssConfig
    extends XmlImport
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JavancssConfig.class );

    /** Path for the result file */
    private String resultFilePath;

    /**
     * Default Constructor
     */
    public JavancssConfig()
    {
        super( LOGGER );
    }

    /**
     * Reading of the configuration
     * 
     * @param pStream stream
     * @throws ConfigurationException exception if an error occurs
     */
    public void parse( InputStream pStream )
        throws ConfigurationException
    {
        StringBuffer errors = new StringBuffer();
        Digester digester =
            preSetupDigester( "-//Javancss Configuration DTD //EN", "/config/javancss-config.dtd", errors );
        // Root directory treatment
        digester.addCallMethod( "javancss-configuration/resultFilePath", "setResultFilePath", 1, new Class[] { String.class } );
        digester.addCallParam( "javancss-configuration/resultFilePath", 0 );
        digester.push( this );
        // Parser call
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( JavancssMessages.getString( "javancss.exception.configuration.parse",
                                                                     new Object[] { errors.toString() } ) );
        }
    }

    /**
     * Getter for the pathResultFile attribute
     * 
     * @return the path of the result file
     */
    public String getResultFilePath()
    {
        return resultFilePath;
    }

    /**
     * Setter for the pathResultFile attribute
     * 
     * @param pResultFilePath The path of the result file
     */
    public void setResultFilePath( String pResultFilePath )
    {
        resultFilePath = pResultFilePath;
    }

}
