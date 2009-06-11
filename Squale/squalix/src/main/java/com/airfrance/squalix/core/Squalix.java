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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\core\\Squalix.java
package com.airfrance.squalix.core;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.airfrance.jraf.bootstrap.initializer.Initializer;
import com.airfrance.squalix.messages.Messages;
import com.airfrance.squalix.core.quartz.QuartzSqualixScheduler;

/**
 * Lance l'application Squalix. <br /> Ceci consiste en quelques opérations simples :
 * <ul>
 * <li>initialisation du socle JRAF,</li>
 * <li>lecture des paramètres de lancement,</li>
 * <li>instanciation d'un scheduler si l'exécution peut-être lancée.</li>
 * </ul>
 * <br /> Le lancement du scheduler est synchrone, ce qui bloque l'application tant que le scheduler tourne. <br /> Pour
 * plus de renseignements sur le fonctionnement du moteur de tâches, reportez-vous à la javadoc des classes
 * <code>Scheduler</code> et <code>ResourcesManager</code>.
 * 
 * @see com.airfrance.squalix.core.Scheduler
 * @see com.airfrance.squalix.core.ResourcesManager
 * @author m400842
 * @version 1.0
 */
public class Squalix
{

    /**
     * Logger
     */
    private static Log mLOGGER;

    /**
     * Clé du site hébergeant l'application
     */
    private static String mSite;

    /**
     * Chemin du fichier de configuration
     */
    private static String mConfigFile;

    /**
     * Clé de périodicité pour le lancement de l'application Squalix
     */
    private static String mCron = "";

    /**
     * Boolean permettant de savoir la manière de lancer le batch Squalix
     */
    private static boolean mQuartzActive;

    /**
     * Options de lancement de la tache Squalix
     */
    private static Options optionsDemarage = new Options();

    /**
     * Option -s passée en paramètre
     */
    private static Option optionSite =
        new Option( Messages.getString( "main.site_option" ), "site", true,
                    "site principale sur lequel tourne l'application squalix" );

    /**
     * Option -f passée en paramètre
     */
    private static Option optionFichierConf =
        new Option( Messages.getString( "main.configFile_option" ), "fichierConf", false,
                    "Fichier de paramétrage de la configuration de Squalix" );

    /**
     * Option -cron passée en paramètre
     */
    private static Option optionCron =
        new Option( Messages.getString( "main.configCron_option" ), "chronologique", true,
                    "Si la tâche doit être lancée de manière asynchrone" );

    /**
     * Option -debug passée en paramètre ne sert à rien pour le lancement en mode Debug, mais doit figurer dans la liste
     * des options
     */
    private static Option optionDebug =
        new Option( "debug", "debogage", false, "Indique si on a lancé la tâche en mode DEBUG" );

    /**
     * parser de la ligne de commande
     */
    private static CommandLineParser parser = new PosixParser();

    static
    {
        // Spécification du nom du paramètre de l'argument qui sera affiché dans l'aide
        optionSite.setArgName( Messages.getString( "main.site_option" ) );
        optionFichierConf.setArgName( Messages.getString( "main.configFile_option" ) );
        optionCron.setArgName( Messages.getString( "main.configCron_option" ) );
        optionDebug.setArgName( "debug" );
        // Spécification du type d'object que retournera getValue
        optionSite.setType( String.class );
        optionFichierConf.setType( String.class );
        optionCron.setType( String.class );
        // Oblige le paramètre d'être présent lors de l'exécution
        optionSite.setRequired( true );
        optionFichierConf.setRequired( false );
        optionCron.setRequired( false );
        optionDebug.setRequired( false );

        // On ajout ensuite les "Option" dans la collection
        optionsDemarage.addOption( optionSite );
        optionsDemarage.addOption( optionFichierConf );
        optionsDemarage.addOption( optionCron );
        optionsDemarage.addOption( optionDebug );
    }

    /**
     * Méthode main.
     * 
     * @param pArgs arguments de lancement.
     * @roseuid 42918D3702A5
     */
    public static void main( final String[] pArgs )
    {
        // lancement de l’initialisation JRAF
        String rootPath = pArgs[0];
        String configFile = "/config/providers-config.xml";
        Initializer init = new Initializer( rootPath, configFile );
        init.initialize();
        // Maintenant que le socle JRAF est initialisé, on peut créer un logger
        mLOGGER = LogFactory.getLog( Squalix.class );
        if ( null != pArgs && pArgs.length > 0 )
        {
            try
            {
                // On trie les paramètres
                getParameters( pArgs );
                Scheduler scheduler = null;
                if ( null == mSite )
                {
                    // Si aucun site n'est spécifié, on quitte l'application
                    mLOGGER.fatal( Messages.getString( "main.missing_parameters" ) );
                }
                else if ( mQuartzActive )
                {
                    QuartzSqualixScheduler qss = new QuartzSqualixScheduler( mSite, mCron );
                    qss.scheduleSqualix( new StdSchedulerFactory() );
                }
                else
                {
                    //Launch of the scheduler in a new thread
                    scheduler = new Scheduler( new Long( mSite ).longValue() );
                    Thread schedulerThread = new Thread(scheduler);
                    schedulerThread.start();
                }
            }
            catch ( Exception e )
            {
                // Si un problème apparaît dans la récupération de la configuration
                // ou des paramètres, on loggue et on quitte
                mLOGGER.fatal( "Fatal error", e );
            }
        }
        else
        {
            mLOGGER.fatal( Messages.getString( "main.missing_parameters" ) );
        }
    }

    /**
     * Récupère les paramètres de la ligne de commande
     * 
     * @param pArgs liste des paramètres.
     * @throws ParseException
     * @roseuid 42CE4F340204
     * @throws ParseException si la liste des arguments est mal interprétée
     */
    private static void getParameters( final String[] pArgs )
        throws ParseException
    {
        CommandLine cmd = parser.parse( optionsDemarage, pArgs, false );

        mSite = (String) cmd.getOptionObject( Messages.getString( "main.site_option" ) );

        if ( cmd.hasOption( Messages.getString( "main.configFile_option" ) ) )
        {
            mConfigFile = (String) cmd.getOptionObject( Messages.getString( "main.configFile_option" ) );
        }

        if ( cmd.hasOption( Messages.getString( "main.configCron_option" ) ) )
        {
            mCron = (String) cmd.getOptionObject( Messages.getString( "main.configCron_option" ) );
            if ( mCron == null )
            {
                mCron = "";
            }
            mQuartzActive = true;
        }

    }

    /**
     * Getter method for the attribuet mSite (id of the Squalix server)
     * 
     * @return The id of the Squalix server
     */
    /*
     * public static String getMSite() { return mSite; }
     */

    /**
     * Getter for the attribute isQuartzExecuteRunning
     * 
     * @return true if the squalix job already running
     */
    /*
     * public static boolean isQuartzExecuteRunning() { return isQuartzExecuteRunning; }
     */

    /**
     * Setter for the attribute isQuartzExecuteRunning This attribute indicate if a squalix job already running
     * 
     * @param pIsQuartzExecuteRunning The new value fior the attribute
     */
    /*
     * public static void setQuartzExecuteRunning( boolean pIsQuartzExecuteRunning ) { isQuartzExecuteRunning =
     * pIsQuartzExecuteRunning; }
     */
}
