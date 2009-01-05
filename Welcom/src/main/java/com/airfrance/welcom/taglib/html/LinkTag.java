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
 * Créé le 3 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.html;

import javax.servlet.jsp.JspException;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.struts.util.WRequestUtils;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class LinkTag
    extends org.apache.struts.taglib.html.LinkTag
{

    /**
     * 
     */
    private static final long serialVersionUID = -5683341365632034727L;

    /** attribut */
    protected String titleKey = "";

    /** attribut */
    protected String titleKeyArg0 = "";

    /** attribut */
    protected String titleKeyArg1 = "";

    /** attribut */
    protected String titleKeyArg2 = "";

    /** attribut */
    protected String titleKeyArg3 = "";

    /**
     * @see org.apache.struts.taglib.html.LinkTag#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        setTitle( computeTitle() );

        return super.doStartTag();
    }

    /**
     * @return le title
     */
    public String computeTitle()
    {
        if ( !GenericValidator.isBlankOrNull( titleKey ) )
        {
            final String titlekeyArgs[] = { titleKeyArg0, titleKeyArg1, titleKeyArg2, titleKeyArg3 };
            String message;
            message = WRequestUtils.message( super.pageContext, titleKey, titlekeyArgs );

            if ( GenericValidator.isBlankOrNull( message ) )
            {
                message = titleKey;
            }

            return message;
        }
        else
        {
            return super.getTitle();
        }
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
     * @param string titleKey
     */
    public void setTitleKey( final String string )
    {
        titleKey = string;
    }

    /**
     * @param string titleKeyArg0
     */
    public void setTitleKeyArg0( final String string )
    {
        titleKeyArg0 = string;
    }

    /**
     * @param string titleKeyArg1
     */
    public void setTitleKeyArg1( final String string )
    {
        titleKeyArg1 = string;
    }

    /**
     * @param string titleKeyArg2
     */
    public void setTitleKeyArg2( final String string )
    {
        titleKeyArg2 = string;
    }

    /**
     * @param string titleKeyArg3
     */
    public void setTitleKeyArg3( final String string )
    {
        titleKeyArg3 = string;
    }
}