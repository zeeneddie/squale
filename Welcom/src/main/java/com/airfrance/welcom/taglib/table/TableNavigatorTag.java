package com.airfrance.welcom.taglib.table;

import java.util.Collection;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.html.FormTag;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * Generates an image representing current sorting order
 * over some field and link to change this mode.
 *
 * @author     Yuriy Zubarev
 * @version    1.0
 */
public class TableNavigatorTag extends TagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = 1645916293761797206L;
    /** Constante*/
    public static final int PAGES_PER_NAVIGATIONBAR = 10;
    /** parametre du tag*/
    private String name;
    /** parametre du tag*/
    private HttpServletResponse response;
    /** parametre du tag*/
    private HttpServletRequest request;
    /** parametre du tag*/
    private String baseURL;
    /** parametre du tag*/
    private int from;
    /** parametre du tag*/
    private int volume;
    /** parametre du tag*/
    private int length;
    /** parametre du tag*/
    private String localeKey;
    /** parametre du tag*/
    private String bundle;
    /** parametre du tag*/
    private String pagesPerNavBar = null;
    /** parametre du tag*/
    private HTMLTable table;
    /** parametre du tag*/
    private String txtPrev = "";
    /** parametre du tag*/
    private String txtNext = "";
    /** parametre du tag*/
    private String formName = null;
    /** parametre du tag*/
    private String callBackUrl = "";
    /** parametre du tag*/
    private int ipagesPerNavBar = PAGES_PER_NAVIGATIONBAR; // defaultValue
    /** render */
    private static ITableNavigatorRenderer render = (ITableNavigatorRenderer) RendererFactory.getRenderer(RendererFactory.TABLE_NAVIGATOR);

    /** 
     * Constructeur
     */
    public TableNavigatorTag() {
        name = null;
        localeKey = Globals.LOCALE_KEY;
        bundle = Globals.MESSAGES_KEY;
        table = null;
    }

    /**
     *Sets the name attribute of the TableNavigatorTag object
     *
     * @param  pPagesPerNavBar  The new name value
     */
    public void setPagesPerNavBar(final String pPagesPerNavBar) {
        pagesPerNavBar = pPagesPerNavBar;
    }

    /**
     * 
     * @return le formName
     */
    protected String getFormName() {

        final FormTag formTag = (FormTag) pageContext.getRequest().getAttribute("org.apache.struts.taglib.html.FORM");

        if (formTag != null) {
            return formTag.getFormName();
        }

        return null;
    }

    /**
     * 
     * @return chaine URL
     */
    protected String getServletName() {
        if (!GenericValidator.isBlankOrNull(callBackUrl)) {
            if (callBackUrl.indexOf('?') > 0) {
                return callBackUrl + "&action=navigate&";
            } else {
                return callBackUrl + "?action=navigate&";
            }
        } else {
            return Util.SERVEPATH + "?";
        }
    }

    /**
     *Gets the name attribute of the TableNavigatorTag object
     *
     * @return    The name value
     */
    public String getPagesPerNavBar() {
        return (this.pagesPerNavBar);
    }

    /**
     * Gere le lien pour un id donnée
     * @return : liens
     */
    protected String getStartHref() {
        String frwd = null;

        if (!GenericValidator.isBlankOrNull(table.getMapping())) {
            frwd = "wforward=" + Util.encode(table.getMapping());
        } else {
            frwd = "requestURI=" + Util.encode(request.getRequestURI());
        }

        final String result = baseURL + "/" + getServletName() + frwd + "&";

        return response.encodeURL(result);
    }

    /**
     * Desine la barre de navigation
     * @param pFrom : ID de depart
     * @param pVolume : Taille de la collection
     * @param pLength : Nombre par page
     * @return le flux
     */
    protected StringBuffer drawBar(final int pFrom, final int pVolume, final int pLength) {
        final StringBuffer sb = new StringBuffer();

        // Recupere la locale de la page
        final Locale localeRequest = (Locale) request.getSession().getAttribute(localeKey);

        // Recuperer le fichier des Bundle
        final MessageResources resources = (MessageResources) pageContext.getServletContext().getAttribute(bundle);

        sb.append(render.drawBar(resources, localeRequest, formName, getStartHref(), pFrom, pVolume, pLength, ipagesPerNavBar, getName()));

        return sb;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        final StringBuffer results = new StringBuffer();

        results.append(drawStart());
        ResponseUtils.write(pageContext, results.toString());

        return 0;
    }

    /**
     * Desine la bare, et initialise les params
     * @return : le flux
     * @throws JspException : pd a la creation
     */
    public String drawStart() throws JspException {
        init();

        return drawBar(from, volume, length).toString();
    }

    /**
     * 
     * @return Ecriture a la fin de la barre
     * @throws JspException : exception
     */
    public String drawEnd() throws JspException {
        return "";
    }

    /**
     * Initialise les proprietes
     * @throws JspException : Exception
     */
    protected void init() throws JspException {
        response = (HttpServletResponse) pageContext.getResponse();
        request = (HttpServletRequest) pageContext.getRequest();
        baseURL = ((HttpServletRequest) pageContext.getRequest()).getContextPath();

        if (table == null) {
            final Object bean = RequestUtils.lookup(pageContext, name, null);

            if (!(bean instanceof Collection)) {
                throw new JspException("Bean should be type of HTMLTable");
            }

            table = (HTMLTable) bean;
        }

        if (table == null) {
            throw new JspException("Table not set for pagination");
        }

        from = table.getFrom();
        volume = table.getVolume();
        length = table.getLength();

        if (pagesPerNavBar != null) {
            ipagesPerNavBar = Integer.parseInt(pagesPerNavBar);
        }

        if (WelcomConfigurator.getCharte().isV2()) {
            // Recalcule la taille
            if (((volume / length) <= PAGES_PER_NAVIGATIONBAR) && GenericValidator.isBlankOrNull(pagesPerNavBar)) {
                ipagesPerNavBar = 1;
            }
            if (ipagesPerNavBar != 1) {
                txtPrev += " (" + ipagesPerNavBar + ")";
                txtNext += " (" + ipagesPerNavBar + ")";
            }
        }

        // Recupere le formBean
        formName = getFormName();

    }

    /**
     * Release any acquired resources.
     */
    public void release() {
        super.release();
        name = null;
        baseURL = null;
        response = null;
        from = 0;
        volume = 0;
        length = 0;
        pagesPerNavBar = null;
        formName = null;
    }

    /**
     * @return accesseur
     */
    public int getLength() {
        return length;
    }

    /**
     * @return accesseur
     */
    public String getLocaleKey() {
        return localeKey;
    }

    /**
     * @return accesseur
     */
    public String getName() {
        return name;
    }

    /**
     * @return accesseur
     */
    public int getVolume() {
        return volume;
    }

    /**
     * @param i accesseur
     */
    public void setLength(final int i) {
        length = i;
    }

    /**
     * @param string accesseur
     */
    public void setLocaleKey(final String string) {
        localeKey = string;
    }

    /**
     * @param string accesseur
     */
    public void setName(final String string) {
        name = string;
    }

    /**
     * @param i accesseur
     */
    public void setVolume(final int i) {
        volume = i;
    }

    /**
     * @return accesseur
     */
    public HTMLTable getTable() {
        return table;
    }

    /**
     * @param pTable accesseur
     */
    public void setTable(final HTMLTable pTable) {
        table = pTable;
    }

    /**
     * @return accesseur
     */
    public String getCallBackUrl() {
        return callBackUrl;
    }

    /**
     * @param string accesseur
     */
    public void setCallBackUrl(final String string) {
        callBackUrl = string;
    }
}