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
package com.airfrance.squalix.tools.ruleschecking;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Java;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Commandline.Argument;

import com.airfrance.squalix.core.exception.ConfigurationException;

/**
 * Processus checkstyle L'exécution de l'outil Checkstyle se fait par l'intermédiaire de cette classe. Elle exécute
 * checkstyle dans une tâche ant de type java pour permettre l'accès aux jars spécifique de checkstyle qui sont en
 * conflit avec ceux de hibernate. Le processus prend en entrée le fichier de configuration au format xml ainsi que la
 * liste des répertoires contenant les fichiers à analyser. Il fournit en sortie un fichier au format XML qui contient
 * les trasngressions repérées
 */
public class CheckStyleProcess
    implements BuildListener
{
    /**
     * Logger.
     */
    private static final Log LOGGER = LogFactory.getLog( CheckStyleProcess.class );

    /** Répertoire des jars */
    private File mJarDirectory;

    /** Répertoire des rapports */
    private File mReportDirectory;

    /** Indicateur d'erreur d'exécution */
    private boolean mErrorOccurred;

    /** version des sources java */
    private String mJavaVersion;

    /**
     * Constructeur
     * 
     * @param pJarDirectory répertoire des jars
     * @param pReportDirectory répertoire des rapports
     * @param pJavaVersion la version des sources
     * @throws ConfigurationException si erreur
     */
    public CheckStyleProcess( File pJarDirectory, File pReportDirectory, String pJavaVersion )
        throws ConfigurationException
    {
        checkDirectory( pJarDirectory );
        mJarDirectory = pJarDirectory;
        checkDirectory( pReportDirectory );
        mReportDirectory = pReportDirectory;
        mJavaVersion = pJavaVersion;
    }

    /**
     * Vérification d'un répertoire
     * 
     * @param pDirectory répertoire
     * @throws ConfigurationException si répertoire inexistant
     */
    private void checkDirectory( File pDirectory )
        throws ConfigurationException
    {
        if ( !pDirectory.exists() || !pDirectory.isDirectory() )
        {
            // On essaye de le créer sinon on retourne une exception
            if ( !pDirectory.mkdirs() )
            {
                throw new ConfigurationException( RulesCheckingMessages.getString( "error.bad_directory", pDirectory ) );
            }
        }
    }

    /**
     * Analyse des sources avec Checkstyle
     * 
     * @param pRuleFile fichier de règles
     * @param sourceDir répertoires contenant les sources
     * @param pReportName nom du rapport généré
     * @return fichier généré
     */
    public File analyseSources( File pRuleFile, File sourceDir, String pReportName )
    {
        File result = new File( mReportDirectory, pReportName );
        Java task = createAntTask( pRuleFile, sourceDir, result );
        mErrorOccurred = false;
        task.getCommandLine();
        task.execute();

        // mErrorOccured peut passer à true
        return result;
    }

    /**
     * Indique si une erreur s'est produite
     * 
     * @return true si une erreur s'est produite
     */
    public boolean hasErrorOccurred()
    {
        return mErrorOccurred;
    }

    /**
     * Create a ajava ant task for launch checkstyle
     * 
     * @param pRuleFile The rule file
     * @param sourceDir The directory in which there is the sources to analyze
     * @param pResultFile The result file
     * @return a configured ANT task
     */
    private Java createAntTask( File pRuleFile, File sourceDir, File pResultFile )
    {
        // We create a java ant task
        Java task = new Java();

        // We create the project
        Project antProject = new Project();
        antProject.addBuildListener( this );
        task.setProject( antProject );

        // We fork to avoid classpath problems
        task.setFork( true );

        // The class to call
        task.setClassname( "com.puppycrawl.tools.checkstyle.Main" );

        // Classpath creation
        Path path = createClassPath( antProject );
        task.setClasspath( path );

        // Jvm argument. We increase the memory allocate to the JVM
        Argument jvmArg = task.createJvmarg();
        jvmArg.setValue( "-Xmx128M" );
        jvmArg = task.createJvmarg();
        jvmArg.setValue( "-Xss1M" );

        // We create the task argument
        Argument arg = task.createArg();

        // The result format : xml
        arg = task.createArg();
        arg.setValue( "-f" );
        arg = task.createArg();
        arg.setValue( "xml" );

        // Location for the result file
        arg = task.createArg();
        arg.setValue( "-o" );
        arg = task.createArg();
        arg.setValue( pResultFile.getAbsolutePath() );

        // Location of the result file
        arg = task.createArg();
        arg.setValue( "-c" );
        arg = task.createArg();
        arg.setValue( pRuleFile.getAbsolutePath() );

        // Location of the directori in which there ise the file to analyze
        arg = task.createArg();
        arg.setValue( "-r" );
        arg = task.createArg();
        arg.setValue( sourceDir.getAbsolutePath() );

        return task;
    }

    /**
     * This method create the classpath for the java ant task that will launch checkstyle.
     * 
     * @param pAntProject The ANT project for which we create the classpath
     * @return The correct classpath for the ant task
     */
    private Path createClassPath( Project pAntProject )
    {
        Path path = new Path( pAntProject );
        // We create a list of all the jar of squalix and we keep only those needed for run checkstyle
        File[] jarFiles = mJarDirectory.listFiles( new FilenameFilter()
        {
            public boolean accept( File dir, String name )
            {
                return name.endsWith( ".jar" )
                    && ( name.startsWith( "checkstyle" ) || name.startsWith( "antlr" )
                        || name.startsWith( "commons-beanutils" ) || name.startsWith( "commons-collections" )
                        || name.startsWith( "commons-logging" ) || name.startsWith( "commons-cli" ) );
            }
        } );
        // We add the list of jar to the path
        for ( int i = 0; i < jarFiles.length; i++ )
        {
            path.setPath( jarFiles[i].getAbsolutePath() );
        }
        return path;
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.tools.ant.BuildListener#buildStarted(org.apache.tools.ant.BuildEvent)
     */
    public void buildStarted( BuildEvent event )
    {
        log( event );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.tools.ant.BuildListener#buildFinished(org.apache.tools.ant.BuildEvent)
     */
    public void buildFinished( BuildEvent event )
    {
        log( event );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.tools.ant.BuildListener#targetStarted(org.apache.tools.ant.BuildEvent)
     */
    public void targetStarted( BuildEvent event )
    {
        log( event );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.tools.ant.BuildListener#targetFinished(org.apache.tools.ant.BuildEvent)
     */
    public void targetFinished( BuildEvent event )
    {
        log( event );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.tools.ant.BuildListener#taskStarted(org.apache.tools.ant.BuildEvent)
     */
    public void taskStarted( BuildEvent event )
    {
        log( event );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.tools.ant.BuildListener#taskFinished(org.apache.tools.ant.BuildEvent)
     */
    public void taskFinished( BuildEvent event )
    {
        log( event );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.tools.ant.BuildListener#messageLogged(org.apache.tools.ant.BuildEvent)
     */
    public void messageLogged( BuildEvent event )
    {
        log( event );
    }

    /**
     * @param pEvent évènement
     */
    private void log( BuildEvent pEvent )
    {
        // On adapte les logs en fonction de la gravité du log initial
        String message = pEvent.getMessage();
        Throwable exception = pEvent.getException();
        // Utilisation d'un switch car on ne peut pas
        // se passer du mapping entre en entier et une méthode
        // à appeler : on pourrait mémoriser dans une map
        // la méthode et l'entier mais celà est un peu lourd
        switch ( pEvent.getPriority() )
        {
            case Project.MSG_ERR:
                LOGGER.error( message, exception );
                mErrorOccurred = true;
                break;

            case Project.MSG_WARN:
                LOGGER.warn( message, exception );
                break;

            case Project.MSG_INFO:
                LOGGER.info( message, exception );
                break;

            case Project.MSG_VERBOSE:
                LOGGER.trace( message, exception );
                break;

            case Project.MSG_DEBUG:
                LOGGER.debug( message, exception );
                break;

            default:
                // Par défaut on log sur le flux de warning
                LOGGER.warn( message, exception );
                // par défaut
                break;
        }
    }

}
