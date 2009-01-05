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
 * Implementation of DatabaseType for MySQL
 */
public class MySQLType
    implements DatabaseType
{

    /**
     * {@inheritDoc}
     */
    public String toDate( String date )
    {
        StringBuffer query = new StringBuffer( " str_to_date( '" );
        query.append( date );
        query.append( "' , '%d/%m/%Y %T') " );
        return query.toString();
    }

    /**
     * {@inheritDoc}
     */
    public String resNumberLimit( int numberOfResults )
    {
        // we sort by type by name
        StringBuffer query = new StringBuffer( " order by  m1.component.class,  m1.component.name asc " );

        // we restrict the number of results
        query.append( " LIMIT = '" );
        query.append( numberOfResults );
        query.append( "'" );
        return query.toString();
    }

}
