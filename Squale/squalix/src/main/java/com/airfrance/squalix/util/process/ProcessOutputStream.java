package com.airfrance.squalix.util.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Traitement du flux stdout des processus
 * Le flux stdout est traité dans un thread pour éviter les deadlocks
 */
class ProcessOutputStream implements Runnable {
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog(
    ProcessOutputStream.class);;

    /** Handler poour l'output */
    protected ProcessOutputHandler mOutputHandler;
    /**
     * Buffer de sortie du shell (<code>stdout</code>).
     */
    protected BufferedReader mBr;


    /**
     * Constructeur.
     * @param pStream flux de sortie (<code>stdout</code>).
     * @param pOutputHandler handler de sortie
     */
    public ProcessOutputStream(InputStream pStream, ProcessOutputHandler pOutputHandler) {
        mBr = new BufferedReader(new InputStreamReader(
            pStream));
        mOutputHandler = pOutputHandler;
    }

    
    /**
     * Méthode de lancement du thread.
     */
    public void run() {
        String inputMessage;
        try{
            /* on lit une à une les lignes du flux */
            while (null != (inputMessage = mBr.readLine())) {
                LOGGER.info(inputMessage);
                // Passage de la ligne au handler d'output si besoin
                if (mOutputHandler!=null) {
                    mOutputHandler.processOutput(inputMessage);
                }
            }
            // Fermeture du flux pour éviter un deadlock
            // avec le process externe
            mBr.close();
        } catch (IOException e){
            // Cas peu probable car on lit stdout d'un process
            // externe
            LOGGER.error(e,e);
        }
    }
}
