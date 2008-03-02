package com.airfrance.welcom.struts.action;

/**
 * @author M327837 classe qui assure la gestion des l'exception d'un erreur sur la verification des habilitations
 */
public class NoHabilitationException
    extends Exception
{

    /**
     * serial version
     */
    private static final long serialVersionUID = 1918261844678079975L;

    /**
     * Contructeur herités
     */
    public NoHabilitationException()
    {
    }

    /**
     * Contructeur herités
     * 
     * @param message message
     */
    public NoHabilitationException( String message )
    {
        super( message );
    }

    /**
     * Contructeur herités
     * 
     * @param cause cause
     */
    public NoHabilitationException( Throwable cause )
    {
        super( cause );
    }

    /**
     * Contructeur herités
     * 
     * @param message message
     * @param cause cause
     */
    public NoHabilitationException( String message, Throwable cause )
    {
        super( message, cause );
    }

}
