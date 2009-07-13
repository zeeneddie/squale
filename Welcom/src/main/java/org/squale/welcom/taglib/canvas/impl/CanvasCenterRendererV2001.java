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
package org.squale.welcom.taglib.canvas.impl;

import org.apache.commons.validator.GenericValidator;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.canvas.ICanvasCenterRenderer;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasCenterRendererV2001
    implements ICanvasCenterRenderer
{

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasCenterRenderer#drawStart(boolean)
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
     * @see org.squale.welcom.taglib.canvas.ICanvasCenterRenderer#drawTitre(java.lang.String, java.lang.String,
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
     * @see org.squale.welcom.taglib.canvas.ICanvasCenterRenderer#drawEnd()
     */
    public String drawEnd()
    {
        return ( "</td>\n" );
    }

}
