/*
 * Created on Dec 29, 2004
 */
package com.airfrance.jraf.bootstrap.config;

import java.util.List;

import com.airfrance.jraf.commons.exception.JrafConfigException;

/**
 * <p>Title : IProviderConfigReader.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface IProviderConfigReader {
	
	/**
	 * Lis la configuration de provider
	 * @return List d'element de configuration de provider
	 * @throws JrafConfigException
	 */
	public List readConfig() throws JrafConfigException;

}
