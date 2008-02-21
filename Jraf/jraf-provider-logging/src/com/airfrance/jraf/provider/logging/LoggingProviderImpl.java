package com.airfrance.jraf.provider.logging;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.spi.logging.ILogger;
import com.airfrance.jraf.spi.logging.ILoggingProvider;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderLogging
 * <p>Title : LoggingProviderImpl.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 *  Mécanisme de trace en attendant la version améliorée du MessageManager
 *  Cette version est implémentée sous forme de singleton. Elle utilise le commons-logging qui propose
 *  une sur couche indépendante du module de trace utilisé. Si le jar de log4j est
 *  présent, il est utilisé. 
 *  Cette version, minimise les problèmes de performances liées aux traces en associant
 *  un seul logger à chaque classe (géré par HashTable). Le fichier de configuration log4j.properties est fourni
 *  par JRAF.
 */
public class LoggingProviderImpl implements ILoggingProvider {

	/**
	 * Constructeur vide (IOC type 2).
	 */
	public LoggingProviderImpl() {
	}

	/**
	 * Creation d'un logger
	 * @param clazz classe a logger
	 */
	public ILogger getInstance(Class clazz) {
		return new LoggerImpl(LogFactory.getLog(clazz));
	}

	/**
	 * Creation d'un logger
	 * @param clazz classe a logger
	 */
	public ILogger getInstance(String clazz) {
		return new LoggerImpl(LogFactory.getLog(clazz));
	}

}
