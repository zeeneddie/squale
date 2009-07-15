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
package org.squale.squalecommon.enterpriselayer.facade.checkstyle.xml;

import org.xml.sax.Attributes;
import org.squale.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleModuleBO;
import org.squale.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de Module checstyle
 */
public class CheckstyleModuleFactory
    extends FactoryAdapter
{

    /**
     * Constructeur
     */
    public CheckstyleModuleFactory()
    {

    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes pAttributes )
    {
        CheckstyleModuleBO module = null;
        String name = pAttributes.getValue( "name" );

        if ( null != name )
        {
            module = new CheckstyleModuleBO();
            module.setName( name.trim() );

        }
        return module;
    }
}
