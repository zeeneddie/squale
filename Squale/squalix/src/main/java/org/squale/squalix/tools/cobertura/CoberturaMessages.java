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
package org.squale.squalix.tools.cobertura;

import org.squale.squalecommon.util.messages.BaseMessages;

/**
 * Messages class for CoberturaTask/CoberturaParser
 */
public final class CoberturaMessages
    extends BaseMessages
{

    /** Instance of CoberturaMessages */
    private static CoberturaMessages mMessages = new CoberturaMessages();

    /**
     * Default constructor
     */
    private CoberturaMessages()
    {
        /* Passing in the message.properties file which stores the messages */
        super( "org.squale.squalix.tools.cobertura.coberturaMessages" );
    }

    /**
     * Returns the String associate to the key in the file *.properties
     * 
     * @param pKey The key
     * @return The String associate to the key
     */
    public static String getMessage( String pKey )
    {
        return mMessages.getBundleString( pKey );
    }

    /**
     * Return the String associate to the key in the file *.properties and insert the object in the string
     * 
     * @param pKey The key name.
     * @param pValues List of arguments to insert
     * @return The string associate to the key with all arguments insert
     */
    public static String getMessage( String pKey, Object[] pValues )
    {
        return mMessages.getBundleString( pKey, pValues );
    }

    /**
     * Return the String associate to the key in the file *.properties and insert the argument in the string
     * 
     * @param pKey The key name
     * @param pArgument The argument to insert in the string
     * @return The string associate to the key with the argument insert
     */
    public static String getMessage( String pKey, Object pArgument )
    {
        return getMessage( pKey, new Object[] { pArgument } );
    }

}
