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

import org.apache.log4j.Hierarchy;

import org.squale.jraf.spi.logging.IGroup;
import org.squale.jraf.spi.logging.ISeverity;

/**
 * <p>Project: JRAF 
 * <p>Module: jraf-provider-logging-cexi
 * <p>Title : Message.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 *  
 * Cette classe definit un message qui pourra etre logge au format Cexi
 * dans un appender defini dans le fichier log4j.properties
 * Ce message contient les informations necessaires a Cexi pour la supervision,
 * lorsqu'un probleme survient en production (code message, severity ...)
 */
public class Message {
	
	private int code;
	private String severity = ISeverity.UNKNOWN;
	private String group = IGroup.APPLICATION;
	private String sourceComposant;
	private String message;
	
	/**
	 * 
	 */
	public Message(int code, String severity, String group,
					String sourceComposant, String message) {
		super();
		this.code = code;
		this.severity = severity;
		this.group = Group.validGroup(group);
		this.sourceComposant = sourceComposant;
		this.message = message;
	}
	
	/**
	 * @return
	 */
	public String getSourceComposant() {
		return sourceComposant;
	}

	/**
	 * @return
	 */
	public String getGroup() {
		return group;
	}

	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return
	 */
	public String getSeverity() {
		return severity;
	}

	/**
	 * @param string
	 */
	public void setSourceComposant(String string) {
		sourceComposant = string;
	}


	/**
	 * @param string
	 */
	public void setGroup(String string) {
		group = string;
	}

	/**
	 * @param string
	 */
	public void setMessage(String string) {
		message = string;
	}

	/**
	 * @param string
	 */
	public void setSeverity(String string) {
		severity = string;
	}

	/**
	 * @return
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param i
	 */
	public void setCode(int i) {
		code = i;
	}

}
