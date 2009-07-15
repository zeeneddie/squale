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
package org.squale.squalix.util.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalix.messages.Messages;

/**
 * Manager de lancement de processus externe Une processus permet le lancement d'une commande externe et son suivi en
 * terme de flux de sortie sur stdout et stderr
 */
public class ProcessManager
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( ProcessManager.class );

    /** Répertoire d'exécution */
    private File mDir;

    /** Environnement d'exécution */
    private String[] mEnvp;

    /** Commande à exécuter */
    private String[] mCommand;

    /** Handler de sortie */
    private ProcessOutputHandler mOutputHandler;

    /** le fichier de log */
    private File mLog;

    /**
     * Constructeur
     * 
     * @param pCommand commande à lancer
     * @param pEnvp environnement d'exécution
     * @param pDir répertoire d'exécution
     */
    public ProcessManager( String[] pCommand, String pEnvp[], File pDir )
    {
        this( pCommand, pEnvp, pDir, null );
    }

    /**
     * Constructeur
     * 
     * @param pCommand commande à lancer
     * @param pEnvp environnement d'exécution
     * @param pDir répertoire d'exécution
     * @param pLog le fichiee de log
     */
    public ProcessManager( String[] pCommand, String pEnvp[], File pDir, File pLog )
    {
        mCommand = pCommand;
        mEnvp = pEnvp;
        mDir = pDir;
        mLog = pLog;
    }

    /**
     * Constructeur
     * 
     * @param pCommand commande à lancer
     * @param pEnvp environnement d'exécution
     * @param pDir répertoire d'exécution
     */
    public ProcessManager( String pCommand, String pEnvp[], File pDir )
    {
        this( pCommand, pEnvp, pDir, null );
    }

    /**
     * Constructeur
     * 
     * @param pCommand commande à lancer
     * @param pEnvp environnement d'exécution
     * @param pDir répertoire d'exécution
     * @param pLog le fichier de log
     */
    public ProcessManager( String pCommand, String pEnvp[], File pDir, File pLog )
    {
        mCommand = splitCommand( pCommand );
        mEnvp = pEnvp;
        mDir = pDir;
        mLog = pLog;
    }

    /**
     * affectation du handler d'output
     * 
     * @param pOutputHandler handler de sortie
     */
    public void setOutputHandler( ProcessOutputHandler pOutputHandler )
    {
        mOutputHandler = pOutputHandler;
    }

    /**
     * Lancement du processus externe
     * 
     * @param pHandler interface de traitement des erreurs
     * @return code de retour d'exécution
     * @throws IOException si erreur
     * @throws InterruptedException si erreur
     */
    public int startProcess( ProcessErrorHandler pHandler )
        throws IOException, InterruptedException
    {
        int result;
        Runtime runtime = Runtime.getRuntime();
        // Informations de debug
        if ( LOGGER.isDebugEnabled() )
        {
            LOGGER.debug( Messages.getString( "process.dir" ) + mDir );
            for ( int i = 0; i < mCommand.length; i++ )
            {
                LOGGER.debug( Messages.getString( "process.argument" ) + i + " " + mCommand[i] );
            }
        }
        // Vérification de l'existence du répertoire de lancement
        // celà peut être à l'origine de situations de blocage
        if ( ( mDir != null ) && ( !mDir.exists() || !mDir.isDirectory() ) )
        {
            throw new IOException( "unexisting directory: " + mDir );
        }
        Process processCheck = runtime.exec( mCommand, mEnvp, mDir );

        /*
         * Pour éviter le lock du process, il faut envoyer le flux d'erreur et le flux de sortie sur des threads.
         */
        ProcessErrorStream err = new ProcessErrorStream( processCheck.getErrorStream(), pHandler );
        Thread errorThread = new Thread( err, "StderrStreamConsumer" );
        errorThread.start();

        ProcessOutputStream outp = createOutputStream( processCheck.getInputStream(), mOutputHandler );
        Thread outputThread = new Thread( outp, "StdoutStreamConsumer" );
        outputThread.start();

        /* Obtention du code de retour du process */
        result = processCheck.waitFor();
        // Attente de la terminaison des threads
        // pour etre certain de recuperer les données
        // Voir bugid 4422496 de Sun
        outputThread.join();
        errorThread.join();

        return result;
    }

    /**
     * @param pInput l'entrée
     * @param pHandler le handler de sortie
     * @return le process de sortie à utiliser
     * @throws IOException si erreur
     */
    public ProcessOutputStream createOutputStream( InputStream pInput, ProcessOutputHandler pHandler )
        throws IOException
    {
        ProcessOutputStream process = null;
        if ( null == mLog )
        {
            process = new ProcessOutputStream( pInput, mOutputHandler );
        }
        else
        {
            // Si le fichier contient déjà des traces, on ne les écrase pas (le true du FileWriter)
            process =
                new ProcessOutputStreamInFile( pInput, mOutputHandler,
                                               new BufferedWriter( new FileWriter( mLog, true ) ) );
        }
        return process;
    }

    /**
     * Découpage de la commande en plusieurs paramètres
     * 
     * @param pCommand ligne de commande
     * @return la commande splittée
     */
    private String[] splitCommand( String pCommand )
    {
        int count = 0;
        String cmdarray[];
        StringTokenizer st;

        if ( pCommand.length() == 0 )
        {
            throw new IllegalArgumentException( "Empty command" );
        }

        st = new StringTokenizer( pCommand );
        count = st.countTokens();

        cmdarray = new String[count];
        st = new StringTokenizer( pCommand );
        count = 0;
        while ( st.hasMoreTokens() )
        {
            cmdarray[count++] = st.nextToken();
        }
        return cmdarray;
    }

    /**
     * @return l'outputHandler
     */
    public ProcessOutputHandler getOutputHandler()
    {
        return mOutputHandler;
    }

}
