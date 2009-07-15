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
package org.squale.squalecommon.daolayer.message;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafPersistenceException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.enterpriselayer.businessobject.message.MessageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.message.MessageId;
import org.squale.squalecommon.enterpriselayer.businessobject.message.NewsBO;

/**
 * 
 */
public class NewsDAOImplTest
    extends SqualeTestCase
{

    /** le bo */
    private NewsBO bo;

    /** un message BO pour tester */
    private MessageBO messBo;

    /** provider de persistence */
    private static IPersistenceProvider PERSISTENT_PROVIDER;

    /**
     * setUp le test
     * 
     * @throws Exception en cas d'échec
     */
    public void setUp()
        throws Exception
    {
        super.setUp();
        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.MONTH, Calendar.AUGUST );
        calendar.set( Calendar.YEAR, 2005 );
        Date begin = calendar.getTime();
        calendar.set( Calendar.YEAR, 2007 );
        Date end = calendar.getTime();
        bo = new NewsBO();
        bo.setId( 1 );
        bo.setBeginningDate( begin );
        bo.setEndDate( end );
        bo.setKey( "test" );
        messBo = new MessageBO();
        messBo.setKey( "rule.test" );
        messBo.setText( "Text" );
        messBo.setTitle( "title" );
        messBo.setLang( "en" );
        MessageId id = new MessageId();
        id.setKey( messBo.getKey() );
        id.setLang( messBo.getLang() );
        messBo.setId( id );

    }

    /**
     * Test la création et l'enregistrement en base d'un newsBO et de son messageBO
     */
    public void testAddNews()
    {
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        NewsDAOImpl dao = NewsDAOImpl.getInstance();
        try
        {
            session = PERSISTENT_PROVIDER.getSession();
            session.beginTransaction();
            Collection coll = dao.findAll( session );
            // on vérifie qu'on en récupère bien 0 car pour l'instant il n'y en a pas en session
            assertEquals( coll.size(), 0 );
            // on en sauvegarde 1
            dao.addNews( session, bo, messBo );
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
     * Test la création et l'enregistrement en base d'un newsBO et de son messageBO
     */
    public void testModifyNews()
    {
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        NewsDAOImpl dao = NewsDAOImpl.getInstance();
        try
        {
            session = PERSISTENT_PROVIDER.getSession();
            session.beginTransaction();
            Collection coll = dao.findAll( session );
            // on vérifie qu'on en récupère bien 0 car pour l'instant il n'y en a pas en session
            assertEquals( coll.size(), 0 );
            // on en sauvegarde 1
            dao.addNews( session, bo, messBo );
            session.commitTransactionWithoutClose();
            // après avoir commité, on vérifie qu'il a bien été
            // enregistré et qu'on peut le récupérer
            session.beginTransaction();
            coll = dao.findAll( session );
            session.commitTransactionWithoutClose();
            // vérification
            assertEquals( coll.size(), 1 );
            // Modification
            session.beginTransaction();
            bo.setKey( "test.modification" );
            messBo.setKey( "test.modification.messageKey" );
            dao.modifyNews( session, bo, messBo );
            session.commitTransactionWithoutClose();
            session.beginTransaction();
            coll = dao.findAll( session );
            session.commitTransaction();
            // On ne doit toujours qu'en avoir qu'un
            // le meme mais avec la clé modifiée
            assertEquals( coll.size(), 1 );
            Iterator it = coll.iterator();
            NewsBO newBo = (NewsBO) it.next();
            // on vérifie que la clé a bien été modifiée
            assertEquals( newBo.getKey(), "test.modification" );
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
     * Test la création et l'enregistrement en base d'un newsBO et de son messageBO
     */
    public void testPurgeNews()
    {
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        NewsDAOImpl dao = NewsDAOImpl.getInstance();
        try
        {
            session = PERSISTENT_PROVIDER.getSession();
            session.beginTransaction();
            Collection coll = dao.findAll( session );
            // on vérifie qu'on en récupère bien 0 car pour l'instant il n'y en a pas en session
            assertEquals( coll.size(), 0 );
            // on en sauvegarde 1
            dao.addNews( session, bo, messBo );
            session.commitTransactionWithoutClose();
            // après avoir commité, on vérifie qu'il a bien été
            // enregistré et qu'on peut le récupérer
            session.beginTransaction();
            coll = dao.findAll( session );
            session.commitTransactionWithoutClose();
            // vérification
            assertEquals( coll.size(), 1 );
            Iterator it = coll.iterator();
            NewsBO newBo = (NewsBO) it.next();
            session.beginTransaction();
            dao.removeNews( session, newBo, messBo );
            session.commitTransactionWithoutClose();
            // on vérifie que la suppression a bien été faite
            session.beginTransaction();
            coll = dao.findAll( session );
            session.commitTransaction();
            // vérification
            assertEquals( coll.size(), 0 );

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
     * Test la récupération en base de news en fonction de leurs catégories (toutes, les courantes, les anciennes)
     */
    public void testFindWhereKind()
    {
        PERSISTENT_PROVIDER = PersistenceHelper.getPersistenceProvider();
        ISession session;
        NewsDAOImpl dao = NewsDAOImpl.getInstance();
        MessageDAOImpl messDao = MessageDAOImpl.getInstance();
        Collection coll;
        try
        {
            session = PERSISTENT_PROVIDER.getSession();
            session.beginTransaction();
            // on en sauvegarde 1
            dao.addNews( session, bo, messBo );
            session.commitTransactionWithoutClose();
            session.beginTransaction();
            coll = dao.findWhereKind( session, "all" );
            session.commitTransactionWithoutClose();
            assertTrue( coll.size() > 0 );
            /*
             * le todate ne marche pas en test unitaire on ne peut pas effectuer les tests suivants
             * session.beginTransaction(); coll = dao.findWhereKind(session, "old");
             * session.commitTransactionWithoutClose(); assertTrue(coll.size() == 0); session.beginTransaction(); coll =
             * dao.findWhereKind(session, "current"); session.commitTransaction(); assertTrue(coll.size() > 0);
             */

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
