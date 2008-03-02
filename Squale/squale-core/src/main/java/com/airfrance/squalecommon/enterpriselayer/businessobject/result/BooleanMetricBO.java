/*
 * Créé le 16 déc. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

/**
 * @author M401540
 * @hibernate.subclass discriminator-value="Bool"
 */
public class BooleanMetricBO
    extends MetricBO
{
    /**
     * Valeur boolean du mérique
     */
    protected Boolean mValue;

    /**
     * Access method for the mValue property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="Value" column="Boolean_val" type="boolean" not-null="false" unique="false"
     */
    public Object getValue()
    {
        return mValue;
    }

    /**
     * Sets the value of the mValue property.
     * 
     * @param pValue the new value of the mValue property
     */
    public void setValue( Object pValue )
    {
        mValue = (Boolean) pValue;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO#isPrintable()
     */
    public boolean isPrintable()
    {
        return true;
    }

}
