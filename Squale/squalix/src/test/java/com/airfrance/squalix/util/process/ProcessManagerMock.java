package com.airfrance.squalix.util.process;

import java.io.File;
import java.io.IOException;

/**
 * Un Mock pour les tests unitaires où il faut utiliser un ProcessManager
 */
public class ProcessManagerMock
    extends ProcessManager
{

    /**
     * Valeur que doit renvoyer le flux
     */
    private String mExpectedOutput;

    /**
     * @param pCommand la liste de commandes à exécuter
     * @param pEnvp les variables d'environnement à surcharger
     * @param pDir le fichier
     */
    public ProcessManagerMock( String[] pCommand, String[] pEnvp, File pDir )
    {
        super( pCommand, pEnvp, pDir );
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalix.util.process.ProcessManager#startProcess(com.airfrance.squalix.util.process.ProcessErrorHandler)
     */
    public int startProcess( ProcessErrorHandler pHandler )
        throws IOException, InterruptedException
    {
        getOutputHandler().processOutput( mExpectedOutput );
        return 0;
    }

    /**
     * @param pExpectedOutput la nouvelle valeur du flux
     */
    public void setExpectedOutput( String pExpectedOutput )
    {
        mExpectedOutput = pExpectedOutput;

    }

}