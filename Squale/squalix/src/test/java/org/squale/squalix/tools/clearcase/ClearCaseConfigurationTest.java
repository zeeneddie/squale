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
package com.airfrance.squalix.tools.clearcase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalix.tools.clearcase.configuration.ClearCaseConfiguration;

/**
 * Cette classe teste la configuration de la tâche ClearCase.
 * 
 * @author M400832
 * @version 1.0
 */
public class ClearCaseConfigurationTest
    extends SqualeTestCase
{

    /** String contenant juste le nom de la vob sans /vob avant */
    private String mSimpleVobName;

    /** La map clearcase */
    private MapParameterBO mClearcaseMap;

    /**
     * Nom du jalon.
     */
    private String mMilestoneName;

    /**
     * Nom de la branche.
     */
    private String mBranchName;

    /**
     * Nom du projet.
     */
    private String mProjectName;

    /**
     * Nom de la vob sous la forme /vob/nom de la vob.
     */
    private String mVobName;

    /**
     * Configuration ClearCase.
     */
    private ClearCaseConfiguration mCcc;

    /**
     * Projet.
     */
    private ProjectBO mProject;

    /**
     * Application.
     */
    private ApplicationBO mApplication;

    /**
     * Audit.
     */
    private AuditBO mAudit;

    /**
     * HashMap de paramètres du projet.
     */
    private MapParameterBO mHashMap;

    /**
     * Répertoire de stockage de la vue. Doit être configuré en fonction du fichier de configuration XML.
     */
    private final String snapshotPath = "/app/SQUALE/dev/data/cc_snapshot/";

    /**
     * LOGGER.
     */
    private static final Log LOGGER = LogFactory.getLog( ClearCaseConfigurationTest.class );

    /**
     * Constructeur.
     * 
     * @param pArg argument.
     */
    public ClearCaseConfigurationTest( String pArg )
    {
        super( pArg );
    }

    /**
     * Set-up.
     * 
     * @throws Exception en cas d'échec lors de la réinitialisation.
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        /* initialisation */
        mApplication = new ApplicationBO( "mon_application_essai" );
        mProjectName = "mon_projet";
        mSimpleVobName = "squale";
        mVobName = "/vobs/squale";
        mBranchName = "squale_v1_1_act";
        mMilestoneName = "squale_V1_2_ACT";
    }

    /**
     * Teste la validité des commandes ClearCase pour le montage
     * 
     * @throws JrafPersistenceException si erreur
     */
    public void testNormalConfiguration()
        throws JrafPersistenceException
    {
        /* initilisation et lancement */
        initNormalContext();
        launchConfiguration();
        // Vaut "squale_v1_1_act_mon_application_essai_mon_projet_squale
        String path = mBranchName + "_" + mApplication.getName() + "_" + mProjectName + "_" + mSimpleVobName;

        String stringToCheck =
            "/usr/atria/bin/Perl -S /DINB/outils/gcl/script/mkview.pl -application " + mApplication.getName()
                + " -vob /vobs/squale -travail " + mBranchName + " -vws /app/SQUALE/clearcase/cc_storage/views/" + path
                + "dev" + ".vws -login mon_application_essai_mon_projet_squale"
                // Environnement dev
                + "dev" + " -snap -dir " + snapshotPath + path
                // Environnement dev
                + "dev";

        String command = mCcc.getMountWorkViewCommand();
        assertEquals( stringToCheck, command );

        stringToCheck = "/usr/atria/bin/cleartool rmview " + snapshotPath + path
        // Environnement dev
            + "dev";
        command = mCcc.getUmountViewCommand();
        assertEquals( stringToCheck, command );
        stringToCheck = "/usr/atria/bin/cleartool lsview " + path
        // Environnement dev
            + "dev";
        command = mCcc.getVerifyViewExistenceCommand();
        assertEquals( stringToCheck, command );
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Teste la validité des commandes ClearCase pour le montage
     * 
     * @throws JrafPersistenceException si erreur
     */
    public void testMilestoneConfiguration()
        throws JrafPersistenceException
    {
        /* initilisation et lancement */
        initMilestoneContext();
        launchConfiguration();

        // Vaut "squale_v1_1_act_mon_application_essai_mon_projet_squale
        String path = "squale_v1_2_act_" + mApplication.getName() + "_" + mProjectName + "_" + mSimpleVobName;

        String stringToCheck =
            "/usr/atria/bin/Perl -S /DINB/outils/gcl/script/mkview.pl -application " + mApplication.getName()
                + " -vob /vobs/squale -consultation " + mMilestoneName
                + " -vws /app/SQUALE/clearcase/cc_storage/views/" + mMilestoneName
                + "_mon_application_essai_mon_projet_squale" + "dev"
                + ".vws -login mon_application_essai_mon_projet_squale" + "dev" + " -snap -dir " + snapshotPath + path
                + "dev";
        String command = mCcc.getMountConsultationViewCommand();
        assertEquals( stringToCheck, command );
        assertEquals( "/usr/atria/bin/cleartool rmview " + snapshotPath
            + "squale_v1_2_act_mon_application_essai_mon_projet_squale" + "dev" + "", mCcc.getUmountViewCommand() );
        assertEquals( "/usr/atria/bin/cleartool lsview squale_v1_2_act_mon_application_essai_mon_projet_squale" + "dev"
            + "", mCcc.getVerifyViewExistenceCommand() );
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Lance la configuration de la tâche ClearCase.
     * 
     * @return <code>true</code> en cas de succès, <code>
     * false</code> sinon.
     */
    private boolean launchConfiguration()
    {
        boolean success = false;
        try
        {
            mCcc = new ClearCaseConfiguration( mProject, mAudit );
            success = true;
        }
        catch ( Exception e )
        {
            LOGGER.error( e, e );
            success = false;
        }
        return success;
    }

    /**
     * Initialise le contexte d'un audit de suivi.
     */
    private void initNormalContext()
    {
        /* initialisation de l'audit */
        mAudit = new AuditBO();
        mAudit.setType( AuditBO.NORMAL );

        /* initialisation du projet */
        initProject();
    }

    /**
     * Initialise le contexte d'un audit de suivi.
     */
    private void initMilestoneContext()
    {
        /* initialisation de l'audit */
        mAudit = new AuditBO();
        mAudit.setType( AuditBO.MILESTONE );
        mAudit.setName( mMilestoneName );

        /* initialisation du projet */
        initProject();
    }

    /**
     * Initialise le projet
     */
    private void initProject()
    {
        mHashMap = new MapParameterBO();
        mClearcaseMap = new MapParameterBO();
        StringParameterBO branchBO = new StringParameterBO();
        StringParameterBO appliBO = new StringParameterBO();
        ListParameterBO vobsBO = new ListParameterBO();
        StringParameterBO vobBO = new StringParameterBO();
        branchBO.setValue( mBranchName );
        appliBO.setValue( mApplication.getName() );
        vobBO.setValue( mVobName );
        vobsBO.getParameters().add( vobBO );
        mClearcaseMap.getParameters().put( ParametersConstants.BRANCH, branchBO );
        mClearcaseMap.getParameters().put( ParametersConstants.APPLI, appliBO );
        mClearcaseMap.getParameters().put( ParametersConstants.VOBS, vobsBO );
        mHashMap.getParameters().put( ParametersConstants.CLEARCASE, mClearcaseMap );
        mProject = new ProjectBO();
        mProject.setName( mProjectName );
        mProject.setParameters( mHashMap );
        mProject.setParent( mApplication );
    }

}
