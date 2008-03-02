package com.airfrance.squalix.util.file;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages pour FileUtility
 */
public class FileMessages
    extends BaseMessages
{
    /** Instance */
    static private FileMessages mInstance = new FileMessages();

    /**
     * Retourne la chaîne de caractère spécifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

    /**
     * Constructeur privé pour éviter l'instanciation
     */
    private FileMessages()
    {
        super( "com.airfrance.squalix.util.file.file" );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @param pValues les valeurs à insérer dans la chaine
     * @return la chaîne associée.
     */
    public static String getString( String pKey, Object[] pValues )
    {
        return mInstance.getBundleString( pKey, pValues );
    }

    /**
     * @param pKey clef
     * @param pArgument argument
     * @return chaîne associée
     */
    public static String getString( String pKey, Object pArgument )
    {
        return getString( pKey, new Object[] { pArgument } );
    }
}
