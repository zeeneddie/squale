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
package com.airfrance.squalix.tools.ruleschecking;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Configuration Checkstyle La configuration Checkstyle est définie dans un fichier XML, celui-ci est lu par cette
 * classe.
 */
public class CheckstyleConfiguration
    extends XmlImport
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( CheckstyleConfiguration.class );

    /** Répertoire de génération de rapport */
    private String mReportDirectory;

    /** Répertoire contenant les jars */
    private String mJarDirectory;

    /**
     * Constructeur
     */
    public CheckstyleConfiguration()
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
            preSetupDigester( "-//Checkstyle Configuration DTD 1.0//EN",
                              "/com/airfrance/squalix/tools/ruleschecking/checkstyle-config-1.0.dtd", errors );
        // Traitement du répertoire de génération des reports
        digester.addCallMethod( "checkstyle-configuration/reportDirectory", "setReportDirectory", 1,
                                new Class[] { String.class } );
        digester.addCallParam( "checkstyle-configuration/reportDirectory", 0 );
        // Traitement du répertoire des jars
        digester.addCallMethod( "checkstyle-configuration/jarDirectory", "setJarDirectory", 1,
                                new Class[] { String.class } );
        digester.addCallParam( "checkstyle-configuration/jarDirectory", 0 );
        digester.push( this );
        // Appel du parser
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( RulesCheckingMessages.getString( "error.configuration",
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

    /**
     * @return répertoire des jars
     */
    public String getJarDirectory()
    {
        return mJarDirectory;
    }

    /**
     * @param pJarDirectory répertoire des jars
     */
    public void setJarDirectory( String pJarDirectory )
    {
        mJarDirectory = ConfigUtility.filterStringWithSystemProps( pJarDirectory );
    }
}
