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
package org.squale.squalecommon.util.xml;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.squale.squalecommon.util.messages.CommonMessages;

/**
 * Résolution des DTD Les DTD sont le plus souvent embarquées dans les jars, cette classe mémorise l'association entre
 * un id public de DTD et le nom de la ressource
 */
public class XmlResolver
    implements EntityResolver
{
    /** Identificateur public */
    private String mPublicId;

    /** Localisation de la dtd */
    private String mLocation;

    /**
     * Constructeur
     * 
     * @param pPublicId identification publique
     * @param pLocation ressource correspondante
     */
    public XmlResolver( String pPublicId, String pLocation )
    {
        mPublicId = pPublicId;
        mLocation = pLocation;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity( String pPublicId, String pSystemId )
        throws SAXException, IOException
    {
        if ( pPublicId.equals( mPublicId ) )
        {
            InputStream stream = getClass().getResourceAsStream( mLocation );
            if ( stream == null )
            {
                // On indique que la DTD est introuvable
                throw new SAXException( CommonMessages.getString( "xml.dtd.missing", new Object[] { mLocation } ) );
            }
            InputSource result = new InputSource( stream );
            result.setPublicId( pPublicId );
            result.setSystemId( pSystemId );
            return result;
        }
        else
        {
            // On refuse toute autre DTD que celle enregistrée
            throw new SAXException( CommonMessages.getString( "xml.dtd.unexpected",
                                                              new Object[] { pPublicId, mPublicId } ) );
        }
    }

}
