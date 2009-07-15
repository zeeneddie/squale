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
package org.squale.squalecommon.datatransfertobject.transform.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.squale.squalecommon.datatransfertobject.result.MarkDTO;
import org.squale.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.result.MarkBO;

/**
 * bo <--> dto des notes
 */
public class MarkTransform
{

    /**
     * @param pMarks les notes à transformer
     * @return les notes sous forme dto
     */
    public static Collection bo2Dto( Collection pMarks )
    {
        Collection dtos = new ArrayList();
        for ( Iterator it = pMarks.iterator(); it.hasNext(); )
        {
            dtos.add( bo2Dto( (MarkBO) it.next() ) );
        }
        return dtos;
    }

    /**
     * Le composant associé à la note est transformé avec son nom entier
     * 
     * @param pMark la note
     * @return la note sous forme DTO
     */
    public static MarkDTO bo2Dto( MarkBO pMark )
    {
        MarkDTO markDTO = new MarkDTO();
        markDTO.setComponent( ComponentTransform.bo2DtoWithFullName( pMark.getComponent() ) );
        markDTO.setValue( pMark.getValue() );
        return markDTO;
    }
}
