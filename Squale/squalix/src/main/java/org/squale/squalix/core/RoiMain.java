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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.bootstrap.initializer.Initializer;
import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MarkDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.result.rulechecking.RuleCheckingTransgressionDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.roi.RoiMetricsBO;

/**
 * Calcule tous les ROI pour les audits terminés (Pour exécuter le main sous unix mettre son nom dans le manifest.mf
 * dans Main-Class)
 */
public class RoiMain
{

    /**
     * Logger
     */
    private static Log mLOGGER = null;

    /**
     * Sauvegarde le roi calculé en base
     * 
     * @param pSession la session
     * @param pValue le nombre de correction
     * @param pApplication l'application courante
     * @param pAudit l'audit courant
     * @throws JrafDaoException si erreur
     */
    private static void createROI( ISession pSession, int pValue, ApplicationBO pApplication, AuditBO pAudit )
        throws JrafDaoException
    {
        RoiMetricsBO roi = new RoiMetricsBO();
        roi.setNbCorrections( pValue );
        roi.setComponent( pApplication );
        roi.setAudit( pAudit );
        // Initialisation du DAO
        MeasureDAOImpl roiDAO = MeasureDAOImpl.getInstance();
        roiDAO.create( pSession, roi );
    }

    /**
     * Calcule le nombre de corrections pour l'audit courant pour le ROI
     * 
     * @param pSession la session
     * @param pPreviousAudit l'audit précédent
     * @param pCurrentAudit l'audit courant
     * @param pLogger le logger
     * @return le nombre de corrections faites entre les deux audits
     * @throws JrafDaoException si erreur
     */
    public static int calculateNbCorrection( ISession pSession, AuditBO pPreviousAudit, AuditBO pCurrentAudit,
                                             Log pLogger )
        throws JrafDaoException
    {
        int nbProgressions = 0;
        int nbSuppressions = 0;
        int transgressionDiff = 0;
        // En cas d'appel depuis une méthode extérieur
        if ( mLOGGER == null )
        {
            mLOGGER = pLogger;
        }
        /* On récupère le nombre de corrections faites entre les deux audits */
        Long auditId = new Long( pCurrentAudit.getId() );
        Long previousAuditId = new Long( pPreviousAudit.getId() );
        MarkDAOImpl markDao = MarkDAOImpl.getInstance();
        // Les composants qui sont passés de zéro à 1, 2 ou 3
        nbProgressions = markDao.findCorrectionsWithProgessions( pSession, auditId, previousAuditId );
        mLOGGER.info( "Nombre de progressions = " + nbProgressions );
        // Les composants qui étaient à zéro et qui n'existent plus
        nbSuppressions = markDao.findCorrectionsWithSuppressions( pSession, auditId, previousAuditId );
        mLOGGER.info( "Nombre de suppressions = " + nbSuppressions );
        // La progression du nombre de transgressions de niveau erreur et warning
        RuleCheckingTransgressionDAOImpl ruleDao = RuleCheckingTransgressionDAOImpl.getInstance();
        int nbTransgressions = ruleDao.findNbErrorAndWarning( pSession, auditId );
        int nbPreviousTransgressions = ruleDao.findNbErrorAndWarning( pSession, previousAuditId );
        transgressionDiff = nbPreviousTransgressions - nbTransgressions;
        mLOGGER.info( "Nombre de transgressions = " + transgressionDiff );
        if ( transgressionDiff <= 0 )
        {
            transgressionDiff = 0;
        }
        return nbProgressions + nbSuppressions + transgressionDiff;
    }

    /**
     * Calcul de tous les roi
     * 
     * @param pArgs arguments de lancement.
     * @throws JrafPersistenceException si erreur
     */
    public static void main( String pArgs[] )
        throws JrafPersistenceException
    {
        // lancement de l’initialisation JRAF
        String rootPath = pArgs[0];
        String configFile = "/config/providers-config.xml";
        Initializer init = new Initializer( rootPath, configFile );
        init.initialize();
        // Maintenant que le socle JRAF est initialisé, on peut créer un logger
        mLOGGER = LogFactory.getLog( RoiMain.class );
        ISession session = null;
        try
        {
            session = PersistenceHelper.getPersistenceProvider().getSession();
            // Instanciation des DAO
            AuditDAOImpl auditDAO = AuditDAOImpl.getInstance();
            ApplicationDAOImpl appliDAO = ApplicationDAOImpl.getInstance();
            MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
            // On récupère tous les audits terminés
            List audits = auditDAO.findWhereStatus( session, AuditBO.TERMINATED );
            // Pour chaque audit, on récupère son précédent et on calcule le nombre de corrections
            AuditBO currentAudit;
            ApplicationBO currentApplication;
            AuditBO previousAudit;
            Long auditId;
            for ( int i = 0; i < audits.size(); i++ )
            {
                currentAudit = (AuditBO) audits.get( i );
                auditId = new Long( currentAudit.getId() );
                // On charge l'application associée
                currentApplication = appliDAO.loadByAuditId( session, auditId );
                // Si il n'y a pas de mesure de ROI déjà calculée pour cette application
                if ( null == measureDAO.load( session, new Long( currentApplication.getId() ), auditId,
                                              RoiMetricsBO.class ) )
                {
                    // On récupère l'audit précédent
                    previousAudit =
                        auditDAO.findPreviousAudit( session, currentAudit.getId(), currentAudit.getHistoricalDate(),
                                                    currentApplication.getId(), null );
                    if ( null != previousAudit )
                    {
                        // On calcule le nombre de corrections réalisé entre les deux audits
                        int nbCorrections = calculateNbCorrection( session, previousAudit, currentAudit, null );
                        mLOGGER.info( "nbCorrections = " + nbCorrections + " pour l'application \""
                            + currentApplication.getName() + "\" de l'audit n° " + previousAudit.getId()
                            + " à l'audit n° " + currentAudit.getId() );
                        // Création de la mesure
                        createROI( session, nbCorrections, currentApplication, currentAudit );
                    }
                }
            }

        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
        finally
        {
            if ( null != session )
            {
                session.closeSession();
            }
        }
    }

}
