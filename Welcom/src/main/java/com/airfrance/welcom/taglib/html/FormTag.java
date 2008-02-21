/*
 * Créé le 18 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.html;

import java.util.Locale;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.ResponseUtils;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class FormTag extends org.apache.struts.taglib.html.FormTag {

    /**
     * 
     */
    private static final long serialVersionUID = 5442988797985217865L;
    /** constante*/
    public static final String UNSELECT_CHECKBOX_FIELD = "wUnSelectBox";
    /** attribut*/
    private String functionNameTestSend = "envoi";
    /** message ressource */
    protected MessageResources resources = null;
    /** locale*/
    protected Locale localeRequest = Locale.FRENCH;
    /** action par defaut*/
    private String defaultAction = "";
    /**
     *
     */
    public FormTag() {
        super();
    }

    /**
     * @see org.apache.struts.taglib.html.FormTag#doStartTag()
     */
    public int doStartTag() throws JspException {
        // Recuperer le fichier des Bundle
        resources = (MessageResources) pageContext.getServletContext().getAttribute(Globals.MESSAGES_KEY);

        // Recupere la locale de la page
        localeRequest = (Locale) pageContext.getSession().getAttribute(Globals.LOCALE_KEY);

        if (!GenericValidator.isBlankOrNull(getOnsubmit())) {
            if (getOnsubmit().startsWith("return ")) {
                setOnsubmit(getOnsubmit().substring(7, getOnsubmit().length()));
                if ((getOnsubmit().trim().length() > 0) && getOnsubmit().trim().endsWith(";")) {
                    setOnsubmit(getOnsubmit().trim().substring(0, getOnsubmit().trim().length() - 1));
                }
                setOnsubmit("return " + getOnsubmit() + " && " + functionNameTestSend + "();");
            } else {
                setOnsubmit(getOnsubmit() + " ; return " + functionNameTestSend + "();");
            }
        } else {
            setOnsubmit("return " + functionNameTestSend + "()");
        }

        // Ajout des fonctions 
        ResponseUtils.write(pageContext, writeFunctionSingleSend());

        return super.doStartTag();
    }

    /**
     * @see org.apache.struts.taglib.html.FormTag#doEndTag()
     */
    public int doEndTag() throws JspException {
        ResponseUtils.write(pageContext, "<input type='hidden' name='action' value=\"" + defaultAction + "\">");

        ResponseUtils.write(pageContext, "<input type='hidden' name='" + UNSELECT_CHECKBOX_FIELD + "'>");

        release();

        return super.doEndTag();
    }

    /**
     * 
     * @return le html a renvoyer
     */
    public String writeFunctionSingleSend() {
        final StringBuffer buf = new StringBuffer();
        buf.append("<script language=\"javascript\">");
        buf.append("var unique=0;");
        buf.append("var msgconfirm=null;");
        buf.append("function " + functionNameTestSend + "()");
        buf.append("{");
        buf.append("if (unique <= 0)");
        buf.append("{");
        buf.append("unique++;");
        buf.append("if (msgconfirm==null) {return true;};");
        buf.append("if (confirm(msgconfirm))");
        buf.append(" { msgconfirm=null; return true; }");
        buf.append("else { unique--; msgconfirm=null; document.body.style.cursor='default';return false; }");
        buf.append("}");
        buf.append("else");
        buf.append("{");
        buf.append("alert(\"");
        buf.append(resources.getMessage(localeRequest, "welcom.internal.doublevalidation.message"));
        buf.append("\");");
        buf.append("return false;");
        buf.append("}");
        buf.append("};");
        buf.append("function resetUnique()");
        buf.append("{");
        buf.append("unique--;");
        buf.append("};");
        buf.append("function setMsg(msg)");
        buf.append("{");
        buf.append("msgconfirm=msg;");
        buf.append("}");
        buf.append("</script>");

        return buf.toString();
    }

    /**
     * @return functionNameTestSend
     */
    public String getFunctionNameTestSend() {
        return functionNameTestSend;
    }

    /**
     * @param string functionNameTestSend
     */
    public void setFunctionNameTestSend(final String string) {
        functionNameTestSend = string;
    }

    /**
     * 
     * @return le formName
     */
    public String getFormName() {
        try {
            return BeanUtils.getProperty(this, "beanName");
        } catch (final Exception e) {
            try {
                return BeanUtils.getProperty(this, "name");
            } catch (final Exception e2) {
                // On ne change pas le contenu
                return "0";
            }
        }
    }
    /**
     * @return recupere l'action
     */
    public String getDefaultAction() {
        return defaultAction;
    }

    /**
     * @param string Sette l'action
     */
    public void setDefaultAction(final String string) {
        defaultAction = string;
    }

    /**
     * Relache tou les events
     */
    public void release() {
        super.release();
        functionNameTestSend = "envoi";
        resources = null;
        localeRequest = Locale.FRENCH;
        defaultAction = "";
    }

}