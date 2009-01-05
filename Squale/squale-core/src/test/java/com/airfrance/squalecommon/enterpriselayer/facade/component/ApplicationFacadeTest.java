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
package com.airfrance.squalecommon.enterpriselayer.facade.component;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.datatransfertobject.config.ServeurDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.UserTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;

/**
 * Test de la facade d'application Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ApplicationFacadeTest
    extends SqualeTestCase
{

    /**
     * Test de la méthode getApplicationConf(...); - Test avec un identifiant valide - Test avec un identifiant non
     * valide
     */
    public void testGetApplicationConf()
    {
        ApplicationBO application = null;
        try
        {
            application = getComponentFactory().createApplication( null );
            // Excution de la méthode avec un identifiant valide
            ApplicationConfDTO applicationConfIn = new ApplicationConfDTO();
            applicationConfIn.setId( application.getId() );
            ApplicationConfDTO out = ApplicationFacade.getApplicationConf( applicationConfIn );

            // Excution de la méthode avec un identifiant non valide
            applicationConfIn.setId( -1 );
            out = (ApplicationConfDTO) ApplicationFacade.getApplicationConf( applicationConfIn );
            assertNull( out );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }

    }

    /**
     * Test de la méthode exists() - Test avec un nom existant - Test avec un nom inexistant - Test avec nu nom
     * <code>null</code>
     */
    public void testExists()
    {
        ComponentDTO dto = new ComponentDTO();
        // Initialisation des parametres de la methode
        ApplicationBO application;
        try
        {
            application = getComponentFactory().createApplication( null );
            dto.setName( application.getName() );

            // Excution de la méthode avec une application existante
            boolean existsApplication = ApplicationFacade.exists( dto );
            assertTrue( existsApplication );

            // Excution de la méthode avec un nom d'application n'existant pas
            dto.setName( "" );
            existsApplication = ApplicationFacade.exists( dto );
            assertFalse( existsApplication );

            // Excution de la méthode avec un nom non renseigné
            dto.setName( null );
            existsApplication = ApplicationFacade.exists( dto );
            assertFalse( existsApplication );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }

    }

    /**
     * Test de update(ApplicationConfDTO, ISession) - test avec modification des données - test avec ajout
     * d'utilisateurs
     */
    public void testUpdateApplicationConfDTOISession()
    {
        ApplicationBO application;
        try
        {
            application = getComponentFactory().createApplication( null );
            ComponentDTO dto = new ComponentDTO();

            // Initialisation
            ISession session = null; // session Hibernate

            // Insertion de l'objet a modifier en base
            ApplicationConfDTO applicationConfIn = new ApplicationConfDTO();
            applicationConfIn.setId( application.getId() );
            ApplicationConfDTO applicationConf = ApplicationFacade.getApplicationConf( applicationConfIn );
            ServeurDTO serverDTO = new ServeurDTO();
            serverDTO.setName( "TLS" );
            serverDTO.setServeurId( 1 );
            applicationConf.setServeurDTO( serverDTO );
            // Methode update
            ApplicationFacade.update( applicationConf );

            // Chargement de l'objet pour vérifier le changement
            applicationConf = ApplicationFacade.getApplicationConf( applicationConf );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test pour ApplicationConfDTO insert(ApplicationConfDTO, ISession)
     */
    public void testInsertApplicationConfDTOISession()
    {

        // Initialisation
        ISession session = null; // session Hibernate
        // Execution de la methode avec un nom d'application existante
        ApplicationBO application;
        try
        {
            session = getSession();
            session.beginTransaction();
            application = getComponentFactory().createApplication( session );
            UserDTO user = UserTransform.bo2Dto( getComponentFactory().createUser( session ) );
            session.commitTransactionWithoutClose();
            ApplicationConfDTO applicationConfIn = new ApplicationConfDTO();
            applicationConfIn.setId( application.getId() );
            ApplicationConfDTO applicationConf = ApplicationFacade.getApplicationConf( applicationConfIn );

            session.beginTransaction();
            applicationConf = ApplicationFacade.insert( applicationConf, user, session );
            session.commitTransactionWithoutClose();

            assertNull( applicationConf );

            // Execution de la méthode avec un nom d'application inexistante
            applicationConfIn.setName( "ema_application" );
            // il doit échouer
            boolean failed = false;
            try
            {
                applicationConf = ApplicationFacade.insert( applicationConfIn, user, session );
            }
            catch ( NullPointerException e )
            {
                failed = true;
            }
            assertTrue( failed );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }

    }

    /**
     * 
     */
    public void testGetApplication()
    {
        ISession session;
        try
        {
            session = getSession();
            ApplicationBO application = getComponentFactory().createApplication( session );
            // Initialisation des parametres de la methode
            ComponentDTO applicationIn = new ComponentDTO();
            applicationIn.setID( application.getId() );

            // Initialisation du retour

            // Exceution de la méthode avec un identifiant valide
            ComponentDTO out = ApplicationFacade.getApplication( applicationIn );
            assertNotNull( out );
            // Exceution de la méthode avec un identifiant non valide
            applicationIn.setID( -1 );
            out = (ComponentDTO) ApplicationFacade.getApplication( applicationIn );
            assertNull( out );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }

    }

    /**
     * Test pour void deleteApplicationConfList(Collection, ISession)
     */
    public void testDeleteApplicationConfListCollectionISession()
    {
        try
        {
            // Insertion de l'application a manipuler
            ISession session = getSession();
            session.beginTransaction();
            ApplicationBO application = getComponentFactory().createApplication( session );
            ApplicationConfDTO dto = new ApplicationConfDTO();
            dto.setId( application.getId() );

            // Suppression de l'objet
            Collection applicationConfs = new ArrayList();
            applicationConfs.add( dto );
            ApplicationFacade.deleteApplicationConfList( applicationConfs );

            // Verifier que l'objet n'est plus en base
            ApplicationConfDTO applicationConf = ApplicationFacade.getApplicationConf( dto );
            assertTrue( applicationConf.getStatus() == ApplicationConfDTO.DELETED );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test pour void validateApplicationConfList(Collection)
     */
    public void testValidateApplicationConfListCollection()
    {
        try
        {
            // Insertion de l'application a manipuler
            ISession session = getSession();
            session.beginTransaction();

            // Insertion de l'application a manipuler
            ApplicationBO application = getComponentFactory().createApplication( session );
            ApplicationConfDTO dto = new ApplicationConfDTO();
            dto.setId( application.getId() );
            ApplicationConfDTO applicationConf = ApplicationFacade.getApplicationConf( dto );
            assertTrue( applicationConf.getStatus() == ApplicationConfDTO.CREATED );

            // Execution de la méthode
            Collection applicationConfs = new ArrayList();
            applicationConfs.add( dto );
            ApplicationFacade.validateApplicationConfList( applicationConfs );

            // Verification que le champs est bien passé a validé
            applicationConf = ApplicationFacade.getApplicationConf( dto );
            assertTrue( applicationConf.getStatus() == ApplicationConfDTO.VALIDATED );
            // Vérification qu'un audit périodique n'a pas été créé
            assertEquals( 0, applicationConf.getAuditFrequency() );
            ApplicationDAOImpl.getInstance().refresh( session, application );
            assertEquals( 0, application.getAudits().size() );

            // Placement d'une péridodicité sur l'audit
            final int auditFrequency = 1;
            application.setAuditFrequency( auditFrequency );
            ApplicationDAOImpl.getInstance().save( session, application );
            session.commitTransactionWithoutClose();
            ApplicationFacade.validateApplicationConfList( applicationConfs );
            applicationConf = ApplicationFacade.getApplicationConf( dto );
            // Vérification qu'un audit périodique a pas été créé
            assertEquals( auditFrequency, applicationConf.getAuditFrequency() );
            ApplicationDAOImpl.getInstance().refresh( session, application );
            assertEquals( 1, application.getAudits().size() );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }

    }
}
