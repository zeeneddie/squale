/*
 * Créé le 26 mai 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.transformer;

/**
 * @author M325379
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class UtilConvert {

    /**
     * Convertit un chaine en Float
     * @param str Chaine a convertir
     * @return Le Float correspondant ou null si la chaine est vide
     */
    public static Float stringToFloat(final String str) {
        if ((str != null) && (str.length() > 0)) {
            return Float.valueOf(str);
        } else {
            return null;
        }
    }

    /**
     * Convertit un chaine en Long
     * @param str Chaine a convertir
     * @return Le Long correspondant ou null si la chaine est vide
     */
    public static Long stringToLong(final String str) {
        if ((str != null) && (str.length() > 0)) {
            return Long.valueOf(str);
        } else {
            return null;
        }
    }

    /**
     * Convertit un chaine en Integer
     * @param str Chaine a convertir
     * @return Le Integer correspondant ou null si la chaine est vide
     */
    public static Integer stringToInteger(final String str) {
        if ((str != null) && (str.length() > 0)) {
            return Integer.valueOf(str);
        } else {
            return null;
        }
    }

    /**
     * Convertit un chaine en Character
     * @param str Chaine a convertir
     * @return Le Character correspondant ou null si la chaine est vide
     */
    public static Character stringToCharacter(final String str) {
        if ((str != null) && (str.length() > 0)) {
            return new Character(str.charAt(0));
        } else {
            return null;
        }
    }
}