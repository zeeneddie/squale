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
package com.airfrance.welcom.taglib.button.impl;

import javax.servlet.jsp.PageContext;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.struts.util.WRequestUtils;

public class AbstractButtonRederer
{
    /**
     * retourne le label du bouton
     * 
     * @param type le type du bouton
     * @param name nom du bouton
     * @param pageContext contexte
     * @return le label
     */
    protected String getLabel( final PageContext pageContext, final String name, final String rawValue,
                               final String type )
    {

        if ( !GenericValidator.isBlankOrNull( rawValue ) )
        {
            return rawValue;
        }
        else
        {
            final String labelKey = "buttonTag." + type + "." + name;
            String label = WRequestUtils.message( pageContext, labelKey );

            if ( !GenericValidator.isBlankOrNull( label ) && Util.isNonEquals( label, labelKey ) )
            {
                return label;
            }
            else
            {
                label = WRequestUtils.message( pageContext, "buttonTag." + name );
                if ( !GenericValidator.isBlankOrNull( label ) )
                {
                    return label;
                }
                else
                {
                    return labelKey;
                }
            }
        }
    }
}
