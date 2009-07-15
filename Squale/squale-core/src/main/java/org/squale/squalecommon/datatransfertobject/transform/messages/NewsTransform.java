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
package org.squale.squalecommon.datatransfertobject.transform.messages;

import org.squale.squalecommon.datatransfertobject.message.NewsDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.message.NewsBO;

/**
 * 
 */
public class NewsTransform
{

    /**
     * Transform le dto en bo
     * 
     * @param pDTO le dto à transformer
     * @return le bo
     */
    public static NewsBO dto2Bo( NewsDTO pDTO )
    {
        NewsBO bo = new NewsBO();
        bo.setBeginningDate( pDTO.getBeginningDate() );
        bo.setEndDate( pDTO.getEndDate() );
        bo.setKey( pDTO.getKey() );
        bo.setId( pDTO.getId() );
        return bo;
    }

    /**
     * Transform le bo en dto
     * 
     * @param pBo le bo à transformer
     * @return le dto
     */
    public static NewsDTO bo2Dto( NewsBO pBo )
    {
        NewsDTO dto = new NewsDTO();
        dto.setBeginningDate( pBo.getBeginningDate() );
        dto.setEndDate( pBo.getEndDate() );
        dto.setKey( pBo.getKey() );
        dto.setId( pBo.getId() );
        return dto;
    }

}
