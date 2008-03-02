package com.airfrance.squalix.core.exception;

/**
 * Exception liée à la configuration
 * 
 * @author M400842
 */
public class ConfigurationException
    extends Exception
{

    /**
     * @param cause cause
     */
    public ConfigurationException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message message
     * @param cause cause
     */
    public ConfigurationException( String message, Throwable cause )
    {
        super( message, cause );
    }

    /**
     * @param pMessage message de l'exception
     * @roseuid 42C4164501D6
     */
    public ConfigurationException( String pMessage )
    {
        super( pMessage );
    }
}
