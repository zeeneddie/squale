package com.airfrance.squalix.core.quartz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.airfrance.squalix.core.Scheduler;
import com.airfrance.squalix.core.Squalix;
import com.airfrance.squalix.core.exception.ConfigurationException;

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
        mLOGGER = LogFactory.getLog( Squalix.class );
        mLOGGER.info( QuartzMessages.getString( "quartz.squalixJob.launch" ) );
        site = context.getJobDetail().getJobDataMap().getString( SITE_KEY );
    
        try
        {
            //Launch of the Squalix scheduler
            scheduler = new Scheduler( new Long( site ).longValue() );
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

        mLOGGER.info( QuartzMessages.getString( "quartz.squalixJob.finish" ) );
    }

}
