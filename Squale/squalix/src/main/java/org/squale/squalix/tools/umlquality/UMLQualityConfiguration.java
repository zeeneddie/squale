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
package org.squale.squalix.tools.umlquality;

import java.io.InputStream;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.util.xml.XmlImport;
import org.squale.squalix.configurationmanager.ConfigUtility;
import org.squale.squalix.core.exception.ConfigurationException;

/**
 * Configuration UMLQuality. La configuration UMLQuality est d�finie dans un fichier XML, celui-ci est lu par cette
 * classe.
 */

public class UMLQualityConfiguration
    extends XmlImport
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( UMLQualityConfiguration.class );

    /** R�pertoire de g�n�ration de rapport */
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
        // Traitement du r�pertoire de g�n�ration des reports
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
     * @param pDirectory r�pertoire
     */
    public void setReportDirectory( String pDirectory )
    {
        mReportDirectory = ConfigUtility.filterStringWithSystemProps( pDirectory );
    }

}
