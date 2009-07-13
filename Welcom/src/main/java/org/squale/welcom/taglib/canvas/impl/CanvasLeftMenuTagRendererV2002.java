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
import org.squale.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasLeftMenuTagRendererV2002
    implements ICanvasLeftMenuTagRenderer
{

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer#drawStart()
     */
    public String drawStart()
    {

        return null;
    }

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasLeftMenuTagRenderer#drawEnd(java.lang.String, int, boolean,
     *      boolean)
     */
    public String drawEnd( String body, int width, boolean containsMenu, boolean containsBouton )
    {
        StringBuffer sb = new StringBuffer();
        if ( !containsMenu )
        {
            sb.append( "<div id=\"navigationS\">\n" );
        }
        if ( containsBouton )
        {
            sb.append( "<div class=\"menuAction\">" );
        }
        if ( Util.isTrimNonVide( body ) )
        {
            sb.append( body );
        }
        if ( containsBouton )
        {
            sb.append( "</div>" );
        }

        if ( !containsMenu )
        {
            sb.append( "</div>" );
        }
        return sb.toString();
    }
}
