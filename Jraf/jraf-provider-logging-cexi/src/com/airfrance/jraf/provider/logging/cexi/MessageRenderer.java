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

import org.apache.log4j.or.ObjectRenderer;

/**
 * <p>Project: JRAF 
 * <p>Module: jraf-provider-logging-cexi
 * <p>Title : StringRenderer.java</p>
 * <p>Description : String renderer</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 * Cette classe implemente l'interface ObjectRenderer de log4j.
 * Elle permet de formater un message de type String, en vue de le 
 * logger pour Cexi.
 */
public class MessageRenderer implements ObjectRenderer {

	/**
	 * 
	 */
	public MessageRenderer() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.apache.log4j.or.ObjectRenderer#doRender(java.lang.Object)
	 */
	public String doRender(Object arg0) {
		if( arg0 instanceof Message ) {
			Message mess = (Message) arg0;
			return mess.getCode()
					+" - "+mess.getSeverity()
					+" - "+mess.getGroup()
					+" - "+mess.getSourceComposant()
					+" - "+mess.getMessage();
		} else
			return "Erreur dans le StringRenderer";
	}

}
