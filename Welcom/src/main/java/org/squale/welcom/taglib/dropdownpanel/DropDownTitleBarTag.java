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
/*
 * Créé le 27 avr. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.dropdownpanel;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;
import org.squale.welcom.outils.TrimStringBuffer;


/**
 * DropDownTitleBarTag
 */
public class DropDownTitleBarTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 7732292016166075789L;

    /** parametre du tag */
    private String key = "";

    /** parametre du tag */
    private String styleClass = "";

    /** parametre du tag */
    private String style = "";

    /** le dropdown panel tag */
    private DropDownPanelTag dropDown = null;

    /** le message ressourcee */
    private MessageResources resources = null;

    /** la locale */
    private Locale localeRequest = Locale.FRENCH;

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        resources = (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );
        localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );

        // Recherche si un parent est du bon type
        Tag curParent = null;

        for ( curParent = getParent(); ( curParent != null ) && !( curParent instanceof DropDownPanelTag ); )
        {
            curParent = curParent.getParent();
        }

        dropDown = (DropDownPanelTag) curParent;

        if ( dropDown == null )
        {
            throw new JspException( "DropDownTitleBarTag  must be used between DropDownPanelTag." );
        }

        final TrimStringBuffer sb = new TrimStringBuffer();
        sb.append( "<span" );

        if ( !GenericValidator.isBlankOrNull( styleClass ) )
        {
            sb.append( " class=\"" + styleClass + "\"" );
        }

        if ( !GenericValidator.isBlankOrNull( style ) )
        {
            sb.append( " style=\"" + style + "\"" );
        }

        sb.append( ">" );

        String title = resources.getMessage( localeRequest, key );

        if ( title == null )
        {
            title = key;
        }

        sb.append( title );

        sb.append( "</span>" );

        // incone de fermeture
        sb.append( "<div class=\"iconClose\"><a href=\"javascript:vis(document.getElementById('" + dropDown.getName()
            + "'));\"><img src=\"theme/charte_v02_002/img/ico/blueaf/icon_close.gif\" border=\"0\"></a></div>" );

        ResponseUtils.write( pageContext, sb.toString() );

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        release();
        return EVAL_PAGE;
    }

    /**
     * @return style
     */
    public String getStyle()
    {
        return style;
    }

    /**
     * @return styleClass
     */
    public String getStyleClass()
    {
        return styleClass;
    }

    /**
     * @param string le style
     */
    public void setStyle( final String string )
    {
        style = string;
    }

    /**
     * @param string le styleClass
     */
    public void setStyleClass( final String string )
    {
        styleClass = string;
    }

    /**
     * @return la key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @param string la key
     */
    public void setKey( final String string )
    {
        key = string;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        super.release();
        key = "";
        styleClass = "";
        style = "";
        dropDown = null;
        resources = null;
        localeRequest = Locale.FRENCH;
    }

}