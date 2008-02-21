package com.airfrance.jraf.spi.logging;

import org.apache.commons.logging.Log;

import com.airfrance.jraf.spi.provider.IProvider;

 
/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : ILoggingProvider.java</p>
 * <p>Description : Interface definissant le comportement du provider de logging</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */

public interface ILoggingProvider extends IProvider   {
	/**
	 * Retourne un logger jraf
	 * @param clazz classe a logger
	 * @return logger
	 */
	ILogger getInstance(Class clazz);
	
	/**
	 * Retourne un logger jraf
	 * @param clazz nom de la classe a logger
	 * @return logger
	 */
	ILogger getInstance(String clazz);
}