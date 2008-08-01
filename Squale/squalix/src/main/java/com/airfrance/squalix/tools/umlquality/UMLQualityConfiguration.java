package com.airfrance.squalix.tools.umlquality;

import java.io.InputStream;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Configuration UMLQuality. La configuration UMLQuality est définie dans un fichier XML, celui-ci est lu par cette
 * classe.
 */

public class UMLQualityConfiguration
    extends XmlImport
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( UMLQualityConfiguration.class );

    /** Répertoire de génération de rapport */
    private String mReportDirectory;

    /**
     * Constructeur
     */
    public UMLQualityConfiguration()
    {
        super( LOGGER );
    }

    /**
     * Lecture du fichier de configuration
     * 
     * @param pStream flux
     * @throws ConfigurationException si erreur
     */
    public void parse( InputStream pStream )
        throws ConfigurationException
    {
        StringBuffer errors = new StringBuffer();
        Digester digester =
            preSetupDigester( "-//UMLQuality Configuration DTD 1.0//EN", "/config/umlquality-config-1.0.dtd", errors );
        // Traitement du répertoire de génération des reports
        digester.addCallMethod( "umlquality-configuration/reportDirectory", "setReportDirectory", 1,
                                new Class[] { String.class } );
        digester.addCallParam( "umlquality-configuration/reportDirectory", 0 );
        digester.push( this );
        // Appel du parser
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( UMLQualityMessages.getString( "error.configuration",
                                                                            new Object[] { errors.toString() } ) );
        }
    }

    /**
     * @return directory
     */
    public String getReportDirectory()
    {
        return mReportDirectory;
    }

    /**
     * @param pDirectory répertoire
     */
    public void setReportDirectory( String pDirectory )
    {
        mReportDirectory = ConfigUtility.filterStringWithSystemProps( pDirectory );
    }

}
