/*
 * Created on Dec 28, 2004
 */
package com.airfrance.jraf.provider.accessdelegate.config;

import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : IApplicationComponentConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface IApplicationComponentConfigReader {
	/**
	 * Methode qui declenche la lecture du fichier de configuration
	 * @throws JrafConfigException
	 */
	public abstract Map readConfig() throws JrafConfigException;
}