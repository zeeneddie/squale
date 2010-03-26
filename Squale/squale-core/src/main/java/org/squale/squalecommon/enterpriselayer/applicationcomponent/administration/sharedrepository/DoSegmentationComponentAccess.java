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
package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration.sharedrepository;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.sharedrepository.segmentref.CategoryRefType;
import org.squale.squalecommon.datatransfertobject.component.ApplicationLightDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentCategoryDTO;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import org.squale.squalecommon.enterpriselayer.facade.component.ApplicationFacade;
import org.squale.squalecommon.enterpriselayer.facade.sharedrepository.segment.SegmentCategoryFacade;
import org.squale.squalecommon.enterpriselayer.facade.sharedrepository.segment.SegmentFacade;

/**
 * Component access for the link segment module
 */
public class DoSegmentationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * UID
     */
    private static final long serialVersionUID = 3348240432256448294L;

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( ReferenceImportComponentAccess.class );

    /**
     * Persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * This method retrieves all the application available for the shared repository and the module linked to these
     * applications
     * 
     * @return the list of application
     * @throws JrafEnterpriseException Exception occurs during the retrieve
     */
    public List<ApplicationLightDTO> retrieveAppAndModule()
        throws JrafEnterpriseException
    {
        ISession session = null;
        List<ApplicationLightDTO> listAppToReturn = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            listAppToReturn = ApplicationFacade.availableForSharedRepository( session );
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return listAppToReturn;
    }

    /**
     * This method retrieve the segment linked to the component (id) given in argument
     * 
     * @param componentId the component
     * @return The list of segment linked to the component
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    public List<Long> retrieveLinkedSegments( String componentId )
        throws JrafEnterpriseException
    {
        ISession session;
        List<Long> selectedSegments = new ArrayList<Long>();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            selectedSegments = SegmentFacade.getLinkedForComponent( session, componentId );
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return selectedSegments;
    }

    /**
     * This method retrieve for the
     * 
     * @param type Indicate which cartegory type to search. If this param is equal to true then this method should
     *            search category of type application then it search category of type module
     * @return The list of category linked to the specified category type
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    public List<SegmentCategoryDTO> allCategory( CategoryRefType type )
        throws JrafEnterpriseException
    {
        List<SegmentCategoryDTO> allCategory = new ArrayList<SegmentCategoryDTO>();
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            allCategory = SegmentCategoryFacade.getAllByType( session, type );
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return allCategory;
    }

    /**
     * This method links a list of segment to a component
     * 
     * @param componentId The technical id of the component
     * @param segmentsToAdd Segment to add the component
     * @throws JrafEnterpriseException Exception occurs during the execution
     */
    private void addSegments( String componentId, List<Long> segmentsToAdd )
        throws JrafEnterpriseException
    {
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            SegmentFacade.addSegmentsToApp( session, componentId, segmentsToAdd );

        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }
    }

    /**
     * This method breaks the link between a component and a list of segment
     * 
     * @param componentId The technical id of the component
     * @param segmentsToRemove The list of segment to remove
     * @throws JrafEnterpriseException Exception occurs during the execution
     */
    private void removeSegments( String componentId, List<Long> segmentsToRemove )
        throws JrafEnterpriseException
    {
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            SegmentFacade.removeSegmentsFromApp( session, componentId, segmentsToRemove );

        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }
    }

    /**
     * This method updates the list of segment linked to the application given in argument
     * 
     * @param componentId The technical id of the component
     * @param segmentsToAdd The list of segments to add
     * @param segmentsToRemove The list of segment to remove
     * @throws JrafEnterpriseException exception occurs during the treatment
     */
    public void updateSegments( String componentId, List<Long> segmentsToAdd, List<Long> segmentsToRemove )
        throws JrafEnterpriseException
    {
        addSegments( componentId, segmentsToAdd );
        removeSegments( componentId, segmentsToRemove );
    }
}
