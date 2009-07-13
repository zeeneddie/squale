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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;
import org.squale.welcom.outils.Charte;
import org.squale.welcom.outils.DateUtil;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.field.util.TagUtils;


public abstract class BaseDateHeureTag
    extends org.apache.struts.taglib.html.BaseFieldTag
{

    /** logger */
    private static Log log = LogFactory.getLog( DateHeureTag.class );

    /** Retourn si la reecriture du check doit etre effectué */
    private boolean checkEnabled = false;

    /** parametre du tag */
    private int calendarBeginYear;

    /** parametre du tag */
    private int calendarEndYear;

    /** TYpe date */
    public static final int TYPE_WDATE = 1;

    /** type heure */
    public static final int TYPE_WHOUR = 2;

    /** Ancien on blur */
    private String oldOnBlur = "";

    /** pour savoir si la verification est necessaire */
    private boolean checkenabled = false;

    /**
     * Ajout l'attribut avec sa valeur au stringbuffer
     * 
     * @param sb stringbuffer
     * @param name nom
     * @param value valeur
     */
    protected void addParam( StringBuffer sb, String name, String value )
    {

        TagUtils.addParam( sb, name, value );

    }

    /**
     * Retourne si le check a etait mis en place le supprime la cas echeant
     * 
     * @return si le check a etait mis en place
     */
    private boolean isCheckEnabled()
    {
        if ( checkEnabled == false && getOnchange() != null )
        {
            // String regExpFunctJS = "checkValue\\([\\w,\\s'\\)\\(]*\\);";
            String regExpFunctJS = "checkValue\\([\\w\\s[\\'.\\'],\\.\\[\\]]*\\);";

            String s = getOnchange().replaceAll( regExpFunctJS, "" );
            checkEnabled = !getOnchange().equals( s );
            if ( GenericValidator.isBlankOrNull( s ) )
            {
                setOnchange( null );
            }
            else
            {
                setOnchange( s );
            }
        }
        return checkEnabled;
    }

    /**
     * Generate the required input tag. Indexed property since 1.1
     * 
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        final StringBuffer results = new StringBuffer();

        // A supprimer uterieurmenet
        checkenabled = isCheckEnabled();
        oldOnBlur = getOnblur();

        doRender( results );

        // Print this field to our output writer
        ResponseUtils.write( pageContext, results.toString() );

        // Continue processing this page
        return ( EVAL_BODY_BUFFERED );
    }

    /**
     * Dessine le contenu du tag
     * 
     * @param sb string buffer
     * @throws JspException erreur sur le recupration de la value
     */
    protected abstract void doRender( StringBuffer sb )
        throws JspException;

    /**
     * Dessine les champs inputs
     * 
     * @param sb string buffer
     * @param currentType type TYPE_WDATE / TYPE_WHOUR
     * @throws JspException erreur sur le recupration de la value
     */
    protected void doRenderInput( StringBuffer sb, int currentType )
        throws JspException
    {
        sb.append( "<input type=\"text\" " );
        addParam( sb, "name", getParameterNameInRequest( currentType ) );
        addParam( sb, "accesskey", accesskey );
        addParam( sb, "accept", accept );
        addParam( sb, "maxlength", Integer.toString( getFormatDateOrHourFormat( currentType ).length() ) );
        addParam( sb, "size", Integer.toString( getFormatDateOrHourFormat( currentType ).length() + 2 ) );
        addParam( sb, "tabindex", tabindex );

        doRenderValue( sb, currentType );

        // ajoute le control javascript
        setOnblur( TagUtils.getJavascriptCheckValue( property, getJavascriptDateOrHour( currentType ), false, false,
                                                     false, false, getDateOrHourPattern( currentType ) )
            + ( oldOnBlur == null ? "" : oldOnBlur ) );

        sb.append( prepareStyles() );

        sb.append( prepareEventHandlers() );

        if ( currentType == TYPE_WDATE )
        {
            addParam( sb, "id", getCalendarId() );
        }

        sb.append( ">" );
    }

    /**
     * Retourne l'id du calendrier
     * 
     * @return retourne l'id du calendrier
     */
    protected String getCalendarId()
    {
        if ( !GenericValidator.isBlankOrNull( super.id ) )
        {
            return super.id;
        }
        String temp = getParameterNameInRequest( TYPE_WDATE );
        String id = "";
        for ( int ind = 0; ind < temp.length(); ind++ )
        {
            final char c = temp.charAt( ind );

            if ( ( c != '.' ) && ( c != '[' ) && ( c != ']' ) )
            {
                id = id + c;
            }
        }
        return id;
    }

    /**
     * Affiche le calendrier
     * 
     * @param results buffer
     * @param id id du field
     */
    protected void doRenderCalendar( final StringBuffer results, final String id )
    {
        results.append( "<a href=\"#\"" );
        if ( WelcomConfigurator.getCharte() == Charte.V3_001 )
        {
            results.append( " class=\"nobottom\"" );
        }
        results.append( " id=\"ancreimagecalendrier\" onclick=\"this.blur()\">" );
        results.append( "<img src=\"" );
        results.append( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_IMG ) );
        results.append( WelcomConfigurator.getMessageWithCfgChartePrefix( ".calendrier.gif" ) );
        results.append( "\"  altKey=\"welcom.internal.tooltip.selDate\" border=\"0\" align=\"absmiddle\"" );
        results.append( " onclick=\"javascript:afficherCalendrier('" );
        results.append( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_HTML ) );
        results.append( WelcomConfigurator.getMessageWithCfgChartePrefix( ".calendrier.html" ) );
        results.append( "',document.getElementById('" );
        results.append( id );
        results.append( "')" );

        final String beginYear = WelcomConfigurator.getMessage( WelcomConfigurator.CALENDAR_DEFAULT_BEGIN_YEAR );

        if ( ( calendarBeginYear == 0 ) && !GenericValidator.isBlankOrNull( beginYear ) )
        {
            calendarBeginYear = Integer.parseInt( beginYear );
        }

        final String endYear = WelcomConfigurator.getMessage( WelcomConfigurator.CALENDAR_DEFAULT_END_YEAR );

        if ( ( calendarEndYear == 0 ) && !GenericValidator.isBlankOrNull( endYear ) )
        {
            calendarEndYear = Integer.parseInt( endYear );
        }

        results.append( ",null,null," );

        if ( calendarBeginYear > 0 )
        {
            results.append( calendarBeginYear );
            results.append( "," );
        }
        else
        {
            results.append( "null," );
        }

        if ( calendarEndYear > 0 )
        {
            results.append( calendarEndYear );
        }
        else
        {
            results.append( "null" );
        }
        results.append( ",'" );
        results.append( getDateOrHourPattern( TYPE_WDATE ) );
        results.append( "'" );

        if ( !( (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY ) ).getLanguage().equals( "fr" ) )
        {
            results.append( ",'en'" );
        }
        results.append( " );return false;\"></a>" );
    }

    /**
     * Gere la value
     * 
     * @param results le stringBuffer
     * @param currentType TYPE_WDATE / TYPE_WHOUR
     * @throws JspException Exception pouvant etre levee
     */
    public void doRenderValue( final StringBuffer results, int currentType )
        throws JspException
    {

        // on met a jour le simpleDateFormat
        updateSimpleDateFormats();

        results.append( " value=\"" );

        if ( value != null )
        {
            results.append( ResponseUtils.filter( value ) );
        }
        else if ( redisplay || !"password".equals( type ) )
        {
            final Object value = RequestUtils.lookup( super.pageContext, super.name, super.property, null );
            String renderValue = "";

            if ( value == null )
            {
                renderValue = doRenderValueFromError( currentType );
            }
            else
            {
                if ( value instanceof Date )
                {
                    renderValue = getSimpleDateOrHourFormat( currentType ).format( value );
                }
                else
                {

                    try
                    {
                        if ( ( ( (String) value ).length() < 1 ) || ( ( (String) value ).indexOf( "null" ) >= 0 ) )
                        {
                            renderValue = "";
                        }
                        else
                        {
                            // Format la chaine
                            if ( getFormatKeyDateOrHourFormat( currentType ) != null
                                && GenericValidator.isLong( (String) value ) )
                            {
                                renderValue =
                                    getSimpleDateOrHourFormat( currentType ).format(
                                                                                     new Date(
                                                                                               Long.parseLong( (String) value ) ) );
                            }
                            else
                            {
                                renderValue =
                                    getSimpleDateOrHourFormat( currentType ).format(
                                                                                     DateUtil.parseAllDate( (String) value ) );
                            }
                        }
                    }
                    catch ( final Exception ex )
                    {
                        // On ne loggue pas l'exception, on prefere retouver la
                        // valeur saisie
                        // log.error(ex, ex);
                        renderValue = (String) value;
                    }
                }
            }

            results.append( ResponseUtils.filter( renderValue ) );
        }

        results.append( "\"" );
    }

    /**
     * Retrouve la valeur si elle viens suite a une soumission avec erreur
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return la valeur statocké en requete
     */
    private String doRenderValueFromError( int typeOfElement )
    {
        SimpleDateFormat sdf = getSimpleDateOrHourFormat( typeOfElement );
        String valString = pageContext.getRequest().getParameter( getParameterNameInRequest( typeOfElement ) );
        if ( !GenericValidator.isBlankOrNull( valString ) )
        {
            try
            {
                sdf.setLenient( false );
                sdf.parse( valString );
            }
            catch ( ParseException e )
            {
                return valString;
            }
        }
        return "";
    }

    /**
     * on cherche si le dateFormatKey est defini, sinon, on prend le pattern du dateFormat, sinon on prend
     * Util.stringFormatDt pareil pour les heures...
     */
    public void updateSimpleDateFormats()
    {
        final MessageResources resources =
            (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );
        final Locale localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );

        HashMap hashMap =
            (HashMap) ( (HttpServletRequest) pageContext.getRequest() ).getSession().getAttribute( "dateformats" );
        if ( hashMap == null )
        {
            hashMap = new HashMap();
            ( (HttpServletRequest) pageContext.getRequest() ).getSession().setAttribute( "dateformats", hashMap );
        }

        updateSimpleDateFormats( resources, localeRequest, hashMap );

    }

    /**
     * Retourne DATE ou HEURE pour la fonction javascript si TYPE_WDATE alors DATE si TYPE_WHOUR alors HEURE
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return DATE ou HEURE
     */
    protected abstract String getJavascriptDateOrHour( int typeOfElement );

    /**
     * Retourne le simple date/hour format key en fonction du type si TYPE_WDATE alors dateFormat si TYPE_WHOUR alors
     * hourFormat
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le date/hour format
     */
    protected abstract String getFormatDateOrHourFormat( int typeOfElement );

    /**
     * Retourne le simple date/hour format key en fonction du type si TYPE_WDATE alors dateFormatKey si TYPE_WHOUR alors
     * hourFormatKey
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le date/hour formatKey
     */
    protected abstract String getFormatKeyDateOrHourFormat( int typeOfElement );

    /**
     * Retourne le simple date format en fonction du type si TYPE_WDATE alors simpleDateFormat si TYPE_WHOUR alors
     * simpleHourFormat
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le simple date format
     */
    protected abstract SimpleDateFormat getSimpleDateOrHourFormat( int typeOfElement );

    /**
     * Retourne le nom du parametre dans la requete si TYPE_WDATE alors super.property + "WDate"; si TYPE_WHOUR alors
     * uper.property + "WHour";
     * 
     * @param typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le nom du parametre dans la requete
     */
    protected abstract String getParameterNameInRequest( int typeOfElement );

    /**
     * on cherche si le dateFormatKey est defini, sinon, on prend le pattern du dateFormat, sinon on prend
     * Util.stringFormatDt pareil pour les heures...
     * 
     * @param resources resources
     * @param localeRequest requette
     * @param hashMap HashMap des clef stocké en parametres
     */
    protected abstract void updateSimpleDateFormats( final MessageResources resources, final Locale localeRequest,
                                                     HashMap hashMap );

    /**
     * Recupere le format par defaut Util.stringFormatDt ou Util.stringFormatHr
     * 
     * @param typeOfElement typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @return le formet de la date par defaut
     */
    protected abstract String getDefaultFormatDateOrHour( int typeOfElement );

    /**
     * Stocke le pattern de la date
     * 
     * @param typeOfElement typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @param patten pattern
     */
    protected abstract void setDateOrHourPattern( int typeOfElement, String patten );

    /**
     * Stocke le pattern de la date
     * 
     * @param typeOfElement typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @param patten pattern
     */
    protected abstract String getDateOrHourPattern( int typeOfElement );

    /**
     * Sauve le nouveau simpledate format a partir de celui fournit par le pattern
     * 
     * @param typeOfElement typeOfElement type TYPE_WDATE / TYPE_WHOUR
     * @param SimpleDateFormat SimpleDateFormat
     */
    protected abstract void saveSimpleDateFormatsForDateOrHour( int typeOfElement, SimpleDateFormat sdf );

    /**
     * Met a jour le les simple date format pour l'heure
     * 
     * @param resources Resources
     * @param localeRequest locale
     * @param hashMap hashmap des formats de clefs ...
     */
    protected void updateSimpleDateFormat( final int currentType, final MessageResources resources,
                                           final Locale localeRequest, HashMap hashMap )
    {
        String hourOrDateFormatTmp;
        if ( !GenericValidator.isBlankOrNull( getFormatKeyDateOrHourFormat( currentType ) ) )
        {
            hourOrDateFormatTmp = resources.getMessage( localeRequest, getFormatKeyDateOrHourFormat( currentType ) );
        }
        else if ( !GenericValidator.isBlankOrNull( getFormatDateOrHourFormat( currentType ) ) )
        {
            hourOrDateFormatTmp = getFormatDateOrHourFormat( currentType );
        }
        else
        {
            hourOrDateFormatTmp = getDefaultFormatDateOrHour( currentType );
        }

        // Garde le mode de compatibilité
        // On ne renseign dans le dictionnaire pour conversion que
        // si le mode legacy est activé ou bien si on a spécié un dateformatkey
        if ( Util.isFalse( WelcomConfigurator.getMessage( WelcomConfigurator.DATE_USE_LEGACY ) )
            || !GenericValidator.isBlankOrNull( getFormatKeyDateOrHourFormat( currentType ) ) )
        {
            hashMap.put( getParameterNameInRequest( currentType ), hourOrDateFormatTmp );
        }
        setDateOrHourPattern( currentType, hourOrDateFormatTmp );
        saveSimpleDateFormatsForDateOrHour( currentType, new java.text.SimpleDateFormat( hourOrDateFormatTmp ) );
    }

    /**
     * @return calendarBeginYear
     */
    public int getCalendarBeginYear()
    {
        return calendarBeginYear;
    }

    /**
     * @return calendarEndYear
     */
    public int getCalendarEndYear()
    {
        return calendarEndYear;
    }

    /**
     * @param i le calendarBeginYear
     */
    public void setCalendarBeginYear( final int i )
    {
        calendarBeginYear = i;
    }

    /**
     * @param i le calendarEndYear
     */
    public void setCalendarEndYear( final int i )
    {
        calendarEndYear = i;
    }

    /**
     * @see org.apache.struts.taglib.html.BaseFieldTag#release()
     */
    public void release()
    {
        super.release();
        calendarBeginYear = -1;
        calendarEndYear = -1;
        checkenabled = false;
        oldOnBlur = "";

    }

}
