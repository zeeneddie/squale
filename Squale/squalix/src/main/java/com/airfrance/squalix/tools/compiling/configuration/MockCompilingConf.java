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
package com.airfrance.squalix.tools.compiling.configuration;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.tools.compiling.CompilingMessages;

/**
 * Configuration pour la récupération des sources compilés via une arborescence de fichiers. La configuration associée
 * est définie dans un fichier XML (<code>compiling-config.xml</code>), celui-ci est lu par cette classe.
 */
public class MockCompilingConf
    extends XmlImport
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( MockCompilingConf.class );

    /** Répertoire racine */
    private String mRootDirectory;

    /**
     * Constructeur par défaut
     */
    public MockCompilingConf()
    {
        super( LOGGER );
    }

    /**
     * @return le répertoire racine
     */
    public String getRootDirectory()
    {
        return mRootDirectory;
    }

    /**
     * @param pRootDirectory le répertoire racine
     */
    public void setRootDirectory( String pRootDirectory )
    {
        // On rajoute éventuellement un "/" en bout
        String newRootDirectory = pRootDirectory;
        if ( !newRootDirectory.endsWith( "/" ) )
        {
            newRootDirectory += "/";
        }
        mRootDirectory = ConfigUtility.filterStringWithSystemProps( newRootDirectory );
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
            preSetupDigester( "-//MockCompiling Configuration DTD //EN", "/config/mockcompiling-config.dtd", errors );
        // Traitement du répertoire racine
        digester.addCallMethod( "mockcompiling-configuration/rootPath", "setRootDirectory", 1,
                                new Class[] { String.class } );
        digester.addCallParam( "mockcompiling-configuration/rootPath", 0 );
        digester.push( this );
        // Appel du parser
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( CompilingMessages.getString( "exception.configuration",
                                                                           new Object[] { errors.toString() } ) );
        }
    }

}
