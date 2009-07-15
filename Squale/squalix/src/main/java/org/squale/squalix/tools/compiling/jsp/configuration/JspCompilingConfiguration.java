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
package org.squale.squalix.tools.compiling.jsp.configuration;

import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.util.xml.XmlImport;
import org.squale.squalix.configurationmanager.ConfigUtility;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.tools.compiling.CompilingMessages;

/**
 * Configuration pour la compilation des JSPs. La configuration de la compilation des JSPs est définie dans un fichier
 * XML (<code>jspcompiling-config.xml</code>), celui-ci est lu par cette classe.
 */
public class JspCompilingConfiguration
    extends XmlImport
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( JspCompilingConfiguration.class );

    /**
     * Nom du premier package pour les jsps Ce nom peut être suivi du numéro d'index+1 du répertoire de la liste des
     * sources JSP donnée en paramétre de configuration si la jsp est contenue dans ce répertoire (si index = 0 on ne
     * met pas de numéro)
     */
    public static final String FIRST_PACKAGE = "jsp";

    /** Répertoire contenant les jars */
    private String mJarDirectory;

    /**
     * Constructeur par défaut
     */
    public JspCompilingConfiguration()
    {
        super( LOGGER );
    }

    /**
     * @return le répertoire contenant les jars
     */
    public String getJarDirectory()
    {
        return mJarDirectory;
    }

    /**
     * @param pJarDirectory le répertoire contenant les jars
     */
    public void setJarDirectory( String pJarDirectory )
    {
        mJarDirectory = ConfigUtility.filterStringWithSystemProps( pJarDirectory );
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
            preSetupDigester( "-//JspCompiling Configuration DTD //EN", "/config/jspcompiling-config.dtd", errors );
        // Traitement du répertoire des jars
        digester.addCallMethod( "jspcompiling-configuration/jarDirectory", "setJarDirectory", 1,
                                new Class[] { String.class } );
        digester.addCallParam( "jspcompiling-configuration/jarDirectory", 0 );
        digester.push( this );
        // Appel du parser
        parse( digester, pStream, errors );
        if ( errors.length() > 0 )
        {
            throw new ConfigurationException( CompilingMessages.getString( "error.configuration",
                                                                           new Object[] { errors.toString() } ) );
        }
    }

}
