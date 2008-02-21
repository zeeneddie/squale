/*
 * Créé le 18 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.button.impl;

import java.util.Locale;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.RequestUtils;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.button.IButtonRenderer;

/**
 * ButtonSkinv1 : boutons statiques charte v001 et charte v002
 * 
 */
public class ButtonSkinv1 implements IButtonRenderer {
    /**
     * retourne une String pour la locale
     * @param page : Contexte de la page
     * @return la locale (en String)
     */
    private String getLocaleString(final PageContext page) {
        final Locale userLocale = RequestUtils.retrieveUserLocale(page, Globals.LOCALE_KEY);

        if (userLocale != null) {
            return userLocale.getLanguage();
        } else {
            return "fr";
        }
    }

    /**
     * retourne le path de l'image du bouton
     * @param type le type du bouton
     * @param name nom du bouton
     * @param page : Contexte de la page
     * @return le path
     */
    private String getButtonName(final PageContext page, final String name, final String rawValue, final String type) {
        String path = null;

        if (type.equals("form")) {
            path = WelcomConfigurator.getMessage("chartev2.bouton.path.form." + name, getLocaleString(page));
        } else {
            path = WelcomConfigurator.getMessage("chartev2.bouton.path.menu." + name, getLocaleString(page));
        }

        if (path != null) {
            return path;
        }

        // Recherche dans les diffs generique
        if (type.equals("form")) {
            path = WelcomConfigurator.getMessage(WelcomConfigurator.CHARTEV2_BOUTON_PATH_FORM, getLocaleString(page));
        } else {
            path = WelcomConfigurator.getMessage(WelcomConfigurator.CHARTEV2_BOUTON_PATH_MENU, getLocaleString(page));
        }

        path += ("/" + name + ".gif");

        return path;
    }

    /**
     * @see com.airfrance.welcom.taglib.button.IButtonRenderer#drawRenderHRefTag(StringBuffer results)
     */
    public String drawRenderHRefTag(final PageContext page, final Tag parent, final String name, final String rawValue, final String target, final String href, final String onclick, final String toolTip, final String styleId) {
        StringBuffer results = new StringBuffer();
        results.append("<a href=\"" + href);
        results.append("\" ");

		if (!GenericValidator.isBlankOrNull(onclick)) {
					results.append("onClick=\"" + onclick + "\" ");
				}

        if (!GenericValidator.isBlankOrNull(toolTip)) {
            results.append(toolTip);
        }

        if (!GenericValidator.isBlankOrNull(styleId)) {
            results.append(" id=\"" + styleId + "\"");
        }

        results.append("onclick=\"this.blur()\" >");
        results.append("<img src=\"" + getButtonName(page, name, rawValue, "form") + "\" border=\"0\"  align=\"absmiddle\">");
        results.append("</a>");
        return results.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.button.IButtonRenderer#drawRenderMenuHRefTag(StringBuffer results)
     */
    public String drawRenderMenuHRefTag(final PageContext page, final Tag parent, final String name, final String rawValue, final String target, final String href, final String onclick, final String toolTip, final String styleId) {
        StringBuffer results = new StringBuffer();

		String onClickAction= "";        
		if (!GenericValidator.isBlankOrNull(onclick)) {
			onClickAction = onclick;
		}
        
        if (WelcomConfigurator.getCharte() == Charte.V2_002) {
            results.append("<a href=\"" + href + "\" onclick=\"this.blur();" + onClickAction + "\" ");
        } else {
            results.append("<a href=\"" + href + "\" onclick=\"this.blur();" + onClickAction + "\" class=\"mymenu\"");
        }



        if (!GenericValidator.isBlankOrNull(target)) {
            results.append(" target=\"" + target + "\" ");
        }

        if (!GenericValidator.isBlankOrNull(styleId)) {
            results.append(" id=\"" + styleId + "\"");
        }

        if (!GenericValidator.isBlankOrNull(toolTip)) {
            results.append(toolTip);
        }

        results.append(">");
        results.append("<img src=\"" + getButtonName(page, name, rawValue, "menu") + "\" width=\"140\" height=\"18\" border=\"0\">");
        results.append("</a>");
        return results.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.button.IButtonRenderer#drawRenderFormHRefTag(StringBuffer results)
     */
    public String drawRenderFormHRefTag(final PageContext page, final Tag parent, final String name, final String rawValue, final String target, final String onclick, final String toolTip, final String styleId) {
        StringBuffer results = new StringBuffer();
        results.append("<a href=\"javascript:");
        results.append(onclick);
        results.append("\" ");

        if (!GenericValidator.isBlankOrNull(toolTip)) {
            results.append(toolTip);
        }

        if (!GenericValidator.isBlankOrNull(styleId)) {
            results.append(" id=\"" + styleId + "\"");
        }

        results.append(" onclick=\"this.blur()\" >");
        results.append("<img src=\"" + getButtonName(page, name, rawValue, "form") + "\" border=\"0\"  align=\"absmiddle\">");
        results.append("</a>");
        return results.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.button.IButtonRenderer#drawRenderFormInputTag(StringBuffer results)
     */
    public String drawRenderFormInputTag(final PageContext page, final Tag parent, final String name, final String rawValue, final String target, final String onclick, final String toolTip, final String styleId) {
        StringBuffer results = new StringBuffer();
        results.append("<input");

        if (!GenericValidator.isBlankOrNull(toolTip)) {
            results.append(toolTip);
        }

        if (!GenericValidator.isBlankOrNull(styleId)) {
            results.append(" id=\"" + styleId + "\"");
        }

        results.append(" class=\"normal\" type=\"image\" name=\"Submit\" value=\"Submit\"");
        results.append(" src=\"" + getButtonName(page, name, rawValue, "form") + "\"");

        results.append(" onclick=\"" + onclick + "\"");
        results.append(" />");
        return results.toString();
    }
}