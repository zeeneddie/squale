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
package com.airfrance.jraf.provider.logging.cexi;

import java.lang.reflect.Field;

import com.airfrance.jraf.spi.logging.IGroup;

/**
 * <p>Project: JRAF 
 * <p>Module: jraf-provider-logging-cexi
 * <p>Title : Group.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 * Cette classe assure que le groupe données est un groupe connu 
 * de type Message.
 */
public class Group implements IGroup {
	/**
	 * Methode qui veririfie si le groupe passé en paramètre fait
	 * partir des groupes definis dans l'interface IGroup
	 * @param pGroupName Le nom du groupe à vérifier
	 * @return retourne le nom du groupe si il est connu, 
	 * sinon retourne IGroup.APPLICATION
	 */
	public static String validGroup(String pGroupName) {
		Field[] fields = IGroup.class.getFields();
		
		for(int i=0;i<fields.length;i++) {
			String value = new String();
			try {
				value = (String)fields[i].get(null);
			} catch (IllegalArgumentException e) {
				// Par defaut on retourne un group
			} catch (IllegalAccessException e) {
				// Par defaut on retourne un group
			}
			if(value.equals(pGroupName))
				return pGroupName;
		}
		
		return IGroup.APPLICATION; 
	}
	/**
	 * 
	 */
	public Group() {
		super();
	}

}
