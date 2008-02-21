/*
 * Créé le 15 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class Table {

    /**
     * @return Ligne 1px, pour separation en charte v2_001
     */
    public static String ligneRail() {
        if (WelcomConfigurator.getCharte() == Charte.V2_001) {
            return ("\t <tr class=\"rail\"><td colspan=\"50\"></td></tr>\n");
        } else {
            return "";
        }
    }
}