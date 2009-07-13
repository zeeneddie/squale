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
/*
 * Créé le 17 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.config;

import java.io.File;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.RequestUtils;
import org.squale.welcom.outils.WelcomConfigurator;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public abstract class WAddOns
    implements WIAddOns
{

    /** Memorise si le plufin est chargé */
    private boolean loaded = false;

    /**
     * @see org.squale.welcom.addons.config.WIAddOns#destroy()
     */
    public abstract void destroy();

    /**
     * @see org.squale.welcom.addons.config.WIAddOns#init(org.apache.struts.action.ActionServlet,
     *      org.apache.struts.config.ModuleConfig)
     */
    public abstract void init( ActionServlet servlet, ModuleConfig config )
        throws AddonsException;

    /**
     * @see org.squale.welcom.addons.config.WIAddOns#getDisplayName()
     */
    public abstract String getDisplayName();

    /**
     * @see org.squale.welcom.addons.config.WIAddOns#getName()
     */
    public abstract String getName();

    /**
     * @see org.squale.welcom.addons.config.WIAddOns#getVersion()
     */
    public abstract String getVersion();

    /**
     * Ajoute un bean
     * 
     * @param config : config struts
     * @param name : nom du bean
     * @param type : classe du bean
     */
    public void initFormBeanConfig( final ModuleConfig config, final String name, final String type )
    {

        final FormBeanConfig fbc = new FormBeanConfig();
        fbc.setName( name );
        fbc.setType( type );
        fbc.setModuleConfig( config );

        config.addFormBeanConfig( fbc );
    }

    /**
     * Initialise le mapping, si un bean est spécfié le stocke en session
     * 
     * @param servlet : Servlet
     * @param config : Module Config
     * @param name : Nom du bean
     * @param welcomKey : key
     * @param path : path
     * @param type : type
     * @throws AddonsException : Probleme du 'linstanciation de la classe
     */
    public void initMappings( final ActionServlet servlet, final ModuleConfig config, final String name,
                              final String welcomKey, final String path, final String type )
        throws AddonsException
    {
        initMappings( servlet, config, name, welcomKey, path, type, "session" );
    }

    /**
     * Initialise le mapping
     * 
     * @param servlet : Servlet
     * @param config : Module Config
     * @param name : Nom du bean
     * @param welcomKey : key
     * @param path : path
     * @param type : type
     * @param scope : scope de staockage du bean
     * @throws AddonsException : Probleme du 'linstanciation de la classe
     */
    public void initMappings( final ActionServlet servlet, final ModuleConfig config, final String name,
                              final String welcomKey, final String path, final String type, final String scope )
        throws AddonsException
    {
        // Verification config
        final String jsp = WelcomConfigurator.getMessage( welcomKey );
        if ( GenericValidator.isBlankOrNull( jsp ) )
        {
            throw new AddonsException( "ATTENTION Verifier la presence d'une valeur pour la key '" + welcomKey
                + "' de votre WelcomResources" );
        }

        final String jspFullPath = servlet.getServletContext().getRealPath( jsp );
        final File f = new File( jspFullPath );
        if ( !f.exists() && !GenericValidator.isBlankOrNull( jspFullPath ) )
        {
            throw new AddonsException( "ATTENTION page non trouvé : " + jspFullPath );
        }
        else
        {
            try
            {
                // Table forward
                final ActionMapping mapping =
                    (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( path );
                mapping.setParameter( "action" );
                mapping.setType( type );
                if ( !GenericValidator.isBlankOrNull( name ) )
                {
                    mapping.setName( name );
                    mapping.setScope( scope );
                }
                mapping.addForwardConfig( new ActionForward( "success", jsp, false ) );

                config.addActionConfig( mapping );
            }
            catch ( final Exception e )
            {
                throw new AddonsException( e.getMessage() );
            }
        }
    }

    /**
     * @see org.squale.welcom.addons.config.WIAddOns#isLoaded()
     */
    public boolean isLoaded()
    {
        return loaded;
    }

    /**
     * Spécifie si l'objet est chargé
     * 
     * @param b : stocke sur l'addons a étét initalisé
     */
    public void setLoaded( final boolean b )
    {
        loaded = b;
    }

}
