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
 * Créé le 22 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.table;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface ITableNavigatorRenderer
{

    /**
     * Rendu : pour l'ecriture de l'enpied de table
     * 
     * @param formName : nom du formaulaire
     * @param href : lien hypertexte
     * @param ipagesPerNavBar : ipagesPerNavBar
     * @param localeRequest : locale de la requete
     * @param pFrom pform
     * @param pLength plenght
     * @param pVolume pvalume
     * @param resources resource
     * @param tableName nom de la table
     * @return la barre de navigation
     */
    public String drawBar( MessageResources resources, Locale localeRequest, String formName, String href, int pFrom,
                           int pVolume, int pLength, int ipagesPerNavBar, String tableName );

}