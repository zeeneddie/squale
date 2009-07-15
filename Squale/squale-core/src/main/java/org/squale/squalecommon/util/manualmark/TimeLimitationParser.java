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
package org.squale.squalecommon.util.manualmark;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.squale.squalecommon.util.TimeUtil;

/**
 * Utility class for the manual mark
 */
public final class TimeLimitationParser
{

    /**
     * Private constructor
     */
    private TimeLimitationParser()
    {

    }

    /**
     * This method return the date of end of validity of the current note
     * 
     * @param validityPeriod The string to parse
     * @param creationDate Date of creation
     * @return The date of end of validity
     */
    public static Calendar endValidityDate( String validityPeriod, Date creationDate )
    {
        Calendar calCreation = new GregorianCalendar();
        if ( validityPeriod != null )
        {
            // Date of creation of the manual mark
            calCreation.setTime( creationDate );
            calCreation = TimeUtil.calDateOnly( calCreation );

            // The validity period
            char unitIndication = validityPeriod.charAt( validityPeriod.length() - 1 );
            int period = Integer.parseInt( validityPeriod.substring( 0, validityPeriod.length() - 1 ) );
            switch ( unitIndication )
            {
                // If the time limitation unit is day
                case 'D':
                    calCreation.add( Calendar.DATE, period );
                    break;

                // If the time limatation unit is month
                case 'M':
                    calCreation.add( Calendar.MONTH, period );
                    break;

                // If the time limitation unit is year
                case 'Y':
                    calCreation.add( Calendar.YEAR, period );
                    break;

                // Else we do nothing.
                default:
                    break;
            }
        }
        return calCreation;
    }

    /**
     * This method use the period and unit give in argument to create a well formated string for being save as
     * timeLimitation in the database. If at least one of the argument is null then the string return is 'A' (for
     * always). Else the String return is the period concatenate with the first letter (in upper case) of the unit : 'D'
     * or 'M' or 'Y' (for DAY, MONTH, YEAR)
     * 
     * @param period The duration of the validity period
     * @param unit The unit of the period
     * @return The timeLimiation string formated for being record in the database
     */
    public static String periodUnitToString( String period, String unit )
    {
        // By default we set timelimitation to null in the return String.
        // When timelimitation is null, that means there is no time limitation.
        String timeLimitation = null;
        if ( period != null && unit != null )
        {
            // First part of the String return
            timeLimitation = period;

            // If the time limitation unit is day
            if ( unit.toUpperCase().equals( "DAY" ) )
            {
                timeLimitation = timeLimitation + 'D';
            }
            // If the time limitation unit is month
            else if ( unit.toUpperCase().equals( "MONTH" ) )
            {
                timeLimitation = timeLimitation + 'M';
            }
            // else the time limitation unit is year
            else if ( unit.toUpperCase().equals( "YEAR" ) )
            {
                timeLimitation = timeLimitation + 'Y';
            }
        }
        return timeLimitation;
    }

    /**
     * This method indicate if the mark is valid. If the endValidityDate is before the cursorDate, then the mark is not
     * valid
     * 
     * @param cursorDate First date to compare
     * @param endValidityDate Second date to compare
     * @return true if the mark is valid
     */
    public static boolean isMarkValid( Calendar cursorDate, Calendar endValidityDate )
    {
        boolean valid = true;
        // Format of the date
        Calendar currentCal = TimeUtil.calDateOnly( cursorDate );
        Calendar endValidityCal = TimeUtil.calDateOnly( endValidityDate );
        if ( !endValidityCal.after( currentCal ) )
        {
            valid = false;
        }
        return valid;
    }

    /**
     * This method indicate if the mark is valid. If the date of creation + the validity period give a date which is
     * before today, then the mark is not valid
     * 
     * @param validityPeriod string which represents the duration of the validity period
     * @param creationDate Date of creation of the mark
     * @return true if the mark is valid
     */
    public static boolean isMarkValid( String validityPeriod, Date creationDate )
    {
        boolean valid = true;
        if(validityPeriod!=null)
        {
            Calendar endValidityCal = endValidityDate( validityPeriod, creationDate );
            Calendar currentCal = TimeUtil.calDateOnly();
            valid = isMarkValid( currentCal, endValidityCal );
        }
        return valid;
    }
}
