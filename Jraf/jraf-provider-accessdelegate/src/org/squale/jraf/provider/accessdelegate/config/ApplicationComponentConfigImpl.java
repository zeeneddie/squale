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
 * Created on Mar 3, 2004
 */
package org.squale.jraf.provider.accessdelegate.config;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title : ApplicationComponentConfigImpl.java</p>
 * <p>Description : Implémentation de la configuration 
 * d'un application component</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
 * @author Eric BELLARD
 */
public class ApplicationComponentConfigImpl
	implements IApplicationComponentConfig {

	/** nom */
	String name;
	/** description */
	String description;

	/** classe d'implementation */
	String impl;

	/** nom jndi */
	String jndiName;

	/** composants */
	Map components;

	/**
	 * Ajoute un composant
	 * @param component composant
	 */
	public void putComponent(String component) {

		getComponents().put(component, component);
	}

	/**
	 * Supprime un composant
	 * @param component composant
	 */
	public void removeComponent(String component) {
		getComponents().remove(component);
	}
	/* (non-Javadoc)
	 * @see org.squale.jraf.config.IApplicationComponentConfig#getName()
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.config.IApplicationComponentConfig#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.config.IApplicationComponentConfig#getImpl()
	 */
	public String getImpl() {
		return impl;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.config.IApplicationComponentConfig#getJNDIname()
	 */
	public String getJndiName() {
		return jndiName;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.config.IApplicationComponentConfig#getProperties()
	 */
	public Map getComponents() {
		if (components == null) {
			components = new HashMap();
		}
		return components;
	}

	/**
	 * Fixe une description
	 * @param description description 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Fixe une implementation
	 * @param implementation implementation
	 */
	public void setImpl(String implementation) {
		impl = implementation;
	}

	/**
	 * Fixe le nom jndi
	 * @param jndiName nom jndi
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	/**
	 * Fixe le nom
	 * @param name nom 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.config.IApplicationComponentConfig#isComponent(java.lang.String)
	 */
	public boolean isComponent(String name) {

		// retourne vrai si la map contient la cle
		return getComponents().containsKey(name);
	}

}
