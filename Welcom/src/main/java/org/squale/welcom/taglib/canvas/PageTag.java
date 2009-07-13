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
 * Créé le 9 mai 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.canvas;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.squale.welcom.outils.Access;
import org.squale.welcom.struts.bean.WILogonBeanSecurity;
import org.squale.welcom.struts.util.WConstants;


/**
 * PageTag
 */
public class PageTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -5573071429915297169L;

    /** parametre du tag */
    protected String titleKey = "";

    /** parametre du tag */
    protected String titleKeyArg0 = "";

    /** parametre du tag */
    protected String titleKeyArg1 = "";

    /** parametre du tag */
    protected String titleKeyArg2 = "";

    /** parametre du tag */
    protected String titleKeyArg3 = "";

    /** parametre du tag */
    protected String subTitleKey = "";

    /** parametre du tag */
    protected String subTitleKeyArg0 = "";

    /** parametre du tag */
    protected String subTitleKeyArg1 = "";

    /** parametre du tag */
    protected String subTitleKeyArg2 = "";

    /** parametre du tag */
    protected String subTitleKeyArg3 = "";

    /** parametre du tag */
    protected String accessKey = "";

    /** parametre du tag */
    protected String headPageInclude = "";

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        final String access = computeMode();
        // Si la page n'est pas accessible alors redirige vers la page d'erreur
        if ( access.equals( Access.NONE ) || access.equals( Access.NO ) )
        {
            try
            {
                final ActionErrors errors = new ActionErrors();
                errors.add( "error", new ActionError( "welcom.internal.page.non.access" ) );
                pageContext.getRequest().setAttribute( Globals.ERROR_KEY, errors );
                pageContext.getRequest().getRequestDispatcher( "/error.do" ).include( pageContext.getRequest(),
                                                                                      pageContext.getResponse() );
            }
            catch ( final ServletException e )
            {
                e.printStackTrace();
            }
            catch ( final IOException e )
            {
                e.printStackTrace();
            }
            ;
            return SKIP_PAGE;
        }

        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        return super.doEndTag();
    }

    /**
     * @return subTitleKey
     */
    public String getSubTitleKey()
    {
        return subTitleKey;
    }

    /**
     * @return subTitleKeyArg0
     */
    public String getSubTitleKeyArg0()
    {
        return subTitleKeyArg0;
    }

    /**
     * @return subTitleKeyArg1
     */
    public String getSubTitleKeyArg1()
    {
        return subTitleKeyArg1;
    }

    /**
     * @return subTitleKeyArg2
     */
    public String getSubTitleKeyArg2()
    {
        return subTitleKeyArg2;
    }

    /**
     * @return subTitleKeyArg3
     */
    public String getSubTitleKeyArg3()
    {
        return subTitleKeyArg3;
    }

    /**
     * @return titleKey
     */
    public String getTitleKey()
    {
        return titleKey;
    }

    /**
     * @return titleKeyArg0
     */
    public String getTitleKeyArg0()
    {
        return titleKeyArg0;
    }

    /**
     * @return titleKeyArg1
     */
    public String getTitleKeyArg1()
    {
        return titleKeyArg1;
    }

    /**
     * @return titleKeyArg2
     */
    public String getTitleKeyArg2()
    {
        return titleKeyArg2;
    }

    /**
     * @return titleKeyArg3
     */
    public String getTitleKeyArg3()
    {
        return titleKeyArg3;
    }

    /**
     * @param string le subTitleKey
     */
    public void setSubTitleKey( final String string )
    {
        subTitleKey = string;
    }

    /**
     * @param string le subTitleKeyArg0
     */
    public void setSubTitleKeyArg0( final String string )
    {
        subTitleKeyArg0 = string;
    }

    /**
     * @param string le subTitleKeyArg1
     */
    public void setSubTitleKeyArg1( final String string )
    {
        subTitleKeyArg1 = string;
    }

    /**
     * @param string le subTitleKeyArg2
     */
    public void setSubTitleKeyArg2( final String string )
    {
        subTitleKeyArg2 = string;
    }

    /**
     * @param string le subTitleKeyArg3
     */
    public void setSubTitleKeyArg3( final String string )
    {
        subTitleKeyArg3 = string;
    }

    /**
     * @param string le titleKey
     */
    public void setTitleKey( final String string )
    {
        titleKey = string;
    }

    /**
     * @param string le titleKeyArg0
     */
    public void setTitleKeyArg0( final String string )
    {
        titleKeyArg0 = string;
    }

    /**
     * @param string le titleKeyArg1
     */
    public void setTitleKeyArg1( final String string )
    {
        titleKeyArg1 = string;
    }

    /**
     * @param string le titleKeyArg2
     */
    public void setTitleKeyArg2( final String string )
    {
        titleKeyArg2 = string;
    }

    /**
     * @param string le titleKeyArg3
     */
    public void setTitleKeyArg3( final String string )
    {
        titleKeyArg3 = string;
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
     * Recupere les droits stockes dans le profil et les stocke dans la page
     * 
     * @return le pageAccess
     */
    public String computeMode()
    {
        // Recupere le droits stocké dans le profil
        if ( ( pageContext.getSession().getAttribute( WConstants.USER_KEY ) instanceof WILogonBeanSecurity )
            && !GenericValidator.isBlankOrNull( accessKey ) )
        {
            final WILogonBeanSecurity lb =
                (WILogonBeanSecurity) pageContext.getSession().getAttribute( WConstants.USER_KEY );
            final String pageAccess = Access.getMultipleSecurityPage( lb, accessKey );
            // Stocke dans la page le droit
            pageContext.setAttribute( "access", pageAccess );

            return pageAccess;
        }
        else
        {
            return "";
        }
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    public void release()
    {
        super.release();
        titleKey = "";
        titleKeyArg0 = "";
        titleKeyArg1 = "";
        titleKeyArg2 = "";
        titleKeyArg3 = "";
        subTitleKey = "";
        subTitleKeyArg0 = "";
        subTitleKeyArg1 = "";
        subTitleKeyArg2 = "";
        subTitleKeyArg3 = "";
        accessKey = "";
        headPageInclude = "";
    }

}