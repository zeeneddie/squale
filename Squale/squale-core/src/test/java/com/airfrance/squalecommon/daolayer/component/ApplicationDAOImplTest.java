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
package com.airfrance.squalecommon.daolayer.component;

import java.util.Collection;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

/**
 * @author M400843
 */
public class ApplicationDAOImplTest
    extends SqualeTestCase
{

    /**
     * test la suppression d'une application
     * 
     * @throws JrafDaoException en cas d'échec
     */
    public void testRemove()
        throws JrafDaoException
    {
        ApplicationDAOImpl applicationDAO = ApplicationDAOImpl.getInstance();
        ISession session = getSession();
        // on utilise une transaction pour créer l'application
        session.beginTransaction();
        ApplicationBO application = getComponentFactory().createApplication( session );
        assertEquals( ApplicationBO.IN_CREATION, application.getStatus() );

        String name = application.getName();
        session.commitTransactionWithoutClose();

        // on vérifie qu'on peut bien charger l'application qu'on vient de créer
        session.beginTransaction();
        assertNotNull( applicationDAO.loadByName( session, name ) );
        session.commitTransactionWithoutClose();

        // et une autre pour tester la supression
        session.beginTransaction();
        applicationDAO.remove( session, application );
        session.commitTransactionWithoutClose();

        // une dernière pour vérifier que l'application a bien été supprimée
        session.beginTransaction();
        application = applicationDAO.loadByName( session, name );
        assertEquals( ApplicationBO.DELETED, application.getStatus() );
        session.commitTransactionWithoutClose();
    }

    /**
     * Test le comptage d'applications ayant eu des audits (sans status particulier)
     * 
     * @throws JrafDaoException en cas d'échec
     */
    public void testCountWhereHaveAudit()
        throws JrafDaoException
    {
        // la date n'a pas d'importance, car on ne peut pas tester sur la date, to_date est réservé à Oracle
        ApplicationDAOImpl appliDao = ApplicationDAOImpl.getInstance();
        AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
        // on crée les différents éléments
        // On crée les serveurs
        ServeurBO qvg = getComponentFactory().createServer( getSession(), "qvg" );
        ServeurBO tls = getComponentFactory().createServer( getSession(), "tls" );
        ApplicationBO appli = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        ProjectBO proj = getComponentFactory().createProject( getSession(), appli, null );
        AuditBO audit = getComponentFactory().createAudit( getSession(), proj );
        // l'audit est crée pour un projet, il faut rajouter l'audit à l'application mère
        appli.addAudit( audit );
        // enregistre explicitement en base car ce n'est pas une méthode de la factory
        getSession().beginTransaction();
        appliDao.save( getSession(), appli );
        getSession().commitTransactionWithoutClose();
        // Vérifie que l'audit a bien été enregistré
        getSession().beginTransaction();
        AuditBO auditResult = (AuditBO) auditDao.findAll( getSession() ).get( 0 );
        getSession().commitTransactionWithoutClose();
        // l'audit qu'on récupère doit etre le meme que celui crée
        assertEquals( auditResult, audit );
        // une seule application a été crée, sur QVI
        int nbAppliQVI =
            appliDao.countWhereHaveAuditByStatus( getSession(), appli.getServeurBO().getServeurId(),
                                                  new Integer( AuditBO.ALL_STATUS ), null );
        assertTrue( nbAppliQVI == 1 );
        int nbAppliQVG =
            appliDao.countWhereHaveAuditByStatus( getSession(), qvg.getServeurId(), new Integer( AuditBO.ALL_STATUS ),
                                                  null );
        assertTrue( nbAppliQVG == 0 );
        int nbAppliTLS =
            appliDao.countWhereHaveAuditByStatus( getSession(), tls.getServeurId(), new Integer( AuditBO.ALL_STATUS ),
                                                  null );
        assertTrue( nbAppliTLS == 0 );
    }

    /**
     * Test le comptage d'applications validées ayant eu aucun audit exécuté
     * 
     * @throws JrafDaoException en cas d'échec
     */
    public void testCountWhereHaveNoAudit()
        throws JrafDaoException
    {
        ApplicationDAOImpl appliDao = ApplicationDAOImpl.getInstance();
        AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
        // on crée les différents éléments
        ApplicationBO appli = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        appli.setStatus( ApplicationBO.VALIDATED );
        ProjectBO proj = getComponentFactory().createProject( getSession(), appli, null );
        AuditBO audit = getComponentFactory().createAudit( getSession(), proj ); // en attente
        // l'audit est crée pour un projet, il faut rajouter l'audit à l'application mère
        appli.addAudit( audit );
        // enregistre explicitement en base car ce n'est pas une méthode de la factory
        getSession().beginTransaction();
        appliDao.save( getSession(), appli );
        getSession().commitTransactionWithoutClose();
        // Vérifie que l'audit a bien été enregistré
        getSession().beginTransaction();
        AuditBO auditResult = (AuditBO) auditDao.findAll( getSession() ).get( 0 );
        getSession().commitTransactionWithoutClose();
        // l'audit qu'on récupère doit etre le meme que celui crée
        assertEquals( auditResult, audit );
        // une seule application a été crée, sur QVI
        int nbAppliQVI = appliDao.countWhereHaveNoAudits( getSession(), appli.getServeurBO().getServeurId() );
        assertTrue( nbAppliQVI == 1 );
        // On ajoute un audit exécuté
        AuditBO executedAudit =
            getComponentFactory().createAuditWithStatus( getSession(), proj, new Integer( AuditBO.TERMINATED ) ); // terminé
        // l'audit est crée pour un projet, il faut rajouter l'audit à l'application mère
        appli.addAudit( executedAudit );
        // enregistre explicitement en base car ce n'est pas une méthode de la factory
        getSession().beginTransaction();
        appliDao.save( getSession(), appli );
        getSession().commitTransactionWithoutClose();
        // Vérifie que l'audit a bien été enregistré
        getSession().beginTransaction();
        // L'application a maintenant un audit éxécuté
        nbAppliQVI = appliDao.countWhereHaveNoAudits( getSession(), appli.getServeurBO().getServeurId() );
        assertEquals( 0, nbAppliQVI );

    }

    /**
     * Test le comptage d'applications ayant eu des audits (avec un status donné)
     * 
     * @throws JrafDaoException en cas d'échec
     */
    public void testCountWhereHaveAuditTerminated()
        throws JrafDaoException
    {
        // la date n'a pas d'importance, car on ne peut pas tester sur la date, to_date est réservé à Oracle
        ApplicationDAOImpl appliDao = ApplicationDAOImpl.getInstance();
        AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
        // on crée les différents éléments
        getSession().beginTransaction();
        ApplicationBO appli1 = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        appli1.setStatus( ApplicationBO.VALIDATED );
        getSession().commitTransactionWithoutClose();
        getSession().beginTransaction();
        ApplicationBO appli2 = getComponentFactory().createApplicationWithSite( getSession(), "qvg" );
        appli2.setStatus( ApplicationBO.VALIDATED );
        getSession().commitTransactionWithoutClose();
        getSession().beginTransaction();
        ApplicationBO appli3 = getComponentFactory().createApplicationWithSite( getSession(), "tls" );
        appli3.setStatus( ApplicationBO.VALIDATED );
        getSession().commitTransactionWithoutClose();
        getSession().beginTransaction();
        Collection applis = appliDao.findAll( getSession() );
        getSession().commitTransactionWithoutClose();
        assertTrue( applis.size() == 3 );
        ProjectBO proj1QviAppli1 = getComponentFactory().createProject( getSession(), appli1, null );
        ProjectBO proj1QvgAppli2 = getComponentFactory().createProject( getSession(), appli2, null );
        ProjectBO proj1TlsAppli3 = getComponentFactory().createProject( getSession(), appli3, null );
        // les audits rattachés à des applis de qvi
        AuditBO auditTerminated1 =
            getComponentFactory().createAuditWithStatus( getSession(), proj1QviAppli1, new Integer( AuditBO.TERMINATED ) );
        // les audits rattachés à des applis de tls
        AuditBO auditFailed2 =
            getComponentFactory().createAuditWithStatus( getSession(), proj1QvgAppli2, new Integer( AuditBO.FAILED ) );
        // les audits rattachés à des applis de qvg
        AuditBO auditNotAttempted2 =
            getComponentFactory().createAuditWithStatus( getSession(), proj1TlsAppli3,
                                                         new Integer( AuditBO.NOT_ATTEMPTED ) );
        // l'audit est crée pour un projet, il faut rajouter l'audit à l'application mère
        // Il y a 1 application sur qvi, avec un audit réussi
        // une application sur tls, avec un audit failed
        // une sur qvg, avec un audit programmé
        appli1.addAudit( auditTerminated1 );
        appli2.addAudit( auditFailed2 );
        appli3.addAudit( auditNotAttempted2 );
        // enregistre explicitement en base car ce n'est pas une méthode de la factory
        getSession().beginTransaction();
        appliDao.save( getSession(), appli1 );
        getSession().commitTransactionWithoutClose();
        // Vérifie que l'audit a bien été enregistré
        getSession().beginTransaction();
        AuditBO auditResult = (AuditBO) auditDao.findAll( getSession() ).get( 0 );
        getSession().commitTransactionWithoutClose();
        // une seule application avec un audit réussi a été crée, sur QVI
        int nbAppliQVITerm =
            appliDao.countWhereHaveAuditByStatus( getSession(), appli1.getServeurBO().getServeurId(),
                                                  new Integer( AuditBO.TERMINATED ), null );
        assertTrue( nbAppliQVITerm == 1 );
        int nbAppliQVGTerm =
            appliDao.countWhereHaveAuditByStatus( getSession(), appli2.getServeurBO().getServeurId(),
                                                  new Integer( AuditBO.TERMINATED ), null );
        assertTrue( nbAppliQVGTerm == 0 );
        int nbAppliTLSTerm =
            appliDao.countWhereHaveAuditByStatus( getSession(), appli3.getServeurBO().getServeurId(),
                                                  new Integer( AuditBO.TERMINATED ), null );
        assertTrue( nbAppliTLSTerm == 0 );
        int nbAppliQVI = appliDao.countWhereHaveOnlyFailedAudit( getSession(), appli1.getServeurBO().getServeurId() );
        assertTrue( nbAppliQVI == 0 );
        int nbAppliQVG = appliDao.countWhereHaveOnlyFailedAudit( getSession(), appli2.getServeurBO().getServeurId() );
        assertTrue( nbAppliQVG == 1 );
        int nbAppliTLS = appliDao.countWhereHaveOnlyFailedAudit( getSession(), appli3.getServeurBO().getServeurId() );
        assertTrue( nbAppliTLS == 0 );
        Collection applisQvi =
            appliDao.findWhereHaveNotAttemptedAuditBySite( getSession(), appli1.getServeurBO().getServeurId() );
        assertEquals( 0, applisQvi.size() );
    }

    /**
     * Test la récupération des applications avec un audits de suivi en attente
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindWhereHaveNotAttemptedAuditBySite()
        throws JrafDaoException
    {
        // Initialisation DAO
        ApplicationDAOImpl appliDao = ApplicationDAOImpl.getInstance();

        // Créationa appli sur toulouse avec un audit en attente
        ApplicationBO appliTls = getComponentFactory().createApplicationWithSite( getSession(), "tls" );
        appliTls.setStatus( ApplicationBO.VALIDATED );
        appliTls.setAuditFrequency( 2 );
        ProjectBO projTls = getComponentFactory().createProject( getSession(), appliTls, null );
        AuditBO auditNotAttempted =
            getComponentFactory().createAuditWithStatus( getSession(), projTls, new Integer( AuditBO.NOT_ATTEMPTED ) );
        appliTls.addAudit( auditNotAttempted );

        // Création appli sur vilgénis avec un audit en échec
        ApplicationBO appliQvg = getComponentFactory().createApplicationWithSite( getSession(), "qvg" );
        appliQvg.setStatus( ApplicationBO.VALIDATED );
        ProjectBO projQvg = getComponentFactory().createProject( getSession(), appliQvg, null );
        AuditBO auditFailed =
            getComponentFactory().createAuditWithStatus( getSession(), projQvg, new Integer( AuditBO.FAILED ) );
        appliQvg.addAudit( auditFailed );

        // enregistre en base
        getSession().beginTransaction();
        appliDao.save( getSession(), appliTls );
        getSession().commitTransactionWithoutClose();
        // 1 appli pour toulouse, 0 pour vilgénis
        Collection applisTls =
            appliDao.findWhereHaveNotAttemptedAuditBySite( getSession(), appliTls.getServeurBO().getServeurId() );
        assertEquals( 1, applisTls.size() );
        Collection applisQvg =
            appliDao.findWhereHaveNotAttemptedAuditBySite( getSession(), appliQvg.getServeurBO().getServeurId() );
        assertEquals( 0, applisQvg.size() );
    }

    /**
     * @throws JrafDaoException si erreur
     */
    public void testFindWhereStatus()
        throws JrafDaoException
    {
        ApplicationDAOImpl appliDao = ApplicationDAOImpl.getInstance();
        AuditDAOImpl auditDao = AuditDAOImpl.getInstance();
        // on crée les différents éléments
        getSession().beginTransaction();
        ApplicationBO appli1 = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        ApplicationBO appli2 = getComponentFactory().createApplicationWithSite( getSession(), "QVG" );
        ApplicationBO appli3 = getComponentFactory().createApplicationWithSite( getSession(), "QVI" );
        getSession().commitTransactionWithoutClose();
        List applisInCreation = appliDao.findWhereStatus( getSession(), ApplicationBO.IN_CREATION );
        final int nbApplis = 3;
        assertEquals( nbApplis, applisInCreation.size() );
        // La première doit être applicationqvg
        assertEquals( "applicationQVG", ( (ApplicationBO) applisInCreation.get( 0 ) ).getName() );
    }
}
