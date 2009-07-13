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
 * Créé le 28 oct. 04
 */
package org.squale.jraf.provider.logging.cexi;

import org.apache.log4j.Level;
import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.spi.LoggingEvent;

import org.squale.jraf.spi.logging.IGroup;
import org.squale.jraf.spi.logging.ILogger;
import org.squale.jraf.spi.logging.ISeverity;

/**
 * <p>Title : PatternParser.java</p>
 * <p>Description : Parser de pattern log4j.
 * Permet d'ajouter des patterns aux patterns log4j
 * Cette classe permet d'adapter les logs JRAF au formalisme CEXI.
 * </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 */
public class PatternParser extends org.apache.log4j.helpers.PatternParser {

	/** initialisation JRAF -> CONFIGURATION CEXI*/
	private final static String JRAF_CFG = "org.squale.jraf.bootstrap";
	/** initialisation JRAF -> CONFIGURATION CEXI*/
	private final static String JRAF_INIT = "org.squale.jraf.initializer";
	/** persistance JRAF -> DB CEXI*/
	private final static String JRAF_DB =
		"org.squale.jraf.provider.persistence";
	/** JRAF -> APPLICATION CEXI*/
	private final static String JRAF_ALL = "org.squale.jraf";

	/**
	 * Constructeur
	 * @param arg0
	 */
	public PatternParser(String arg0) {
		super(arg0);
	}

	/**
	 * Parse des converteurs
	 */
	public void finalizeConverter(char c) {
		if (c == 's') {
			addConverter(new Level2SeverityPatternConverter(formattingInfo));
			currentLiteral.setLength(0);

		} else if (c == 'g') {
			addConverter(new JRAF2GroupPatternConverter(formattingInfo));
			currentLiteral.setLength(0);

		} else {
			super.finalizeConverter(c);
		}
	}

	/**
	 * <p>Title : Level2SeverityPatternConverter</p>
	 * <p>Description : Definition a partir d'une classe JRAF 
	 * de son groupe CEXI: APPLICATION, CONFIGURATION ou DATABASE.</p>
	 * <p>Copyright : Copyright (c) 2004</p>
	 * 
	 */
	private class JRAF2GroupPatternConverter extends PatternConverter {
		JRAF2GroupPatternConverter(FormattingInfo formattingInfo) {
			super(formattingInfo);
		}
		public String convert(LoggingEvent event) {

			if (event.getLoggerName().startsWith(JRAF_CFG)
				|| event.getLoggerName().startsWith(JRAF_INIT)) {
				return IGroup.CONFIGURATION;
			}
			if (event.getLoggerName().startsWith(JRAF_DB)) {
				return IGroup.DATABASE;
			}
			if (event.getLoggerName().startsWith(JRAF_ALL)) {
				return IGroup.APPLICATION;
			}
			return null;
		}

	}
	
	/**
	 * <p>Title : Level2SeverityPatternConverter</p>
	 * <p>Description : Conversion du niveau log4j en niveau CEXI
	 * DEBUG -> debug, 
	 * INFO -> normal, 
	 * WARN -> warning, 
	 * ERROR -> major, 
	 * FATAL -> critical.
	 * Note: la severite minor n'est pas utilisee.
	 * </p>
	 * <p>Copyright : Copyright (c) 2004</p>
	 * 
	 */
	private class Level2SeverityPatternConverter extends PatternConverter {
		Level2SeverityPatternConverter(FormattingInfo formattingInfo) {
			super(formattingInfo);
		}

		public String convert(LoggingEvent event) {

			if (event.getLevel() == Level.DEBUG) {
				return ISeverity.DEBUG;
			}
			if (event.getLevel() == Level.INFO) {
				return ISeverity.NORMAL;
			}
			if (event.getLevel() == Level.WARN) {
				return ISeverity.WARNING;
			}
			if (event.getLevel() == Level.ERROR) {
				return ISeverity.MAJOR;
			}
			if (event.getLevel() == Level.FATAL) {
				return ISeverity.CRITICAL;
			} else
				return null;
		}

	}

}
