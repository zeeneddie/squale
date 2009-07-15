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
package com.airfrance.squalecommon.enterpriselayer.facade.config.xml;

import java.util.Hashtable;

import org.xml.sax.Attributes;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

/**
 * Fabrique de type de récupération des sources
 */
public class SourceManagementFactory
    extends FactoryAdapter
{

    /** Les noms des types de récupération des sources */
    private Hashtable mSourceManagements;

    /**
     * Constructeur
     */
    public SourceManagementFactory()
    {
        mSourceManagements = new Hashtable();
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
        SourceManagementBO sourceManagement = (SourceManagementBO) mSourceManagements.get( name );
        if ( null == sourceManagement )
        {
            sourceManagement = new SourceManagementBO();
            sourceManagement.setName( name );
            mSourceManagements.put( name, sourceManagement );
        }
        else
        {
            throw new Exception( XmlConfigMessages.getString( "sourcemanagement.duplicate", new Object[] { name } ) );
        }
        return sourceManagement;
    }

    /**
     * @return la liste des différents types de récupération des sources
     */
    public Hashtable getSourceManagements()
    {
        return mSourceManagements;
    }

}