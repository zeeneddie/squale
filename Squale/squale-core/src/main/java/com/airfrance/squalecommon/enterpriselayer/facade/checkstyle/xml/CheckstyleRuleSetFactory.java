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
package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleSetBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de régles internes
 */
public class CheckstyleRuleSetFactory
    extends FactoryAdapter
{

    /**
     * Checkstyle Rules Set
     */
    private CheckstyleRuleSetBO mCheckstyleRuleSet = null;

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes pAttributes )
        throws Exception
    {
        mCheckstyleRuleSet = new CheckstyleRuleSetBO();
        String name = pAttributes.getValue( "name" );
        String value = pAttributes.getValue( "value" );
        if ( name.trim().equals( XmlCheckstyleParsingMessages.getString( "checkstyle.version" ) ) )
        {
            mCheckstyleRuleSet.setName( value );
        }
        else
        {
            throw new Exception( XmlCheckstyleParsingMessages.getString( "checkstyle.pattern.forgeted",
                                                                         new Object[] { "\n<module>\n\t"
                                                                             + "<metadata>\n" } ) );
        }
        return mCheckstyleRuleSet;
    }

    /**
     * @return rulesSet checkstyle
     */
    public CheckstyleRuleSetBO getCheckstyleRuleSets()
    {
        return mCheckstyleRuleSet;
    }

}
