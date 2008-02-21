package com.airfrance.jraf.spi.accessdelegate;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.spi.provider.IProvider;


/**
 * Interface obligatoire pour les classes applicationComponents.
 * @author Courtial
 */
public interface IApplicationComponent extends IProvider {

	/**
	 * Retourne le nom de l'application component
	 * @return String le nom du component.
	 */
	public String getApplicationComponent();
	
	/**
	 * Fixe le nom de l'application component
	 * @param applicationComponent nom de l'application component
	 */
	public void setApplicationComponent(String applicationComponent);

	/**
	 * Retourne le provider access delegate
	 * @return provider access delegate
	 */
	public IAccessDelegateProvider getAccessDelegateProvider();
	
	/**
	 * Fixe le provider access delegate
	 * @param adp access delegate provider
	 */	
	public void setAccessDelegateProvider(IAccessDelegateProvider adp);
	
	
	
	/**
	 * Appel d'un component sans paramètre.
	 * @param componentname
	 * @return
	 * @throws JrafEnterpriseException
	 */
	Object execute(String componentname) throws JrafEnterpriseException;
	
	/**
	 * Appel d'un component ayant une liste de parametre sous
	 * la forme d'un tableau d'objet.
	 * @param componentname
	 * @param paramIn
	 * @return
	 * @throws JrafEnterpriseException
	 */
	Object execute(String componentname, Object[] paramIn) throws JrafEnterpriseException;
}
