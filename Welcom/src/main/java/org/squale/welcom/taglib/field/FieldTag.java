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

import java.util.Date;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.RadioTag;
import org.apache.struts.util.ResponseUtils;
import org.squale.welcom.outils.Access;
import org.squale.welcom.outils.Charte;
import org.squale.welcom.outils.DateUtil;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.struts.bean.WILogonBeanSecurity;
import org.squale.welcom.struts.util.WConstants;
import org.squale.welcom.taglib.canvas.CanvasUtil;
import org.squale.welcom.taglib.field.util.LayoutUtils;
import org.squale.welcom.taglib.field.wrap.WAutoCompleteTag;
import org.squale.welcom.taglib.field.wrap.WCheckBoxTag;
import org.squale.welcom.taglib.field.wrap.WDateHeureTag;
import org.squale.welcom.taglib.field.wrap.WDateTag;
import org.squale.welcom.taglib.field.wrap.WEmailTag;
import org.squale.welcom.taglib.field.wrap.WNumberTag;
import org.squale.welcom.taglib.field.wrap.WPasswordTag;
import org.squale.welcom.taglib.field.wrap.WRadioTag;
import org.squale.welcom.taglib.field.wrap.WTextTag;
import org.squale.welcom.taglib.field.wrap.WTextareaTag;
import org.squale.welcom.taglib.table.ColTag;
import org.squale.welcom.taglib.table.ColsTag;
import org.squale.welcom.taglib.table.TableTag;


/**
 * Class for the usual input fields (text, textarea, password and checkbox). <br>
 * This tag automatically display the error associated to the input field if there is one. Javascript for simple type
 * checking is also generated.<br>
 * <br>
 * Type for text can be: text, number, date or email.<br>
 * <br>
 * Values can be read-write, read-only, or read & resend. The content of the tag is displayed after the input field so
 * that buttons or other elements can be added
 * 
 * @author: Fabienne Madaule
 * @version: 1.0
 */
public class FieldTag
    extends BaseFieldTag
{

    /**
     * 
     */
    private static final long serialVersionUID = -8587741495938990413L;

    /** logger */
    private static Log log = LogFactory.getLog( BaseFieldTag.class );

    /** accent */
    protected boolean accent = true;

    /** accept */
    protected String accept;

    /** access par defaut */
    protected String access = READWRITE;

    /** the taglib.html field properties */
    protected String cols = null;

    /** the error associated with this input field */
    protected String error = null;

    /** Le fiel instancié */
    protected org.apache.struts.taglib.html.BaseHandlerTag fieldTag = null;

    /** Longueur max */
    protected String maxlength = null;

    /** reaffichage */
    protected boolean redisplay = true;

    /** nombre de rows */
    protected String rows = null;

    /** valeur */
    protected String value = null;

    /** Tabluation index */
    protected String tabindex = null;

    // easyComplete
    /** Nom du bean contenant la collection ou nom de la collection si celle-ci est en session */
    protected String easyCompleteName = "";

    /** Nom de la collection contenue dans le bean déclaré dans easyCompleteName */
    protected String easyCompleteProperty = "";

    /** Nom de la propriété des beans contenus dans la collection représentant la valeur à renseigner. */
    protected String easyCompleteBeanValue = "";

    /** Nom de la propriété des beans contenus dans la collection représentant le label à afficher. */
    protected String easyCompleteBeanLabel = "";

    /** Nom de la méthode à appeller pour récupérer la liste des éléments à afficher. */
    protected String easyCompleteCallBackUrl = "";

    /** Nom de la classe css à appliquer à la partie de texte matchée. */
    protected String easyCompleteDecorationClass = "";

    /** Valeur si la case a coché est décoché */
    protected String unSelectValue = null;

    /** Gestion des majuscules (transforme automatiquement en majuscule) */
    protected boolean upperCase = false;

    /** Gestion de la premiere lettre en majuscule */
    protected boolean firstUpperCase = false;

    /** Active le filtrage de caractere spécuax */
    protected boolean filterSpecialChar = false;

    /** Formatage de la date */
    private java.lang.String dateFormat;

    /** Formatage de l'heure */
    private java.lang.String hourFormat;

    /** Clé de formatage de la date */
    private java.lang.String dateFormatKey;

    /** Clé de formatage de l'heure */
    private java.lang.String hourFormatKey;

    /** Date de debut du calendrié */
    protected int calendarBeginYear;

    /** Date de fin du calendier */
    protected int calendarEndYear;

    /** FOrce l'accés l'ecture/ecriture */
    protected boolean forceReadWrite = false;

    /** Desacitve */
    protected boolean disabled = false;

    /** Clef de droit d'accés */
    protected String accessKey = "";

    /** Surcharge le droit */
    protected String overridePageAccess = "true";

    /** Lance la verification du spell check */
    protected boolean spellChecked = false;

    /**
     * Constructeur
     */
    public FieldTag()
    {
        name = Constants.BEAN_KEY;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final StringBuffer buffer = new StringBuffer();

        endField( buffer );

        ResponseUtils.write( pageContext, buffer.toString() );

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        // Recupere le droit sur la page

        final String pageAccess = getPageAccess();

        if ( ( ( pageAccess != null ) && pageAccess.equals( READONLY ) && ( forceReadWrite == false ) )
            || ( ( access != null ) && access.equals( "READONLY" ) ) )
        {
            access = READONLY;

            if ( GenericValidator.isBlankOrNull( styleClass ) )
            {
                styleClass = "normalBold";
            }
        }

        // Verifcation que le pres-requis d'inclusion dans une colonne est correct
        checkColInclusion();

        // Check for WriteTD
        checkWriteTD();

        final StringBuffer buffer = new StringBuffer();

        // display the label
        beginField( buffer );

        // get the error if any and print the html tags
        error = retrieveError();

        if ( ( error != null ) && access.equals( READWRITE ) )
        {
            buffer.append( "<span class=\"redtextecourant\">" );
        }

        ResponseUtils.write( pageContext, buffer.toString() );
        buffer.setLength( 0 );

        if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.GENIAL_FIELD ) ) )
        {
            fieldTag = createSpecificIntelligentField();
            // initialize the tag
            LayoutUtils.copyProperties( fieldTag, this );
            fieldTag.doStartTag();
            fieldTag.doEndTag();
        }
        else
        {
            // create the corresponding tag
            // maybe having a pool of tags would be better
            if ( access.equals( READSEND ) )
            {
                type = "hidden";
            }
            Object objvalue = writeOldField( buffer );
            // display an image if a value is needed
            if ( objvalue == null )
            {
                objvalue = "";
            }
        }

        // display the error if any
        if ( ( error != null ) && access.equals( READWRITE ) )
        {
            buffer.append( error );
            buffer.append( "</span>" );
        }

        // ajoute l'étoile en V2
        buffer.append( addRequiredField() );

        ResponseUtils.write( pageContext, buffer.toString() );

        return EVAL_BODY_INCLUDE;
    }

    /**
     * @param buffer
     * @return
     * @throws JspException
     */
    private Object writeOldField( final StringBuffer buffer )
        throws JspException
    {
        // creation du field
        fieldTag = createSpecificField();

        // initialize the tag
        LayoutUtils.copyProperties( fieldTag, this );

        // Le style pour détouré les checkbox en charte v2.002 si non spécifé
        if ( ( type.equals( CHECKBOX ) || type.equals( RADIO ) ) && WelcomConfigurator.getCharte() == Charte.V2_002
            && GenericValidator.isBlankOrNull( fieldTag.getStyleClass() ) )
        {
            fieldTag.setStyleClass( "normal" );
        }

        // start the tag
        if ( !access.equals( READONLY ) )
        {

            // Ecrit le tag en lecture ecriture
            writeTagInReadWriteMode();

        }
        else
        {
            if ( type.equals( TEXTAREA ) )
            {
                fieldTag.setReadonly( true );
                fieldTag.doStartTag();
                fieldTag.doEndTag();
            }
            else if ( type.equals( CHECKBOX ) || type.equals( RADIO ) )
            {
                fieldTag.setDisabled( true );
                fieldTag.doStartTag();
                fieldTag.doEndTag();
            }
        }

        buffer.setLength( 0 );

        Object objvalue = LayoutUtils.getBeanFromPageContext( pageContext, name, property );

        // the value is read only so display it as text.
        if ( !access.equals( READWRITE ) && ( objvalue != null )
            && ( !type.equals( CHECKBOX ) && !type.equals( TEXTAREA ) && !type.equals( RADIO ) ) )
        {
            // buffer.append("<span align=\"absmiddle\" class=\"");
            // buffer.append(styleClass);
            // buffer.append("\">");
            // Si DATEHEURE ou DATE, afficher avec le bon format
            if ( GenericValidator.isBlankOrNull( objvalue.toString() ) )
            {
                buffer.append( WelcomConfigurator.getMessageWithCfgChartePrefix( ".default.char.if.empty" ) );
            }
            else
            {
                if ( type.equals( DATEHEURE ) )
                {
                    try
                    {
                        ( (DateHeureTag) fieldTag ).updateSimpleDateFormats();
                        Date date;
                        if ( objvalue instanceof Date )
                        {
                            date = (Date) objvalue;
                        }
                        else
                        {
                            date = DateUtil.parseAllDate( objvalue.toString() );
                        }
                        buffer.append( ( (DateHeureTag) fieldTag ).getSimpleDateFormat().format( date ) + " "
                            + ( (DateHeureTag) fieldTag ).getSimpleHourFormat().format( date ) );
                    }
                    catch ( final Exception ex )
                    {
                        log.error( ex, ex );
                    }
                }
                else if ( type.equals( DATE ) )
                {
                    try
                    {
                        Date date;
                        if ( objvalue instanceof Date )
                        {
                            date = (Date) objvalue;
                        }
                        else
                        {
                            date = DateUtil.parseAllDate( objvalue.toString() );
                        }
                        ( (DateTag) fieldTag ).updateSimpleDateFormats();
                        buffer.append( ( (DateTag) fieldTag ).getSimpleDateFormat().format( date ) );
                    }
                    catch ( final Exception ex )
                    {
                        log.error( ex, ex );
                    }
                }
                else if ( type.equals( CHECKBOX ) )
                {
                    buffer.append( LayoutUtils.getLabel( pageContext, "checkbox." + objvalue.toString(),
                                                         (String[]) null ) );
                }
                else if ( type.equals( PASSWORD ) )
                {
                    buffer.append( objvalue.toString().replaceAll( ".", "*" ) );
                }
                else
                {
                    buffer.append( ResponseUtils.filter( objvalue.toString() ) );
                }
            }

            // buffer.append("</span>");
            // sur un Field en READONLY, on ajoute automatiquement
            // le input hidden pour que l'attribut soit repasse dans le bean en sortie
            // Si DATEHEURE ou DATE, HIDDEN avec le bon format
            if ( type.equals( DATEHEURE ) )
            {
                buffer.append( "<input type=\"hidden\" name=\"" );
                buffer.append( property + "WDate" );
                buffer.append( "\"" );
                ( (DateHeureTag) fieldTag ).doRenderValue( buffer, DateHeureTag.TYPE_WDATE );
                buffer.append( ">" );
                buffer.append( "<input type=\"hidden\" name=\"" );
                buffer.append( property + "WHour" );
                buffer.append( "\"" );
                ( (DateHeureTag) fieldTag ).doRenderValue( buffer, DateHeureTag.TYPE_WHOUR );
                buffer.append( ">" );
            }
            else if ( type.equals( DATE ) )
            {
                buffer.append( "<input type=\"hidden\" name=\"" );
                buffer.append( property + "WDate" );
                buffer.append( "\"" );
                ( (DateTag) fieldTag ).doRenderValue( buffer, DateTag.TYPE_WDATE );
                buffer.append( ">" );
            }
            else
            {
                buffer.append( "<input type=\"hidden\" name=\"" );
                buffer.append( property );
                buffer.append( "\" value=\"" );
                buffer.append( ResponseUtils.filter( objvalue.toString() ) );
                buffer.append( "\">" );
            }
        }
        return objvalue;
    }

    /**
     * Ajout l'etoile si le champs est requis en charte v2
     * 
     * @return buffer
     */
    private String addRequiredField()
    {
        StringBuffer sb = new StringBuffer();
        if ( isRequired && WelcomConfigurator.getCharte().isV2() )
        {
            if ( !access.equals( "READONLY" ) )
            {
                sb.append( "<img name=\"" + property + "required\" src=\"" );
                sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_IMG ) );
                sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_FIELD_AST ) );
                sb.append( "\">" );
            }
            else
            {
                sb.append( "<img name=\"" + property + "required\" src=\"" );
                sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_IMG ) );
                sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_FIELD_CLEARPIXEL ) );
                sb.append( "\">" );
            }
        }
        return sb.toString();
    }

    /**
     * Ecrit le tag en lecture ecriture
     * 
     * @throws JspException pb
     */
    private void writeTagInReadWriteMode()
        throws JspException
    {
        // set the name of the javascript to use
        final boolean check = isRequired;

        // On vérifie le champ sur changement de valeur.
        // Mais on bloque la validation si le champ texte comporte des caractères spéciaux.
        if ( ( access.equals( READWRITE ) ) && ( !type.equals( CHECKBOX ) ) && ( !type.equals( RADIO ) ) )
        {
            if ( GenericValidator.isBlankOrNull( fieldTag.getOnchange() ) )
            {
                fieldTag.setOnchange( "checkValue(this, '" + property + "','" + type + "'," + check + ","
                    + isUpperCase() + "," + isFirstUpperCase() + "," + accent + ");" );
            }
            else
            {
                fieldTag.setOnchange( "checkValue(this, '" + property + "','" + type + "'," + check + ","
                    + isUpperCase() + "," + isFirstUpperCase() + "," + accent + ");" + fieldTag.getOnchange() );
            }
        }

        // AJoute a verification du spécifal char
        writeCheckSpecialChar( fieldTag );

        // Ajoute l'easy Complete si activé
        writeEasyComplete( fieldTag );

        if ( !GenericValidator.isBlankOrNull( easyCompleteName )
            || !GenericValidator.isBlankOrNull( easyCompleteCallBackUrl ) )
        {
            final String js = (String) pageContext.getRequest().getAttribute( "jsEasyComplete" );

            if ( js == null )
            {
                pageContext.getRequest().setAttribute( "jsEasyComplete", "true" );
                CanvasUtil.addJs( WelcomConfigurator.getMessage( WelcomConfigurator.HEADER_LOCALJS_PATH_KEY )
                    + "auto-complete.js", this, pageContext );
            }
        }

        fieldTag.doStartTag();
        fieldTag.doEndTag();
    }

    /**
     * Verifie que l'inclusion du tag dans un colonne d'une table est correct
     * 
     * @throws JspException exception exliquant la parrametr du tag le cas echeant
     */
    private void checkColInclusion()
        throws JspException
    {
        if ( access.equalsIgnoreCase( "READWRITE" ) || forceReadWrite )
        {
            final Tag colsTag = findAncestorWithClass( this, ColsTag.class );
            if ( colsTag != null )
            {
                if ( ( (ColsTag) colsTag ).getId().equals( getName() ) )
                {
                    final TableTag tableTag = (TableTag) findAncestorWithClass( this, TableTag.class );
                    final StringBuffer exceptionMessage = new StringBuffer();
                    exceptionMessage.append( "Dans le cas d'utilisation d'un <af:field> dans un <af:col> la property doit être écrite en absolue. Elle doit ressembler à : name=\"" );
                    exceptionMessage.append( tableTag.getName() );
                    exceptionMessage.append( "\" property='<%=\"" );
                    exceptionMessage.append( tableTag.getProperty() );
                    exceptionMessage.append( "[\"+" );
                    exceptionMessage.append( ( (ColsTag) colsTag ).getIdIndex() );
                    exceptionMessage.append( "+\"]." );
                    exceptionMessage.append( getProperty() );
                    exceptionMessage.append( "\"%>'" );
                    throw new JspException( exceptionMessage.toString() );
                }

            }
        }
    }

    /**
     * Met a false le write TD si necessaire
     */
    private void checkWriteTD()
    {

        final ColTag t = (ColTag) findAncestorWithClass( this, ColTag.class );
        if ( ( t != null ) && t.isWriteTD() && this.isWriteTD() )
        {
            this.setWriteTD( false );
        }
    }

    /**
     * Ajout de la verification des caracteres spéciaux
     * 
     * @param myFieldTag : field
     */
    private void writeCheckSpecialChar( final BaseHandlerTag myFieldTag )
    {
        if ( ( ( type.equals( PASSWORD ) ) || ( type.equals( TEXT ) ) || ( type.equals( TEXTAREA ) ) )
            && isFilterSpecialChar() )
        {
            String onb = "checkCaractereSpecial(this, '" + property + "');";

            if ( !GenericValidator.isBlankOrNull( getOnblur() ) )
            {
                onb = getOnblur() + onb;
            }

            myFieldTag.setOnblur( onb );
        }
    }

    /**
     * Ecrit le info necessaire pour l'easyComplete
     * 
     * @param myFieldTag : Field sur lequel on ajoute les proprietess
     */
    private void writeEasyComplete( final BaseHandlerTag myFieldTag )
    {
        if ( ( !GenericValidator.isBlankOrNull( easyCompleteName ) || !GenericValidator.isBlankOrNull( easyCompleteCallBackUrl ) )
            && !access.equals( READONLY ) )
        {
            final StringBuffer buf = new StringBuffer();
            buf.append( "doCompletion(event,this" );

            if ( !GenericValidator.isBlankOrNull( easyCompleteName ) )
            {
                buf.append( ",'" + easyCompleteName + "'" );
            }
            else
            {
                buf.append( ",null" );
            }

            if ( !GenericValidator.isBlankOrNull( easyCompleteProperty ) )
            {
                buf.append( ",'" + easyCompleteProperty + "'" );
            }
            else
            {
                buf.append( ",null" );
            }

            if ( !GenericValidator.isBlankOrNull( easyCompleteBeanValue ) )
            {
                buf.append( ",'" + easyCompleteBeanValue + "'" );
            }
            else
            {
                buf.append( ",null" );
            }

            if ( !GenericValidator.isBlankOrNull( easyCompleteBeanLabel ) )
            {
                buf.append( ",'" + easyCompleteBeanLabel + "'" );
            }
            else
            {
                buf.append( ",null" );
            }

            if ( !GenericValidator.isBlankOrNull( easyCompleteDecorationClass ) )
            {
                buf.append( ",'" + easyCompleteDecorationClass + "'" );
            }
            else
            {
                buf.append( ",'"
                    + WelcomConfigurator.getMessage( WelcomConfigurator.EASY_COMPLETE_DEFAULT_DECORATION_CLASS ) + "'" );
            }

            if ( !GenericValidator.isBlankOrNull( easyCompleteCallBackUrl ) )
            {
                buf.append( ",'" + easyCompleteCallBackUrl + "'" );
            }

            buf.append( ");" );

            if ( !GenericValidator.isBlankOrNull( myFieldTag.getOnkeyup() ) )
            {
                buf.append( myFieldTag.getOnkeyup() );
            }

            myFieldTag.setOnkeyup( buf.toString() );
        }
    }

    /**
     * Cree le tag
     * 
     * @return Retourne le tag reél
     * @throws JspException exception pouvant etre levee
     */
    private BaseHandlerTag createSpecificIntelligentField()
        throws JspException
    {
        BaseHandlerTag myFieldTag = null;
        if ( type.equals( RADIO ) )
        {
            if ( GenericValidator.isBlankOrNull( value ) )
            {
                throw new JspException( "Pour utiliser le type RADIO, l'attribut 'value' doit être spécifié" );
            }
            myFieldTag = new WRadioTag();
        }
        else if ( type.equals( CHECKBOX ) )
        {
            myFieldTag = new WCheckBoxTag();
        }
        else if ( type.equals( PASSWORD ) )
        {
            myFieldTag = new WPasswordTag();
        }
        else if ( ( type.equals( TEXT ) )
            && ( !GenericValidator.isBlankOrNull( easyCompleteName ) || !GenericValidator.isBlankOrNull( easyCompleteCallBackUrl ) ) )
        {
            myFieldTag = new WAutoCompleteTag();
        }
        else if ( type.equals( TEXT ) )
        {
            myFieldTag = new WTextTag();
        }
        else if ( type.equals( TEXTAREA ) )
        {
            myFieldTag = new WTextareaTag();
        }
        else if ( type.equals( DATE ) )
        {
            myFieldTag = new WDateTag();
        }
        else if ( type.equals( DATEHEURE ) )
        {
            myFieldTag = new WDateHeureTag();
        }
        else if ( type.equals( NUMBER ) )
        {
            myFieldTag = new WNumberTag();
        }
        else if ( type.equals( EMAIL ) )
        {
            myFieldTag = new WEmailTag();
        }

        if ( type.equals( "hidden" ) )
        {
            myFieldTag = new org.apache.struts.taglib.html.HiddenTag();
        }

        return myFieldTag;
    }

    /**
     * Cree le tag
     * 
     * @return Retourne le tag reél
     * @throws JspException exception pouvant etre levee
     */
    private BaseHandlerTag createSpecificField()
        throws JspException
    {
        BaseHandlerTag myFieldTag = null;
        if ( type.equals( RADIO ) )
        {
            if ( GenericValidator.isBlankOrNull( value ) )
            {
                throw new JspException( "Pour utiliser le type RADIO, l'attribut 'value' doit être spécifié" );
            }
            myFieldTag = new RadioTag();
        }

        if ( type.equals( CHECKBOX ) )
        {
            myFieldTag = new CheckBoxTag();
        }

        if ( type.equals( PASSWORD ) )
        {
            myFieldTag = new org.squale.welcom.taglib.field.PasswordTag();
        }

        if ( ( type.equals( TEXT ) )
            && ( !GenericValidator.isBlankOrNull( easyCompleteName ) || !GenericValidator.isBlankOrNull( easyCompleteCallBackUrl ) )
            && !access.equals( READONLY ) )
        {
            myFieldTag = new AutoCompleteTag()
            {
                protected String prepareEventHandlers()
                {
                    final StringBuffer buf = new StringBuffer( super.prepareEventHandlers() );
                    if ( isSpellChecked() )
                    {
                        buf.append( " spell=\"true\"" );
                    }
                    return buf.toString();
                }
            };
            ;
        }
        else if ( type.equals( TEXT ) )
        {
            myFieldTag = new org.apache.struts.taglib.html.TextTag()
            {
                protected String prepareEventHandlers()
                {
                    final StringBuffer buf = new StringBuffer( super.prepareEventHandlers() );
                    if ( ( getAutoComplete() != null ) && getAutoComplete().equals( "false" ) )
                    {
                        buf.append( " autocomplete=\"off\"" );
                    }
                    if ( isSpellChecked() )
                    {
                        buf.append( " spell=\"true\"" );
                    }
                    return buf.toString();
                }

            };
        }

        if ( type.equals( TEXTAREA ) )
        {
            myFieldTag = new org.apache.struts.taglib.html.TextareaTag()
            {
                protected String prepareEventHandlers()
                {
                    final StringBuffer buf = new StringBuffer( super.prepareEventHandlers() );
                    if ( isSpellChecked() )
                    {
                        buf.append( " spell=\"true\"" );
                    }
                    return buf.toString();
                }

            };
        }

        if ( type.equals( DATE ) )
        {
            myFieldTag = new org.squale.welcom.taglib.field.DateTag();
        }

        if ( type.equals( DATEHEURE ) )
        {
            myFieldTag = new org.squale.welcom.taglib.field.DateHeureTag();
        }

        if ( type.equals( NUMBER ) || type.equals( EMAIL ) )
        {
            myFieldTag = new org.apache.struts.taglib.html.TextTag();
        }

        if ( type.equals( "hidden" ) )
        {
            myFieldTag = new org.apache.struts.taglib.html.HiddenTag();
        }

        return myFieldTag;
    }

    /**
     * Cacule le droit de la page
     * 
     * @return droit calculé de la page
     * @throws JspException Probleme sur la recuperation
     */
    private String getPageAccess()
        throws JspException
    {
        String pageAccess = (String) pageContext.getAttribute( "access" );
        // Si il y a un accesskey alors outre passe le droit ou on combine
        if ( !GenericValidator.isBlankOrNull( accessKey ) )
        {
            if ( ( pageAccess != null )
                && !( Util.isEquals( pageAccess, Access.READONLY ) || Util.isEquals( pageAccess, Access.READWRITE ) || Util.isEquals(
                                                                                                                                      pageAccess,
                                                                                                                                      Access.NONE ) ) )
            {
                throw new JspException(
                                        "L'attribut accessKey doit retourner une valeur READWRITE ou READONLY ou NONE (cf getSecuritePage()) : "
                                            + pageAccess );
            }

            final Object o = pageContext.getSession().getAttribute( WConstants.USER_KEY );

            if ( o != null )
            {
                if ( o instanceof WILogonBeanSecurity )
                {
                    final WILogonBeanSecurity lb = (WILogonBeanSecurity) o;

                    final String accessTag = Access.getMultipleSecurityPage( lb, accessKey );

                    if ( ( overridePageAccess == null ) || Util.isTrue( overridePageAccess ) )
                    {
                        pageAccess = accessTag;
                    }
                    else
                    {
                        // si mis a false explicitement
                        if ( Util.isEquals( pageAccess, Access.READONLY )
                            && Util.isEquals( accessTag, Access.READWRITE ) )
                        {
                            pageAccess = Access.READONLY;
                        }
                        else
                        {
                            pageAccess = accessTag;
                        }
                    }
                }
            }

            if ( Access.checkAccessDroit( pageAccess, Access.NONE ) )
            {
                pageAccess = Access.READONLY;
            }
        }
        return pageAccess;
    }

    /**
     * @return accept
     */
    public String getAccept()
    {
        return accept;
    }

    /**
     * @return cols
     */
    public String getCols()
    {
        return cols;
    }

    /**
     * @return maxlength
     */
    public String getMaxlength()
    {
        return maxlength;
    }

    /**
     * @return redisplay
     */
    public boolean getRedisplay()
    {
        return redisplay;
    }

    /**
     * @return rows
     */
    public String getRows()
    {
        return rows;
    }

    /**
     * @return cols
     */
    public String getSize()
    {
        return cols;
    }

    /**
     * @return type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @return value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @return accent
     */
    public boolean isAccent()
    {
        return accent;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#release()
     */
    public void release()
    {
        access = READWRITE;
        cols = null;
        isRequired = false;
        rows = null;
        type = TEXT;
        name = Constants.BEAN_KEY;

        if ( fieldTag != null )
        {
            fieldTag.release();
            fieldTag = null;
        }

        error = null;
        super.release();
    }

    /**
     * @throws JspException exception pouvant etre levee
     * @return the first error associated with the current property if there is one
     */
    protected String retrieveError()
        throws JspException
    {
        final ActionErrors errors =
            (ActionErrors) pageContext.getAttribute( Globals.ERROR_KEY, PageContext.REQUEST_SCOPE );
        error = null;

        if ( ( errors != null ) && !errors.isEmpty() )
        {
            final Iterator it = errors.get( property );

            if ( it.hasNext() )
            {
                final ActionError report = (ActionError) it.next();
                error = LayoutUtils.getLabel( pageContext, report.getKey(), report.getValues() );
            }
        }

        return error;
    }

    /**
     * @param newAccent boolean l'accent
     */
    public void setAccent( final boolean newAccent )
    {
        accent = newAccent;
    } // Gestion des accents (transforme automatiquement en caractere de base)

    /**
     * @param pAccept le accept
     */
    public void setAccept( final String pAccept )
    {
        accept = pAccept;
    }

    /**
     * Default Value: READWRITE Allowed values: READWRITE, READONLY, READSEND Setting access to READONLY will render the
     * property value as text instead of a input field Setting access to READSEND will render the property value as text
     * and as an hidden input field so that the server can get the value back
     * 
     * @param newAccess l'access
     */
    public void setAccess( final String newAccess )
    {
        access = READWRITE;

        if ( newAccess.equalsIgnoreCase( READONLY ) )
        {
            access = READONLY;
        }

        if ( newAccess.equalsIgnoreCase( READSEND ) )
        {
            access = READSEND;
        }
    }

    /**
     * @param newCols le cols
     */
    public void setCols( final String newCols )
    {
        cols = newCols;
    }

    /**
     * If set to "TRUE" a red star is added after the field when the value is null A piece of Javascript adds or removes
     * the start when the value changes
     * 
     * @param newIsRequired le isRequired
     */
    public void setIsRequired( final String newIsRequired )
    {
        isRequired = newIsRequired.equalsIgnoreCase( "true" );
    }

    /**
     * @param pMaxlength le maxlength
     */
    public void setMaxlength( final String pMaxlength )
    {
        maxlength = pMaxlength;
    }

    /**
     * @param pRedisplay le redisplay
     */
    public void setRedisplay( final boolean pRedisplay )
    {
        redisplay = pRedisplay;
    }

    /**
     * @param newRows le rows
     */
    public void setRows( final String newRows )
    {
        rows = newRows;
    }

    /**
     * @param newSize le cols
     */
    public void setSize( final String newSize )
    {
        cols = newSize;
    }

    /**
     * Type of the input field <br>
     * Default value: TEXT <br>
     * Allowed values: PASSWORD, CHECKBOX, NUMBER, DATE, EMAIL <br>
     * For NUMBER, DATE and EMAIL some Javascipt test is done to check if the value type in is correct or not. If not a
     * red start is displayed after the field.
     * 
     * @param newType le type
     */
    public void setType( final String newType )
    {

        if ( newType.equalsIgnoreCase( RADIO ) )
        {
            type = RADIO;
        }

        if ( newType.equalsIgnoreCase( TEXT ) )
        {
            type = TEXT;
        }

        if ( newType.equalsIgnoreCase( TEXTAREA ) )
        {
            type = TEXTAREA;
        }

        if ( newType.equalsIgnoreCase( PASSWORD ) )
        {
            type = PASSWORD;
        }

        if ( newType.equalsIgnoreCase( CHECKBOX ) )
        {
            type = CHECKBOX;
        }

        if ( newType.equalsIgnoreCase( NUMBER ) )
        {
            type = NUMBER;
        }

        if ( newType.equalsIgnoreCase( DATE ) )
        {
            type = DATE;
        }

        if ( newType.equalsIgnoreCase( "DATEHOUR" ) || newType.equalsIgnoreCase( "DATETIME" )
            || newType.equalsIgnoreCase( DATEHEURE ) )
        {
            type = DATEHEURE;
        }

        if ( newType.equalsIgnoreCase( EMAIL ) )
        {
            type = EMAIL;
        }
    }

    /**
     * @param pValue la value
     */
    public void setValue( final String pValue )
    {
        value = pValue;
    }

    /**
     * Returns the forceReadWrite.
     * 
     * @return forceReadWrite
     */
    public boolean isForceReadWrite()
    {
        return forceReadWrite;
    }

    /**
     * Sets the forceReadWrite.
     * 
     * @param pForceReadWrite The forceReadWrite to set
     */
    public void setForceReadWrite( final boolean pForceReadWrite )
    {
        forceReadWrite = pForceReadWrite;
    }

    /**
     * @return tabindex
     */
    public String getTabindex()
    {
        return tabindex;
    }

    /**
     * @param string le tabindex
     */
    public void setTabindex( final String string )
    {
        tabindex = string;
    }

    /**
     * @return disabled
     */
    public boolean isDisabled()
    {
        return disabled;
    }

    /**
     * @param b le disabled
     */
    public void setDisabled( final boolean b )
    {
        disabled = b;
    }

    /**
     * @return accessKey
     */
    public String getAccessKey()
    {
        return accessKey;
    }

    /**
     * @param string le accessKey
     */
    public void setAccessKey( final String string )
    {
        accessKey = string;
    }

    /**
     * @return filterSpecialChar
     */
    public boolean isFilterSpecialChar()
    {
        return filterSpecialChar;
    }

    /**
     * @param b le filterSpecialChar
     */
    public void setFilterSpecialChar( final boolean b )
    {
        filterSpecialChar = b;
    }

    /**
     * @return upperCase
     */
    public boolean isUpperCase()
    {
        return upperCase;
    }

    /**
     * @param b le upperCase
     */
    public void setUpperCase( final boolean b )
    {
        upperCase = b;
    }

    /**
     * @return firstUpperCase
     */
    public boolean isFirstUpperCase()
    {
        return firstUpperCase;
    }

    /**
     * @param b le firstUpperCase
     */
    public void setFirstUpperCase( final boolean b )
    {
        firstUpperCase = b;
    }

    /**
     * @return calendarEndYear
     */
    public int getCalendarEndYear()
    {
        return calendarEndYear;
    }

    /**
     * @param i calendarEndYear
     */
    public void setCalendarEndYear( final int i )
    {
        calendarEndYear = i;
    }

    /**
     * @return dateFormat
     */
    public java.lang.String getDateFormat()
    {
        return dateFormat;
    }

    /**
     * @return hourFormat
     */
    public java.lang.String getHourFormat()
    {
        return hourFormat;
    }

    /**
     * @param string le dateFormat
     */
    public void setDateFormat( final java.lang.String string )
    {
        dateFormat = string;
    }

    /**
     * @param string le hourFormat
     */
    public void setHourFormat( final java.lang.String string )
    {
        hourFormat = string;
    }

    /**
     * @return overridePageAccess
     */
    public String getOverridePageAccess()
    {
        return overridePageAccess;
    }

    /**
     * @param string le overridePageAccess
     */
    public void setOverridePageAccess( final String string )
    {
        overridePageAccess = string;
    }

    /**
     * @return calendarBeginYear
     */
    public int getCalendarBeginYear()
    {
        return calendarBeginYear;
    }

    /**
     * @param i calendarBeginYear
     */
    public void setCalendarBeginYear( final int i )
    {
        calendarBeginYear = i;
    }

    /**
     * @return easyCompleteBeanLabel
     */
    public String getEasyCompleteBeanLabel()
    {
        return easyCompleteBeanLabel;
    }

    /**
     * @return easyCompleteBeanValue
     */
    public String getEasyCompleteBeanValue()
    {
        return easyCompleteBeanValue;
    }

    /**
     * @return easyCompleteCallBackUrl
     */
    public String getEasyCompleteCallBackUrl()
    {
        return easyCompleteCallBackUrl;
    }

    /**
     * @return easyCompleteDecorationClass
     */
    public String getEasyCompleteDecorationClass()
    {
        return easyCompleteDecorationClass;
    }

    /**
     * @return easyCompleteName
     */
    public String getEasyCompleteName()
    {
        return easyCompleteName;
    }

    /**
     * @return easyCompleteProperty
     */
    public String getEasyCompleteProperty()
    {
        return easyCompleteProperty;
    }

    /**
     * @param string easyCompleteBeanLabel
     */
    public void setEasyCompleteBeanLabel( final String string )
    {
        easyCompleteBeanLabel = string;
    }

    /**
     * @param string easyCompleteBeanValue
     */
    public void setEasyCompleteBeanValue( final String string )
    {
        easyCompleteBeanValue = string;
    }

    /**
     * @param string easyCompleteCallBackUrl
     */
    public void setEasyCompleteCallBackUrl( final String string )
    {
        easyCompleteCallBackUrl = string;
    }

    /**
     * @param string easyCompleteDecorationClass
     */
    public void setEasyCompleteDecorationClass( final String string )
    {
        easyCompleteDecorationClass = string;
    }

    /**
     * @param string easyCompleteName
     */
    public void setEasyCompleteName( final String string )
    {
        easyCompleteName = string;
    }

    /**
     * @param string easyCompleteProperty
     */
    public void setEasyCompleteProperty( final String string )
    {
        easyCompleteProperty = string;
    }

    /**
     * @return dateFormatKey
     */
    public java.lang.String getDateFormatKey()
    {
        return dateFormatKey;
    }

    /**
     * @return hourFormatKey
     */
    public java.lang.String getHourFormatKey()
    {
        return hourFormatKey;
    }

    /**
     * @param string dateFormatKey
     */
    public void setDateFormatKey( final java.lang.String string )
    {
        dateFormatKey = string;
    }

    /**
     * @param string hourFormatKey
     */
    public void setHourFormatKey( final java.lang.String string )
    {
        hourFormatKey = string;
    }

    /**
     * @return spellChecked
     */
    public boolean isSpellChecked()
    {
        return spellChecked;
    }

    /**
     * @param b spellChecked
     */
    public void setSpellChecked( final boolean b )
    {
        spellChecked = b;
    }

    /**
     * @return unSelectValue
     */
    public String getUnSelectValue()
    {
        return unSelectValue;
    }

    /**
     * @param string unSelectValue
     */
    public void setUnSelectValue( final String string )
    {
        unSelectValue = string;
    }

    /**
     * Permet de renoyer l'accéess de l'objet
     * 
     * @return access attribut
     */
    public String getAccess()
    {
        return access;
    }

}