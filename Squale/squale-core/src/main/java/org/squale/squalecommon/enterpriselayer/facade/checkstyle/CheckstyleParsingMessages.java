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
package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author sportorico
 */
public class CheckstyleParsingMessages
    extends BaseMessages
{
    /** Instance */
    static private CheckstyleParsingMessages mInstance = new CheckstyleParsingMessages();

    /**
     * Constructeur privé pour éviter l'instanciation
     */
    private CheckstyleParsingMessages()
    {
        super( "com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.checkstyle_messages" );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @param pValues les valeurs à insérer dans la chaine
     * @return la chaîne associée.
     */
    public static String getString( String pKey, Object[] pValues )
    {
        return mInstance.getBundleString( pKey, pValues );
    }
}
