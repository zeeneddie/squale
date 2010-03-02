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

import org.squale.squalecommon.datatransfertobject.sharedrepository.SharedRepoStatsDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.SharedRepoStatsBO;

/**
 * Transform an stats object from BO to DTO and vice versa
 */
public final class SharedRepoStatsTransform
{

    /**
     * Default constructor set as private
     */
    private SharedRepoStatsTransform()
    {

    }

    /**
     * Convert an {@link SharedRepoStatsBO} to an {@link SharedRepoStatsDTO}
     * 
     * @param statsBo The statsBO to convert
     * @return The StatsDTO
     */
    public static SharedRepoStatsDTO bo2dto( SharedRepoStatsBO statsBo )
    {
        SharedRepoStatsDTO dto = new SharedRepoStatsDTO();
        dto.setStatsId( statsBo.getStatsId() );
        dto.setElementType( statsBo.getElementType() );
        dto.setDataType( statsBo.getDataType() );
        dto.setDataName( statsBo.getDataName() );
        dto.setLanguage( statsBo.getLanguage() );
        dto.setMean( statsBo.getMean() );
        dto.setMax( statsBo.getMax() );
        dto.setMin( statsBo.getMin() );
        dto.setDeviation( statsBo.getDeviation() );
        dto.setElements( statsBo.getElements() );
        dto.setSegmentation( SegmentationTransform.bo2dto( statsBo.getSegmentation() ) );

        return dto;
    }

    /**
     * Convert an {@link SharedRepoStatsDTO} to an {@link SharedRepoStatsBO}
     * 
     * @param statsDto The StatsDTO to convert
     * @return The StatsBO
     */
    public static SharedRepoStatsBO dto2bo( SharedRepoStatsDTO statsDto )
    {
        SharedRepoStatsBO bo = new SharedRepoStatsBO();
        bo.setStatsId( statsDto.getStatsId() );
        bo.setElementType( statsDto.getElementType() );
        bo.setDataType( statsDto.getDataType() );
        bo.setDataName( statsDto.getDataName() );
        bo.setLanguage( statsDto.getLanguage() );
        bo.setMean( statsDto.getMean() );
        bo.setMax( statsDto.getMax() );
        bo.setMin( statsDto.getMin() );
        bo.setDeviation( statsDto.getDeviation() );
        bo.setElements( statsDto.getElements() );
        bo.setSegmentation( SegmentationTransform.dto2bo( statsDto.getSegmentation() ) );
        return bo;
    }

}
