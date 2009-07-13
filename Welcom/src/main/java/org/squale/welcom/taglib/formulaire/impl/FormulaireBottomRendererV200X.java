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
package org.squale.welcom.taglib.formulaire.impl;

import org.squale.welcom.outils.TrimStringBuffer;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.taglib.button.ButtonBarTag;
import org.squale.welcom.taglib.formulaire.IFormulaireBottomRenderer;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class FormulaireBottomRendererV200X
    implements IFormulaireBottomRenderer
{

    /** le pixel gris */
    private final String imgPixGrey = WelcomConfigurator.getMessage( WelcomConfigurator.CHARTEV2_PIX_GREY_GIF );

    /**
     * @see org.squale.welcom.taglib.formulaire.IFormulaireBottomRenderer#drawTable(java.lang.String)
     */
    public String drawTable( String bottomvalue )
    {

        final TrimStringBuffer buf = new TrimStringBuffer();

        if ( Util.isTrimNonVide( ButtonBarTag.purgeBodyHidden( bottomvalue ) ) )
        {
            // buf.append("<!-- Génération de la barre de boutons -->\n");
            buf.append( "\t<table class=\"wide\">\n" );

            buf.append( "\t\t<tr class=trtete align=\"center\" height=\"25\"> \n" );

            buf.append( bottomvalue );

            buf.append( "\t\t</tr>\n" );

            // buf.append("<!-- Fin de la Génération de la barre de boutons -->\n");
            buf.append( "\t</table>\n" );
        }
        else
        {
            buf.append( "\t<table class=\"wide\">\n" );
            buf.append( "\t\t<tr>\n" );
            buf.append( "<td height=\"1\"><img src=\"" + imgPixGrey + "\" height=\"1\" width=\"100%\"></td> \n" );
            buf.append( "\t\t</tr>\n" );
            buf.append( "\t</table>\n" );
        }

        return buf.toString();

    }

}
