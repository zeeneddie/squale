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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\org\\squale\\squalecommon\\enterpriselayer\\applicationcomponent\\ApplicationAdminApplicationComponentAccess.java

package org.squale.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.accessdelegate.DefaultExecuteComponent;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.datatransfertobject.tag.TagCategoryDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.enterpriselayer.facade.component.ProjectFacade;
import org.squale.squalecommon.enterpriselayer.facade.tag.TagFacade;

/**
 * <p>
 * Title : ApplicationAdminApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component de configuration du projet
 * </p>
 */
public class TagAdminTagComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( TagAdminTagComponentAccess.class );

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * default constructor
     * 
     * @roseuid 42CBFC010285
     */
    public TagAdminTagComponentAccess()
    {
    }
    
    /**
     * Retrieves the tag from the database with its Id
     * 
     * @param pTagId the id of the wanted tag
     * @return the tag if it exists, null otherwise
     * @throws JrafEnterpriseException if an error occurs
     */
    public TagDTO getTag ( Long pTagId )
        throws JrafEnterpriseException
    {
        return TagFacade.getTag( pTagId );
    }
    
    /**
     * Retrieves the tags from the given name
     * 
     * @param pStringFirstChars array containing the beginning of the names of the wanted TagDTOs
     * @return the list of tag if there is at least one, null otherwise
     * @throws JrafEnterpriseException if an error occurs
     */
    public Collection<TagDTO> getTagsByName ( String[] pStringFirstChars )
        throws JrafEnterpriseException
    {
        return TagFacade.getTagsByName( pStringFirstChars );
    }
    
    /**
     * Retrieves all the existing tags
     * 
     * @return the list of existing tags in the database, null if there are none
     * @throws JrafEnterpriseException if an error occurs
     */
    public Collection<TagDTO> getTags()
        throws JrafEnterpriseException
    {
        return TagFacade.getTags();
    }
    
    /**
     * Retrieves all the existing tag categories
     * 
     * @return the list of existing tag categories in the database, null if there are none
     * @throws JrafEnterpriseException si erreur
     */
    public Collection<TagCategoryDTO> getTagCategories()
        throws JrafEnterpriseException
    {
        return TagFacade.getTagCategories();
    }
    
    /**
     * Retrieves the tag categories from the given name
     * 
     * @param pStringFirstChars the beginning of the name of the tag category
     * @return the list of tag categories if there is at least one, null otherwise
     * @throws JrafEnterpriseException if an error occurs
     */
    public Collection<TagCategoryDTO> getTagCategoriesByName ( String pStringFirstChars )
        throws JrafEnterpriseException
    {
        return TagFacade.getTagCategoriesByName( pStringFirstChars );
    }
    
    /**
     * creates a Tag in the database from a given object tag
     * 
     * @param pTagDTO the tag to create in the database
     * @return the TagDTO once it is created
     * @throws JrafEnterpriseException exception JRAF
     */
    public TagDTO createTag ( TagDTO pTagDTO )
        throws JrafEnterpriseException
    {
        return TagFacade.createTag( pTagDTO );
    }
    
    /**
     * modifies a Tag in the database from a given object tag
     * 
     * @param pTagDTO the tag to modify in the database
     * @return the TagDTO once it has been modified
     * @throws JrafEnterpriseException exception JRAF
     */
    public TagDTO modifyTag ( TagDTO pTagDTO )
        throws JrafEnterpriseException
    {
        return TagFacade.modifyTag( pTagDTO );
    }
    
    /**
     * creates a TagCategory in the database from a given object tagCategory
     * 
     * @param pTagCategoryDTO the tag to create in the database
     * @return the TagCategoryDTO once it is created
     * @throws JrafEnterpriseException exception JRAF
     */
    public TagCategoryDTO createTagCategory ( TagCategoryDTO pTagCategoryDTO )
        throws JrafEnterpriseException
    {
        return TagFacade.createTagCategory( pTagCategoryDTO );
    }
    
    /**
     * modifies a TagCategory in the database from a given object tag category
     * 
     * @param pTagCategoryDTO the tag category to modify in the database
     * @return the TagCategoryDTO once it has been modified
     * @throws JrafEnterpriseException exception JRAF
     */
    public TagCategoryDTO modifyTagCategory ( TagCategoryDTO pTagCategoryDTO )
        throws JrafEnterpriseException
    {
        return TagFacade.modifyTagCategory( pTagCategoryDTO );
    }
    
    /**
     * deletes one or more tags from the database with the naves given as a parameter
     * 
     * @param pNamesToDelete a collection of Strings of the names of the tags to delete
     * @return boolean if the number of removals from the database equals the number wanted
     * @throws JrafEnterpriseException exception JRAF
     */
    public Boolean deleteTags ( Collection<String> pNamesToDelete )
        throws JrafEnterpriseException
    {
        return TagFacade.deleteTags( pNamesToDelete );
    }
    
    /**
     * deletes one or more tag categories from the database with the naves given as a parameter
     * 
     * @param pNamesToDelete a collection of Strings of the names of the tag categories to delete
     * @return boolean if the number of removals from the database equals the number wanted
     * @throws JrafEnterpriseException exception JRAF
     */
    public Boolean deleteTagCategories ( Collection<String> pNamesToDelete )
        throws JrafEnterpriseException
    {
        return TagFacade.deleteTagCategories( pNamesToDelete );
    }
}
