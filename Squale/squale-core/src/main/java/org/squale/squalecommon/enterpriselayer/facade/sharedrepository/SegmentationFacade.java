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

import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.sharedrepository.SegmentationDAOImpl;
import org.squale.squalecommon.daolayer.sharedrepository.segment.SegmentDAOImpl;
import org.squale.squalecommon.datatransfertobject.sharedrepository.SegmentationDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentDTO;
import org.squale.squalecommon.datatransfertobject.transform.sharedrepository.SegmentationTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.SegmentationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;

/**
 * Facade for the component {@link SegmentationBO}
 */
public final class SegmentationFacade
    implements IFacade
{

    /**
     * Private constructor
     */
    private SegmentationFacade()
    {

    }

    /**
     * This method remove all the segmentation
     * 
     * @param session The hibernate session
     * @throws JrafEnterpriseException Exception occurs during the remove work
     */
    public static void removeAll( ISession session )
        throws JrafEnterpriseException
    {
        try
        {
            SegmentationDAOImpl dao = SegmentationDAOImpl.getInstance();
            dao.removeAll( session );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "removeAll" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SegmentationFacade.class.getName() + ".removeAll" );
        }
    }

    /**
     * This method persist ine the database the segmentation given in argument
     * 
     * @param session The hibernate session
     * @param dto the segmentation to create
     * @throws JrafEnterpriseException Exception occurs during the creation work
     */
    public static void create( ISession session, SegmentationDTO dto )
        throws JrafEnterpriseException
    {
        try
        {
            SegmentationBO segmentationBo = SegmentationTransform.dto2bo( dto );
            SegmentationDAOImpl segmentationDao = SegmentationDAOImpl.getInstance();
            SegmentDAOImpl segmentDao = SegmentDAOImpl.getInstance();
            for ( SegmentDTO segmentdto : dto.getSegmentList() )
            {
                SegmentBO example = new SegmentBO();
                example.setIdentifier( segmentdto.getIdentifier() );
                List<SegmentBO> segmentList = segmentDao.findByExample( session, example );
                if(segmentList.size()>0)
                {
                    SegmentBO segmentBo = segmentList.get( 0 );
                    segmentationBo.addSegment( segmentBo );
                }
            }
            segmentationDao.create( session, segmentationBo );
            dto.setSegmentationId( segmentationBo.getSegmentationId() );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "create" );
        }
    }

}
