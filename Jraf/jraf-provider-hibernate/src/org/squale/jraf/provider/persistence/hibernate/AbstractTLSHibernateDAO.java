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
 * Cree le 31 janv. 05
 */
package org.squale.jraf.provider.persistence.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.spi.initializer.IInitializableBean;
import org.squale.jraf.spi.persistence.IMetaData;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.jraf.spi.persistence.ITLSHibernateDAO;

/**
 * <p>Project: JRAF 
 * <p>Title : AbstractTLSHibernateDAO</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 *  
 */
public abstract class AbstractTLSHibernateDAO
	extends AbstractDAOImpl
	implements ITLSHibernateDAO, IInitializableBean {

	/** logger */
	private final static Log log =
		LogFactory.getLog(AbstractTLSHibernateDAO.class);

	/** classe du bo lie */
	private Class businessClass = null;

	/** provider de persistance */
	private IPersistenceProvider persistenceProvider;

	/** alias du au bo lie */
	private String alias = null;

	/**
	 * variable contenant le début d'une requete. 
	 * ex: pour une classe Foo nous aurons <b>from Foo as foo</b>
	 */
	private String requete = null;

	/**
	 * Constructeur avec parametres type IOC3
	 * @param businessClass bo associe
	 * @param persistenceProvider provider de persistance
	 */
	public AbstractTLSHibernateDAO(
		Class clazz,
		IPersistenceProvider provider) {
		setBusinessClass(clazz);
		setPersistenceProvider(provider);
		afterPropertiesSet();

	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param businessClass bo associe
	 */
	public AbstractTLSHibernateDAO(Class clazz) {
		this(clazz, null);
	}

	/**
	 * Constructeur vide type IOC2
	 */
	public AbstractTLSHibernateDAO() {

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#count()
	 */
	public Integer count() throws JrafDaoException {
		return count(getPersistenceProvider().getSession());
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#countWhere(java.lang.String)
	 */
	public Integer countWhere(String whereClause) throws JrafDaoException {
		return countWhere(getPersistenceProvider().getSession(), whereClause);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.persistence.ITLSHibernateDAO#countWhere(java.lang.String, java.lang.Object)
	 */
	public Integer countWhere(String whereClause, Object bean)
		throws JrafDaoException {
		return countWhere(
			getPersistenceProvider().getSession(),
			whereClause,
			bean);
	}

	/**
	 * Retourne le nombre d'objets en fonction de la clause where
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = ?'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un <b>select *</b>
	 * @param whereClause
     * @param values Tableau des valeurs des parametres de la requete
	 * @param types Tableau des types hibernate des parametres de la requete (ex Hibernate.STRING) 
	 * @return Integer Nombre d'élément trouvé.
	 * @throws JrafDaoException
	 */

	public Integer countWhere(
		String whereClause,
		Object[] values,
		Type[] types)
		throws JrafDaoException {
		return countWhere(
			getPersistenceProvider().getSession(),
			whereClause,
			values,
			types);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#create(java.lang.Object)
	 */
	public void create(Object objMetier) throws JrafDaoException {
		create(getPersistenceProvider().getSession(), objMetier);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#find(java.lang.String)
	 */
	public List find(String req) throws JrafDaoException {
		return find(getPersistenceProvider().getSession(), req);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#findAll()
	 */
	public List findAll() throws JrafDaoException {
		return findAll(getPersistenceProvider().getSession());
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#findByExample(java.lang.Object, boolean, boolean)
	 */
	public List findByExample(Object bo, boolean ignoreCase, boolean likeMode)
		throws JrafDaoException {
		return findByExample(bo, -1, -1, ignoreCase, likeMode);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#findByExample(java.lang.Object, int, int, boolean, boolean)
	 */
	public List findByExample(
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean ignoreCase,
		boolean likeMode)
		throws JrafDaoException {
		return findByExample(
			bo,
			nbLignes,
			indexDepart,
			ignoreCase,
			likeMode,
			false);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#findByExample(java.lang.Object, int, int)
	 */
	public List findByExample(Object bo, int nbLignes, int indexDepart)
		throws JrafDaoException {
		return findByExample(bo, nbLignes, indexDepart, false, false);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#findByExample(java.lang.Object)
	 */
	public List findByExample(Object bo) throws JrafDaoException {

		return findByExample(bo, -1, -1, false, false);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#findWhere(java.lang.String)
	 */
	public List findWhere(String whereClause) throws JrafDaoException {
		return findWhere(getPersistenceProvider().getSession(), whereClause);
	}

	/**
	 * Retourne une liste d'objet fonction de la clause where.
	 * @param whereClause: clause avec le mot clef "where" en premier
	 * @param values Tableau des valeurs des parametres de la requete
	 * @param types Tableau des types des parametres de la requete 
	 * @return List
	 * @throws JrafDaoException
	 */
	public java.util.List findWhere(
		String whereClause,
		Object[] values,
		Type[] types)
		throws JrafDaoException {
		return findWhere(
			getPersistenceProvider().getSession(),
			whereClause,
			values,
			types);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.persistence.ITLSHibernateDAO#findWhere(java.lang.String, java.lang.Object)
	 */
	public java.util.List findWhere(String whereClause, Object bean)
		throws JrafDaoException {
		return findWhere(
			getPersistenceProvider().getSession(),
			whereClause,
			bean);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#findWhereScrollable(java.lang.String, int, int, boolean)
	 */
	public Object findWhereScrollable(
		String whereClause,
		int nbLignes,
		int indexDepart,
		boolean bCaching)
		throws JrafDaoException {
		return findWhereScrollable(
			getPersistenceProvider().getSession(),
			whereClause,
			nbLignes,
			indexDepart,
			bCaching);
	}

	/**
	 * Method findWhereScrollable. Retourne nbligne de la requete passée en argument
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = ?'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param whereClause  Requete a executer
	 * @param values Tableau des valeurs des parametres de la requete
	 * @param types Tableau des types des parametres de la requete 
	 * @param nbLignes Nombre de lignes a retourner
	 * @param indexDepart Index de lma premiere ligne a retourner
	 * @param bCaching Si <b>true</b> retourne utilise l'API iterate() sinon list() 
	 * @return Object soit une List si bcaching = true.
	 *                 soit un Iterator si bcaching = false.
	 * @throws JrafDaoException
	 */
	public Object findWhereScrollable(
		String whereClause,
		Object[] values,
		Type[] types,
		int nbLignes,
		int indexDepart,
		boolean bCaching)
		throws JrafDaoException {
		return findWhereScrollable(
			getPersistenceProvider().getSession(),
			whereClause,
			values,
			types,
			nbLignes,
			indexDepart,
			bCaching);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.persistence.ITLSHibernateDAO#findWhereScrollable(java.lang.String, java.lang.Object, int, int, boolean)
	 */
	public Object findWhereScrollable(
		String whereClause,
		Object bean,
		int nbLignes,
		int indexDepart,
		boolean bCaching)
		throws JrafDaoException {
		return findWhereScrollable(
			getPersistenceProvider().getSession(),
			whereClause,
			bean,
			nbLignes,
			indexDepart,
			bCaching);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#get(java.io.Serializable)
	 */
	public Object get(Serializable id) throws JrafDaoException {
		ISession session = getPersistenceProvider().getSession();
		return get(session, id);
	}

	/**
	 * Implémentation spécifique Hibernate<br>
	 * Retourne un iterator sur le résultat d'une clause OQL. L'usage de cette méthode
	 * permet de ne pas forcer la lecture complètes des objets. Par ailleurs les objets
	 * sans proxy sont mis dans le cache de la session.
	 * <br><br>
	 * La clause where doit comporter une clause where valide incluant le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * <br><br>
	 * <b>Rappel</b><br>
	 * Si vous utilisez un proxy, aucun objet n'est instancié réellement la mise en cache aura lieu uniquement
	 * pour les objets qui seront réellement concrétisés.
	 * <br>
	 * @param sessionHibernate
	 * @param whereClause
	 * @return Iterator
	 * @throws JrafDaoException
	 */
	public Iterator iterate(String whereClause) throws JrafDaoException {
		return iterate(getPersistenceProvider().getSession(), whereClause);
	}

	/**
	 * Implémentation spécifique Hibernate<br>
	 * Retourne un iterator sur le résultat d'une clause OQL. L'usage de cette méthode
	 * permet de ne pas forcer la lecture complètes des objets. Par ailleurs les objets
	 * sans proxy sont mis dans le cache de la session.
	 * <br><br>
	 * La clause where doit comporter une clause where valide incluant le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = ?'</li>
	 * <br><br>
	 * <b>Rappel</b><br>
	 * Si vous utilisez un proxy, aucun objet n'est instancié réellement la mise en cache aura lieu uniquement
	 * pour les objets qui seront réellement concrétisés.
	 * <br>
	 * @param whereClause
	* @param values Tableau des valeurs des parametres de la requete
	* @param types Tableau des types des parametres de la requete 
	 * @return Iterator
	 * @throws JrafDaoException
	 */
	public Iterator iterate(String whereClause, Object[] values, Type[] types)
		throws JrafDaoException {
		return iterate(
			getPersistenceProvider().getSession(),
			whereClause,
			values,
			types);
	}

	/**
	 * Implémentation spécifique Hibernate<br>
	 * Retourne un iterator sur le résultat d'une clause OQL. L'usage de cette méthode
	 * permet de ne pas forcer la lecture complètes des objets. Par ailleurs les objets
	 * sans proxy sont mis dans le cache de la session.
	 * <br><br>
	 * La clause where doit comporter une clause where valide incluant le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = :avion'</li>
	 * <br><br>
	 * <b>Rappel</b><br>
	 * Si vous utilisez un proxy, aucun objet n'est instancié réellement la mise en cache aura lieu uniquement
	 * pour les objets qui seront réellement concrétisés.
	 * <br>
	 * @param whereClause
	* @param bean Bean ou sont lu les parametres de la requete  
	 * @return Iterator
	 * @throws JrafDaoException
	 */
	public Iterator iterate(String whereClause, Object bean)
		throws JrafDaoException {
		return iterate(
			getPersistenceProvider().getSession(),
			whereClause,
			bean);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#load(java.io.Serializable)
	 */
	public Object load(Serializable oid) throws JrafDaoException {
		Object obj = load(getPersistenceProvider().getSession(), oid);
		return obj;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#loadFromSession(java.io.Serializable)
	 */
	public Object loadFromSession(Serializable oid) throws JrafDaoException {
		Object loaded =
			loadFromSession(getPersistenceProvider().getSession(), oid);
		return loaded;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#refresh(java.lang.Object)
	 */
	public void refresh(Object obj) throws JrafDaoException {
		refresh(getPersistenceProvider().getSession(), obj);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#remove(java.lang.Object)
	 */
	public void remove(Object objMetier) throws JrafDaoException {
		remove(getPersistenceProvider().getSession(), objMetier);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#removeAll()
	 */
	public void removeAll() throws JrafDaoException {
		removeAll(getPersistenceProvider().getSession());

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#removeWhere(java.lang.String)
	 */
	public int removeWhere(String whereClause) throws JrafDaoException {
		return removeWhere(getPersistenceProvider().getSession(), whereClause);

	}

	/**
	 * Implémentation spécifique Hibernate<br>
	 * Supression d'un sous ensemble selectionné suivant une requete HQL.
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = ?'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param session
	 * @param whereClause clause where de la requete HQL.
	 * @param values Tableau des valeurs des parametres de la requete
	 * @param types Tableau des types des parametres de la requete 
	 * @return nombre d'instance supprime
	 * @throws JrafDaoException
	 */
	public int removeWhere(String whereClause, Object[] values, Type[] types)
		throws JrafDaoException {
		return removeWhere(
			getPersistenceProvider().getSession(),
			whereClause,
			values,
			types);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.persistence.ITLSHibernateDAO#removeWhere(java.lang.String, java.lang.Object)
	 */
	public int removeWhere(String whereClause, Object bean)
		throws JrafDaoException {
		return removeWhere(
			getPersistenceProvider().getSession(),
			whereClause,
			bean);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.persistence.hibernate.tls.spi.IPersistenceDAOTLS#save(java.lang.Object)
	 */
	public void save(Object objMetier) throws JrafDaoException {
		save(getPersistenceProvider().getSession(), objMetier);

	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		if (getBusinessClass() == null) {
			throw new JrafConfigException("Le bo lie ne peut etre null");
		}

		if (getPersistenceProvider() == null) {
			setPersistenceProvider(PersistenceHelper.getPersistenceProvider());
		}

		// on fixe l'alias
		setAlias(getClassName(getBusinessClass()).toLowerCase());

		// on fixe la requete
		setRequete(
			"from "
				+ getClassName(getBusinessClass())
				+ " as "
				+ getAlias()
				+ " ");

	}

	/**
	 * Retourne la classe du bo lie
	 * @return classe du bo lie
	 */
	public Class getBusinessClass() {
		return businessClass;
	}

	/**
	 * Retourne le provider de persistance
	 * @return provider de persistance
	 */
	public IPersistenceProvider getPersistenceProvider() {
		return persistenceProvider;
	}

	/**
	 * Fixe le bo lie
	 * @param class1 bo lie
	 */
	public void setBusinessClass(Class class1) {
		businessClass = class1;
	}

	/**
	 * Fixe le provider de persistance
	 * @param provider provider de persistance
	 */
	public void setPersistenceProvider(IPersistenceProvider provider) {
		persistenceProvider = provider;
	}

	/**
	 * Retourne l'alias
	 * @return alias
	 */
	protected String getAlias() {
		return alias;
	}

	/**
	 * Fixe la requete
	 * @return requete
	 */
	public String getRequete() {
		return requete;
	}

	/**
	 * Fixe l'alias
	 * @param string alias
	 */
	protected void setAlias(String string) {
		alias = string;
	}

	/**
	 * Fixe la requete
	 * @param string requete
	 */
	protected void setRequete(String string) {
		requete = string;
	}

	/**
	 * Retourne le nom court de la classe
	 * @param class1 objet class
	 * @return nom court de la classe
	 */
	private static String getClassName(Class class1) {
		int index = class1.getName().lastIndexOf(".");
		return (class1.getName().substring(index + 1));
	}

	/**
	 * Générer une exception de niveau DAO
	 * @param e
	 * @param methodName
	 * @throws JrafDaoException
	 */
	protected void throwDAOException(Exception e, String methodName)
		throws JrafDaoException {
		log.error("Erreur AbstractDAOImpl methode :" + methodName, e);
		throw new JrafDaoException(
			"Erreur AbstractDAOImpl methode :" + methodName,
			e);
	}

	/**
	 * Retourne les meta donnees de persistance
	 * @return meta donnees de persistance
	 */
	protected IMetaData getMetaData() {
		return getPersistenceProvider().getMetaData();
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.persistence.ITLSHibernateDAO#findByExample(java.lang.Object, int, int, boolean, boolean, boolean)
	 */
	public List findByExample(
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean ignoreCase,
		boolean likeMode,
		boolean cache)
		throws JrafDaoException {

		List l =
			findByExample(
				getPersistenceProvider().getSession(),
				bo,
				nbLignes,
				indexDepart,
				ignoreCase,
				likeMode,
				cache);
		return l;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.persistence.ITLSHibernateDAO#findByExample(java.lang.Object, int, int, boolean)
	 */
	public List findByExample(
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean cache)
		throws JrafDaoException {
		return findByExample(bo, nbLignes, indexDepart, false, false, cache);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.persistence.ITLSHibernateDAO#findWhereScrollable(java.lang.String, int, int)
	 */
	public List findWhereScrollable(
		String whereClause,
		int nbLignes,
		int indexDepart)
		throws JrafDaoException {

		return (List) findWhereScrollable(
			whereClause,
			nbLignes,
			indexDepart,
			false);
	}

}
