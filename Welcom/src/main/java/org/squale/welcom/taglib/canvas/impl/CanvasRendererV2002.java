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
import org.squale.welcom.taglib.canvas.ICanvasRenderer;

/***********************************************************************************************************************
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class CanvasRendererV2002
    implements ICanvasRenderer
{

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasRenderer#drawStart(String)
     */
    public String drawStart( String event )
    {
        final StringBuffer sb = new StringBuffer();

        sb.append( "<body " );

        sb.append( event );

        sb.append( " id=\"" );
        sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_ID ) );
        sb.append( "\"  class=\"" );
        sb.append( WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_CLASS ) );
        sb.append( "\">\n" );

        return sb.toString();
    }

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasRenderer#drawFooter(String)
     */
    public String drawFooter( String menuBuffer )
    {
        final StringBuffer sb = new StringBuffer();

        sb.append( "<TR>\n" );
        sb.append( "</TABLE>\n" );

        // ajout du menu light
        sb.append( menuBuffer );

        // Lance les behavirous pour les boutons ...
        sb.append( "<script language=\"javascript\">" );
        sb.append( "if (typeof HTMLArea != \"undefined\") {HTMLArea.init();}" );

        sb.append( "</script>\n" );

        return sb.toString();
    }

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasRenderer#drawEnd()
     */
    public String drawEnd()
    {
        final StringBuffer sb = new StringBuffer();

        sb.append( "</body>\n" );
        sb.append( "</html>\n" );

        return sb.toString();
    }

    /**
     * @see org.squale.welcom.taglib.canvas.ICanvasRenderer#drawTraceur(java.lang.String, java.lang.String)
     */
    public String drawTraceur( String menuName, String itemName )
    {

        final StringBuffer sb = new StringBuffer();

        // Incorporation de la lib
        sb.append( "<script type=\"text/javascript\" src=\"jslib/comTraceurAF_v2.js\"></script>" );

        // Ecriture la la tetiere
        sb.append( "<div id=tetiere align=right><script>" );

        sb.append( "comTraceurAF_v2_parMenu(" );
        sb.append( menuName );
        sb.append( "," );
        sb.append( "\"" + itemName + "\");" );
        sb.append( "</script></div>" );

        return sb.toString();
    }
}
