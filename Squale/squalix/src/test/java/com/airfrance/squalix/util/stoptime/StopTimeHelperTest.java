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
package com.airfrance.squalix.util.stoptime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import com.airfrance.squalecommon.datatransfertobject.config.StopTimeDTO;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 */
public class StopTimeHelperTest
    extends SqualeTestCase
{

    /** configuration squalix */
    private SqualixConfigurationDTO mConf;

    /** méthode set_up */
    public void setUp()
    {
        try
        {
            super.setUp();
            mConf = new SqualixConfigurationDTO();
            StopTimeDTO stop1 = new StopTimeDTO();
            stop1.setDay( "Monday" );
            stop1.setTime( "04:00" );
            StopTimeDTO stop2 = new StopTimeDTO();
            stop2.setDay( "Tuesday" );
            stop2.setTime( "04:00" );
            StopTimeDTO stop3 = new StopTimeDTO();
            stop3.setDay( "Wednesday" );
            stop3.setTime( "04:00" );
            StopTimeDTO stop4 = new StopTimeDTO();
            stop4.setDay( "Thursday" );
            stop4.setTime( "04:00" );
            StopTimeDTO stop5 = new StopTimeDTO();
            stop5.setDay( "Friday" );
            stop5.setTime( "04:00" );
            Collection col = new ArrayList();
            col.add( stop1 );
            col.add( stop2 );
            col.add( stop3 );
            col.add( stop4 );
            col.add( stop5 );
            mConf.setStopTimes( col );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * test pour vérifier que le stopTimeHelper fonctionne correctement
     */
    public void testStopTimeHelper()
    {
        try
        {
            Calendar cal = Calendar.getInstance();
            final int year = 2006;
            final int day = 6;
            final int hour = 20;
            cal.set( year, Calendar.OCTOBER, day, hour, 0, 0 ); // vendredi 6 octobre 2006 20:00:00
            cal.set( Calendar.HOUR_OF_DAY, hour );
            cal.set( Calendar.MINUTE, 0 );
            cal.set( Calendar.SECOND, 0 );
            // Cas 1 on se place à la fin de la semaine, la date d'arrêt attendue est
            // le lundi suivant
            StopTimeHelper stop;
            stop = new StopTimeHelper( mConf, cal );
            Calendar limitCal = stop.getLimitCal();
            assertEquals( 9, limitCal.get( Calendar.DAY_OF_MONTH ) );
            assertEquals( 4, limitCal.get( Calendar.HOUR_OF_DAY ) );
            assertEquals( 0, limitCal.get( Calendar.MINUTE ) );
            // Cas 2 on se place le lundi 09/10/2006 à 03:59
            cal.set( Calendar.DAY_OF_MONTH, 9 );
            cal.set( Calendar.HOUR_OF_DAY, 3 );
            cal.set( Calendar.MINUTE, 59 );
            stop = new StopTimeHelper( mConf, cal );
            limitCal = stop.getLimitCal();
            assertEquals( 9, limitCal.get( Calendar.DAY_OF_MONTH ) );
            assertEquals( 4, limitCal.get( Calendar.HOUR_OF_DAY ) );
            assertEquals( 0, limitCal.get( Calendar.MINUTE ) );
            // Cas 2 on se place le lundi à 04:00
            cal.set( Calendar.HOUR_OF_DAY, 4 );
            cal.set( Calendar.MINUTE, 1 );
            stop = new StopTimeHelper( mConf, cal );
            limitCal = stop.getLimitCal();
            assertEquals( 10, limitCal.get( Calendar.DAY_OF_MONTH ) );
            assertEquals( 4, limitCal.get( Calendar.HOUR_OF_DAY ) );
            assertEquals( 0, limitCal.get( Calendar.MINUTE ) );
        }
        catch ( ConfigurationException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
