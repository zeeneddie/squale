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
package org.squale.squalecommon.datatransfertobject.transform.sharedrepository.segment;

import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentCategoryDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentCategoryBO;

/**
 * This class transform SegmentCategoryBO <=> SegmentCategoryDTO
 */
public final class SegmentCategoryTransform
{

    /**
     * Private constructor
     */
    private SegmentCategoryTransform()
    {

    }

    /**
     * Transform a SegmentcatgeoryBO into a SegmentCategoryDTO
     * 
     * @param bo The SegmentCategoryBO to transform
     * @return A SegmentCategoryDTO
     */
    public static SegmentCategoryDTO bo2dto( SegmentCategoryBO bo )
    {
        SegmentCategoryDTO dto = new SegmentCategoryDTO();
        dto.setTechnicalId( bo.getTechnicalId() );
        dto.setIdentifier( bo.getIdentifier() );
        dto.setCategoryName( bo.getCategoryName() );
        dto.setDeprecated( bo.isDeprecated() );
        dto.setType( bo.getType() );
        return dto;
    }

    /**
     * Transform a SegmentCategroryDTO into a SegmentCategoryBO
     * 
     * @param dto The SegmentCatgeoryDTO to transform
     * @return A SegmentCategoryBO
     */
    public static SegmentCategoryBO dto2bo( SegmentCategoryDTO dto )
    {
        SegmentCategoryBO bo = new SegmentCategoryBO();
        bo.setTechnicalId( dto.getTechnicalId() );
        bo.setIdentifier( dto.getIdentifier() );
        bo.setCategoryName( dto.getCategoryKeyName() );
        bo.setDeprecated( dto.isDeprecated() );
        bo.setType( dto.getType() );
        return bo;
    }

}
