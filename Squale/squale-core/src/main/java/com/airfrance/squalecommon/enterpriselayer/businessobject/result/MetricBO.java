/*
 * Créé le 16 déc. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

/**
 * @author m401540
 * @version 1.0
 * @hibernate.class table="Metric" mutable="true" discriminator-value="Metric"
 * @hibernate.discriminator column="subclass"
 */
public abstract class MetricBO
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Nom de la métrique
     */
    protected String mName;

    /**
     * Mesure de la métrique
     */
    protected MeasureBO mMeasure;

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="MetricId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="metric_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="Name" column="Name" type="string" not-null="false" unique="false"
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @param pName value of mName
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mMeasure property.
     * 
     * @return the current measure
     * @hibernate.many-to-one name="measure" column="MeasureId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO"
     */
    public MeasureBO getMeasure()
    {
        return mMeasure;
    }

    /**
     * @param pMeasure measure de la métrique
     */
    public void setMeasure( MeasureBO pMeasure )
    {
        mMeasure = pMeasure;
    }

    /**
     * Constructeur par défaut
     */
    public MetricBO()
    {
        mId = -1;
    }

    /**
     * @return la valeur du metric (voir les classes filles)
     */
    public abstract Object getValue();

    /**
     * @param value valeur du metric (Integer, Boolean...)
     */
    public abstract void setValue( Object value );

    /**
     * @return vrai si on peut reprensenter la valeur sous forme de sting
     */
    public boolean isPrintable()
    {
        return false;
    }
}
