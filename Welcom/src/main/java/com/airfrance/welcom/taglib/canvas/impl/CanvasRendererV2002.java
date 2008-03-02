package com.airfrance.welcom.taglib.canvas.impl;

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.ICanvasRenderer;

/***********************************************************************************************************************
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasRendererV2002
    implements ICanvasRenderer
{

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasRenderer#drawStart(String)
     */
    public String drawStart( String event )
    {
        final StringBuffer sb = new StringBuffer();

        sb.append( "<body " );

        sb.append( event );

        sb.append( " id=\"" );
        sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ID ) );
        sb.append( "\"  class=\"" );
        sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_CLASS ) );
        sb.append( "\">\n" );

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasRenderer#drawFooter(String)
     */
    public String drawFooter( String menuBuffer )
    {
        final StringBuffer sb = new StringBuffer();

        sb.append( "<TR>\n" );
        sb.append( "</TABLE>\n" );

        // ajout du menu light
        sb.append( menuBuffer );

        // Lance les behavirous pour les boutons ...
        sb.append( "<script language=\"javascript\">" );
        sb.append( "if (typeof HTMLArea != \"undefined\") {HTMLArea.init();}" );

        sb.append( "</script>\n" );

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasRenderer#drawEnd()
     */
    public String drawEnd()
    {
        final StringBuffer sb = new StringBuffer();

        sb.append( "</body>\n" );
        sb.append( "</html>\n" );

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasRenderer#drawTraceur(java.lang.String, java.lang.String)
     */
    public String drawTraceur( String menuName, String itemName )
    {

        final StringBuffer sb = new StringBuffer();

        // Incorporation de la lib
        sb.append( "<script type=\"text/javascript\" src=\"jslib/comTraceurAF_v2.js\"></script>" );

        // Ecriture la la tetiere
        sb.append( "<div id=tetiere align=right><script>" );

        sb.append( "comTraceurAF_v2_parMenu(" );
        sb.append( menuName );
        sb.append( "," );
        sb.append( "\"" + itemName + "\");" );
        sb.append( "</script></div>" );

        return sb.toString();
    }
}
