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
package org.squale.squalecommon.datatransfertobject.transform.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.squale.squalecommon.datatransfertobject.config.SqualeParamsDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SqualeParamsBO;

/**
 * Transform an squaleParams object from BO to DTO and vice versa
 */
public final class SqualeParamsTransform
{

    /**
     * Default constructor set as private
     */
    private SqualeParamsTransform()
    {

    }

    /**
     * Convert an adminParamsBO to an adminParamsDTO
     * 
     * @param squaleParam The SqualeParamsBO to convert
     * @return The AdminParamsDTO
     */
    public static SqualeParamsDTO bo2dto( SqualeParamsBO squaleParam )
    {
        SqualeParamsDTO dto = new SqualeParamsDTO();
        dto.setId( squaleParam.getId() );
        dto.setParamKey( squaleParam.getParamKey() );
        dto.setParamValue( squaleParam.getParamValue() );
        return dto;
    }

    /**
     * Convert a collection of SqualeParamsBO to a collection of SqualeParamsDTO
     * 
     * @param squaleParams The collection of SqualeParamsBO to convert
     * @return The collection of SqualeParamsDTO
     */
    public static List<SqualeParamsDTO> bo2dto( Collection<SqualeParamsBO> squaleParams )
    {
        List<SqualeParamsDTO> collectionsDTO = new ArrayList<SqualeParamsDTO>();
        SqualeParamsDTO dto;
        SqualeParamsBO bo;
        Iterator<SqualeParamsBO> it = squaleParams.iterator();
        while ( it.hasNext() )
        {
            bo = it.next();
            dto = bo2dto( bo );
            collectionsDTO.add( dto );
        }
        return collectionsDTO;
    }

    /**
     * Convert an SqualeParamsDTO to an SqualeParamsBO
     * 
     * @param squaleParam The SqualeParamsDTO to convert
     * @return The SqualeParamsBO
     */
    public static SqualeParamsBO dto2bo( SqualeParamsDTO squaleParam )
    {
        SqualeParamsBO bo = new SqualeParamsBO();
        bo.setId( squaleParam.getId() );
        bo.setParamKey( squaleParam.getParamKey() );
        bo.setParamValue( squaleParam.getParamValue() );
        return bo;
    }

}
