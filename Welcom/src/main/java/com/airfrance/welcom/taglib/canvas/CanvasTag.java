/*
 * Créé le 14 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.canvas;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.struts.bean.WResultAction;
import com.airfrance.welcom.struts.util.WConstants;
import com.airfrance.welcom.taglib.menu.JSMenuBase;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * CanvasTag
 */
public class CanvasTag extends CanvasBodyEventHandler {
    /**
     * 
     */
    private static final long serialVersionUID = -3017335470744229202L;

    /** logger */
    private static Log log = LogFactory.getLog(CanvasTag.class);

    /** parametre du tag */
    private String navigationMenuKey = "";
    /** parametre du tag */
    private String navigationMenuName = JSMenuBase.DEFAULT_NAME;
    /** parametre du tag */
    private String canvasHeaderPageInclude = WelcomConfigurator.getMessage(WelcomConfigurator.BODY_CANVAS_HEADER_PAGE_INCLUDE);
    /** parametre du tag */
    private String canvasLeftPageInclude = WelcomConfigurator.getMessage(WelcomConfigurator.BODY_CANVAS_LEFT_PAGE_INCLUDE);
    /** clef du body*/
    public static final String CANVASTAG_KEY = "com.airfrance.welcom.tag.body";
    /** bufferization du menu */
    private String bufMenu = "";
    /** render */
    private ICanvasRenderer render = (ICanvasRenderer) RendererFactory.getRenderer(RendererFactory.CANVAS);

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        // Ajoute la fenetre d'erreur de popup
        onload = addMessagePopupOnOnLoad(pageContext, onload);

        // Se memorise dans la page
        pageContext.getRequest().setAttribute(CANVASTAG_KEY, this);

        addHeader();

        final StringBuffer sb = new StringBuffer();

        sb.append(render.drawStart(prepareEventHandlers()));

        // Si la navigation n'est pas vide
        if (!GenericValidator.isBlankOrNull(navigationMenuKey) && WelcomConfigurator.getCharte().isV2()) {
            sb.append(doTraceur() + "\n");
        }

        ResponseUtils.write(pageContext, sb.toString());
        doCanvasHeader();
        doCanvasLeft();
        return EVAL_PAGE;
    }

    /**
     * surcharge du on load
     * @param pageContext contexte
     * @param oldOnLoad ancien onload su body
     * @return le onload formatte
     */
    public static String addMessagePopupOnOnLoad(final PageContext pageContext, final String oldOnLoad) {
        // on gere un 2eme stringbuffer au cas ou on aurait pas besoin de onload
        final StringBuffer sbOnload = new StringBuffer();

        final Object logonBean = (pageContext.getSession().getAttribute(WConstants.USER_KEY));
        final String msg = WResultAction.readMessage(pageContext.getRequest());
        if (!GenericValidator.isBlankOrNull(msg)) {

            sbOnload.append("javascript:alert(\'" + Util.formatJavaScript(msg) + "\');");

            // message afficher, suppression du message
            WResultAction.resetMessage(pageContext.getRequest());
        }

        if (!GenericValidator.isBlankOrNull(oldOnLoad)) {
            sbOnload.append(ResponseUtils.filter(oldOnLoad) + ";");
        }

        return sbOnload.toString();

    }

    /**
     * Ajoute le header si non spécifie
     * @throws JspException exception pouvant etre levee
     */
    private void addHeader() throws JspException {

        if (pageContext.getRequest().getAttribute(HeadTag.KEY_TAG) == null) {
            final HeadTag tag = new HeadTag();
            tag.setPageContext(pageContext);
            tag.setParent(this);
            tag.doStartTag();
            tag.doEndTag();
        }
    }

    /**
     * appelle le canvasHeader
     * @throws JspException exception pouvant etre levee
     */
    private void doCanvasHeader() throws JspException {
        // Si un menuPageInclude est spécifie alors  il le mets
        if (!GenericValidator.isBlankOrNull(canvasHeaderPageInclude) && !Util.isEqualsIgnoreCase(canvasHeaderPageInclude, "none")) {

            final String path = pageContext.getServletContext().getRealPath(getAbsolutePathRelativeToContext(canvasHeaderPageInclude));
            final File f = new File(path);
            if (f.exists()) {
                try {
                    pageContext.include(canvasHeaderPageInclude);
                } catch (final Exception e) {
                    log.error(e, e);
                    throw new JspException(e);
                }
            } else {
                throw new JspException("Impossible d'inclure le fichier : " + path);
            }
        }
    }

    /**
     * Recupere le chemin complete en fonction de la servlet
     * @param s : fichier a recherche
     * @return : chain complet ....
     */
    private final String getAbsolutePathRelativeToContext(final String s) {
        String s1 = s;
        if (!s1.startsWith("/")) {
            String s2 = (String) pageContext.getRequest().getAttribute("javax.servlet.include.servlet_path");
            if (s2 == null) {
                s2 = ((HttpServletRequest) pageContext.getRequest()).getServletPath();
            }
            final String s3 = s2.substring(0, s2.lastIndexOf('/'));
            s1 = s3 + '/' + s1;
        }
        return s1;
    }

    /**
     * appelle le canvasLeft
     * @throws JspException exception pouvant etre levee
     */
    private void doCanvasLeft() throws JspException {
        // Si un menuPageInclude est spécifie alors il le mets
        if (!GenericValidator.isBlankOrNull(canvasLeftPageInclude) && !Util.isEqualsIgnoreCase(canvasLeftPageInclude, "none")) {
            try {
                pageContext.include(canvasLeftPageInclude);
            } catch (final Exception e) {
                log.error(e, e);
                throw new JspException(e.getMessage());
            }
        }
    }

    /**
     * gere le traceur
     * @return le html genere
     * @throws JspException exception pouvant etre levee
     */
    private String doTraceur() throws JspException {
        String navigationMenuLabel = RequestUtils.message(super.pageContext, "org.apache.struts.action.MESSAGE", "org.apache.struts.action.LOCALE", navigationMenuKey);

        if (GenericValidator.isBlankOrNull(navigationMenuLabel)) {
            navigationMenuLabel = navigationMenuKey;
        }

        return render.drawTraceur(navigationMenuName, navigationMenuLabel);
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {

        ResponseUtils.write(pageContext, render.drawFooter(bufMenu));

        // Si la navigation n'est pas vide
        if (!GenericValidator.isBlankOrNull(navigationMenuKey) && WelcomConfigurator.getCharte().isV3()) {
            ResponseUtils.write(pageContext, doTraceur() + "\n");
        }

        ResponseUtils.write(pageContext, render.drawEnd());

        release();
        return EVAL_PAGE;
    }

    /**
     * Accesseur
     * @return navigationMenuKey
     */
    public String getNavigationMenuKey() {
        return navigationMenuKey;
    }

    /**
     * Accesseur
     * @param string le navigationMenuKey
     */
    public void setNavigationMenuKey(final String string) {
        navigationMenuKey = string;
    }

    /**
     * Accesseur
     * @return navigationMenuName
     */
    public String getNavigationMenuName() {
        return navigationMenuName;
    }

    /**
     * Accesseur
     * @param string le navigationMenuName
     */
    public void setNavigationMenuName(final String string) {
        navigationMenuName = string;
    }

    /**
     * Accesseur
     * @return le canvasHeaderPageInclude
     */
    public String getCanvasHeaderPageInclude() {
        return canvasHeaderPageInclude;
    }

    /**
     * Accesseur
     * @return le canvasLeftPageInclude
     */
    public String getCanvasLeftPageInclude() {
        return canvasLeftPageInclude;
    }

    /**
     * Accesseur
     * @param string le canvasHeaderPageInclude
     */
    public void setCanvasHeaderPageInclude(final String string) {
        canvasHeaderPageInclude = string;
    }

    /**
     * Accesseur
     * @param string le canvasLeftPageInclude
     */
    public void setCanvasLeftPageInclude(final String string) {
        canvasLeftPageInclude = string;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    public void release() {
        super.release();
        navigationMenuKey = "";
        navigationMenuName = JSMenuBase.DEFAULT_NAME;
        canvasHeaderPageInclude = WelcomConfigurator.getMessage(WelcomConfigurator.BODY_CANVAS_HEADER_PAGE_INCLUDE);
        canvasLeftPageInclude = WelcomConfigurator.getMessage(WelcomConfigurator.BODY_CANVAS_LEFT_PAGE_INCLUDE);
        onload = "";
        onscroll = "";
        onclick = "";
        bufMenu = "";
    }

    /**
     * @return accesseur
     */
    public String getBufMenu() {
        return bufMenu;
    }

    /**
     * @param string accesseur
     */
    public void setBufMenu(final String string) {
        bufMenu = string;
    }

    /**
     * @param string ajoute le menu derriere
     */
    public void pushBufMenu(final String string) {
        if (WelcomConfigurator.getCharte().isV2()) {
            bufMenu = string + bufMenu;
        } else {
            bufMenu = bufMenu + string;
        }
    }

}