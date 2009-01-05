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
/*
 * Créé le 9 nov. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.pdf;

/**
 * @author M327836 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class PDFEngine
{
    /** constante */
    public final static PDFEngine ITEXT = new PDFEngine( "iText" );

    /** constante */
    public final static PDFEngine JASPERREPORTS = new PDFEngine( "jasperReports" );

    /** identifiant */
    private String name = "";

    /**
     * Constructeur
     * 
     * @param engineName identifiant
     */
    private PDFEngine( final String engineName )
    {
        name = engineName;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {

        return name;
    }

}
