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
