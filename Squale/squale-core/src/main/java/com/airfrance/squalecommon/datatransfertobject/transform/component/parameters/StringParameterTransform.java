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
package com.airfrance.squalecommon.datatransfertobject.transform.component.parameters;

import com.airfrance.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 */
public class StringParameterTransform
{

    /**
     * @param pStringParameterDTO le DTO à transformer en BO
     * @return le BO
     */
    public static StringParameterBO dto2Bo( StringParameterDTO pStringParameterDTO )
    {
        StringParameterBO result = new StringParameterBO();
        result.setId( pStringParameterDTO.getId() );
        result.setValue( pStringParameterDTO.getValue() );
        return result;
    }

    /**
     * @param pStringParameterBO le BO à transformer en DTO
     * @return le DTO
     */
    public static StringParameterDTO bo2Dto( StringParameterBO pStringParameterBO )
    {
        StringParameterDTO result = new StringParameterDTO();
        result.setId( pStringParameterBO.getId() );
        result.setValue( pStringParameterBO.getValue() );
        return result;

    }

}