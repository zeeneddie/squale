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
package com.airfrance.squalecommon.enterpriselayer.facade.macker;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Factory pour les règles
 */
public class RuleFactory
    extends FactoryAdapter
{

    /** La catégorie de la règle par défaut */
    public static final String CATEGORY = "layerrespect";

    /** Le code de la règle par défaut */
    public static final String CODE = "Illegal reference";

    /** La sévérité de la règle par défaut */
    public static final String SEVERITY = "error";

    /**
     * Crée une règle en lui attribuant une catégorie, un code et une sévérité par défaut.
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes arg0 )
        throws Exception
    {
        RuleBO rule = new RuleBO();
        rule.setCategory( CATEGORY );
        rule.setCode( CODE );
        rule.setSeverity( SEVERITY );
        return rule;
    }

}
