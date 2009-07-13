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
package org.squale.jraf.provider.persistence.hibernate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.persistence.IMetaData;
import org.squale.jraf.spi.persistence.IPersistenceDAO;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;

/**
 * <p>
 * Project: JRAF
 * <p>
 * Module: jrafProviderPersistence
 * <p>
 * Title : AbstractDAOImpl.java
 * </p>
 * <p>
 * Description :
 * </p>
 * <p>
 * Copyright : Copyright (c) 2004
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 * <br>
 * Classe de la couche DAO: propose une implémentation de l'interface d'accès à l'ORM pour le produit Hibernate Il faut
 * <b>impérativement</b> utiliser la méthode setBusinessClass dans le constructeur du DAO pour établir la valeur de la
 * clause from des requetes HQL. Par exemple pour une classe <b>EscaleDAOImpl</b> nous aurions le code:<br>
 * <b> <br>
 * private EscaleDAOImpl() throws JrafDaoException { <br>
 * <i> setBusinessClass(Escale.class);</i> <br>
 * </b>
 */
public abstract class AbstractDAOImpl
    implements IPersistenceDAO
{

    /** logger */
    private static final Log log = LogFactory.getLog( AbstractDAOImpl.class );

    /** persistence provider */
    private IPersistenceProvider persistentProvider = null;

    /**
     * variable contenant le début d'une requete. ex: pour une classe Foo nous aurons <b>from Foo as foo</b>
     */
    private String requete = null;

    /** classe du bo lie */
    private Class businessClass = null;

    /** alias du au bo lie */
    private String alias = null;

    /**
     * COnstructeur par defaut
     */
    protected AbstractDAOImpl()
    {
        super();
    }

    /**
     * Récupérer un objet à partir de son identifiant. On le cherche d'abord en cache session, auquel cas rien ne se
     * passe <li>Si le mapping défini un proxy (voir iterate), une référence est ajoutée. <li>Sinon l'objet est lue en
     * base et caché dans la session
     * 
     * @param session session de persistance
     * @param oid valeur de l'identifiant
     * @return Objet charge.
     * @throws JrafDaoException
     */
    public Object load( ISession session, Serializable oid )
        throws JrafDaoException
    {
        Object obj = null;
        try
        {
            // Retrouver le nom de l'identifiant de clazz
            String id = getMetaData().getIdentifierName( getBusinessClass() );
            Iterator it = iterate( session, " where " + getAlias() + "." + id + " = '" + oid + "'" );
            if ( it != null && it.hasNext() )
            {
                obj = it.next();
            }
        }
        catch ( Exception e )
        {
            throwDAOException( e, "load" );
        }
        return obj;
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#refresh(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object)
     */
    public void refresh( ISession session, Object obj )
        throws JrafDaoException
    {
        try
        {
            ( (SessionImpl) session ).getSession().refresh( obj );
        }
        catch ( Exception e )
        {
            throwDAOException( e, "refresh" );
        }

    }

    /**
     * Force la lecture complète de l'objet qq soit le mapping (lasy true) Si l'objet existe sous forme de proxy, il est
     * remplacé par l'objet complet et mis en cache. Si l'object existe déjà dans le cache, il n'est pas relu.
     * 
     * @param session
     * @param clazz
     * @param id
     * @return
     * @throws JrafDaoException
     * @deprecated depuis JRAF v2.1. Cette methode va disparaitre. Utiliser la methode get(ISession, Serializable).
     */
    public Object get( ISession session, Class clazz, Serializable id )
        throws JrafDaoException
    {
        Object entity = null;
        try
        {
            entity = ( (SessionImpl) session ).getSession().get( clazz, id );

            if ( entity != null && ( entity instanceof HibernateProxy ) )
            {
                ( (SessionImpl) session ).getSession().evict( entity );
                entity = ( (SessionImpl) session ).getSession().get( clazz, id );
            }
        }
        catch ( HibernateException e )
        {
            throwDAOException( e, "get" );
        }
        return entity;
    }

    /**
     * Force la lecture complète de l'objet qq soit le mapping (lasy true) Si l'objet existe sous forme de proxy, il est
     * remplacé par l'objet complet et mis en cache. Si l'object existe déjà dans le cache, il n'est pas relu.
     * 
     * @param session session de persistance
     * @param id id de l'objet
     * @return l'objet trouve ou null
     * @throws JrafDaoException
     */
    public Object get( ISession session, Serializable id )
        throws JrafDaoException
    {
        Object entity = null;
        try
        {
            Class clazz = getBusinessClass();
            entity = ( (SessionImpl) session ).getSession().get( clazz, id );

            if ( entity != null && ( entity instanceof HibernateProxy ) )
            {
                ( (SessionImpl) session ).getSession().evict( entity );
                entity = ( (SessionImpl) session ).getSession().get( clazz, id );
            }
        }
        catch ( HibernateException e )
        {
            throwDAOException( e, "get" );
        }
        return entity;
    }

    /**
     * Charge un objet depuis la base avec une relation (eager-fetching). Si l'objet est deja dans le cache des proxy,
     * il est efface puis repris de la base.
     * 
     * @param session session de persistance
     * @param id id de l'objet a charge
     * @param relationName nom de la relation a charger
     * @return objet charge
     * @throws JrafDaoException
     */
    public Object getWithRelation( ISession session, String relationName, Serializable id )
        throws JrafDaoException
    {

        // resultat
        Object result = null;

        // recuperation de la session hibernate
        Session hSession = ( (SessionImpl) session ).getSession();

        // creation d'un critere de recherche
        Criteria c = hSession.createCriteria( getBusinessClass() );

        try
        {
            // on fixe le eager fetching sur la relation
            c.setFetchMode( relationName, FetchMode.JOIN );

            // critere de recherche
            c.add( Expression.eq( getMetaData().getIdentifierName( getBusinessClass() ), id ) );
            // un seul objet remonte
            result = c.uniqueResult();

            // si l'element charge est un proxy, on l'efface du cache
            // et on recharge l'objet
            if ( result != null && ( result instanceof HibernateProxy ) )
            {
                ( (SessionImpl) session ).getSession().evict( result );

                // on recree le critere
                c = hSession.createCriteria( getBusinessClass() );
                // on fixe le eager fetching sur la relation
                c.setFetchMode( relationName, FetchMode.JOIN );

                // critere de recherche
                c.add( Expression.eq( getMetaData().getIdentifierName( getBusinessClass() ), id ) );
                // un seul objet remonte
                result = c.uniqueResult();
            }

        }
        catch ( HibernateException e )
        {
            throwDAOException( e, "loadWithRelation" );
        }

        return result;
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#find(org.squale.jraf.spi.persistence.ISession,
     * java.lang.String)
     */
    public java.util.List find( ISession session, String lRequete )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        List liste = null;
        try
        {
            liste = sessionHibernate.getSession().createQuery( lRequete ).list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "find" );
        }
        return liste;
    }

    /**
     * With this method you could do a search restricted to a number of result to return.
     * 
     * @param session The hibernate session to use
     * @param lRequete The request
     * @param nbLignes The number of lines to retrieve
     * @param indexDepart The first line to retrieve
     * @return The results found
     * @throws JrafDaoException exception happened during the search
     */
    public java.util.List findScrollable( ISession session, String lRequete, int nbLignes, int indexDepart )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        List liste = null;
        try
        {
            Query q =
                sessionHibernate.getSession().createQuery( lRequete ).setFirstResult( indexDepart ).setMaxResults(
                                                                                                                   nbLignes );
            liste = q.list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "findScrollable" );
        }
        return liste;
    }

    /**
     * Execute la requete en valorisant les parametres. Les paramametres sont passes sous forme de tableau dans l'ordre
     * de la requete. Requête libre en langage OQL ou SQL suivant l'implémentation du moteur d'accès aux données. La
     * clause where doit comporter une clause where valide incluant le mot clé <b>Where</b <li>ie: 'where Avion = ?'</li>
     * 
     * @param session session a utiliser
     * @param lRequete requete HQL executee
     * @param values tableau de valeurs
     * @param types tableau des types
     * @return liste d'objets retrouves
     * @throws JrafDaoException exception
     */
    public java.util.List find( ISession session, String lRequete, Object[] values, Type[] types )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        List liste = null;
        try
        {
            Query query = sessionHibernate.getSession().createQuery( lRequete );
            query.setParameters( values, types );
            liste = query.list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "find" );
        }
        return liste;
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#find(org.squale.jraf.spi.persistence.ISession,
     * java.lang.String, java.lang.Object)
     */
    public java.util.List find( ISession session, String lRequete, Object bean )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        List liste = null;
        try
        {
            Query query = sessionHibernate.getSession().createQuery( lRequete );
            query.setProperties( bean );
            liste = query.list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "find" );
        }
        return liste;
    }

    /*
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#findAll(ISession)
     */
    public List findAll( ISession session )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        List liste = null;
        try
        {
            liste = sessionHibernate.getSession().createQuery( getRequete() ).list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "findAll" );
        }
        return liste;
    }

    /**
     * La construction de la requete utilise la méthode getRequete() pour initialiser le début de la clause.
     * 
     * @param session session a utiliser
     * @param whereClause requete HQL executee
     * @return liste d'objets trouves
     */
    public java.util.List findWhere( ISession session, String whereClause )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        List liste = null;
        try
        {
            liste = sessionHibernate.getSession().createQuery( getRequete() + whereClause ).list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "findWhere" );
        }
        return liste;
    }

    /**
     * La construction de la requete utilise la méthode getRequete() pour initialiser le début de la clause. Les
     * paramametres sont passes sous forme de tableau dans l'ordre de la requete.
     * 
     * @param session session a utiliser
     * @param whereClause requete HQL executee
     * @param values tableau de valeurs
     * @param types tableau des types
     * @return liste d'objets trouves
     */
    public java.util.List findWhere( ISession session, String whereClause, Object[] values, Type[] types )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        List liste = null;
        try
        {
            Query query = sessionHibernate.getSession().createQuery( getRequete() + whereClause );
            query.setParameters( values, types );
            liste = query.list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "findWhere" );
        }
        return liste;
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#findWhere(org.squale.jraf.spi.persistence.ISession,
     * java.lang.String, java.lang.Object)
     */
    public java.util.List findWhere( ISession session, String whereClause, Object bean )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        List liste = null;
        try
        {
            Query query = sessionHibernate.getSession().createQuery( getRequete() + whereClause );
            query.setProperties( bean );
            liste = query.list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "findWhere" );
        }
        return liste;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#findWhereScrollable(org.squale.jraf.spi.persistence.ISession
     * , java.lang.String, int, int, boolean)
     */
    public Object findWhereScrollable( ISession session, String whereClause, int nbLignes, int indexDepart,
                                       boolean bCaching )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        Object obj = null;
        try
        {
            // log.logPourDebug("requete:" + getRequete() + whereClause);
            Query q =
                sessionHibernate.getSession().createQuery( getRequete() + whereClause ).setFirstResult( indexDepart ).setMaxResults(
                                                                                                                                     nbLignes );
            if ( bCaching )
            {
                obj = q.iterate();
            }
            else
            {
                obj = q.list();
            }
        }
        catch ( Exception e )
        {
            throwDAOException( e, "findWhereScrollable" );
        }
        return obj;

    }

    /**
     * Method findWhereScrollable. Retourne nbligne de la requete passée en argument Si la clause where existe, elle
     * doit comporter une clause where valide incluant le mot clé <b>Where</b <li>ie: 'where Avion = ?'</li> Sinon, elle
     * peut être nulle dans ce cas il s'agit d'un select *
     * 
     * @param whereClause Requete a executer
     * @param session session utilisee
     * @param values Tableau des valeurs des parametres de la requete
     * @param types Tableau des types des parametres de la requete
     * @param nbLignes Nombre de lignes a retourner
     * @param indexDepart Index de lma premiere ligne a retourner
     * @param bCaching Si <b>true</b> retourne utilise l'API iterate() sinon list()
     * @return Object soit une List si bcaching = true. soit un Iterator si bcaching = false.
     * @throws JrafDaoException
     */
    public Object findWhereScrollable( ISession session, String whereClause, Object[] values, Type[] types,
                                       int nbLignes, int indexDepart, boolean bCaching )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        Object obj = null;
        try
        {
            // log.logPourDebug("requete:" + getRequete() + whereClause);
            Query q =
                sessionHibernate.getSession().createQuery( getRequete() + whereClause ).setFirstResult( indexDepart ).setMaxResults(
                                                                                                                                     nbLignes ).setParameters(
                                                                                                                                                               values,
                                                                                                                                                               types );
            if ( bCaching )
            {
                obj = q.iterate();
            }
            else
            {
                obj = q.list();
            }
        }
        catch ( Exception e )
        {
            throwDAOException( e, "findWhereScrollable" );
        }
        return obj;

    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#findWhereScrollable(org.squale.jraf.spi.persistence.ISession
     * , java.lang.String, int, int, boolean)
     */
    public Object findWhereScrollable( ISession session, String whereClause, Object bean, int nbLignes,
                                       int indexDepart, boolean bCaching )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        Object obj = null;
        try
        {
            // log.logPourDebug("requete:" + getRequete() + whereClause);
            Query q =
                sessionHibernate.getSession().createQuery( getRequete() + whereClause ).setFirstResult( indexDepart ).setMaxResults(
                                                                                                                                     nbLignes ).setProperties(
                                                                                                                                                               bean );
            if ( bCaching )
            {
                obj = q.iterate();
            }
            else
            {
                obj = q.list();
            }
        }
        catch ( Exception e )
        {
            throwDAOException( e, "findWhereScrollable" );
        }
        return obj;

    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#create(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object)
     */
    public void create( ISession session, Object obj )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        try
        {
            sessionHibernate.getSession().save( obj );
        }
        catch ( Exception e )
        {
            throwDAOException( e, "create" );
        }
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#save(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object)
     */
    public void save( ISession session, Object obj )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        try
        {
            sessionHibernate.getSession().saveOrUpdate( obj );
        }
        catch ( Exception e )
        {
            throwDAOException( e, "save" );
        }
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#remove(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object)
     */
    public void remove( ISession session, Object obj )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        try
        {
            sessionHibernate.getSession().delete( obj );
        }
        catch ( Exception e )
        {
            throwDAOException( e, "remove" );
        }
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#removeAll(org.squale.jraf.spi.persistence.ISession)
     */
    public void removeAll( ISession session )
        throws JrafDaoException
    {
        removeWhere( session, "" );
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * Supression d'un sous ensemble selectionné suivant une requete HQL. Si la clause where existe, elle doit etre une
     * clause where valide incluant le mot clé <b>Where</b <li>ie: 'where Avion = a'</li> Sinon, elle peut être nulle
     * dans ce cas il s'agit d'un delete *
     * 
     * @param session
     * @param whereClause clause where de la requete HQL.
     * @return nombre d'instance supprime
     * @throws JrafDaoException
     */
    public int removeWhere( ISession session, String whereClause )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        String req = "delete " + getClassName( getBusinessClass() ) + " " + getAlias() + " ";
        int nbInstanceSupprime = 0;
        try
        {
            nbInstanceSupprime = sessionHibernate.getSession().createQuery( req + whereClause ).executeUpdate();

        }
        catch ( Exception e )
        {
            throwDAOException( e, "removeAll" );
        }
        return nbInstanceSupprime;
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * Supression d'un sous ensemble selectionné suivant une requete HQL. Si la clause where existe, elle doit etre une
     * clause where valide incluant le mot clé <b>Where</b <li>ie: 'where Avion = ?'</li> Sinon, elle peut être nulle
     * dans ce cas il s'agit d'un delete *
     * 
     * @param session
     * @param whereClause clause where de la requete HQL.
     * @param values Tableau des valeurs des parametres de la requete
     * @param types Tableau des types des parametres de la requete
     * @return nombre d'instance supprime
     * @throws JrafDaoException
     */
    public int removeWhere( ISession session, String whereClause, Object[] values, Type[] types )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        String req = "delete " + getClassName( getBusinessClass() ) + " " + getAlias() + " ";
        int nbInstanceSupprime = 0;
        try
        {
            Query query = sessionHibernate.getSession().createQuery( req + whereClause );
            query.setParameters( values, types );
            nbInstanceSupprime = query.executeUpdate();

        }
        catch ( Exception e )
        {
            throwDAOException( e, "removeAll" );
        }
        return nbInstanceSupprime;
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#removeWhere(org.squale.jraf.spi.persistence.ISession,
     * java.lang.String, java.lang.Object)
     */
    public int removeWhere( ISession session, String whereClause, Object bean )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        String req = "delete " + getClassName( getBusinessClass() ) + " " + getAlias() + " ";
        int nbInstanceSupprime = 0;
        try
        {
            Query query = sessionHibernate.getSession().createQuery( req + whereClause );
            query.setProperties( bean );
            nbInstanceSupprime = query.executeUpdate();

        }
        catch ( Exception e )
        {
            throwDAOException( e, "removeAll" );
        }
        return nbInstanceSupprime;
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * Retourne un iterator sur le résultat d'une clause OQL. L'usage de cette méthode permet de ne pas forcer la
     * lecture complètes des objets. Par ailleurs les objets sans proxy sont mis dans le cache de la session. <br>
     * <br>
     * La clause where doit comporter une clause where valide incluant le mot clé <b>Where</b <li>ie: 'where Avion = a'</li>
     * <br>
     * <br>
     * <b>Rappel</b><br>
     * Si vous utilisez un proxy, aucun objet n'est instancié réellement la mise en cache aura lieu uniquement pour les
     * objets qui seront réellement concrétisés. <br>
     * 
     * @param sessionHibernate
     * @param whereClause
     * @return Iterator
     * @throws JrafDaoException
     */
    public Iterator iterate( ISession session, String whereClause )
        throws JrafDaoException
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        String req = null;
        Iterator iter = null;
        try
        {
            req = getRequete() + whereClause;
            iter = sessionImpl.getSession().createQuery( req ).iterate();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "iterate: " + req );
        }
        return iter;
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * Retourne un iterator sur le résultat d'une clause OQL. L'usage de cette méthode permet de ne pas forcer la
     * lecture complètes des objets. Par ailleurs les objets sans proxy sont mis dans le cache de la session. <br>
     * <br>
     * La clause where doit comporter une clause where valide incluant le mot clé <b>Where</b <li>ie: 'where Avion = ?'</li>
     * <br>
     * <br>
     * <b>Rappel</b><br>
     * Si vous utilisez un proxy, aucun objet n'est instancié réellement la mise en cache aura lieu uniquement pour les
     * objets qui seront réellement concrétisés. <br>
     * 
     * @param sessionHibernate
     * @param whereClause
     * @param values Tableau des valeurs des parametres de la requete
     * @param types Tableau des types des parametres de la requete
     * @return Iterator
     * @throws JrafDaoException
     */

    public Iterator iterate( ISession session, String whereClause, Object[] values, Type[] types )
        throws JrafDaoException
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        String req = null;
        Iterator iter = null;
        try
        {
            req = getRequete() + whereClause;
            iter = sessionImpl.getSession().createQuery( req ).setParameters( values, types ).iterate();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "iterate: " + req );
        }
        return iter;
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * Retourne un iterator sur le résultat d'une clause OQL. L'usage de cette méthode permet de ne pas forcer la
     * lecture complètes des objets. Par ailleurs les objets sans proxy sont mis dans le cache de la session. <br>
     * <br>
     * La clause where doit comporter une clause where valide incluant le mot clé <b>Where</b <li>ie: 'where Avion =
     * :avion'</li> <br>
     * <br>
     * <b>Rappel</b><br>
     * Si vous utilisez un proxy, aucun objet n'est instancié réellement la mise en cache aura lieu uniquement pour les
     * objets qui seront réellement concrétisés. <br>
     * 
     * @param sessionHibernate
     * @param whereClause
     * @param bean Bean ou sont lu les parametres de la requete
     * @return Iterator
     * @throws JrafDaoException
     */
    public Iterator iterate( ISession session, String whereClause, Object bean )
        throws JrafDaoException
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        String req = null;
        Iterator iter = null;
        try
        {
            req = getRequete() + whereClause;
            iter = sessionImpl.getSession().createQuery( req ).setProperties( bean ).iterate();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "iterate: " + req );
        }
        return iter;
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * Method filter. Restreint une liste suivant une clause Hql. Retourne une List
     * 
     * @param sessionHibernate
     * @param liste = List d'objets à restreindre
     * @param clause
     * @return Collection
     * @throws JrafDaoException
     */
    public List filter( ISession session, Object liste, String clause )
        throws JrafDaoException
    {
        List list = null;
        try
        {
            SessionImpl sessionImpl = (SessionImpl) session;
            list = sessionImpl.getSession().createFilter( liste, clause ).list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "filter" );
        }
        return list;
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * Method filter. Restreint une liste suivant une clause Hql. Retourne une List
     * 
     * @param sessionHibernate
     * @param liste = List d'objets à restreindre
     * @param clause
     * @param values Tableau des valeurs des parametres de la requete
     * @param types Tableau des types des parametres de la requete
     * @return List
     * @throws JrafDaoException
     */

    public List filter( ISession session, Object liste, String clause, Object[] values, Type[] types )
        throws JrafDaoException
    {
        List list = null;
        try
        {
            SessionImpl sessionImpl = (SessionImpl) session;
            list = sessionImpl.getSession().createFilter( liste, clause ).setParameters( values, types ).list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "filter" );
        }
        return list;
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * Method filter. Restreint une liste suivant une clause Hql. Retourne une List
     * 
     * @param sessionHibernate
     * @param liste = List d'objets à restreindre
     * @param clause
     * @param bean Bean ou sont lu les parametres de la requete
     * @return List
     * @throws JrafDaoException
     */
    public List filter( ISession session, Object liste, String clause, Object bean )
        throws JrafDaoException
    {
        List list = null;
        try
        {
            SessionImpl sessionImpl = (SessionImpl) session;
            list = sessionImpl.getSession().createFilter( liste, clause ).setProperties( bean ).list();
        }
        catch ( Exception e )
        {
            throwDAOException( e, "filter" );
        }
        return list;
    }

    /**
     * Générer une exception de niveau DAO
     * 
     * @param e
     * @param methodName
     * @throws JrafDaoException
     */
    protected void throwDAOException( Exception e, String methodName )
        throws JrafDaoException
    {
        log.error( "Erreur AbstractDAOImpl methode :" + methodName, e );
        throw new JrafDaoException( "Erreur AbstractDAOImpl methode :" + methodName, e );
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * la variable requete contient la partie fixe de la requete OQL par exemple <b> from Avion as avion</b> pour un DAO
     * de type AvionDAOImpl
     * 
     * @return String
     */
    public String getRequete()
    {
        if ( requete == null )
        {
            if ( getBusinessClass() != null )
            {
                requete = "from " + getClassName( getBusinessClass() ) + " as " + getAlias() + " ";
            }
        }
        return requete;
    }

    /**
     * Implémentation spécifique Hibernate<br>
     * la variable requete contient la partie fixe de la requete OQL par exemple <b> from Avion as avion</b> pour un DAO
     * de type AvionDAOImpl
     * 
     * @param req The requete to set
     */
    protected void setRequete( String req )
    {
        this.requete = req;
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#count(org.squale.jraf.spi.persistence.ISession)
     */
    public Integer count( ISession session )
        throws JrafDaoException
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        try
        {
            return ( (Integer) sessionImpl.getSession().createQuery( "select count(*) " + getRequete() ).iterate().next() );
        }
        catch ( Exception e )
        {
            throw new JrafDaoException( e.getMessage() );
        }
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#countWhere(org.squale.jraf.spi.persistence.ISession,
     * java.lang.String)
     */
    public Integer countWhere( ISession session, String whereClause )
        throws JrafDaoException
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        try
        {
            return ( (Integer) sessionImpl.getSession().createQuery( "select count(*) " + getRequete() + whereClause ).iterate().next() );
        }
        catch ( Exception e )
        {
            throw new JrafDaoException( "requete: select count(*) " + getRequete() + whereClause + " message: "
                + e.getMessage() );
        }
    }

    /**
     * Retourne le nombre d'objets en fonction de la clause where Si la clause where existe, elle doit comporter une
     * clause where valide incluant le mot clé <b>Where</b <li>ie: 'where Avion = ?'</li> Sinon, elle peut être nulle
     * dans ce cas il s'agit d'un <b>select *</b>
     * 
     * @param session
     * @param whereClause
     * @param values Tableau des valeurs des parametres de la requete
     * @param types Tableau des types hibernate des parametres de la requete (ex Hibernate.STRING)
     * @return Integer Nombre d'élément trouvé.
     * @throws JrafDaoException
     */

    public Integer countWhere( ISession session, String whereClause, Object[] values, Type[] types )
        throws JrafDaoException
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        try
        {
            return ( (Integer) sessionImpl.getSession().createQuery( "select count(*) " + getRequete() + whereClause ).setParameters(
                                                                                                                                      values,
                                                                                                                                      types ).iterate().next() );
        }
        catch ( Exception e )
        {
            throw new JrafDaoException( "requete: select count(*) " + getRequete() + whereClause + " message: "
                + e.getMessage() );
        }
    }

    /*
     * (non-Javadoc)
     * @see org.squale.jraf.spi.persistence.IPersistenceDAO#countWhere(org.squale.jraf.spi.persistence.ISession,
     * java.lang.String, java.lang.Object)
     */
    public Integer countWhere( ISession session, String whereClause, Object bean )
        throws JrafDaoException
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        try
        {
            return ( (Integer) sessionImpl.getSession().createQuery( "select count(*) " + getRequete() + whereClause ).setProperties(
                                                                                                                                      bean ).iterate().next() );
        }
        catch ( Exception e )
        {
            throw new JrafDaoException( "requete: select count(*) " + getRequete() + whereClause + " message: "
                + e.getMessage() );
        }
    }

    /**
     * Retourne l'alias
     * 
     * @return alias
     */
    protected String getAlias()
    {
        if ( alias == null )
        {
            alias = getClassName( getBusinessClass() ).toLowerCase();
        }
        return alias;
    }

    /**
     * @param string
     */
    private void setAlias( String string )
    {
        alias = string;
    }

    /**
     * Retourne la classe metier liee au DAO
     * 
     * @return classe metier liee au DAO
     */
    protected Class getBusinessClass()
    {
        return businessClass;
    }

    /**
     * Fixe le bo lie
     * 
     * @param class1 bo lie
     */
    private void setBusinessClass( Class class1 )
    {
        businessClass = class1;
    }

    /**
     * Retourne le nom court de la classe
     * 
     * @param class1 objet class
     * @return nom court de la classe
     */
    private static String getClassName( Class class1 )
    {
        int index = class1.getName().lastIndexOf( "." );
        return ( class1.getName().substring( index + 1 ) );
    }

    /**
     * Initialize le DAO Important : Cette methode ne doit etre appelee qu'une seule fois lors de la creation d'une
     * instance de DAO par le constructeur.
     * 
     * @param clazz classe metier liee
     * @param persistenceProvider provider de persistance
     */
    protected void initialize( Class clazz, IPersistenceProvider persistenceProvider )
    {

        // reinitialise
        clean();
        setBusinessClass( clazz );
        setPersistentProvider( persistenceProvider );
    }

    /**
     * Initialize le DAO. Utilise le provider de persistance par défaut. Important : Cette methode ne doit etre appelee
     * qu'une seule fois lors de la creation d'une instance de DAO par le constructeur.
     * 
     * @param clazz classe metier liee
     */
    protected void initialize( Class clazz )
    {
        initialize( clazz, PersistenceHelper.getPersistenceProvider() );
    }

    /**
     * Retourne les metadata du provider de persistance
     * 
     * @return metadata
     */
    protected IMetaData getMetaData()
    {
        return persistentProvider.getMetaData();
    }

    /**
     * Retourne le provider de persistance
     * 
     * @return provider de persistance
     */
    protected IPersistenceProvider getPersistentProvider()
    {
        return persistentProvider;
    }

    /**
     * Fixe le provider de persistance
     * 
     * @param provider
     */
    protected void setPersistentProvider( IPersistenceProvider provider )
    {
        persistentProvider = provider;
    }

    /**
     * Reinitialise l'ensemble des proprietes
     */
    private void clean()
    {
        setRequete( null );
        setBusinessClass( null );
        setAlias( null );
    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#findByExample(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object, int, int, boolean, boolean)
     */
    public List findByExample( ISession session, Object bo, int nbLignes, int indexDepart, boolean ignoreCase,
                               boolean likeMode )
        throws JrafDaoException
    {

        return findByExample( session, bo, nbLignes, indexDepart, ignoreCase, likeMode, false );

    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#findByExample(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object, int, int, boolean, boolean, boolean)
     */
    public List findByExample( ISession session, Object bo, int nbLignes, int indexDepart, boolean ignoreCase,
                               boolean likeMode, boolean cache )
        throws JrafDaoException
    {

        return findByExample( session, bo, nbLignes, indexDepart, ignoreCase, likeMode, cache, -1 );
    }

    /**
     * Execute un find a partir d'un objet exemple
     * 
     * @param session session de persistance
     * @param bo businness object exemple
     * @param nbLignes nombre de lignes
     * @param indexDepart index de depart
     * @param ignoreCase ignore la casse
     * @param likeMode mode like
     * @param cache true si les elements retournes sont mis en cache, false sinon
     * @return liste d'objets retrouves
     * @throws JrafDaoException
     */
    public List findByExample( ISession session, Object bo, int nbLignes, int indexDepart, boolean ignoreCase,
                               boolean likeMode, boolean cache, int matchMode )
        throws JrafDaoException
    {

        SessionImpl sessionImpl = (SessionImpl) session;
        Criteria crit = sessionImpl.getSession().createCriteria( bo.getClass() );

        Example example = Example.create( bo );

        // ignore la casse
        if ( ignoreCase )
        {
            example.ignoreCase();
        }

        // mode like
        if ( likeMode )
        {
            switch ( matchMode )
            {
                case 0:
                    example.enableLike( MatchMode.START );
                    break;
                case 1:
                    example.enableLike( MatchMode.END );
                    break;
                default:
                    example.enableLike( MatchMode.ANYWHERE );
                    break;
            }
        }

        // ajout de l'example
        crit.add( example );

        if ( indexDepart > -1 )
        {
            crit.setFirstResult( indexDepart );
        }

        if ( nbLignes > 0 )
        {
            crit.setMaxResults( nbLignes );
        }

        crit.setCacheable( cache );

        List l = null;
        try
        {
            l = crit.list();
        }
        catch ( HibernateException e )
        {
            throwDAOException( e, "findByExample" );

        }
        return l;

    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#findByExample(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object)
     */
    public List findByExample( ISession session, Object bo )
        throws JrafDaoException
    {

        return findByExample( session, bo, -1, -1, false, false, false );
    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#findByExample(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object, boolean, boolean)
     */
    public List findByExample( ISession session, Object bo, boolean ignoreCase, boolean likeMode )
        throws JrafDaoException
    {

        return findByExample( session, bo, -1, -1, ignoreCase, likeMode, false );
    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#findByExample(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object, int, int)
     */
    public List findByExample( ISession session, Object bo, int nbLignes, int indexDepart )
        throws JrafDaoException
    {

        return findByExample( session, bo, nbLignes, indexDepart, false, false, false );
    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#findByExample(org.squale.jraf.spi.persistence.ISession,
     * java.lang.Object, int, int, boolean)
     */
    public List findByExample( ISession session, Object bo, int nbLignes, int indexDepart, boolean cache )
        throws JrafDaoException
    {

        return findByExample( session, bo, nbLignes, indexDepart, false, false, cache );
    }

    /**
     * création d'une query à partir d'une requête sql, reprise d'une fonctionnalité d'Hibernate pour éviter d'avoir à
     * importer les packages Hibernate dans les DAO de l'application cf API de la classe org.hibernate.Session
     * 
     * @param session
     * @param sql
     * @return
     */
    public SQLQuery createSQLQuery( ISession session, String sql )
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        return sessionImpl.getSession().createSQLQuery( sql );
    }

    /**
     * création d'une query à partir d'une requête sql, reprise d'une fonctionnalité d'Hibernate pour éviter d'avoir à
     * importer les packages Hibernate dans les DAO de l'application cf API de la classe org.hibernate.Session
     * 
     * @param session
     * @param sql
     * @param returnAliases
     * @param returnClasses
     * @return
     */
    public SQLQuery createSQLQuery( ISession session, String sql, String[] returnAliases, Class[] returnClasses )
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        SQLQuery query = sessionImpl.getSession().createSQLQuery( sql );
        for ( int i = 0; i < returnAliases.length; i++ )
        {
            query.addEntity( returnAliases[i], returnClasses[i] );
        }
        return query;
    }

    /**
     * création d'une query à partir d'une requête sql, reprise d'une fonctionnalité d'Hibernate pour éviter d'avoir à
     * importer les packages Hibernate dans les DAO de l'application cf API de la classe org.hibernate.Session
     * 
     * @param session
     * @param sql
     * @param returnAlias
     * @param returnClass
     * @return
     */
    public SQLQuery createSQLQuery( ISession session, String sql, String returnAlias, Class returnClass )
    {
        SessionImpl sessionImpl = (SessionImpl) session;
        return sessionImpl.getSession().createSQLQuery( sql ).addEntity( returnAlias, returnClass );
    }

    /*
     * (non-Javadoc)
     * @see
     * org.squale.jraf.spi.persistence.IPersistenceDAO#loadFromSession(org.squale.jraf.spi.persistence.ISession,
     * java.io.Serializable)
     */
    public Object loadFromSession( ISession session, Serializable oid )
        throws JrafDaoException
    {
        SessionImpl sessionHibernate = (SessionImpl) session;
        Object loaded = null;
        try
        {
            loaded = sessionHibernate.getSession().load( getBusinessClass(), oid );
        }
        catch ( ObjectNotFoundException e )
        {
            // Eliminer le cas ou on a une proxy deja lu.
            if ( log.isDebugEnabled() )
            {
                log.debug( "l'objet " + getBusinessClass() + "." + oid + " n'a pas ete trouve : " + e.getMessage() );
            }

        }
        catch ( HibernateException e )
        {
            // autre type d'exception
            throwDAOException( e, "loadFromSession" );
            throw new JrafDaoException( e );
        }
        return loaded;

    }
}
