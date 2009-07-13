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
package org.squale.jraf.provider.accessdelegate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafConfigException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.accessdelegate.IAccessDelegateProvider;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.jraf.spi.initializer.IInitializableBean;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : DefaultExecuteComponent.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 *  
 * implémentation standard d'appel d'un component contenu dans un 
 * applicationComponent. 
 * <br>exemple:<br>
 * <b>IApplicationComponent id = ApplicationComponentDelegate.getInstance("Crud");<br>
 * nbInstances = (Integer) id.execute("countAvion");</b><br>
 */
public class DefaultExecuteComponent
	implements IApplicationComponent, IInitializableBean {

	/** logger */
	private static final Log log =
		LogFactory.getLog(DefaultExecuteComponent.class);

	/** nom de l'application component */
	private String applicationComponent = null;

	/** provider access delegate */
	private IAccessDelegateProvider accessDelegateProvider = null;

	/**
	 * Constructeur vide type IOC 2
	 */
	public DefaultExecuteComponent() {
		super();
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param adp access delegate provider
	 * @param name application component name
	 */
	public DefaultExecuteComponent(String name, IAccessDelegateProvider adp) {
		super();
		setAccessDelegateProvider(adp);
		setApplicationComponent(name);
		afterPropertiesSet();

	}

	/**
	 * @see org.squale.jraf.enterpriselayer.itface.IApplicationComponent#execute(java.lang.String)
	 */
	public Object execute(String method) throws JrafEnterpriseException {
		return execute(method, null);
	}

	/** 
	 * @see org.squale.jraf.enterpriselayer.itface.IApplicationComponent#execute(java.lang.String, java.lang.Object[])
	 * */
	public Object execute(String method, Object[] parameter)
		throws JrafEnterpriseException {

		// declaration de la valeur retourne
		Object lc_returnedValue = null;

		// delegation de l'appel de methode sur le helper
		lc_returnedValue = ExecuteHelper.execute(this, method, parameter);

		// on retourne le resultat de la delegation
		return lc_returnedValue;

	}

	/**
	 * @see org.squale.jraf.enterpriselayer.itface.IApplicationComponent#getApplicationComponent()
	 */
	public String getApplicationComponent() {
		return applicationComponent;
	}

	/**
	 * @see org.squale.jraf.enterpriselayer.itface.IApplicationComponent#setApplicationComponent(String)
	 */
	public void setApplicationComponent(String applicationComponent) {
		this.applicationComponent = applicationComponent;
	}
	/**
	 * @return
	 */
	public IAccessDelegateProvider getAccessDelegateProvider() {
		return accessDelegateProvider;
	}

	/**
	 * @param provider
	 */
	public void setAccessDelegateProvider(IAccessDelegateProvider provider) {
		accessDelegateProvider = provider;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		if (getAccessDelegateProvider() == null) {
			throw new JrafConfigException("Le provider access delegate n'est pas renseigne.");
		}

		if (getApplicationComponent() == null) {
			throw new JrafConfigException("Le nom de l'application component n'est pas renseigne.");
		}

	}

}
