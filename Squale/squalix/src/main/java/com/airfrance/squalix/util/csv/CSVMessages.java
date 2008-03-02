package com.airfrance.squalix.util.csv;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author m400842 (by rose)
 * @version 1.0
 */
public class CSVMessages
    extends BaseMessages
{
    /** Instance */
    static private CSVMessages mInstance = new CSVMessages();

    /**
     * Retourne la chaîne de caractère spécifiée par la clé.
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
     * Constructeur privé pour éviter l'instanciation
     * 
     * @roseuid 42CE768B01BC
     */
    private CSVMessages()
    {
        super( "com.airfrance.squalix.util.csv.csv" );
    }
}
