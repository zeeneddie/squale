package com.airfrance.squalecommon.enterpriselayer.businessobject.config.web;

import java.util.HashSet;
import java.util.Set;

/**
 * Configuration pour l'affichage de la volumétrie
 * 
 * @hibernate.subclass lazy="true" discriminator-value="volumetry"
 */
public class VolumetryConfBO
    extends AbstractDisplayConfBO
{

    /** Le type du composant (application, projet) */
    private String mComponentType;

    /** L'ensemble des TREs correspondant à une mesure de la volumétrie */
    private Set mTres = new HashSet();

    /**
     * @return le type du composant
     * @hibernate.property name="componentType" column="componentType" type="string"
     */
    public String getComponentType()
    {
        return mComponentType;
    }

    /**
     * @return les TREs
     * @hibernate.set table="Volumetry_Measures" lazy="false" inverse="false"
     * @hibernate.collection-key column="VolumetryId"
     * @hibernate.collection-element column="Measure" type="string" not-null="true"
     */
    public Set getTres()
    {
        return mTres;
    }

    /**
     * @param pType le type du composant
     */
    public void setComponentType( String pType )
    {
        mComponentType = pType;
    }

    /**
     * @param pTres les TREs
     */
    public void setTres( Set pTres )
    {
        mTres = pTres;
    }

    /**
     * Ajoute un nom de tre
     * 
     * @param pTre le nom du tre
     */
    public void addTre( String pTre )
    {
        mTres.add( pTre );
    }

    /**
     * {@inheritDoc}
     * 
     * @param pVisitor {@inheritDoc}
     * @param pArgument {@inheritDoc}
     * @return {@inheritDoc}
     * @see com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO#accept(com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.DisplayConfVisitor,
     *      java.lang.Object)
     */
    public Object accept( DisplayConfVisitor pVisitor, Object pArgument )
    {
        return pVisitor.visit( this, pArgument );
    }

    /**
     * {@inheritDoc}
     * 
     * @param obj {@inheritDoc}
     * @return {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object obj )
    {
        boolean result = false;
        if ( obj instanceof VolumetryConfBO )
        {
            VolumetryConfBO volum = (VolumetryConfBO) obj;
            result = volum.getComponentType().equals( getComponentType() );
            result &= volum.getTres().equals( getTres() );
        }
        return result;
    }
}
