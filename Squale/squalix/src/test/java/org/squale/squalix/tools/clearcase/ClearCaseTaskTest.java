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
 * Créé le 6 sept. 05, par M400832.
 */
package org.squale.squalix.tools.clearcase;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import org.squale.squalix.core.TaskData;
import org.squale.squalix.tools.clearcase.task.ClearCaseTask;

/**
 * Cette classe teste le montage de vues ClearCase.
 */
// UNIT_PENDING : montage de la vue au niveau windows --> configuration xml à changer
// Les test à été mis à jour pour les initialisations mais il faut se pencher sur le fonctionnel
// au niveau Windows.
public class ClearCaseTaskTest
    extends SqualeTestCase
{

    /**
     * Nom de la branche.
     */
    private static final String BRANCH_NAME = "squale_v0_0_act";

    /**
     * Nom de l'application au sens clearcase.
     */
    private static final String APPLI_NAME = "squale";

    /**
     * Nom de la vob.
     */
    private static final String VOB_NAME = "/vobs/squale/squaleCommon";

    /**
     * Nom du view path
     */
    private static final String VIEW_PATH = "data/viewPath";

    /** pour le view_path */
    private TaskData mDatas;

    /**
     * Audit.
     */
    private AuditBO mAudit;

    /**
     * Projet
     */
    private ProjectBO mProject;

    /**
     * Application.
     */
    private ApplicationBO mApplication;

    /**
     * HashMap de paramètres du projet.
     */
    private MapParameterBO mHashMap;

    /**
     * Tâche ClearCase.
     */
    private ClearCaseTask mCct;

    /**
     * LOGGER.
     */
    private static final Log LOGGER = LogFactory.getLog( ClearCaseTaskTest.class );

    /**
     * Constructeur.
     * 
     * @param pArgs paramètre.
     */
    public ClearCaseTaskTest( String pArgs )
    {
        super( pArgs );
    }

    /**
     * Set-up.
     * 
     * @throws Exception en cas de problème lors de la réinitialisation
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        /* Définitions des paramètres communs */
        // L'application
        mApplication = getComponentFactory().createApplication( getSession() );
        // Le projet avec sa grille associée
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        mProject = getComponentFactory().createProject( getSession(), mApplication, grid );
        initProject();
        // L'audit
        mAudit = getComponentFactory().createAudit( getSession(), mProject );
        // Les paramètres temporaires
        mDatas = new TaskData();
        mDatas.putData( TaskData.VIEW_PATH, VIEW_PATH );
        getSession().commitTransactionWithoutClose();

    }

    /**
     * Montage d'un audit de suivi sur l'application SQUALE.
     * 
     * @see #doNormalTest(String, String)
     */
    public void testMountSqualeNormal()
        throws JrafPersistenceException
    {
        /* lancement de la tâche */
        launchTask();

        /* test de présence de la vue */
        assertTrue( viewIsMounted() );

        /* démontage de la vue */
        umountView();
    }

    /**
     * Montage d'un audit de jalon.
     * 
     * @see #doMilestoneTest(String, String)
     */
    public void testSqualeMilestone()
        throws JrafPersistenceException
    {
        // Audit de jalon
        mAudit.setType( AuditBO.MILESTONE );
        mAudit.setName( "SQUALE_V0_0_ACT" );
        testMountSqualeNormal();
    }

    /**
     * Initialise le projet
     * 
     * @throws JrafDaoException si exception
     */
    private void initProject()
        throws JrafDaoException
    {
        mHashMap = new MapParameterBO();
        MapParameterBO clearcaseMap = new MapParameterBO();
        StringParameterBO branchBO = new StringParameterBO();
        StringParameterBO appliBO = new StringParameterBO();
        ListParameterBO vobsBO = new ListParameterBO();
        StringParameterBO vobBO = new StringParameterBO();
        branchBO.setValue( BRANCH_NAME );
        appliBO.setValue( APPLI_NAME );
        vobBO.setValue( VOB_NAME );
        vobsBO.getParameters().add( vobBO );
        clearcaseMap.getParameters().put( ParametersConstants.BRANCH, branchBO );
        clearcaseMap.getParameters().put( ParametersConstants.APPLI, appliBO );
        clearcaseMap.getParameters().put( ParametersConstants.VOBS, vobsBO );
        mHashMap.getParameters().put( ParametersConstants.CLEARCASE, clearcaseMap );
        ProjectParameterDAOImpl.getInstance().save( getSession(), mHashMap );
        mProject.setParameters( mHashMap );
        ProjectDAOImpl.getInstance().save( getSession(), mProject );
    }

    /**
     * Lance la tâche de montage.
     */
    private void launchTask()
    {
        mCct = new ClearCaseTask();
        mCct.setApplicationId( new Long( mApplication.getId() ) );
        mCct.setProjectId( new Long( mProject.getId() ) );
        mCct.setAuditId( new Long( mAudit.getId() ) );
        mCct.setData( mDatas );
        mCct.run();
    }

    /**
     * Retourne <code>true</code> si la vue a été correctement montée, <code>false</code> sinon.
     * 
     * @return <code>true</code> en cas de succès, <code>
     * false</code> sinon.
     */
    private boolean viewIsMounted()
    {
        boolean success = false;

        File dir = new File( VIEW_PATH );

        /*
         * on vérifie que le répertoire contenant les fichiers de la vue existe.
         */
        if ( dir.isDirectory() )
        {
            /*
             * si le répertoire existe, on vérifie qu'il a bien été créé par mkview.
             */
            success = checkViewExistence();
        }
        return success;
    }

    /**
     * Vérifie si la vue a bien été montée, i.e. si le fichier <code>.vws</code> existe.
     * 
     * @return <code>true</code> en cas de succès, <code>
     * false</code> sinon.
     */
    private boolean checkViewExistence()
    {
        boolean alreadyMounted = false;
        try
        {
            /* initialisation du runtime. */
            Runtime runtime = Runtime.getRuntime();
            Process processViewExist = runtime.exec( mCct.getConfiguration().getVerifyViewExistenceCommand() );

            /* si la commande est exécutée avec succès. */
            if ( 0 == processViewExist.waitFor() )
            {
                alreadyMounted = true;
            }

            processViewExist = null;
            runtime = null;
        }
        catch ( Exception e )
        {
            LOGGER.error( e, e );
            alreadyMounted = false;
        }
        return alreadyMounted;
    }

    /**
     * Supprime / démonte la vue ClearCase.
     */
    protected void umountView()
    {
        try
        {
            Runtime runtime = Runtime.getRuntime();
            Process processCleanView = runtime.exec( mCct.getConfiguration().getUmountViewCommand() );

            /* le processus se termine correctement */
            processCleanView.waitFor();

            processCleanView = null;
            runtime = null;
        }
        catch ( Exception e )
        {
            LOGGER.error( e, e );
        }
    }

}
