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
 * Créé le 25 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.message;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.struts.util.MessageResources;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WResourceBundle
    extends ResourceBundle
{
    /** Message resource */
    private MessageResources resources;

    /** locale */
    private Locale locale;

    /**
     * Contructeur dun WResourceBundle
     * 
     * @param pResources Resource
     * @param pLocale Locale
     */
    public WResourceBundle( final MessageResources pResources, final Locale pLocale )
    {
        this.resources = pResources;
        this.locale = pLocale;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
     */
    protected Object handleGetObject( final String key )
    {
        return resources.getMessage( locale, key );
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.util.ResourceBundle#getKeys()
     */
    public Enumeration getKeys()
    {
        return null;
    }

    /**
     * @see java.util.ResourceBundle#getLocale()
     */
    public Locale getLocale()
    {
        return locale;
    }

}
