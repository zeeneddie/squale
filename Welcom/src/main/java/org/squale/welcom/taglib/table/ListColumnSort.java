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
 * Créé le 2 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.taglib.table;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.commons.validator.GenericValidator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ListColumnSort
    implements Serializable
{

    /** ID pour la serialization */
    static final long serialVersionUID = -1575338713418720436L;

    /** Constante */
    private static final String TABLE_TAG_KEY_SORT = "org.squale.welcom.tableTag";

    /** le vecteur des colonnes */
    private final Vector vcolumnsSort = new Vector();

    /** la hashtable contenant les colonnes */
    private final Hashtable hcolumnsSort = new Hashtable();

    /** Nom du bean conteant la table */
    private String name = "";

    /** Nom de la property conteant la table */
    private String property = "";

    /**
     * Ajoute le colSort en fonction a ajouter
     * 
     * @param pageContext pageContext
     */
    public void addColSort( final PageContext pageContext )
    {
        final ColSort colSort = new ColSort();
        if ( getCle( getName(), getProperty() ).equals( pageContext.getRequest().getParameter( "table" ) ) )
        {
            colSort.setColumn( pageContext.getRequest().getParameter( "colonne" ) );
            colSort.setType( pageContext.getRequest().getParameter( "type" ) );
            colSort.setDateFormat( pageContext.getRequest().getParameter( "dateformat" ) );
            colSort.setSort( SortOrder.getSortOrder( pageContext.getRequest().getParameter( "sens" ) ) );
            addColSort( colSort );
        }
    }

    /**
     * @param colSort le colSort a ajouter
     */
    public void addColSort( final ColSort colSort )
    {
        if ( ( colSort != null ) && ( colSort.getColumn() != null ) )
        {
            vcolumnsSort.clear();
            hcolumnsSort.clear();

            if ( !vcolumnsSort.contains( colSort ) )
            {
                vcolumnsSort.add( colSort );
                hcolumnsSort.put( colSort.getColumn(), colSort );
            }
        }
    }

    /**
     * @param col la colonne
     * @return le ColSort associé ) la colonne col
     */
    public ColSort getColSort( final Col col )
    {
        if ( hcolumnsSort.containsKey( col.getProperty() ) )
        {
            return (ColSort) ( hcolumnsSort.get( col.getProperty() ) );
        }
        else
        {
            final ColSort colSort = new ColSort();
            colSort.setColumn( col.getProperty() );
            colSort.setSort( SortOrder.NONE );

            return colSort;
        }
    }

    /**
     * @return retourne le 1er ColSort
     */
    public ColSort getFirst()
    {
        return (ColSort) vcolumnsSort.firstElement();
    }

    /**
     * @return true si on a aucune colonne dans le tableSort
     */
    public boolean isEmpty()
    {
        return vcolumnsSort.isEmpty();
    }

    /**
     * initialise l'attribut keySort ou le retourne
     * 
     * @param session : Session
     * @param name : nom bean de la table
     * @param property : nom de la property
     */
    public static synchronized ListColumnSort getKeySortOfTable( final HttpSession session, String name, String property )
    {

        ListColumnSort keySort = null;
        HashMap tableKeySort = (HashMap) session.getAttribute( TABLE_TAG_KEY_SORT );

        String cle = getCle( name, property );

        // initialise la liste les tables pour le trie
        if ( tableKeySort == null )
        {
            tableKeySort = new HashMap();
            session.setAttribute( TABLE_TAG_KEY_SORT, tableKeySort );
        }

        // initialise la liste des clef pour une table donnée
        if ( !tableKeySort.containsKey( cle ) )
        {
            keySort = new ListColumnSort();
            keySort.setName( name );
            keySort.setProperty( property );
            tableKeySort.put( cle, keySort );
        }
        else
        {
            keySort = (ListColumnSort) tableKeySort.get( cle );
        }

        // Retourne la liste des clef pour une table donnée
        return keySort;
    }

    /**
     * supprimer l'ordre de trie pour la table
     * 
     * @param session : Session
     * @param name : nom bean de la table
     * @param property : nom de la property
     */
    public static synchronized void resetKeySortOfTable( final HttpSession session, String name, String property )
    {

        HashMap tableKeySort = (HashMap) session.getAttribute( TABLE_TAG_KEY_SORT );

        String cle = getCle( name, property );

        if ( tableKeySort != null && tableKeySort.containsKey( cle ) )
        {
            tableKeySort.remove( cle );
        }
    }

    /**
     * Transforme le couple name, property en clef
     * 
     * @param name name
     * @param property property
     * @return la clef (name.property)
     */
    public static String getCle( String name, String property )
    {
        String cle = name;

        if ( !GenericValidator.isBlankOrNull( property ) )
        {
            cle += ( "." + property );
        }
        return cle;
    }

    /**
     * @return name attribut
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name name attribut
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * @return property attribut
     */
    public String getProperty()
    {
        return property;
    }

    /**
     * @param property property attribut
     */
    public void setProperty( String property )
    {
        this.property = property;
    }
}