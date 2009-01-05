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
package com.airfrance.jraf.provider.logging;

import java.io.Serializable;
import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.Priority;

import com.airfrance.jraf.spi.logging.ILogger;

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
 *  Le fichier de configuration log4j.properties est fourni
 *  par JRAF.
 */
public class LoggerImpl implements ILogger {

	private Log logger;


	/**
	 * Constructeur avec chaine de caractere indiquant le nom de la classe cible
	 * @param name
	 * @deprecated
	 */
	public LoggerImpl(String name) {
		logger = LogFactory.getLog(name);
	}

	/**
	 * Constructor for Log.
	 * @deprecated
	 */
	public LoggerImpl(Class clazz) {
		logger = LogFactory.getLog(clazz);
	}

	//============================================

	public LoggerImpl(Log logger) {
		this.logger = null;
		this.logger = logger;
	}

	public void trace(Object message) {
		logger.trace(message);
//		logger.log(FQCN, Priority.DEBUG, message, null);
	}

	public void trace(Object message, Throwable t) {
		logger.trace(message, t);
//		logger.log(FQCN, Priority.DEBUG, message, t);
	}

	public void debug(Object message) {
		logger.debug(message);
//		logger.log(FQCN, Priority.DEBUG, message, null);
	}

	public void debug(Object message, Throwable t) {
		logger.debug(message, t);
//		logger.log(FQCN, Priority.DEBUG, message, t);
	}

	public void info(Object message) {
		logger.info(message);
//		logger.log(FQCN, Priority.INFO, message, null);
	}

	public void info(Object message, Throwable t) {
		logger.info(message, t);
//		logger.log(FQCN, Priority.INFO, message, t);
	}

	public void warn(Object message) {
		logger.warn(message);
//		logger.log(FQCN, Priority.WARN, message, null);
	}

	public void warn(Object message, Throwable t) {
		logger.warn(message, t);
//		logger.log(FQCN, Priority.WARN, message, t);
	}

	public void error(Object message) {
		logger.error(message);
//		logger.log(FQCN, Priority.ERROR, message, null);
	}

	public void error(Object message, Throwable t) {
		logger.error(message, t);
//		logger.log(FQCN, Priority.ERROR, message, t);
	}

	public void fatal(Object message) {
		logger.fatal(message);
//		logger.log(FQCN, Priority.FATAL, message, null);
	}

	public void fatal(Object message, Throwable t) {
		logger.fatal(message, t);
//		logger.log(FQCN, Priority.FATAL, message, t);
	}

	public Log getLogger() {
		return logger;
	}

	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return logger.isErrorEnabled();
//		return logger.isEnabledFor(Priority.ERROR);
	}

	public boolean isFatalEnabled() {
		return logger.isFatalEnabled();
//		return logger.isEnabledFor(Priority.FATAL);
	}

	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
//		return logger.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
//		return logger.isDebugEnabled();
	}

	public boolean isWarnEnabled() {
		return logger.isWarnEnabled();
//		return logger.isEnabledFor(Priority.WARN);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.spi.logging.ILogger#logCexi(int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void logCexi(
		int code,
		String severity,
		String group,
		String sourceComposant,
		String message) {

		// methode non supportee par cette implementation
		throw new UnsupportedOperationException("La methode n'est supportee par cette implementation");

	}


}
