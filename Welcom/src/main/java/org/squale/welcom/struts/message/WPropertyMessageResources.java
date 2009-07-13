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
 * Créé le 23 nov. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.message;

import java.util.Locale;

import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPropertyMessageResources
    extends PropertyMessageResources
{

    /**
     * 
     */
    private static final long serialVersionUID = -4715846547962969092L;

    /** Integration des default message Default Message */
    private PropertyMessageResources defaultWelcomMessages;

    /**
     * Creation d'un WPropertyMessageResources
     * 
     * @param factory : Factory
     * @param config : non de la resource
     */
    public WPropertyMessageResources( final MessageResourcesFactory factory, final String config )
    {
        super( factory, config );
        defaultWelcomMessages =
            new PropertyMessageResources( factory, "org.squale.welcom.resources.DefaultMessages" );
    }

    /**
     * Creation d'un WPropertyMessageResources
     * 
     * @param factory : Factory
     * @param config : non de la resource
     * @param returnNull : retourne null
     */
    public WPropertyMessageResources( final MessageResourcesFactory factory, final String config,
                                      final boolean returnNull )
    {
        super( factory, config, returnNull );
        defaultWelcomMessages =
            new PropertyMessageResources( factory, "org.squale.welcom.resources.DefaultMessages", returnNull );

    }

    /**
     * @see org.apache.struts.util.PropertyMessageResources#getMessage(java.util.Locale, java.lang.String)
     */
    public String getMessage( final Locale locale, final String key )
    {
        String result = super.getMessage( locale, key );
        if ( ( result == null ) )
        {
            result = defaultWelcomMessages.getMessage( locale, key );
        }
        return result;

    }

    /**
     * Surchage du message originale pour ne pas aller chercher le default locale de struts ...
     * 
     * @param locale locale
     * @param key clef
     * @return valeur
     */
    public String getOriginalMessage( Locale locale, String key )
    {

        if ( log.isDebugEnabled() )
        {
            log.debug( "getMessage(" + locale + "," + key + ")" );
        }

        // Initialize variables we will require
        String localeKey = localeKey( locale );
        String originalKey = messageKey( localeKey, key );
        String messageKey = null;
        String message = null;
        int underscore = 0;
        boolean addIt = false; // Add if not found under the original key

        // Loop from specific to general Locales looking for this message
        while ( true )
        {

            // Load this Locale's messages if we have not done so yet
            loadLocale( localeKey );

            // Check if we have this key for the current locale key
            messageKey = messageKey( localeKey, key );
            synchronized ( messages )
            {
                message = (String) messages.get( messageKey );
                if ( message != null )
                {
                    if ( addIt )
                    {
                        messages.put( originalKey, message );
                    }
                    return ( message );
                }
            }

            // Strip trailing modifiers to try a more general locale key
            addIt = true;
            underscore = localeKey.lastIndexOf( "_" );
            if ( underscore < 0 )
            {
                break;
            }
            localeKey = localeKey.substring( 0, underscore );

        }

        // Return an appropriate error indication
        if ( returnNull )
        {
            return ( null );
        }
        else
        {
            return ( "???" + messageKey( locale, key ) + "???" );
        }

    }

}
