package com.airfrance.squalix.tools.rsm;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages pour la tache RSM
 */
public class RSMMessages
    extends BaseMessages
{

    /** Instance */
    static private RSMMessages mInstance = new RSMMessages();

    /**
     * Constructeur privé pour éviter l'instanciation
     */
    private RSMMessages()
    {
        super( "com.airfrance.squalix.tools.rsm.rsm" );
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
