package com.airfrance.squalix.tools.compiling;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Classe gérant le RessourceBundle pour la tâche de compilation.
 * 
 * @author m400832 (by rose)
 * @version 1.0
 */
public class CompilingMessages
    extends BaseMessages
{
    /** Instance */
    static private CompilingMessages mInstance = new CompilingMessages();

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

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation directe.
     */
    private CompilingMessages()
    {
        super( "com.airfrance.squalix.tools.compiling.compiling" );
    }
}
