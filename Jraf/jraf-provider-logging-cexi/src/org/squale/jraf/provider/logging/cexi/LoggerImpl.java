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
package org.squale.jraf.provider.logging.cexi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

import org.squale.jraf.spi.logging.ILogger;
import org.squale.jraf.spi.logging.ISeverity;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderLogging
 * <p>Title : LoggingProviderImpl.java</p>
 * <p>Description : Logger JRAF. 
 * Permet de logger au formalisme CEXI  a partir de la methode logCexi.
 * Cette implementation utilise Log4J.
 * L'ecriture des traces est configuree dans le fichier log4j.properties.
 * </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class LoggerImpl implements ILogger {


	/** logger log4j */
	private Log logger;


	/**
	 * Constructeur avec nom de la classe sous forme de chaine de caractere
	 * @param name nom de la classe
	 */
	public LoggerImpl(String name) {
		logger = LogFactory.getLog(name);
	}

	/**
	 * Constructeur
	 * @param clazz classe 
	 */
	public LoggerImpl(Class clazz) {
		logger = LogFactory.getLog(clazz);
	}

	//============================================

	/**
	 * Constructeur avec un logger
	 * @param logger logger log4j
	 */
	public LoggerImpl(Log logger) {
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

	/**
	 * Ecrit une trace au format cexi 
	 * @param code code de l'erreur
	 * @param severity niveau de criticite du message
	 * @param group groupe auquel appartient le message 
	 * @param sourceComposant composant qui ecrit la trace
	 * @param message message
	 */
	public void logCexi(
		int code,
		String severity,
		String group,
		String sourceComposant,
		String text) {
		Message message =
			new Message(code, severity, group, sourceComposant, text);
		if ((severity == null)
			|| severity.equals(ISeverity.NORMAL)
			|| severity.equals(ISeverity.UNKNOWN)) {
			info(message);
		} else if (severity.equals(ISeverity.WARNING)) {
			warn(message);
		} else if (severity.equals(ISeverity.MINOR)) {
			error(message);
		} else if (severity.equals(ISeverity.MAJOR)) {
			error(message);
		} else if (severity.equals(ISeverity.CRITICAL)) {
			fatal(message);
		} else if (severity.equals(ISeverity.DEBUG)) {
			debug(message);
		} else {
			info(message);
		}

	}


}
