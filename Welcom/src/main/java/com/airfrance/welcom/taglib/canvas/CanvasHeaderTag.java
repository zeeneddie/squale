/*
 * Créé le 25 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.canvas;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.ResponseUtils;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.renderer.RendererFactory;

/**
 * CanvasHeaderTag
 */
public class CanvasHeaderTag extends TagSupport {
    /**
     * 
     */
    private static final long serialVersionUID = -3576518524264665546L;
    /** parametre du tag */
    private String headerImageURL = "";
    /** renderer */
    private static ICanvasHeaderRenderer render = (ICanvasHeaderRenderer) RendererFactory.getRenderer(RendererFactory.CANVAS_HEADER);

    /**
       * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
       */
    public int doStartTag() throws JspException {

        if (GenericValidator.isBlankOrNull(headerImageURL)) {
            headerImageURL = WelcomConfigurator.getMessage(WelcomConfigurator.HEADER_IMAGE_KEY);
        }

        ResponseUtils.write(pageContext, render.drawHeader(headerImageURL).toString());

        return EVAL_PAGE;
    }

    /**
     * Accesseur
     * @return le headerImageURL
     */
    public String getHeaderImageURL() {
        return headerImageURL;
    }

    /**
     * Accesseur le headerImageURL
     * @param string le headerImageURL
     */
    public void setHeaderImageURL(final String string) {
        headerImageURL = string;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    public void release() {
        super.release();
        headerImageURL = "";
    }

}