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
package com.airfrance.welcom.taglib.logic;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.logic.ConditionalTagBase;
import org.apache.struts.util.RequestUtils;

import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * Class IsEmptyTag
 */
public class IsEmptyTag
    extends ConditionalTagBase
{
    /**
     * 
     */
    private static final long serialVersionUID = 4528251686653500057L;

    /**
     * Constructeur
     */
    public IsEmptyTag()
    {
    }

    /**
     * @see org.apache.struts.taglib.logic.ConditionalTagBase#condition()
     */
    protected boolean condition()
        throws JspException
    {
        return condition( true );
    }

    /**
     * @param desired boolean desire
     * @return la condition
     * @throws JspException exception pouvant etre levee
     */
    protected boolean condition( final boolean desired )
        throws JspException
    {
        boolean empty = true;

        if ( super.name != null )
        {
            final Object value = LayoutUtils.getBeanFromPageContext( super.pageContext, super.name, super.property );

            if ( value != null )
            {
                if ( value instanceof String )
                {
                    final String strValue = (String) value;
                    empty = strValue.length() < 1;
                }
                else
                {
                    empty = false;
                }
            }
        }
        else
        {
            final JspException e = new JspException( ConditionalTagBase.messages.getMessage( "logic.selector" ) );
            RequestUtils.saveException( super.pageContext, e );
            throw e;
        }

        return empty == desired;
    }
}