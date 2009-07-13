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
package org.squale.welcom.outils.pdf.advanced;

/*
 * Créé le 3 nov. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WPdfFieldType
{
    /** type */
    private int type = 0;

    /** constante */
    public final static WPdfFieldType TEXT = new WPdfFieldType( 1 );

    /** constante */
    public final static WPdfFieldType MUTILINETEXT = new WPdfFieldType( 2 );

    /** constante */
    public final static WPdfFieldType INCONNU = new WPdfFieldType( 3 );

    /**
     * @param i le type
     */
    private WPdfFieldType( final int i )
    {
        super();
        type = i;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        String theString = "INCONNU";
        if ( this == TEXT )
        {
            theString = "LIGNE SIMPLE";
        }
        else if ( this == MUTILINETEXT )
        {
            theString = "MULTI-LIGNE";
        }
        return theString;
    }

}
