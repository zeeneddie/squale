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

import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasLeftMenuTagRendererV2001
    implements ICanvasLeftMenuTagRenderer
{

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer#drawStart()
     */
    public String drawStart()
    {
        return "<TABLE class=\"wide\" cellpadding=\"0\" cellspacing=\"0\"><tr>\n";
    }

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer#drawEnd(java.lang.String, int, boolean,
     *      boolean)
     */
    public String drawEnd( String body, int width, boolean containsMenu, boolean containsBouton )
    {
        StringBuffer sb = new StringBuffer();
        sb.append( "<td width=\"" + width + "px\" valign=\"top\">\n" );
        sb.append( "<table id=\"menu\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" );
        sb.append( "<tr><td height=\"21\"></td></tr>" );

        final String pix_grey = WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_PIX_GREY_GIF );

        sb.append( "<tr><td><img src=\"" );
        sb.append( pix_grey );
        sb.append( "\" width=\"140\" height=\"1\" border=\"0\"></td></tr>\n" );
        if ( Util.isTrimNonVide( body ) )
        {
            sb.append( body );
        }
        sb.append( "</table>" );
        sb.append( "</td>" );
        sb.append( "<td width=\"20\"></td>" );
        return sb.toString();
    }

}
