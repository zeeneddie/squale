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
 * Créé le 8 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.jraf.spi.accessdelegate;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.spi.provider.IProvider;

/**
 * <p>Project: JRAF 
 * <p>Module: jrafSpi
 * <p>Title : IAccessDelegateProvider.java</p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Company : AIRFRANCE </p>
 */
public interface IAccessDelegateProvider extends IProvider {

	public IApplicationComponent getInstance(String applicationComponentName)throws JrafEnterpriseException;
	
	/**
	 * Method validateService. Retourne la valeur true si le component est valide.
	 * @param applicationcomponent
	 * @param namecomponent
	 * @return boolean
	 */
	public boolean validateService(String applicationcomponent, String nameService);

}
