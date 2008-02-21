package com.airfrance.jraf.spi.logging;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : ISeverity.java</p>
 * <p>Description : Niveau de severitées pour les logs cexi</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */

public interface ISeverity {
	/**
	 * Niveau connus de severites 
	 */
	/** niveau inconnu */
	public static String UNKNOWN = "unknown";
	/** niveau normal */
	public static String NORMAL = "normal";
	/** niveau warning */
	public static String WARNING = "warning";
	/** niveau mnior */
	public static String MINOR = "minor";
	/** niveau major */
	public static String MAJOR = "major";
	/** niveau critique */
	public static String CRITICAL = "critical";
	/** niveau debug */
	public static String DEBUG = "debug";

}
