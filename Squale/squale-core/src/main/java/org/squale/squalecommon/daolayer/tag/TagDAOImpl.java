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
 * Créé le 8 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.squalecommon.daolayer.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import org.squale.squalecommon.enterpriselayer.businessobject.tag.TagBO;
import org.squale.squalecommon.enterpriselayer.businessobject.tag.TagCategoryBO;

/**
 * The dao implementation for the tag
 */
public final class TagDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static TagDAOImpl instance;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new TagDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private TagDAOImpl()
    {
        initialize( TagBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( TagDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static TagDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Retrieves the components posessing the wanted tag
     * 
     * @param pSession the current session
     * @param pTag the wanted tag
     * @param pClass the class of the wanted component
     * @return a Collection of Components linked to the given tag
     * @throws JrafDaoException si une erreur à lieu
     */
    public Collection findTaggedComponents( ISession pSession, TagBO pTag, Class pClass )
        throws JrafDaoException
    {
        Collection components = new ArrayList();

        // récupération des component possédant le même tag
        String whereClause = "where ";
        whereClause += pTag.getId() + " in elements(" + getAlias() + ".tags)";

        Iterator it = findWhere( pSession, whereClause ).iterator();
        while ( it.hasNext() )
        {
            // pour chaque component renvoyé, on va verifier qu'il correspond bien à
            // la même classe que la classe demandée
            AbstractComponentBO component = (AbstractComponentBO) it.next();
            if ( pClass.isInstance( component ) )
            {
                // Si le comosant correspond bien à celui demandé, on
                // va l'ajouter à la liste des composants à renvoyer
                components.add( component );
            }
        }
        return components;
    }

    /**
     * Retrieves the tags with their name starting with the given string
     * 
     * @param pSession the current session
     * @param pTagNames an array of the staring caracters of the names of the wanted tags
     * @return a Collection of Tags with their name starting with the given caracters
     * @throws JrafDaoException if an error occurs
     */
    public Collection findNamedTags( ISession pSession, String[] pTagNames )
        throws JrafDaoException
    {
        Collection<TagBO> tags = new ArrayList<TagBO>();

        if ( pTagNames != null && pTagNames.length > 0 && !"".equals( pTagNames[0] ) )
        {
            // récupération des component possédant le même tag
            String whereClause = "where ";

            whereClause += getAlias() + ".name like(\'" + pTagNames[0] + "%\')";
            if ( pTagNames.length > 1 )
            {
                for ( int i = 1; i < pTagNames.length; ++i )
                {
                    whereClause += "or " + getAlias() + ".name like(\'" + pTagNames[i] + "%\')";
                }
            }
            whereClause += "order by " + getAlias() + ".name";

            tags = (Collection<TagBO>) findWhere( pSession, whereClause );
        }
        return tags;
    }

    /**
     * Retrieves the tags with their name equal to the given string
     * 
     * @param pSession the current session
     * @param pTagNames an array of the staring caracters of the names of the wanted tags
     * @return a Collection of Tags with their name starting with the given caracters
     * @throws JrafDaoException if an error occurs
     */
    public Collection findExactNamedTags( ISession pSession, String[] pTagNames )
        throws JrafDaoException
    {
        Collection<TagBO> tags = new ArrayList<TagBO>();

        if ( pTagNames != null && pTagNames.length > 0 && !"".equals( pTagNames[0] ) )
        {
            // récupération des component possédant le même tag
            String whereClause = "where ";

            whereClause += getAlias() + ".name = (\'" + pTagNames[0] + "\')";
            if ( pTagNames.length > 1 )
            {
                for ( int i = 1; i < pTagNames.length; ++i )
                {
                    whereClause += "or " + getAlias() + ".name = (\'" + pTagNames[i] + "\')";
                }
            }
            whereClause += "order by " + getAlias() + ".name";

            tags = (Collection<TagBO>) findWhere( pSession, whereClause );
        }
        return tags;
    }

    /**
     * Retrieves the tags of the wanted tagcategory
     * 
     * @param pSession the current session
     * @param pTagCategory the category used to retrieve the tags with the same category
     * @return a Collection of Tags of the wanted category
     * @throws JrafDaoException if an error occurs
     */
    public Collection<TagBO> findCategoryTags( ISession pSession, TagCategoryBO pTagCategory )
        throws JrafDaoException
    {
        Collection<TagBO> tags = new ArrayList<TagBO>();

        // retrieval of the tags with the current tagcategory
        String whereClause = "where ";
        whereClause += getAlias() + ".tagCategoryBO = " + pTagCategory.getId();

        Iterator<TagBO> it = findWhere( pSession, whereClause ).iterator();
        while ( it.hasNext() )
        {
            // every tag is individually added to the return collection
            TagBO tag = (TagBO) it.next();
            tags.add( tag );
        }
        return tags;
    }

    /**
     * retrieves the existing tags from the database
     * 
     * @param pSession the current session
     * @return a collection of all the existing Tags in the database
     * @throws JrafDaoException if an error occurs
     */
    public Collection findTags( ISession pSession )
        throws JrafDaoException
    {
        Collection<TagBO> tags = new ArrayList();

        // récupération des component possédant le même tag
        String whereClause = "order by " + getAlias() + ".id";

        Iterator it = findWhere( pSession, whereClause ).iterator();
        while ( it.hasNext() )
        {
            // pour chaque component renvoyé, on va verifier qu'il correspond bien à
            // la même classe que la classe demandée
            TagBO tag = (TagBO) it.next();
            tags.add( tag );
        }
        return tags;
    }

    /**
     * Creates the wanted tag
     * 
     * @param pSession the current session
     * @param pTagToCreate the wanted tag that will be inserted in the database
     * @return createdTag the tag inserted in the database
     * @throws JrafDaoException if an error occurs
     */
    public TagBO createTag( ISession pSession, TagBO pTagToCreate )
        throws JrafDaoException
    {
        TagBO createdTag = null;

        // Verification que le nom du tag n'existe pas déjà
        int existantTag = countWhereName( pSession, pTagToCreate );
        if ( 0 == existantTag )
        {
            super.create( pSession, pTagToCreate );
            createdTag = pTagToCreate;
        }
        return createdTag;
    }

    /**
     * Modifies the wanted tag
     * 
     * @param pSession the current session
     * @param pTagToModify the wanted tag that will be modified in the database
     * @return modifiedTag the tag modified in the database
     * @throws JrafDaoException if an error occurs
     */
    public TagBO modifyTag( ISession pSession, TagBO pTagToModify )
        throws JrafDaoException
    {
        TagBO modifiedTag = null;

        super.save( pSession, pTagToModify );
        modifiedTag = pTagToModify;
        return modifiedTag;
    }

    /**
     * deletes one or more TagBOs from the database with the naves given as a parameter
     * 
     * @param pSession the current session
     * @param pNamesToDelete a collection of Strings of the names of the tags to delete
     * @return boolean if the number of removals from the database equals the number wanted
     * @throws JrafDaoException if an error occurs
     */
    public boolean deleteTags( ISession pSession, Collection<String> pNamesToDelete )
        throws JrafDaoException
    {
        String whereClause = "where ";
        // porte le même nom
        boolean first = true;
        whereClause += getAlias() + ".name in (";
        for ( String tagName : pNamesToDelete )
        {
            if ( !first )
            {
                whereClause += ",";
            }
            whereClause += "'" + tagName + "'";
            first = false;
        }
        whereClause += ")";

        int nbremoves = super.removeWhere( pSession, whereClause );

        return nbremoves == pNamesToDelete.size();
    }

    /**
     * returns the number of tags with the given name
     * 
     * @param pSession the current session
     * @param pTagBO the tag to find
     * @return a number of tags
     * @throws JrafDaoException if an error occurs
     */
    public int countWhereName( ISession pSession, TagBO pTagBO )
        throws JrafDaoException
    {
        String whereClause = "where ";
        // porte le même nom
        whereClause += getAlias() + ".name = '" + pTagBO.getName() + "'";

        int ret = countWhere( pSession, whereClause ).intValue();
        return ret;
    }
}
