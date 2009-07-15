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
package org.squale.squalix.util.csv;

import org.squale.squalecommon.util.messages.BaseMessages;

/**
 * @author m400842 (by rose)
 * @version 1.0
 */
public class CSVMessages
    extends BaseMessages
{
    /** Instance */
    static private CSVMessages mInstance = new CSVMessages();

    /**
     * Retourne la chaîne de caractère spécifiée par la clé.
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
     * Constructeur privé pour éviter l'instanciation
     * 
     * @roseuid 42CE768B01BC
     */
    private CSVMessages()
    {
        super( "org.squale.squalix.util.csv.csv" );
    }
}
