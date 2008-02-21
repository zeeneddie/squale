/*
 * Created on Mar 5, 2004
 */
package com.airfrance.jraf.bootstrap;

import java.util.List;
import java.util.Map;

import com.airfrance.jraf.spi.bootstrap.IBootstrapProvider;

/**
 * <p>Title : BootstrapProviderImpl.java</p>
 * <p>Description : Implementation du provider de bootstrap.
 * Provider responsable de l'initialisation de tous les providers d'une application.</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 * @author Eric BELLARD
 */
public class BootstrapProviderImpl implements IBootstrapProvider {

	/** liste pour stocker les configurations de provider */
	private List providers;

	/** map de parametres */
	private Map parameters;

	/**
	 * Constructeur du bootstrap vide IOC type 2
	 */
	public BootstrapProviderImpl() {
		super();
	}

	/**
	 * Constructeur du bootstrap provider avec parametres IOC type 3
	 * @param in_parameters parametres generaux d'initialisation
	 * @param in_providers liste des providers initialises
	 */
	public BootstrapProviderImpl(Map in_parameters, List in_providers) {
		parameters = in_parameters;
	}

	/**
	 * Retourne les parametres pour le provider locator
	 * @return parametres pour le provider locator
	 */
	public Map getParameters() {
		return parameters;
	}

	/**
	 * Retourne les providers
	 * @return providers
	 */
	public List getProviders() {
		return providers;
	}

	/**
	 * Fixe les parametres d'initialisation
	 * @param map
	 */
	public void setParameters(Map map) {
		parameters = map;
	}

	/**
	 * Fixe la liste de configuration de providers
	 * @param list liste des configurations de provider
	 */
	public void setProviders(List list) {
		providers = list;
	}
}
