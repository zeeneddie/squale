package com.airfrance.squalecommon.util.mail;

/**
 * Exception d'envoi de mail Cette classe permet de remonter les erreurs lors de l'envoi de mail
 */
public class MailException
    extends Exception
{

    /**
     * Constructeur
     * 
     * @param e exception
     */
    public MailException( Exception e )
    {
        super( e );
    }

    /**
     * Constructor
     * 
     * @param message Error message
     */
    public MailException( String message )
    {
        super( message );
    }

}
