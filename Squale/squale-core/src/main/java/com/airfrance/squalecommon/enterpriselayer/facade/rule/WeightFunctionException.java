package com.airfrance.squalecommon.enterpriselayer.facade.rule;

/**
 * Exception lancée lors de l'avaluation d'une fonction de pondération
 */
public class WeightFunctionException extends Exception {

    /**
     * @param pString texte
     */
    public WeightFunctionException(String pString) {
        super(pString);
    }

    /**
     * @param pString texte
     * @param e exception
     */
    public WeightFunctionException(String pString, Throwable e) {
        super(pString, e);
    }

    /**
     * @param e exception
     */
    public WeightFunctionException(Throwable e) {
        super(e);
    }

}