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
package org.squale.squalecommon.util.mapping;

import org.squale.squalecommon.util.messages.BaseMessages;

/**
 * Implementation of the message for mapping
 */
public final class MappingMessages
    extends BaseMessages
{

    /** Create the singleton */
    private static MappingMessages instance = new MappingMessages();

    /**
     * Default constructor 
     */
    private MappingMessages()
    {
        super( "org.squale.squalecommon.util.mapping.mapping_messages" );
    }

    /**
     * Return the string link to the key.
     * 
     * @param pKey The key to search.
     * @return The associated string.
     */
    public static String getString( String pKey )
    {
        return instance.getBundleString( pKey );
    }

}
