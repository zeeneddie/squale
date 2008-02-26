package com.airfrance.squalecommon.util.mail.javamail;

import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.initializer.IInitializable;
import com.airfrance.jraf.spi.initializer.IInitializableBean;
import com.airfrance.jraf.spi.provider.IProvider;

public class Initializer implements IInitializable, IInitializableBean {

	public Initializer(){
		super();
	}
	
	
	public IProvider initialize(Map objectInitialize)
			throws JrafConfigException {
		
		return initialize();
	}

	public IProvider initialize() throws JrafConfigException {
		IProvider mailer = new JavaMailProviderImpl();
		return mailer;
	}

	public void afterPropertiesSet() {
		

	}

}
