/*
 * Created on Mar 5, 2004
 */
package com.airfrance.jraf.spi.bootstrap;

import java.util.List;
import java.util.Map;

import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Title : IBootstrapProvider.java</p>
 * <p>Description : Provider du bootstrap</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public interface IBootstrapProvider extends IProvider {

	/**
	 * Retourne les parametres d'initialisation.
	 * @return parametres d'initialisation
	 */
	public Map getParameters();
	
	/**
	 * Fixe la liste des parametres d'initialisation
	 * @param parameters liste des parametres d'initialisation
	 */
	public void setParameters(Map in_parameters);
	
	/**
	 * Retourne les providers initialises
	 * @return providers
	 */
	public List getProviders();

	/**
	 * Fixe la liste de configuration des providers 
	 * @param providers
	 */
	public void setProviders(List in_providers);


}
