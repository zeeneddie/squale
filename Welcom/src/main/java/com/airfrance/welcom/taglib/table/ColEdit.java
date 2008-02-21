/*
 * Créé le 4 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.bean.WIEditable;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColEdit extends ColDisabled implements ICol {
    /** logger */
    private static Log log = LogFactory.getLog(ColEdit.class);
    /** ordre ascendant */
    protected String titleEdit = "";
    /** ordre descendant */
    protected String titleCancel = "";
    /** From (pour revenir sur la table ) */
    protected String from;
    /** Base URL */
    protected String baseURL = "";
    /** RequestURI */
    protected String requestURI = "";

    /**
     * Constructeur
     *
     */
    public ColEdit() {

    }

    /**
    * @see com.airfrance.welcom.taglib.table.ICol#getCurrentValue(int, java.lang.Object, int, java.lang.String, java.lang.String)
    */
    public String getSpecificContent(final int position, final Object bean, final int idIndex, final String style, final String styleSelect, final int pageLength) throws JspException {

        final StringBuffer s = new StringBuffer();
        final String formName = getCols().getTable().getFormName();
        
        if (GenericValidator.isBlankOrNull(formName)){
        	throw new JspException("ColEditTag  must be used inside a form tag.");
        }
        
        if (bean instanceof WIEditable) {
            if ((!getProperty().equals("")) && (!getProperty().equals("edited")) ) {
                return ("Attention, le bean '" + bean.getClass() + "' implémente déjà WIEditable, on ne tient donc pas compte de la property '" + getProperty() + "'");
            }
            setProperty("edited");
        } else if (GenericValidator.isBlankOrNull(getProperty())) {
            return ("le bean '" + bean.getClass() + "' doit implémenter WIEditable, ou l'attribut property du tag <ColEdit> doit être renseigner");

        }
        
        
        String result="";
        try {
            result = BeanUtils.getProperty(bean, getProperty());
        } catch (final IllegalAccessException e) {
            log.error(e,e);
        } catch (final InvocationTargetException e) {
            log.error(e,e);
        } catch (final NoSuchMethodException e) {
            log.error(e,e);
        }

        s.append("<a href=\"");
        if (Util.isTrue(result)) {
            s.append("javascript:tableForward('" + formName + "','" + getHref("false", idIndex, pageLength) + "')\">");
            s.append("<img src=\"");
            s.append(WelcomConfigurator.getMessageWithCfgChartePrefix(".htmltable.images.coledit.cancel"));
            s.append("\"");
            if (!GenericValidator.isBlankOrNull(titleCancel)) {
                s.append(" title=\"");
                s.append(titleCancel);
                s.append("\"");
            }
        } else {
            s.append("javascript:tableForward('" + formName + "','" + getHref("true", idIndex, pageLength) + "')\">");
            s.append("<img src=\"");
            s.append(WelcomConfigurator.getMessageWithCfgChartePrefix(".htmltable.images.coledit.edit"));
            s.append("\"");
            if (!GenericValidator.isBlankOrNull(titleEdit)) {
                s.append(" title=\"");
                s.append(titleEdit);
                s.append("\"");
            }
        }
        s.append(" border=0>");
        s.append("</a>");
        return s.toString();
    }

    /**
      * 
      * @param position la position de la colonne
      * @param edited en edition
      * @return href en rajoutant le sens
      */
    private String getHref(final String edited, final int position, final int pageLength) {
        final StringBuffer s = new StringBuffer();
        s.append(baseURL);
        s.append("/");
        s.append(Util.SERVEPATH);
        s.append("?requestURI=");
        s.append(Util.encode(getRequestURI()));
        s.append("&from=");
        
        if (from == null){
			s.append((position / pageLength) * pageLength) ;
        }
        else {
			s.append(getFrom());
        }
        
        s.append("&");
        s.append(getCols().getPropertyFull(position));
        s.append(".");
        s.append(getProperty());
        s.append("=");
        s.append(edited);
        s.append("&");
        s.append(getCols().getPropertyFull(position));
        s.append(".changed=true");
        return s.toString();
    }

    /**
     * @return accesseur
     */
    public String getTitleCancel() {
        return titleCancel;
    }

    /**
     * @return accesseur
     */
    public String getTitleEdit() {
        return titleEdit;
    }

    /**
     * @param string accesseur
     */
    public void setTitleCancel(final String string) {
        titleCancel = string;
    }

    /**
     * @param string accesseur
     */
    public void setTitleEdit(final String string) {
        titleEdit = string;
    }

    /**
     * @return accesseur
     */
    public String getFrom() {
        if (from == null) {
            return "0";
        }
        return from;
    }

    /**
     * @param string accesseur
     */
    public void setFrom(final String string) {
        from = string;
    }

    /**
     * @return accesseur
     */
    public String getBaseURL() {
        return baseURL;
    }

    /**
     * @param string accesseur
     */
    public void setBaseURL(final String string) {
        baseURL = string;
    }

    /**
     * @return accesseur
     */
    public String getRequestURI() {
        return requestURI;
    }

    /**
     * @param string accesseur
     */
    public void setRequestURI(final String string) {
        requestURI = string;
    }

}