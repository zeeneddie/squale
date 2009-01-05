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

import com.airfrance.welcom.taglib.field.DateHeureTag;
import com.airfrance.welcom.taglib.field.DateTag;

public class WDateTag
    extends DateTag
    implements IWelcomInputTag
{

    /**
     * Spécifie le mode d'accée du composant
     */
    private String access = READWRITE;

    /**
     * {@inheritDoc}
     */
    protected void doRender( StringBuffer sb )
        throws JspException
    {

        if ( READWRITE.equals( access ) )
        {
            super.doRender( sb );
        }
        else
        {
            doRenderValue( sb, TYPE_WDATE );
            if ( READSEND.equals( access ) )
            {
                doRenderHidden( sb );
            }
        }
    }

    /**
     * Effectue un rendu sou forme caché ...
     * 
     * @param buffer buffer
     * @throws JspException erreur sur le recuperation de la valeur
     */
    protected void doRenderHidden( StringBuffer buffer )
        throws JspException
    {
        buffer.append( "<input type=\"hidden\" name=\"" );
        buffer.append( property + "WDate" );
        buffer.append( "\"" );
        this.doRenderValue( buffer, DateHeureTag.TYPE_WDATE );
        buffer.append( ">" );
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
