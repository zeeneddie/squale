package com.airfrance.squalecommon.datatransfertobject.component;

import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;

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
