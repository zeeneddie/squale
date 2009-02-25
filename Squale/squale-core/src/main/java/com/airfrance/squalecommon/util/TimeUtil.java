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
package com.airfrance.squalecommon.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This class give some utility method for the date
 */
public final class TimeUtil
{
    /**
     * Private constructor
     */
    private TimeUtil()
    {

    }

    /**
     * This method set the hour of day, minute, second and millisecond value of the calendar give in argument to 0
     * 
     * @param iniCal The calendar to format
     * @return The formated calendar
     */
    public static Calendar calDateOnly( Calendar iniCal )
    {
        Calendar cal = iniCal;
        cal.set( Calendar.HOUR_OF_DAY, 0 );
        cal.set( Calendar.MINUTE, 0 );
        cal.set( Calendar.SECOND, 0 );
        cal.set( Calendar.MILLISECOND, 0 );
        return cal;
    }

    /**
     * This method return a new calendar set to the today date. It's hour of day, minute, second and millisecond value
     * are set to 0
     * 
     * @return a formated calendar set to today date
     */
    public static Calendar calDateOnly()
    {
        Calendar cal = new GregorianCalendar();
        cal.set( Calendar.HOUR_OF_DAY, 0 );
        cal.set( Calendar.MINUTE, 0 );
        cal.set( Calendar.SECOND, 0 );
        cal.set( Calendar.MILLISECOND, 0 );
        return cal;
    }

}
