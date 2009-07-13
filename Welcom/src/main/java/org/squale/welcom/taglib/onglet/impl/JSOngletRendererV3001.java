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
public class JSOngletRendererV3001
    implements IJSOngletRenderer
{

    /**
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawTableBottom(java.lang.String)
     */
    public String drawTableBottom( String bottomValue )
    {

        final TrimStringBuffer sb = new TrimStringBuffer();
        sb.append( "<table class=\"formulaire 100pc\"> \n" );
        sb.append( "<tfoot><tr><td>" );
        if ( Util.isTrimNonVide( ButtonBarTag.purgeBodyHidden( bottomValue ) ) )
        {
            sb.append( bottomValue );
        }
        sb.append( "</td></tr></tfoot>" );
        // buf.append("<!-- Fin de la Génération de la barre de boutons -->\n");
        sb.append( "</table> \n" );

        return sb.toString();

    }

    /**
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawTitleBar(java.lang.String)
     */
    public String drawTitleBar( String titles )
    {

        final StringBuffer buf = new StringBuffer();

        buf.append( "<div class=\"onglet\">" );

        buf.append( titles );

        buf.append( "</div>" );

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
            buf.append( "<div class=\"onglet_contenu\" ID=\"" + name + "\" STYLE=''" );
        }
        else
        {
            buf.append( "<div class=\"onglet_contenu\" ID=\"" + name + "\" STYLE='display: none'" );
        }

        if ( lazyLoading )
        {
            buf.append( " load=\"true\"" );
        }

        buf.append( ">\n" );
        // width:auto !important; width:100%;
        buf.append( "<table style=\"width:100%\"><tr><td>" ); // resoud pb de style ie

        return buf.toString();
    }

    /**
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawBodyEnd()
     */
    public String drawBodyEnd()
    {
        final StringBuffer buf = new StringBuffer();
        buf.append( "</td></tr></table>" ); // resoud pb de style ie
        buf.append( "</div>" );
        return buf.toString();
    }

    /**
     * @see org.squale.welcom.taglib.onglet.IJSOngletRenderer#drawTitle(java.lang.String, java.lang.String,
     *      java.lang.String, int, boolean)
     */
    public String drawTitle( final String name, final String titre, final String parentName, final int indice,
                             final boolean ongletSelected, final String onClickAfterShow )
    {
        final StringBuffer buf = new StringBuffer();
        buf.append( "<a href=\"#\"" );
        buf.append( " onclick=\"F_OngletSelectionner3( '" + parentName + "', '" + name + "', this )" );
        if ( !GenericValidator.isBlankOrNull( onClickAfterShow ) )
        {
            buf.append( ";" + onClickAfterShow );
        }
        buf.append( "\"" );
        if ( ongletSelected )
        {
            buf.append( " class='"
                + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_ONGLET_STYLE_SELECTIONNER ) + "'" );
        }

        buf.append( " >" + titre + "</a>" );

        return buf.toString();
    }
}
