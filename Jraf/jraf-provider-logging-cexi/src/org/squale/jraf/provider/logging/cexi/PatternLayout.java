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

/**
 * <p>Project: JRAF 
 * <p>Module: jraf-provider-logging-cexi
 * <p>Title : PatternLayout.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 *  
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
