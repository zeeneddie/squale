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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\util\\csv\\CSVException.java

package com.airfrance.squalix.util.csv;

/**
 * Exception
 * 
 * @author m400842
 * @version 1.0
 */
public class CSVException
    extends Exception
{

    /**
     * Constructeur spécifiant un message.
     * 
     * @param pMessage Message de l'exception
     * @roseuid 429C2A390354
     */
    public CSVException( final String pMessage )
    {
        super( pMessage );
    }

    /**
     * Constructeur de recopie.
     * 
     * @param pE Nom de l'exaception cause
     * @roseuid 42D2560200AA
     */
    public CSVException( Exception pE )
    {
        super( pE );
    }
}
