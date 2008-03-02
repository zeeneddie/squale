package com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * @author sportorico
 */
class XmlCheckstyleParsingMessages
    extends BaseMessages
{
    /** Instance */
    static private XmlCheckstyleParsingMessages mInstance = new XmlCheckstyleParsingMessages();

    /**
     * Constructeur privé pour éviter l'instanciation
     */
    private XmlCheckstyleParsingMessages()
    {
        super( "com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.xml.xml_checkstyle_messages" );
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
}
