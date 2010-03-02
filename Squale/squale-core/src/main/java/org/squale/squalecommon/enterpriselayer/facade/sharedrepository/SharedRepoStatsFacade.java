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
package org.squale.squalecommon.enterpriselayer.facade.sharedrepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.sharedrepository.SegmentationDAOImpl;
import org.squale.squalecommon.daolayer.sharedrepository.SharedRepoStatsDAOImpl;
import org.squale.squalecommon.datatransfertobject.sharedrepository.SharedRepoStatsDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.datatransfertobject.transform.sharedrepository.SharedRepoStatsTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.SegmentationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.SharedRepoStatsBO;

/**
 * This class is the facade linked to the {@link SharedRepoStatsBO}
 */
public final class SharedRepoStatsFacade
    implements IFacade
{
    /**
     * Private constructor
     */
    private SharedRepoStatsFacade()
    {

    }

    /**
     * This method persists in the database the list of statistics given in argument
     * 
     * @param session The hibernate session
     * @param statsListDto The list of statistics to persist in the database
     * @throws JrafEnterpriseException exception occurs the saveAll work
     */
    public static void saveAll( ISession session, List<SharedRepoStatsDTO> statsListDto )
        throws JrafEnterpriseException
    {
        try
        {
            SharedRepoStatsDAOImpl sharedRepoDao = SharedRepoStatsDAOImpl.getInstance();
            SegmentationDAOImpl segmentationDao = SegmentationDAOImpl.getInstance();
            for ( SharedRepoStatsDTO sharedRepoStatsDto : statsListDto )
            {
                SharedRepoStatsBO statsBo = SharedRepoStatsTransform.dto2bo( sharedRepoStatsDto );
                SegmentationBO segment =
                    (SegmentationBO) segmentationDao.get( session, statsBo.getSegmentation().getSegmentationId() );
                segment.getStatsList().add( statsBo );
                statsBo.setSegmentation( segment );
                sharedRepoDao.save( session, statsBo );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "saveAll" );
        }
    }

    /**
     * This methods retrieve the statistics which are linked to the segmentation (tag list) given in argument
     * 
     * @param session The hibernate session
     * @param tagList the list of tags which represent the segmentation
     * @return The list of statistics linked to the segmentation
     * @throws JrafEnterpriseException Exception occurs during the retrieve work
     */
    public static List<SharedRepoStatsDTO> retrieveStats( ISession session, List<TagDTO> tagList )
        throws JrafEnterpriseException
    {
        List<SharedRepoStatsDTO> listStatsDto = new ArrayList<SharedRepoStatsDTO>();
        try
        {
            SegmentationDAOImpl dao = SegmentationDAOImpl.getInstance();
            List<SegmentationBO> segmentList = (List<SegmentationBO>) dao.findAll( session );
            if ( segmentList.size() > 0 )
            {
                SegmentationBO segmentationBo = segmentList.get( 0 );
                Set<SharedRepoStatsBO> statsBoList = segmentationBo.getStatsList();
                for ( SharedRepoStatsBO statsBo : statsBoList )
                {
                    listStatsDto.add( SharedRepoStatsTransform.bo2dto( statsBo ) );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "create" );
        }
        return listStatsDto;
    }

}
