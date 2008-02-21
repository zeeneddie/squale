/*
 * Created on Mar 5, 2004
 */
package com.airfrance.jraf.bootstrap;

import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.bootstrap.IBootstrapProvider;
import com.airfrance.jraf.spi.initializer.IInitializable;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Title : BootstrapInitializer.java</p>
 * <p>Description : Initializer du bootstrap</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class BootstrapInitializer
	implements IInitializable, IInitializableBean {

	/** map de parametres */
	private Map initParams;

	/**
	 * Constructeur vide IOC type 2
	 */
	public BootstrapInitializer() {
		super();
	}

	/**
	 * Constructeur vide IOC type 2
	 */
	public BootstrapInitializer(Map params) {
		super();
		setInitParams(params);

	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializable#initialize(java.util.Map)
	 */
	public IProvider initialize(Map initParams) throws JrafConfigException {
		setInitParams(initParams);
		afterPropertiesSet();
		return initialize();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializable#initialize()
	 */
	public IProvider initialize() {
		// creation du bootstrap 
		IBootstrapProvider lc_bootstrapProvider = new BootstrapProviderImpl();
		lc_bootstrapProvider.setParameters(getInitParams());

		return lc_bootstrapProvider;
	}

	/**
	 * Retourne la map de parametres
	 * @return map de parametres
	 */
	public Map getInitParams() {
		return initParams;
	}

	/**
	 * @param map
	 */
	public void setInitParams(Map map) {
		initParams = map;
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializableBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		// rien a verifier
		// les parametres d'initialisation peuvent être null
	}
}
