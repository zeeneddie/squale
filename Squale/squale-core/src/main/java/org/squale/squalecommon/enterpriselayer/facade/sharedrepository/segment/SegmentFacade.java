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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.sharedrepository.segmentref.CategoryRefType;
import org.squale.squalecommon.daolayer.component.AbstractComponentDAOImpl;
import org.squale.squalecommon.daolayer.sharedrepository.segment.SegmentDAOImpl;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentCategoryDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentDTO;
import org.squale.squalecommon.datatransfertobject.transform.sharedrepository.segment.SegmentCategoryTransform;
import org.squale.squalecommon.datatransfertobject.transform.sharedrepository.segment.SegmentTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.sharedrepository.segment.SegmentCategoryBO;

/**
 * Facade for the segment
 */
public final class SegmentFacade
    implements IFacade
{

    /**
     * Private constructor
     */
    private SegmentFacade()
    {

    }

    /**
     * This method save or update the list of segment given in argument
     * 
     * @param session The hibernate session
     * @param segmentList The list of segment to update
     * @throws JrafEnterpriseException exception occurs during the update
     */
    public static void saveOrUpdateForACategory( ISession session, List<SegmentDTO> segmentList )
        throws JrafEnterpriseException
    {
        try
        {
            if ( segmentList.size() > 0 )
            {
                SegmentCategoryDTO categoryDto = segmentList.get( 0 ).getSegmentCategory();
                SegmentCategoryBO categoryBo = SegmentCategoryTransform.dto2bo( categoryDto );
                SegmentDAOImpl dao = SegmentDAOImpl.getInstance();
                for ( SegmentDTO segmentDTO : segmentList )
                {
                    SegmentBO segmentBo = null;
                    if ( segmentDTO.getTechnicalId() != -1L )
                    {
                        segmentBo = (SegmentBO) dao.get( session, segmentDTO.getTechnicalId() );
                        segmentBo.setDeprecated( segmentDTO.isDeprecated() );
                        segmentBo.setSegmentName( segmentDTO.getSegmentKeyName() );
                    }
                    else
                    {
                        segmentBo = SegmentTransform.dto2bo( segmentDTO );
                        segmentBo.setSegmentCategory( categoryBo );
                    }
                    dao.save( session, segmentBo );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "saveOrUpdateForACategory" );
        }
    }

    /**
     * This method retrieve the identifier and the technical id for each segment whose category has for technical the
     * one given in argument
     * 
     * @param session The hibernate session
     * @param technicalId The technical of the category of the segment to retrieve
     * @return A map identifier <=> technicalId
     * @throws JrafEnterpriseException Exception occurs during the retrieve of the informations
     */
    public static HashMap<Long, Long> getSegmentIdentifierTechnicalIdForACategory( ISession session, long technicalId )
        throws JrafEnterpriseException
    {
        HashMap<Long, Long> mapIdentifierTechnicalId = new HashMap<Long, Long>();
        try
        {
            SegmentDAOImpl dao = SegmentDAOImpl.getInstance();
            List<Object[]> identifierTechicalId = dao.retrieveAllSegIdentifierTechnicalId( session, technicalId );
            for ( Object[] objects : identifierTechicalId )
            {
                mapIdentifierTechnicalId.put( (Long) objects[0], (Long) objects[1] );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "getSegmentIdentifierTechnicalIdForACategory" );
        }
        return mapIdentifierTechnicalId;
    }

    /**
     * This method return the list of segment linked to the component (id) given in argument
     * 
     * @param session The hibernate session
     * @param componentId The component
     * @return The list of segment selected
     * @throws JrafEnterpriseException exception occurs
     */
    public static List<Long> getLinkedForComponent( ISession session, String componentId )
        throws JrafEnterpriseException
    {
        List<Long> allLinkedSegment = new ArrayList<Long>();
        try
        {
            AbstractComponentDAOImpl componentDao = AbstractComponentDAOImpl.getInstance();
            AbstractComponentBO component =
                (AbstractComponentBO) componentDao.get( session, Long.parseLong( componentId ) );
            CategoryRefType catType = CategoryRefType.module;
            if ( component instanceof ApplicationBO )
            {
                ApplicationBO app = (ApplicationBO) component;
                List<AbstractComponentBO> childList = (List<AbstractComponentBO>) app.getChildren();
                catType = CategoryRefType.application;
                Iterator<AbstractComponentBO> listIt = childList.iterator();
                boolean isOk = false;
                while ( listIt.hasNext() && !isOk )
                {
                    component = listIt.next();
                    if ( component instanceof ProjectBO )
                    {
                        isOk = true;
                    }
                }
            }
            if ( component instanceof ProjectBO )
            {
                SegmentDAOImpl segDao = SegmentDAOImpl.getInstance();
                allLinkedSegment = segDao.findSegmentByType( session, component.getId(), catType );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "getSelectedForComponent" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SegmentFacade.class.getName() + ".getSelectedForComponent" );
        }
        return allLinkedSegment;
    }

    /**
     * This method add a linked a list of segment to the component (id) given in argument
     * 
     * @param session The hibernate session
     * @param componentId The id of the component linked to the segments
     * @param segmentsToAdd The list of segment of the component
     * @throws JrafEnterpriseException Exception occurs during the save
     */
    public static void addSegmentsToApp( ISession session, String componentId, List<Long> segmentsToAdd )
        throws JrafEnterpriseException
    {
        try
        {
            AbstractComponentDAOImpl componentDao = AbstractComponentDAOImpl.getInstance();
            AbstractComponentBO component =
                (AbstractComponentBO) componentDao.get( session, Long.parseLong( componentId ) );
            ArrayList<ProjectBO> moduleList = new ArrayList<ProjectBO>();
            if ( component instanceof ApplicationBO )
            {
                ApplicationBO app = (ApplicationBO) component;
                List<AbstractComponentBO> childList = (List<AbstractComponentBO>) app.getChildren();
                Iterator<AbstractComponentBO> listIt = childList.iterator();
                while ( listIt.hasNext() )
                {
                    component = listIt.next();
                    if ( component instanceof ProjectBO )
                    {
                        moduleList.add( (ProjectBO) component );
                    }
                }
            }
            else if ( component instanceof ProjectBO )
            {
                moduleList.add( (ProjectBO) component );
            }
            if ( moduleList.size() > 0 )
            {
                SegmentDAOImpl segDao = SegmentDAOImpl.getInstance();
                for ( Long long1 : segmentsToAdd )
                {
                    SegmentBO segment = (SegmentBO) segDao.get( session, long1 );
                    Set<ProjectBO> linkedModule = segment.getModuleList();
                    linkedModule.addAll( moduleList );
                    segDao.save( session, segment );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "addSegmentsToApp" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SegmentFacade.class.getName() + ".addSegmentsToApp" );
        }
    }

    /**
     * This method add a linked a list of segment to the component (id) given in argument
     * 
     * @param session The hibernate session
     * @param componentId The id of the component linked to the segments
     * @param segmentsToAdd The list of segment of the component
     * @throws JrafEnterpriseException Exception occurs during the save
     */
    public static void removeSegmentsFromApp( ISession session, String componentId, List<Long> segmentsToAdd )
        throws JrafEnterpriseException
    {
        try
        {
            AbstractComponentDAOImpl componentDao = AbstractComponentDAOImpl.getInstance();
            AbstractComponentBO component =
                (AbstractComponentBO) componentDao.get( session, Long.parseLong( componentId ) );
            ArrayList<ProjectBO> moduleList = new ArrayList<ProjectBO>();
            if ( component instanceof ApplicationBO )
            {
                ApplicationBO app = (ApplicationBO) component;
                List<AbstractComponentBO> childList = (List<AbstractComponentBO>) app.getChildren();
                Iterator<AbstractComponentBO> listIt = childList.iterator();
                while ( listIt.hasNext() )
                {
                    component = listIt.next();
                    if ( component instanceof ProjectBO )
                    {
                        moduleList.add( (ProjectBO) component );
                    }
                }
            }
            else if ( component instanceof ProjectBO )
            {
                moduleList.add( (ProjectBO) component );
            }
            if ( moduleList.size() > 0 )
            {
                SegmentDAOImpl segDao = SegmentDAOImpl.getInstance();
                for ( Long long1 : segmentsToAdd )
                {
                    SegmentBO segment = (SegmentBO) segDao.get( session, long1 );
                    Set<ProjectBO> linkedModule = segment.getModuleList();
                    linkedModule.removeAll( moduleList );
                    segDao.save( session, segment );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, "removeSegmentsFromApp" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SegmentFacade.class.getName() + ".removeSegmentsFromApp" );
        }
    }

}
