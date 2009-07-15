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
package org.squale.squalix.core.quartz;

import org.squale.squalecommon.util.messages.BaseMessages;

/**
 * This class permit to get back a message associate to a key. So there is only the key in the code and all real
 * messages are in a same file outside the code.
 */
public final class QuartzMessages
    extends BaseMessages
{

    /** Instance of JavancssMessages */
    private static QuartzMessages mMessages = new QuartzMessages();

    /**
     * Default constructor
     */
    private QuartzMessages()
    {
        super( "org.squale.squalix.core.quartz.quartzMessages" );
    }

    /**
     * Return the String associate to the key in the file *.properties
     * 
     * @param pKey The key
     * @return The String associate to the key
     */
    public static String getString( String pKey )
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
    public static String getString( String pKey, Object[] pValues )
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
    public static String getString( String pKey, Object pArgument )
    {
        return getString( pKey, new Object[] { pArgument } );
    }

}
