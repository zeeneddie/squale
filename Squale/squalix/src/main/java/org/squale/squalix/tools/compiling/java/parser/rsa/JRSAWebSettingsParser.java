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
package com.airfrance.squalix.tools.compiling.java.parser.rsa;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalecommon.util.xml.XmlImport;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.tools.compiling.CompilingMessages;

/**
 * Parse le fichier org.eclipse.wst.common.component du répertoire .settings
 */
public class JRSAWebSettingsParser
    extends XmlImport
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JRSAWebSettingsParser.class );

    /** Le nom du répertoire web */
    private String mWebContentFolder;

    /**
     * @param pLog
     */
    protected JRSAWebSettingsParser()
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
        Digester digester = preSetupDigester( null, null, errors );
        // Traitement du répertoire web
        digester.addCallMethod( "project-modules/wb-module/wb-resource", "setWebContentFolder", 2 );
        digester.addCallParam( "project-modules/wb-module/wb-resource", 0, "deploy-path" );
        digester.addCallParam( "project-modules/wb-module/wb-resource", 1, "source-path" );
        digester.push( this );
        // Appel du parser
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( CompilingMessages.getString( "error.configuration",
                                                                           new Object[] { errors.toString() } ) );
        }
    }

    /**
     * @return le nom du répertoire web
     */
    public String getWebContentFolder()
    {
        return mWebContentFolder;
    }

    /**
     * @param pDeployPath le chemin de déploiement
     * @param pSourcePath le nom du répertoire web
     */
    public void setWebContentFolder( String pDeployPath, String pSourcePath )
    {
        // si le chemin de déploiement est "/", on modifie le répertoire
        // TODO : vérifier que le chemin de déploiement ne pas être autre chose que "/"
        if ( "/".equals( pDeployPath ) )
        {
            mWebContentFolder = pSourcePath;
        }
    }

}
