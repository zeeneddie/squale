package com.airfrance.jraf.provider.logging.cexi;

/**
 * <p>Project: JRAF 
 * <p>Module: jraf-provider-logging-cexi
 * <p>Title : PatternLayout.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 * Cette classe surcharge celle de log4j, afin de proposer des
 * caracteres/mots de conversion supplementaires:
 *       %a ou %appli pour le code de l'application
 * Cette classe est utilisee dans le log4j.properties, comme layout
 * associe a l'appender pour Cexi.
 * Le fichier de configuration log4j.properties est fourni
 * par JRAF.
 */
public class PatternLayout extends org.apache.log4j.PatternLayout {

	/**
	 * 
	 */
	public PatternLayout() {
		this(DEFAULT_CONVERSION_PATTERN);
	}
	
	/**
	 * @param arg0
	 */
	public PatternLayout(String arg0) {
		super(arg0);
	}
	
	public org.apache.log4j.helpers.PatternParser createPatternParser(String pattern) {
		return new PatternParser(pattern==null ? DEFAULT_CONVERSION_PATTERN : pattern);
	}

}
