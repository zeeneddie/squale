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
//Source file: D:\\cc_views\\squale_v0_0_act_M400843\\squale\\src\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\businessobject\\result\\MarkBO.java

package org.squale.squalecommon.enterpriselayer.businessobject.result;

import java.io.Serializable;

import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;

/**
 * Note du composant
 * 
 * @author m400842
 * @version 1.0
 * @hibernate.class table="Mark" mutable="true"
 */
public class MarkBO
    implements Serializable
{

    /** valeur de la note pour les composants non notés */
    public static final float NOT_NOTED_VALUE = -1.0f;

    /**
     * Valeur de la note du composant.
     */
    private float mValue;

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Résultat de Pratique dans lequel la note est prise en compte.
     */
    private PracticeResultBO mPractice;

    /**
     * Composant possédant la note.
     */
    private AbstractComponentBO mComponent;

    /**
     * Access method for the mValue property.
     * 
     * @return the current value of the mValue property
     * @hibernate.property name="value" column="Value" type="float" not-null="true" unique="false" update="true"
     *                     insert="true"
     */
    public float getValue()
    {
        return mValue;
    }

    /**
     * Sets the value of the mValue property.
     * 
     * @param pValue the new value of the mValue property
     */
    public void setValue( float pValue )
    {
        mValue = pValue;
    }

    /**
     * Access method for the mComponent property.
     * 
     * @return the current value of the mComponent property
     * @hibernate.many-to-one column="ComponentId"
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO"
     *                        cascade="none" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BACEE3015E
     */
    public AbstractComponentBO getComponent()
    {
        return mComponent;
    }

    /**
     * Sets the value of the mComponent property.
     * 
     * @param pComponent the new value of the mComponent property
     * @roseuid 42BACEE3015F
     */
    public void setComponent( AbstractComponentBO pComponent )
    {
        mComponent = pComponent;
    }

    /**
     * Access method for the mPractice property.
     * 
     * @return the current value of the mPractice property
     * @hibernate.many-to-one column="PracticeResultId"
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO"
     *                        cascade="none" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BACEE30161
     */
    public PracticeResultBO getPractice()
    {
        return mPractice;
    }

    /**
     * Sets the value of the mPractice property.
     * 
     * @param pPractice the new value of the mPractice property
     * @roseuid 42BACEE3016E
     */
    public void setPractice( PracticeResultBO pPractice )
    {
        mPractice = pPractice;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="MarkId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="mark_sequence"
     * @roseuid 42BFF4E803CC
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFF4E90003
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CA3E3F01C0
     */
    public MarkBO()
    {
        mId = -1;
    }

    /**
     * Constructeur par défaut
     * 
     * @param pValue valeur de la note
     * @param pComponent sous-projet à laquelle la note doit être rattachée
     * @param pPractice pratique à laquelle la note doit être rattachée
     * @roseuid 42CA3E3F01D0
     */
    public MarkBO( int pValue, AbstractComponentBO pComponent, PracticeResultBO pPractice )
    {
        mId = -1;
        mValue = pValue;
        mComponent = pComponent;
        mPractice = pPractice;
    }
}
