package com.airfrance.jraf.spi.persistence;

import java.io.Serializable;

import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : IPersistenceProvider.java</p>
 * <p>Description : Comportement d'un provider de persistence</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */

public interface IPersistenceProvider extends IProvider {
	/**
	 * Retourne true si gestion automatique des transactions, false sinon
	 * @return true si gestion automatique des transactions, false sinon
	 */
	public boolean isAutomaticTransaction(); 
	/**
	* Retourne true si les transaction sont gerees par le conteneur, false sinon.
	* @return true si les transaction sont gerees par le conteneur, false sinon.
	*/
	public boolean isContainerManagedTransaction();

	/**
	 * Retourne true si utilisation d'une session longue, false sinon
	 * @return true si utilisation d'une session longue, false sinon
	 */
	public boolean isLongSession();

	/**
	 * Retourne true si utilisation du design pattern thread local session, false sinon
	 * @return true si utilisation du design pattern thread local session, false sinon
	 */
	public boolean isThreadLocalSession();  
	
	/**
	 * Retourne true si l'introspection est utilisee, false sinon
	 * @return true si l'introspection est utilisee, false sinon
	 */
	public boolean isIntrospection();

    /**
     * Fournir une session de type ISession
	 * @return session de type ISession
	 * @throws JrafPersistenceException
	 */
	ISession getSession() throws JrafPersistenceException;


	/**
	 * Fournir un point d'accès aux meta données du mapping.
	 * @return un objet de type IMetaData.
	 */
	IMetaData getMetaData();

 
}
