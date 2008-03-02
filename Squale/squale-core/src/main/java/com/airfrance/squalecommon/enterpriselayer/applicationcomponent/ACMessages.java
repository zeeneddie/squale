package com.airfrance.squalecommon.enterpriselayer.applicationcomponent;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author M400843
 */
public class ACMessages
    extends BaseMessages
{
    /** Instance */
    static private ACMessages mInstance = new ACMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private ACMessages()
    {
        super( "com.airfrance.squalecommon.enterpriselayer.applicationcomponent.ac_messages" );
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
