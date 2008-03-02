package com.airfrance.squalix.tools.clearcase.configuration;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author m400832 (by rose)
 * @version 1.0
 */
public class ClearCaseMessages
    extends BaseMessages
{
    /** Instance */
    static private ClearCaseMessages mInstance = new ClearCaseMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private ClearCaseMessages()
    {
        super( "com.airfrance.squalix.tools.clearcase.clearcase" );
    }

    /**
     * Retourne la chaîne de caractère identifiée par la clé.
     * 
     * @param pKey nom de la clé.
     * @return la chaîne associée.
     * @roseuid 42D3C0FE0325
     */
    public static String getString( String pKey )
    {
        return mInstance.getBundleString( pKey );
    }
}
