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
