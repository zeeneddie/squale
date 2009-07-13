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
package org.squale.welcom.taglib.util;

import javax.servlet.jsp.tagext.TagSupport;

import org.squale.welcom.taglib.field.util.TagUtils;


/**
 * @author M327837 Ajoute le support da l'ajour de parametre
 */
public class WBodyTagSupport
    extends TagSupport
{

    /**
     * 
     */
    private static final long serialVersionUID = -8307143465885866995L;

    /**
     * Ajout l'attribut avec sa valeur au stringbuffer
     * 
     * @param sb stringbuffer
     * @param name nom
     * @param value valeur
     */
    protected void addParam( StringBuffer sb, String name, String value )
    {

        TagUtils.addParam( sb, name, value );

    }

}
