package com.airfrance.squalix.tools.javancss;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * This class permit to get back a message associate to a key. So there is only the key in the code and all real
 * messages are in a same file outside the code.
 */
public final class JavancssMessages
    extends BaseMessages
{

    /** Instance of JavancssMessages */
    private static JavancssMessages mMessages = new JavancssMessages();

    /**
     * Default constructor
     */
    private JavancssMessages()
    {
        super( "com.airfrance.squalix.tools.javancss.javancssMessages" );
    }

    /**
     * Return the String associate to the key in the file *.properties
     * 
     * @param pKey The key
     * @return The String associate to the key
     */
    public static String getString( String pKey )
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
    public static String getString( String pKey, Object[] pValues )
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
    public static String getString( String pKey, Object pArgument )
    {
        return getString( pKey, new Object[] { pArgument } );
    }

}
