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
 * Créé le 22 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.config;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface WIAddOns
{

    /**
     * Fermeture de l'addons..
     */
    public void destroy();

    /**
     * la config de l'Add-on est Ok
     * 
     * @param servlet : Servlet
     * @param config : Config
     * @throws AddonsException : Probleme sur l'initialisation de l'addons
     */
    public void init( ActionServlet servlet, ModuleConfig config )
        throws AddonsException;

    /**
     * @return Affichage
     */
    public String getDisplayName();

    /**
     * @return nom de l'addon
     */
    public String getName();

    /**
     * @return Version
     */
    public String getVersion();

    /**
     * @return Retourne le plugin est initialialisé
     */
    public boolean isLoaded();
}
