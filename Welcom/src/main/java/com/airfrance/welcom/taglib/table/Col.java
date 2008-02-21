/*
 * Créé le 30 mars 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.taglib.field.util.LayoutUtils;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class Col implements Comparable, ICol {

    /** logger */
    private static Log log = LogFactory.getLog(Col.class);

    /** parametre du tag*/
    private String property;
    /** parametre du tag*/
    private String key;
    /** parametre du tag*/
    private boolean sortable = false;
    /** parametre du tag*/
    private boolean editable = false;

    /** parametre du tag*/
    private String paramId = "";
    /** parametre du tag*/
    private String paramName = "";
    /** parametre du tag*/
    private String paramProperty = "";
    /** parametre du tag*/
    private String link = "";
    /** parametre du tag*/
    private String width = "";
    /** parametre du tag*/
    private PageContext pageContext = null;
    /** parametre du tag*/
    protected String currentValue = "";
    /** parametre du tag*/
    private boolean writeTD = true;
    /** parametre du tag*/
    private String type;
    /** parametre du tag*/
    private String dateFormatKey;
    /** parametre du tag*/
    private String dateFormat;
    /** parametre du tag*/
    private String emptyKey = "";
    /** cols .. la Collection, setté lors de l'ajout */
    private Cols cols = null;
    /** parametre du tag */
    private String contentClass = "";
    /** parametre du tag */
    private String contentStyle = "";
    /** parametre du tag */
    private String headerStyle = "";
    /** parametre du tag */
    private String headerClass = "";
    /** parametre du tag */
    private String headerTruncate = "";
    /** parametre du tag */
    private String contentTruncate = "";
    /** parametre du tag */
    private boolean contentNoWrap = false;
    /** parametre du tag */
    private boolean headerNoWrap = true;

    /**parametre interne */
    protected boolean specialHeader = false;
    /** param*/
    protected String specialHeaderContent = "";

    /**
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param string la nouvelle key
     */
    public void setKey(final String string) {
        key = string;
    }

    /**
     * @param string la nouvelle property
     */
    public void setProperty(final String string) {
        property = string;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final Object o) {
        return this.getKey().compareTo(((Col) o).getKey());
    }

    /**
     * @return sortable
     */
    public boolean isSortable() {
        return sortable;
    }

    /**
     * @param b le nouveau sortable
     */
    public void setSortable(final boolean b) {
        sortable = b;
    }

    /**
     * @return paramId
     */
    public String getParamId() {
        return paramId;
    }

    /**
     * @param string le nouveau paramId
     */
    public void setParamId(final String string) {
        paramId = string;
    }

    /**
    * @return le href construit
    */
    public String getHref() {
        String linkParams = "";

        if (GenericValidator.isBlankOrNull(link)) {
            return null;
        }

        if (GenericValidator.isBlankOrNull(paramName)) {
            return link;
        }

        try {
            final Object tmpLinkParams = LayoutUtils.getBeanFromPageContext(pageContext, paramName, paramProperty);

            if (tmpLinkParams.getClass() == String.class) {
                // Retour d'un string: on caste sans problème
                linkParams = (String) tmpLinkParams;
            } else {
                // Retour d'un pas string: en général un nombre (id)
                linkParams += tmpLinkParams;
            }
        } catch (final JspException e) {
            log.error(e, e);
            return link;
        }

        String carsup = "";

        if (!GenericValidator.isBlankOrNull(link)) {
            if ((link.indexOf("?") > -1) && !GenericValidator.isBlankOrNull(linkParams)) {
                carsup = "&";
            } else if (!GenericValidator.isBlankOrNull(linkParams)) {
                carsup = "?";
            }
        }

        return link + carsup + Util.encode(paramId) + "=" + Util.encode(linkParams);
    }

    /**
     * @return pageContext
     */
    public PageContext getPageContext() {
        return pageContext;
    }

    /**
     * @param context le nouveau pageContext
     */
    public void setPageContext(final PageContext context) {
        pageContext = context;
    }

    /**
     * @return link
     */
    public String getLink() {
        return link;
    }

    /**
     * @return paramName
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * @return paramProperty
     */
    public String getParamProperty() {
        return paramProperty;
    }

    /**
     * @param string le nouveau paramName
     */
    public void setParamName(final String string) {
        paramName = string;
    }

    /**
     * @param string le nouveau paramProperty
     */
    public void setParamProperty(final String string) {
        paramProperty = string;
    }

    /**
     * @param string le nouveau link
     */
    public void setLink(final String string) {
        link = string;
    }

    /**
     * @return width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param string le nouveau width
     */
    public void setWidth(final String string) {
        width = string;
    }

    /**
    * @see com.airfrance.welcom.taglib.table.ICol#getCurrentValue(int, java.lang.Object, int, java.lang.String, java.lang.String)
    */
    public String getCurrentValue(final int position, final Object bean, final int idIndex, final String style, final String styleSelect, final int pageLength) throws JspException{
        return currentValue;
    }

    /**
     * @param string le nouveau currentValue
     */
    public void setCurrentValue(final String string) {
        currentValue = string;
    }

    /**
     * Relache les ressources initialisées
     *
     */
    public void release() {
        property = "";
        key = "";
        sortable = true;
        paramId = "";
        paramName = "";
        paramProperty = "";
        link = "";
        width = "";
        pageContext = null;
        currentValue = "";
        emptyKey = "";
        contentStyle = "";
        contentClass = "";
        headerStyle = "";
        headerClass = "";
        contentNoWrap = false;
        headerNoWrap = true;
    }

    /**
     * @return writeTD
     */
    public boolean isWriteTD() {
        return writeTD;
    }

    /**
     * @param b le nouveau writeTD
     */
    public void setWriteTD(final boolean b) {
        writeTD = b;
    }

    /**
     * @return dateFormat
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param string le nouveau dateFormat
     */
    public void setDateFormat(final String string) {
        dateFormat = string;
    }

    /**
     * @param string le nouveau type
     */
    public void setType(final String string) {
        type = string;
    }

    /**
     * @return emptyKey
     */
    public String getEmptyKey() {
        return emptyKey;
    }

    /**
     * @param string le nouveau EmptyKey
     */
    public void setEmptyKey(final String string) {
        emptyKey = string;
    }
    /**
     * @return cols
     */
    public Cols getCols() {
        return cols;
    }

    /**
     * @param pCols cols
     */
    public void setCols(final Cols pCols) {
        this.cols = pCols;
    }

    /**
     * @return editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param b editable
     */
    public void setEditable(final boolean b) {
        editable = b;
    }

    /**
     * @return headerStyle
     */
    public String getHeaderStyle() {
        return headerStyle;
    }

    /**
     * @param string headerStyle
     */
    public void setHeaderStyle(final String string) {
        headerStyle = string;
    }

    /**
     * @return contentTruncate
     */
    public String getContentTruncate() {
        return contentTruncate;
    }

    /**
     * @return headerTruncate
     */
    public String getHeaderTruncate() {
        return headerTruncate;
    }

    /**
     * @param string contentTruncate
     */
    public void setContentTruncate(final String string) {
        contentTruncate = string;
    }

    /**
     * @param string headerTruncate
     */
    public void setHeaderTruncate(final String string) {
        headerTruncate = string;
    }

    /**
     * @return contentClass
     */
    public String getContentClass() {
        return contentClass;
    }

    /**
     * @return contentStyle
     */
    public String getContentStyle() {
        return contentStyle;
    }

    /**
     * @return currentValue
     */
    public String getCurrentValue(){
        return currentValue;
    }

    /**
     * @return headerClass
     */
    public String getHeaderClass() {
        return headerClass;
    }

    /**
     * @param string contentClass
     */
    public void setContentClass(final String string) {
        contentClass = string;
    }

    /**
     * @param string contentStyle
     */
    public void setContentStyle(final String string) {
        contentStyle = string;
    }

    /**
     * @param string headerClass
     */
    public void setHeaderClass(final String string) {
        headerClass = string;
    }

    /**
     * @return dateFormatKey
     */
    public String getDateFormatKey() {
        return dateFormatKey;
    }

    /**
     * @param string dateFormatKey
     */
    public void setDateFormatKey(final String string) {
        dateFormatKey = string;
    }

    /**
     * @return specialHeader
     */
    public boolean isSpecialHeader() {
        return specialHeader;
    }

    /**
     * @return specialHeaderContent
     */
    public String getSpecialHeaderContent() {
        return specialHeaderContent;
    }

    /**
     * @param b specialHeader
     */
    public void setSpecialHeader(final boolean b) {
        specialHeader = b;
    }

    /**
     * @param string specialHeaderContent
     */
    public void setSpecialHeaderContent(final String string) {
        specialHeaderContent = string;
    }

    /**
     * @return ContentNoWrap
     */
    public boolean isContentNoWrap() {
        return contentNoWrap;
    }

    /**
     * @return HeaderNoWrap
     */
    public boolean isHeaderNoWrap() {
        return headerNoWrap;
    }

    /**
     * @param b ContentNoWrap
     */
    public void setContentNoWrap(final boolean b) {
        contentNoWrap = b;
    }

    /**
     * @param b HeaderNoWrap
     */
    public void setHeaderNoWrap(final boolean b) {
        headerNoWrap = b;
    }

}