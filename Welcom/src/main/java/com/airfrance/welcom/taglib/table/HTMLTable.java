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
 * Créé le 24 mai 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.taglib.table;

import java.util.Vector;

/**
 * @author M327836 Classe contenant les "lignes" du tableau Pour changer le modèle de ce commentaire de type généré,
 *         allez à : Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class HTMLTable
    extends Vector
{
    /**
     * 
     */
    private static final long serialVersionUID = 206082772928774524L;

    /** taille du vecteur */
    protected int volume;

    /** nombre d'element dans la page */
    protected int length;

    /** from */
    protected int from;

    /** offset */
    protected int offset;

    /** previousFrom */
    protected int previousFrom;

    /** colonne triee */
    protected String sortColumn;

    /** si le tri est ascendant */
    protected boolean isAscending;

    /** mapping */
    protected String mapping;

    // protected Hashtable properties;
    // protected Hashtable booleanProperties;
    // protected Hashtable visibilityColumns;
    // protected Hashtable columnValues;

    /**
     * Constructeur
     */
    public HTMLTable()
    {
        // properties = new Hashtable();
        // booleanProperties = new Hashtable();
        // visibilityColumns = new Hashtable();
        // columnValues = new Hashtable();
        length = TableTag.DEFAULT_PAGE_LENGTH;
    }

    /**
     * @param pVolume le nouveau volume
     */
    public void setVolume( final int pVolume )
    {
        volume = pVolume;
    }

    /**
     * @return volume
     */
    public int getVolume()
    {
        return volume;
    }

    /**
     * @param pLength le nouveau length
     */
    public void setLength( final int pLength )
    {
        length = pLength;
    }

    /**
     * @return length
     */
    public int getLength()
    {
        return length;
    }

    /**
     * @param pFrom le nouveau from
     */
    public void setFrom( final int pFrom )
    {
        from = pFrom;
    }

    /**
     * @return from
     */
    public int getFrom()
    {
        return from;
    }

    /**
     * @param pSortColumn le nouveau sortColumn
     */
    public void setSortColumn( final String pSortColumn )
    {
        sortColumn = pSortColumn;
    }

    /**
     * @return sortColumn
     */
    public String getSortColumn()
    {
        return sortColumn;
    }

    /**
     * @param pIsAscending le nouveau isAscending
     */
    public void setIsAscending( final boolean pIsAscending )
    {
        isAscending = pIsAscending;
    }

    /**
     * @return isAscending
     */
    public boolean getIsAscending()
    {
        return isAscending;
    }

    /**
     * @param pMapping le nouveau pMapping
     */
    public void setMapping( final String pMapping )
    {
        mapping = pMapping;
    }

    /**
     * @return mapping
     */
    public String getMapping()
    {
        return mapping;
    }

    // public void setColumnVisibility(String columnID, boolean isVisible) {
    // visibilityColumns.put(columnID, new Boolean(isVisible));
    // }
    //
    // public boolean getColumnVisibility(String columnID) {
    // Boolean result = (Boolean) visibilityColumns.get(columnID);
    //
    // if (result == null) {
    // return true;
    // } else {
    // return result.booleanValue();
    // }
    // }

    /**
     * @return prefiousFrom
     */
    public int getPreviousFrom()
    {
        return previousFrom;
    }

    /**
     * @param i le nouveau previousFrom
     */
    public void setPreviousFrom( final int i )
    {
        previousFrom = i;
    }

    // public void setColumnValue(String columnID, String value) {
    // columnValues.put(columnID, value);
    // }
    //
    // public String getColumnValue(String columnID) {
    // return (String) columnValues.get(columnID);
    // }
    //
    // public void setProperty(String propertyName, String propertyValue) {
    // if (propertyValue == null) {
    // propertyValue = "";
    // }
    //
    // properties.put(propertyName, propertyValue);
    // }
    //
    // public String getProperty(String propertyName) {
    // return (String) properties.get(propertyName);
    // }
    //
    // public Enumeration getPropertyNames() {
    // return properties.keys();
    // }
    //
    // public void setBooleanProperty(String propertyName, Boolean propertyValue) {
    // booleanProperties.put(propertyName, propertyValue);
    // }
    //
    // public Boolean getBooleanProperty(String propertyName) {
    // return (Boolean) booleanProperties.get(propertyName);
    // }
    //
    // public Enumeration getBooleanPropertyNames() {
    // return booleanProperties.keys();
    // }
}