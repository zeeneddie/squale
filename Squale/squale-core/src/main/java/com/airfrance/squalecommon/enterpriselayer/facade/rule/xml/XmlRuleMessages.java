package com.airfrance.squalecommon.enterpriselayer.facade.rule.xml;

import com.airfrance.squalecommon.util.messages.BaseMessages;

/**
 * Messages pour les règles qualité
 */
class XmlRuleMessages
    extends BaseMessages
{
    /** Instance */
    static private XmlRuleMessages mInstance = new XmlRuleMessages();

    /**
     * Constructeur par défaut. Privé pour éviter l'instanciation.
     */
    private XmlRuleMessages()
    {
        super( "com.airfrance.squalecommon.enterpriselayer.facade.rule.xml.xml_rule_messages" );
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
