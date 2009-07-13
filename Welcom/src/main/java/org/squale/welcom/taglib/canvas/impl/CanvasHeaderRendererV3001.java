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

import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.canvas.ICanvasHeaderRenderer;

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
     * @see org.squale.welcom.taglib.canvas.ICanvasHeaderRenderer#drawHeader(java.lang.String)
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
