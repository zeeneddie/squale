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
package com.airfrance.squalecommon.util.xml;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.xml.sax.SAXException;

import com.airfrance.squalecommon.util.messages.CommonMessages;

/**
 * Importation de données XML Cette calsse factorise l'importation de données XML avec un digester, les données XML sont
 * supposées définies par une DTD accessible par le classpath que l'on fournit en paramètre à la classe
 */
public class XmlImport
{
    /** Log */
    private Log mLog;

    /**
     * Constructeur
     * 
     * @param pLog log
     */
    protected XmlImport( Log pLog )
    {
        mLog = pLog;
    }

    /**
     * Configuration du digester Le digester est utilisé pour le chargement du fichier XML
     * 
     * @param pPublicId identification publique ou null si pas de DTD associée
     * @param pLocation ressource correspondante
     * @param pErrors erreurs de traitement
     * @return digester
     */
    protected Digester preSetupDigester( String pPublicId, String pLocation, StringBuffer pErrors )
    {
        Digester configDigester = new Digester();
        configDigester.setNamespaceAware( true );
        configDigester.setUseContextClassLoader( true );
        // Placement du traitement d'erreur
        configDigester.setErrorHandler( new ParsingHandler( mLog, pErrors ) );
        // Résolution de DTD
        if ( pPublicId != null )
        {
            configDigester.setValidating( true );
            configDigester.setPublicId( pPublicId );
            configDigester.setEntityResolver( new XmlResolver( pPublicId, pLocation ) );
        }
        else
        {
            configDigester.setValidating( false );
        }
        return configDigester;
    }

    /**
     * Parsing du fichier XML Le parsing est exécuté, puis le flux est fermé
     * 
     * @param pConfigDigester digester
     * @param pStream flux de grille
     * @param pErrors erreurs
     */
    protected void parse( Digester pConfigDigester, InputStream pStream, StringBuffer pErrors )
    {
        try
        {
            pConfigDigester.parse( pStream );
        }
        catch ( IOException e )
        {
            // Traitement par défaut de l'exception
            handleException( e, pErrors );
        }
        catch ( SAXException e )
        {
            // Traitement par défaut de l'exception
            handleException( e, pErrors );
        }
        finally
        {
            try
            {
                // Fermeture du flux en entrée
                pStream.close();
            }
            catch ( IOException e1 )
            {
                // Traitement par défaut de l'exception
                handleException( e1, pErrors );
            }
        }
    }

    /**
     * Traitement d'une exception
     * 
     * @param pException exception
     * @param pErrors erreurs
     */
    protected void handleException( Exception pException, StringBuffer pErrors )
    {
        String message = CommonMessages.getString( "xml.parsing.error", new Object[] { pException.getMessage() } );
        pErrors.append( message );
        pErrors.append( '\n' );
        mLog.error( message, pException );
    }
}
