/*
 * Créé le 24 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.bean;

import java.util.Comparator;

/**
 * Classe WCouple
 */
public class WCouple
    implements Comparator
{
    /** la value */
    private String value = "";

    /** le label */
    private String label = "";

    /**
     * Constructeur
     * 
     * @param pValue la value
     * @param pLabel le label
     */
    public WCouple( final String pValue, final String pLabel )
    {
        value = pValue;
        label = pLabel;
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
     * Returns the value.
     * 
     * @return String
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Sets the label.
     * 
     * @param pLabel The label to set
     */
    public void setLabel( final String pLabel )
    {
        label = pLabel;
    }

    /**
     * Sets the value.
     * 
     * @param pValue The value to set
     */
    public void setValue( final String pValue )
    {
        value = pValue;
    }

    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare( final Object o1, final Object o2 )
    {
        return ( (String) o1 ).compareTo( (String) o2 );
    }
}