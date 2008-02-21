/*
 * Créé le 22 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public interface ITableNavigatorRenderer {

    /**
     * Rendu : pour l'ecriture de l'enpied de table
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
    public String drawBar(MessageResources resources, Locale localeRequest, String formName, String href, int pFrom, int pVolume, int pLength, int ipagesPerNavBar, String tableName);

}