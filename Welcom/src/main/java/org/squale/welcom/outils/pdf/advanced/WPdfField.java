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
public class WPdfField
{
    /** le name */
    private String name = "";

    /** la valeur par defaut */
    private String defaultValue = "";

    /** la value */
    private String value = "";

    /** le type */
    private WPdfFieldType type = WPdfFieldType.INCONNU;

    /**
     * @return defaultValue
     */
    public String getDefaultValue()
    {
        return defaultValue;
    }

    /**
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return type
     */
    public WPdfFieldType getType()
    {
        return type;
    }

    /**
     * @return value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param string defaultValue
     */
    public void setDefaultValue( final String string )
    {
        defaultValue = string;
    }

    /**
     * @param string name
     */
    public void setName( final String string )
    {
        name = string;
    }

    /**
     * @param pType type
     */
    public void setType( final WPdfFieldType pType )
    {
        type = pType;
    }

    /**
     * @param string value
     */
    public void setValue( final String string )
    {
        value = string;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return name + " : ('" + defaultValue + "','" + value + "','" + type + "')";
    }

}
