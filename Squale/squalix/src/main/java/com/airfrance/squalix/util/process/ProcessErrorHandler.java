package com.airfrance.squalix.util.process;

/**
 * Interface de traitement des erreurs de processus externe Un processus externe écrit des données sur le flux standard
 * ou d'erreur, le but de cette interface est de fournir un moyen pour intercepter et traiter ces erreurs.
 */
public interface ProcessErrorHandler
{

    /**
     * Traitement d'une erreur
     * 
     * @param pErrorMessage message d'erreur
     */
    void processError( String pErrorMessage );

}
