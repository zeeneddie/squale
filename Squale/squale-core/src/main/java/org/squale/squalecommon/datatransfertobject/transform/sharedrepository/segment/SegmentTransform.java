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

import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;

/**
 * Transformer {@link SegmentBO} <=> {@link SegmentDTO}
 */
public final class SegmentTransform
{

    /**
     * Private constructor
     */
    private SegmentTransform()
    {

    }

    /**
     * Transform a segmentBO into a segment DTO
     * 
     * @param bo The segment bo tranform
     * @return a segmentDTO
     */
    public static SegmentDTO bo2dto( SegmentBO bo )
    {
        SegmentDTO dto = new SegmentDTO();
        dto.setTechnicalId( bo.getTechnicalId() );
        dto.setIdentifier( bo.getIdentifier() );
        dto.setSegmentName( bo.getSegmentName() );
        dto.setDeprecated( bo.isDeprecated() );
        dto.setSegmentCategory( SegmentCategoryTransform.bo2dto( bo.getSegmentCategory() ) );
        return dto;
    }

    /**
     * Transform a segmentDTO into a segmentBO
     * 
     * @param dto The segment DTO to transform
     * @return a segmentBO
     */
    public static SegmentBO dto2bo( SegmentDTO dto )
    {
        SegmentBO bo = new SegmentBO();
        bo.setTechnicalId( dto.getTechnicalId() );
        bo.setIdentifier( dto.getIdentifier() );
        bo.setDeprecated( dto.isDeprecated() );
        bo.setSegmentName( dto.getSegmentKeyName() );
        bo.setSegmentCategory( SegmentCategoryTransform.dto2bo( dto.getSegmentCategory() ) );
        return bo;
    }

}
