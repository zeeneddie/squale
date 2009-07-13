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

import javax.servlet.jsp.JspException;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * PasswordTag
 */
public class PasswordTag
    extends org.apache.struts.taglib.html.PasswordTag
{
    /**
     * 
     */
    private static final long serialVersionUID = 4640755869899615527L;

    /** propriete du tag */
    private java.lang.String autoComplete;

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        // Create an appropriate "input" element based on our parameters
        final StringBuffer results = new StringBuffer( "<input type=\"" );
        results.append( type );
        results.append( "\" name=\"" );

        // since 1.1
        // if( indexed )
        // prepareIndex( results, name );
        results.append( property );
        results.append( "\"" );
        results.append( prepareEventHandlers() );

        if ( accesskey != null )
        {
            results.append( " accesskey=\"" );
            results.append( accesskey );
            results.append( "\"" );
        }

        if ( accept != null )
        {
            results.append( " accept=\"" );
            results.append( accept );
            results.append( "\"" );
        }

        if ( maxlength != null )
        {
            results.append( " maxlength=\"" );
            results.append( maxlength );
            results.append( "\"" );
        }

        if ( cols != null )
        {
            results.append( " size=\"" );
            results.append( cols );
            results.append( "\"" );
        }

        if ( tabindex != null )
        {
            results.append( " tabindex=\"" );
            results.append( tabindex );
            results.append( "\"" );
        }

        if ( autoComplete != null )
        {
            results.append( " autocomplete=\"" );
            results.append( autoComplete );
            results.append( "\"" );
        }

        doRenderValue( results );
        results.append( prepareEventHandlers() );
        results.append( prepareStyles() );
        results.append( ">" );

        // Print this field to our output writer
        ResponseUtils.write( pageContext, results.toString() );

        // Continue processing this page
        return ( EVAL_BODY_BUFFERED );
    }

    /**
     * Ecrit dans le stringbuffer la partie relative a la value
     * 
     * @param results le stringbuffer
     * @throws JspException exception pouvant etre levee
     */
    private void doRenderValue( final StringBuffer results )
        throws JspException
    {
        results.append( " value=\"" );

        if ( value != null )
        {
            results.append( ResponseUtils.filter( value ) );
        }
        else if ( redisplay || !"password".equals( type ) )
        {
            Object value = RequestUtils.lookup( pageContext, name, property, null );

            if ( value == null )
            {
                value = "";
            }

            results.append( ResponseUtils.filter( value.toString() ) );
        }

        results.append( "\"" );
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (09/09/2002 15:39:33)
     * 
     * @return java.lang.String
     */
    public java.lang.String getAutoComplete()
    {
        return autoComplete;
    }

    /**
     * Insérez la description de la méthode ici. Date de création : (09/09/2002 15:39:33)
     * 
     * @param newAutoComplete java.lang.String
     */
    public void setAutoComplete( final java.lang.String newAutoComplete )
    {
        autoComplete = newAutoComplete;
    }
}