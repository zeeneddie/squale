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
package com.airfrance.squalecommon.daolayer.message;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.message.MessageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.message.MessageId;
import com.airfrance.squalecommon.util.initialisor.JRafConfigurator;

/**
 * 
 */
public class MessageDAOImplTest
    extends SqualeTestCase
{

    /** provider de persistence */
    private static IPersistenceProvider PERSISTENT_PROVIDER;

    /** le bo pour les tests */
    private MessageBO bo = new MessageBO();

    /**
     * inititialisation
     * 
     * @throws Exception en cas de problèmes
     */
    public void setUp()
        throws Exception
    {
        super.setUp();
        bo.setKey( "rule.test" );
        bo.setText( "Text" );
        bo.setTitle( "title" );
        bo.setLang( "en" );
        MessageId id = new MessageId();
        id.setKey( "news." + bo.getKey() );
        id.setLang( bo.getLang() );
        bo.setId( id );
    }

    /**
     * test après la migration en J-Raf 1.2.4
     */
    public void testRemoveAll()
    {
        JRafConfigurator.initialize();
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        try
        {
            session = PERSISTENT_PROVIDER.getSession();
            session.beginTransaction();
            MessageDAOImpl messageDAO = MessageDAOImpl.getInstance();
            MessageBO message1 = new MessageBO();
            MessageBO message2 = new MessageBO();
            assertTrue( messageDAO.count( session ).intValue() == 0 );
            MessageId id = new MessageId();
            id.setLang( "fr" );
            id.setKey( "key1" );
            message1.setId( id );
            message1.setText( "text1" );
            id = new MessageId();
            id.setLang( "fr" );
            id.setKey( "key2" );
            message2.setId( id );
            message2.setText( "text2" );
            messageDAO.save( session, message1 );
            messageDAO.save( session, message2 );
            assertTrue( messageDAO.count( session ).intValue() == 2 );
            session.commitTransactionWithoutClose();
            // On sauvegarde dans une autre session
            message2.setText( "text2bis" );
            getSession().beginTransaction();
            messageDAO.save( getSession(), message2 );
            assertTrue( messageDAO.count( session ).intValue() == 2 );
            getSession().commitTransactionWithoutClose();
            // On recharge le message dans l'autre session
            MessageBO message = (MessageBO) messageDAO.get( session, message2.getId() );
            assertEquals( "", message2.getText(), message.getText() );
            // On fait du menage
            messageDAO.removeAll( session );
            assertTrue( messageDAO.count( session ).intValue() == 0 );

        }
        catch ( JrafPersistenceException e )
        {
            fail( "unexpectedException" );
            e.printStackTrace();
        }
        catch ( JrafDaoException e )
        {
            fail( "unexpectedException" );
            e.printStackTrace();
        }
    }

    /**
     * Test la création et l'enregistrement en base d'un newsBO
     */
    public void testCreate()
    {
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        MessageDAOImpl dao = MessageDAOImpl.getInstance();
        try
        {
            session = PERSISTENT_PROVIDER.getSession();
            session.beginTransaction();
            Collection coll = dao.findAll( session );
            // on vérifie qu'on en récupère bien 0 car pour l'instant il n'y en a pas en session
            assertEquals( coll.size(), 0 );
            // on en sauvegarde 1
            dao.create( session, bo );
            session.commitTransactionWithoutClose();
            // après avoir commité, on vérifie qu'il a bien été
            // enregistré et qu'on peut le récupérer
            session.beginTransaction();
            coll = dao.findAll( session );
            session.commitTransaction();
            // vérification
            assertEquals( coll.size(), 1 );
        }
        catch ( JrafPersistenceException e )
        {
            fail( "unexpectedException" );
            e.printStackTrace();
        }
        catch ( JrafDaoException e )
        {
            fail( "unexpectedException" );
            e.printStackTrace();
        }
    }

    /**
     * Test la récupération de tous les messages dont la clé contient une chaine donnée
     */
    public void testFindByKey()
    {
        JRafConfigurator.initialize();
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        MessageDAOImpl dao = MessageDAOImpl.getInstance();
        try
        {
            session = PERSISTENT_PROVIDER.getSession();
            MessageDAOImpl messageDAO = MessageDAOImpl.getInstance();
            session.beginTransaction();
            // on en sauvegarde 1 avec une clé adéquate
            dao.create( session, bo );
            session.commitTransactionWithoutClose();
            session.beginTransaction();
            Collection coll = messageDAO.findAll( session );
            session.commitTransactionWithoutClose();
            assertTrue( coll.size() > 0 );
            session.beginTransaction();
            String key = "rule.test";
            coll = messageDAO.findWhereKey( session, key, null );
            session.commitTransaction();
            assertTrue( coll.size() > 0 );
        }
        catch ( JrafPersistenceException e )
        {
            fail( "unexpectedException" );
            e.printStackTrace();
        }
        catch ( JrafDaoException e )
        {
            fail( "unexpectedException" );
            e.printStackTrace();
        }
    }

    /**
     * Teste la récupération des languages
     */
    public void testGetLangs()
    {
        JRafConfigurator.initialize();
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        MessageDAOImpl dao = MessageDAOImpl.getInstance();

        try
        {
            session = PERSISTENT_PROVIDER.getSession();
            session.beginTransaction();
            // on en sauvegarde 1 avec une clé adéquate
            dao.create( session, bo );
            session.commitTransactionWithoutClose();
            session.beginTransaction();
            MessageDAOImpl messageDAO = MessageDAOImpl.getInstance();
            Collection coll = messageDAO.findLangs( session );
            session.commitTransaction();
            assertTrue( coll.size() == 1 );
        }
        catch ( JrafPersistenceException e )
        {
            fail( "unexpectedException" );
            e.printStackTrace();
        }
        catch ( JrafDaoException e )
        {
            fail( "unexpectedException" );
            e.printStackTrace();
        }

    }

}