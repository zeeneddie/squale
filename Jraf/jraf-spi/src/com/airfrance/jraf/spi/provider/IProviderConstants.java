/*
 * Created on Mar 9, 2004
 */
package com.airfrance.jraf.spi.provider;

/**
 * <p>Title : IProviderConstants.java</p>
 * <p>Description : Constantes communes aux providers</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public interface IProviderConstants {

	/** cle pour retrouver le provider de logging */
	public final static String BOOTSTRAP_PROVIDER_KEY = "bootstrap";

	/** cle pour retrouver le provider de logging */
	public final static String LOGGING_PROVIDER_KEY = "logging";
	
	/** cle pour retrouver le provider de persistence */
	public final static String PERSISTENCE_PROVIDER_KEY = "persistence";

	/** cle pour retrouver le provider d'access */
	public final static String ACCESS_DELEGATE_PROVIDER_KEY = "accessdelegate";

	/** cle pour retrouver le provider adhesion */
	public final static String ADHESION_PROVIDER_KEY = "adhesion";

}
