/*
 * Cree le 21 janv. 05
 */
package com.airfrance.jraf.bootstrap.locator;

/**
 * <p>Project: JRAF 
 * <p>Title : IProviderLocatorConstants</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2005</p>
 * <p>Company : AIRFRANCE </p>
 */
public interface IProviderLocatorConstants {
	
	/** cle pour retrouver le nom jndi du provider locator dans jndi */
	public final static String PROVIDER_LOCATOR_NAME_KEY =
		"java:comp/env/providerLocator";

	/** cle pour retrouver le nom jndi du provider locator dans jndi */
	public final static String JNDI_URL_KEY = "java:comp/env/jndi.url";

	/** cle pour retrouver le nom de la classe factory d'acces à JNDI */
	public final static String JNDI_CLASSNAME_KEY = "java:comp/env/jndi.class";

}
