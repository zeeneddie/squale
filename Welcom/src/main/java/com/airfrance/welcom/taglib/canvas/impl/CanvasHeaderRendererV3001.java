package com.airfrance.welcom.taglib.canvas.impl;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.ICanvasHeaderRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasHeaderRendererV3001
    implements ICanvasHeaderRenderer
{

    /**
     * Initilise le header
     * 
     * @see com.airfrance.welcom.taglib.canvas.ICanvasHeaderRenderer#drawHeader(java.lang.String)
     */
    public StringBuffer drawHeader( String headerImageURL )
    {
        StringBuffer sb = new StringBuffer();

        sb.append( "<a name=\"top\"></a>" );

        sb.append( "<div id=\"header\" class=\""
            + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_HEADER_CIEL ) + "\">" );
        sb.append( "<hr class=\"bg_theme\">" );
        sb.append( "<div id=\"visuel\" style=\"background-image:url(" + headerImageURL + ")\">" );

        // sb.append("<p>La charte graphique v03.001 </p>");
        sb.append( "</div>" );
        sb.append( "</div>" );
        sb.append( "<div id=\"navigation\" class=\"bg_theme\"></div>" );

        return sb;
    }

}
