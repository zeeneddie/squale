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
 * Créé le 31 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.access;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.struts.bean.WILogonBeanSecurity;
import com.airfrance.welcom.struts.util.WConstants;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * @author M325379 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ChangeAccessTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -5439124180702475906L;

    /** attribut */
    private String accessKey = "";

    /** attribut */
    private String name = "";

    /** attribut */
    private String property = "";

    /** le page access (pour le stocker) */
    private String pageAccess = null;

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        if ( GenericValidator.isBlankOrNull( accessKey ) && GenericValidator.isBlankOrNull( name )
            && GenericValidator.isBlankOrNull( property ) )
        {
            throw new JspException( "Aucun attribut spécifié pour le ChangeAccessTag" );
        }

        if ( !GenericValidator.isBlankOrNull( accessKey ) && !GenericValidator.isBlankOrNull( name )
            && !GenericValidator.isBlankOrNull( property ) )
        {
            throw new JspException( "Avec le tag ChangeAccessTag, utiliser soit accessKey, soit name et property" );
        }

        pageAccess = (String) pageContext.getAttribute( "access" );
        String access = null;
        if ( !GenericValidator.isBlankOrNull( accessKey ) )
        {
            final WILogonBeanSecurity lb =
                (WILogonBeanSecurity) pageContext.getSession().getAttribute( WConstants.USER_KEY );
            access = Access.getMultipleSecurityPage( lb, accessKey );
        }
        else
        { // on utilise name et property
            access = (String) LayoutUtils.getBeanFromPageContext( pageContext, name, property );
        }

        if ( access != null )
        {
            pageContext.setAttribute( "access", access );
        }

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        if ( pageAccess != null )
        {
            pageContext.setAttribute( "access", pageAccess );
        }
        else if ( pageContext.getAttribute( "access" ) != null )
        {
            pageContext.removeAttribute( "access" );
        }
        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    public void release()
    {
        accessKey = "";
        name = "";
        property = "";
        pageAccess = null;
    }

    /**
     * @return accessKey
     */
    public String getAccessKey()
    {
        return accessKey;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return property
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @param string accessKey
     */
    public void setAccessKey( final String string )
    {
        accessKey = string;
    }

    /**
     * @param string name
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * @param string property
     */
    public void setProperty( final String string )
    {
        property = string;
    }

}
