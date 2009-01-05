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
package com.airfrance.squalecommon.datatransfertobject.result;

import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.rule.QualityRuleDTO;

/**
 * DTO pour les résultats qualités
 */
public class QualityResultDTO
{

    /**
     * Note moyenne du résultat qualité
     */
    protected float mMeanMark;

    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId;

    /**
     * Projet sur lequel est calculé le résultat qualité.
     */
    protected ComponentDTO mProject;

    /**
     * Audit durant lequel a été généré le résultat
     */
    protected AuditDTO mAudit;

    /**
     * La règle
     */
    protected QualityRuleDTO mRule;

    /**
     * @return la règle
     */
    public QualityRuleDTO getRule()
    {
        return mRule;
    }

    /**
     * Sets the value of the mRule property.
     * 
     * @param pRule the new value of the mRule property
     */
    public void setRule( QualityRuleDTO pRule )
    {
        mRule = pRule;
    }

    /**
     * Access method for the mMeanMark property.
     * 
     * @return the current value of the mMeanMark property
     */
    public float getMeanMark()
    {
        return mMeanMark;
    }

    /**
     * Sets the value of the mMeanMark property.
     * 
     * @param pMeanMark the new value of the mMeanMark property
     */
    public void setMeanMark( float pMeanMark )
    {
        mMeanMark = pMeanMark;
    }

    /**
     * Access method for the mProject property.
     * 
     * @return the current value of the mProject property
     */
    public ComponentDTO getProject()
    {
        return mProject;
    }

    /**
     * Sets the value of the mProject property.
     * 
     * @param pProject the new value of the mProject property
     */
    public void setProject( ComponentDTO pProject )
    {
        mProject = pProject;
    }

    /**
     * Access method for the mAudit property.
     * 
     * @return the current value of the mAudit property
     */
    public AuditDTO getAudit()
    {
        return mAudit;
    }

    /**
     * Sets the value of the mAudit property.
     * 
     * @param pAudit the new value of the mAudit property
     */
    public void setAudit( AuditDTO pAudit )
    {
        mAudit = pAudit;
    }

    /**
     * Constructeur par défaut
     */
    public QualityResultDTO()
    {
        mMeanMark = -1;
    }
}
