package com.airfrance.welcom.taglib.onglet.impl;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.button.ButtonBarTag;
import com.airfrance.welcom.taglib.onglet.IJSOngletRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class JSOngletRendererV2001
    implements IJSOngletRenderer
{

    /**
     * @see com.airfrance.welcom.taglib.onglet.IJSOngletRenderer#drawTableBottom(java.lang.String)
     */
    public String drawTableBottom( String bottomValue )
    {

        final TrimStringBuffer sb = new TrimStringBuffer();

        if ( Util.isTrimNonVide( ButtonBarTag.purgeBodyHidden( bottomValue ) ) )
        {
            // buf.append("<!-- Génération de la barre de boutons -->\n");
            sb.append( "\t<table class=\"wide\">\n" );
            sb.append( "\t\t<tr class=trtete align=\"center\" height=\"25\"> \n" );
            sb.append( "\t\t\t" + bottomValue + "  \n" );
            sb.append( "\t\t</tr>\n" );
            sb.append( "\t</table>\n" );
        }
        else
        {
            sb.append( "\t<table class=\"wide\">\n" );
            sb.append( "\t\t<tr class=trtete align=\"center\"><td height=\"5\"></td>\n" );
            sb.append( "\t\t</tr>\n" );
            sb.append( "\t</table>\n" );
        }

        return sb.toString();

    }

    /**
     * @see com.airfrance.welcom.taglib.onglet.IJSOngletRenderer#drawTitleBar(java.lang.String)
     */
    public String drawTitleBar( String titles )
    {

        final StringBuffer buf = new StringBuffer();

        buf.append( "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" );
        buf.append( "<tr>\n" );

        buf.append( titles );

        buf.append( "</tr>\n" );
        buf.append( "</table>\n" );

        return buf.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.onglet.IJSOngletRenderer#drawBodyStart(java.lang.String, boolean, boolean)
     */
    public String drawBodyStart( String name, boolean ongletSelected, boolean lazyLoading )
    {
        final StringBuffer buf = new StringBuffer();
        if ( ongletSelected )
        {
            buf.append( "<DIV class=\"fondCouleur\" ID=\"" + name + "\" width=\"100%\" STYLE=''" );
        }
        else
        {
            buf.append( "<DIV class=\"fondCouleur\" ID=\"" + name + "\" width=\"100%\" STYLE='display: none'" );
        }

        if ( lazyLoading )
        {
            buf.append( " load=\"true\"" );
        }

        buf.append( ">\n" );
        return buf.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.onglet.IJSOngletRenderer#drawBodyEnd()
     */
    public String drawBodyEnd()
    {
        return "</div>";
    }

    /**
     * @see com.airfrance.welcom.taglib.onglet.IJSOngletRenderer#drawTitle(java.lang.String, java.lang.String,
     *      java.lang.String, int, boolean)
     */
    public String drawTitle( final String name, final String titre, final String parentName, final int indice,
                             boolean ongletSelected, final String onClickAfterShow )
    {
        final StringBuffer buf = new StringBuffer();
        // left
        buf.append( "<td id=\"" + name + "" + indice + "Av\" width=\"10\" class=" );

        if ( ongletSelected )
        {
            buf.append( "'"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ONGLET_STYLE_SELECTIONNER_LEFT ) + "'" );
        }
        else
        {
            buf.append( "'"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ONGLET_STYLE_NON_SELECTIONNER_LEFT ) + "'" );
        }

        buf.append( ">&nbsp;</td>" );

        // center
        buf.append( "<td onclick=\"F_OngletSelectionner( '" + parentName + "', '" + name + "'," + name + "" + indice
            + "Av, this," + name + "" + indice + "Ap )" );
        if ( !GenericValidator.isBlankOrNull( onClickAfterShow ) )
        {
            buf.append( ";" + onClickAfterShow );
        }
        buf.append( "\"" );

        if ( ongletSelected )
        {
            buf.append( " class='"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ONGLET_STYLE_SELECTIONNER_CENTER ) + "'" );
        }
        else
        {
            buf.append( " class='"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ONGLET_STYLE_NON_SELECTIONNER_CENTER )
                + "'" );
        }

        buf.append( " onmouseover=\"setCursor(this);\">" + titre + "</td>" );

        // Right
        buf.append( "<td id=\"" + name + "" + indice + "Ap\" width=\"10\" class=" );

        if ( ongletSelected )
        {
            buf.append( "'"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ONGLET_STYLE_SELECTIONNER_RIGHT ) + "'" );
        }
        else
        {
            buf.append( "'"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ONGLET_STYLE_NON_SELECTIONNER_RIGHT )
                + "'" );
        }

        buf.append( ">&nbsp;</td>" );

        return buf.toString();
    }

}
