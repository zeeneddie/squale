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
package com.airfrance.welcom.taglib.field.wrap;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.ResponseUtils;

public class WPasswordTag
    extends WBaseTextTag
    implements IWelcomInputTag
{

    /**
     * Spécifie le mode d'accée du composant
     */
    private String access = READWRITE;

    /**
     * Contructeur
     */
    public WPasswordTag()
    {
        type = "password";
    }

    /**
     * {@inheritDoc}
     */
    public int doStartTag()
        throws JspException
    {
        setAutoComplete( "false" );
        if ( READWRITE.equals( access ) )
        {
            return super.doStartTag();
        }
        else
        {
            StringBuffer sb = new StringBuffer();
            doRenderMaskValue( sb );
            ResponseUtils.write( pageContext, sb.toString() );
            if ( READSEND.equals( access ) )
            {
                throw new JspException( "Impossible d'affiché un champ Password avec un accé READSEND" );
            }
            return EVAL_PAGE;
        }
    }

    /**
     * Masque la valeur
     * 
     * @param sb string buffer contenant la valeur masqué
     * @throws JspException erreur sur la recuperation de la valeur
     */
    private void doRenderMaskValue( StringBuffer sb )
        throws JspException
    {
        StringBuffer sbTemp = new StringBuffer();
        doRenderValue( sbTemp );
        sb.append( sbTemp.toString().replaceAll( ".", "*" ) );

    }

    /**
     * @return access attribut
     */
    public String getAccess()
    {
        return access;
    }

    /**
     * @param access access attribut
     */
    public void setAccess( String access )
    {
        this.access = access;
    }

}
