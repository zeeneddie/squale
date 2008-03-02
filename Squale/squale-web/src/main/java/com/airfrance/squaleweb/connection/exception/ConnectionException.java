package com.airfrance.squaleweb.connection.exception;

/**
 * Gestion des exceptions de connexion
 */
public class ConnectionException
    extends Exception
{

    /**
     * @param excep l'exception
     */
    public ConnectionException( Exception excep )
    {
        super( excep );
    }

}
