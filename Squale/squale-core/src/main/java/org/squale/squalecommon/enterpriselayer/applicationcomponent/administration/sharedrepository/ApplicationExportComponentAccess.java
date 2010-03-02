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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.job.JobDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.ApplicationExportDTO;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.ACMessages;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobName;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobStatus;
import org.squale.squalecommon.enterpriselayer.facade.job.JobFacade;
import org.squale.squalecommon.enterpriselayer.facade.sharedrepository.ApplicationExportFacade;

/**
 * Access class for the export (Shared repository)
 */
public class ApplicationExportComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * UID
     */
    private static final long serialVersionUID = 1793166358209845223L;

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( ApplicationExportComponentAccess.class );

    /**
     * Persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * This method recover for an application the {@link ApplicationExportDTO} linked to this application
     * 
     * @param appId The id of the application
     * @return The {@link ApplicationExportDTO} linked to the application
     * @throws JrafEnterpriseException Exception occurs during the search of the applicationExportDTO
     */
    public ApplicationExportDTO getExportApplication( String appId )
        throws JrafEnterpriseException
    {
        ISession session;
        ApplicationExportDTO appliDto = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            appliDto = ApplicationExportFacade.getApplicationExport( session, Long.valueOf( appId ) );
        }
        catch ( JrafPersistenceException e )
        {
            LOG.error( e.getMessage() );
            throw new JrafEnterpriseException( e );
        }
        return appliDto;
    }

    /**
     * This method recover the last export job
     * 
     * @return The last export job
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    public List<JobDTO> getLastJobs()
        throws JrafEnterpriseException
    {
        ISession session;
        List<JobDTO> listJobDto = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            listJobDto = JobFacade.getLastExportJob( session );
        }
        catch ( JrafPersistenceException e )
        {
            LOG.error( e.getMessage() );
            throw new JrafEnterpriseException( e );
        }
        return listJobDto;
    }

    /**
     * In this method, each application selected by the user is mark as to export
     * 
     * @param listDto The application list
     * @throws JrafEnterpriseException exception occurs during the treatment
     */
    public void applicationToExport( List<ApplicationExportDTO> listDto )
        throws JrafEnterpriseException
    {
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            ApplicationExportFacade.saveOrUpdateList( session, listDto );
        }
        catch ( JrafPersistenceException e )
        {
            LOG.error( e.getMessage() );
            throw new JrafEnterpriseException( e );
        }
    }

    /**
     * This method record a new scheduled job
     * 
     * @throws JrafEnterpriseException Exception occurs during the record
     */
    public void scheduledJob()
        throws JrafEnterpriseException
    {
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            JobDTO dto = new JobDTO( JobName.APPLICATION_EXPORT.getLabel(), JobStatus.SCHEDULED.getLabel() );
            JobFacade.saveJob( session, dto );
        }
        catch ( JrafPersistenceException e )
        {
            LOG.error( e.getMessage() );
            throw new JrafEnterpriseException( e );
        }
    }

    /**
     * This method remove the scheduled job
     * 
     * @throws JrafEnterpriseException Exception occurs during the record
     */
    public void cancelJob()
        throws JrafEnterpriseException
    {
        ISession session;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            JobFacade.cancelJob( session );
        }
        catch ( JrafPersistenceException e )
        {
            LOG.error( e.getMessage() );
            throw new JrafEnterpriseException( e );
        }
    }

    /**
     * This method determine if an export is in progress
     * 
     * @return true if an export is in progress
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    public boolean isInProgress()
        throws JrafEnterpriseException
    {
        ISession session;
        boolean inProgress = true;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            inProgress = JobFacade.isInProgress( session );
        }
        catch ( JrafPersistenceException e )
        {
            String message = ACMessages.getString( "ac.exception.generic.retrieveHibernateSession" );
            LOG.error( message, e );
            throw new JrafEnterpriseException( message, e );
        }
        return inProgress;
    }
}
