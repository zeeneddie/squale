package com.airfrance.welcom.taglib.aide;

/**
 * Exception concernant l'aide
 */
public class AideException
    extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = -2724637079725690431L;

    /**
     * Constructeur
     * 
     * @param message le message de l'exception
     */
    public AideException( final String message )
    {
        super( message );
    }
}