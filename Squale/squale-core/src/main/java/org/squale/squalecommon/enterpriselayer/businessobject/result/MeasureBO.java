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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;

/**
 * @author m400842 (by rose)
 * @version 1.0
 * @hibernate.class table="Measure" mutable="true" discriminator-value="Measure"
 * @hibernate.discriminator column="subclass"
 */
public abstract class MeasureBO
    implements Serializable
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /**
     * Nom de la tâche ayant créé ce résultat
     */
    protected String mTaskName;

    /**
     * Audit durant lequel les mesures ont été générées
     */
    protected AuditBO mAudit;

    /**
     * Composant sur lequel les mesures ont été effectuées
     */
    protected AbstractComponentBO mComponent;

    /**
     * Liste des métriques
     */
    protected Map mMetrics = new HashMap();

    /**
     * Access method for the mMetrics property.
     * 
     * @return the current value of the mTaskName property
     * @hibernate.map lazy="true" cascade="save-update" sort="unsorted"
     * @hibernate.index column="Name" type="string"
     * @hibernate.key column="MeasureId"
     * @hibernate.one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO"
     */
    public Map getMetrics()
    {
        return mMetrics;
    }

    /**
     * Sets the value of the mMetrics property.
     * 
     * @param pMetrics the new value of the mMetrics property
     */
    public void setMetrics( Map pMetrics )
    {
        mMetrics = pMetrics;
    }

    /**
     * Access method for the mTaskName property.
     * 
     * @return the current value of the mTaskName property
     * @hibernate.property name="TaskName" column="TaskName" type="string" not-null="false" unique="false" update="true"
     *                     insert="true"
     * @roseuid 42BACEE30268
     */
    public String getTaskName()
    {
        return mTaskName;
    }

    /**
     * Access method for the mAudit property.
     * 
     * @return the current value of the mAudit property
     * @hibernate.many-to-one column="AuditId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO"
     *                        cascade="none" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BACEE30277
     */
    public AuditBO getAudit()
    {
        return mAudit;
    }

    /**
     * Sets the value of the mAudit property.
     * 
     * @param pAudit the new value of the mAudit property
     * @roseuid 42BACEE30278
     */
    public void setAudit( AuditBO pAudit )
    {
        mAudit = pAudit;
    }

    /**
     * Access method for the mComponent property.
     * 
     * @return the current value of the mComponent property
     * @hibernate.many-to-one column="ComponentId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO"
     *                        cascade="save-update" not-null="true" outer-join="auto" update="true" insert="true"
     * @roseuid 42BACEE3027A
     */
    public AbstractComponentBO getComponent()
    {
        return mComponent;
    }

    /**
     * Sets the value of the mComponent property.
     * 
     * @param pComponent the new value of the mComponent property
     * @roseuid 42BACEE30287
     */
    public void setComponent( AbstractComponentBO pComponent )
    {
        mComponent = pComponent;
    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="MeasureId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="measure_sequence"
     * @roseuid 42BFF5630333
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFF5630342
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CA3CBF035C
     */
    public MeasureBO()
    {
    }

    /**
     * Sets the value of the mTaskName property
     * 
     * @param pName le nom de la tache
     * @roseuid 42CBC8620303
     */
    public void setTaskName( String pName )
    {
        mTaskName = pName;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object pObj )
    {
        boolean ret = false;
        MeasureBO measure = null;
        if ( pObj instanceof MeasureBO )
        {
            measure = (MeasureBO) pObj;
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append( mTaskName, measure.getTaskName() );
            equalsBuilder.append( mId, measure.getId() ); // Attention: utilisation de l'id est
            // fortement déconseillée par Hibernate
            ret = equalsBuilder.isEquals();
        }
        return ret;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        HashCodeBuilder hashBuilder = new HashCodeBuilder();
        hashBuilder.append( mTaskName );
        hashBuilder.append( mId ); // Attention: utilisation de l'id est
        // fortement déconseillée par Hibernate
        return hashBuilder.toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        ToStringBuilder stringBuilder = new ToStringBuilder( this );
        stringBuilder.append( "TaskName", mTaskName );
        stringBuilder.append( "Id", mId ); // Attention: utilisation de l'id est
        // fortement déconseillée par Hibernate
        return stringBuilder.toString();
    }
}
