package com.airfrance.squalix.util.process;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 */
public class ProcessOutputStreamInFile extends ProcessOutputStream {
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog(ProcessOutputStreamInFile.class);
    
    /** le buffer du fichier */
    private BufferedWriter mWriter;

    /**
     * Constructeur.
     * @param pStream flux de sortie (<code>stdout</code>).
     * @param pOutputHandler handler de sortie
     */
   public ProcessOutputStreamInFile(InputStream pStream, ProcessOutputHandler pOutputHandler) {
        super(pStream, pOutputHandler);
        try {
            mWriter = new BufferedWriter(new FileWriter("."));
        } catch (IOException e) {
            LOGGER.error(e,e);
        }
    }

    /**
     * Constructeur.
     * @param pStream flux de sortie (<code>stdout</code>).
     * @param pOutputHandler handler de sortie
     * @param pWriter le buffer de sortie
     */
   public ProcessOutputStreamInFile(InputStream pStream, ProcessOutputHandler pOutputHandler, BufferedWriter pWriter) {
        super(pStream, pOutputHandler);
        mWriter = pWriter;
    }

    
    /**
     * Méthode de lancement du thread.
     */
    public void run() {
        String inputMessage;
        try{
            /* on lit une à une les lignes du flux */
            while (null != (inputMessage = mBr.readLine())) {
                // On va écrire la sortie dans le fichier
                mWriter.write(inputMessage);
                mWriter.newLine();
                mWriter.flush();
                // Passage de la ligne au handler d'output si besoin
                if (mOutputHandler!=null) {
                    mOutputHandler.processOutput(inputMessage);
                }
            }
            // Fermeture du flux pour éviter un deadlock
            // avec le process externe
            mBr.close();
            mWriter.close();
        } catch (IOException e){
            // Cas peu probable car on lit stdout d'un process
            // externe
            LOGGER.error(e,e);
        }
    }

}
