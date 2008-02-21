package com.airfrance.squalix.tools.cpptest;

/**
 * Buffer d'erreur
 * Les erreurs provenant de l'exécution de CppTest
 * sont remontées sous la forme de lignes noyées dans
 * des traces. Cette classe permet de filtrer ces traces
 * et d'intercepter les messages d'erreur 
 */
public class ErrorFilter {
    /** Erreur courante */
    private StringBuffer mCurrentError;
    /** Indicateur d'état */
    private boolean mInsideError;
    
    /**
     * Constructeur
     */
    public ErrorFilter() {
        mInsideError = false;
        mCurrentError = new StringBuffer();
    }
    /**
     * Traitement d'une ligne
     * @param pLine ligne traitée
     */
    public void processLine(String pLine) {
        if (mInsideError) {
            // Pattern de fin d'erreur
            if (pLine.indexOf('^')>=0) {
                mInsideError = false;
            } else {
                appendError(pLine);
            }
        } else if (pLine.indexOf("error:")>=0) { // Détection du pattern
            mInsideError = true;
            appendError(pLine);
       }
    }
    
    /**
     * Ajout d'une ligne d'erreur
     * @param pLine ligne
     */
    private void appendError(String pLine) {
        mCurrentError.append(pLine);
        mCurrentError.append('\n');
    }
    
    /**
     * Erreur disponible
     * @return true si une erreur est prête à être consommée
     */
    public boolean errorAvailable() {
        return (mInsideError==false)&&mCurrentError.length()>0;
    }
    
    /**
     * Consommation d'une erreur
     * @return erreur courante
     */
    public String consumeError() {
        String result = mCurrentError.toString();
        mCurrentError.setLength(0);
        return result;
    }
}
