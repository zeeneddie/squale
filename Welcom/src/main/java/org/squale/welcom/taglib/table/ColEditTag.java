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
 * Créé le 4 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.table;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.squale.welcom.outils.Access;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColEditTag
    extends BodyTagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = 5820983669399545887L;

    /** parametre du tag */
    private String property = "";

    /** parametre du tag */
    private String key = "";

    /** parametre du tag */
    private String width = "24px";

    /** parametre du tag */
    private ColsTag colsTag;

    /** parametre du tag */
    private String toolTipKeyEdit = "";

    /** parametre du tag */
    private String toolTipKeyCancel = "";

    /** parametre du tag */
    private boolean forceReadWrite = false;

    /** parametre du tag */
    private String disabledProperty = "";

    /**
     * @see javax.servlet.jsp.tagext.BodyTagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        final String pageAccess = (String) pageContext.getAttribute( "access" );

        if ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && !forceReadWrite )
        {
            return SKIP_BODY;
        }

        // Recherche si un parent est du bon type
        Tag curParent = null;

        for ( curParent = getParent(); ( curParent != null ) && !( curParent instanceof ColsTag ); )
        {
            curParent = curParent.getParent();
        }

        if ( curParent == null )
        {
            throw new JspException( "ColTag  must be used between Cols Tag." );
        }

        colsTag = (ColsTag) curParent;

        if ( !GenericValidator.isBlankOrNull( colsTag.getEmptyKey() )
            && ( pageContext.getAttribute( colsTag.getId() ) == null ) )
        {
            return SKIP_BODY;
        }
        else
        {
            return EVAL_PAGE;
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        final String pageAccess = (String) pageContext.getAttribute( "access" );

        if ( ( pageAccess != null ) && pageAccess.equals( Access.READONLY ) && !forceReadWrite )
        {
            return SKIP_BODY;
        }

        // Recupere la locale de la page
        final Locale localeRequest = (Locale) pageContext.getSession().getAttribute( Globals.LOCALE_KEY );

        // Recuperer le fichier des Bundle
        final MessageResources resources =
            (MessageResources) pageContext.getServletContext().getAttribute( Globals.MESSAGES_KEY );

        final ColEdit c = new ColEdit();

        if ( key == null )
        {
            if ( key != null )
            {
                key = property;
            }
            else
            {
                key = "edited";
            }
        }

        c.setKey( key );
        c.setProperty( property );
        if ( pageContext.getRequest().getParameter( "from" ) != null )
        {
            c.setFrom( pageContext.getRequest().getParameter( "from" ) );
        }
        c.setBaseURL( ( (HttpServletRequest) pageContext.getRequest() ).getContextPath() );
        c.setWidth( width );
        c.setPageContext( pageContext );
        c.setRequestURI( ( (HttpServletRequest) pageContext.getRequest() ).getRequestURI() );
        c.setDisabledProperty( disabledProperty );

        if ( !GenericValidator.isBlankOrNull( toolTipKeyEdit ) )
        {
            c.setTitleEdit( resources.getMessage( localeRequest, toolTipKeyEdit ) );
            if ( GenericValidator.isBlankOrNull( c.getTitleEdit() ) )
            {
                c.setTitleEdit( toolTipKeyEdit );
            }
        }

        if ( !GenericValidator.isBlankOrNull( toolTipKeyCancel ) )
        {
            c.setTitleCancel( resources.getMessage( localeRequest, toolTipKeyCancel ) );
            if ( GenericValidator.isBlankOrNull( c.getTitleCancel() ) )
            {
                c.setTitleCancel( toolTipKeyCancel );
            }
        }

        if ( getBodyContent() != null )
        {
            c.setCurrentValue( getBodyContent().getString().trim() );
        }

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
        width = "";
        toolTipKeyEdit = "";
        toolTipKeyCancel = "";
        forceReadWrite = false;
        disabledProperty = "";
        super.release();
    }

    /**
     * @return colsTag
     */
    public ColsTag getColsTag()
    {
        return colsTag;
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
     * @return width
     */
    public String getWidth()
    {
        return width;
    }

    /**
     * @param tag le nouveau colsTag
     */
    public void setColsTag( final ColsTag tag )
    {
        colsTag = tag;
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
     * @param string le nouveau width
     */
    public void setWidth( final String string )
    {
        width = string;
    }

    /**
     * @return forceReadWrite
     */
    public boolean isForceReadWrite()
    {
        return forceReadWrite;
    }

    /**
     * @param b le nouveau forceReadWrite
     */
    public void setForceReadWrite( final boolean b )
    {
        forceReadWrite = b;
    }

    /**
     * @return accesseur
     */
    public String getToolTipKeyCancel()
    {
        return toolTipKeyCancel;
    }

    /**
     * @return accesseur
     */
    public String getToolTipKeyEdit()
    {
        return toolTipKeyEdit;
    }

    /**
     * @param string accesseur
     */
    public void setToolTipKeyCancel( final String string )
    {
        toolTipKeyCancel = string;
    }

    /**
     * @param string accesseur
     */
    public void setToolTipKeyEdit( final String string )
    {
        toolTipKeyEdit = string;
    }

    /**
     * @return disabledProperty
     */
    public String getDisabledProperty()
    {
        return disabledProperty;
    }

    /**
     * @param string disabledProperty
     */
    public void setDisabledProperty( final String string )
    {
        disabledProperty = string;
    }

}