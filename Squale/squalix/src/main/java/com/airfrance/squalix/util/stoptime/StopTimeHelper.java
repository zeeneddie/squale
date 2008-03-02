package com.airfrance.squalix.util.stoptime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import com.airfrance.squalecommon.datatransfertobject.config.StopTimeDTO;
import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Gestion de la date d'arrêt
 */
public class StopTimeHelper
{
    /** Nombre de minutes par heure */
    static final int MINUTES_PER_HOUR = 60;

    /** Nombre de minutes par jour */
    static final int MINUTES_PER_DAY = 60 * 24;

    /** Calendrier limite */
    private Calendar mLimitCal;

    /**
     * constructeur
     * 
     * @param pConf la conf squalix
     * @param pLaunchingCal le calendrier décrivant l'heure de lancement
     * @throws ConfigurationException si erreur
     */
    public StopTimeHelper( SqualixConfigurationDTO pConf, Calendar pLaunchingCal )
        throws ConfigurationException
    {
        buildLimitTime( pConf, pLaunchingCal );
    }

    /**
     * Sélectionne parmi la liste des StopTimeDTO de la config celui qui concerne l'audit courant
     * 
     * @param pConf la configuration squalix
     * @param pLaunchingCal l'heure de lancement du calendar
     * @throws ConfigurationException si erreur
     */
    private void buildLimitTime( SqualixConfigurationDTO pConf, Calendar pLaunchingCal )
        throws ConfigurationException
    {
        StopTimeDTO result = null;
        Collection col = pConf.getStopTimes();
        ArrayList lst = new ArrayList();
        lst.addAll( col );
        Collections.sort( lst, new StopTimeComparator() );
        // On fait le parcours de chaque stoptime pour trouver le plus proche dans le futur
        Iterator it = lst.iterator();
        long ref =
            pLaunchingCal.get( Calendar.DAY_OF_WEEK ) * MINUTES_PER_DAY + pLaunchingCal.get( Calendar.HOUR_OF_DAY )
                * MINUTES_PER_HOUR + pLaunchingCal.get( Calendar.MINUTE );
        while ( it.hasNext() && ( result == null ) )
        {
            StopTimeDTO time = (StopTimeDTO) it.next();
            long t =
                convertStringDayToIntDay( time.getDay() ) * MINUTES_PER_DAY
                    + convertStringHourToMinutes( time.getTime() );
            // Le premier trouvé est le bon puisque la liste est triée
            if ( ref < t )
            {
                result = time;
            }
        }
        // Si on n'a rien trouvé, la date de référence est plus grande
        // que toutes les dates, on retient donc la première date possible
        if ( result == null )
        {
            result = (StopTimeDTO) lst.get( 0 );
        }
        // On détermine le calendrier pour la date d'arrêt
        mLimitCal = Calendar.getInstance();
        mLimitCal.setTime( pLaunchingCal.getTime() );
        mLimitCal.set( Calendar.SECOND, 0 );
        int day = convertStringDayToIntDay( result.getDay() );
        if ( day >= 0 )
        {
            while ( mLimitCal.get( Calendar.DAY_OF_WEEK ) != day )
            {
                mLimitCal.add( Calendar.DAY_OF_MONTH, 1 );
            }
        }
        else
        {
            throw new ConfigurationException( "Format de jour inconnu : " + result.getDay() );
        }
        // On positionne l'heure/minute correspondantes
        long minutesInDay = convertStringHourToMinutes( result.getTime() );
        mLimitCal.set( Calendar.HOUR_OF_DAY, (int) minutesInDay / MINUTES_PER_HOUR );
        mLimitCal.set( Calendar.MINUTE, (int) minutesInDay % MINUTES_PER_HOUR );
    }

    /**
     * @return un booléen indiquant si l'heure limite d'exécution de squalix a été dépassée ou pas
     */
    public boolean isTimeToStop()
    {
        Calendar currentCal = Calendar.getInstance();
        return currentCal.after( mLimitCal );
    }

    /**
     * Convertit la chaine décrivant l'heure en long
     * 
     * @param pHour la chaine à convertir
     * @return le long correspondant
     */
    private long convertStringHourToMinutes( String pHour )
    {
        String[] tab = pHour.split( ":" );
        return ( ( ( new Long( tab[0] ).longValue() ) * MINUTES_PER_HOUR ) + ( new Long( tab[1] ).longValue() ) );
    }

    /**
     * Convertit un jour de la semaine en sa valeur correspondante dans la classe Calendar
     * 
     * @param pDay la chaine à convertir
     * @return la valeur de Calendar associée
     */
    private int convertStringDayToIntDay( String pDay )
    {
        int result = -1;
        // on compare pour convertir le jour de la semaine
        // sous forme de chaine de caractères en la constante
        // entière définie dans la classe Calendar.
        // on aurait aussi pu utiliser une HashMap mais c'est un
        // peu lourd pour seulement 7 cas
        if ( pDay.equalsIgnoreCase( "monday" ) )
        {
            result = Calendar.MONDAY;
        }
        else if ( pDay.equalsIgnoreCase( "tuesday" ) )
        {
            result = Calendar.TUESDAY;
        }
        else if ( pDay.equalsIgnoreCase( "wednesday" ) )
        {
            result = Calendar.WEDNESDAY;
        }
        else if ( pDay.equalsIgnoreCase( "thursday" ) )
        {
            result = Calendar.THURSDAY;
        }
        else if ( pDay.equalsIgnoreCase( "friday" ) )
        {
            result = Calendar.FRIDAY;
        }
        else if ( pDay.equalsIgnoreCase( "saturday" ) )
        {
            result = Calendar.SATURDAY;
        }
        else if ( pDay.equalsIgnoreCase( "sunday" ) )
        {
            result = Calendar.SUNDAY;
        }
        return result;
    }

    /**
     * Comparateur de date
     */
    class StopTimeComparator
        implements Comparator
    {

        /**
         * (non-Javadoc)
         * 
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        public int compare( Object o1, Object o2 )
        {
            StopTimeDTO st1 = (StopTimeDTO) o1;
            StopTimeDTO st2 = (StopTimeDTO) o2;
            int result = 0;
            long d1 =
                convertStringDayToIntDay( st1.getDay() ) * MINUTES_PER_DAY + convertStringHourToMinutes( st1.getTime() );
            long d2 =
                convertStringDayToIntDay( st2.getDay() ) * MINUTES_PER_DAY + convertStringHourToMinutes( st2.getTime() );
            if ( d1 > d2 )
            {
                result = 1;
            }
            else if ( d1 < d2 )
            {
                result = -1;
            }
            return result;
        }
    }

    /**
     * @return date limite
     */
    public Calendar getLimitCal()
    {
        return mLimitCal;
    }

}
