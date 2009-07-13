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

/**
 * 
 * <p>Title : IPersistenceDAO.java</p>
 * <p>Description : Interface décrivant le comportement 
 * d'un DAO de persistance.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public interface IPersistenceDAO extends IDAO {
	/**
	 * Rafraichir un objet du cache session depuis la base
	 * L'objet doit posséder un identifiant valide.
	 * @param session
	 * @param obj: Objet a lire.
	 * @throws JrafDaoException
	 */
	public abstract void refresh(ISession session, Object obj)
		throws JrafDaoException;
	/**
	 * Find utilisant un objet exemple: 
	 * le find est effectue suivant les proprietes simples de l'objet en parametre
	 * @param session session de persistance
	 * @param bo businness object
	 * @return List des elements trouves
	 * @throws JrafDaoException
	 */
	public abstract List findByExample(ISession session, Object bo)
		throws JrafDaoException;
	/**
	 * Find utilisant un objet exemple: 
	 * le find est effectue suivant les proprietes simples de l'objet en parametre
	 * @param session session de persistance
	 * @param bo businness object
	 * @param ignoreCase ignore la casse
	 * @param likeMode mode like
	 * @return List des elements trouves
	 * @throws JrafDaoException
	 */
	public abstract List findByExample(
		ISession session,
		Object bo,
		boolean ignoreCase,
		boolean likeMode)
		throws JrafDaoException;
	/**
	 * Find utilisant un objet exemple: 
	 * le find est effectue suivant les proprietes simples de l'objet en parametre
	 * @param session session de persistance
	 * @param bo businness object
	 * @param nbLignes nombre de lignes
	 * @param indexDepart index de depart
	 * @return List des elements trouves
	 * @throws JrafDaoException
	 */
	public abstract List findByExample(
		ISession session,
		Object bo,
		int nbLignes,
		int indexDepart)
		throws JrafDaoException;
	/**
	 * Execute un find a partir d'un objet exemple
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
	public abstract List findByExample(
		ISession session,
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean ignoreCase,
		boolean likeMode,
		boolean cache)
		throws JrafDaoException;

	/**
	 * Find utilisant un objet exemple: 
	 * le find est effectue suivant les proprietes simples de l'objet en parametre
	 * @param session session de persistance
	 * @param bo businness object
	 * @param nbLignes nombre de lignes
	 * @param indexDepart index de depart
	 * @param cache true pour placer les elements retournes en cache session, false sinon
	 * @return List des elements trouves
	 * @throws JrafDaoException
	 */
	public List findByExample(
		ISession session,
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean cache)
		throws JrafDaoException;

	/**
	 * Execute un find a partir d'un objet exemple.
	 * @param session session de persistance
	 * @param bo businness object exemple
	 * @param nbLignes nombre de lignes
	 * @param indexDepart index de depart
	 * @param ignoreCase ignore la casse
	 * @param likeMode mode like
	 * @return liste d'objets retrouves
	 * @throws JrafDaoException
	 */
	public List findByExample(
		ISession session,
		Object bo,
		int nbLignes,
		int indexDepart,
		boolean ignoreCase,
		boolean likeMode)
		throws JrafDaoException;

	/**
	 * Creation de l'objet métier persistant.
	 * @param objMetier
	 * @param session
	 * @throws JrafDaoException
	 */
	void create(ISession session, Object objMetier) throws JrafDaoException;

	/**
	 * Lecture d'un objet. Lecture depuis le cache ou la base.
	 * @param session session de persistance 
	 * @param id id de l'objet  
	 * @throws JrafDaoException
	 */
	Object get(ISession session, Serializable id) throws JrafDaoException;

	/**
	 * Lecture depuis la session courante. <br>
	 * Cette méthode suppose que l'objet est présent dans le cache de la session .
	 * Lecture simple.
	 * <li>si l'id n'est pas complet génère une exception.
	 * <li>Si l'objet n'est pas dans le cache/référencé par un proxy sur la classe génère une
	 * exception si lazy=false, sinon cree un proxy
	 * <li>Si l'objet est marqué comme étant un proxy au niveau du mapping il
	 * est utilisé.
	 * <li>Si l'objet est déja référencé par un proxy rien ne se passe.
	 * You should not use this method to determine if an instance exists (use get() instead). Use this only to retrieve an instance that you assume exists, where non-existence would be an actual error. 
	 * @param oid id de l'objet
	 * @throws JrafDaoException
	 */
	public Object loadFromSession(ISession session, Serializable oid)
		throws JrafDaoException;

	/**
	 * Lecture d'un objet a partir de sa cle
	 * @param session session de persistance
	 * @param oid cle
	 * @return objet charge
	 * @throws JrafDaoException
	 */
	public Object load(ISession session, Serializable oid)
		throws JrafDaoException;

	/**
	 * Suppression d'un objet dont l'identifiant est complet.
	 * @param objMetier
	 * @param session
	 * @throws JrafDaoException
	 */
	void remove(ISession session, Object objMetier) throws JrafDaoException;

	/**
	 * Suppression de l'ensemble des entités d'une classe donnée.
	 * @param session
	 * @throws JrafDaoException
	 */
	void removeAll(ISession session) throws JrafDaoException;

	/**
	 * Supression d'un sous ensemble selectionné suivant une requete HQL.
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param session
	 * @param whereClause clause where de la requete HQL.
	 * @return nombre d'instance supprime
	 * @throws JrafDaoException
	 */
	int removeWhere(ISession session, String whereClause)
		throws JrafDaoException;

	/**
	 * Implémentation spécifique Hibernate<br>
	 * Supression d'un sous ensemble selectionné suivant une requete HQL.
	 * Si la  clause where existe, elle doit etre une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = :avion'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un delete *
	 * @param session
	 * @param whereClause clause where de la requete HQL.
	 * @param bean Bean ou sont lu les parametres de la requete  
	 * @return nombre d'instance supprime
	 * @throws JrafDaoException
	 */
	public int removeWhere(ISession session, String whereClause, Object bean)
		throws JrafDaoException;
		
	/**
	 * Création ou sauvegarde de l'objet en fonction du paramètrage défini dans le fichier de mapping
	 * @param objMetier
	 * @param session
	 * @throws JrafDaoException
	 */
	void save(ISession session, Object objMetier) throws JrafDaoException;

	/**
	 * Requête libre en langage OQL ou SQL suivant l'implémentation du moteur d'accès aux données.
	 * La clause where doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * @param requete
	 * @param session
	 * @return List
	 * @throws JrafDaoException
	 */
	java.util.List find(ISession session, String requete)
		throws JrafDaoException;

	/**
	 * Execute la requete en valorisant les parametres.
	 * Les valeurs des parametres sont recuperes dans le bean en suivant les noms
	 * utilises dans la requete
	 * Requête libre en langage OQL ou SQL suivant l'implémentation du moteur d'accès aux données.
	 * La clause where doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = :avion'</li>
	 * @param session session a utiliser
	 * @param lRequete requete HQL executee
	 * @param bean bean contenant les parametre de la requete
	 * @return liste d'objets trouves
	 * @throws JrafDaoException  exception 
	 */
	public java.util.List find(
		ISession session,
		String lRequete,
		Object bean)
		throws JrafDaoException;

	/**
	 * Method findAll. Remonte une liste complète d'objets métiers.
	 * @param requete
	 * @param session
	 * @return List
	 * @throws JrafDaoException
	 */
	java.util.List findAll(ISession session) throws JrafDaoException;

	/**
	 * Method findWhereScrollable. Retourne nbligne de la requete passée en argument
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un select *
	 * @param whereClause Requete a executer
	 * @param session session utilisee
	 * @param nbLignes Nombre de lignes a retourner
	 * @param indexDepart Index de lma premiere ligne a retourner
	 * @param bCaching: Si <b>true</b> retourne utilise l'API iterate() sinon list() 
	 * @return Object soit une List si bcaching = true.
	 *                 soit un Iterator si bcaching = false.
	 * @throws JrafDaoException
	 */
	Object findWhereScrollable(
		ISession session,
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
	 * @param whereClause  Requete a executer
	 * @param session session utilisee
	 * @param bean Bean ou sont lu les parametres de la requete  
	 * @param nbLignes Nombre de lignes a retourner
	 * @param indexDepart Index de lma premiere ligne a retourner
	 * @param bCaching: Si <b>true</b> retourne utilise l'API iterate() sinon list() 
	 * @return Object soit une List si bcaching = true.
	 *                 soit un Iterator si bcaching = false.
	 * @throws JrafDaoException
	 */
	Object findWhereScrollable(
		ISession session,
		String whereClause,
		Object bean,
		int nbLignes,
		int indexDepart,
		boolean bCaching)
		throws JrafDaoException;

	/**
	 * Retourne une List d'objet fonction de la clause where.
	 * La construction de la requete utilise la méthode getRequete() pour initialiser
	 * le début de la clause. 
	 * @param whereClause: clause avec le mot clef "where" en premier
	 * @param session
	 * @return List
	 * @throws JrafDaoException
	 */
	java.util.List findWhere(ISession session, String whereClause)
		throws JrafDaoException;

	/**
	 * Retourne une List d'objet fonction de la clause where.
	 * La construction de la requete utilise la méthode getRequete() pour initialiser
	 * le début de la clause. 
	 * Les valeurs des parametres sont recuperes dans le bean en suivant les noms
	 * utilises dans la requete
	 * @param session session a utiliser
	 * @param whereClause whereClause: clause avec le mot clef "where" en premier
	 * @param bean bean contenant les parametre de la requete
	 * @return liste d'objets trouves
	 * @see org.squale.jraf.spi.persistence.IPersistenceDAO#findWhere(org.squale.jraf.spi.persistence.ISession, java.lang.String)
	 */
	 java.util.List findWhere(
		ISession session,
		String whereClause,
		Object bean)
		throws JrafDaoException; 
		
	/**
	 * Retourne le nombre d'occurence totale d'un objet en
	 * base.
	 * @param session
	 * @return Integer (null si pb).
	 * @throws JrafDaoException
	 */
	public Integer count(ISession session) throws JrafDaoException;

	/**
	 * Retourne le nombre d'objets en fonction de la clause where
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un <b>select *</b>
	 * @param session
	 * @param whereClause
	 * @return Integer Nombre d'élément trouvé.
	 * @throws JrafDaoException
	 */
	public Integer countWhere(ISession session, String whereClause)
		throws JrafDaoException;

	/**
	 * Retourne le nombre d'objets en fonction de la clause where
	 * Si la  clause where existe, elle doit comporter une clause where valide incluant
	 * le mot clé <b>Where</b 
	 * <li> ie: 'where Avion = a'</li>
	 * Sinon, elle peut être nulle dans ce cas il s'agit d'un <b>select *</b>
	 * @param session
	 * @param whereClause
	* @param bean Bean ou sont lu les parametres de la requete  
	 * @return Integer Nombre d'élément trouvé.
	 * @throws JrafDaoException
	 */
	public Integer countWhere(ISession session, String whereClause, Object bean)
		throws JrafDaoException;

}
