package com.airfrance.squaleweb.applicationlayer.action.results.audit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.component.AuditForm;

/**
 * factorisation du code util dans les actions AuditAction et ManagerAuditAction
 */
public class AuditUtils
{

    /**
     * Récupère la sélection des audits à partir du paramètre et de la liste fournie.
     * 
     * @param pList la liste des audits (AuditForm).
     * @return la liste des audits sélectionnés.
     */
    public List getSelection( final List pList )
    {
        ArrayList auditsSelected = new ArrayList();
        // Il s'agit d'une sélection par check boxes
        if ( pList != null )
        {
            // Au moins un coché
            Iterator it = pList.iterator();
            AuditForm auditForm;
            // Parcours de la liste des audits
            while ( it.hasNext() )
            {
                auditForm = (AuditForm) it.next();
                // Ajout des audits sélectionnés
                if ( auditForm.isSelected() )
                {
                    auditsSelected.add( auditForm );
                }
            }
        }
        return auditsSelected;
    }
}
