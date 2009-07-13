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
package org.squale.welcom.taglib.field;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;
import org.squale.welcom.outils.Util;


/**
 * input text pour une date
 * 
 * @author Fabien LEBRERE
 * @version 1.0
 */
public class DateTag
    extends BaseDateHeureTag
{

    /** logger */
    private static Log log = LogFactory.getLog( DateTag.class );

    /** parametre du tag */
    private String dateFormat;

    /** parametre du tag */
    private String dateFormatKey;

    /** parametre du tag */
    private java.text.SimpleDateFormat simpleDateFormat = null;

    /** datePattern (pour ne le chercher qu'une fois dans le ApplicationResource) */
    private String datePattern;

    /**
     * Dessine le contenu du tag
     * 
     * @param sb string buffer
     * @throws JspException erreur sur le recupration de la value
     */
    protected void doRender( StringBuffer sb )
        throws JspException
    {
        doRenderInput( sb, TYPE_WDATE );
        if ( !getDisabled() )
        {
            doRenderCalendar( sb, getCalendarId() );
        }
        else
        {
            sb.append( "&nbsp;" );
        }
    }

    /**
     * Retourne le simple date/hour format key en fonction du type si TYPE_WDATE alors dateFormat si TYPE_WHOUR alors
     * hourFormat
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le date/hour format
     */
    protected String getFormatDateOrHourFormat( int typeOfElement )
    {
        return dateFormat;
    }

    /**
     * Retourne le simple date/hour format key en fonction du type si TYPE_WDATE alors dateFormatKey si TYPE_WHOUR alors
     * hourFormatKey
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le date/hour formatKey
     */
    protected String getFormatKeyDateOrHourFormat( int typeOfElement )
    {
        return dateFormatKey;
    }

    /**
     * Retourne le simple date format en fonction du type si TYPE_WDATE alors simpleDateFormat si TYPE_WHOUR alors
     * simpleHourFormat
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le simple date format
     */
    protected SimpleDateFormat getSimpleDateOrHourFormat( int typeOfElement )
    {
        return simpleDateFormat;
    }

    /**
     * Retourne le nom du parametre dans la requete si TYPE_WDATE alors super.property + "WDate"; si TYPE_WHOUR alors
     * uper.property + "WHour";
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le nom du parametre dans la requete
     */
    protected String getParameterNameInRequest( int typeOfElement )
    {
        return super.property + "WDate";
    }

    /**
     * Retourne le simple date/hour format key en fonction du type si TYPE_WDATE alors dateFormat si TYPE_WHOUR alors
     * hourFormat
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le date/hour format
     */
    protected String getDateOrHourPattern( int typeOfElement )
    {
        return datePattern;
    }

    /**
     * Recupere le format par defaut Util.stringFormatDt ou Util.stringFormatHr
     * 
     * @param typeOfElement typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le formet de la date par defaut
     */
    protected String getDefaultFormatDateOrHour( int typeOfElement )
    {
        return Util.stringFormatDt;
    }

    /**
     * Sauve le nouveau simpledate format a partir de celui fournit par le pattern
     * 
     * @param typeOfElement typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @param SimpleDateFormat SimpleDateFormat
     */
    protected void saveSimpleDateFormatsForDateOrHour( int typeOfElement, SimpleDateFormat sdf )
    {
        simpleDateFormat = sdf;
    }

    /**
     * Stocke le pattern de la date
     * 
     * @param typeOfElement typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @param patten pattern
     */
    protected void setDateOrHourPattern( int typeOfElement, String patten )
    {
        datePattern = patten;
    }

    /**
     * Retourne DATE ou HEURE pour la fonction javascript si TYPE_WDATE alors DATE si TYPE_WHOUR alors HEURE
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return DATE ou HEURE
     */
    protected String getJavascriptDateOrHour( int typeOfElement )
    {
        return "DATE";
    }

    /**
     * @param newDateFormat le dateFormat
     */
    public void setDateFormat( final java.lang.String newDateFormat )
    {
        if ( GenericValidator.isBlankOrNull( newDateFormat ) )
        {
            dateFormat = getDefaultFormatDateOrHour( TYPE_WDATE );
        }
        else
        {
            dateFormat = newDateFormat;
        }
    }

    /**
     * on cherche si le dateFormatKey est defini, sinon, on prend le pattern du dateFormat, sinon on prend
     * Util.stringFormatDt pareil pour les heures...
     */

    protected void updateSimpleDateFormats( MessageResources resources, Locale localeRequest, HashMap hashMap )
    {
        updateSimpleDateFormat( TYPE_WDATE, resources, localeRequest, hashMap );
    }

    /**
     * @see org.apache.struts.taglib.html.BaseFieldTag#release()
     */
    public void release()
    {
        super.release();
        dateFormat = "";
        dateFormatKey = "";
        simpleDateFormat = null;

    }

    /**
     * @return dateFormat
     */
    public String getDateFormat()
    {
        return dateFormat;
    }

    /**
     * @param format le simpleDateFormat
     */
    public void setSimpleDateFormat( final java.text.SimpleDateFormat format )
    {
        simpleDateFormat = format;
    }

    /**
     * @return dateFormatKey
     */
    public String getDateFormatKey()
    {
        return dateFormatKey;
    }

    /**
     * @param string dateFormatKey
     */
    public void setDateFormatKey( final String string )
    {
        dateFormatKey = string;
    }

    /**
     * @return simpleDateFormat
     */
    public java.text.SimpleDateFormat getSimpleDateFormat()
    {
        return simpleDateFormat;
    }

}