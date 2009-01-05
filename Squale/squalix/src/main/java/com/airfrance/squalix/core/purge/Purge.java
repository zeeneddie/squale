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
/*
 * Created on 3 août 06
 *
 */
package com.airfrance.squalix.core.purge;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.config.web.AbstractDisplayConfDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalix.core.CoreMessages;

/**
 * Thread séparée de purge
 * @author M401540 
 */
public class Purge
    extends Thread
{

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( Purge.class );

    /**
     * Site
     */
    private long mSiteId;

    /**
     * La liste des applications qui vont être auditées en même temps que la purge. Pour éviter d'avoir des croisements
     * de session, on ne va pas purger les audits qui rattaché à une de ces applications
     */
    private Set mForbiddenApplis = new HashSet();

    /**
     * Fichier de configuration de la purge.
     */
    private final String mPurgeConfigFile = "config/purge.xml";

    /**
     * Analyseur de fichier de configuration de la purge d'audits.
     */
    private final PurgeConfParser mPurgeConfParser = new PurgeConfParser();

    /**
     * Constructeur
     * 
     * @param pSiteId site de lancement
     * @param pRunningAudits les audits qui vont être lancés
     */
    public Purge( long pSiteId, List pRunningAudits )
    {
        mSiteId = pSiteId;
        configPurge();
        setForbiddenApplis( pRunningAudits );
    }

    /**
     * Lance le Thread de purge des applications et des audits
     */
    public void run()
    {
        try
        {
            // ouvre une session Hibernate
            ISession session = PersistenceHelper.getPersistenceProvider().getSession();
            // purge des audits supprimés ou obsolètes à supprimer
            purgeAudits( session );

            // On va purger les configurations d'affichage qui ne servent plus
            // i.e. qui ne sont plus rattachées ni à un audit, ni a un profil
            session.beginTransaction();
            AbstractDisplayConfDAOImpl.getInstance().removeUnusedConf( session );
            session.commitTransactionWithoutClose();
            // maintenant que tous les audits sont supprimés, on peut supprimer les applis...
            session.beginTransaction();
            Iterator it = ApplicationDAOImpl.getInstance().findWhereStatus( session, ApplicationBO.DELETED ).iterator();
            session.commitTransactionWithoutClose();
            while ( it.hasNext() )
            {
                ApplicationBO a = (ApplicationBO) it.next();
                // du site
                if ( a.getServeurBO() == null || a.getServeurBO().getServeurId() == mSiteId )
                {
                    // supresion physique comme pour les audits
                    // Commit un par un pour des raisons de perf
                    session.beginTransaction();
                    ApplicationDAOImpl.getInstance().remove( session, (Object) a );
                    session.commitTransactionWithoutClose();
                }
            }
            // maintenant que toutes les applis sont supprimées, on peut supprimer les projets...
            session.beginTransaction();
            Collection projects =
                ProjectDAOImpl.getInstance().findWhereStatusAndSite( session, ProjectBO.DELETED, mSiteId );
            it = projects.iterator();
            LOGGER.info( CoreMessages.getString( "projects.todelete", new Object[] { new Integer( projects.size() ) } ) );
            session.commitTransactionWithoutClose();
            while ( it.hasNext() )
            {
                ProjectBO p = (ProjectBO) it.next();
                // supresion physique comme pour les audits
                // Commit un par un pour des raisons de perf
                session.beginTransaction();
                ProjectDAOImpl.getInstance().remove( session, (Object) p );
                session.commitTransactionWithoutClose();
            }
            // ferme la session
            session.closeSession();
        }
        catch ( JrafPersistenceException e )
        {
            LOGGER.error( e, e );
        }
        catch ( JrafDaoException e )
        {
            LOGGER.error( e, e );
        }
    }

    /**
     * Purge les audits supprimés ou obsolètes au regard de la fréquence de purge de l'application. <br />
     * Pour les audits de jalon, seuls les audits en échec sont supprimés. <br />
     * Pour les audits de suivi, un minimum d'audits obsolètes sont conservés pour historique (selon configuration).
     * <br />
     * Un maximum donné d'audits sont supprimés (selon configuration). Pré-condition : la liste des audits est triée par
     * date décroissante.
     * 
     * @param pSession session de persistence.
     * @throws JrafDaoException si exception de persistence.
     */
    private void purgeAudits( ISession pSession )
        throws JrafDaoException
    {
        int lNbAuditsDeleted = 0;

        // suppression des audits supprimés qui n'appartiennent pas à une application
        // auditée dans un thread parallèle
        Iterator it = AuditDAOImpl.getInstance().findDeleted( pSession, mSiteId, mForbiddenApplis ).iterator();
        while ( it.hasNext() && lNbAuditsDeleted < mPurgeConfParser.getMaxAuditsToDelete().intValue() )
        {
            AuditBO a = (AuditBO) it.next();
            // Commit un par un pour des raisons de perf/charge
            purgeOneAudit( pSession, a );
            lNbAuditsDeleted++;
            LOGGER.info( CoreMessages.getString( "purge.info.deleted", new Object[] { new Long( a.getId() ) } ) );
        }

        // traitement des audits obsolètes à supprimer
        List lAuditsList = AuditDAOImpl.getInstance().findObsoleteAuditsToDelete( pSession, mSiteId, mForbiddenApplis );
        List lAuditsToDelete = new ArrayList();
        List lTreatedApplicationsList = new ArrayList();
        it = lAuditsList.iterator();
        // les derniers audits obsolètes de suivi d'une application sont conservés
        while ( it.hasNext() )
        {
            Object[] lListItem = (Object[]) it.next();
            AuditBO lAuditBO = (AuditBO) lListItem[0];
            ApplicationBO lApplicationBO = (ApplicationBO) lListItem[1];
            if ( !lTreatedApplicationsList.contains( new Long( lApplicationBO.getId() ) ) )
            {
                // copie des audits obsolètes de suivi à ne pas conserver
                Iterator it2 = lAuditsList.iterator();
                int lNbAuditsKept = 0;
                while ( it2.hasNext() )
                {
                    Object[] lListItem2 = (Object[]) it2.next();
                    AuditBO lAuditBO2 = (AuditBO) lListItem2[0];
                    ApplicationBO lApplicationBO2 = (ApplicationBO) lListItem2[1];
                    if ( lApplicationBO.getId() == lApplicationBO2.getId() )
                    {
                        if ( lAuditBO2.getType().equals( AuditBO.NORMAL )
                            && lNbAuditsKept < mPurgeConfParser.getMinObsoleteAuditsToKeep().intValue() )
                        {
                            lNbAuditsKept++;
                        }
                        else
                        {
                            lAuditsToDelete.add( lAuditBO2 );
                        }
                    }
                }
                // les audits de l'application ont été traités
                lTreatedApplicationsList.add( new Long( lApplicationBO.getId() ) );
            }
        }
        // suppression des audits obsolètes à supprimer (ordre chronologique)
        final int lNbAuditsToDelete = lAuditsToDelete.size();
        int i = lNbAuditsToDelete - 1;
        while ( i >= 0 && lNbAuditsDeleted < mPurgeConfParser.getMaxAuditsToDelete().intValue() )
        {
            AuditBO lAuditBO = (AuditBO) lAuditsToDelete.get( i );
            purgeOneAudit( pSession, lAuditBO );
            lNbAuditsDeleted++;
            i--;
            LOGGER.info( CoreMessages.getString( "purge.info.obsolete", new Object[] { new Long( lAuditBO.getId() ) } ) );
        }
    }

    /**
     * Supprime un audit.
     * 
     * @param pSession session de persistence.
     * @param pAuditBO audit à supprimer.
     * @throws JrafDaoException exception de persistence.
     */
    private void purgeOneAudit( ISession pSession, AuditBO pAuditBO )
        throws JrafDaoException
    {
        // supprime vraiment l'audit (et tous le reste avec le delete cascade)
        // en appelant la methode remove Object (et no AuditBO qui ne realise
        // que le delete logique
        pSession.beginTransaction();
        AuditDAOImpl.getInstance().remove( pSession, (Object) pAuditBO );
        pSession.commitTransactionWithoutClose();
    }

    /**
     * Configure la purge.
     */
    private void configPurge()
    {
        try
        {
            mPurgeConfParser.parse( new FileInputStream( mPurgeConfigFile ) );
            // suppression de la contrainte de maximum d'audits
            if ( mPurgeConfParser.getMaxAuditsToDelete().intValue() < 0 )
            {
                mPurgeConfParser.setMaxAuditsToDelete( new Integer( Integer.MAX_VALUE ) );
            }
        }
        catch ( Exception e )
        {
            // ré-initialisation de la configuration de purge
            mPurgeConfParser.setMinObsoleteAuditsToKeep( new Integer( 0 ) );
            mPurgeConfParser.setMaxAuditsToDelete( new Integer( 0 ) );
            LOGGER.error( CoreMessages.getString( "purge.config.error.1" ) );
            LOGGER.error( CoreMessages.getString( "purge.config.error.2" ) );
        }
    }

    /**
     * Créer l'ensemble des ids des applications auditées en même temps que la purge
     * 
     * @param pRunningAudits les audits qui peuvent être audités en même temps que la purge
     */
    private void setForbiddenApplis( List pRunningAudits )
    {
        try
        {
            // ouvre une session Hibernate
            ISession session = PersistenceHelper.getPersistenceProvider().getSession();
            ApplicationBO curAppli;
            AuditBO curAudit;
            for ( int i = 0; i < pRunningAudits.size(); i++ )
            {
                curAudit = (AuditBO) pRunningAudits.get( i );
                curAppli = ApplicationDAOImpl.getInstance().loadByAuditId( session, new Long( curAudit.getId() ) );
                // On ajoute son id au set
                mForbiddenApplis.add( new Long( curAppli.getId() ) );
            }
            // ferme la session
            session.closeSession();
        }
        catch ( JrafPersistenceException e )
        {
            LOGGER.error( e, e );
        }
        catch ( JrafDaoException e )
        {
            LOGGER.error( e, e );
        }
    }
}
