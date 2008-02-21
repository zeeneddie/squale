/*
 * Créé le 9 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.jraf.provider.accessdelegate;

import com.airfrance.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader;
import com.airfrance.jraf.spi.accessdelegate.IAccessDelegateProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : Initializer.java</p>
 * <p>Description : Initialiseur du provider access delegate</p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public class Initializer extends AbstractInitializer {

	/**
	 * Constructeur vide type IOC2
	 */
	public Initializer() {
		super();
	}

	/**
	 * Constructeur avec parametres type IOC3
	 * @param rootPath
	 * @param configFileName
	 */
	public Initializer(String rootPath, String configFileName) {
		super(rootPath, configFileName);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.accessdelegate.AbstractInitializer#getAccessDelegateProvider(com.airfrance.jraf.provider.accessdelegate.ILookupComponent)
	 */
	public IAccessDelegateProvider getAccessDelegateProvider(ILookupComponent lookupComponent) {
		return new AccessDelegateProviderImpl(lookupComponent);
	}

	/* (non-Javadoc)
	 * @see com.airfrance.jraf.provider.accessdelegate.AbstractInitializer#buildLookupComponent(com.airfrance.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader)
	 */
	protected ILookupComponent buildLookupComponent(IApplicationComponentConfigReader reader) {
		// lookup
		return new LookUpComponent(reader);
	}

}
