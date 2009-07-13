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
 * Créé le 30 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.table;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -3223443429650477776L;

    /** parametre du tag */
    private String property = "";

    /** parametre du tag */
    private String key = "";

    /** parametre du tag */
    private boolean sortable = false;

    /** parametre du tag */
    private boolean editable = true;

    /** parametre du tag */
    private String paramId = "";

    /** parametre du tag */
    private String paramName = "";

    /** parametre du tag */
    private String paramProperty = "";

    /** parametre du tag */
    private String href = "";

    /** parametre du tag */
    private String width = "";

    /** parametre du tag */
    private String type = null;

    /** parametre du tag */
    private String dateFormatKey = null;

    /** parametre du tag */
    private String dateFormat = null;

    /** parametre du tag */
    private boolean writeTD = true;

    /** parametre du tag */
    private ColsTag colsTag;

    /** parametre du tag */
    private String emptyKey = "";

    /** parametre du tag */
    private String contentClass = "";

    /** parametre du tag */
    private String contentStyle = "";

    /** parametre du tag */
    private String headerStyle = "";

    /** parametre du tag */
    private String headerClass = "";

    /** parametre du tag */
    private String headerTruncate = "";

    /** parametre du tag */
    private String contentTruncate = "";

    /** parametre du tag */
    private boolean contentNoWrap = false;

    /** parametre du tag */
    private boolean headerNoWrap = true;

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        // Recherche si un parent est du bon type
        Tag curParent = null;

        for ( curParent = getParent(); ( curParent != null ) && !( curParent instanceof ColsTag ); )
        {
            curParent = curParent.getParent();
        }

        if ( curParent == null )
        {
            throw new JspException( "ColTag  must be used between Table Tag." );
        }

        colsTag = (ColsTag) curParent;

        if ( !GenericValidator.isBlankOrNull( colsTag.getEmptyKey() )
            && ( pageContext.getAttribute( colsTag.getId() ) == null ) )
        {
            return SKIP_BODY;
        }
        else
        {
            return EVAL_BODY_BUFFERED;
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final Col c = new Col();

        if ( key == null )
        {
            key = property;
        }

        c.setKey( key );
        c.setProperty( property );
        c.setSortable( sortable );
        c.setEditable( editable );
        c.setParamId( paramId );
        c.setParamName( paramName );
        c.setParamProperty( paramProperty );
        c.setLink( href );
        c.setWidth( width );
        c.setPageContext( pageContext );
        c.setContentStyle( contentStyle );
        c.setContentClass( contentClass );
        c.setHeaderStyle( headerStyle );
        c.setHeaderClass( headerClass );
        c.setHeaderTruncate( headerTruncate );
        c.setContentTruncate( contentTruncate );
        c.setHeaderNoWrap( headerNoWrap );
        c.setContentNoWrap( contentNoWrap );

        if ( getBodyContent() != null )
        {
            c.setCurrentValue( getBodyContent().getString().trim() );
        }

        c.setWriteTD( writeTD );

        if ( type != null )
        {
            type = type.toUpperCase();
        }

        c.setType( type );
        c.setDateFormatKey( dateFormatKey );
        c.setDateFormat( dateFormat );
        c.setEmptyKey( emptyKey );
        colsTag.addCellule( c );

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#release()
     */
    public void release()
    {
        colsTag = null;
        property = "";
        key = "";
        sortable = false;
        editable = true;
        paramId = "";
        paramName = "";
        paramProperty = "";
        href = "";
        width = "";
        type = null;
        dateFormatKey = null;
        dateFormat = null;
        writeTD = true;
        emptyKey = "";
        contentStyle = "";
        contentClass = "";
        headerStyle = "";
        headerClass = "";
        headerTruncate = "";
        contentTruncate = "";
        headerNoWrap = true;
        contentNoWrap = false;

        super.release();
    }

    /**
     * @return key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * @return property
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @return sortable
     */
    public boolean isSortable()
    {
        return sortable;
    }

    /**
     * @param string le nouveau key
     */
    public void setKey( final String string )
    {
        key = string;
    }

    /**
     * @param string le nouveau property
     */
    public void setProperty( final String string )
    {
        property = string;
    }

    /**
     * @param b le nouveau sortable
     */
    public void setSortable( final boolean b )
    {
        sortable = b;
    }

    /**
     * @return paramId
     */
    public String getParamId()
    {
        return paramId;
    }

    /**
     * @return paramName
     */
    public String getParamName()
    {
        return paramName;
    }

    /**
     * @return paramProperty
     */
    public String getParamProperty()
    {
        return paramProperty;
    }

    /**
     * @param string le nouveau paramId
     */
    public void setParamId( final String string )
    {
        paramId = string;
    }

    /**
     * @param string le nouveau paramName
     */
    public void setParamName( final String string )
    {
        paramName = string;
    }

    /**
     * @param string le nouveau paramProperty
     */
    public void setParamProperty( final String string )
    {
        paramProperty = string;
    }

    /**
     * @return href
     */
    public String getHref()
    {
        return href;
    }

    /**
     * @return width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param string le nouveau href
     */
    public void setHref( final String string )
    {
        href = string;
    }

    /**
     * @param string le nouveau width
     */
    public void setWidth( final String string )
    {
        width = string;
    }

    /**
     * @return writeTD
     */
    public boolean isWriteTD()
    {
        return writeTD;
    }

    /**
     * @param b le nouveau writeTD
     */
    public void setWriteTD( final boolean b )
    {
        writeTD = b;
    }

    /**
     * @return dateFormat
     */
    public String getDateFormat()
    {
        return dateFormat;
    }

    /**
     * @return type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param string le nouveau dateFormat
     */
    public void setDateFormat( final String string )
    {
        dateFormat = string;
    }

    /**
     * @param string le nouveau type
     */
    public void setType( final String string )
    {
        type = string;
    }

    /**
     * @return emptyKey
     */
    public String getEmptyKey()
    {
        return emptyKey;
    }

    /**
     * @param string le nouveau emptyKEy
     */
    public void setEmptyKey( final String string )
    {
        emptyKey = string;
    }

    /**
     * @return editable
     */
    public boolean isEditable()
    {
        return editable;
    }

    /**
     * @param b editable
     */
    public void setEditable( final boolean b )
    {
        editable = b;
    }

    /**
     * @return headerStyle
     */
    public String getHeaderStyle()
    {
        return headerStyle;
    }

    /**
     * @param string headerStyle
     */
    public void setHeaderStyle( final String string )
    {
        headerStyle = string;
    }

    /**
     * @return contentTruncate
     */
    public String getContentTruncate()
    {
        return contentTruncate;
    }

    /**
     * @return headerTruncate
     */
    public String getHeaderTruncate()
    {
        return headerTruncate;
    }

    /**
     * @param string contentTruncate
     */
    public void setContentTruncate( final String string )
    {
        contentTruncate = string;
    }

    /**
     * @param string headerTruncate
     */
    public void setHeaderTruncate( final String string )
    {
        headerTruncate = string;
    }

    /**
     * @return contentClass
     */
    public String getContentClass()
    {
        return contentClass;
    }

    /**
     * @return contentStyle
     */
    public String getContentStyle()
    {
        return contentStyle;
    }

    /**
     * @return headerClass
     */
    public String getHeaderClass()
    {
        return headerClass;
    }

    /**
     * @param string contentClass
     */
    public void setContentClass( final String string )
    {
        contentClass = string;
    }

    /**
     * @param string contentStyle
     */
    public void setContentStyle( final String string )
    {
        contentStyle = string;
    }

    /**
     * @param string headerClass
     */
    public void setHeaderClass( final String string )
    {
        headerClass = string;
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
     * @return ContentNoWrap
     */
    public boolean isContentNoWrap()
    {
        return contentNoWrap;
    }

    /**
     * @return HeaderNoWrap
     */
    public boolean isHeaderNoWrap()
    {
        return headerNoWrap;
    }

    /**
     * @param b ContentNoWrap
     */
    public void setContentNoWrap( final boolean b )
    {
        contentNoWrap = b;
    }

    /**
     * @param b HeaderNoWrap
     */
    public void setHeaderNoWrap( final boolean b )
    {
        headerNoWrap = b;
    }

}