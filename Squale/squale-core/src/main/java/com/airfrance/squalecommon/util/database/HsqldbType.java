package com.airfrance.squalecommon.util.database;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of DatabaseType for hsqldb
 */
public class HsqldbType
    implements DatabaseType
{

    /**
     * {@inheritDoc}
     */
    public String dateAddDay( String date, String day )
    {
        StringBuffer query = new StringBuffer( " DATEDIFF( 'dd' ," );
        query.append( date );
        query.append( " , " );
        query.append( toDate( new Date() ) );
        query.append( " ) " );
        query.append( " > " );
        query.append( day );
        query.append( " " );
        return query.toString();
    }

    /**
     * {@inheritDoc}
     */
    public String toDate( Date date )
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        String dateString = dateFormat.format( date );
        StringBuffer query = new StringBuffer( "'" );
        query.append( dateString );
        query.append( "' " );
        return query.toString();
    }

}
