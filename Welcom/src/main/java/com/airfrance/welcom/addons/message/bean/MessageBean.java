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
 * Créé le 24 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.message.bean;

import java.util.HashMap;
import java.util.Iterator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.struts.bean.WActionForm;

/**
 *
 */
public class MessageBean
    extends WActionForm
{

    /**
     * 
     */
    private static final long serialVersionUID = 3317401158823274755L;

    /** clef du message */
    private String messageKey = "";

    /** Valeur par defaut */
    private String defaultValue = "";

    /** Liste des valeurs */
    private final HashMap values = new HashMap();

    /** Liste des nouvelles valeurs */
    private final HashMap valuesNew = new HashMap();

    /**
     * @return la valeur par defaut
     */
    public String getDefaultValue()
    {
        return defaultValue;
    }

    /**
     * @return la clef du message
     */
    public String getMessageKey()
    {
        return messageKey;
    }

    /**
     * @param string Speficie la nouvelle valeur
     */
    public void setDefaultValue( final String string )
    {
        defaultValue = string;
    }

    /**
     * @param string spécifie la nouvelle key
     */
    public void setMessageKey( final String string )
    {
        messageKey = string;
    }

    /**
     * Retourn la veleur en fonction de la locale
     * 
     * @param locale : locale
     * @return Valeur
     */
    public String getValue( final String locale )
    {
        return (String) values.get( locale );
    }

    /**
     * Speficie une valeur pour une locale donnée
     * 
     * @param locale : locale
     * @param value : valeur
     */
    public void setValue( final String locale, final String value )
    {
        values.put( locale, value );
        valuesNew.put( locale, value );
    }

    /**
     * Recupere la nouvelle valeur pour une locale donnée
     * 
     * @param locale : locale
     * @return : valeur
     */
    public String getValueNew( final String locale )
    {
        return (String) valuesNew.get( locale );
    }

    /**
     * Speficie une nouvelle valeur pour une locale donnée
     * 
     * @param locale : locale
     * @param value : valeur
     */
    public void setValueNew( final String locale, final String value )
    {
        valuesNew.put( locale, value );
    }

    /**
     * Retourne vrai si la valeur a été modifié
     * 
     * @param locale : locale
     * @return vrais sir value et valueNew <>
     */
    public boolean isChanged( final String locale )
    {
        return Util.isNonEquals( getValue( locale ), getValueNew( locale ) );
    }

    /**
     * Retourn vrais si le mesages est déja cohntenu
     * 
     * @param message : message
     * @return : vrai si deja existant
     */
    public boolean contain( final String message )
    {
        if ( ( message.length() == 0 ) || ( defaultValue.indexOf( message ) != -1 ) )
        {
            return true;
        }
        else
        {
            final Iterator iter = values.values().iterator();
            while ( iter.hasNext() )
            {
                final String result = (String) iter.next();
                if ( result.indexOf( message ) != -1 )
                {
                    return true;
                }
            }
        }
        return false;
    }
}
