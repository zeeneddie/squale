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
package org.squale.squalecommon.enterpriselayer.facade.rule.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import org.squale.squalecommon.enterpriselayer.businessobject.rule.CriteriumRuleBO;
import org.squale.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de références de critère
 */
class CriteriumRefFactory
    extends FactoryAdapter
{
    /** Critères */
    private Hashtable mCriteria;

    /**
     * Constructeur
     * 
     * @param pCriteria critères existants
     */
    public CriteriumRefFactory( Hashtable pCriteria )
    {
        mCriteria = pCriteria;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes attributes )
        throws Exception
    {
        String name = attributes.getValue( "name" );
        CriteriumRuleBO practice = (CriteriumRuleBO) mCriteria.get( name );
        if ( practice == null )
        {
            // Détection d'objet inexistant
            throw new Exception( XmlRuleMessages.getString( "criterium.unknown", new Object[] { name } ) );
        }
        return practice;
    }
}