/*
 * Créé le 22 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.access.excel.object;

import com.airfrance.welcom.outils.Util;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class AccessKey
{

    /** id */
    private int idAccessKey = 0;

    /** Nombre de tabulation */
    private String tab = "";

    /** Valeur de la clef */
    private String accesskey = "";

    /** libelle */
    private String label = "";

    /** type de donnée */
    private String type = "";

    /**
     * @return key
     */
    public String getAccesskey()
    {
        return accesskey;
    }

    /**
     * @return label
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * @return tab
     */
    public String getTab()
    {
        return tab;
    }

    /**
     * @return type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param string key
     */
    public void setAccesskey( final String string )
    {
        accesskey = string;
    }

    /**
     * @param string label
     */
    public void setLabel( final String string )
    {
        label = string;
    }

    /**
     * @param string tab
     */
    public void setTab( final String string )
    {
        tab = string;
    }

    /**
     * @param string type
     */
    public void setType( final String string )
    {
        type = string;
    }

    /**
     * @return idAccessKey
     */
    public int getIdAccessKey()
    {
        return idAccessKey;
    }

    /**
     * @param i :idAccessKey
     */
    public void setIdAccessKey( final int i )
    {
        idAccessKey = i;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( final Object obj )
    {

        return Util.isEquals( this.getAccesskey(), ( (AccessKey) obj ).getAccesskey() )
            && Util.isEquals( this.getLabel(), ( (AccessKey) obj ).getLabel() )
            && Util.isEquals( this.getTab(), ( (AccessKey) obj ).getTab() )
            && Util.isEquals( this.getType(), ( (AccessKey) obj ).getType() )
            && ( this.getIdAccessKey() == ( (AccessKey) obj ).getIdAccessKey() );
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {

        return " AccessKey [ " + this.getIdAccessKey() + " - " + this.getAccesskey() + " - " + this.getLabel() + " - "
            + this.getTab() + " - " + this.getType() + " ]";
    }

}
