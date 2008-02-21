/*
 * Créé le 5 mars 04
 */
package com.airfrance.jraf.spi.initializer;

import java.util.Map;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.spi.provider.IProvider;

/**
 * <p>Title : IInitializable.java</p>
 * <p>Description : Interface definissant le comportement d'initialisation</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface IInitializable {

	/**
	 * Initialisation du Provider à partir des informations retournées
	 * par le socle. 
	 * @param objectInitialize contient la liste des paramètres sous la forme
	 * clé => valeur 
	 * @return IProvider
	 */
	public IProvider initialize(Map objectInitialize)
		throws JrafConfigException;

	
	/**
	 * Initialisation du provider a partir des informations renseignees par le constructeur 
	 * ou par des getter/setter.
	 * @return provider
	 */
	public IProvider initialize() throws JrafConfigException;
}
