/*
 * Created on Mar 2, 2004
 */
package com.airfrance.jraf.provider.accessdelegate.ejb;

import javax.ejb.EJBLocalObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.ejb.util.EJBApplicationComponentHelper;
import com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.jraf.spi.initializer.IInitializableBean;

/**
 * <p>Title : EJBApplicationComponent.java</p>
 * <p>Description : Default EJB application component</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class EJBApplicationComponent
	implements IApplicationComponent, IEJBAccess, IInitializableBean {

	/** logger */
	private static final Log log =
		LogFactory.getLog(EJBApplicationComponent.class);

	/** ejb local object */
	private EJBLocalObject ejbLocalObject;

	/** nom de composant */
	private String applicationComponent;

	/** nom jndi */
	private String jndiName;

	/** provider access delegate */
	private IAccessDelegateProvider accessDelegateProvider = null;

	/**
	 * Constructeur vide type IOC2.
	 */
	public EJBApplicationComponent() {
		if (log.isDebugEnabled()) {
			log.debug("Creation EJBApplicationComponent");
		}
	}

	/**
	 * Constructeur avec parametres type IOC3.
	 */
	public EJBApplicationComponent(
		String name,
		IAccessDelegateProvider adp,
		String jndiName) {

		if (log.isDebugEnabled()) {
			log.debug("Creation EJBApplicationComponent...");
		}
		setApplicationComponent(name);
		setAccessDelegateProvider(adp);
		setJndiName(jndiName);

		afterPropertiesSet();

		if (log.isDebugEnabled()) {
			log.debug("EJBApplicationComponent cree.");
		}

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.IApplicationComponent#execute(java.lang.String, java.lang.Object[])
	 */
	public Object execute(String componentname, Object[] parameters)
		throws JrafEnterpriseException {
		if (log.isDebugEnabled()) {
			log.debug("execute:" + componentname);
		}
		return EJBApplicationComponentHelper.execute(
			this,
			ejbLocalObject,
			componentname,
			parameters);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.IApplicationComponent#execute(java.lang.String)
	 */
	public Object execute(String componentname)
		throws JrafEnterpriseException {
		return execute(componentname, null);

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.IApplicationComponent#getApplicationComponent()
	 */
	public String getApplicationComponent() {
		return applicationComponent;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.IApplicationComponent#setApplicationComponent(java.lang.String)
	 */
	public void setApplicationComponent(String applicationComponent) {
		this.applicationComponent = applicationComponent;

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.adapter.ejb.IEJBAccess#getJndiName()
	 */
	public String getJndiName() {
		return jndiName;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.adapter.ejb.IEJBAccess#setJndiName(java.lang.String)
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	/**
	 * Activation de l'ejb
	 */
	public void activate() {

		if (log.isDebugEnabled()) {
			log.debug("EJBApplicationComponent activation...");
		}

		ejbLocalObject =
			EJBApplicationComponentHelper.createLocalObject(jndiName);

		if (log.isDebugEnabled()) {
			log.debug("EJBApplicationComponent activated");
		}

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.accessdelegate.IApplicationComponent#getAccessDelegateProvider()
	 */
	public IAccessDelegateProvider getAccessDelegateProvider() {
		return accessDelegateProvider;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.accessdelegate.IApplicationComponent#setAccessDelegateProvider(com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider)
	 */
	public void setAccessDelegateProvider(IAccessDelegateProvider adp) {
		this.accessDelegateProvider = adp;

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {

		// verification de l'initialisation
		if (getAccessDelegateProvider() == null) {
			String message =
				"Probleme d'initialisation : le provider access delegate est null";
			log.error(message);
			throw new JrafConfigException(message);
		}

		if (getApplicationComponent() == null) {
			String message =
				"Probleme d'initialisation : le nom de l'application component est null";
			log.error(message);
			throw new JrafConfigException(message);
		}

		if (getJndiName() == null) {
			String message =
				"Probleme d'initialisation : le nom JNDI de l'EJB est null";
			log.error(message);
			throw new JrafConfigException(message);
		}

		// activation de l'ejb
		activate();
	}

}
