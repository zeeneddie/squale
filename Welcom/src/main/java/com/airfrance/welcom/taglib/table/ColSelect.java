/*
 * Créé le 4 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Charte;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.bean.WISelectable;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ColSelect extends ColDisabled {
    /** logger */
    private static Log log = LogFactory.getLog(ColSelect.class);

    /** signleSelect */
    protected boolean enableSingleSelect = false;
    /** ToolTip */
    protected String toolTip = "";
    /** onclick */
    protected String onclick = "";
    /** parametre du tag */
    private String specialHeaderTitle = "";

    /**
     * 
     * @param bean : Bean
     * @param position la position de la colonne
     * @param idIndex Index
     * @param style Style
     * @param styleSelect Style selectionne
     * @return le html généré
     */

    public String getSpecificContent(final int position, final Object bean, final int idIndex, final String style, final String styleSelect, final int pageLength) {

        String result=null;

        if (bean instanceof WISelectable) {
            if ((!getProperty().equals("")) && (!getProperty().equals("selected"))) {
                result = "Attention, le bean '" + bean.getClass() + "' implémente déjà WISelectable, on ne tient donc pas compte de la property '" + getProperty() + "'";
            }
            setProperty("selected");
        } else if (GenericValidator.isBlankOrNull(getProperty())) {
            result = "le bean '" + bean.getClass() + "' doit implémenter WISelectable, ou l'attribut property du tag <ColSelect> doit être renseigner";
        } 
        
        if (result==null) {
            result = writeSpecificContent(bean, idIndex, style, styleSelect);
        }

        return result;
    }

    /**
     * Ecrit le contenu spécifique
     * @param bean bean
     * @param idIndex index
     * @param style style
     * @param styleSelect style select
     * @return le contenu HTML
     */
    private String writeSpecificContent(final Object bean, final int idIndex, final String style, final String styleSelect) {

        final StringBuffer sb = new StringBuffer();

        String checked = getChecked(bean);
        
        String name = getCols().getTable().getName();
        String wdt = "";
        String st = style;
        
        if (isNeedWriteWidth(idIndex)) {
            wdt = " width=\"" + getWidth() + "\"";
        }
        if (WelcomConfigurator.getCharte() == Charte.V2_002) {
            st = "normal";
        }
        
        if (!GenericValidator.isBlankOrNull(getCols().getTable().getProperty())) {
            name = getCols().getTable().getProperty();
        }
        
        // Creation du TD
        sb.append("<td ");
        sb.append(wdt);
        sb.append(" classSelect=\"");
        sb.append(styleSelect);
        sb.append("\" classDefault=\"");
        sb.append(style);
        sb.append("\">");
        
        // Ajout de la gestion auto ...
        if (Util.isFalse(WelcomConfigurator.getMessage(WelcomConfigurator.OPTIFLUX_AUTORESET_CHECKBOX))) {
            sb.append("<input type=\"hidden\" name=\"checkName\" value=\"" + name + "[" + idIndex + "].selected" + "\">");
        }
        
        // Creation de la check box
        sb.append("<input class=\"");
        sb.append(st);
        sb.append("\" type=\"checkbox\" name=\"");
        sb.append(name);
        sb.append("[");
        sb.append(idIndex);
        sb.append("].");
        if (!GenericValidator.isBlankOrNull(getProperty())) {
            sb.append(getProperty());        	
        } else {
        	sb.append("selected");
        }
        sb.append("\" value=\"true\"");
        
        if (!enableSingleSelect) {
            sb.append("onclick=\"check(this);");
        } else {
            sb.append("onclick=\"checkSingle(this);");
        }
        if (!GenericValidator.isBlankOrNull(onclick)) {
            sb.append(onclick);
        }
        sb.append(";\"");
        
        if (Util.isTrue(checked)) {
            sb.append(" checked ");
        }
        
        sb.append("></td>");
        
        return sb.toString();
    }
    
    /** 
     * Retourne si le bean est checked
     * @param bean bean
     * @return si le bean est checked
     */
    private String getChecked(final Object bean) {
        String checked = "";
        try {
            checked = BeanUtils.getProperty(bean, getProperty());
        } catch (final IllegalAccessException e) {
            log.error(e, e);
        } catch (final InvocationTargetException e) {
            log.error(e, e);
        } catch (final NoSuchMethodException e) {
            log.error(e, e);
        }
        return checked;
    }
    /**
     * @see com.airfrance.welcom.taglib.table.Col#getSpecialHeaderContent()
     */
    public String getSpecialHeaderContent() {
        final StringBuffer sb = new StringBuffer();
        String name = getCols().getTable().getName();
        if (!GenericValidator.isBlankOrNull(getCols().getTable().getProperty())) {
            name = getCols().getTable().getProperty();
        }
        sb.append("<input type=\"checkbox\" id=\"selectAllCHK\" class=\"normal\" onclick=\"selectAll('");
        sb.append(name);
        sb.append("',this)\"");
        sb.append(" title=\"");
        sb.append(specialHeaderTitle);
        sb.append("\">");

        return sb.toString();
    }

    /**
     * @return accesseur
     */
    public boolean isEnableSingleSelect() {
        return enableSingleSelect;
    }

    /**
     * @param b accesseur
     */
    public void setEnableSingleSelect(final boolean b) {
        enableSingleSelect = b;
    }

    /**
     * @return accesseur
     */
    public String getToolTip() {
        return toolTip;
    }

    /**
     * @param string accesseur
     */
    public void setToolTip(final String string) {
        toolTip = string;
    }

    /**
     * @return onclick
     */
    public String getOnclick() {
        return onclick;
    }

    /**
     * @param string onclick
     */
    public void setOnclick(final String string) {
        onclick = string;
    }

    /**
     * @return specialHeaderTitle
     */
    public String getSpecialHeaderTitle() {
        return specialHeaderTitle;
    }

    /**
     * @param string specialHeaderTitle
     */
    public void setSpecialHeaderTitle(final String string) {
        specialHeaderTitle = string;
    }

}