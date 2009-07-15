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
package org.squale.squalecommon.enterpriselayer.facade.message;

import java.io.InputStream;
import java.util.Collection;
import java.util.TreeSet;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.message.MessageBO;
import org.squale.squalecommon.util.xml.XmlImport;

/**
 * Importation des messages
 */
public class MessageImport
    extends XmlImport
{
    /** Log */
    private static Log LOG = LogFactory.getLog( MessageImport.class );

    /** Nom publique de la DTD */
    final static String PUBLIC_DTD = "-//Squale//DTD Message Configuration 1.0//EN";

    /** Localisation de la DTD */
    final static String DTD_LOCATION = "/org/squale/squalecommon/dtd/message-1.0.dtd";

    /**
     * Constructeur
     */
    public MessageImport()
    {
        super( LOG );
    }

    /**
     * Importation de messages
     * 
     * @param pStream flux de messages
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return collection de messages importés sous la forme de MessageBO
     */
    public Collection importMessages( InputStream pStream, StringBuffer pErrors )
    {
        Digester configDigester = setupDigester( pErrors );
        TreeSet messages = new TreeSet();
        configDigester.push( messages );
        parse( configDigester, pStream, pErrors );
        return messages;
    }

    /**
     * Configuration du digester Le digester est utilisé pour le chargement du fichier XML de règles
     * 
     * @param pErrors erreurs de traitement
     * @return digester
     */
    private Digester setupDigester( StringBuffer pErrors )
    {
        Digester configDigester = preSetupDigester( PUBLIC_DTD, DTD_LOCATION, pErrors );
        // Traitement des messages
        configDigester.addObjectCreate( "messages/message", MessageBO.class );
        configDigester.addSetProperties( "messages/message" );
        configDigester.addCallMethod( "messages/message", "setText", 1 );
        configDigester.addCallParam( "messages/message", 0 );
        configDigester.addSetNext( "messages/message", "add" );
        return configDigester;
    }

}
