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
package org.squale.welcom.taglib.security;

import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.squale.welcom.outils.Util;
import org.squale.welcom.struts.bean.WILogonBeanSecurity;
import org.squale.welcom.struts.util.WConstants;


/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class HasAccessTag
    extends TagSupport
{
    /**
     * 
     */
    private static final long serialVersionUID = -3386107136459567510L;

    /** Clef d'accé */
    protected String accessKey = "";

    /** Valeur a comparer */
    protected String value = "";

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag()
        throws JspException
    {
        return EVAL_PAGE;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag()
        throws JspException
    {
        // Recupere le doits stock dans le profil
        final WILogonBeanSecurity lb =
            (WILogonBeanSecurity) pageContext.getSession().getAttribute( WConstants.USER_KEY );

        // Le tag permet de gerer les acces en fonction de plusieurs droits du style
        // <af:hasAccess accessKey="DROIT1 || DROIT2|| DROIT3" value="YES">

        // On va donc verifier les differents droits jusqu'a en avoir un qui soit OK
        final StringTokenizer droitSplitter = new StringTokenizer( accessKey, "||" );

        while ( droitSplitter.hasMoreElements() )
        {
            // On recupere le droit suivant a tester
            final String item = (String) droitSplitter.nextElement();
            // On recupere la valeur qui lui est associee
            final String access = lb.getAccess( item );

            // On teste si cette valeur correspond a la value recherchee
            if ( Util.isEquals( value, access ) )
            {
                return EVAL_BODY_INCLUDE;
            }
        }

        // On n'a pas trouve de droit ayant la valeur recherchee
        return SKIP_BODY;
    }

    /**
     * Returns the access.
     * 
     * @return String
     */
    public String getAccessKey()
    {
        return accessKey;
    }

    /**
     * Sets the access.
     * 
     * @param pAccessKey The access to set
     */
    public void setAccessKey( final String pAccessKey )
    {
        this.accessKey = pAccessKey;
    }

    /**
     * @return value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param string value a comparer
     */
    public void setValue( final String string )
    {
        value = string;
    }
}