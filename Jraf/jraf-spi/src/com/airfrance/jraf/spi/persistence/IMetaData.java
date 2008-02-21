/*
 * Créé le 8 janv. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.jraf.spi.persistence;

import com.airfrance.jraf.commons.exception.JrafConfigException;
import com.airfrance.jraf.commons.exception.JrafDaoException;

/**
 * @author M312644
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface IMetaData {
	
	/**
	 * Retourne le nom de l'attribut identifiant pour la classe passée en paramètre.
	 * @param clazz
	 * @return
	 */
	public String getIdentifierName(Class clazz) throws JrafDaoException;

	/**
	 * Retourne la taille de la colonne
	 * @param clazz Nom de la classe
	 * @param columnName Nom de la colonne
	 * @return Taille de la colonne
	 * @throws JrafDaoException
	 */
	public int getColumnMaxSize(Class clazz, String columnName) throws JrafConfigException;
}
