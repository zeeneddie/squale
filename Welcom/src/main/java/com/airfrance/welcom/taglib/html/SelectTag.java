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
package com.airfrance.welcom.taglib.html;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.bean.WILogonBeanSecurity;
import com.airfrance.welcom.struts.lazyLoading.WLazyLoadingPersistance;
import com.airfrance.welcom.struts.lazyLoading.WLazyLoadingType;
import com.airfrance.welcom.struts.lazyLoading.WLazyUtil;
import com.airfrance.welcom.struts.util.WConstants;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;
import com.airfrance.welcom.taglib.table.ColsTag;
import com.airfrance.welcom.taglib.table.TableTag;

/**
 * Referenced classes of package org.apache.struts.taglib.html: BaseHandlerTag
 */
public class SelectTag
    extends BaseHandlerTag
{
    /**
     * 
     */
    private static final long serialVersionUID = 7462881264905964450L;

    /** message resources */
    protected static MessageResources messages =
        MessageResources.getMessageResources( "org.apache.struts.taglib.html.LocalStrings" );

    /** attribut du tag */
    protected String error = null;

    /** attribut interne */
    protected String match[];

    /** attribut du tag */
    protected String multiple;

    /** attribut du tag */
    protected String name;

    /** attribut du tag */
    protected String property;

    /** attribut interne contenant le html généré pour le tag */
    protected String saveBody;

    /** attribut du tag */
    protected String size;

    /** attribut du tag */
    protected String value;

    /** attribut du tag */
    protected boolean forceReadWrite = false;

    /** attribut du tag */
    protected String accessKey;

    /** attribut du tag */
    protected String overridePageAccess = "true";

    /** attribut du tag */
    protected boolean lazyLoading = true;

    /** définit si le chaps est obligatoire */
    protected boolean isRequired = false;

    /** le resultAccess */
    private String resultAccess = null;

    /**
     * constructeur
     */
    public SelectTag()
    {
        match = null;
        multiple = null;
        name = "org.apache.struts.taglib.html.BEAN";
        property = null;
        saveBody = null;
        size = null;
        value = null;
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
     * acesseur
     * 
     * @return le parametre
     */
    public String getMultiple()
    {
        return multiple;
    }

    /**
     * accesseur
     * 
     * @param pmultiple multiple
     */
    public void setMultiple( final String pmultiple )
    {
        this.multiple = pmultiple;
    }

    /**
     * acesseur
     * 
     * @return le parametre
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param pname name
     */
    public void setName( final String pname )
    {
        this.name = pname;
    }

    /**
     * acesseur
     * 
     * @return le parametre
     */
    public String getSize()
    {
        return size;
    }

    /**
     * @param psize name
     */
    public void setSize( final String psize )
    {
        this.size = psize;
    }

    /**
     * @param pvalue chaine à matcher
     * @return vrai si ça matche
     */
    public boolean isMatched( final String pvalue )
    {
        if ( ( match == null ) || ( pvalue == null ) )
        {
            return false;
        }

        for ( int i = 0; i < match.length; i++ )
        {
            if ( pvalue.equals( match[i] ) )
            {
                return true;
            }
        }

        return false;
    }

    /**
     * @return property
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @param pproperty property
     */
    public void setProperty( final String pproperty )
    {
        this.property = pproperty;
    }

    /**
     * @return value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param pvalue value
     */
    public void setValue( final String pvalue )
    {
        this.value = pvalue;
    }

    /**
     * Calcule les droits du tag
     * 
     * @throws JspException exception pouvant etre levee
     */
    public void computeAccess()
        throws JspException
    {
        // Recupere le droit sur la page
        resultAccess = (String) pageContext.getAttribute( "access" );

        // Si il y a un accesskey alors outre passe le droit
        // Si il y a un accesskey alors outre passe le droit ou on combine
        if ( !GenericValidator.isBlankOrNull( accessKey ) )
        {
            if ( ( resultAccess != null )
                && !( Util.isEquals( resultAccess, Access.READONLY ) || Util.isEquals( resultAccess, Access.READWRITE ) || Util.isEquals(
                                                                                                                                          resultAccess,
                                                                                                                                          Access.NONE ) ) )
            {
                throw new JspException(
                                        "L'attribut accessKey doit retourner une valeur READWRITE ou READONLY ou NONE (cf getSecuritePage()) : "
                                            + resultAccess );
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
                        resultAccess = accessTag;
                    }
                    else
                    // si mis a false explicitement
                    if ( Util.isEquals( resultAccess, Access.READONLY ) && Util.isEquals( accessTag, Access.READWRITE ) )
                    {
                        resultAccess = Access.READONLY;
                    }
                    else
                    {
                        resultAccess = accessTag;
                    }
                }
            }
        }
    }

    /**
     * @return the resultAccess
     */
    public String getResultAccess()
    {
        return resultAccess;
    }

    /**
     * @see org.apache.struts.taglib.html.SelectTag
     */
    public int doStartTag()
        throws JspException
    {
        computeAccess();
        // Recupere le droit sur la page
        final String pageAccess = getResultAccess();
        if ( ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && ( forceReadWrite == false ) ) )
        {
            super.pageContext.setAttribute( "com.airfrance.welcom.taglib.html.SELECT", this );
        }
        else
        {
            final Tag colsTag = findAncestorWithClass( this, ColsTag.class );
            if ( colsTag != null )
            {
                if ( ( (ColsTag) colsTag ).getId().equals( getName() ) )
                {
                    final TableTag tableTag = (TableTag) findAncestorWithClass( this, TableTag.class );
                    final StringBuffer exceptionMessage = new StringBuffer();
                    exceptionMessage.append( "Dans le cas d'utilisation d'un <af:select> dans un <af:col> la property doit être écrite en absolue. Elle doit ressembler à : name=\"" );
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

            final StringBuffer results = new StringBuffer();
            error = retrieveError();

            if ( ( error != null ) )
            {
                results.append( "<span class=\"redtextecourant\">" );
            }

            results.append( "<select" );
            results.append( " name=\"" );

            results.append( property );
            results.append( "\"" );

            if ( super.accesskey != null )
            {
                results.append( " accesskey=\"" );
                results.append( super.accesskey );
                results.append( "\"" );
            }

            if ( ( multiple != null ) && ( multiple.equalsIgnoreCase( "true" ) ) )
            {
                results.append( " multiple=\"multiple\"" );
            }

            if ( size != null )
            {
                results.append( " size=\"" );
                results.append( size );
                results.append( "\"" );
            }

            if ( super.tabindex != null )
            {
                results.append( " tabindex=\"" );
                results.append( super.tabindex );
                results.append( "\"" );
            }

            if ( WLazyUtil.isLazy( lazyLoading )
                && Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_GLOBAL_LAZYLOADING_COMBO ) ) )
            {
                results.append( " load=\"true\"" );
                results.append( " onmouseover=\"updateCombo(this);\"" );
                results.append( " onfocusin=\"updateCombo(this);\"" );
            }

            // pif(!GenericValidator.isBlankOrNull(getOnclick()))
            // psetOnclick("updateCombo(this);"+getOnclick());
            // pelse
            // psetOnclick("updateCombo(this);");
            results.append( prepareEventHandlers() );
            results.append( prepareStyles() );
            results.append( ">" );
            ResponseUtils.write( super.pageContext, results.toString() );
            super.pageContext.setAttribute( "com.airfrance.welcom.taglib.html.SELECT", this );
        }

        if ( value != null )
        {
            match = new String[1];
            match[0] = value;
        }
        else
        {
            final Object bean = super.pageContext.findAttribute( name );

            if ( bean == null )
            {
                final JspException e = new JspException( messages.getMessage( "getter.bean", name ) );
                RequestUtils.saveException( super.pageContext, e );
                throw e;
            }

            try
            {
                match = BeanUtils.getArrayProperty( bean, property );

                if ( match == null )
                {
                    match = new String[0];
                }
            }
            catch ( final IllegalAccessException e )
            {
                RequestUtils.saveException( super.pageContext, e );
                throw new JspException( messages.getMessage( "getter.access", property, name ) );
            }
            catch ( final InvocationTargetException e )
            {
                final Throwable t = e.getTargetException();
                RequestUtils.saveException( super.pageContext, t );
                throw new JspException( messages.getMessage( "getter.result", property, t.toString() ) );
            }
            catch ( final NoSuchMethodException e )
            {
                RequestUtils.saveException( super.pageContext, e );
                throw new JspException( messages.getMessage( "getter.method", property, name ) );
            }
        }

        return 2;
    }

    /**
     * @see org.apache.struts.taglib.html.SelectTag
     */
    public int doAfterBody()
        throws JspException
    {
        if ( super.bodyContent != null )
        {
            String pValue = super.bodyContent.getString();

            if ( pValue == null )
            {
                pValue = "";
            }

            saveBody = pValue.trim();
        }

        return 0;
    }

    /**
     * @see org.apache.struts.taglib.html.SelectTag
     */
    public int doEndTag()
        throws JspException
    {
        super.pageContext.removeAttribute( "com.airfrance.welcom.taglib.html.SELECT" );

        final StringBuffer results = new StringBuffer();
        final String pageAccess = (String) pageContext.getAttribute( "access" );

        if ( ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && ( forceReadWrite == false ) ) )
        {
            if ( saveBody == null )
            {
                results.append( "<span class=\"normalBold\" style=\"valign:middle\">-</span>" );
            }
            else
            {
                results.append( saveBody );
            }
        }
        else
        {
            if ( saveBody != null )
            {
                if ( WLazyUtil.isLazy( lazyLoading )
                    && Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_GLOBAL_LAZYLOADING_COMBO ) ) )
                {
                    saveBody = saveBody.replaceAll( "&nbsp;", " " );
                    WLazyLoadingPersistance.find( pageContext.getSession() ).add( WLazyLoadingType.COMBO, property,
                                                                                  "<select>" + saveBody + "</select>" );
                    // System.err.println(saveBody);
                    results.append( WLazyUtil.getLightCombo( saveBody ) );
                }
                else
                {
                    results.append( saveBody );
                }
            }

            results.append( "</select>" );
            // display the error if any
            if ( ( error != null ) )
            {
                results.append( error );
                results.append( "</span>" );
            }

            if ( isRequired && WelcomConfigurator.getCharte().isV2() )
            {
                if ( isRequired && !getDisabled() )
                {
                    results.append( "<img name=\"" + property + "required\" src=\"" );
                    results.append( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_IMG ) );
                    results.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_FIELD_AST ) );
                    results.append( "\">" );
                }
                else
                {
                    results.append( "<img name=\"" + property + "required\" src=\"" );
                    results.append( WelcomConfigurator.getMessage( WelcomConfigurator.OPTIFLUX_COMPRESSION_PREFIX_IMG ) );
                    results.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_FIELD_CLEARPIXEL ) );
                    results.append( "\">" );
                }
                // results.append("<input type=\"hidden\" name=\""+property+".load\" value=\"true\">");
            }
        }

        ResponseUtils.write( super.pageContext, results.toString() );

        return EVAL_PAGE;
    }

    /**
     * methode de libération du tag
     */
    public void release()
    {
        super.release();
        match = null;
        multiple = null;
        name = "org.apache.struts.taglib.html.BEAN";
        property = null;
        saveBody = null;
        size = null;
        value = null;
    }

    /**
     * Returns the forceReadWrite.
     * 
     * @return boolean
     */
    public boolean isForceReadWrite()
    {
        return forceReadWrite;
    }

    /**
     * @param pforceReadWrite pforceReadWrite
     */
    public void setForceReadWrite( final boolean pforceReadWrite )
    {
        this.forceReadWrite = pforceReadWrite;
    }

    /**
     * @return accessKey
     */
    public String getAccessKey()
    {
        return accessKey;
    }

    /**
     * @param string string
     */
    public void setAccessKey( final String string )
    {
        accessKey = string;
    }

    /**
     * @return lazyLoading
     */
    public boolean isLazyLoading()
    {
        return lazyLoading;
    }

    /**
     * @param b lazyLoading
     */
    public void setLazyLoading( final boolean b )
    {
        lazyLoading = b;
    }

    /**
     * @return overridePageAccess
     */
    public String getOverridePageAccess()
    {
        return overridePageAccess;
    }

    /**
     * @param string overridePageAccess
     */
    public void setOverridePageAccess( final String string )
    {
        overridePageAccess = string;
    }

    /**
     * @return isRequired
     */
    public boolean isRequired()
    {
        return isRequired;
    }

    /**
     * @param b isRequired
     */
    public void setIsRequired( final boolean b )
    {
        isRequired = b;
    }

}