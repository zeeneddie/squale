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
