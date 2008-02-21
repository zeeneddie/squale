/*
 * Créé le 1 févr. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class LocaleUtil {

    /** Liste des locales */
    private static ArrayList listLocale;

    /**
     * Retourne la locale suivante a celle en cours
     * @param curlocale : Locale
     * @return Locale suivante
     */
    public static String getNextLocale(final String curlocale) {
        final ArrayList list = getAvailableLocales();
        int index = list.indexOf(curlocale);
        if (index == list.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        return (String) list.get(index);
    }

    /**
     * Retourne la liste des locales valides pour l'application
     * @return Liste des locales
     */
    public static ArrayList getAvailableLocales() {
        if (listLocale == null) {
            listLocale = new ArrayList();

            final String localesStr = WelcomConfigurator.getMessage(WelcomConfigurator.ADDONS_MESSAGE_MANAGER_LOCALES);
            if (localesStr != null) {
                final StringTokenizer tokenizer = new StringTokenizer(localesStr, ",");
                while (tokenizer.hasMoreTokens()) {
                    listLocale.add(new String(tokenizer.nextToken()));
                }
            }
        }
        return listLocale;
    }
}
