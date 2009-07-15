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
package org.squale.squalecommon.enterpriselayer.businessobject.component;

import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Grille utilisée dans un audit
 * 
 * @hibernate.class table="AuditGridBO" mutable="true"
 */
public class AuditGridBO
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /** Projet */
    private ProjectBO mProject;

    /** Grille */
    private QualityGridBO mGrid;

    /** Audit (lien inverse */
    private AuditBO mAudit;

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="AuditGridId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="auditgrid_sequence"
     * @roseuid 42BFDEB701B4
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     * @roseuid 42BFDEB701F2
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * @return grille
     * @hibernate.many-to-one class="org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO"
     *                        column="QualityGridId" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true" 
     */
    public QualityGridBO getGrid()
    {
        return mGrid;
    }

    /**
     * @return grille
     * @hibernate.many-to-one class="org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO"
     *                        column="ProjectId" not-null="false" cascade="none" outer-join="auto" update="true"
     *                        insert="true"
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * @param pGridBO grille qualité
     */
    public void setGrid( QualityGridBO pGridBO )
    {
        mGrid = pGridBO;
    }

    /**
     * @param pProjectBO projet
     */
    public void setProject( ProjectBO pProjectBO )
    {
        mProject = pProjectBO;
    }

    /**
     * @return grilles qualité de l'audit
     * @hibernate.many-to-one column="AuditId"
     *                        class="org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO"
     *                        cascade="none" outer-join="auto" update="true" insert="true"
     */
    public AuditBO getAudit()
    {
        return mAudit;
    }

    /**
     * @param pAuditBO audit
     */
    public void setAudit( AuditBO pAuditBO )
    {
        mAudit = pAuditBO;
    }

}
