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

import java.util.Enumeration;
import java.util.Hashtable;

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
public class WPdfFields
{
    /** hashtable */
    private final Hashtable h = new Hashtable();

    /**
     * 
     */
    public WPdfFields()
    {
        super();
    }

    /**
     * Ajoute le pdfField a la hashtable
     * 
     * @param pdfField le pdfField a ajouter
     */
    public void add( final WPdfField pdfField )
    {
        h.put( pdfField.getName(), pdfField );
    }

    /**
     * @param pdfFields les pdfFields a ajouter
     */
    public void addAll( final WPdfFields pdfFields )
    {
        final Enumeration enumeration = elements();
        while ( enumeration.hasMoreElements() )
        {
            add( (WPdfField) enumeration.nextElement() );
        }
    }

    /**
     * @return une enumeration des pdffields
     */
    public Enumeration elements()
    {
        return h.elements();
    }

    /**
     * @param name la chainee teste
     * @return true si on contient une cle avec le nom name
     */
    public boolean contains( final String name )
    {
        return h.containsKey( name );
    }

    /**
     * @param name le nom du field
     * @return le WPdfField
     * @throws WPdfFieldException exception pouvant etre levee
     */
    public WPdfField getField( final String name )
        throws WPdfFieldException
    {
        if ( h.containsKey( name ) )
        {
            return (WPdfField) h.get( name );
        }
        else
        {
            throw new WPdfFieldException( "Champs '" + name + "' non disponible dans le document" );
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return h.toString();
    }

}
