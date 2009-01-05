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
package com.airfrance.welcom.taglib.canvas.impl;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasCenterRendererV3001
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
            sb.append( "<div id=\"conteneur\">\n" );
            sb.append( "<div id=\"traceur\"></div>" );
        }
        sb.append( "<div id=\"contenu\">\n" );
        return sb.toString();

    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer#drawTitre(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    public String drawTitre( String titre, String subTitleKey, String soustitre )
    {
        final StringBuffer sb = new StringBuffer();

        if ( !GenericValidator.isBlankOrNull( titre ) )
        {
            sb.append( "<h1>" );
            sb.append( titre );
            sb.append( "</h1>" );
        }

        if ( !GenericValidator.isBlankOrNull( subTitleKey ) )
        {
            sb.append( "<h3>" );
            sb.append( soustitre );
            sb.append( "</h3>" );
        }
        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasCenterRenderer#drawEnd()
     */
    public String drawEnd()
    {
        return ( "</div>\n" );
    }
}
