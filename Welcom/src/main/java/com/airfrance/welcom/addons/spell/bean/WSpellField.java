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
 * Créé le 30 janv. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.spell.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WSpellField
    implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -4891177367107395064L;

    /** nom du champ */
    private String name;

    /** valuer du champ */
    private String value;

    /** liste des erreur */
    private ArrayList mistake = new ArrayList();

    /**
     * @return accesseur
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return accesseur
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param string accesseur
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * @param string accesseur
     */
    public void setValue( final String string )
    {
        value = string;
    }

    /**
     * @return accesseur
     */
    public ArrayList getMistake()
    {
        return mistake;
    }

    /**
     * @param list accesseur
     */
    public void setMistake( final ArrayList list )
    {
        mistake = list;
    }

    /**
     * Ajoute une erreur
     * 
     * @param sm erreur
     */
    public void addMistake( final WSpellMistake sm )
    {
        mistake.add( sm );
    }

    /**
     * @return accesseur
     */
    public int getMistakeSize()
    {
        return mistake.size();
    }

}
