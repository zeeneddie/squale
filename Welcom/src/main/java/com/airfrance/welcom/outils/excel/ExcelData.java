/*
 * Créé le 28 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.excel;

import java.util.Locale;

import org.apache.struts.util.MessageResources;

/**
 * Classe de données à exporter dans un ficheir Excel.
 *
 * @author Rémy Bouquet
 *
 */
public abstract class ExcelData {
    
    /**
     * Locale Interne
     */
    protected Locale locale = null;
    
    /**
     * Bundle pour la traduction
     */
    protected MessageResources messages = null;
    
    /**
     * 
     * @param pLocale : c'est la locale de l'application pour l'internationnalisation
     * @param pMessages :un MessageResources initialisé avec le fichier ressource contenant les labels à utiliser dans le ficheir excel.
     */
    public ExcelData(final Locale pLocale, final MessageResources pMessages) {
        this.locale = pLocale;
        this.messages = pMessages;
    }

    /**
     *
     * @return retourne la locale.
     */
    public Locale locale() {
        return locale;
    }

    /**
     *
     * @param xlGenerateur : Un Excel générateur qui permet de formater la sortie du fichier excel.
     * @throws ExcelGenerateurException : Level une erreur sur la production du fichier
     */
    public abstract void fill(ExcelGenerateur xlGenerateur) throws ExcelGenerateurException;

    /**
     * permet d'aller chercher un message dans le MessageResources.
     * @param key : clé du libellé à aller chercher.
     * @return retourne le libellé correspondant à la clé passée en paramètre.
     */
    public String getMessage(final String key) {
        return messages.getMessage(locale, key);
    }
}