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
package com.airfrance.squalix.core.quartz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;

import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * This class instantiate a quartz scheduler. It will schedule Squalix jobs
 */
public class QuartzSqualixScheduler
{

    /**
     * Logger
     */
    private static Log mLOGGER;

    /**
     * The quartz cron-expression (fill in at the launch of Squalix)
     */
    private String mCron = "";

    /**
     * Technical Id of the Squalix Server
     */
    private String site;

    /** Quartz cronTrigger configuration */
    private QuartzTimerConfig configuration;

    /**
     * Default constructor
     */
    public QuartzSqualixScheduler()
    {
    }

    /**
     * Constructor with two arguments : one for the id of the server, the other for cron String
     * 
     * @param pSite String which represent the id of the Squalix server
     * @param pCron String which represent the cron expression for quartz
     */
    public QuartzSqualixScheduler( String pSite, String pCron )
    {
        site = pSite;
        mCron = pCron;
    }

    /**
     * This method create the quartz scheduler for schedule the squalix job
     * 
     * @param sf The quartz scheduler factory (The standard one, based on the file quartz.properties
     * @throws Exception exception happened
     */
    public void scheduleSqualix( SchedulerFactory sf )
        throws Exception
    {

        mLOGGER = LogFactory.getLog( QuartzSqualixScheduler.class );
        mLOGGER.debug( QuartzMessages.getString( "quartz.initializing.begin" ) );
        Scheduler sched = sf.getScheduler();

        // Groups reset
        String[] groups = sched.getTriggerGroupNames();
        for ( int i = 0; i < groups.length; i++ )
        {
            String[] names = sched.getTriggerNames( groups[i] );
            for ( int j = 0; j < names.length; j++ )
            {
                sched.unscheduleJob( names[j], groups[i] );
            }
        }

        // Job reset
        groups = sched.getJobGroupNames();
        for ( int i = 0; i < groups.length; i++ )
        {
            String[] names = sched.getJobNames( groups[i] );
            for ( int j = 0; j < names.length; j++ )
            {
                sched.deleteJob( names[j], groups[i] );
            }
        }

        mLOGGER.debug( QuartzMessages.getString( "quartz.initializing.end" ) );
        mLOGGER.debug( QuartzMessages.getString( "quartz.scheduling.begin" ) );

        // Creation of the squalix job
        JobDetail job =
            new JobDetail( "jobSqualix", "groupSqualix", com.airfrance.squalix.core.quartz.SqualixJob.class );
        job.getJobDataMap().put( SqualixJob.SITE_KEY, site );

        // Creation of the trigger
        CronTrigger cTrigger = createTrigger();
        cTrigger.setMisfireInstruction( CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING );

        // Scheduling the job
        Date ft = sched.scheduleJob( job, cTrigger );
        Object[] argument = { job.getFullName(), ft, cTrigger.getCronExpression() };
        mLOGGER.info( QuartzMessages.getString( "quartz.schedule", argument ) );

        // Launch of the quartz scheduler
        sched.start();
        mLOGGER.debug( QuartzMessages.getString( "quartz.scheduler.start" ) );
        mLOGGER.debug( QuartzMessages.getString( "quartz.waiting" ) );

    }

    /**
     * This method do the configuration of quartz.
     * 
     * @throws ConfigurationException Exception happen if the xml configuration file is not found.
     */
    private void config()
        throws ConfigurationException
    {
        configuration = new QuartzTimerConfig();
        try
        {
            configuration.parse( new FileInputStream( "config/quartz-timer-config.xml" ) );
            setCron( configuration.getQuartzKey() );
        }
        catch ( FileNotFoundException e )
        {
            String message = QuartzMessages.getString( "quartz.exception.configuration.xml_file_not_found" );
            mLOGGER.error( message );
        }
    }

    /**
     * This method create the CronTrigger for the job and configured it
     * 
     * @return A configured CronTrigger
     * @throws Exception exception happen during the trigger creation
     */
    private CronTrigger createTrigger()
        throws Exception
    {
        CronTrigger cTrigger = null;
        if ( !"".equals( mCron ) )
        {
            /*
             * If the "cron-expression" has been filled then we used it for configure the trigger
             */
            cTrigger = new CronTrigger( "triggSqualix", "groupSqualix", "jobSqualix", "groupSqualix", mCron );
        }
        else
        {
            /*
             * Else we retrieve the "cron-expression" from a xml configuration file
             */
            try
            {
                config();
            }
            catch ( ConfigurationException cfe )
            {
                throw new Exception( cfe );
            }
            if ( !"".equals( mCron ) )
            {
                // If the "cron-expression" has been found in the xml file, we use it for configure the trigger       
                cTrigger = new CronTrigger( "triggSqualix", "groupSqualix", "jobSqualix", "groupSqualix", mCron );
            }
            else
            {
                // Else we defined a default "cron-expression" : launch of a squalix job each 30 minutes 
                cTrigger =
                    new CronTrigger( "triggSqualix", "groupSqualix", "jobSqualix", "groupSqualix", "0 0/30 * * * ?" );
                mLOGGER.info( QuartzMessages.getString( "quartz.trigger.default", cTrigger.getCronExpression() ) );
            }
        }
        return cTrigger;
    }

    /**
     * Getter for the mCron attribute
     * 
     * @return The "cron-expression" for quartz
     */
    public String getCron()
    {
        return mCron;
    }

    /**
     * Setter for the mCron attribute
     * 
     * @param pCron The new value for the "cron-expression"
     */
    public void setCron( String pCron )
    {
        mCron = pCron;
    }

}
