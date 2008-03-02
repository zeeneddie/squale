package com.airfrance.squalix.tools.computing;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author M400843
 */
public class ComputingMessages
    extends BaseMessages
{
    /** Instance */
    static private ComputingMessages mInstance = new ComputingMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private ComputingMessages()
    {
        super( "com.airfrance.squalix.tools.computing.computing_messages" );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @param pValues les valeurs à insérer dans la chaine
     * @return la chaîne associée.
     */
    public static String getString( String pKey, String[] pValues )
    {
        return mInstance.getBundleString( pKey, pValues );
    }

}
