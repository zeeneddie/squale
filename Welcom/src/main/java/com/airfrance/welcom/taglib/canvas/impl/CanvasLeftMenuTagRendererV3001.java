package com.airfrance.welcom.taglib.canvas.impl;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer;

/**
 * 
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasLeftMenuTagRendererV3001 implements ICanvasLeftMenuTagRenderer {

    /**
    * @see com.airfrance.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer#drawStart()
    */
    public String drawStart() {

        return null;
    }

    /**
      * @see com.airfrance.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer#drawEnd(java.lang.String, int, boolean, boolean)
     */
    public String drawEnd(String body, int width, boolean containsMenu, boolean containsBouton) {
        StringBuffer sb = new StringBuffer();
        sb.append("<div id=\"conteneur\">\n");
        sb.append("<div id=\"traceur\"></div>");
        sb.append("<div id=\"gauche\">\n");
        if (containsBouton) {
            sb.append("<div class=\"menu_action\">");
        }
        if (Util.isTrimNonVide(body)) {
            sb.append(body);
        }
        if (containsBouton) {
            sb.append("</div>");
        }

        sb.append("</div>");

        return sb.toString();
    }

}
