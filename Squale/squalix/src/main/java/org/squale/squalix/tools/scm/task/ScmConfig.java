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
package org.squale.squalix.tools.scm.task;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.util.xml.XmlImport;
import org.squale.squalix.configurationmanager.ConfigUtility;
import org.squale.squalix.core.exception.ConfigurationException;

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

    /** Scm temporary directory */
    private String mScmDirectory;

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
     * @return accessor for the mScmTempDirectory
     */
    public String getScmDirectory()
    {
        return mScmDirectory;
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
        mRootDirectory = ConfigUtility.filterStringWithSystemProps( newRootDirectory );
    }

    /**
     * Accessor
     * 
     * @param pScmDirectory accessor for the mScmDirectory property
     */
    public void setScmDirectory( String pScmDirectory )
    {
        // The character "/" may be added at the end
        String newScmDirectory = pScmDirectory;
        if ( !newScmDirectory.endsWith( "/" ) )
        {
            newScmDirectory += "/";
        }
        mScmDirectory = ConfigUtility.filterStringWithSystemProps( newScmDirectory );
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
        Digester digester = preSetupDigester( "-//Scm Configuration DTD //EN", "/config/scm-config.dtd", errors );
        // Root node
        digester.addCallMethod( "scm-configuration/rootPath", "setRootDirectory", 1, new Class[] { String.class } );
        digester.addCallParam( "scm-configuration/rootPath", 0 );

        // Scm temporary node
        digester.addCallMethod( "scm-configuration/scmTempPath", "setScmDirectory", 1, new Class[] { String.class } );
        digester.addCallParam( "scm-configuration/scmTempPath", 0 );
        digester.push( this );
        // Parsing
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( ScmMessages.getString( "exception.configuration",
                                                                     new Object[] { errors.toString() } ) );
        }
    }
}
