package com.airfrance.squaleweb.applicationlayer.action.export.ppt;

/**
 * Exception launched during ppt generation
 */
public class PPTGeneratorException
 extends Exception {
    
    /**
     * serial number
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor 
     * @param message error message
     */
   public PPTGeneratorException( String message )
   {
       super( message );
   }

}
