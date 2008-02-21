package com.airfrance.jraf.spi.logging;

import org.apache.commons.logging.Log;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : ILogger.java</p>
 * <p>Description : Logger JRAF</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */

public interface ILogger extends Log {

	/**
	 * Ecriture d'une trace au format CEXI.
	 * Cette operation est optionelle.
	 * @throws UnsupportedOperationException si l'operation n'est pas implementee
	 */
	public abstract void logCexi(
		int code,
		String severity,
		String group,
		String sourceComposant,
		String message);
}