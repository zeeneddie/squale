package com.airfrance.welcom.taglib.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * 
 * @author M327837
 *
 * Etend la classe TextTag pour ajouter une methode prepareOthersAttributes
 */
public class BaseTextTag extends TextTag {

    /**
     * @see javax.servlet.jsp.tagext.Tag#doStartTag()
     */
    public int doStartTag() throws JspException {
        // Create an appropriate "input" element based on our parameters
        final StringBuffer results = new StringBuffer("<input type=\"");
        results.append(type);
        results.append("\" name=\"");

        // * @since Struts 1.1
        if (indexed) {
            prepareIndex(results, name);
        }

        results.append(property);
        results.append("\"");

        if (accesskey != null) {
            results.append(" accesskey=\"");
            results.append(accesskey);
            results.append("\"");
        }

        if (accept != null) {
            results.append(" accept=\"");
            results.append(accept);
            results.append("\"");
        }

        if (maxlength != null) {
            results.append(" maxlength=\"");
            results.append(maxlength);
            results.append("\"");
        }

        if (cols != null) {
            results.append(" size=\"");
            results.append(cols);
            results.append("\"");
        }

        if (tabindex != null) {
            results.append(" tabindex=\"");
            results.append(tabindex);
            results.append("\"");
        }

        prepareOthersAttributes(results);

        results.append(prepareValue());
        results.append(prepareEventHandlers());
        results.append(prepareStyles());
        results.append(getElementClose());

        // Print this field to our output writer
        ResponseUtils.write(pageContext, results.toString());

        // Continue processing this page
        return (EVAL_PAGE);
    }
    
    /**
     * Ecrit dans le stringbuffer la partie relative a la value
     * @return lattribut value
     * @throws JspException exception sur la recherche de l'objet
     */
    private String prepareValue() throws JspException {
        StringBuffer results = new StringBuffer();
        results.append(" value=\"");
        doRenderValue(results);
        results.append("\"");
        return results.toString();        
    }
    
    /**
     * Ecrit dans le stringbuffer la partie relative a la value 
     * @param results le stringbuffer
     * @throws JspException exception pouvant etre levee
     */
    protected void doRenderValue(final StringBuffer results) throws JspException {

        if (value != null) {
            results.append(ResponseUtils.filter(value));
        } else if (redisplay || !"password".equals(type)) {
            Object value = RequestUtils.lookup(pageContext, name, property, null);

            if (value == null) {
                value = "";
            }

            results.append(ResponseUtils.filter(value.toString()));
        }

    }
    
    /**
     * Ajoute a string buffer les attributs supplementaires
     * @param sb string buffer
     */
    protected void prepareOthersAttributes(StringBuffer sb) {
        // Dans ce ca ne fais rien
    }
    
}
