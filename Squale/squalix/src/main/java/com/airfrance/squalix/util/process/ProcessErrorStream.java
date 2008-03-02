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
