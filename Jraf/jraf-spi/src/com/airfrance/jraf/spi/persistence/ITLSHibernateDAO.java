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
package org.squale.jraf.spi.persistence;

import java.io.Serializable;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.spi.daolayer.IDAO;
import org.squale.jraf.spi.persistence.ISession;

/**
 * 
 * <p>Title : ITLSHibernateDAO.java</p>
 * <p>Description : Interface décrivant le comportement 
 * d'un DAO de persistance.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface ITLSHibernateDAO extends IDAO {

	/**
	 * Rafraichir un objet du cache session depuis la base
	 * L'objet doit posséder un identifiant valide.
	 * @param obj: Objet a lire.
	 * @throws JrafDaoException
	 */
	public abstract void refresh(Object obj) throws JrafDaoException;
	/**
	 * Find utilisant un objet exemple: 
	 * le find est effectue suivant les proprietes simples de l'objet en parametre
	 * @param bo businness object
	 * @return List des elements trouves
	 * @throws JrafDaoException
	 */
	public abstract List findByExample(Object bo) throws JrafDaoException;
	/**
	 * Find utilisant un objet exemple: 
	 * le find est effectue suivant les proprietes simples de l'objet en parametre
	 * @param bo businness object
	 * @param ignoreCase ignore la casse
	 * @param likeMode mode like
	 * @return List des elements trouves
	 * @throws JrafDaoException
	 */
	public abstract List findByExample(
		Object bo,
		boolean ignoreCase,
		boolean likeMode)
		throws JrafDaoException;
	/**
	 * Find utilisant un objet exemple: 
	 * le find est effectue suivant les proprietes simples de l'objet en parametre
	 * @param bo businness object
	 * @param nbLignes nombre de lignes
	 * @param indexDepart index de depart
	 * @return List des elements trouves
	 * @throws JrafDaoException
	 */
	public abstract List findByExample(
		Object bo,
		int nbLignes,
		int indexDepart)
		throws JrafDaoException;
	/**
	 * Execute un find a partir d'un objet exemple
	 * @param bo businness object exemple
	 * @param nbLignes nombre de lignes
	 * @param indexDepart index de depart
	 * @param ignoreCase ignore la casse
	 * @param likeMode mode like
	 * @return liste d'objets retrouves
	 * @throws JrafDaoException
	 */
	public abstract List findByExample(
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean ignoreCase,
		boolean likeMode)
		throws JrafDaoException;

	/**
	 * Find utilisant un objet exemple: 
	 * le find est effectue suivant les proprietes simples de l'objet en parametre
	 * @param bo businness object
	 * @param nbLignes nombre de lignes
	 * @param indexDepart index de depart
	 * @param cache true pour placer les elements retournes en cache session, false sinon
	 * @return List des elements trouves
	 * @throws JrafDaoException
	 */
	public List findByExample(
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean cache)
		throws JrafDaoException;
	/**
	 * Execute un find a partir d'un objet exemple
	 * @param bo businness object exemple
	 * @param nbLignes nombre de lignes
	 * @param indexDepart index de depart
	 * @param ignoreCase ignore la casse
	 * @param likeMode mode like
	 * @param cache true si les elements retournes sont mis en cache, false sinon
	 * @return liste d'objets retrouves
	 * @throws JrafDaoException
	 */
	public abstract List findByExample(
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean ignoreCase,
		boolean likeMode,
		boolean cache)
		throws JrafDaoException;

	/**
	 * Creation de l'objet métier persistant.
	 * @param objMetier
	 * @throws JrafDaoException
	 */
	void create(Object objMetier) throws JrafDaoException;

	/**
	 * Lecture d'un objet. Lecture depuis le cache ou la base.
	 * @param id id de l'objet  
	 * @throws JrafDaoException
	 */
	Object get(Serializable id) throws JrafDaoException;

	/**
	 * Lecture depuis la session courante. <br>
	 * Cette méthode suppose que l'objet est présent dans le cache de la session .
	 * Lecture simple.
	 * <li>si l'id n'est pas complet génère une exception.
	 * <li>Si l'objet n'est pas dans le cache/référencé par un proxy génère une
	 * exception.
	 * <li>Si l'objet est marqué comme étant un proxy au niveau du mapping il
	 * est utilisé.
	 * <li>Si l'objet est déja référencé par un proxy rien ne se passe.
	 * @param oid id de l'objet
	 * @throws JrafDaoException
	 */
	public Object loadFromSession(Serializable oid) throws JrafDaoException;

	/**
	 * Lecture d'un objet a partir de sa cle
	 * @param oid cle
	 * @return objet charge
	 * @throws JrafDaoException
	 */
	public Object load(Serializable oid) throws JrafDaoException;

	/**
	 * Suppression d'un objet dont l'identifiant est complet.
	 * @param objMetier
	 * @throws JrafDaoException
	 */
	void remove(Object objMetier) throws JrafDaoException;

	/**
	 * Suppression de l'ensemble des entités d'une classe donnée.
	 * @throws JrafDaoException
	 */
	void removeAll() throws JrafDaoException;

	/**
	 * Supression d'un sous ensemble selectionné suivant une requete HQL.
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param whereClause clause where de la requete HQL.
	 * @return Nombre d'instances supprimees
	 * @throws JrafDaoException
	 */
	int removeWhere(String whereClause) throws JrafDaoException;

	/**
	 * Supression d'un sous ensemble selectionné suivant une requete HQL.
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param whereClause clause where de la requete HQL.
	 * @param bean Bean ou sont lus les valeurs des parametres de la requete  
	 * @return Nombre d'instances supprimees
	 * @throws JrafDaoException
	 */
	int removeWhere(String whereClause, Object bean) throws JrafDaoException;

	/**
	 * Création ou sauvegarde de l'objet en fonction du paramètrage défini dans le fichier de mapping
	 * @param objMetier
	 * @throws JrafDaoException
	 */
	void save(Object objMetier) throws JrafDaoException;

	/**
	 * Requête libre en langage OQL ou SQL suivant l'implémentation du moteur d'accès aux données.
	 * La clause where doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * @param requete
	 * @return List
	 * @throws JrafDaoException
	 */
	List find(String requete) throws JrafDaoException;

	/**
	 * Method findAll. Remonte une liste complète d'objets métiers.
	 * @param requete
	 * @return List
	 * @throws JrafDaoException
	 */
	List findAll() throws JrafDaoException;

	/**
	 * Method findWhereScrollable. Retourne nbligne de la requete passée en argument
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param whereClause 
	 * @param nbLignes
	 * @param indexDepart
	 * @param bCaching: Si <b>true</b> retourne utilise l'API iterate() sinon list() 
	 * @return Object soit une List si bcaching = true.
	 *                 soit un Iterator si bcaching = false.
	 */
	Object findWhereScrollable(
		String whereClause,
		int nbLignes,
		int indexDepart,
		boolean bCaching)
		throws JrafDaoException;
		
	/**
	 * Method findWhereScrollable. Retourne nbligne de la requete passée en argument
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param whereClause 
	 * @param bean Bean ou sont lus les valeurs des parametres de la requete
	 * @param nbLignes
	 * @param indexDepart
	 * @param bCaching: Si <b>true</b> retourne utilise l'API iterate() sinon list() 
	 * @return Object soit une List si bcaching = true.
	 *                 soit un Iterator si bcaching = false.
	 */
	public Object findWhereScrollable(
		String whereClause,
		Object bean,
		int nbLignes,
		int indexDepart,
		boolean bCaching)
		throws JrafDaoException ;
	/**
	 * Method findWhereScrollable. Retourne nbligne de la requete passée en argument
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param whereClause 
	 * @param nbLignes
	 * @param indexDepart
	 * @return Liste des elements trouves
	 */
	List findWhereScrollable(
		String whereClause,
		int nbLignes,
		int indexDepart)
		throws JrafDaoException;

	/**
	 * Retourne une liste d'objet fonction de la clause where.
	 * @param whereClause: clause avec le mot clef "where" en premier
	 * @return List
	 * @throws JrafDaoException
	 */
	List findWhere(String whereClause) throws JrafDaoException;

	/**
	 * Retourne une liste d'objet fonction de la clause where.
	 * @param whereClause: clause avec le mot clef "where" en premier
	 * @param bean Bean ou sont lu les parametres de la requete
	 * @return List
	 * @throws JrafDaoException
	 */
	List findWhere(String whereClause, Object bean) throws JrafDaoException;

	/**
	 * Retourne le nombre d'occurence totale d'un objet en
	 * base.
	 * @return Integer (null si pb).
	 * @throws JrafDaoException
	 */
	 Integer count() throws JrafDaoException;

	/**
	 * Retourne le nombre d'objets en fonction de la clause where
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un <b>select *</b>
	 * @param whereClause
	 * @return Integer Nombre d'élément trouvé.
	 * @throws JrafDaoException
	 */
	 Integer countWhere(String whereClause) throws JrafDaoException;

	/**
	 * Retourne le nombre d'objets en fonction de la clause where
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un <b>select *</b>
	 * @param whereClause
	 * @param bean Bean ou sont lu les parametres de la requete  
	 * @return Integer Nombre d'élément trouvé.
	 * @throws JrafDaoException
	 */
	 Integer countWhere(String whereClause, Object bean)
		throws JrafDaoException;

}
