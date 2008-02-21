package com.airfrance.jraf.bootstrap.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title : ProviderConfig</p>
 * <p>Description : Configuration d'un provider</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */

public class ProviderConfig implements Serializable {

	/** id */
	private String id;

	/** nom de classe */
	private String className;

	/** description */
	private String description;

	/** ordre de chargement */
	/** par defaut l'ordre de chargement est maximum -> chargement du provider en dernier */
	private int loadOnStartup = Integer.MAX_VALUE;

	/** properties */
	private Map properties = new HashMap();

	/**
	 * Insere un couple (name, value) dans la map de proprietes
	 * @param in_name nom de la propriete
	 * @param in_value value de la propriete
	 */
	public void putProperty(String in_name, String in_value) {
		properties.put(in_name, in_value);
	}

	/**
	 * Supprime un couple (name, value) de la map de proprietes
	 * @param in_name nom de la propriete
	 */
	public void removeProperty(String in_name) {
		properties.remove(in_name);
	}

	/**
	 * Retourne les proprietes
	 * @return properties proprietes
	 */
	public Map getProperties() {
		return properties;
	}

	/**
	 * Retourne le nom de la classe d'initialisation
	 * @return nom de la classe d'initialisation
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Retourne la description d'un provider
	 * @return description d'un provider
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Retourne l'id d'un provider
	 * @return id d'un provider
	 */
	public String getId() {
		return id;
	}

	/**
	 * Fixe le nom de la classe d'initialisation d'un provider
	 * @param in_className nom de la classe d'initialisation
	 */
	public void setClassName(String in_className) {
		className = in_className;
	}

	/**
	 * Fixe la description d'un provider
	 * @param description description d'un provider
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Fixe l'id d'un provider
	 * @param id id d'un provider
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retourne l'ordre de chargement
	 * @return ordre de chargement
	 */
	public int getLoadOnStartup() {
		return loadOnStartup;
	}

	/**
	 * Fixe l'ordre de chargement
	 * @param loadOnStartup ordre de chargement
	 */
	public void setLoadOnStartup(int loadOnStartup) {
		this.loadOnStartup = loadOnStartup;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[id=" + getId() + ",loadOnStartup=" + getLoadOnStartup() + "]";
	}

}
