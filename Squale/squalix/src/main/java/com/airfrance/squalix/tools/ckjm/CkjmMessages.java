package com.airfrance.squalix.tools.ckjm;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages pour la tâche ckjm
 */
public class CkjmMessages
    extends BaseMessages
{
    /** Instance */
    static private CkjmMessages mInstance = new CkjmMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private CkjmMessages()
    {
        super( "com.airfrance.squalix.tools.ckjm.ckjm" );
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
}
