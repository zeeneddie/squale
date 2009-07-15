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

import org.squale.squalecommon.datatransfertobject.component.AuditGridDTO;

/**
 * Comparateur de grilles d'audit
 */
public class AuditGridComparator
    implements Comparator
{

    /**
     * Compare les griles d'audit en utilisant les dates de leur audit.
     * 
     * @param pAudit1 la première application
     * @param pAudit2 la seconde application
     * @return la comparaison des noms des applications.
     */
    public int compare( Object pAudit1, Object pAudit2 )
    {
        int result = 0;
        result =
            ( (AuditGridDTO) pAudit1 ).getAudit().getRealDate().compareTo(
                                                                           ( (AuditGridDTO) pAudit2 ).getAudit().getRealDate() );
        return result;
    }
}
