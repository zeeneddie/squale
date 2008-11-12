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
