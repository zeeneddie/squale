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
package org.squale.squalecommon.datatransfertobject.transform.access;

import java.util.ArrayList;
import java.util.List;

import org.squale.squalecommon.datatransfertobject.access.UserAccessDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.access.UserAccessBO;

/**
 * Transformeur pour les accès utilisateur
 */
public class UserAccessTransform
{

    /**
     * @param pAccessDTOs les accès sous forme de DTO
     * @return les accès sous forme BO
     */
    public static List dto2bo( List pAccessDTOs )
    {
        int dtoSize = pAccessDTOs.size();
        List stackBO = new ArrayList();
        for ( int i = 0; i < dtoSize; i++ )
        {
            stackBO.add( dto2bo( (UserAccessDTO) pAccessDTOs.get( i ) ) );
        }
        return stackBO;
    }

    /**
     * @param pAccessBOs les accès sous forme de BO
     * @return les accès sous forme DTO
     */
    public static List bo2dto( List pAccessBOs )
    {
        int boSize = pAccessBOs.size();
        List listDTO = new ArrayList( boSize );
        for ( int i = 0; i < boSize; i++ )
        {
            listDTO.add( bo2dto( (UserAccessBO) pAccessBOs.get( i ) ) );
        }
        return listDTO;
    }

    /**
     * @param pAccessDTO l'accès sous forme DTO
     * @return l'accès sous forme BO
     */
    public static UserAccessBO dto2bo( UserAccessDTO pAccessDTO )
    {
        UserAccessBO accessBO = new UserAccessBO();
        accessBO.setDate( pAccessDTO.getDate() );
        accessBO.setMatricule( pAccessDTO.getMatricule() );
        accessBO.setId( pAccessDTO.getId() );
        return accessBO;
    }

    /**
     * @param pAccessBO l'accès sous forme BO
     * @return l'accès sous forme DTO
     */
    public static UserAccessDTO bo2dto( UserAccessBO pAccessBO )
    {
        UserAccessDTO accessDTO = new UserAccessDTO();
        accessDTO.setDate( pAccessBO.getDate() );
        accessDTO.setMatricule( pAccessBO.getMatricule() );
        accessDTO.setId( pAccessBO.getId() );
        return accessDTO;
    }

}
