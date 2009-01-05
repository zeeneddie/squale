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
package com.airfrance.squalix.util.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Traitement du flux stderr des processus Le flux stderr est traité dans un thread pour éviter les deadlocks
 */
class ProcessErrorStream
    implements Runnable
{

    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( ProcessErrorStream.class );;

    /**
     * Interface de traitement des erreurs
     */
    private ProcessErrorHandler mErrorHandler;

    /**
     * Buffer d'erreurs (<code>stderr</code>) du shell.
     */
    private BufferedReader mBr;

    /**
     * Constructeur.
     * 
     * @param pStream flux d'erreurs (<code>stderr</code>) du shell.
     * @param pErrorHandler interface de traitement des erreurs
     */
    public ProcessErrorStream( InputStream pStream, ProcessErrorHandler pErrorHandler )
    {
        mBr = new BufferedReader( new InputStreamReader( pStream ) );
        mErrorHandler = pErrorHandler;
    }

    /**
     * Lancement du thread.
     */
    public void run()
    {
        String inputLine = null;
        try
        {
            while ( null != ( inputLine = mBr.readLine() ) )
            {
                /* quel que soit le comportement, on crée des erreurs. */
                mErrorHandler.processError( inputLine );
            }
            mBr.close();
        }
        catch ( IOException e )
        {
            LOGGER.error( e, e );
        }
    }
}
