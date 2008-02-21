//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\util\\csv\\CSVException.java

package com.airfrance.squalix.util.csv;


/**
 * Exception
 * @author m400842
 * @version 1.0
 */
public class CSVException extends Exception {
    
    /**
     * Constructeur spécifiant un message.
     * 
     * @param pMessage Message de l'exception
     * @roseuid 429C2A390354
     */
    public CSVException(final String pMessage) {
        super(pMessage);
    }
    
    /**
     * Constructeur de recopie.
     * @param pE Nom de l'exaception cause
     * @roseuid 42D2560200AA
     */
    public CSVException(Exception pE) {
        super(pE);
    }
}
