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
package org.squale.squaleweb.comparator;

import java.util.Comparator;

import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squaleweb.applicationlayer.formbean.component.AuditForm;

/**
 * Compare les audits par rapport à leurs dates.
 * 
 * @author M400842
 */
public class AuditComparator
    implements Comparator
{

    /**
     * Compare les audits en utilisant leurs dates.
     * 
     * @param pAudit1 la première application
     * @param pAudit2 la seconde application
     * @return la comparaison des dates réelles des audits.
     */
    public int compare( Object pAudit1, Object pAudit2 )
    {
        int result = 1;
        // Vérification du type des objets
        if ( pAudit1 instanceof AuditForm )
        {
            // Si les éléments sont comparables
            if ( ( (AuditForm) pAudit1 ).getRealDate() != null )
            {
                if ( ( (AuditForm) pAudit2 ).getRealDate() != null )
                {
                    // on compare selon la date réelle de l'audit
                    // en prenant compte de la date historique
                    result = ( (AuditForm) pAudit1 ).getRealDate().compareTo( ( (AuditForm) pAudit2 ).getRealDate() );
                }
                else
                {
                    result = -1;
                }
            }
        }
        else
        {
            // On compare à l'identique dans le cas d'un DTO
            if ( ( (AuditDTO) pAudit1 ).getRealDate() != null )
            {
                if ( ( (AuditDTO) pAudit2 ).getRealDate() != null )
                {
                    // Comparaison selon la date réelle de l'audit
                    result = ( (AuditDTO) pAudit1 ).getRealDate().compareTo( ( (AuditDTO) pAudit2 ).getRealDate() );
                }
                else
                {
                    result = -1;
                }
            }
        }
        return result;
    }
}
