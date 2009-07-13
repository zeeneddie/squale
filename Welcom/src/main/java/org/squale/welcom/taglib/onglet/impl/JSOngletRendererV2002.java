/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.welcom.taglib.onglet.impl;

import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.outils.TrimStringBuffer;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.button.ButtonBarTag;
import org.squale.welcom.taglib.onglet.IJSOngletRenderer;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class JSOngletRendererV2002
    implements IJSOngletRenderer
{

    /**
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawTableBottom(java.lang.String)
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
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawTitleBar(java.lang.String)
     */
    public String drawTitleBar( String titles )
    {

        final StringBuffer buf = new StringBuffer();

        buf.append( "<span>" );

        buf.append( titles );

        buf.append( "</span>" );

        return buf.toString();
    }

    /**
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawBodyStart(java.lang.String, boolean, boolean)
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
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawBodyEnd()
     */
    public String drawBodyEnd()
    {
        return "</div>";
    }

    /**
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawTitle(java.lang.String, java.lang.String,
     *      java.lang.String, int, boolean,final String onClickAfterShow)
     */
    public String drawTitle( final String name, final String titre, final String parentName, final int indice,
                             boolean ongletSelected, final String onClickAfterShow )
    {
        final StringBuffer buf = new StringBuffer();
        buf.append( "<a href=\"#\"" );
        buf.append( " onclick=\"F_OngletSelectionner2( '" + parentName + "', '" + name + "', this )" );
        if ( !GenericValidator.isBlankOrNull( onClickAfterShow ) )
        {
            buf.append( ";" + onClickAfterShow );
        }
        buf.append( "\"" );

        if ( ongletSelected )
        {
            buf.append( " class='"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ONGLET_STYLE_SELECTIONNER ) + "'" );
        }
        else
        {
            buf.append( " class='"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ONGLET_STYLE_NON_SELECTIONNER ) + "'" );
        }

        buf.append( " >" + titre + "</a>" );

        return buf.toString();
    }

}
