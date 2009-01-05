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
package com.airfrance.squalix.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.SessionImpl;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MarkDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.result.rulechecking.RuleCheckingTransgressionDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import com.airfrance.squalecommon.datatransfertobject.result.SqualeReferenceDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.AuditTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.AuditFrequencyTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.roi.RoiMetricsBO;
import com.airfrance.squalecommon.enterpriselayer.facade.config.SqualixConfigFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.quality.SqualeReferenceFacade;
import com.airfrance.squalecommon.util.SqualeCommonConstants;
import com.airfrance.squalecommon.util.SqualeCommonUtils;
import com.airfrance.squalecommon.util.mail.IMailerProvider;
import com.airfrance.squalecommon.util.mail.MailerHelper;
import com.airfrance.squalix.core.exception.ConfigurationException;
import com.airfrance.squalix.core.purge.Purge;
import com.airfrance.squalix.messages.Messages;
import com.airfrance.squalix.stats.ComputeStats;
import com.airfrance.squalix.util.sourcesrecovering.SourcesRecoveringOptimisation;
import com.airfrance.squalix.util.stoptime.StopTimeHelper;

/**
 * A partir du fichier de configuration globale, il détermine la séquence des actions pour le projet associé et les
 * exécute.<br />
 * Le scheduler assure :
 * <ul>
 * <li>la récupération de la configuration du moteur de tâches,</li>
 * <li>la récupération des audits à lancer,</li>
 * <li>la création d'un exécuteur d'analyse (AuditExecutor) pour chacun des projets associés aux audits,</li>
 * <li>le lancement des tâches indépendantes d'un projet,</li>
 * <li>le lancement des exécuteurs d'analyse,</li>
 * <li>le lancement des tâches calculant les résultats au niveau du projet (graphes,...),</li>
 * <li>la modification du statut de l'audit lorsque celui-ci est terminé, et la création éventuelle du prochain audit
 * dans le cas d'un audit de suivi.</li>
 * </ul>
 * <br />
 * Tous les audits et tâches sont exécutés via des pools de threads, gérés par le gestionnaire de ressources.<br />
 * Voir la documentation de la classe <code>ResourcesManager</code> pour plus de renseignements sur son
 * fonctionnement. <br />
 * <br />
 * Lorsque tous les audits ont été lancés, les scheduler est bloqué tant que les pools sont ouverts.<br />
 * Pour s'assurer que ceux-ci sont ouverts, on utilise un <code>CountDownLatch</code> décrémenté par le gestionnaire
 * de ressources lorsqu'il ferme ses pools. <br />
 * <br />
 * Les tâches non liées à un projet et les exécuteurs d'analyse sont associés au scheduler par un pattern
 * Observateur-Observable, dans lequel le scheduler est l'observateur. Sa méthode <code>update()</code> est appelée
 * lorsque l'exécuteur ou la tâche est terminé.
 * 
 * @see com.airfrance.squalix.core.ResourcesManager
 * @see com.airfrance.squalix.core.AuditExecutor
 * @author m400842
 * @version 1.0
 */
public class Scheduler
    extends Thread
{

    /**
     * L'objet contenant les configurations
     */
    private SqualixConfigurationDTO mConf;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( Scheduler.class );

    /**
     * Liste des audits executeur crées.
     */
    private List mAudits = new ArrayList( 0 );

    /**
     * Clé du site hébergeant l'application.
     */
    private long mSiteId;

    /**
     * Provider de persistance
     */
    private static final IPersistenceProvider PERSISTANT_PROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Session de travail
     */
    private ISession mSession;

    /**
     * Le calendrier permettant de savoir à quel heure à été lancé le batch
     */
    private Calendar mLaunchingCal;

    /** la liste des projets d'un audit */
    private List mProjectsByAudit;

    /** pour envoyer des mails */
    private IMailerProvider mMailer = MailerHelper.getMailerProvider();

    /**
     * Instancie un scheduler.
     * 
     * @param pSiteId clé du site hébergeant l'application.
     * @throws ConfigurationException Si un problème de configuration apparaît.
     * @roseuid 42935241035F
     */
    public Scheduler( long pSiteId )
        throws ConfigurationException
    {
        try
        {
            mSession = PERSISTANT_PROVIDER.getSession();
            mConf = SqualixConfigFacade.getConfig();
            mLaunchingCal = Calendar.getInstance();

        }
        catch ( JrafEnterpriseException e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
        catch ( JrafPersistenceException e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
        mSiteId = pSiteId;
    }

    /**
     * Récupère les audits à réaliser le jour présent.
     * 
     * @param pStopTime timer donnant l'arrêt du batch
     * @return la liste des audits à réaliser ce jour
     * @throws JrafDaoException si un probleme de récupération apparaît.
     * @roseuid 42B7E4E30396
     */
    private List getAudits( StopTimeHelper pStopTime )
        throws JrafDaoException
    {
        mSession.beginTransaction();
        // Récupère les audits dont la date d'exécution est
        // antérieure à la date limite d'exécution
        List audits =
            (List) AuditDAOImpl.getInstance().findBeforeBySiteAndStatus( mSession, mSiteId, AuditBO.NOT_ATTEMPTED,
                                                                         pStopTime.getLimitCal().getTime() );
        mSession.commitTransactionWithoutClose();
        LOGGER.info( CoreMessages.getString( "audits.toprocess", new Object[] { new Integer( audits.size() ) } ) );
        return audits;
    }

    /**
     * Récupère la liste des projets d'un audit, et exécute les analyses dans l'ordre. L'audit est rattaché à chaque
     * projet
     * 
     * @param pAudit Audit pour lequel on désire obtenir la liste des projets
     * @return La collection des projets liés à l'audit
     * @roseuid 42B7E54D0127
     */
    private List getProjectsByAudit( final AuditBO pAudit )
    {
        List list = new ArrayList();
        try
        {
            Long id = new Long( pAudit.getId() );
            mSession.beginTransaction();
            ApplicationBO application = ApplicationDAOImpl.getInstance().loadByAuditId( mSession, id );
            Collection children = application.getChildren();
            if ( null != children )
            {
                // Si l'application contient des projets, on les ajoute
                // à la liste des projets à analyser
                // pour cet audit.
                Iterator it = children.iterator();
                Object obj = null;
                while ( it.hasNext() )
                {
                    obj = it.next();
                    if ( obj instanceof ProjectBO )
                    {
                        ProjectBO project = (ProjectBO) obj;
                        // Rattachement de l'audit au projet si celui-ci est actif
                        if ( project.getStatus() == ProjectBO.ACTIVATED )
                        {
                            project.addAudit( pAudit );
                            ProjectDAOImpl.getInstance().save( mSession, project );
                            list.add( project );
                        }
                    }
                }
            }
            mSession.commitTransactionWithoutClose();
        }
        catch ( Exception e )
        {
            // Si un problème de DAO apparaît, l'exception est logguée
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
        return list;
    }

    /**
     * Lance le scheduleur.<br>
     * 
     * @roseuid 42CD2F0B0287
     */
    public void run()
    {
        try
        {
            // On lance le monitoring de la mémoire
            // MemoryMonitor.startMonitoring(new MemoryMonitorConfiguration());
            StopTimeHelper stop = new StopTimeHelper( mConf, mLaunchingCal );
            LOGGER.info( CoreMessages.getString( "time.limit", stop.getLimitCal().getTime() ) );
            boolean canContinue = true;
            // Si on est le jour de la rotation des partitions
            if ( isRotationDay() )
            {
                // tache de purge et d'administration avec notamment la rotation des partitions
                admindb( stop );
            }
            // TODO voir comment récupérer les audits un par un dans la base
            // pour possible multi-threading ultérieur
            List audits = getAudits( stop );

            // purge dans un thread parallele
            new Purge( mSiteId, audits ).start();

            // Test si il y a des audits avec le status en cours pour le site concerné
            // Si oui, c'est anormal, on prévient les administrateurs mais on continue quand meme
            // car c'est peut etre simplement un audit qui a été interrompu brutalement et ça n'empechera
            // donc pas le nouveau de tourner
            if ( existsAlreadyRunningAudits( mSiteId ) )
            {
                // On envoie un mail pour signaler qu'un audit est en cours sur l'application
                String sender = Messages.getString( "mail.sender.squalix" );
                String header = Messages.getString( "mail.header" );
                String object = sender + Messages.getString( "mail.running_audit.exists.object" );
                String content = header + Messages.getString( "mail.running_audit.exists.content" );
                String dest = SqualeCommonConstants.ONLY_ADMINS;
                // On envoir le mail qu'aux abonnés
                SqualeCommonUtils.notifyByEmail( mMailer, null, SqualeCommonConstants.ONLY_ADMINS, null, object,
                                                 content, false );
            }
            // Exécuter les audits
            launchAudits( audits, stop );
            // Mise à jour des indicateurs DICT pour squale
            new ComputeStats().computeDICTStats();
            // Correction automatique de la fréquence des audits si la configuration existe
            if ( null != mConf.getFrequencies() && mConf.getFrequencies().size() > 0 )
            {
                manageAuditFrequency();
            }
            LOGGER.info( CoreMessages.getString( "endofexec" ) );
            // Fermeture de la session
            mSession.closeSession();
        }
        catch ( JrafDaoException e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
        catch ( ConfigurationException e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
        catch ( InterruptedException e )
        {
            // On n'a pas pu effectuer la purge correctement
            // On prévient les admins
            String sender = Messages.getString( "mail.sender.squalix" );
            String header = Messages.getString( "mail.header" );
            String object = sender + Messages.getString( "mail.rotation.audit.shutDown.object" );
            String content = header + Messages.getString( "mail.rotation.audit.shutDown.content" );
            SqualeCommonUtils.notifyByEmail( mMailer, null, SqualeCommonConstants.ONLY_ADMINS, null, object, content,
                                             false );
        }
        finally
        {
            // On arrête le monitoring de la mémoire
            // MemoryMonitor.stopMonitoring();
        }
    }

    /**
     * On fait une passe sur toutes les applications ayant un audit programmé pour vérifier les règles défini dans le
     * paramétrage de la fréquence. On baisse ensuite la fréquence des applications si besoin et on envoie un mail de
     * notification de changement de paramétrage à l'administrateur de l'application et aux admins SQUALE.
     * 
     * @throws JrafDaoException si erreur
     */
    private void manageAuditFrequency()
        throws JrafDaoException
    {
        mSession.beginTransaction();
        // On récupère les applications du site qui ont un audit de suivi programmé
        Collection applis = ApplicationDAOImpl.getInstance().findWhereHaveNotAttemptedAuditBySite( mSession, mSiteId );
        // Pour toutes les applis, on modifie la fréquence en fonction de son dernier accès utilisateur
        ApplicationBO appli = null;
        // On sauvegarde l'ancienne valeur de la fréquence pour le message du mail
        int oldFreq = 0;
        for ( Iterator it = applis.iterator(); it.hasNext(); )
        {
            appli = (ApplicationBO) it.next();
            oldFreq = appli.getAuditFrequency();
            // On change la fréquence si nécessaire
            if ( appli.changeFrequency( AuditFrequencyTransform.dto2bo( mConf.getFrequencies() ) ) )
            {
                LOGGER.info( "frequency has been changed for " + appli.getName() );
                // On sauvegarde les modifications
                ApplicationDAOImpl.getInstance().save( mSession, appli );
                // On envoit un mail d'information aux managers de l'application et aux admins SQUALE
                String sender = Messages.getString( "mail.sender.squalix" );
                String header = Messages.getString( "mail.header" );
                String object = sender + Messages.getString( "mail.application_frequency.changed.object" );
                String content =
                    header
                        + Messages.getString( "mail.application_frequency.changed.content", new String[] {
                            appli.getName(), "" + oldFreq, "" + appli.getAuditFrequency() } );
                String dest = SqualeCommonConstants.ONLY_ADMINS;
                SqualeCommonUtils.notifyByEmail( mMailer, null, SqualeCommonConstants.MANAGERS_AND_ADMINS, null,
                                                 object, content, false );
            }
        }
        mSession.commitTransactionWithoutClose();
    }

    /**
     * @param pSiteId le nom du site
     * @return true si il existe des audits avec le status running pour ce site
     * @throws JrafDaoException en cas d'échec
     */
    private boolean existsAlreadyRunningAudits( long pSiteId )
        throws JrafDaoException
    {
        return AuditDAOImpl.getInstance().countWhereStatusAndSite( mSession, pSiteId, AuditBO.RUNNING ) != 0;
    }

    /**
     * @return un booléen indiquant si il faut faire la répartition des audits ou pas
     * @throws JrafDaoException en cas d'echec de récupération de l'audit
     */
    private boolean isRotationDay()
        throws JrafDaoException
    {
        AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
        Calendar today = Calendar.getInstance();
        Calendar auditDate = Calendar.getInstance();
        Collection coll;
        boolean result = false;
        coll = auditDao.findRotationAudit( mSession );
        if ( coll != null && coll.size() != 0 )
        {
            Iterator it = coll.iterator();
            // Normalement un seul résultat
            while ( it.hasNext() )
            {
                AuditBO audit = (AuditBO) it.next();
                auditDate.setTime( audit.getDate() );
                result =
                    audit.getDate() != null
                        && auditDate.get( Calendar.DAY_OF_WEEK ) <= today.get( Calendar.DAY_OF_WEEK );
            }
        }
        else
        {
            // On a pas pu trouver l'audit de rotation des partitions,
            // On en informe les admins
            String header = Messages.getString( "mail.header" );
            String sender = Messages.getString( "mail.sender.squalix" );
            String object = sender + Messages.getString( "mail.rotation.audit.unknown.object" );
            String content = header + Messages.getString( "mail.rotation.audit.unknown.content" );
            SqualeCommonUtils.notifyByEmail( mMailer, null, SqualeCommonConstants.ONLY_ADMINS, null, object, content,
                                             false );
        }
        return result;
    }

    /**
     * Lance les exécutions des audits
     * 
     * @param pAudits audits à réaliser.
     * @param pStopTime timer d'arrêt du scheduler
     * @roseuid 42CE34390161
     */
    private void launchAudits( List pAudits, StopTimeHelper pStopTime )
    {
        if ( null != pAudits )
        {
            Iterator it = pAudits.iterator();
            AuditBO audit = null;
            // pour chaque audit lance les auditExecutors associés
            while ( it.hasNext() && !pStopTime.isTimeToStop() )
            {
                SourcesRecoveringOptimisation.reinit();
                audit = (AuditBO) it.next();
                audit.setRealBeginningDate( Calendar.getInstance().getTime() );
                launchAudit( audit );
                audit.setEndDate( Calendar.getInstance().getTime() );
                // met à jour le champ durée de l'audit en calculant
                // à partir des dates de début et de fin
                audit.calculeDuration();
                // Ne stocke pas la taille de la JVM car le résultat n'est pas pertinent
            }
            // Affichage d'un message s'il reste des audits à traiter
            if ( it.hasNext() )
            {
                LOGGER.warn( CoreMessages.getString( "time.limitreached" ) );
            }
        }
    }

    /**
     * Lance l'exécution d'un audit et donc de tous ses exécuteurs d'audit pour les projets que l'application contient
     * 
     * @param pCurrentAudit l'audit lancé
     */
    private void launchAudit( AuditBO pCurrentAudit )
    {
        AuditBO previousAudit = null;
        ApplicationBO application = null;
        // on passe tout de suite le status de l'audit à "en cours" et on crée de suite
        // un nouvel audit pour éviter les problèmes liés à l'interruption brutale du batch
        // enregistrement de l'audit
        try
        {
            mSession.beginTransaction();
            pCurrentAudit.setStatus( AuditBO.RUNNING );
            // On met à jour la version de SQUALE
            pCurrentAudit.setSqualeVersion( AuditBO.getCurrentSqualeVersion() );
            // On met la date tout de suite pour ne pas avoir de problèmes avec les audits interrompus
            pCurrentAudit.setDate( Calendar.getInstance().getTime() );
            AuditDAOImpl.getInstance().save( mSession, pCurrentAudit );
            application = ApplicationDAOImpl.getInstance().loadByAuditId( mSession, new Long( pCurrentAudit.getId() ) );

            // On récupère l'audit précédent
            AuditDAOImpl dao = AuditDAOImpl.getInstance();
            previousAudit = dao.getLastAuditByApplication( mSession, application.getId(), null, AuditBO.TERMINATED );

            createNewAudit( pCurrentAudit, application );
            mSession.commitTransactionWithoutClose();
            // On met la variable à terminé par défaut, de toute façon si on l'affecte alors la bonne valeur
            // a été traitée dans la boucle suivante, et si on ne rentre pas dans la boucle
            // c'est qu'on avait rien à faire mais on a pas eu d'erreurs
            int status = AuditBO.TERMINATED;
            // récupère la liste des auditsExecutors
            // un AuditExecutor par projet de l'application de l'audit
            List aeList = getAuditExecutors( pCurrentAudit );
            // le nombre de projets pour cet audit
            // aeList ne peut pas etre null, au pire vide
            // lance les taches de l'auditExecutor
            for ( int i = 0; i < aeList.size(); i++ )
            {
                launchTasks( (AuditExecutor) aeList.get( i ) );
                // un audit failed est définitif
                if ( status != AuditBO.FAILED )
                {
                    // Si il n'est pas non plus partiel, on récupère la valeur courante
                    // Sinon, un status partial ne peut etre changé que pour un status failed
                    // car failed est prioritaire sur partial
                    int newStatus = ( (AuditExecutor) aeList.get( i ) ).getFinalStatus();
                    if ( status != AuditBO.PARTIAL || ( status == AuditBO.PARTIAL && newStatus == AuditBO.FAILED ) )
                    {
                        status = newStatus;
                    }
                }
            }
            // on met à jour le status de l'audit que si on a effectué tous les projets de l'application de l'audit
            pCurrentAudit.setStatus( status );
            // sauvegarde l'audit
            saveCurrentAudit( pCurrentAudit, application );
            // Notification : en cas de réussite, on prévient les managers de l'application
            // que l'audit s'est bien passé
            String[] infos = new String[] { application.getName(), application.getServeurBO().getName() };
            manageResultMailing( pCurrentAudit.getStatus(), infos, application.getId() );
            // On exécute le calcul du ROI si l'audit a fonctionné et si l'application a un audit
            // terminé qui précéde le courant
            if ( pCurrentAudit.getStatus() == AuditBO.TERMINATED && null != previousAudit )
            {
                mSession.beginTransaction();
                int nbCorrections = calculateNbCorrection( previousAudit, pCurrentAudit );
                createROI( nbCorrections, application, pCurrentAudit );
                mSession.commitTransactionWithoutClose();
            }

        }
        catch ( JrafDaoException e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
    }

    /**
     * Méthode qui gère l'envoi de mail en fonction du résultat de l'audit
     * 
     * @param pStatus le status de l'audit
     * @param pInfos les informations concernant l'audit pour le mail
     * @param pApplicationId l'id de l'application concernée
     */
    private void manageResultMailing( int pStatus, String[] pInfos, long pApplicationId )
    {
        String dest = "";
        String object = "";
        String content = "";
        boolean sendMail = false;
        String sender = Messages.getString( "mail.sender.squalix" );
        String header = Messages.getString( "mail.header" );
        if ( pStatus == AuditBO.TERMINATED )
        {
            object = sender + Messages.getString( "mail.audit.terminated.object", pInfos );
            content = header + Messages.getString( "mail.audit.terminated.content", pInfos );
            dest = SqualeCommonConstants.MANAGERS_AND_READERS;
            sendMail = true;
        }
        else
        {
            // en cas d'échec, on prévient les administrateurs de SQUALE et
            // les managers de l'application concernée de l'échec
            if ( pStatus == AuditBO.FAILED )
            {
                object = sender + Messages.getString( "mail.audit.failed.object", pInfos );
                content = header + Messages.getString( "mail.audit.failed.content", pInfos );
                dest = SqualeCommonConstants.MANAGERS_AND_ADMINS;
                sendMail = true;
            }
            else
            {
                // en cas d'échec, on prévient les administrateurs de SQUALE et
                // les managers de l'application concernée de l'échec
                if ( pStatus == AuditBO.PARTIAL )
                {
                    object = sender + Messages.getString( "mail.audit.partial.object", pInfos );
                    content = header + Messages.getString( "mail.audit.partial.content", pInfos );
                    dest = SqualeCommonConstants.MANAGERS_AND_ADMINS;
                    sendMail = true;
                }
            }
        }
        // On envoie un email aux utilisateurs abonnés selon le statut de l'audit
        if ( sendMail )
        {
            // complément d'infos
            SqualeCommonUtils.notifyByEmail( mMailer, null, dest, new Long( pApplicationId ), object, content, false );
        }
    }

    /**
     * Sauvegarde le roi calculé en base
     * 
     * @param pValue le nombre de correction
     * @param pApplication l'application courante
     * @param pAudit l'audit courant
     * @throws JrafDaoException si erreur
     */
    public void createROI( int pValue, ApplicationBO pApplication, AuditBO pAudit )
        throws JrafDaoException
    {
        RoiMetricsBO roi = new RoiMetricsBO();
        roi.setNbCorrections( pValue );
        roi.setComponent( pApplication );
        roi.setAudit( pAudit );
        // Initialisation du DAO
        MeasureDAOImpl roiDAO = MeasureDAOImpl.getInstance();
        roiDAO.create( mSession, roi );
    }

    /**
     * Calcule le nombre de corrections pour l'audit courant pour le ROI
     * 
     * @param pPreviousAudit l'audit précédent
     * @param pCurrentAudit l'audit courant
     * @return le nombre de corrections faites entre les deux audits
     * @throws JrafDaoException si erreur
     */
    public int calculateNbCorrection( AuditBO pPreviousAudit, AuditBO pCurrentAudit )
        throws JrafDaoException
    {
        int nbProgressions = 0;
        int nbSuppressions = 0;
        int transgressionDiff = 0;
        if ( pCurrentAudit.getRealDate().before( pPreviousAudit.getRealDate() ) )
        {
            // On ne calcule pas le nombre de correction si il s'agit d'un audit de jalon intercalé
            LOGGER.info( CoreMessages.getString( "roi.not_calculated",
                                                 new Object[] { new Long( pCurrentAudit.getId() ) } ) );
        }
        else
        {
            /* On récupère le nombre de corrections faites entre les deux audits */
            Long auditId = new Long( pCurrentAudit.getId() );
            Long previousAuditId = new Long( pPreviousAudit.getId() );
            MarkDAOImpl markDao = MarkDAOImpl.getInstance();
            // Les composants qui sont passés de zéro à 1, 2 ou 3
            nbProgressions = markDao.findCorrectionsWithProgessions( mSession, auditId, previousAuditId );
            // Les composants qui étaient à zéro et qui n'existent plus
            nbSuppressions = markDao.findCorrectionsWithSuppressions( mSession, auditId, previousAuditId );
            // La progression du nombre de transgressions de niveau erreur et warning
            RuleCheckingTransgressionDAOImpl ruleDao = RuleCheckingTransgressionDAOImpl.getInstance();
            int nbTransgressions = ruleDao.findNbErrorAndWarning( mSession, auditId );
            int nbPreviousTransgressions = ruleDao.findNbErrorAndWarning( mSession, previousAuditId );
            transgressionDiff = nbPreviousTransgressions - nbTransgressions;
            if ( transgressionDiff <= 0 )
            {
                transgressionDiff = 0;
            }
        }
        return nbProgressions + nbSuppressions + transgressionDiff;
    }

    /**
     * Lance les tâches .
     * 
     * @param pAuditExec l'executeur d'audit .
     * @roseuid 42CE30D601DD
     */
    private void launchTasks( AuditExecutor pAuditExec )
    {
        // lance les taches de l'auditExecutor
        pAuditExec.run();
    }

    /**
     * @param pAudit audit d'applications à réaliser.
     * @return Retourne une liste d'executeur d'audits pour l'audit en parametre.<br>
     * @roseuid 42CE37D70124
     */
    private List getAuditExecutors( final AuditBO pAudit )
    {
        AuditExecutor ae = null;
        List aeList = new ArrayList( 0 );
        ProjectBO project = null;
        // La liste des taches à effectuer
        List analyze = new ArrayList( 0 );
        // La liste des taches de terminaison à effectuer dans tous les cas
        List termination = new ArrayList( 0 );
        // La liste des taches à effectuer pour récupérer les sources
        List analyzeSource = new ArrayList( 0 );
        // La liste des taches de terminaison à effectuer pour la récupération
        // de sources, comme le démontage de la vue clearcase...
        List terminationSource = new ArrayList( 0 );
        // La liste des taches de profil
        List analyzeProfil = new ArrayList( 0 );
        // La liste des taches de terminaison de profil à effectuer
        List terminationProfil = new ArrayList( 0 );
        try
        {
            // On récupère tous les projets à analyser pour cet audit
            mProjectsByAudit = getProjectsByAudit( pAudit );
            Iterator it = mProjectsByAudit.iterator();
            while ( it.hasNext() )
            {
                // Pour chaque projet, on crée un exécuteur d'audit,
                // gérant le type de récupérateur de source associé au projet et
                // dépendant du profil du projet
                project = (ProjectBO) it.next();
                analyzeSource = project.getSourceManager().getAnalysisTasks();
                terminationSource = project.getSourceManager().getTerminationTasks();
                String profile = project.getProfile().getName();
                analyzeProfil = project.getProfile().getAnalysisTasks();
                terminationProfil = project.getProfile().getTerminationTasks();
                // Il y a 2 types de taches (analyse ou terminale) combinable
                // avec 2 types de fonction (récupération de source ou profil)
                // il faut faire dans l'ordre:
                // * Taches d'analyse de récupération de source
                // * Taches d'analyse du profil
                // * Taches terminales du profil
                // * Taches terminales de récupération de source
                analyze.addAll( analyzeSource );
                analyze.addAll( analyzeProfil );
                termination.addAll( terminationProfil );
                termination.addAll( terminationSource );
                // Es-ce le dernier projet de l'audit  ?
                boolean lastProject = false;
                if ( !it.hasNext() )
                {
                    lastProject = true;
                }
                // creation de lauditexecutor associé
                ae = new AuditExecutor( analyze, termination, pAudit, project, lastProject );
                ae.setScheduler( this );
                aeList.add( ae );
                mAudits.add( ae );
                analyze = new ArrayList( 0 );
                termination = new ArrayList( 0 );
            }
        }
        catch ( Exception e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
            pAudit.setStatus( AuditBO.FAILED );
        }
        return aeList;
    }

    /**
     * Réalise les tâches de niveau application (création des graphes, calculs,...)
     * 
     * @param pCurrentAudit l'audit courant.
     * @param pApplicationBO l'application sur lequel calculer les résultats.
     * @roseuid 42CE40E102B8
     */
    private void createNewAudit( final AuditBO pCurrentAudit, final ApplicationBO pApplicationBO )
    {
        ApplicationBO application = null;
        // on ne crée pas de nouvelles transactions car il faut faut que la transaction
        // pour cette méthode soit la meme que pour la méthode appelante de cette méthode
        try
        {
            Long applicationId = new Long( pApplicationBO.getId() );
            application = (ApplicationBO) ApplicationDAOImpl.getInstance().get( mSession, applicationId );
            GregorianCalendar cal = new GregorianCalendar();
            // Création d'un nouvel audit si il s'agit d'un audit de suivi
            if ( pCurrentAudit.getType().equals( AuditBO.NORMAL ) )
            {
                // Création d'un nouvel audit
                AuditBO audit = new AuditBO();
                // Ajout de la fréquence d'audit à la date courante
                cal.add( Calendar.DATE, pApplicationBO.getAuditFrequency() );
                audit.setDate( cal.getTime() );
                audit.setStatus( AuditBO.NOT_ATTEMPTED );
                audit.setType( pCurrentAudit.getType() );
                AuditDAOImpl.getInstance().create( mSession, audit );
                // Ajout du nouvel audit à l'application
                application.addAudit( audit );
                ApplicationDAOImpl.getInstance().save( mSession, application );
            }
        }
        catch ( Exception e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
    }

    /**
     * enregistre l'audit courant
     * 
     * @param pCurrentAudit l'audit courant
     * @param pApplicationBO l'application liée à l'audit
     */
    private void saveCurrentAudit( final AuditBO pCurrentAudit, final ApplicationBO pApplicationBO )
    {
        ApplicationBO application = null;
        try
        {
            Long applicationId = new Long( pApplicationBO.getId() );
            mSession.beginTransaction();
            application = (ApplicationBO) ApplicationDAOImpl.getInstance().get( mSession, applicationId );
            Long auditId = new Long( pCurrentAudit.getId() );
            GregorianCalendar cal = new GregorianCalendar();
            AuditBO finishedAudit = (AuditBO) AuditDAOImpl.getInstance().get( mSession, auditId );
            // Remet à jour la date réelle de l'audit
            finishedAudit.setDate( Calendar.getInstance().getTime() );
            finishedAudit.setStatus( pCurrentAudit.getStatus() );
            // sauvegarde l'audit
            AuditDAOImpl.getInstance().save( mSession, finishedAudit );
            // on commit pour sauvegarder le status de l'audit
            mSession.commitTransactionWithoutClose();
            mSession.beginTransaction();
            // Ajoute l'audit réussit dans le Referenciel
            manageSqualeReference( finishedAudit, pApplicationBO.getName(), pApplicationBO.getId() );
            mSession.commitTransactionWithoutClose();

        }
        catch ( Exception e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
    }

    /**
     * @param pAudit l'audit courant terminé
     * @param pAppliName le nom de l'application recherché
     * @param pAppliId l'id de l'application pour la transformation
     * @throws JrafEnterpriseException en cas d'échec de sauvegarde de l'outil
     */
    private void manageSqualeReference( AuditBO pAudit, String pAppliName, long pAppliId )
        throws JrafEnterpriseException
    {
        boolean insert = false;
        SqualeReferenceDTO storedAudit = SqualeReferenceFacade.getReferencedAudit( pAppliName, mSession );
        // Les différentes règles suivantes sont priorisées
        // 1) On ne peut ajouter au référentiel que des audits terminés
        // 2) Si aucun audit n'est déjà présent en base, on insère le courant de toute facon
        // 3) les audits de jalon sont prioritaires
        // 4) On garde l'audit le plus récent
        if ( pAudit.getStatus() == AuditBO.TERMINATED
            && ( null == storedAudit || ( ( AuditBO.NORMAL.equals( storedAudit.getAuditType() ) && AuditBO.MILESTONE.equals( pAudit.getType() ) ) || ( storedAudit.getAuditType().equals(
                                                                                                                                                                                          pAudit.getType() ) && pAudit.getDate().getTime() > storedAudit.getDate().getTime() ) ) ) )
        {
            SqualeReferenceFacade.insertAudit( AuditTransform.bo2Dto( pAudit, pAppliId ), mSession );
        }
    }

    /**
     * Administration de la base de donnée pour Oracle gestion des tables partitionnées...
     * 
     * @param pStopTime la limite de temps pour l'exécution des audits
     * @return un booléen indiquant si on peut continuer l'exécution normale
     * @throws JrafDaoException en cas de problème lors de la récupération ou de la sauvegarde des données
     * @throws InterruptedException en cas d'interruption inopiné du thread
     */
    private boolean admindb( StopTimeHelper pStopTime )
        throws InterruptedException, JrafDaoException
    {
        boolean result = true;
        String object = "";
        String content = "";
        String sender = Messages.getString( "mail.sender.squalix" );
        String header = Messages.getString( "mail.header" );
        // sur le site maitre
        if ( Messages.getString( "admin.mastersite" ).equalsIgnoreCase( mSiteId + "" ) )
        {
            // verifie qu'il y a une requete d'admin
            String sql = Messages.getString( "admin.sql.query" );
            if ( !sql.startsWith( "?" ) )
            {
                // et la lance
                try
                {
                    // execute la commande sql d'admin de la base
                    ( (SessionImpl) mSession ).getSession().connection().prepareCall( sql ).execute();
                }
                catch ( HibernateException e )
                {
                    // en cas d'exception, log juste les erreurs
                    LOGGER.error( CoreMessages.getString( "exception" ), e );
                }
                catch ( SQLException e )
                {
                    LOGGER.error( CoreMessages.getString( "exception" ), e );
                }
            }
        }
        else
        {
            // le délai d'attente entre 2 tests
            // une demi-heure
            final int waitDelay = 1800000;
            // on n'est pas le site maitre, on doit attendre que le script de rotation soit exécutée
            // avant de pouvoir lancer les audits
            while ( isRotationDay() && !pStopTime.isTimeToStop() )
            {
                sleep( waitDelay );
            }
            // Si on est sortis parce que on a dépassé le stopTime, on arrete pour de bon
            // on envoie un mail aux administrateurs
            if ( pStopTime.isTimeToStop() )
            {
                result = false;
                object = sender + Messages.getString( "mail.audits.notDone.object" );
                content = header + Messages.getString( "mail.audits.notDone.content" );
                SqualeCommonUtils.notifyByEmail( mMailer, null, SqualeCommonConstants.ONLY_ADMINS, null, object,
                                                 content, false );
            } // sinon le script de rotation a été exécutée, on peut continuer normalement
        }
        // le script de rotation a été bien lancée, on avertit juste les administrateurs
        // et on change la date de l'audit avec un délai de 12 semaines.
        if ( result )
        {
            object = sender + Messages.getString( "mail.rotation.done.object" );
            content = header + Messages.getString( "mail.rotation.one.content" );
            SqualeCommonUtils.notifyByEmail( mMailer, null, SqualeCommonConstants.ONLY_ADMINS, null, object, content,
                                             false );
            AuditDAOImpl.getInstance().reportRotationAudit( mSession );
        }
        return result;
    }

}
