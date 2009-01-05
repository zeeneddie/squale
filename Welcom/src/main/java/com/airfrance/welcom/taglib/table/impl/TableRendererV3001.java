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
package com.airfrance.welcom.taglib.table.impl;

import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.TrimStringBuffer;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.taglib.button.ButtonBarTag;
import com.airfrance.welcom.taglib.table.ITableRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class TableRendererV3001
    implements ITableRenderer
{

    /**
     * @see ITableRenderer#drawTableBottom(String, String)
     */
    public String drawTableBottom( String bottomValue, String width )
    {

        final TrimStringBuffer sb = new TrimStringBuffer();
        sb.append( "<table class=\"formulaire\" width=\"" + width + "\"> \n" );
        sb.append( "<tfoot><tr><td>" );
        if ( Util.isTrimNonVide( ButtonBarTag.purgeBodyHidden( bottomValue ) ) )
        {
            sb.append( bottomValue );
        }
        else
        {
            sb.append( "&nbsp;" );
        }
        sb.append( "</td></tr></tfoot>" );
        // buf.append("<!-- Fin de la Génération de la barre de boutons -->\n");
        sb.append( "</table> \n" );

        return sb.toString();

    }

    /**
     * @see ITableRenderer#drawTableTitle(String)
     */
    public String drawTableTitle( String columnsTitles )
    {
        final TrimStringBuffer sb = new TrimStringBuffer();

        sb.append( "\t<thead>\n" );
        sb.append( "\t<tr>\n" );
        sb.append( columnsTitles );
        sb.append( "</tr>\n" );
        sb.append( "</thead>\n" );

        return sb.toString();
    }

    /**
     * @see ITableRenderer#drawTableStart(String, String)
     */
    public String drawTableStart( String tableName, String width )
    {
        final TrimStringBuffer sb = new TrimStringBuffer();
        sb.append( "<table class=\"tblh\" " );
        if ( !GenericValidator.isBlankOrNull( tableName ) )
        {
            sb.append( "id=\"" + tableName + "\" " );
        }
        sb.append( "width=\"" + width + "\"> \n" );

        return sb.toString();
    }

    /**
     * @see ITableRenderer#drawTableEnd()
     */
    public String drawTableEnd()
    {
        final StringBuffer sb = new StringBuffer();
        sb.append( "</table>" );
        return sb.toString();

    }

    /**
     * @see ITableRenderer#drawNavigation(String, String, String)
     */
    public String drawNavigation( String totalLabel, String navigation, String width )
    {
        TrimStringBuffer sb = new TrimStringBuffer();
        sb.append( "<div class=\"pagination\"> \n" );

        if ( !GenericValidator.isBlankOrNull( totalLabel ) )
        {
            sb.append( "<div style=\"float:left;font-weight: bold;\">" + totalLabel + "</div>" );
        }

        if ( !GenericValidator.isBlankOrNull( navigation ) )
        {
            sb.append( navigation );
        }
        else
        {
            sb.append( "&nbsp;" );
        }
        sb.append( "</div>\n" );
        return sb.toString();
    }

}
