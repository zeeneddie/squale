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
package org.squale.squalix.messages;

import org.squale.squalecommon.util.messages.BaseMessages;
import java.util.TreeMap;

/**
 * Permet l'externalisation des chaînes de caractères
 * 
 * @author M400842
 */
public class Messages
    extends BaseMessages
{
    /** Instance */
    static private Messages mInstance = new Messages();

    /**
     * Constructeur privé pour éviter l'instanciation
     * 
     * @roseuid 42C1678502C4
     */
    private Messages()
    {
        super( "org.squale.squalix.messages.squalix" );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     * @roseuid 42C1678502C5
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @param pValues les paramètres
     * @return la chaîne associée.
     * @roseuid 42C1678502C5
     */
    public static String getString( String pKey, String[] pValues )
    {
        return mInstance.getBundleString( pKey, pValues );
    }
    
    /**
     * Indique si une chaîne existe ou pas pour une clé donnée sans générer d'exception
     * @param pKey nom de la clé
     * @return Vrai si la clé existe, False sinon
     */
    public static Boolean existString ( String pKey )
    {
        return mInstance.existBundleString( pKey );
    }
    
    /**
     * Retourne la chaîne de caractère identifiée par la clé
     * @param pKey nom de la clé
     * @param pValues valeurs à supléer dans la chaîne
     * @return la chaîne associée avec les valeurs suplées si nécessaire
     */
    public static String getMessage ( String pKey, String[] pValues )
    {
        if (pValues==null)
        {
            return getString(pKey);
        }
        else
        {
            return getString(pKey,pValues);
        }
    }

}
