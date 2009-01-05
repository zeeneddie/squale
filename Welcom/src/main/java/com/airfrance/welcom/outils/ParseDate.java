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
package com.airfrance.welcom.outils;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @deprecated utiliser DateUtil à la place
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ParseDate
{

    /** Format de la date/heure JDBC */
    public static SimpleDateFormat sdf_jdbc_date_heure;

    /** Format de la date francais */
    public static SimpleDateFormat sdf_francais;

    /** Format de la date jdbc */
    public static SimpleDateFormat sdf_jdbc_date;

    /**
     * Constructeur
     */
    public ParseDate()
    {
    }

    /**
     * Initialise la classe
     */
    static void instanciate()
    {
        if ( sdf_francais == null )
        {
            sdf_francais = new SimpleDateFormat( "dd/MM/yyyy" );
        }

        if ( sdf_jdbc_date_heure == null )
        {
            sdf_jdbc_date_heure = new SimpleDateFormat( "dd/MM/yyyy HH:mm" );
        }

        if ( sdf_jdbc_date == null )
        {
            sdf_jdbc_date = new SimpleDateFormat( "dd/MM/yyyy" );
        }
    }

    /**
     * Convertit une date en chaine
     * 
     * @param dateAConvertir dateAConvertir
     * @return resultat
     */
    public static String parseDatetoString( final String dateAConvertir )
    {
        try
        {
            if ( dateAConvertir != null )
            {
                instanciate();

                final java.util.Date date = sdf_jdbc_date_heure.parse( dateAConvertir );
                final String dateRenvoyee = sdf_francais.format( date );

                return dateRenvoyee;
            }
            else
            {
                return null;
            }
        }
        catch ( final Throwable theException )
        {
        }

        return null;
    }

    /**
     * Tranforme un date en chiane
     * 
     * @param dateAConvertir dateAConvertir
     * @return dateconvertie
     */
    public static String parseDatetoString( final Date dateAConvertir )
    {
        if ( dateAConvertir != null )
        {
            instanciate();

            final String dateRenvoyee = sdf_francais.format( dateAConvertir );

            return dateRenvoyee;
        }
        else
        {
            return null;
        }
    }

    /**
     * Tranforme un date en jdbc
     * 
     * @param psDate date
     * @return la vrai date
     * @throws ParseException probleme a la conversion
     */
    public static java.util.Date parseJdbcToDate( final String psDate )
        throws ParseException
    {
        instanciate();

        if ( !psDate.equals( "" ) )
        {
            return sdf_jdbc_date.parse( psDate );
        }
        else
        {
            return null;
        }
    }

    /**
     * tranforma une date en date heure
     * 
     * @param psDate date/heure
     * @return la date
     * @throws ParseException procleme a la conversion
     */
    public static java.util.Date parseJdbcToDateHeure( final String psDate )
        throws ParseException
    {
        instanciate();

        if ( !psDate.equals( "" ) )
        {
            return sdf_jdbc_date_heure.parse( psDate );
        }
        else
        {
            return null;
        }
    }

    /**
     * @deprecated utiliser DateUtil.parseAllDate à la place
     * @param date date
     * @return la date formatté
     * @throws ParseException execption si aucun parsing a gagné
     */
    public static java.util.Date parseAllDate( final String date )
        throws ParseException
    {
        instanciate();

        java.util.Date d = null;

        if ( !date.equals( "" ) )
        {
            try
            {
                d = sdf_jdbc_date_heure.parse( date );

                return d;
            }
            catch ( final ParseException e )
            {
                ;
            }

            try
            {
                d = sdf_jdbc_date.parse( date );

                return d;
            }
            catch ( final ParseException e )
            {
                throw e;
            }
        }
        else
        {
            return null;
        }
    }
}