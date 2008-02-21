/*
 * Created on Dec 24, 2004
 */
package com.airfrance.jraf.spi.accessdelegate;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;

/**
 * <p>Title : ICMTApplicationComponent.java</p>
 * <p>Description : Comportement d'un CMTApplicationComponent</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface ICMTApplicationComponent extends IApplicationComponent {

	/**
	 * Retourne un application component.
	 * Si il est de type ICMTApplicationComponent sa session est renseigne pour qu'il s'integre dans la transaction courante.
	 * @param in_name nom de l'application component
	 * @return CMTApplicationComponent
	 * @throws JrafEnterpriseException
	 */
	public IApplicationComponent getCompositeApplicationComponent(String in_name)
		throws JrafEnterpriseException;

	/**
	 * Retourne la session de la transaction courante
	 * @return session de la transaction courante
	 */
	public ISession getSession();
	
}
