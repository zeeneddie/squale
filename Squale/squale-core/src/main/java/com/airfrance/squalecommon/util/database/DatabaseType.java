package com.airfrance.squalecommon.util.database;

/**
 * Interface which give access to the correct implementation according to the database used.
 */
public interface DatabaseType
{

    /**
     * Give the correct string for use the date in a query according to the database used.
     * 
     * @param date the date we want use in the query
     * @return the String to insert in the query
     */
    String toDate( String date );

    /**
     * Give the correct sequence for the query according to the database used. Method specific for the query write in
     * the method findChangedComponentWhere from MarkDAOImpl. The aim is limit the result to numberOfResult and order
     * the result by type and by name
     * 
     * @param numberOfResults maximum number of results
     * @return the String to insert in the query
     */
    String resNumberLimit( int numberOfResults );

}
