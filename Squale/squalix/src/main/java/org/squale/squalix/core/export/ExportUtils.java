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
package org.squale.squalix.core.export;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.job.JobDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobBO;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobName;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobStatus;
import org.squale.squalecommon.util.SqualeCommonConstants;
import org.squale.squalecommon.util.SqualeCommonUtils;
import org.squale.squalecommon.util.mail.IMailerProvider;
import org.squale.squalecommon.util.mail.MailerHelper;
import org.squale.squalix.messages.MessageMailManager;
import org.squale.squalix.messages.Messages;

/**
 * This class contains some utility method for the export
 */
public final class ExportUtils
{

    /**
     * Private constructor
     */
    private ExportUtils()
    {

    }

    /**
     * This method search if there is one job scheduled
     * 
     * @param session The hibernate session
     * @return true if one job scheduled is found
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    public static boolean isJobScheduled( ISession session )
        throws JrafEnterpriseException
    {
        boolean jobScheduled = false;
        try
        {
            JobBO jobBo = new JobBO( JobName.APPLICATION_EXPORT.getLabel(), JobStatus.SCHEDULED.getLabel() );
            List<JobBO> jobsFound;
            JobDAOImpl jobDao = JobDAOImpl.getInstance();
            jobsFound = jobDao.findByExample( session, jobBo );
            if ( jobsFound.size() > 0 )
            {
                // We set the job at the status : in_progress
                jobBo = jobsFound.get( 0 );
                jobBo.setJobStatus( JobStatus.IN_PROGESS.getLabel() );
                jobDao.save( session, jobBo );
                jobScheduled = true;
            }
        }
        catch ( JrafDaoException e )
        {
            String message = Messages.getString( "export.job.searchScheduled.error" );
            throw new JrafEnterpriseException( message, e );
        }
        return jobScheduled;
    }

    /**
     * This method set the export job to the status successful
     * 
     * @param session The hibernate session
     * @throws JrafEnterpriseException Exception occurs during the change of the status
     */
    public static void setJobAsSuccessful( ISession session )
        throws JrafEnterpriseException
    {
        String mailObject = Messages.getString( "export.mail.successful.object" );
        String mailContent = "export.mail.successful.content";
        setJobAs( session, JobStatus.SUCCESSFUL, mailObject, mailContent );
    }

    /**
     * This method set the export job to the status failed ans send an email
     * 
     * @param session The hibernate session
     * @throws JrafEnterpriseException Exception occurs during the change of the status
     */
    public static void setJobAsFailed( ISession session )
        throws JrafEnterpriseException
    {
        String mailObject = Messages.getString( "export.mail.failed.object" );
        String mailContent = "export.mail.failed.content";
        setJobAs( session, JobStatus.FAILED, mailObject, mailContent );
    }

    /**
     * This method sets the status job to failed and send an email which indicate that there is nothing to export
     * 
     * @param session The hibernate session
     * @throws JrafEnterpriseException Exception occurs
     */
    public static void nothingToExport( ISession session )
        throws JrafEnterpriseException
    {
        String mailObject = Messages.getString( "export.mail.nothingToExport.object" );
        String mailContent = "export.mail.nothingToExport.content";
        setJobAs( session, JobStatus.NOTHING_TO_EXPORT, mailObject, mailContent );
    }

    /**
     * This method sets the current job in its new status, clean the previous job and send a mail to the admin
     * 
     * @param session The hibernate session
     * @param newStatus The new status of the current job
     * @param mailObject The mail object
     * @param mailContent The mail content
     * @throws JrafEnterpriseException Exception occurs
     */
    private static void setJobAs( ISession session, JobStatus newStatus, String mailObject, String mailContent )
        throws JrafEnterpriseException
    {
        try
        {
            List<JobBO> jobsFound = search( session );
            JobDAOImpl jobDao = JobDAOImpl.getInstance();

            for ( JobBO jobBO : jobsFound )
            {
                String jobStatus = jobBO.getJobStatus();
                // If the job as the status in_progress, that means is the one that has just been done. So it's status
                // pass to successful
                if ( JobStatus.IN_PROGESS.same( jobStatus ) )
                {
                    jobBO.setJobStatus( newStatus.getLabel() );
                    jobBO.setJobDate( new Date() );
                    jobDao.save( session, jobBO );
                }
                // We remove the other job
                else if ( ( newStatus.equals( JobStatus.SUCCESSFUL ) && JobStatus.SUCCESSFUL.same( jobStatus ) )
                    || JobStatus.FAILED.same( jobStatus ) || JobStatus.NOTHING_TO_EXPORT.same( jobStatus ) )
                {
                    jobDao.remove( session, jobBO );
                }
            }
            sendMail( mailObject, mailContent, null );
        }
        catch ( JrafDaoException e )
        {
            String message = Messages.getString( "export.job.error.setStatus" );
            throw new JrafEnterpriseException( message, e );
        }
    }

    /**
     * This method search the export job record on the database
     * 
     * @param session the hibernate session
     * @return the list of export job found
     * @throws JrafDaoException Exception occurs during the search in the database
     */
    private static List<JobBO> search( ISession session )
        throws JrafDaoException
    {
        JobBO jobBo = new JobBO( JobName.APPLICATION_EXPORT.getLabel() );
        JobDAOImpl jobDao = JobDAOImpl.getInstance();
        List<JobBO> jobsFound = jobDao.findByExample( session, jobBo );
        return jobsFound;
    }

    /**
     * This method launch an e mail to the admin
     * 
     * @param objectExt The specific object
     * @param contentExt The specific content
     * @param contentValues The values to replace in the content
     */
    private static void sendMail( String objectExt, String contentExt, String[] contentValues )
    {
        IMailerProvider mailer = MailerHelper.getMailerProvider();
        String sender = Messages.getString( "mail.sender.squalix" );
        MessageMailManager mail = new MessageMailManager();
        String object = sender + objectExt;
        mail.addContent( "mail.header", null );
        mail.addContent( contentExt, contentValues );
        String content = mail.getContent();
        SqualeCommonUtils.notifyByEmail( mailer, null, SqualeCommonConstants.ONLY_ADMINS, null, object, content, false );

    }

    /**
     * This method sends a mail to the admin to indicate that some applications will not be exported
     * 
     * @param applicationNotExported the list of name of the not exported application
     */
    public static void applicationNotExported( List<String> applicationNotExported )
    {
        StringBuffer appList = new StringBuffer();
        Iterator<String> it = applicationNotExported.iterator();
        while ( it.hasNext() )
        {
            String application = (String) it.next();
            appList.append( application );
            if ( it.hasNext() )
            {
                appList.append( ", " );
            }
        }
        String message = appList.toString();
        String mailObject = Messages.getString( "export.mail.notExported.object" );
        String mailContent = "export.mail.notExported.content";
        sendMail( mailObject, mailContent, new String[] { message } );
    }

}
