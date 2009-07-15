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

import org.apache.commons.logging.Log;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.squale.squalecommon.util.messages.CommonMessages;

/**
 * Traitement des erreurs de parsing XML Les erreurs XML sont loggées et insérées dans un buffer d'erreur
 */
public class ParsingHandler
    implements ErrorHandler
{
    /** Log */
    private Log mLog;

    /** Erreurs */
    private StringBuffer mErrors;

    /**
     * Constructeur
     * 
     * @param pLog log
     * @param pErrors erreurs rencontrées
     */
    public ParsingHandler( Log pLog, StringBuffer pErrors )
    {
        mLog = pLog;
        mErrors = pErrors;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ErrorHandler#warning(org.xml.sax.SAXParseException)
     */
    public void warning( SAXParseException exception )
        throws SAXException
    {
        mLog.warn( CommonMessages.getString( "xml.parsing.warning" ), exception );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ErrorHandler#error(org.xml.sax.SAXParseException)
     */
    public void error( SAXParseException exception )
        throws SAXException
    {
        String message = CommonMessages.getString( "xml.parsing.error", new Object[] { exception.getMessage() } );
        mErrors.append( message );
        mErrors.append( '\n' );
        mLog.error( message, exception );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.xml.sax.ErrorHandler#fatalError(org.xml.sax.SAXParseException)
     */
    public void fatalError( SAXParseException exception )
        throws SAXException
    {
        String message = CommonMessages.getString( "xml.parsing.fatal", new Object[] { exception.getMessage() } );
        mErrors.append( message );
        mErrors.append( '\n' );
        mLog.fatal( CommonMessages.getString( "xml.parsing.fatal" ), exception );
    }
}