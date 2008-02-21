/*
 * Created on Mar 11, 2004
 */
package com.airfrance.jraf.helper;

import com.airfrance.jraf.bootstrap.locator.ProviderLocator;
import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.logging.ILogger;
import com.airfrance.jraf.spi.logging.ILoggingProvider;
import com.airfrance.jraf.spi.provider.IProviderConstants;

/**
 * <p>Title : LoggingHelper.java</p>
 * <p>Description : Helper pour recuperer un logger.
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class LoggingHelper {

	/**
	 * Creation d'un logger
	 * @param clazz classe a logger
	 * @deprecated
	 */
	public final static ILogger getInstance(Class clazz) {

		ILoggingProvider lc_loggingProvider =
			(ILoggingProvider) ProviderLocator.getProvider(
				IProviderConstants.LOGGING_PROVIDER_KEY);
		
		ILogger log = null;
		if(lc_loggingProvider!=null) {
			log =  lc_loggingProvider.getInstance(clazz);
		} else {
			throw new JrafConfigException("No logging provider defined");
		}
		return log;
	}

	/**
	 * Creation d'un logger
	 * @param clazz classe a logger
	 * @deprecated
	 */
	public final static ILogger getInstance(String clazz) {
		ILoggingProvider lc_loggingProvider =
			(ILoggingProvider) ProviderLocator.getProvider(
				IProviderConstants.LOGGING_PROVIDER_KEY);

		ILogger log = null;
		if(lc_loggingProvider!=null) {
			log =  lc_loggingProvider.getInstance(clazz);
		} else {
			throw new JrafConfigException("No logging provider defined");
		}
		return log;
	}
}
