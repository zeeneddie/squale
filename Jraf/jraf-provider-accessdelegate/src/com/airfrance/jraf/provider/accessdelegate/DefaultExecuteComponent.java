package com.airfrance.jraf.provider.accessdelegate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.jraf.spi.initializer.IInitializableBean;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : DefaultExecuteComponent.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
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
	 * @see com.airfrance.jraf.enterpriselayer.itface.IApplicationComponent#execute(java.lang.String)
	 */
	public Object execute(String method) throws JrafEnterpriseException {
		return execute(method, null);
	}

	/** 
	 * @see com.airfrance.jraf.enterpriselayer.itface.IApplicationComponent#execute(java.lang.String, java.lang.Object[])
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
	 * @see com.airfrance.jraf.enterpriselayer.itface.IApplicationComponent#getApplicationComponent()
	 */
	public String getApplicationComponent() {
		return applicationComponent;
	}

	/**
	 * @see com.airfrance.jraf.enterpriselayer.itface.IApplicationComponent#setApplicationComponent(String)
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
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
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
