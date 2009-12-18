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
package org.squale.squaleexport.message;

import org.squale.squalecommon.util.messages.BaseMessages;

/**
 * This class is the implementation for the message for the export functionality
 */
public final class ExportMessages
    extends BaseMessages
{

    /** Instance */
    private static ExportMessages mInstance = new ExportMessages();

    /**
     * Private constructor
     */
    private ExportMessages()
    {
        super( "org.squale.squaleexport.messages.exportMessages" );
    }

    /**
     * Return the string associate to the key  
     * 
     * @param pKey The key
     * @return the String associate to the key
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

    /**
     * Return the string associate to the key
     * 
     * @param pKey The key
     * @param pValues The values to introduce in the return String 
     * @return the String associate to the key with the values
     */
    public static String getString( String pKey, Object[] pValues )
    {
        return mInstance.getBundleString( pKey, pValues );
    }

    /**
     * Return the String associate to the key
     * 
     * @param pKey The key
     * @param pArgument The argument
     * @return the associate string
     */
    public static String getString( String pKey, Object pArgument )
    {
        return getString( pKey, new Object[] { pArgument } );
    }

}
