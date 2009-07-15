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
package org.squale.squalix.tools.abstractgenerictask;

import org.squale.squalecommon.util.messages.BaseMessages;

/**
 * This class retrieves messages from the *.properties file in which are stored the key and values related to the
 * GenericTask.
 */
public final class AbstractGenericTaskMessages
    extends BaseMessages
{
    /** Instance of {@link AbstractGenericTask} */
    private static AbstractGenericTaskMessages mMessages = new AbstractGenericTaskMessages();

    /**
     * A private constructor to avoid any new instance
     */
    private AbstractGenericTaskMessages()
    {
        super( "org.squale.squalix.tools.abstractgenerictask.AbstractGenericTaskMessages" );
    }

    /**
     * This method returns the value of a key passed in as parameter
     * 
     * @param pKey the key the value of which has to be retrieved
     * @return the value of the key stored in the *.properties file
     */
    public static String getMessage( String pKey )
    {
        return mMessages.getBundleString( pKey );
    }

    /**
     * This method returns the value of a key passed in as parameter
     * 
     * @param pKey name of the key
     * @param pValues values that has to be concatenated in the String
     * @return the concatenated String
     */
    public static String getMessage( String pKey, Object[] pValues )
    {
        return mMessages.getBundleString( pKey, pValues );
    }

    /**
     * This method returns the value of a key passed in as parameter
     * 
     * @param pKey name of the key
     * @param pArgument argument
     * @return the concatenated String
     */
    public static String getMessage( String pKey, Object pArgument )
    {
        return getMessage( pKey, new Object[] { pArgument } );
    }
}
