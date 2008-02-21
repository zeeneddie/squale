package com.airfrance.jraf.provider.logging.cexi;

import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.initializer.IInitializable;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderLogging
 * <p>Title : Initializer.java</p>
 * <p>Description : Initializer du provider de logging</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class Initializer implements IInitializable {

	/**
	 * Constructeur vide IOC type 2
	 */
	public Initializer() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializable#initialize(java.util.Map)
	 */
	public IProvider initialize(Map objectInitialize)
		throws JrafConfigException {
		
		return initialize();
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.initializer.IInitializable#initialize()
	 */
	public IProvider initialize() {
		return new LoggingProviderImpl();
	}

}
