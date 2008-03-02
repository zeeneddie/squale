package com.airfrance.welcom.taglib.canvas.impl;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasPopupRendererV2001
    implements ICanvasPopupRenderer
{

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawStartHead(java.lang.String)
     */
    public String drawStartHead( String titre )
    {
        final StringBuffer sb = new StringBuffer();
        sb.append( "<HTML>" );
        sb.append( "<HEAD>" );
        if ( !GenericValidator.isBlankOrNull( titre ) )
        {
            sb.append( "<TITLE>" );
            sb.append( titre );
            sb.append( "</TITLE>" );
        }
        return sb.toString();
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawEndHead()
     */
    public String drawEndHead()
    {
        return "</head>";
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawStartBody(String, String)
     */
    public String drawStartBody( String event, String titreBar )
    {

        final StringBuffer sb = new StringBuffer();
        sb.append( "<BODY id=\"popup\" " );

        sb.append( event );

        sb.append( ">" );

        sb.append( "<div><img src=\"" + WelcomConfigurator.getMessage( WelcomConfigurator.POPUP_IMG_BANDEAU )
            + "\"></div>" );

        sb.append( titreBar );

        sb.append( "<div style=\"padding-left=20px;padding-right=20px\">" );

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawTitre(java.lang.String)
     */
    public String drawTitre( String titre )
    {

        final StringBuffer sb = new StringBuffer();
        sb.append( "<div class=\"blueAFtitre\" style=\"padding-left=20px;\">" + titre + "</div>" );
        sb.append( "<div class= \"separateurParagraphe\" style=\"margin-left=20px;\"></div>" );

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasPopupRenderer#drawEndBody()
     */
    public String drawEndBody( String closeLabel )
    {
        final StringBuffer sb = new StringBuffer();

        sb.append( "</div>" );
        sb.append( "<div id=\"barrePopup\" style=\"padding-top: 2px;padding-right: 5px;\">" + closeLabel
            + "<a href=\"#\"><img align=\"absmiddle\" src=\""
            + WelcomConfigurator.getMessage( WelcomConfigurator.POPUP_IMG_ICON_CLOSE )
            + "\" border=\"0\" onclick=\"javascript:window.close()\"></a></div>" );
        sb.append( "</BODY>" );
        sb.append( "</HTML>" );

        return sb.toString();

    }

}
