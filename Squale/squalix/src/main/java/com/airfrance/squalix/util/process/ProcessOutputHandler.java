package com.airfrance.squalix.util.process;

/**
 * Traitement de la sortie d'un processus Le traitement de la sortie d'un processus externe se fait au travers d'un
 * thread pour empêcher des deadlock. Cette interface permet de traiter cette sortie ligne par ligne
 */
public interface ProcessOutputHandler
{
    /**
     * Traitement d'une ligne
     * 
     * @param pOutputLine ligne de sortie
     */
    void processOutput( String pOutputLine );
}
