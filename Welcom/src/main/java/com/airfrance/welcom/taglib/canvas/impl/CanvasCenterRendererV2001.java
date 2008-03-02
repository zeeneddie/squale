package com.airfrance.welcom.taglib.canvas.impl;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasCenterRendererV2001
    implements ICanvasCenterRenderer
{

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer#drawStart(boolean)
     */
    public String drawStart( boolean hasCanvasLeft )
    {

        final StringBuffer sb = new StringBuffer();
        if ( !hasCanvasLeft )
        {
            sb.append( "<TABLE class=\"wide\" cellpadding=\"0\" cellspacing=\"0\"><tr>\n" );
        }
        sb.append( "<td class=\"formtd\">\n" );
        // Initialise le formulaire
        sb.append( "<table class=\"wide\" cellpadding=\"0\" cellspacing=\"0\">\n" );

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer#drawTitre(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public String drawTitre( String titre, String subTitleKey, String soustitre )
    {
        final StringBuffer sb = new StringBuffer();

        // barred grise
        final String pix_grey = WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_PIX_GREY_GIF );

        sb.append( "<tr>" );
        sb.append( "<td class=\"blueAFtitre\">" );
        sb.append( titre );
        sb.append( "</td></tr>" );

        // ligne grise
        sb.append( "<tr>" );
        sb.append( "<td>" );
        sb.append( "<img src=\"" );
        sb.append( pix_grey );
        sb.append( "\" width=\"100%\" height=\"1\" border=\"0\"></td>" );
        sb.append( "</tr>" );
        if ( !GenericValidator.isBlankOrNull( subTitleKey ) )
        {
            sb.append( "<tr>" );
            sb.append( "<td class=\"blueAFsoustitre\">" );
            sb.append( soustitre );
            sb.append( "</td></tr>" );
        }
        sb.append( "</table>" );
        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer#drawEnd()
     */
    public String drawEnd()
    {
        return ( "</td>\n" );
    }

}
