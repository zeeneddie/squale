/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
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
     * @hibernate.property name="Name" column="Name" type="string" not-null="false" unique="false" update="true"
     *                     insert="true"
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
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO"
     *                        column="MeasureId" cascade="none" outer-join="auto" update="true" insert="true"
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
