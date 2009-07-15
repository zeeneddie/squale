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
package org.squale.squaleweb.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.squale.squalecommon.util.TimeUtil;
import org.squale.squalecommon.util.manualmark.TimeLimitationParser;
import org.squale.squaleweb.resources.WebMessages;

/**
 * Utility method for the time limitation
 */
public final class TimelimitationUtil
{

    /** Key for always */
    public static final String ALWAYS_KEY = "timelimitation.always";

    /** Key for day */
    public static final String DAY_KEY = "timelimitation.unit.day";

    /** Key for month */
    public static final String MONTH_KEY = "timelimitation.unit.month";

    /** Key for year */
    public static final String YEAR_KEY = "timelimitation.unit.year";

    /** Number of month in one year */
    public static final int MONTH_IN_YEAR = 12;

    /** Key for out of date */
    public static final String OUT_OF_DATE = "timelimitation.outOfDate";

    /**
     * Private default constructor
     */
    private TimelimitationUtil()
    {

    }

    /**
     * This method take the String given in argument and split it in order to recover the associated period and unit.
     * The return array has in first position the period and in second position the unit.
     * 
     * @param timeLimitation the string to parse
     * @param local The current local
     * @return An array which contain duration and unit (in this order) of the validity period
     */
    public static String parseStringWithUnitTranslate( String timeLimitation, Locale local )
    {
        String periodUnit;
        if ( timeLimitation != null )
        {
            periodUnit = timeLimitation.substring( 0, timeLimitation.length() - 1 ) + " ";
            char unitIndication = timeLimitation.charAt( timeLimitation.length() - 1 );
            switch ( unitIndication )
            {
                // If the time limitation unit is day
                case 'D':
                    periodUnit = periodUnit + WebMessages.getString( local, TimelimitationUtil.DAY_KEY );
                    break;
                // If the time limitation unit is month
                case 'M':
                    periodUnit = periodUnit + WebMessages.getString( local, TimelimitationUtil.MONTH_KEY );
                    break;
                // If the time limitation unit is year
                case 'Y':
                    periodUnit = periodUnit + WebMessages.getString( local, TimelimitationUtil.YEAR_KEY );
                    break;
                // Else we do nothing
                default:
                    break;
            }
        }
        else
        {
            periodUnit = WebMessages.getString( local, TimelimitationUtil.ALWAYS_KEY );
        }
        return periodUnit;
    }

    /**
     * This method take the String given in argument and split it in order to recover the associated period and unit.
     * The return array has in first position the period and in second position the unit.
     * 
     * @param timeLimitation the string to parse
     * @param local The current local
     * @return An array which contain duration and unit (in this order) of the validity period
     */
    public static String[] parseString( String timeLimitation, Locale local )
    {
        String[] periodUnit = new String[2];
        if ( timeLimitation != null )
        {
            periodUnit[0] = timeLimitation.substring( 0, timeLimitation.length() - 1 );
            char unitIndication = timeLimitation.charAt( timeLimitation.length() - 1 );
            periodUnit[1] = String.valueOf( unitIndication );
        }
        else
        {
            periodUnit[0] = WebMessages.getString( local, TimelimitationUtil.ALWAYS_KEY );
            periodUnit[1] = "";
        }
        return periodUnit;
    }

    /**
     * This method return the time left before the end of validity of the mark. It's compute the difference in year
     * month and day between the two date.
     * 
     * @param endValidityCal The date of end of validity of the mark
     * @param local The current local
     * @return the time left in year month day
     */
    public static String timeleft( Calendar endValidityCal, Locale local )
    {
        String timeleft;
        Calendar cursorCal = TimeUtil.calDateOnly();
        timeleft = "";

        // If the mark is out of date
        if ( !endValidityCal.after( cursorCal ) )
        {
            timeleft = WebMessages.getString( local, TimelimitationUtil.OUT_OF_DATE );
        }
        // If the mark is not out of date
        else
        {
            // The difference in year between the cursor date and the date of end of validity of the mark
            int year = endValidityCal.get( Calendar.YEAR ) - cursorCal.get( Calendar.YEAR );
            // Add the difference in year to the cursor date
            cursorCal.add( Calendar.YEAR, year );
            int month = 0;
            // If the cursor is now after end validity date
            if ( endValidityCal.before( cursorCal ) )
            {
                // We subtract one to the year variable and we add 12 twelve to the month
                year = year - 1;
                month = MONTH_IN_YEAR;
            }
            //The difference of month which separate the two date
            int delta = endValidityCal.get( Calendar.MONTH ) - cursorCal.get( Calendar.MONTH );
            //Add this difference to the variable month
            month = month + delta;
            //Add this difference to the cursor date
            cursorCal.add( Calendar.MONTH, delta );
            int day = 0;
            // If now the cursor date is after the end of validity date
            if ( endValidityCal.before( cursorCal ) )
            {
                //We subtract one to the month variable
                month = month - 1;
                //We subtract one month to the cursor date
                cursorCal.add( Calendar.MONTH, -1 );
                //We add to the day variable the number of day that there is in the current month of the cursor date 
                //(28,29,30,31 according to the current month of the cursor date)
                int maxDay = cursorCal.getActualMaximum( Calendar.DAY_OF_MONTH );
                day = maxDay;
            }
            //Compute of the final number of day 
            day = day + endValidityCal.get( Calendar.DATE ) - cursorCal.get( Calendar.DATE );
            
            //Build of the String which represent the time left in year, month and day.
            if ( year > 0 )
            {
                timeleft = year + " " + WebMessages.getString( local, TimelimitationUtil.YEAR_KEY );
            }
            if ( month > 0 )
            {
                timeleft = timeleft + " " + month + " " + WebMessages.getString( local, TimelimitationUtil.MONTH_KEY );
            }
            if ( day > 0 )
            {
                timeleft = timeleft + " " + day + " " + WebMessages.getString( local, TimelimitationUtil.DAY_KEY );
            }
        }
        return timeleft;
    }

    /**
     * This method return the time left before the end of validity of the mark
     * 
     * @param validityPeriod The string which represent the period of validity
     * @param creationDate The date of creation of the mark
     * @param local The current local of the browser
     * @return a String which represent the time left before the end of validity of the mark
     */
    public static String timeleft( String validityPeriod, Date creationDate, Locale local )
    {
        String timeleft = null;
        Calendar endValidityCal = TimeLimitationParser.endValidityDate( validityPeriod, creationDate );
        timeleft = timeleft( endValidityCal, local );
        return timeleft;
    }
}
