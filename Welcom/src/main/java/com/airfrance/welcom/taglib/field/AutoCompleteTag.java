/*
 * Créé le 2 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.field;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * AutoCompleteTag
 */
public class AutoCompleteTag extends BaseTextTag {
    /**
     * 
     */
    private static final long serialVersionUID = 4279121296835714925L;
    /** parametre du tag */
    private String onitemselection;

    /** 
     * Ajoute l'attribut onintemselection
     */
    protected void prepareOthersAttributes(StringBuffer results) {

        if (Util.isTrue(WelcomConfigurator.getMessage(WelcomConfigurator.EASY_COMPLETE_NOTIFIER))) {
            setStyleClass(getStyleClass() + " suggest");
        }
        
        if (onitemselection != null) {
            results.append(" onitemselection=\"");
            results.append(onitemselection.replaceAll("this", "target"));
            results.append("\"");
        }

        results.append(" autocomplete=\"off\" ");

    }
    

    /**
     * @return onitemselection
     */
    public String getOnitemselection() {
        return onitemselection;
    }

    /**
     * @param string onitemselection
     */
    public void setOnitemselection(final String string) {
        onitemselection = string;
    }

}