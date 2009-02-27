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
package com.airfrance.squalecommon.daolayer.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagCategoryBO;

/**
 * @author M400843
 */
public class TagCategoryDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static TagCategoryDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new TagCategoryDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private TagCategoryDAOImpl()
    {
        initialize( TagCategoryBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( TagCategoryDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static TagCategoryDAOImpl getInstance()
    {
        return instance;
    }
    
    /**
     * Récupére les categories de tags commençant par le nom passé en paramètre
     * 
     * @param pSession la session
     * @param pTagCategoryName le nom de la catégorie de tag que l'on veut récupérer
     * @return une collection de Tags possédant le même début de nom que le tag demandé
     * @throws JrafDaoException si une erreur à lieu
     */
    public Collection findNamedTagCategories( ISession pSession, String pTagCategoryName )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        Collection<TagCategoryBO> tagCats = new ArrayList<TagCategoryBO>();

        //récupération des component possédant le même tag
        String whereClause = "where ";
//        whereClause += pTag.getId() + " in elements(" + getAlias() + ".tags)";
        whereClause += getAlias() + ".name like(\'" + pTagCategoryName + "%\') order by " + getAlias() + ".name";

        Iterator it = findWhere( pSession, whereClause ).iterator();
        while ( it.hasNext() )
        {
            //pour chaque component renvoyé, on va verifier qu'il correspond bien à 
            //la même classe que la classe demandée
            TagCategoryBO tagCat = (TagCategoryBO) it.next();
            tagCats.add( tagCat );
        }

        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return tagCats;
    }
    
    /**
     * Récupére les tags présent en base
     * 
     * @param pSession la session
     * @return une collection de Tags possédant le même début de nom que le tag demandé
     * @throws JrafDaoException si une erreur à lieu
     */
    public Collection findTagCategories( ISession pSession )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        Collection<TagCategoryBO> tagCats = new ArrayList<TagCategoryBO>();

        //récupération des component possédant le même tag
        String whereClause = "order by " + getAlias() + ".id";

        Iterator it = findWhere( pSession, whereClause ).iterator();
        while ( it.hasNext() )
        {
            //pour chaque component renvoyé, on va verifier qu'il correspond bien à 
            //la même classe que la classe demandée
            TagCategoryBO tagCat = (TagCategoryBO) it.next();
            tagCats.add( tagCat );
        }

        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return tagCats;
    }
    
    /**
     * Creates the wanted tag category
     * 
     * @param pSession the current session
     * @param pTagCategoryToCreate the wanted tag that will be inserted in the database
     * @return createdTagCategory the tag inserted in the database
     * @throws JrafDaoException if an error occurs
     */
    public TagCategoryBO createTagCategory( ISession pSession, TagCategoryBO pTagCategoryToCreate )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        TagCategoryBO createdTagCategory = null; 

        // Verification que le nom du tag n'existe pas déjà
        int existantTagCategory = countWhereName( pSession, pTagCategoryToCreate );
        if ( 0 == existantTagCategory )
        {
            super.create( pSession, pTagCategoryToCreate );
            createdTagCategory = pTagCategoryToCreate;
        }

        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return createdTagCategory;
    }
    
    /**
     * Modifies the wanted tag category
     * 
     * @param pSession the current session
     * @param pTagCategoryToModify the wanted tag category that will be modified in the database
     * @return modifiedTagCategory the tag category modified in the database
     * @throws JrafDaoException if an error occurs
     */
    public TagCategoryBO modifyTagCategory( ISession pSession, TagCategoryBO pTagCategoryToModify )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        TagCategoryBO modifiedTagCategory = null; 

        super.save( pSession, pTagCategoryToModify );
        modifiedTagCategory = pTagCategoryToModify;

        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return modifiedTagCategory;
    }
    
    /**
     * deletes one or more TagCategoryBOs from the database with the naves given as a parameter
     * 
     * @param pSession the current session
     * @param pNamesToDelete a collection of Strings of the names of the tags to delete
     * @return boolean if the number of removals from the database equals the number wanted
     * @throws JrafDaoException if an error occurs
     */
    public boolean deleteTagCategories( ISession pSession, Collection<String> pNamesToDelete )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );

        String whereClause = "where ";
        // porte le même nom
        boolean first = true;
        whereClause += getAlias() + ".name in ("; 
        for ( String tagName : pNamesToDelete )
        {
            if (!first){
                whereClause += ",";
            }
            whereClause += "'" + tagName + "'";
            first = false;
        }
        whereClause += ")";
        
        int nbremoves = super.removeWhere( pSession, whereClause );

        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        
        return nbremoves == pNamesToDelete.size();
    }
    
    /**
     * returns the number of tagCategories with the given name
     * 
     * @param pSession the current session
     * @param pTagCategoryBO the tag category to find
     * @return a number of tagCategories
     * @throws JrafDaoException if an error occurs
     */
    public int countWhereName( ISession pSession, TagCategoryBO pTagCategoryBO )
        throws JrafDaoException
    {
        String whereClause = "where ";
        // porte le même nom
        whereClause += getAlias() + ".name = '" + pTagCategoryBO.getName() + "'";

        int ret = countWhere( pSession, whereClause ).intValue();
        return ret;
    }
}
