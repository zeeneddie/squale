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
 * Créé le 9 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.jraf.provider.accessdelegate;

import org.squale.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader;
import org.squale.jraf.spi.accessdelegate.IAccessDelegateProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafProviderAccessdelegate
 * <p>Title : Initializer.java</p>
 * <p>Description : Initialiseur du provider access delegate</p>
 * <p>Copyright : Copyright (c) 2004</p>
 *  
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
	 * @see org.squale.jraf.provider.accessdelegate.AbstractInitializer#getAccessDelegateProvider(org.squale.jraf.provider.accessdelegate.ILookupComponent)
	 */
	public IAccessDelegateProvider getAccessDelegateProvider(ILookupComponent lookupComponent) {
		return new AccessDelegateProviderImpl(lookupComponent);
	}

	/* (non-Javadoc)
	 * @see org.squale.jraf.provider.accessdelegate.AbstractInitializer#buildLookupComponent(org.squale.jraf.provider.accessdelegate.config.IApplicationComponentConfigReader)
	 */
	protected ILookupComponent buildLookupComponent(IApplicationComponentConfigReader reader) {
		// lookup
		return new LookUpComponent(reader);
	}

}
