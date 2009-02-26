package com.airfrance.squalix.tools.abstractgenerictask;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * This class retrieves messages from the *.properties file in which are stored the key and values related to the
 * GenericTask.
 */
public final class AbstractGenericTaskMessages
    extends BaseMessages
{
    /** Instance of {@link AbstractGenericTask} */
    private static AbstractGenericTaskMessages mMessages = new AbstractGenericTaskMessages();

    /**
     * A private constructor to avoid any new instance
     */
    private AbstractGenericTaskMessages()
    {
        super( "com.airfrance.squalix.tools.abstractgenerictask.AbstractGenericTaskMessages" );
    }

    /**
     * This method returns the value of a key passed in as parameter
     * 
     * @param pKey the key the value of which has to be retrieved
     * @return the value of the key stored in the *.properties file
     */
    public static String getMessage( String pKey )
    {
        return mMessages.getBundleString( pKey );
    }

    /**
     * This method returns the value of a key passed in as parameter
     * 
     * @param pKey name of the key
     * @param pValues values that has to be concatenated in the String
     * @return the concatenated String
     */
    public static String getMessage( String pKey, Object[] pValues )
    {
        return mMessages.getBundleString( pKey, pValues );
    }

    /**
     * This method returns the value of a key passed in as parameter
     * 
     * @param pKey name of the key
     * @param pArgument argument
     * @return the concatenated String
     */
    public static String getMessage( String pKey, Object pArgument )
    {
        return getMessage( pKey, new Object[] { pArgument } );
    }
}
