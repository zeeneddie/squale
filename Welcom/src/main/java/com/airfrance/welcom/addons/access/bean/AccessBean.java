package com.airfrance.welcom.addons.access.bean;

import java.io.Serializable;

/**
 * @author user To change this generated comment edit the template variable "typecomment":
 *         Window>Preferences>Java>Templates. To enable and disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class AccessBean
    implements Serializable
{

    /** ID pour la serialization */
    static final long serialVersionUID = -245934596856293274L;

    /** Id profil */
    private String idProfile;

    /** Id access Key */
    private String idAccessKey;

    /** Nom de la clef */
    private String accesskey;

    /** Nombre de tabulation pour la presentation */
    private String tab;

    /** Libelle */
    private String label;

    /** Type */
    private String type;

    /** Valeur */
    private String value;

    /** Nouvelle valeur */
    private String valueNew;

    /**
     * Constructor for Droits.
     */
    public AccessBean()
    {
        super();
    }

    /**
     * Returns the label.
     * 
     * @return String
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Returns the tab.
     * 
     * @return String
     */
    public String getTab()
    {
        return tab;
    }

    /**
     * Returns the type.
     * 
     * @return String
     */
    public String getType()
    {
        return type;
    }

    /**
     * Sets the label.
     * 
     * @param pLabel The label to set
     */
    public void setLabel( final String pLabel )
    {
        this.label = pLabel;
    }

    /**
     * Sets the tab.
     * 
     * @param pTab The tab to set
     */
    public void setTab( final String pTab )
    {
        this.tab = pTab;
    }

    /**
     * Sets the type.
     * 
     * @param pType The type to set
     */
    public void setType( final String pType )
    {
        this.type = pType;
    }

    /**
     * Returns the value.
     * 
     * @return String
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Sets the value.
     * 
     * @param pValue The value to set
     */
    public void setValue( final String pValue )
    {
        this.value = pValue;
    }

    /**
     * Returns the valueNew.
     * 
     * @return String
     */
    public String getValueNew()
    {
        return valueNew;
    }

    /**
     * Sets the valueNew.
     * 
     * @param pValueNew The valueNew to set
     */
    public void setValueNew( final String pValueNew )
    {
        this.valueNew = pValueNew;
    }

    /**
     * @return IdAccessKey
     */
    public String getIdAccessKey()
    {
        return idAccessKey;
    }

    /**
     * @return IdProfile
     */
    public String getIdProfile()
    {
        return idProfile;
    }

    /**
     * @return key
     */
    public String getAccesskey()
    {
        return accesskey;
    }

    /**
     * @param string IdAccessKey
     */
    public void setIdAccessKey( final String string )
    {
        idAccessKey = string;
    }

    /**
     * @param string IdProfile
     */
    public void setIdProfile( final String string )
    {
        idProfile = string;
    }

    /**
     * @param string key
     */
    public void setAccesskey( final String string )
    {
        accesskey = string;
    }

}
