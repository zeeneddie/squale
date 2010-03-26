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
package org.squale.squalecommon.enterpriselayer.facade.sharedrepository.segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.sharedrepository.segmentref.CategoryRefType;
import org.squale.squalecommon.daolayer.sharedrepository.segment.SegmentCategoryDAOImpl;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentCategoryDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentDTO;
import org.squale.squalecommon.datatransfertobject.transform.sharedrepository.segment.SegmentCategoryTransform;
import org.squale.squalecommon.datatransfertobject.transform.sharedrepository.segment.SegmentTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentCategoryBO;

/**
 * This class is a facade for the segment category
 */
public final class SegmentCategoryFacade
    implements IFacade
{

    /**
     * private constructor
     */
    private SegmentCategoryFacade()
    {

    }

    /**
     * This method save or update the category given in argument
     * 
     * @param session the hibernate session
     * @param category The category to save
     * @throws JrafEnterpriseException Exception occurs during the save operation
     */
    public static void saveOrUpdate( ISession session, SegmentCategoryDTO category )
        throws JrafEnterpriseException
    {
        try
        {
            SegmentCategoryBO bo = SegmentCategoryTransform.dto2bo( category );
            SegmentCategoryDAOImpl dao = SegmentCategoryDAOImpl.getInstance();
            dao.save( session, bo );
            category.setTechnicalId( bo.getTechnicalId() );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "saveOrUpdate" );
        }
    }

    /**
     * This method return a map. Each entry of the map represents one segment category. The key is a Long which
     * represents the identifier of the category and the object is a Long which represents the technical id of the
     * category
     * 
     * @param session The hibernate session
     * @return a map identifier <=> technicalId
     * @throws JrafEnterpriseException Exception occurs during the retrieve of the informations
     */
    public static HashMap<Long, Long> getAllIdentifier( ISession session )
        throws JrafEnterpriseException
    {
        HashMap<Long, Long> mapIdentifierTechnicalId = new HashMap<Long, Long>();
        try
        {
            SegmentCategoryDAOImpl dao = SegmentCategoryDAOImpl.getInstance();
            List<Object[]> identifierTechicalId = dao.retrieveAllCatIdentifierTechnicalId( session );
            for ( Object[] objects : identifierTechicalId )
            {
                mapIdentifierTechnicalId.put( (Long) objects[0], (Long) objects[1] );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "saveOrUpdate" );
        }
        return mapIdentifierTechnicalId;
    }

    /**
     * This method returns a list of segment category available for the type given in argument
     * 
     * @param session The hibernate session
     * @param type The type of the category
     * @return a map identifier <=> technicalId
     * @throws JrafEnterpriseException Exception occurs during the retrieve of the informations
     */
    public static List<SegmentCategoryDTO> getAllByType( ISession session, CategoryRefType type )
        throws JrafEnterpriseException
    {
        List<SegmentCategoryDTO> categoryList = new ArrayList<SegmentCategoryDTO>();
        try
        {
            SegmentCategoryDAOImpl dao = SegmentCategoryDAOImpl.getInstance();
            List<SegmentCategoryBO> catList = dao.getByType( session, type );
            for ( SegmentCategoryBO segmentCategoryBO : catList )
            {
                SegmentCategoryDTO catDTO = SegmentCategoryTransform.bo2dto( segmentCategoryBO );
                List<SegmentDTO> segList = new ArrayList<SegmentDTO>();
                for ( SegmentBO segment : segmentCategoryBO.getSegmentList() )
                {
                    segList.add( SegmentTransform.bo2dto( segment ) );
                }
                Collections.sort( segList );
                catDTO.setSegment( segList );
                categoryList.add( catDTO );
            }
            Collections.sort( categoryList );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "getAllByType" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SegmentCategoryFacade.class.getName() + ".getAllByType" );
        }
        return categoryList;
    }

}
