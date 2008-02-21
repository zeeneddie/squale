/*
 * Créé le 9 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * @author M327837
 *
 * Idem Tag CheckBox Struts, ajout seulement de la propritété unselectvalue
 * 
 */
public class CheckBoxTag extends org.apache.struts.taglib.html.CheckboxTag {

    /**
     * 
     */
    private static final long serialVersionUID = -8171403436103989961L;
    /** Valeur si check box pas selectionné*/
    private String unSelectValue = null;

    /**
     * Generate the required input tag.
     * <p>
     * Support for indexed property since Struts 1.1
     *
     * @exception JspException if a JSP exception has occurred
     * @return falg pour continuer
     */
    public int doStartTag() throws JspException {

        // Create an appropriate "input" element based on our parameters
        final StringBuffer results = new StringBuffer("<input type=\"checkbox\"");
        results.append(" name=\"");
        // * @since Struts 1.1
        if (indexed) {
            prepareIndex(results, name);
        }
        results.append(this.property);
        results.append("\"");
        if (accesskey != null) {
            results.append(" accesskey=\"");
            results.append(accesskey);
            results.append("\"");
        }
        if (tabindex != null) {
            results.append(" tabindex=\"");
            results.append(tabindex);
            results.append("\"");
        }
        /***** MODIF ******/
        if (unSelectValue != null) {
            results.append(" unSelectValue=\"");
            results.append(unSelectValue);
            results.append("\"");
        }
        /***** END MODIF ******/
        results.append(" value=\"");
        if (value == null) {
            results.append("on");
        } else {
            results.append(value);
        }
        results.append("\"");
        Object result = RequestUtils.lookup(pageContext, name, property, null);
        if (result == null) {
            result = "";
        }
        if (!(result instanceof String)) {
            result = result.toString();
        }
        final String checked = (String) result;
        if (checked.equalsIgnoreCase(value) || checked.equalsIgnoreCase("true") || checked.equalsIgnoreCase("yes") || checked.equalsIgnoreCase("on")) {
            results.append(" checked=\"checked\"");
        }
        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(getElementClose());

        // Print this field to our output writer
        ResponseUtils.write(pageContext, results.toString());

        // Continue processing this page
        this.text = null;
        return (EVAL_BODY_INCLUDE);

    }

    /**
     * @return accesseur
     */
    public String getUnSelectValue() {
        return unSelectValue;
    }

    /**
     * @param string accesseur
     */
    public void setUnSelectValue(final String string) {
        unSelectValue = string;
    }

}
