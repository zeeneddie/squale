package com.airfrance.squalix.tools.compiling.jsp.wsad;

/**
 * Traitement des exceptions de la factory
 */
public class TomcatCompilerException
    extends Exception
{

    /**
     * @param message message
     */
    public TomcatCompilerException( String message )
    {
        super( message );
    }
}
