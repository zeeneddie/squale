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

import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.taglib.canvas.ICanvasRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasRendererV3001
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
        sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_ID ) );
        sb.append( "\"  class=\"" );
        sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_CLASS ) );
        sb.append( "\">\n" );

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
     * @see com.airfrance.welcom.taglib.canvas.ICanvasRenderer#drawFooter(String)
     */
    public String drawFooter( String menuBuffer )
    {
        final StringBuffer sb = new StringBuffer();

        // ajout du menu light
        sb.append( menuBuffer );

        // Lance les behavirous pour les boutons ...
        sb.append( "</div>" ); // fermture du div "conteneur"
        sb.append( "<br><br><div id=\"footer\" class=\"bg_theme\"></div>" );
        sb.append( "<script language=\"javascript\">" );
        sb.append( "if (typeof HTMLArea != \"undefined\") {HTMLArea.init();}" );

        sb.append( "</script>\n" );

        return sb.toString();
    }

    /**
     * @see com.airfrance.welcom.taglib.canvas.ICanvasRenderer#drawTraceur(java.lang.String, java.lang.String)
     */
    public String drawTraceur( String menuName, String itemName )
    {

        final StringBuffer sb = new StringBuffer();

        // Incorporation de la lib
        sb.append( "<script type=\"text/javascript\" src=\""
            + WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV3_TRACEUR_JSURL ) + "\"></script>" );

        // Ecriture la la tetiere
        sb.append( "<script type=\"text/javascript\">" );

        sb.append( "comTraceurAF_Vx_parMenuActionPageCourante(" );
        sb.append( menuName );
        sb.append( "," );
        sb.append( "\"" + itemName + "\");" );
        sb.append( "</script>" );

        return sb.toString();
    }
}
