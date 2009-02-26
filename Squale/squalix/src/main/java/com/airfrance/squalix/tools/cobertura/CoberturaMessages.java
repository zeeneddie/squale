package com.airfrance.squalix.tools.cobertura;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages class for CoberturaTask/CoberturaParser
 */
public final class CoberturaMessages
    extends BaseMessages
{

    /** Instance of CoberturaMessages */
    private static CoberturaMessages mMessages = new CoberturaMessages();

    /**
     * Default constructor
     */
    private CoberturaMessages()
    {
        /* Passing in the message.properties file which stores the messages */
        super( "com.airfrance.squalix.tools.cobertura.coberturaMessages" );
    }

    /**
     * Returns the String associate to the key in the file *.properties
     * 
     * @param pKey The key
     * @return The String associate to the key
     */
    public static String getMessage( String pKey )
    {
        return mMessages.getBundleString( pKey );
    }

    /**
     * Return the String associate to the key in the file *.properties and insert the object in the string
     * 
     * @param pKey The key name.
     * @param pValues List of arguments to insert
     * @return The string associate to the key with all arguments insert
     */
    public static String getMessage( String pKey, Object[] pValues )
    {
        return mMessages.getBundleString( pKey, pValues );
    }

    /**
     * Return the String associate to the key in the file *.properties and insert the argument in the string
     * 
     * @param pKey The key name
     * @param pArgument The argument to insert in the string
     * @return The string associate to the key with the argument insert
     */
    public static String getMessage( String pKey, Object pArgument )
    {
        return getMessage( pKey, new Object[] { pArgument } );
    }

}
