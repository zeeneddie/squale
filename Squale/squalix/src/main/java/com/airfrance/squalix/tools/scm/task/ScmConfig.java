package com.airfrance.squalix.tools.scm.task;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Configuration to retrieve sources from a repository. This configuration is defined in an XML file (<code>sourcecodeanalyser-config.xml</code>),
 * read by a class.
 */
public class ScmConfig
    extends XmlImport
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( ScmConfig.class );

    /** Root directory */
    private String mRootDirectory;

    /**
     * Constructor by default
     */
    public ScmConfig()
    {
        super( LOGGER );
    }

    /**
     * Accessor
     * 
     * @return accessor for the mRootDirectory
     */
    public String getRootDirectory()
    {
        return mRootDirectory;
    }

    /**
     * Accessor
     * 
     * @param pRootDirectory accessor for the mRootDirectory property
     */
    public void setRootDirectory( String pRootDirectory )
    {
        // The character "/" may be added at the end
        String newRootDirectory = pRootDirectory;
        if ( !newRootDirectory.endsWith( "/" ) )
        {
            newRootDirectory += "/";
        }
        mRootDirectory = newRootDirectory;
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
            preSetupDigester( "-//SourceCodeAnalyser Configuration DTD //EN",
                              "/config/sourcecodeanalyser-config-1.0.dtd", errors );
        // Traitement du répertoire racine
        digester.addCallMethod( "sourcecodeanalyser-configuration/rootPath", "setRootDirectory", 1,
                                new Class[] { String.class } );
        digester.addCallParam( "sourcecodeanalyser-configuration/rootPath", 0 );
        digester.push( this );
        // Appel du parser
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( ScmMessages.getString( "exception.configuration",
                                                                     new Object[] { errors.toString() } ) );
        }
    }
}
