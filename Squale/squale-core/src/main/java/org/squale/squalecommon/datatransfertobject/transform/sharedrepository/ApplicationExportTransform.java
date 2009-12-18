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
package org.squale.squalecommon.datatransfertobject.transform.sharedrepository;

import org.squale.squalecommon.datatransfertobject.sharedrepository.ApplicationExportDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.ApplicationExportBO;

/**
 * This class transform ApplicationExportBO <=> ApplicationExportDTO
 */
public final class ApplicationExportTransform
{

    /**
     * private constructor
     */
    private ApplicationExportTransform()
    {

    }

    /**
     * Transform an {@link ApplicationExportDTO} into an {@link ApplicationExportBO }
     * 
     * @param dto The dto to transform
     * @return The bo
     */
    public static ApplicationExportBO dto2bo( ApplicationExportDTO dto )
    {
        ApplicationExportBO bo = new ApplicationExportBO();
        bo.setId( dto.getId() );
        ApplicationBO appBo = new ApplicationBO();
        appBo.setId( dto.getApplicationId() );
        bo.setApplication( appBo );
        bo.setLastExportDate( dto.getLastExportDate() );
        bo.setToExport( dto.getToExport() );
        return bo;
    }

    /**
     * Transform an {@link ApplicationExportBO} into an {@link ApplicationExportDTO }
     * 
     * @param bo The bo to transform
     * @return The dto
     */
    public static ApplicationExportDTO bo2dto( ApplicationExportBO bo )
    {
        ApplicationExportDTO dto = new ApplicationExportDTO();
        dto.setId( bo.getId() );
        dto.setApplicationId( bo.getApplication().getId() );
        dto.setApplicationName( bo.getApplication().getName() );
        dto.setLastExportDate( bo.getLastExportDate() );
        dto.setToExport( bo.getToExport() );
        return dto;
    }

}
