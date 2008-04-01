package com.airfrance.squalix.tools.scm.task;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Récupérateur des messages pour l'analyseur de source
 */
public class ScmMessages
    extends BaseMessages
{
    /** Instance */
    private static ScmMessages mInstance = new ScmMessages();

    /**
     * Private constructor to avoid new creations of instances
     */
    private ScmMessages()
    {
        super( "com.airfrance.squalix.tools.scm.task.scm" );
    }

    /**
     * Returns string relating to a key
     * 
     * @param pKey key name.
     * @return description.
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

    /**
     * Returns string relating to a key
     * 
     * @param pKey key name.
     * @param pValues values to insert in the string
     * @return description.
     */
    public static String getString( String pKey, Object[] pValues )
    {
        return mInstance.getBundleString( pKey, pValues );
    }

    /**
     * Get description relating to a key
     * 
     * @param pKey key
     * @param pArgument argument
     * @return description
     */
    public static String getString( String pKey, Object pArgument )
    {
        return getString( pKey, new Object[] { pArgument } );
    }
}
