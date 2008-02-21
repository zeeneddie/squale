/*
 * Created on Mar 12, 2004
 */
package com.airfrance.jraf.spi.bootstrap;

/**
 * <p>Title : IBootstrapConstants.java</p>
 * <p>Description : Constantes necessaires 
 * pour l'intialisation des providers</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface IBootstrapConstants {

	/** cle pour recuperer la classe d'initialisation */
	public final static String INITIALIZE_KEY = "initialize";

	/** cle pour recuperer le chemin relatif du fichier de configuration des providers */
	public final static String PROVIDER_CONFIG_KEY = "provider-config";

	/** cle pour recuperer le chemin absolu racine du contexte */
	public final static String ROOT_PATH_KEY = "root-path-key";

	public final static String SPRING_CONTEXT_CONFIG  = "applicationContext-config";

	/** true si le provider locator est place a jndi, 
	 * false si il ne doit pas y etre place.
	 * Renseigner a false pour les applications standalone.
	 */
	public final static String JNDI_BIND = "jndi.bind";


}
