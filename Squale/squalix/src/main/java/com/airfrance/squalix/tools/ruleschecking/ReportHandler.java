package com.airfrance.squalix.tools.ruleschecking;

/**
 * Traitement d'un rapport checkstyle Cette interface permet de réaliser le traitement des anomalies reportées dans un
 * rapport checkstyle. Toutes les informations disponibles dans le rapport sont passées en paramètre
 */
public interface ReportHandler
{
    /**
     * @param pFileName fichier
     * @param pLine ligne
     * @param pColumn colonne
     * @param pSeverity sévérité
     * @param pMessage message
     * @param pSource source
     */
    public void processError( String pFileName, String pLine, String pColumn, String pSeverity, String pMessage,
                              String pSource );
}
