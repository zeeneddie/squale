package org.squale.squalerest.exception;

public class SqualeRestException
    extends Exception
{

    public SqualeRestException()
    {

    }

    public SqualeRestException( String message )
    {
        super( message );
    }

    public SqualeRestException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public SqualeRestException( Throwable cause )
    {
        super( cause );
    }

}
