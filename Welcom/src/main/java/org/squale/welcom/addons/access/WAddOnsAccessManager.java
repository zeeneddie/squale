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
 * Créé le 26 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.addons.access;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;
import org.squale.welcom.addons.access.excel.UpdateAccessManager;
import org.squale.welcom.addons.access.excel.filereader.AccessKeyReaderException;
import org.squale.welcom.addons.config.AddonsException;
import org.squale.welcom.addons.config.WAddOns;
import org.squale.welcom.addons.config.WIAddOns;
import org.squale.welcom.outils.WelcomConfigurator;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WAddOnsAccessManager
    extends WAddOns
{
    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Singleton */
    private static WAddOnsAccessManager me = null;

    /** Addons AccessManagement */
    public final static String ADDONS_ACCESSMANAGEMENT_NAME = "ADDONS_ACCESSMANAGEMENT";

    /** Addons AccessManagement */
    private final static String ADDONS_ACCESSMANAGEMENT_DISPLAYNAME = "Gestion des droits d'accés";

    /** Addons AccessManagement */
    private final static String ADDONS_ACCESSMANAGEMENT_VERSION = "2.3";

    /**
     * @return Affichage
     */
    public String getDisplayName()
    {
        return ADDONS_ACCESSMANAGEMENT_DISPLAYNAME;
    }

    /**
     * @return nom de l'addon
     */
    public String getName()
    {
        return ADDONS_ACCESSMANAGEMENT_NAME;
    }

    /**
     * @return Version
     */
    public String getVersion()
    {
        return ADDONS_ACCESSMANAGEMENT_VERSION;
    }

    /**
     * @see org.squale.welcom.addons.config.WIAddOns#init(org.apache.struts.action.ActionServlet,
     *      org.apache.struts.config.ModuleConfig)
     */
    public void init( final ActionServlet servlet, final ModuleConfig config )
        throws AddonsException
    {

        final String excelFile =
            WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_ACCESS_MANAGER_CONFIG_EXCEL_PATH );

        // Mise a jour de la base de donnée si necessaire
        try
        {
            Class.forName( "jxl.read.biff.BiffException" );
            UpdateAccessManager.update( excelFile );
        }
        catch ( final AccessKeyReaderException e1 )
        {
            throw new AddonsException( e1.getMessage() );
        }
        catch ( final ClassNotFoundException e )
        {
            throw new AddonsException( "L'addon accessManager nécessite la librairie jxl" );
        }

        // Ajout des mappings
        initMappings( servlet, config );

        setLoaded( true );
    }

    /**
     * @see org.squale.welcom.addons.config.WIAddOns#destroy()
     */
    public void destroy()
    {
    }

    /**
     * Initialise le mapping du profil et du profilsaccesskey
     * 
     * @param servlet : Servlet
     * @param config : Module Config
     * @throws AddonsException : Probleme sur l'ajout des mapping ...
     */
    public void initMappings( final ActionServlet servlet, final ModuleConfig config )
        throws AddonsException
    {

        // Liste des profils
        initMappings( servlet, config, null, "addons.accessManager.jsp.profiles", "/wProfileList",
                      "org.squale.welcom.addons.access.action.WProfileListeAction" );
        logStartup.info( "Ajout du mapping '/wProfileList.do' pour afficher la liste des profils" );

        // Liste des droits accés pour un profil
        initFormBeanConfig( config, "wProfileBean", "org.squale.welcom.addons.access.bean.ProfileBean" );
        logStartup.info( "Ajout du bean 'wProfileBean' pour l'action '/wProfile.do'" );
        initMappings( servlet, config, "wProfileBean", "addons.accessManager.jsp.profile", "/wProfile",
                      "org.squale.welcom.addons.access.action.WProfileAction" );
        logStartup.info( "Ajout du mapping '/wProfile.do' pour afficher la liste des profils" );

    }

    /**
     * @return Retourne le sigleton
     */
    public static WIAddOns getAddOns()
    {
        if ( me == null )
        {
            me = new WAddOnsAccessManager();
        }
        return me;
    }

}
