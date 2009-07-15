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
package org.squale.squalecommon.enterpriselayer.businessobject.config;

/**
 * Récupérateur de sources
 * 
 * @hibernate.subclass lazy="false" discriminator-value="SourceManagement"
 */
public class SourceManagementBO
    extends AbstractTasksUserBO
{

    /** Audit de jalon possible */
    private boolean mMilestoneAudit;

    /** Audit de suivi possible */
    private boolean mNormalAudit;

    /**
     * Constructeur par défaut
     */
    public SourceManagementBO()
    {
        super();
        mMilestoneAudit = true;
        mNormalAudit = true;
    }

    /**
     * Méthode d'accès à mMilestoneAudit
     * 
     * @return true si l'audit de jalon est possible
     * @hibernate.property name="milestoneAudit" column="MilestoneAudit" type="boolean" update="true" insert="true"
     */
    public boolean isMilestoneAudit()
    {
        return mMilestoneAudit;
    }

    /**
     * Méthode d'accès à mNormalAudit
     * 
     * @return true si l'audit de suivi est possible
     * @hibernate.property name="normalAudit" column="NormalAudit" type="boolean" update="true" insert="true"
     */
    public boolean isNormalAudit()
    {
        return mNormalAudit;
    }

    /**
     * Change la valeur de mMilestoneAudit
     * 
     * @param pMilestoneAudit la nouvelle valeur de mMilestoneAudit
     */
    public void setMilestoneAudit( boolean pMilestoneAudit )
    {
        mMilestoneAudit = pMilestoneAudit;
    }

    /**
     * Change la valeur de mNormalAudit
     * 
     * @param pNormalAudit la nouvelle valeur de mNormalAudit
     */
    public void setNormalAudit( boolean pNormalAudit )
    {
        mNormalAudit = pNormalAudit;
    }

}
