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

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.util.xml.FactoryAdapter;

;

/**
 * Fabrique de profile
 */
public class ProfileFactory
    extends FactoryAdapter
{

    /** Les noms des profiles */
    private Hashtable mProfiles;

    /**
     * Constructeur
     */
    public ProfileFactory()
    {
        mProfiles = new Hashtable();
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.commons.digester.ObjectCreationFactory#createObject(org.xml.sax.Attributes)
     */
    public Object createObject( Attributes attributes )
        throws Exception
    {
        String profileName = attributes.getValue( "name" );
        ProjectProfileBO profile = (ProjectProfileBO) mProfiles.get( profileName );
        if ( null == profile )
        {
            profile = new ProjectProfileBO();
            profile.setName( profileName );
            mProfiles.put( profileName, profile );
        }
        else
        {
            throw new Exception( XmlConfigMessages.getString( "profile.duplicate", new Object[] { profileName } ) );
        }
        return profile;
    }

    /**
     * @return la liste des différents profiles
     */
    public Hashtable getProfiles()
    {
        return mProfiles;
    }

}