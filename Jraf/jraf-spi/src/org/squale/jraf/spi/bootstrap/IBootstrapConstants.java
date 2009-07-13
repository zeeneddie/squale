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
 * Created on Mar 12, 2004
 */
package org.squale.jraf.spi.bootstrap;

/**
 * <p>Title : IBootstrapConstants.java</p>
 * <p>Description : Constantes necessaires 
 * pour l'intialisation des providers</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * 
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
