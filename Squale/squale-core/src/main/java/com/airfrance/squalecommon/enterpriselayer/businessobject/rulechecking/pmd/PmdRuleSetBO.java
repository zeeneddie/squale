package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.pmd;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleSetBO;
import com.airfrance.squalecommon.util.ConstantRulesChecking;

/**
 * Jeu de règles PMD Le fichier PMD est stocké sous la forme d'un Blob dans la base de données
 * 
 * @hibernate.subclass mutable="true" discriminator-value="Pmd"
 */
public class PmdRuleSetBO
    extends RuleSetBO
{

    /** Nom de la règle pour les JSPS non conforme au XHTML */
    public static final String XHTML_RULE_NAME = "NotXHTMLCompliant";

    /** Sévérité de la règle pour les JSPS non conforme au XHTML */
    public static final String XHTML_RULE_SEVERITY = ConstantRulesChecking.ERROR_LABEL;

    /** Catégorie de la règle pour les JSPS non conforme au XHTML */
    public static final String XHTML_RULE_CATEGORY = "jspstandard";

    /**
     * Valeur sous forme de chaine de bytes
     */
    private byte[] mValue;

    /** Langage */
    private String mLanguage;

    /**
     * Access method for the mFileName property.
     * 
     * @return the current value of the FileName property
     * @hibernate.property name="Value" column="FileContent"
     *                     type="com.airfrance.jraf.provider.persistence.hibernate.BinaryBlobType" not-null="false"
     *                     unique="false"
     */
    public byte[] getValue()
    {
        return mValue;
    }

    /**
     * @param pValue sous forme de byte[]
     */
    public void setValue( byte[] pValue )
    {
        mValue = pValue;
    }

    /**
     * Access method for the mLanguage property.
     * 
     * @return language
     * @hibernate.property name="Language" column="Language" type="string" not-null="false" unique="false"
     */
    public String getLanguage()
    {
        return mLanguage;
    }

    /**
     * @param pLanguage langage
     */
    public void setLanguage( String pLanguage )
    {
        mLanguage = pLanguage;
    }
}
