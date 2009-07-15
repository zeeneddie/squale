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
package org.squale.squalecommon.enterpriselayer.facade.message;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.message.MessageDAOImpl;
import org.squale.squalecommon.daolayer.message.NewsDAOImpl;
import org.squale.squalecommon.datatransfertobject.message.MessageDTO;
import org.squale.squalecommon.datatransfertobject.message.MessagesDTO;
import org.squale.squalecommon.datatransfertobject.message.NewsDTO;
import org.squale.squalecommon.datatransfertobject.message.NewsListDTO;
import org.squale.squalecommon.datatransfertobject.transform.messages.MessageTransform;
import org.squale.squalecommon.datatransfertobject.transform.messages.NewsTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.message.MessageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.message.NewsBO;
import org.squale.squalecommon.enterpriselayer.facade.rule.QualityGridFacade;

/**
 * Facade pour les messages
 */
public class MessageFacade
{
    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Importation de messages Les messages sont importés à partir d'un flux et sont stockés dans la base de données Les
     * messages existants sont mis à jour si besoin, ils ne sont pas supprimés
     * 
     * @param pStream flux
     * @param pErrors erreurs
     * @return messages
     * @throws JrafEnterpriseException si erreur
     */
    public static MessagesDTO importMessages( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        MessagesDTO result = null;
        // Importation des messages depuis le flux XML
        MessageImport imp = new MessageImport();
        Collection coll = imp.importMessages( pStream, pErrors );
        if ( coll.size() != 0 )
        {
            // On ne traite les messages que s'il n'y a pas eu d'erreur
            ISession session = null;

            try
            {
                // récupération d'une session
                session = PERSISTENTPROVIDER.getSession();

                MessageDAOImpl messageDAO = MessageDAOImpl.getInstance();
                // Création de chaque message chargé
                Iterator collIt = coll.iterator();
                while ( collIt.hasNext() )
                {
                    MessageBO message = (MessageBO) collIt.next();
                    messageDAO.save( session, message );
                }
                // Récupération de tous les messages et pas seulement ceux importés
                coll = messageDAO.findAll( session );
                // Conversion des messages
                result = convertMessages( coll );
            }
            catch ( JrafDaoException e )
            {
                // Renvoi d'une exception
                FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".importMessages" );
            }
            finally
            {
                // Fermeture de la session
                FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".importMessages" );
            }

        }
        return result;
    }

    /**
     * Obtention des messages
     * 
     * @return messages
     * @throws JrafEnterpriseException si erreur
     */
    public static MessagesDTO getMessages()
        throws JrafEnterpriseException
    {
        MessagesDTO result = null;
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            MessageDAOImpl messageDAO = MessageDAOImpl.getInstance();
            Collection coll = messageDAO.findAll( session );
            result = convertMessages( coll );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, QualityGridFacade.class.getName() + ".getMessages" );
        }
        finally
        {
            FacadeHelper.closeSession( session, QualityGridFacade.class.getName() + ".getMessages" );
        }
        return result;
    }

    /**
     * Conversion des messages
     * 
     * @param pMessages messages le premier niveau de map étant basé sur la langue et le deuxième sur la clef du message
     *            (le texte étant la valeur)
     * @return messages
     */
    private static MessagesDTO convertMessages( Collection pMessages )
    {
        MessagesDTO result = new MessagesDTO();
        Iterator messagesIt = pMessages.iterator();
        while ( messagesIt.hasNext() )
        {
            MessageBO message = (MessageBO) messagesIt.next();
            result.addMessage( message.getLang(), message.getKey(), message.getText() );
        }
        return result;
    }

    /**
     * @param pKind le paramètre permettant de savoir à partir de quand on doit les récupérer
     * @param pLang la langue éventuelle des news a récupérer (peut etre null si sans importance)
     * @return la collection des news ou des messages
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static NewsListDTO getNews( String pKind, String pLang )
        throws JrafEnterpriseException
    {
        NewsListDTO result = new NewsListDTO();
        Collection newsDtoColl = new ArrayList( 0 );
        Collection aux = null;
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            NewsDAOImpl dao = NewsDAOImpl.getInstance();
            // on récupère toutes les nouveautés suivant le paramètre kind
            // qui détermine un critère en fonction de leurs dates
            aux = dao.findWhereKind( session, pKind );
            if ( aux != null )
            {
                MessageDAOImpl messageDAO = MessageDAOImpl.getInstance();
                Iterator it = aux.iterator();
                while ( it.hasNext() )
                {
                    // on a une collection de news, on doit récupérer les messages associés
                    NewsBO newsBo = (NewsBO) it.next();
                    NewsDTO newsDto = NewsTransform.bo2Dto( newsBo );
                    // on récupère tous les messages qui ont leur clé contenant une partie
                    // de la clé de nouveauté associé
                    Collection messagesColl = messageDAO.findWhereKey( session, newsBo.getKey(), pLang );
                    if ( messagesColl != null )
                    {
                        Iterator itMess = messagesColl.iterator();
                        while ( itMess.hasNext() )
                        {
                            // un newsdto par message associé
                            newsDto = new NewsDTO();
                            newsDto = NewsTransform.bo2Dto( newsBo );
                            // transforme tous les messageBO trouvés en dto
                            MessageBO messBo = (MessageBO) itMess.next();
                            MessageDTO messDto = MessageTransform.bo2Dto( messBo );
                            // on repositionne la valeur du message
                            newsDto.setMessage( messDto );
                            // et on ajoute le newsDto à la liste
                            newsDtoColl.add( newsDto );
                        }
                    }
                    else
                    { // pas de message associé
                        newsDtoColl.add( newsDto );
                    }
                }
                // ajoute la liste des newsDto au DTO qui les regroupe
                result.setNewsList( newsDtoColl );
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, MessageFacade.class.getName() + ".getNews" );
        }
        finally
        {
            FacadeHelper.closeSession( session, MessageFacade.class.getName() + ".getNews" );
        }
        return result;
    }

    /**
     * @param newsDto la news qu'on veut supprimer
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static void purgeNews( NewsDTO newsDto )
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            NewsDAOImpl dao = NewsDAOImpl.getInstance();
            NewsBO newsBo = NewsTransform.dto2Bo( newsDto );
            MessageBO messBo = MessageTransform.dto2Bo( newsDto.getMessage() );
            dao.removeNews( session, newsBo, messBo );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, MessageFacade.class.getName() + ".purgeNews" );
        }
        finally
        {
            FacadeHelper.closeSession( session, MessageFacade.class.getName() + ".purgeNews" );
        }
    }

    /**
     * @param pNewsDto l'id de la news que l'on veut ajouter
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static void addNews( NewsDTO pNewsDto )
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            MessageBO messBO = MessageTransform.dto2Bo( pNewsDto.getMessage() );
            NewsBO newsBO = NewsTransform.dto2Bo( pNewsDto );
            NewsDAOImpl dao = NewsDAOImpl.getInstance();
            dao.addNews( session, newsBO, messBO );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, MessageFacade.class.getName() + ".addNews" );
        }
        finally
        {
            FacadeHelper.closeSession( session, MessageFacade.class.getName() + ".addNews" );
        }
    }

    /**
     * @param pNewsDto l'id de la news que l'on veut modifier
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static void modifyNews( NewsDTO pNewsDto )
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            MessageBO messBO = MessageTransform.dto2Bo( pNewsDto.getMessage() );
            NewsBO newsBO = NewsTransform.dto2Bo( pNewsDto );
            NewsDAOImpl dao = NewsDAOImpl.getInstance();
            dao.modifyNews( session, newsBO, messBO );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, MessageFacade.class.getName() + ".modifyNews" );
        }
        finally
        {
            FacadeHelper.closeSession( session, MessageFacade.class.getName() + ".modifyNews" );
        }
    }

    /**
     * @return la collection des langues disponibles
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static Collection findLangs()
        throws JrafEnterpriseException
    {
        ISession session = null;
        Collection coll = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            MessageDAOImpl messDao = MessageDAOImpl.getInstance();
            coll = messDao.findLangs( session );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, MessageFacade.class.getName() + ".modifyNews" );
        }
        finally
        {
            FacadeHelper.closeSession( session, MessageFacade.class.getName() + ".modifyNews" );
        }
        return coll;
    }

    /**
     * @param pKey la clé dont on veut vérifier si elle existe déjà ou pas
     * @return true si la clé existe déjà
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static Boolean newsAlreadyExists( String pKey )
        throws JrafEnterpriseException
    {
        ISession session = null;
        Collection coll = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            NewsDAOImpl messDao = NewsDAOImpl.getInstance();
            coll = messDao.findWhereNewsKeyIs( session, pKey );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, MessageFacade.class.getName() + ".modifyNews" );
        }
        finally
        {
            FacadeHelper.closeSession( session, MessageFacade.class.getName() + ".modifyNews" );
        }
        return new Boolean( coll.size() != 0 );
    }
}