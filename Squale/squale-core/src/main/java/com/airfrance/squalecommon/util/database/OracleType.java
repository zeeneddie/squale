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
package com.airfrance.squalecommon.util.database;

/**
 * Implementation of DatabaseType for Oracle
 */
public class OracleType
    implements DatabaseType
{

    /**
     * {@inheritDoc}
     */
    public String toDate( String date )
    {
        StringBuffer query = new StringBuffer( "to_date('" );
        query.append( date );
        query.append( "' , 'DD/MM/YYYY HH24:mi:ss') " );
        return query.toString();
    }

    /**
     * {@inheritDoc}
     */
    public String resNumberLimit( int numberOfResults )
    {

        // we restrict the number of results
        StringBuffer query = new StringBuffer( " and rownum < " );
        query.append( numberOfResults );

        // we sort by type by name
        query.append( " order by  m1.component.class,  m1.component.name asc " );
        return query.toString();

    }

}
