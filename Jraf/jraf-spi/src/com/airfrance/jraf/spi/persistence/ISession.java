package com.airfrance.jraf.spi.persistence;

import com.airfrance.jraf.commons.exception.JrafPersistenceException;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : ISession.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
/**
 * Cette interface définie l'unité de travail dans laquelle sont effectués tous les
 * traitement dans les DAOs.
 * @author Courtial Gilles
 */
public interface ISession {

	/**
	 * Démarrage d'une transaction utilisation d'une transaction
	 * dans le cadre d'une gestion transactionnelle managée par container (CMT) 
	 * @throws JrafPersistenceException
	 */
	void beginTransaction() throws JrafPersistenceException;

	/**
	 * Execution des requêtes HQL en attentes et validation des traitements
	 * en base de données. Utilisation de la transaction sous jacente.
	 * La session associée est fermée.
	 * @throws JrafPersistenceException
	 */
	void commitTransaction() throws JrafPersistenceException;

	/**
	 * Annulation des ordres passes pendant la transaction
	 */
	void rollbackTransaction();

	/**
	 * Execution des requêtes HQL en attentes et validation des traitements
	 * en base de données. Utilisation de la transaction sous jacente.
	 * La session associee n'est pas fermee et reste utilisable.
	 * @throws JrafPersistenceException
	 */
	void commitTransactionWithoutClose() throws JrafPersistenceException;

	/**
	 * Annulation des ordres passes pendant la transaction.
	 * La session associee n'est pas fermee et reste utilisable.
	 */
	void rollbackTransactionWithoutClose();

	/**
	 * Fermeture de la session
	 * @throws JrafPersistenceException
	 */
	public void closeSession() throws JrafPersistenceException;

	/**
	 * Suppression de l'objet du cache session
	 * @param object
	 * @throws JrafPersistenceException
	 */
	void evict(Object object) throws JrafPersistenceException;

	/**
	 * Supprimer l'ensemble des objets du cache session
	 */
	void clear();

	/**
	 * retourne true si l'objet existe dans le cache ou comme proxy
	 * retourne false sinon 
	 * @param object
	 * @return
	 */
	public boolean contains(Object object);
	
	
	/**
	 * Retourne true si la session est toujours ouverte, false sinon.
	 * @return true si la session est toujours ouverte, false sinon.
	 */
	public boolean isOpen();
	
	public void invalidate();
	public boolean isValid();
}
