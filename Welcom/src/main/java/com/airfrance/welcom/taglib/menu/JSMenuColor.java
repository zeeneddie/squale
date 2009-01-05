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
package com.airfrance.welcom.taglib.menu;

/**
 * @author LEBRERE fabien
 */
public class JSMenuColor
{
    /** Constante */
    private static final JSMenuColor BLEU_FONCE = new JSMenuColor( "0", "BLEU FONCE", "Infos Compagnie" );

    /** Constante */
    private static final JSMenuColor ROUGE = new JSMenuColor( "1", "ROUGE", "Les Métiers" );

    /** Constante */
    private static final JSMenuColor BLEU_CLAIR = new JSMenuColor( "2", "BLEU_CLAIR", "Coté pratique" );

    /** Constante */
    private static final JSMenuColor ORANGE = new JSMenuColor( "3", "ORANGE", "Parcours professionnel" );

    /** Constante */
    private static final JSMenuColor VERT = new JSMenuColor( "4", "VERT", "Entre nous" );

    /** Constante */
    private static final JSMenuColor PRUNE = new JSMenuColor( "5", "PRUNE", "Environnement" );

    /** l'id */
    private String id;

    /** la couleur */
    private String couleur;

    /** la description */
    private String description;

    /*******************************************************************************************************************
     * Constructeur
     * 
     * @param pId l'id
     * @param pCouleur la couleur
     * @param pDescription la description
     */
    private JSMenuColor( final String pId, final String pCouleur, final String pDescription )
    {
        id = pId;
        couleur = pCouleur;
        description = pDescription;
    }

    /**
     * @param id id de la couleur
     * @return la couleur en fonction de l'id
     * @throws JSMenuColorNotFound exception si la couleur n'est pas trouvee
     */
    public static JSMenuColor getJSMenuColorById( final String id )
        throws JSMenuColorNotFound
    {
        if ( id.equals( "1" ) )
        {
            return BLEU_FONCE;
        }
        else if ( id.equals( "2" ) )
        {
            return ROUGE;
        }
        else if ( id.equals( "3" ) )
        {
            return BLEU_CLAIR;
        }
        else if ( id.equals( "4" ) )
        {
            return ORANGE;
        }
        else if ( id.equals( "5" ) )
        {
            return VERT;
        }
        else if ( id.equals( "6" ) )
        {
            return PRUNE;
        }
        else
        {
            throw new JSMenuColorNotFound( "JSMenuColorNotFound : ID :" + id + " non trouvé" );
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( final Object o )
    {
        if ( o instanceof JSMenuColor )
        {
            return this.getId().equals( ( (JSMenuColor) o ).getId() );
        }

        return super.equals( o );
    }

    /**
     * Returns the couleur.
     * 
     * @return String
     */
    public String getCouleur()
    {
        return couleur;
    }

    /**
     * Returns the description.
     * 
     * @return String
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Returns the id.
     * 
     * @return String
     */
    public String getId()
    {
        return id;
    }

    /**
     * Sets the couleur.
     * 
     * @param pCouleur The couleur to set
     */
    public void setCouleur( final String pCouleur )
    {
        couleur = pCouleur;
    }

    /**
     * Sets the description.
     * 
     * @param pDescription The description to set
     */
    public void setDescription( final String pDescription )
    {
        description = pDescription;
    }

    /**
     * Sets the id.
     * 
     * @param pId The id to set
     */
    public void setId( final String pId )
    {
        id = pId;
    }
}