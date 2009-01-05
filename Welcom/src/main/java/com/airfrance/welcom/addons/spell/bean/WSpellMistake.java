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
 * Créé le 31 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.spell.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WSpellMistake
    implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3837209588092235002L;

    /** Mot incorrect */
    protected String invalidWord;

    /** Premier caractere du mot invalide */
    protected String firstChar;

    /** reste du mot sans la majuscule */
    protected String lastChar;

    /** Liste des suggestion */
    protected ArrayList suggestions = new ArrayList();

    /** nombre de suggestion */
    protected int suggestionSize;

    /** retourne vrai si la premiere lettre est en majuscule */
    protected boolean firstLetterCaps;

    /** retourne vrai si tout est en majuscule */
    protected boolean allCaps;

    /** position sur le contexte de l'erreur */
    protected int wordContextPosition;

    /**
     * @return accesseur
     */
    public boolean isAllCaps()
    {
        return allCaps;
    }

    /**
     * @return accesseur
     */
    public boolean isFirstLetterCaps()
    {
        return firstLetterCaps;
    }

    /**
     * @return accesseur
     */
    public String getInvalidWord()
    {
        return invalidWord;
    }

    /**
     * @param string accesseur
     */
    public void setInvalidWord( final String string )
    {
        invalidWord = string;
        firstChar = invalidWord.substring( 0, 1 );
        lastChar = invalidWord.substring( invalidWord.length() - 1 );
        firstLetterCaps = firstChar.equals( firstChar.toUpperCase() );
        allCaps = firstLetterCaps && lastChar.equals( lastChar.toUpperCase() ); // Assume that all in the middle are too
    }

    /**
     * @return accesseur
     */
    public List getSuggestions()
    {
        return suggestions;
    }

    /**
     * @param list accesseur
     */
    public void setSuggestions( final ArrayList list )
    {
        suggestions = list;
    }

    /**
     * Ajout la suggestion*
     * 
     * @param suggestedWord : Ajout du mot
     */
    public void addSuggestions( String suggestedWord )
    {

        if ( allCaps )
        {
            suggestedWord = suggestedWord.toUpperCase();
        }
        else if ( firstLetterCaps )
        {
            suggestedWord = suggestedWord.substring( 0, 1 ).toUpperCase() + suggestedWord.substring( 1 );
        }
        suggestions.add( suggestedWord );
    }

    /**
     * @return Taille des suggestions
     */
    public int getSuggestionSize()
    {
        return suggestions.size();
    }

    /**
     * @return Postion du mot dans le contexte
     */
    public int getWordContextPosition()
    {
        return wordContextPosition;
    }

    /**
     * @param i Positionne le contexte a une position donnée
     */
    public void setWordContextPosition( final int i )
    {
        wordContextPosition = i;
    }

}
