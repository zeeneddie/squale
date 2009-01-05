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
 * Créé le 16 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;

/**
 * @author Arnaud Lehmann Wrapper de ocnstruction de réponse xml pour l'Easy Complete
 */
public class WHttpEasyCompleteResponse
    extends AjaxXmlResponseWrapper
{

    /** liste des valeurs de l'easyComplete */
    private final ArrayList listeValue = new ArrayList();

    /**
     * constructeur
     * 
     * @param presponse la response HTTP
     */
    public WHttpEasyCompleteResponse( final HttpServletResponse presponse )
    {
        super( presponse, "collection" );
    }

    /**
     * Ajoute une value et un label dans la liste de l'easyComplete
     * 
     * @param value valeur
     * @param label libellé
     * @param hidden valeur hidden
     */
    public void addValueLabelHidden( final String value, final String label, final String hidden )
    {
        final String result = value + label + hidden;

        if ( !listeValue.contains( result ) )
        {
            listeValue.add( result );

            beginTag( "item" );
            addItem( "value", value );

            if ( !value.equals( label ) && !GenericValidator.isBlankOrNull( label ) )
            {
                addItem( "label", label );
            }
            if ( !GenericValidator.isBlankOrNull( hidden ) )
            {
                addItem( "hidden", hidden );
            }

            endTag( "item" );
        }
    }

    /**
     * Ajoute une value et un label dans la liste de l'easyComplete
     * 
     * @param value valeur
     * @param hidden valeur hidden
     */
    public void addValueHidden( final String value, final String hidden )
    {
        addValueLabelHidden( value, null, hidden );
    }

    /**
     * Ajoute une value et un label dans la liste de l'easyComplete
     * 
     * @param value Valeur
     */
    public void addValue( final String value )
    {
        addValueLabelHidden( value, null, null );
    }

    /**
     * Ajoute une value et un label dans la liste de l'easyComplete
     * 
     * @param value Valeur
     * @param label libellé
     */
    public void addValueLabel( final String value, final String label )
    {
        addValueLabelHidden( value, label, null );
    }

}