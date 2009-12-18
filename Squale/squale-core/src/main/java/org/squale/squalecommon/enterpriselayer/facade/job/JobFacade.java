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
package org.squale.squalecommon.enterpriselayer.facade.job;

import java.util.ArrayList;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.job.JobDAOImpl;
import org.squale.squalecommon.datatransfertobject.job.JobDTO;
import org.squale.squalecommon.datatransfertobject.transform.job.JobTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobBO;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobName;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobStatus;
import org.squale.squalecommon.enterpriselayer.facade.config.SqualixConfigFacade;

/**
 * Facade for JobBO
 */
public final class JobFacade
    implements IFacade
{

    /**
     * Private constructor
     */
    private JobFacade()
    {

    }

    /**
     * This method recover the last export job ({@link JobDTO})
     * 
     * @param session The hibernate session
     * @return The last export job
     * @throws JrafEnterpriseException Exception occurs during the searcg of the last export job ({@link JobDTO}
     */
    public static List<JobDTO> getLastExportJob( ISession session )
        throws JrafEnterpriseException
    {
        ArrayList<JobDTO> listJobDto = new ArrayList<JobDTO>();
        try
        {
            JobDAOImpl dao = JobDAOImpl.getInstance();
            List<JobBO> listAppBo = (List<JobBO>) dao.getJobsByName( session, JobName.APPLICATION_EXPORT.getLabel() );
            JobDTO jobDto = null;
            if ( listAppBo.size() > 0 )
            {
                for ( JobBO jobBO : listAppBo )
                {
                    jobDto = JobTransform.bo2dto( jobBO );
                    listJobDto.add( jobDto );
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".getLastExportJob" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".getLastExportJob" );
        }
        return listJobDto;
    }

    /**
     * This method recover the last export job ({@link JobDTO})
     * 
     * @param session The hibernate session
     * @param dto The job to save
     * @throws JrafEnterpriseException Exception occurs during the searcg of the last export job ({@link JobDTO}
     */
    public static void saveJob( ISession session, JobDTO dto )
        throws JrafEnterpriseException
    {
        try
        {
            JobBO jobToSave = JobTransform.dto2bo( dto );
            JobDAOImpl dao = JobDAOImpl.getInstance();
            dao.save( session, jobToSave );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".getLastExportJob" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".getLastExportJob" );
        }
    }

    /**
     * This method remove the scheduled export job
     * 
     * @param session The hibernate session
     * @throws JrafEnterpriseException Exception occurs during the deletion of the schedulled job
     */
    public static void cancelJob( ISession session )
        throws JrafEnterpriseException
    {
        try
        {
            JobBO jobBo = new JobBO( JobName.APPLICATION_EXPORT.getLabel(), JobStatus.SCHEDULED.getLabel() );
            JobDAOImpl jobDao = JobDAOImpl.getInstance();
            List<JobBO> jobsFound = jobDao.findByExample( session, jobBo );
            JobBO jobToDelete = jobsFound.get( 0 );
            JobDAOImpl dao = JobDAOImpl.getInstance();
            dao.remove( session, jobToDelete );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".getLastExportJob" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".getLastExportJob" );
        }
    }
}
