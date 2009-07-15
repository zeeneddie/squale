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
package org.squale.squalix.core.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import org.squale.squalix.core.Scheduler;
import org.squale.squalix.core.Squalix;
import org.squale.squalix.core.exception.ConfigurationException;

/**
 * Implementation of quartz job for Squalix
 */
public class SqualixJob
    implements StatefulJob
{

    /**
     * Logger
     */
    private static Log mLOGGER;

    /**
     * Key used in the jobDataMap
     */
    public static final String SITE_KEY = "site";

    /**
     * The technical id of the Squalix server
     */
    private String site;

    /**
     * Public constructor
     */
    public SqualixJob()
    {

    }

    /**
     * Permet de lancer la tache Squalix par l'intermédiaire de quartz
     * 
     * @param context Le contexte d'exécution du "Job" Quartz
     * @throws JobExecutionException renvoyée si une exception apparait dans le traitement du job Quartz
     */
    public void execute( JobExecutionContext context )
        throws JobExecutionException
    {
        Scheduler scheduler = null;
        mLOGGER = LogFactory.getLog( SqualixJob.class );
        mLOGGER.debug( QuartzMessages.getString( "quartz.squalixJob.launch" ) );
        site = context.getJobDetail().getJobDataMap().getString( SITE_KEY );

        try
        {
            // Launch of the Squalix scheduler
            scheduler = new Scheduler( new Long( site ).longValue(), false );
            scheduler.run();
        }
        catch ( NumberFormatException e )
        {
            throw new JobExecutionException( e );
        }
        catch ( ConfigurationException e )
        {
            throw new JobExecutionException( e );
        }

        mLOGGER.debug( QuartzMessages.getString( "quartz.squalixJob.finish" ) );
    }

}
