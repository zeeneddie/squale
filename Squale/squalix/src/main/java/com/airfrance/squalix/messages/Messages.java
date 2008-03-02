package com.airfrance.squalix.messages;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Permet l'externalisation des chaînes de caractères
 * 
 * @author M400842
 */
public class Messages
    extends BaseMessages
{
    /** Instance */
    static private Messages mInstance = new Messages();

    /**
     * Constructeur privé pour éviter l'instanciation
     * 
     * @roseuid 42C1678502C4
     */
    private Messages()
    {
        super( "com.airfrance.squalix.messages.squalix" );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     * @roseuid 42C1678502C5
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @param pValues les paramètres
     * @return la chaîne associée.
     * @roseuid 42C1678502C5
     */
    public static String getString( String pKey, String[] pValues )
    {
        return mInstance.getBundleString( pKey, pValues );
    }

}
