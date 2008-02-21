package com.airfrance.welcom.taglib.canvas.impl;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasPopupRendererV3001 implements ICanvasPopupRenderer {

    /** (non-Javadoc)
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawStartHead(java.lang.String)
     */
    public String drawStartHead(String titre) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<HTML>");
        sb.append("<HEAD>");
        if (!GenericValidator.isBlankOrNull(titre)) {
            sb.append("<TITLE>");
            sb.append(titre);
            sb.append("</TITLE>");
        }
        return sb.toString();
    }

    /** (non-Javadoc)
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawEndHead()
     */
    public String drawEndHead() {
        return "</head>";
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawStart(String, String)
     */
    public String drawStartBody(String event, String titreBar) {

        final StringBuffer sb = new StringBuffer();
        sb.append("<BODY id=\"popup\" ");
        sb.append("class=\"");
        sb.append(WelcomConfigurator.getMessage(WelcomConfigurator.CHARTEV3_CLASS));
        sb.append("\" ");

        sb.append(event);

        sb.append(">");
        sb.append("<div id=\"header\"></div>");
        sb.append("<div id=\"lignage\"></div>");
        sb.append("<div id=\"contenu\">");

        sb.append(titreBar);

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawTitre(java.lang.String)
     */
    public String drawTitre(String titre) {
        final StringBuffer sb = new StringBuffer();
        sb.append("<h1>" + titre + "</h1>");

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawEnd()
     */
    public String drawEndBody(String closeLabel) {
        final StringBuffer sb = new StringBuffer();

        sb.append("</div>");
        sb.append("<div id=\"footer\"></div>");
        sb.append("</BODY>");
        sb.append("</HTML>");
        return sb.toString();

    }

}
