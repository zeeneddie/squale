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
package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.ArrayList;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ProjectConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ApplicationConfTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ApplicationAdminApplicationComponentAccessTest
    extends SqualeTestCase
{

    /**
     * Test d'accès au componentAccess
     */
    public void testApplicationAdminApplicationComponentAccess()
    {
        // Teste si la construction de l'application component par AccessDelegateHelper
        IApplicationComponent appComponent;
        try
        {
            appComponent = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
            assertNotNull( appComponent );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'ajout de projet
     */
    public void testAddProject()
    {
        try
        {
            ApplicationBO application = getComponentFactory().createApplication( getSession() );
            // Teste si la methode est accessible par AccessDelegateHelper
            // et si l'objet renvoyé n'est pas nul
            IApplicationComponent appComponent;

            // Initialisation des parametres de la methode
            ProjectConfDTO projectConf = new ProjectConfDTO(); // à initialiser
            projectConf.setName( "project" );
            ApplicationConfDTO applicationConf = ApplicationConfTransform.bo2Dto( application, new ArrayList() );

            // Initialisation des parametres sous forme de tableaux d'objets
            Object[] paramIn = new Object[2];
            paramIn[0] = projectConf;
            paramIn[1] = applicationConf;

            // Execution de la methode
            appComponent = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
            appComponent.execute( "addProject", paramIn );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'enregistrement d'application
     */
    public void testSaveApplication()
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation des parametres de la methode
        ApplicationConfDTO applicationConf = new ApplicationConfDTO(); // à initialiser

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[1];
        paramIn[0] = applicationConf;

        // Execution de la methode
        try
        {
            appComponent = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
            appComponent.execute( "saveApplication", paramIn );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de création d'une application
     */
    public void testCreateApplication()
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation des parametres de la methode
        ApplicationConfDTO applicationConf = new ApplicationConfDTO(); // à initialiser
        UserDTO user = new UserDTO();
        applicationConf.setName( "squale1" );

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[2];
        paramIn[0] = applicationConf;
        paramIn[1] = user;

        // Execution de la methode
        try
        {
            appComponent = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
            appComponent.execute( "createApplication", paramIn );
            assertNotNull( "buzz" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de suppression d'un projet
     */
    public void testRemoveProject()
    {
        try
        {
            ISession session = getSession();
            // on crée une transaction juste pour la création des éléments
            session.beginTransaction();
            ApplicationBO application = getComponentFactory().createApplication( session );
            QualityGridBO grid = getComponentFactory().createGrid( session );
            ProjectBO project = getComponentFactory().createProject( session, application, grid );
            // on la ferme donc juste apres sans terminer la session
            session.commitTransactionWithoutClose();

            // Teste si la methode est accessible par AccessDelegateHelper
            // et si l'objet renvoyé n'est pas nul
            IApplicationComponent appComponent;

            // Initialisation des parametres de la methode
            ProjectConfDTO projectConf = new ProjectConfDTO(); // à initialiser
            projectConf.setId( project.getId() );
            // Initialisation des parametres sous forme de tableaux d'objets
            Object[] paramIn = new Object[1];
            paramIn[0] = projectConf;

            // Execution de la methode
            appComponent = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
            appComponent.execute( "removeProject", paramIn );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de sauvegarde d'un projet
     */
    public void testSaveProject()
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation des parametres de la methode
        ProjectConfDTO projectConf = new ProjectConfDTO(); // à initialiser
        ApplicationConfDTO applicationConf = new ApplicationConfDTO(); // à initialiser

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[2];
        paramIn[0] = projectConf;
        paramIn[1] = applicationConf;

        // Execution de la methode
        try
        {
            appComponent = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'ajout d'un audit de jalon
     */
    public void testAddMilestone()
    {
        // Teste si la methode est accessible par AccessDelegateHelper
        // et si l'objet renvoyé n'est pas nul
        IApplicationComponent appComponent;

        // Initialisation des parametres de la methode
        ApplicationConfDTO applicationConf = new ApplicationConfDTO(); // à initialiser
        AuditDTO audit = new AuditDTO(); // à initialiser

        // Initialisation des parametres sous forme de tableaux d'objets
        Object[] paramIn = new Object[1];
        paramIn[0] = audit;

        // Execution de la methode
        try
        {
            appComponent = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
            appComponent.execute( "addMilestone", paramIn );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test d'obtention de la configuration d'une application
     */
    public void testGetApplicationConf()
    {
        try
        {
            ApplicationBO application = getComponentFactory().createApplication( getSession() );
            // Teste si la methode est accessible par AccessDelegateHelper
            // et si l'objet renvoyé n'est pas nul
            IApplicationComponent appComponent;

            // Initialisation du retour
            ApplicationConfDTO applicationConfOut;

            // Initialisation des parametres de la methode
            ApplicationConfDTO applicationConfIn = new ApplicationConfDTO(); // à initialiser
            applicationConfIn.setId( application.getId() );

            // Initialisation des parametres sous forme de tableaux d'objets
            Object[] paramIn = new Object[1];
            paramIn[0] = applicationConfIn;

            // Execution de la methode
            appComponent = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
            Object conf = appComponent.execute( "getApplicationConf", paramIn );
            assertNotNull( conf );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
