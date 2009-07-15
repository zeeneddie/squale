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
package org.squale.squalix.tools.cpptest;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.util.xml.XmlImport;
import org.squale.squalix.configurationmanager.ConfigUtility;
import org.squale.squalix.core.exception.ConfigurationException;

/**
 * Configuration CppTest La configuration CppTest est définie dans un fichier XML, celui-ci est lu par cette classe.
 */
public class CppTestConfiguration
    extends XmlImport
{
    /** Pattern utilisé pour le répertoire de génération des rapports */
    public static final String REPORT_DIRECTORY_PATTERN = "{REPORT_DIRECTORY}";

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( CppTestConfiguration.class );

    /** Répertoire de génération de rapport */
    private String mReportDirectory;

    /** fichier de récupération de la sortie standard */
    private String mLogger;

    /**
     * Constructeur
     */
    public CppTestConfiguration()
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
            preSetupDigester( "-//CppTest Configuration DTD 1.0//EN",
                              "/org/squale/squalix/tools/cpptest/cpptest-config-1.0.dtd", errors );
        // traitement du fichier de log
        digester.addCallMethod( "cpptest-configuration/logger", "setLogger", 1, new Class[] { String.class } );
        digester.addCallParam( "cpptest-configuration/logger", 0 );
        // Traitement du répertoire de génération des reports
        digester.addCallMethod( "cpptest-configuration/reportDirectory", "setReportDirectory", 1,
                                new Class[] { String.class } );
        digester.addCallParam( "cpptest-configuration/reportDirectory", 0 );
        digester.push( this );
        // Appel du parser
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( CppTestMessages.getString( "error.configuration",
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
     * @return le fichier de log
     */
    public String getLogger()
    {
        return mLogger;
    }

    /**
     * @param pLogger le fichier de log
     */
    public void setLogger( String pLogger )
    {
        mLogger = ConfigUtility.filterStringWithSystemProps( pLogger );
    }

}
