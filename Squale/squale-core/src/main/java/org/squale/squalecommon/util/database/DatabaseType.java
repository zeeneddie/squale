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
package org.squale.squalecommon.util.database;

import java.util.Date;

/**
 * Interface which give access to the correct implementation according to the database used.
 */
public interface DatabaseType
{
    /**
     * The date pattern
     */
    String JAVADATEPATTERN = "dd/MM/yyyy HH:mm:ss";

    /**
     * Give the correct string for use the date in a query according to the database used.
     * 
     * @param date the date we want use in the query
     * @return the String to insert in the query
     */
    String toDate( Date date );

    /**
     * Give the correct syntax for add days to a date in a query according to the database used
     * 
     * @param date The date to which we want add days
     * @param day The number of days we want add
     * @return The modified date
     */
    String dateAddDay( String date, String day );

}
