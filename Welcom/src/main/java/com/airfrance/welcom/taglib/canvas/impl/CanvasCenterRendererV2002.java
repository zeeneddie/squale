package com.airfrance.welcom.taglib.canvas.impl;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasCenterRendererV2002
    implements ICanvasCenterRenderer
{

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer#drawStart(boolean)
     */
    public String drawStart( boolean hasCanvasLeft )
    {

        return ( "<div id=\"contenu\">\n" );
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer#drawTitre(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public String drawTitre( String titre, String subTitleKey, String soustitre )
    {
        final StringBuffer sb = new StringBuffer();

        sb.append( "<p class=\"titreGeneral\">" );
        sb.append( titre );
        sb.append( "</p>" );

        // ligne grise
        sb.append( "<div class=\"separateurParagraphe\"></div>" );
        if ( !GenericValidator.isBlankOrNull( subTitleKey ) )
        {
            sb.append( "<p class=\"titreParagraphe \">" );
            sb.append( soustitre );
            sb.append( "</p>" );
        }
        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer#drawEnd()
     */
    public String drawEnd()
    {
        return ( "</div>\n" );
    }
}
