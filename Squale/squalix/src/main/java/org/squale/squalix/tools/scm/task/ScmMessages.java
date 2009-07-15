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
package org.squale.squalix.tools.scm.task;

import org.squale.squalecommon.util.messages.BaseMessages;

/**
 * Récupérateur des messages pour l'analyseur de source
 */
public class ScmMessages
    extends BaseMessages
{
    /** Instance */
    private static ScmMessages mInstance = new ScmMessages();

    /**
     * Private constructor to avoid new creations of instances
     */
    private ScmMessages()
    {
        super( "org.squale.squalix.tools.scm.task.scm" );
    }

    /**
     * Returns string relating to a key
     * 
     * @param pKey key name.
     * @return description.
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

    /**
     * Returns string relating to a key
     * 
     * @param pKey key name.
     * @param pValues values to insert in the string
     * @return description.
     */
    public static String getString( String pKey, Object[] pValues )
    {
        return mInstance.getBundleString( pKey, pValues );
    }

    /**
     * Get description relating to a key
     * 
     * @param pKey key
     * @param pArgument argument
     * @return description
     */
    public static String getString( String pKey, Object pArgument )
    {
        return getString( pKey, new Object[] { pArgument } );
    }
}
