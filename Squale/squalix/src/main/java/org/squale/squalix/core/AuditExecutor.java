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
//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\org\\squale\\squalix\\core\\AuditExecutor.java

package org.squale.squalix.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;
import org.squale.squalix.messages.Messages;

/**
 * Exécute un audit entier sur un projet. <br />
 * Il exécute les tâches associée à une application en les ordonnançant. Cet ordonnancement des tâches est déterminé par
 * rapport au profil du projet. La configuration doit définir un scénario d'exécution pour le profil du projet. Voir la
 * configuration de Squalix pour plus de renseignements. <br />
 * <br />
 * Toutes les tâches que lancent l'exécuteur d'analyse doivent hériter de la classe abstraite <code>Task</code>.
 * <br />
 * <br />
 * Deux groupes de tâches sont lancés par l'exécuteur d'analyse :
 * <ul>
 * <li>le premier concerne les analyses par des outils divers,</il>
 * <li>le second nécessite que toutes les tâches du premier soient terminées (consolidation de résultats, génération de
 * graphiques...).</li>
 * </ul>
 * 
 * @see org.squale.squalix.configurationmanager.Configuration
 * @author m400842
 * @version 1.0
 */
public class AuditExecutor
    implements Runnable
{

    /**
     * Statut de l'exécution de l'audit à la fin de l'exécution
     */
    private int mFinalStatus = AuditBO.NOT_ATTEMPTED;

    /**
     * Audit paramétrant l'analyse.
     */
    private AuditBO mAudit;

    /**
     * Projet sur lequel est exécutée l'audit.
     */
    private ProjectBO mProject;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( AuditExecutor.class );

    /**
     * Liste des tâches instanciées dans l'audit.
     */
    private List mTasks;

    /**
     * Tâches à réaliser lorsque toutes les tâches d'analyse sont terminées.
     */
    private List mTerminationTasks = null;

    /**
     * Instance du scheduleur de l'audit
     */
    private Scheduler mScheduler = null;

    /**
     * Constructor.<br>
     * Create the audit executor which contains task and termination task to execute for an audit.
     * 
     * @param pAudit The audit to do.
     * @param pProject Project on which we realize the audit.
     * @param pTasks Analyze tasks to realize.
     * @param pTerminationTasks Termination task to realize
     * @param lastProject Is it the last project ?
     * @roseuid 42AE866F01F7
     */
    public AuditExecutor( final List pTasks, final List pTerminationTasks, final AuditBO pAudit,
                          final ProjectBO pProject, boolean lastProject )
    {
        /*
         * The TaskData contains some temporary parameter. Empty at the beginning, it could be modified by each task.
         * Then the following tasks could recover the informations they need and which depends from a previous task.
         */
        TaskData taskData = new TaskData();
        ApplicationBO appli = (ApplicationBO) pProject.getParent();
        // Creation des tâches d'analyse
        mTasks = new ArrayList();
        AbstractTask task = null;
        for ( int i = 0; i < pTasks.size(); i++ )
        {
            // Creation of each task
            task =
                TaskUtility.createTask( (TaskRefBO) pTasks.get( i ), pProject.getId(), pProject.getParent().getId(),
                                        pAudit.getId(), taskData );
            // The list of task is completed with the new one
            mTasks.add( task );

        }

        // Creation des tâches de complétude sur le même schéma
        mTerminationTasks = new ArrayList();
        for ( int i = 0; i < pTerminationTasks.size(); i++ )
        {
            task =
                TaskUtility.createTask( (TaskRefBO) pTerminationTasks.get( i ), pProject.getId(),
                                        pProject.getParent().getId(), pAudit.getId(), taskData );

            // Is the task a source code recovering termination task ?
            if ( task instanceof AbstractSourceTerminationTask )
            {
                // if yes then we should determine if task does the optimization of the source code recovering
                AbstractSourceTerminationTask sourceterminationtask = (AbstractSourceTerminationTask) task;
                if ( sourceterminationtask.doOptimization() )
                {
                    // If the task does the optimization of the source code recovering then we don't add this task to
                    // the list and we keep it for after
                    appli.getSourceCodeTerminationTask().add( pTerminationTasks.get( i ) );

                }
                else
                {
                    // If the task doesn't do the optimization of the source code recovering then we add it to list of
                    // termination task
                    mTerminationTasks.add( task );
                }
            }
            else
            {
                // if the task is not a source code recovering termination task then we add it to the list of
                // termination task
                mTerminationTasks.add( task );
            }
        }
        // Is it the last project of this Audit ?
        if ( lastProject )
        {
            // If yes then we add all source code termination task we keep
            Iterator keepTaskIt = appli.getSourceCodeTerminationTask().iterator();
            while ( keepTaskIt.hasNext() )
            {
                // Creation of each task
                task =
                    TaskUtility.createTask( (TaskRefBO) keepTaskIt.next(), pProject.getId(),
                                            pProject.getParent().getId(), pAudit.getId(), taskData );
                // The termination task list is completed with the new one
                mTerminationTasks.add( task );
            }
        }

        mAudit = pAudit;
        mProject = pProject;
    }

    /**
     * @param pTask la tâche observée ayant changé de statut.
     * @return un booléen indiquant si on doit continuer à faire les taches d'analyse, return false si une tache a
     *         échouée
     * @roseuid 42C29E870138
     */
    public boolean update( final AbstractTask pTask )
    {
        // booléen indiquant si on doit continuer à faire l'ensemble
        // des taches normalement ou si on doit arréter si il y a eu un problème
        boolean stop = false;
        int status = pTask.getStatus();
        Iterator it = mTasks.iterator();
        if ( AbstractTask.FAILED == status )
        {
            // la tache a échouée
            // On stoppe l'audit de toutes les tâches en cours non encore lancées
            // En les mettant en statut CANCELLED
            LOGGER.error( "La tache : " + pTask.getClass().getName() + " a echouee" );
            // On parcours la liste des taches jusqu'à celle passée en paramètre
            while ( it.hasNext() && (AbstractTask) it.next() != pTask )
            {
            }
            ;
            // on dit que celle là a échouée
            pTask.setStatus( AbstractTask.FAILED );
            // Si la tache était obligatoire, on annule les autres sinon on continue
            // mais dans tous les cas la tache est mise en echec
            stop = pTask.isMandatoryTask();
            if ( stop )
            {
                // et on change le status des suivantes à "annuler"
                while ( it.hasNext() )
                {
                    AbstractTask t = (AbstractTask) it.next();
                    TaskUtility.stopTask( t );
                    LOGGER.info( "La tache : " + t.getClass().getName() + " a ete annulee" );
                }
            }
        }
        return stop;
    }

    /**
     * Renvoie le status de l'audit executor après analyse des status de toutes les taches
     * 
     * @return le status
     */
    private int getStatus()
    {
        // Initialisation à terminé par défaut
        int result = AuditBO.TERMINATED;
        // On met dans les conditions de sortie de la boucle le staus failed,
        // car une fois que c'est failed c'est définitif
        // On n'affecte jamais terminé dans la boucle, car si on passe dans un des tests
        // c'est qu'il ne peut pas etre terminé
        // Terminé < partiel < failed
        for ( int i = 0; result != AuditBO.FAILED && i < mTasks.size(); i++ )
        {
            AbstractTask task = ( (AbstractTask) mTasks.get( i ) );
            if ( task.getStatus() == AbstractTask.FAILED )
            {
                if ( !task.isMandatoryTask() )
                {
                    result = AuditBO.PARTIAL;
                }
                else
                {
                    result = AuditBO.FAILED;
                }
            }
        }
        return result;
    }

    /**
     * Access method for the mAudit property.
     * 
     * @return the current value of the mAudit property
     * @roseuid 42C2A3B000B6
     */
    public AuditBO getAudit()
    {
        return mAudit;
    }

    /**
     * Getter method for the mTerminationTasks property.
     * 
     * @return the current value of the mTerminationTasks property
     */
    public List getTerminationTasks()
    {
        return mTerminationTasks;
    }

    /**
     * Termine l'audit en effectuant les opérations nécessaires à sa clôture
     * 
     * @roseuid 42CBC4C001F3
     */
    private void terminateAudit()
    {
        try
        {
            LOGGER.info( Messages.getString( "log.audit_executor.terminated" ) + mProject.getParent().getName()
                + Messages.getString( "separator.project" ) + mProject.getName() );
            // Execution des tâches de complétude
            LOGGER.info( "**** Execution des taches terminales ****" );
            for ( int i = 0; i < mTerminationTasks.size(); i++ )
            {
                AbstractTask task = (AbstractTask) mTerminationTasks.get( i );
                task.run();
                // Attention: bien mettre les taches de terminaison
                // qui ne sont pas critiques comme étant facultatives pour ne pas avoir
                // un audit en échec si ces taches là échouent
                update( task );
            }
            // Les status terminés ,partiels ou en échec ne peuvent etre affectés qu'ici
            // une fois que toutes les taches ont été réalisées
            mFinalStatus = getStatus();
        }
        catch ( Exception e )
        {
            LOGGER.error( e, e );
        }
    }

    /**
     * Execute l'ensemble des taches de l'audit
     * 
     * @roseuid 42CCD3550279
     */
    public void run()
    {
        boolean stop = false;
        // On lance les taches systèmes préalables
        AbstractTask task = null;
        if ( mFinalStatus != AuditBO.FAILED )
        {
            // Execution des tâches d'analyse
            for ( int i = 0; i < mTasks.size() && !stop; i++ )
            {
                task = (AbstractTask) mTasks.get( i );
                task.run();
                // On signale que la tache a été faite
                // et on met à jour son status
                stop = update( task );
            }
        }
        // met à jour la taille du file system
        mAudit.setMaxFileSystemSize( getMax( mTasks ) );
        // Puis on lance les tâches qui cloturent l'analyse du projet
        terminateAudit();
    }

    /**
     * @param pTasks la liste des taches de l'audit
     * @return la taille max du file system durant l'audit
     */
    private Long getMax( List pTasks )
    {
        long finalMax = 0;
        for ( int i = 0; i < mTasks.size(); i++ )
        {
            long max = ( (AbstractTask) mTasks.get( i ) ).getMaxFileSystemSize();
            for ( int j = 0; j < mTasks.size(); j++ )
            {
                max += ( (AbstractTask) mTasks.get( i ) ).getPersistentFileSystemSize();
            }
            if ( max > finalMax )
            {
                finalMax = max;
            }
        }
        // Pour un affichage en mégaOctet
        final int Mega = 1000000;
        return new Long( finalMax / Mega );
    }

    /**
     * Sets the value of the mScheduler property.
     * 
     * @param pScheduler the new value of the mScheduler property
     * @roseuid 42CE36C70276
     */
    public void setScheduler( Scheduler pScheduler )
    {
        mScheduler = pScheduler;
    }

    /**
     * Projet sur lequel est exécuté l'analyse.
     * 
     * @return le projet.
     * @roseuid 42DFC07A01C5
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * @return le status à la fin de l'exécution
     */
    public int getFinalStatus()
    {
        return mFinalStatus;
    }

    /**
     * @param pFinalStatus la nouvelle valeur du status à la fin de l'exécution
     */
    public void setFinalStatus( int pFinalStatus )
    {
        mFinalStatus = pFinalStatus;
    }

}
