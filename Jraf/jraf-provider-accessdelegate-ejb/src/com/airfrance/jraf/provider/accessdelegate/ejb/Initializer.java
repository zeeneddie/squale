/*
 * Created on Mar 12, 2004
 */
package com.airfrance.jraf.provider.accessdelegate.ejb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.provider.accessdelegate.AbstractInitializer;
import com.airfrance.jraf.provider.accessdelegate.ILookupComponent;
import com.airfrance.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader;

/**
 * <p>Title : Initializer.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE</p>
 */
public class Initializer extends AbstractInitializer {

	/** logger */
	private static final Log log = LogFactory.getLog(Initializer.class);

	/**
	 * Constructeur vide type IOC2
	 */
	public Initializer() {
		super();
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param rootPath chemin racine
	 * @param configFileName chemin vers le fichier de configuration
	 */
	public Initializer(String rootPath, String configFileName) {
		super(rootPath, configFileName);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.accessdelegate.AbstractInitializer#buildLookupComponent(com.airfrance.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader)
	 */
	protected ILookupComponent buildLookupComponent(IApplicationComponentConfigReader reader) {
		return new EJBLookupComponent(reader);
	}

}
