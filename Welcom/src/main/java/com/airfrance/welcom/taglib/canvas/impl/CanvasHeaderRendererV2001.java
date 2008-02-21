package com.airfrance.welcom.taglib.canvas.impl;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.ICanvasHeaderRenderer;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasHeaderRendererV2001 implements ICanvasHeaderRenderer {

    /** Initilise le header
     * @see com.airfrance.welcom.taglib.canvas.ICanvasHeaderRenderer#drawHeader(java.lang.String)
     */
    public StringBuffer drawHeader(String headerImageURL) {
        StringBuffer sb = new StringBuffer();
        sb.append("<a name=\"top\"></a>");
        sb.append("<TABLE width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        sb.append("<TR>");
        sb.append("<TD align=\"left\"><IMG src=\"" + headerImageURL + "\"></TD>");
        sb.append("<TD align=\"right\"><IMG src=\"" + WelcomConfigurator.getMessage(WelcomConfigurator.HEADER_LOGOAF_KEY) + "\"></TD>");
        sb.append(" </TR>");
        sb.append("</TABLE>");
        sb.append("<TABLE width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        sb.append(" <TR><TD bgcolor=\"#CDD2D5\" height=15>");
        sb.append("<IMG src=\"" + WelcomConfigurator.getMessage(WelcomConfigurator.HEADER_BAYADERE_KEY) + "\" height=\"16\" border=\"0\"></TD></TR>");
        sb.append("</TABLE>");
        return sb;
    }

}
