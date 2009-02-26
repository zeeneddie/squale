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
package com.airfrance.squalix.tools.quartz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SchedulerMetaData;

import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Classe permettant d'instancier un scheduler qui va lancer la tache Squalix de manière asynchrone
 * 
 * @author fgaillard
 */
public class QuartzSqualixScheduler
{

    /**
     * Constructeur
     */
    public QuartzSqualixScheduler()
    {
    }

    /**
     * Le root path défini dans les paramètres de lancement
     */
    private static String mPath = "";

    /**
     * Logger
     */
    private static Log mLOGGER;

    /**
     * Clé de séquencement pour le lancement de l'application Squalix
     */
    private static String mCron = "";

    /** Quartz cronTrigger configuration */
    private QuartzTimerConfig configuration;

    /**
     * La mise en place du scheduler pour le lancement de la tâche Squalix
     * 
     * @param sf La factory de scheduler standard définie par le fichier de config quartz.properties
     * @throws Exception
     */
    public void scheduleSqualix( SchedulerFactory sf )
        throws Exception
    {

        mLOGGER = LogFactory.getLog( QuartzSqualixScheduler.class );
        mLOGGER.info( "------- Initializing -------------------" );
        Scheduler sched = sf.getScheduler();
        mLOGGER.info( "------- Initializing 2 -------------------" );

        // réinitialisation des groupes
        String[] groups = sched.getTriggerGroupNames();
        for ( int i = 0; i < groups.length; i++ )
        {
            String[] names = sched.getTriggerNames( groups[i] );
            for ( int j = 0; j < names.length; j++ )
            {
                sched.unscheduleJob( names[j], groups[i] );
            }
        }

        // réinitialisation des travaux
        groups = sched.getJobGroupNames();
        for ( int i = 0; i < groups.length; i++ )
        {
            String[] names = sched.getJobNames( groups[i] );
            for ( int j = 0; j < names.length; j++ )
            {
                sched.deleteJob( names[j], groups[i] );
            }
        }

        mLOGGER.info( "------- Initialization Complete -----------" );
        mLOGGER.info( "------- Scheduling Jobs -----------" );
        // Instatiation des details de travail de squalix
        JobDetail job = new JobDetail( "jobSqualix", "groupSqualix", com.airfrance.squalix.core.Squalix.class );
        CronTrigger cTrigger = null;
        if ( !"".equals( mCron ) )
        {
            // Si la chaine de définition de la séquence d'exécution a été renseignée au niveau de l'appel
            // du batch, on va utiliser celle-ci
            cTrigger = new CronTrigger( "triggSqualix", "groupSqualix", "jobSqualix", "groupSqualix", mCron );
        }
        else
        {
            // On va regarder la clé du cronTrigger dans le fichier de config
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
                // La définition du cronTrigger a été trouvée dans le fichier XML. On implémente la cronTrigger en
                // fonction du mCron
                cTrigger = new CronTrigger( "triggSqualix", "groupSqualix", "jobSqualix", "groupSqualix", mCron );
            }
            else
            {
                // si la définition du cronTrigger est toujours vide, on en défini une par défaut qui se lance toutes
                // les 30 minutes à h00 et h30
                mLOGGER.info( "The default Cron Trigger is implemented : \"0 0/30 * * * ?\"" );
                cTrigger =
                    new CronTrigger( "triggSqualix", "groupSqualix", "jobSqualix", "groupSqualix", "0 0/30 * * * ?" );
            }
        }
        Date ft = sched.scheduleJob( job, cTrigger );
        mLOGGER.info( job.getFullName() + " will run at: " + ft + " & repeat based on expression: "
            + cTrigger.getCronExpression() );
        sched.start();
        mLOGGER.info( "------- Started Scheduler -----------------" );
        mLOGGER.info( "------- Waiting... -----------------------" );

//        Thread.sleep( 2L * 60L * 1000L );
//
//        mLOGGER.info( "------- Shutting Down ---------------------" );
//        sched.shutdown( true );
//        mLOGGER.info( "------- Shutdown Complete -----------------" );
//        SchedulerMetaData metaData = sched.getMetaData();
//        mLOGGER.info( "Executed " + metaData.numJobsExecuted() + " jobs." );
    }

    /**
     * This method do the configuration of javancss. That means get back the path for the result file.
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
     * Getter de l'attribut static mCron
     * 
     * @return la valeur de l'attribut static mCron
     */
    public static String getCron()
    {
        return mCron;
    }

    /**
     * Setter de l'attribut static mCron
     * 
     * @param pCron la valeur à donner à l'attribut static mCron
     */
    public static void setCron( String pCron )
    {
        mCron = pCron;
    }
}
