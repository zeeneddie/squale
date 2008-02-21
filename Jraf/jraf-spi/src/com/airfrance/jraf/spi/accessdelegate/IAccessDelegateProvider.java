/*
 * Créé le 8 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.jraf.spi.accessdelegate;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.spi.provider.IProvider;

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
