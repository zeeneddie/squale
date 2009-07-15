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
package org.squale.squalix.core;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.AuditDAOImpl;
import org.squale.squalecommon.daolayer.component.AuditDisplayConfDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.result.MeasureDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditDisplayConfBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.web.BubbleConfBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.web.DisplayConfConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.config.web.VolumetryConfBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.jspvolumetry.JSPVolumetryProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.rsm.RSMProjectMetricsBO;

/**
 * Pour exécuter ce test, il faut changer le statut privé des méthodes en public
 */
public class AuditConfMigrationLot3_3Test
    extends SqualeTestCase
{

    /**
     * Test la créetion des liens audits-configurations
     */
    public void testCreateAuditDisplayConfs()
    {
        try
        {
            getSession().beginTransaction();
            // On crée les configurations
            AuditConfMigrationLot3_3.createoldConfigurations( getSession() );
            // On crée 1 audit avec 3 projets différents pour leur affecter des mesures différents
            ApplicationBO appli = getComponentFactory().createApplication( getSession() );
            ProjectBO projectWithoutRSM = getComponentFactory().createProject( getSession(), appli, null );
            ProjectBO projectWithRSM = getComponentFactory().createProject( getSession(), appli, null );
            ProjectBO projectJ2eeWithRSM = getComponentFactory().createProject( getSession(), appli, null );
            AuditBO audit = new AuditBO();
            audit.setStatus( AuditBO.TERMINATED );
            AuditDAOImpl.getInstance().save( getSession(), audit );
            projectJ2eeWithRSM.addAudit( audit );
            projectWithoutRSM.addAudit( audit );
            projectWithRSM.addAudit( audit );
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            projectDAO.save( getSession(), projectJ2eeWithRSM );
            projectDAO.save( getSession(), projectWithoutRSM );
            projectDAO.save( getSession(), projectWithRSM );
            // On ajoute des mesures
            // PureComments pour le projet sans RSM
            getComponentFactory().createComments( getSession(), audit, projectWithoutRSM );
            MeasureDAOImpl measureDAO = MeasureDAOImpl.getInstance();
            // RSM pour le projet RSM
            RSMProjectMetricsBO rsm = new RSMProjectMetricsBO();
            rsm.setComments( new Integer( 1 ) );
            rsm.setAudit( audit );
            rsm.setComponent( projectWithRSM );
            measureDAO.create( getSession(), rsm );
            // JspVolumetry pour le projet RSM J2ee (RSM inutile car on ne fait pas le test dessus)
            JSPVolumetryProjectBO jspVol = new JSPVolumetryProjectBO();
            jspVol.setJSPsLOC( new Integer( 2 ) );
            jspVol.setAudit( audit );
            jspVol.setComponent( projectJ2eeWithRSM );
            measureDAO.create( getSession(), jspVol );
            getSession().commitTransactionWithoutClose();
            // On appel la méthode
            AuditConfMigrationLot3_3.createAuditDisplayConfs( getSession() );
            // Il doit y avoir les bonnes configurations pour les projets
            Long auditId = new Long( audit.getId() );
            verifyConfsWithoutRSM( new Long( projectWithoutRSM.getId() ), auditId );
            verifyConfsWithRSM( new Long( projectWithRSM.getId() ), auditId );
            verifyConfsJ2eeWithRSM( new Long( projectJ2eeWithRSM.getId() ), auditId );
        }
        catch ( JrafDaoException e )
        {
            fail( e.getMessage() );
        }
    }

    /**
     * Test les liens crées avec les configurations dans le cas d'un projet non RSM
     * 
     * @param projectId l'id du projet
     * @param auditId l'id de l'audit
     * @throws JrafDaoException si erreur
     */
    private void verifyConfsWithoutRSM( Long projectId, Long auditId )
        throws JrafDaoException
    {
        AuditDisplayConfDAOImpl auditConfDAO = AuditDisplayConfDAOImpl.getInstance();
        // bubble
        AuditDisplayConfBO bubbleConfWithoutRSM =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId, DisplayConfConstants.BUBBLE_SUBCLASS );
        assertEquals( "mccabe.method.vg", ( (BubbleConfBO) bubbleConfWithoutRSM.getDisplayConf() ).getXTre() );
        // volumétrie niveau application
        AuditDisplayConfBO appVolConfWithoutRSM =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId,
                                                 DisplayConfConstants.VOLUMETRY_SUBCLASS,
                                                 DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE );
        assertEquals( 1, ( (VolumetryConfBO) appVolConfWithoutRSM.getDisplayConf() ).getTres().size() );
        assertTrue( ( (VolumetryConfBO) appVolConfWithoutRSM.getDisplayConf() ).getTres().contains(
                                                                                                    "purecomments.project.sloc" ) );
        // volumétrie niveau projet
        AuditDisplayConfBO projVolConfWithoutRSM =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId,
                                                 DisplayConfConstants.VOLUMETRY_SUBCLASS,
                                                 DisplayConfConstants.VOLUMETRY_PROJECT_TYPE );
        final int nbProjectTres = 4;
        assertEquals( nbProjectTres, ( (VolumetryConfBO) projVolConfWithoutRSM.getDisplayConf() ).getTres().size() );
        assertTrue( ( (VolumetryConfBO) projVolConfWithoutRSM.getDisplayConf() ).getTres().contains(
                                                                                                     "purecomments.project.cloc" ) );
    }

    /**
     * Test les liens crées avec les configurations dans le cas d'un projet RSM
     * 
     * @param projectId l'id du projet
     * @param auditId l'id de l'audit
     * @throws JrafDaoException si erreur
     */
    private void verifyConfsWithRSM( Long projectId, Long auditId )
        throws JrafDaoException
    {
        AuditDisplayConfDAOImpl auditConfDAO = AuditDisplayConfDAOImpl.getInstance();
        // bubble
        AuditDisplayConfBO bubbleConfWithRSM =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId, DisplayConfConstants.BUBBLE_SUBCLASS );
        assertEquals( "mccabe.method.vg", ( (BubbleConfBO) bubbleConfWithRSM.getDisplayConf() ).getXTre() );
        // volumétrie niveau application
        AuditDisplayConfBO appVolConfWithRSM =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId,
                                                 DisplayConfConstants.VOLUMETRY_SUBCLASS,
                                                 DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE );
        assertEquals( 1, ( (VolumetryConfBO) appVolConfWithRSM.getDisplayConf() ).getTres().size() );
        assertTrue( ( (VolumetryConfBO) appVolConfWithRSM.getDisplayConf() ).getTres().contains( "rsm.project.sloc" ) );
        // volumétrie niveau projet
        AuditDisplayConfBO projVolConfWithRSM =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId,
                                                 DisplayConfConstants.VOLUMETRY_SUBCLASS,
                                                 DisplayConfConstants.VOLUMETRY_PROJECT_TYPE );
        final int nbProjectTres = 5;
        assertEquals( nbProjectTres, ( (VolumetryConfBO) projVolConfWithRSM.getDisplayConf() ).getTres().size() );
        assertTrue( ( (VolumetryConfBO) projVolConfWithRSM.getDisplayConf() ).getTres().contains(
                                                                                                  "rsm.project.comments" ) );
        assertFalse( ( (VolumetryConfBO) projVolConfWithRSM.getDisplayConf() ).getTres().contains(
                                                                                                   "jspvolumetry.project.numberOfJSP" ) );
    }

    /**
     * Test les liens crées avec les configurations dans le cas d'un projet RSM J2EE
     * 
     * @param projectId l'id du projet
     * @param auditId l'id de l'audit
     * @throws JrafDaoException si erreur
     */
    private void verifyConfsJ2eeWithRSM( Long projectId, Long auditId )
        throws JrafDaoException
    {
        AuditDisplayConfDAOImpl auditConfDAO = AuditDisplayConfDAOImpl.getInstance();
        // bubble
        AuditDisplayConfBO bubbleConfWithoutRSM =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId, DisplayConfConstants.BUBBLE_SUBCLASS );
        assertEquals( "mccabe.method.vg", ( (BubbleConfBO) bubbleConfWithoutRSM.getDisplayConf() ).getXTre() );
        // volumétrie niveau application
        AuditDisplayConfBO appVolConfJ2ee =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId,
                                                 DisplayConfConstants.VOLUMETRY_SUBCLASS,
                                                 DisplayConfConstants.VOLUMETRY_APPLICATION_TYPE );
        assertEquals( 2, ( (VolumetryConfBO) appVolConfJ2ee.getDisplayConf() ).getTres().size() );
        assertTrue( ( (VolumetryConfBO) appVolConfJ2ee.getDisplayConf() ).getTres().contains(
                                                                                              "jspvolumetry.project.numberOfJSPCodeLines" ) );
        // volumétrie niveau projet
        AuditDisplayConfBO projVolConfJ2ee =
            auditConfDAO.findConfigurationWhere( getSession(), projectId, auditId,
                                                 DisplayConfConstants.VOLUMETRY_SUBCLASS,
                                                 DisplayConfConstants.VOLUMETRY_PROJECT_TYPE );
        final int nbProjectTres = 7;
        assertEquals( nbProjectTres, ( (VolumetryConfBO) projVolConfJ2ee.getDisplayConf() ).getTres().size() );
        assertTrue( ( (VolumetryConfBO) projVolConfJ2ee.getDisplayConf() ).getTres().contains(
                                                                                               "jspvolumetry.project.numberOfJSP" ) );
        assertTrue( ( (VolumetryConfBO) projVolConfJ2ee.getDisplayConf() ).getTres().contains( "rsm.project.comments" ) );
    }

}
