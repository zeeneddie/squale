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

import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;

/**
 * Transform an adminParams object from BO to DTO and vice versa
 */
public final class AdminParamsTransform
{

    /**
     * Default constructor set as private
     */
    private AdminParamsTransform()
    {

    }

    /**
     * Convert an adminParamsBO to an adminParamsDTO
     * 
     * @param adminParam The adminParams as BO to convert
     * @return The adminParams as DTO
     */
    public static AdminParamsDTO bo2dto( AdminParamsBO adminParam )
    {
        AdminParamsDTO dto = new AdminParamsDTO();
        dto.setParamKey( adminParam.getParamKey() );
        dto.setParamValue( adminParam.getParamValue() );
        return dto;
    }

    /**
     * Convert a collection of adminParamsBO to a collection of adminParamsDTO
     * 
     * @param adminParams The collection of adminParamsBO to convert
     * @return The collection of adminParamsDTO
     */
    public static List<AdminParamsDTO> bo2dto( Collection<AdminParamsBO> adminParams )
    {
        List<AdminParamsDTO> collectionsDTO = new ArrayList<AdminParamsDTO>();
        AdminParamsDTO dto;
        AdminParamsBO bo;
        Iterator<AdminParamsBO> it = adminParams.iterator();
        while ( it.hasNext() )
        {
            bo = it.next();
            dto = bo2dto( bo );
            collectionsDTO.add( dto );
        }
        return collectionsDTO;
    }

    /**
     * Convert an adminparamsDTO to an adminParamsBO
     * 
     * @param adminParam The adminParams as DTO to convert
     * @return the adminParams as BO
     */
    public static AdminParamsBO dto2bo( AdminParamsDTO adminParam )
    {
        AdminParamsBO bo = new AdminParamsBO();
        bo.setParamKey( adminParam.getParamKey() );
        bo.setParamValue( adminParam.getParamValue() );
        return bo;
    }

}
