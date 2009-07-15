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
package com.airfrance.squalecommon.datatransfertobject.transform.messages;

import com.airfrance.squalecommon.datatransfertobject.message.MessageDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.message.MessageBO;

/**
 * 
 */
public class MessageTransform
{

    /**
     * Transform le dto en bo
     * 
     * @param pDTO le dto à transformer
     * @return le bo
     */
    public static MessageBO dto2Bo( MessageDTO pDTO )
    {
        MessageBO bo = new MessageBO();
        bo.setText( pDTO.getText() );
        bo.setLang( pDTO.getLang() );
        bo.setTitle( pDTO.getTitle() );
        bo.setKey( pDTO.getKey() );
        return bo;
    }

    /**
     * Transform le bo en dto
     * 
     * @param pBo le bo à transformer
     * @return le dto
     */
    public static MessageDTO bo2Dto( MessageBO pBo )
    {
        MessageDTO dto = new MessageDTO();
        dto.setText( pBo.getText() );
        dto.setLang( pBo.getLang() );
        dto.setTitle( pBo.getTitle() );
        dto.setKey( pBo.getKey() );
        return dto;
    }

}
