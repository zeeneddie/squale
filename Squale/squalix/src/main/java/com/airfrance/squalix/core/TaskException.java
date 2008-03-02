package com.airfrance.squalix.core;

/**
 * Exception dans une tâche
 */
public class TaskException
    extends Exception
{

    /**
     * Constructeur
     */
    public TaskException()
    {
        super();
    }

    /**
     * @param message message
     */
    public TaskException( String message )
    {
        super( message );
    }

    /**
     * @param cause cause
     */
    public TaskException( Throwable cause )
    {
        super( cause );
    }

    /**
     * @param message message
     * @param cause cause
     */
    public TaskException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
