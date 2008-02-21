/*
 * Créé le 16 juil. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.htmlarea;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.struts.bean.WComboValueLabel;
import com.airfrance.welcom.struts.bean.WCouple;

/**
 * @author M327836
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CustomToolTag extends BodyTagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = -8218424225303276713L;
    /** Constante*/
    private static final String DEFAULT_REFRESH = "function(editor) { }";
    /** Constante*/
    private static final String DROPDOWN = "DROPDOWN";
    /** Constante*/
    private static final String BUTTON = "BUTTON";
    /** Constante*/
    private static final String BEFORE = "BEFORE";
    /** Constante*/
    private static final String AFTER = "AFTER";
    /** parametre du tag*/
    private String id;
    /** parametre du tag*/
    private String tooltip = null;
    /** parametre du tag*/
    private String options = null;
    /** parametre du tag*/
    private String image = null;
    /** parametre du tag*/
    private boolean textMode = false;
    /** parametre du tag*/
    private String type;
    /** parametre du tag*/
    private String action;
    /** parametre du tag*/
    private String refresh = DEFAULT_REFRESH;
    /** parametre du tag*/
    private HtmlAreaTag htmlAreaTag;
    /** parametre du tag*/
    private String insert = null;
    /** parametre du tag*/
    private String collection = null;
    /** parametre du tag*/
    protected MessageResources resources = null;
    /** parametre du tag*/
    protected Locale localeRequest = Locale.FRENCH;
    /** parametre du tag*/
    private String edName = null;

    /**
     * 
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        //Recherche si un parent est du bon type
        Tag curParent = null;

        for (curParent = getParent();(curParent != null) && !(curParent instanceof HtmlAreaTag);) {
            curParent = curParent.getParent();
        }

        htmlAreaTag = (HtmlAreaTag) curParent;
        edName = htmlAreaTag.getEdName();

        if (htmlAreaTag == null) {
            throw new JspException("customtool tag must be used between htmlarea tag.");
        }

        localeRequest = (Locale) pageContext.getSession().getAttribute(Globals.LOCALE_KEY);
        resources = (MessageResources) pageContext.getServletContext().getAttribute(Globals.MESSAGES_KEY);

        if (type.toUpperCase().equals(BUTTON)) {
            registerButton();
        }

        if (type.toUpperCase().equals(DROPDOWN)) {
            registerDropDown();
        }

        if (!GenericValidator.isBlankOrNull(insert)) {
            final TrimStringBuffer sb = new TrimStringBuffer();

            if (insert.toUpperCase().equals(AFTER)) {
                sb.append(edName + ".config.toolbar[0].push(['" + id + "']);");
            }

            if (insert.toUpperCase().equals(BEFORE)) {
                sb.append(edName + ".config.toolbar[0].unshift(['" + id + "']);");
            }

            ResponseUtils.write(pageContext, sb.toString());
        }

        return EVAL_PAGE;
    }

    /**
     * 
     * @throws JspException exception pouvant etre levee
     */
    private void registerDropDown() throws JspException {
        final TrimStringBuffer sb = new TrimStringBuffer();

        sb.append(edName + ".config.registerDropdown({\n");
        sb.append("id : '" + id + "',\n");

        if (!GenericValidator.isBlankOrNull(tooltip)) {
            String mess = null;

            mess = resources.getMessage(localeRequest, tooltip);

            if (GenericValidator.isBlankOrNull(mess)) {
                mess = tooltip;
            }

            sb.append("tooltip : '" + mess + "',\n");
        }

        if (!GenericValidator.isBlankOrNull(collection)) {
            final Object o = super.pageContext.findAttribute(collection);

            if (o == null) {
                final JspException e = new JspException("Objet " + collection + " introuvable dans le scope");
                RequestUtils.saveException(super.pageContext, e);
                throw e;
            }

            Iterator iter = null;

            if (o instanceof Collection) {
                iter = ((Collection) o).iterator();
            }

            if (o instanceof WComboValueLabel) {
                iter = ((WComboValueLabel) o).iterator();
            }

            if (iter == null) {
                throw new JspException("Le bean " + collection + "doit être de type Collection ou WComboValueLabel");
            }

            sb.append("options : {");

            String opt = "";

            while (iter.hasNext()) {
                final Object element = iter.next();

                if (!(element instanceof WCouple)) {
                    throw new JspException("Le bean " + collection + "doit contenir des objets de type WCouple");
                }

                final WCouple wc = (WCouple) element;
                String value = wc.getValue();

                if (GenericValidator.isBlankOrNull(value)) {
                    value = wc.getLabel();
                }

                opt += ("'" + wc.getLabel() + "':'" + value + "',");
            }

            opt = opt.substring(0, opt.length() - 1);
            sb.append(opt);
            sb.append("},\n");
        } else if (!GenericValidator.isBlankOrNull(options)) {
            String mess = null;

            mess = resources.getMessage(localeRequest, options);

            if (GenericValidator.isBlankOrNull(mess)) {
                mess = options;
            }

            sb.append("options : {" + mess + "},\n");
        } else {
            throw new JspException("Aucune option pour le combo " + id);
        }

        sb.append("refresh  : " + refresh + ",\n");
        sb.append("action   : " + action);
        sb.append("\n});\n");
        ResponseUtils.write(pageContext, sb.toString());
    }

    /**
     * 
     * @throws JspException exception pouvant etre levee
     */
    private void registerButton() throws JspException {
        final TrimStringBuffer sb = new TrimStringBuffer();

        sb.append(edName + ".config.registerButton({\n");
        sb.append("id : '" + id + "',\n");

        if (!GenericValidator.isBlankOrNull(tooltip)) {
            String mess = null;

            mess = resources.getMessage(localeRequest, tooltip);

            if (GenericValidator.isBlankOrNull(mess)) {
                mess = tooltip;
            }

            sb.append("tooltip : '" + mess + "',\n");
        }

        if (!GenericValidator.isBlankOrNull(image)) {
            sb.append("image : '" + image + "',\n");
        }

        sb.append("textMode : '" + textMode + "',\n");
        sb.append("action : " + action);
        sb.append("\n});\n");
        ResponseUtils.write(pageContext, sb.toString());
    }

    /**
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @return image
     */
    public String getImage() {
        return image;
    }

    /**
     * @return options
     */
    public String getOptions() {
        return options;
    }

    /**
     * @return refresh
     */
    public String getRefresh() {
        return refresh;
    }

    /**
     * @return textMode
     */
    public boolean isTextMode() {
        return textMode;
    }

    /**
     * @return tooltip
     */
    public String getTooltip() {
        return tooltip;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param string action
     */
    public void setAction(final String string) {
        action = string;
    }

    /**
     * @param string id
     */
    public void setId(final String string) {
        id = string;
    }

    /**
     * @param string image
     */
    public void setImage(final String string) {
        image = string;
    }

    /**
     * @param string options
     */
    public void setOptions(final String string) {
        options = string;
    }

    /**
     * @param string refresh
     */
    public void setRefresh(final String string) {
        refresh = string;
    }

    /**
     * @param b textMode
     */
    public void setTextMode(final boolean b) {
        textMode = b;
    }

    /**
     * @param string tooltip
     */
    public void setTooltip(final String string) {
        tooltip = string;
    }

    /**
     * @param string type
     */
    public void setType(final String string) {
        type = string;
    }

    /**
     * @return insert
     */
    public String getInsert() {
        return insert;
    }

    /**
     * @param string insert
     */
    public void setInsert(final String string) {
        insert = string;
    }

    /**
     * @return collection
     */
    public String getCollection() {
        return collection;
    }

    /**
     * @param string collection
     */
    public void setCollection(final String string) {
        collection = string;
    }
}