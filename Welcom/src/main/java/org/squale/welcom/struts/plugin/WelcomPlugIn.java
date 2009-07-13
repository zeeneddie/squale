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
package org.squale.welcom.struts.plugin;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;
import org.squale.welcom.struts.util.WConstants;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WelcomPlugIn
    implements PlugIn
{

    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Fichier de properties de configuration de welcom */
    private String properties = "";

    /** Class de persistance pour les messages */
    private String messagePersistanceClass = "";

    /** Donne le mode par default de Welcom */
    protected String useMode = WConstants.MODE_JRAF;

    /**
     * @see org.apache.struts.action.PlugIn#destroy()
     */
    public void destroy()
    {
        Welcom.destroyAddons();
    }

    /**
     * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet,
     *      org.apache.struts.config.ModuleConfig)
     */
    public void init( final ActionServlet servlet, final ModuleConfig config )
        throws ServletException
    {

        // Repositionne le Welcom Context car on est dans le meme thread que tout le monde
        WelcomContext.initWelcomContextName();

        logStartup.info( "========== WELCOM ==========" );
        logStartup.info( Welcom.about() );

        Welcom.initParameter( servlet, config );

        // Specifie le mode d'utilisation
        try
        {
            Class.forName( "org.squale.jraf.bootstrap.locator.ProviderLocator" );
        }
        catch ( final Exception e )
        {
            useMode = WConstants.MODE_STANDALONE;
        }
        Welcom.initUseMode( servlet, useMode );

        Welcom.initRequestProcessor( servlet, config );

        Welcom.initWelcom( getProperties() );

        Welcom.initDefaults();

        Welcom.initRender();

        Welcom.initFontPDF( servlet );

        Welcom.initForward( servlet, config );

        Welcom.initMapping( config );

        Welcom.initMessageFactory( getMessagePersistanceClass() );

        Welcom.initException( servlet, config );

        Welcom.initAddons( servlet, config );

        Welcom.dumpConfigJVM();

        logStartup.info( "============================" );

    }

    /**
     * @return Fichier de properties de configuration de welcom
     */
    public String getProperties()
    {
        return properties;
    }

    /**
     * @param string Fichier de properties de configuration de welcom
     */
    public void setProperties( final String string )
    {
        properties = string;
    }

    /**
     * @return Class de persistance pour les messages
     */
    public String getMessagePersistanceClass()
    {
        return messagePersistanceClass;
    }

    /**
     * @param string Class de persistance pour les messages
     */
    public void setMessagePersistanceClass( final String string )
    {
        messagePersistanceClass = string;
    }

}