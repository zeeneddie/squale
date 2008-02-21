/*
 * Créé le 10 janv. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.multilingue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.Globals;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.LocaleUtil;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.field.FieldTag;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class MultilingueFieldTag extends FieldTag {

    /**
     * 
     */
    private static final long serialVersionUID = -921691760028293864L;

    /**
     * @see com.airfrance.welcom.taglib.field#doStartTag() 
     */
    public int doStartTag() throws JspException {
        if (getType().equalsIgnoreCase("TEXTAREA") || getType().equalsIgnoreCase("TEXT")) {
            return EVAL_PAGE;
        } else {
            throw new JspException("Multilingue impossible pour ce type ! ");
        }
    }

    /**
     * @see com.airfrance.welcom.taglib.field#doEndTag() 
     */
    public int doEndTag() throws JspException {

        // Recuperation de l'object
        final WILocaleString bean = (WILocaleString) LayoutUtils.getBeanFromPageContext(pageContext, name, property);

        if (!(bean instanceof WILocaleString)) {
            throw new JspException("Bean should be type of WILocaleString");
        }

        // display the label
        StringBuffer buffer = new StringBuffer();
        beginField(buffer);
        ResponseUtils.write(pageContext, buffer.toString());
        final ArrayList locales = LocaleUtil.getAvailableLocales();
        final String localeSession = ((Locale) pageContext.getSession().getAttribute(Globals.LOCALE_KEY)).getLanguage();
        if (!locales.isEmpty()) {
            final Iterator iterLocale = locales.iterator();
            while (iterLocale.hasNext()) {
                final String curLocale = (String) iterLocale.next();

                String masquer = "";
                if (!curLocale.equals(localeSession)) {
                    masquer = "style=\"display:none\"";
                }
                ResponseUtils.write(pageContext, "<span id=\"span." + getPropertyLocale(curLocale) + "\" " + masquer + ">");
                createFieldTag(curLocale);
                createImgFlag(curLocale);
                ResponseUtils.write(pageContext, "</span>");
            }
        }

        // display the label
        buffer = new StringBuffer();
        endField(buffer);
        ResponseUtils.write(pageContext, buffer.toString());

        return EVAL_PAGE;
    }

    /**
     * 
     * @param locale la locale
     * @throws JspException exception pouvant etre levee
     */
    private void createFieldTag(final String locale) throws JspException {
        final FieldTag textTag = new FieldTag();
        try {
            BeanUtils.copyProperties(textTag, this);

        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        } catch (final InvocationTargetException e) {
            e.printStackTrace();
        }
        // Change le nom de la properti pour que l'appel soit correct
        textTag.setProperty(getPropertyLocale(locale));

        // Supprime les TD et la clef
        textTag.setKey("");
        textTag.setWriteTD(false);

        textTag.doStartTag();
        textTag.doEndTag();

    }

    /**
     * Procedure de creation de l'image
     * @param locale la locale
     * @throws JspException exception pouvant etre levee
     */
    private void createImgFlag(final String locale) throws JspException {
        final StringBuffer sb = new StringBuffer();
        sb.append("<a href=\"#\" ");
        sb.append("onclick=\"displayMutilingueField('");
        sb.append("span." + getPropertyLocale(locale));
        sb.append("','");
        sb.append("span." + getPropertyLocale(LocaleUtil.getNextLocale(locale)));
        sb.append("')\" >");
        sb.append("<img align=\"absmiddle\" border=\"0\" src=\"");
        sb.append(WelcomConfigurator.getMessageWithCfgChartePrefix(".field.path.flag." + locale));
        sb.append("\" >");
        sb.append("</a>");
        ResponseUtils.write(pageContext, sb.toString());
    }

    /**
     * Retourne la proptery pour un champ
     * @param locale la locale
     * @return la property 
     */
    private String getPropertyLocale(final String locale) {
        if (this.getProperty() != null) {
            return (this.getProperty() + ".string(" + locale + ")");
        } else {
            return ("string(" + locale + ")");
        }

    }

}
