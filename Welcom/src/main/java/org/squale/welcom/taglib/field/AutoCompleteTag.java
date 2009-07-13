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
 * Créé le 2 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.field;

import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;

/**
 * AutoCompleteTag
 */
public class AutoCompleteTag
    extends BaseTextTag
{
    /**
     * 
     */
    private static final long serialVersionUID = 4279121296835714925L;

    /** parametre du tag */
    private String onitemselection;

    /**
     * Ajoute l'attribut onintemselection
     */
    protected void prepareOthersAttributes( StringBuffer results )
    {

        if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.EASY_COMPLETE_NOTIFIER ) ) )
        {
            setStyleClass( getStyleClass() + " suggest" );
        }

        if ( onitemselection != null )
        {
            results.append( " onitemselection=\"" );
            results.append( onitemselection.replaceAll( "this", "target" ) );
            results.append( "\"" );
        }

        results.append( " autocomplete=\"off\" " );

    }

    /**
     * @return onitemselection
     */
    public String getOnitemselection()
    {
        return onitemselection;
    }

    /**
     * @param string onitemselection
     */
    public void setOnitemselection( final String string )
    {
        onitemselection = string;
    }

}