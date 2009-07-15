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
package org.squale.squalecommon.datatransfertobject.component;

import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;

/**
 * Grille qualité utilisée dans un audit
 */
public class AuditGridDTO
    implements java.lang.Comparable
{
    /** Audit correspondant */
    private AuditDTO mAudit;

    /** Grille qualité */
    private QualityGridDTO mGrid;

    /** Identificateur du projet */
    private ComponentDTO mProject;

    /**
     * @param pGridDTO grille qualité
     */
    public void setGrid( QualityGridDTO pGridDTO )
    {
        mGrid = pGridDTO;
    }

    /**
     * @return grille qualité
     */
    public QualityGridDTO getGrid()
    {
        return mGrid;
    }

    /**
     * @return projet
     */
    public ComponentDTO getProject()
    {
        return mProject;
    }

    /**
     * @param pComponentDTO projet
     */
    public void setProject( ComponentDTO pComponentDTO )
    {
        mProject = pComponentDTO;
    }

    /**
     * @return audit
     */
    public AuditDTO getAudit()
    {
        return mAudit;
    }

    /**
     * @param pAuditDTO audit
     */
    public void setAudit( AuditDTO pAuditDTO )
    {
        mAudit = pAuditDTO;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pAuditGrid l'auditGrid à comparer
     * @return le résultat de la comparaison
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo( Object pAuditGrid )
    {
        int result = 0;
        if ( pAuditGrid instanceof AuditGridDTO )
        {
            AuditGridDTO auditGrid = (AuditGridDTO) pAuditGrid;
            if ( ( auditGrid.getProject() != null ) && ( getProject() != null ) )
            {
                result = getProject().getName().compareTo( auditGrid.getProject().getName() );
            }
        }
        return result;
    }

}
