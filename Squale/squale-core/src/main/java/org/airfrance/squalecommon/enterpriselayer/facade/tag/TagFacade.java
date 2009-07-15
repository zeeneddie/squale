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
package com.airfrance.squalecommon.enterpriselayer.facade.tag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.tag.TagCategoryDAOImpl;
import com.airfrance.squalecommon.daolayer.tag.TagDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.tag.TagCategoryDTO;
import com.airfrance.squalecommon.datatransfertobject.tag.TagDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.tag.TagCategoryTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.tag.TagTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagCategoryBO;
import com.airfrance.squalecommon.enterpriselayer.facade.FacadeMessages;
import com.airfrance.squalecommon.util.messages.CommonMessages;

/**
 * The facade accessing all the operations on tags or tagCategories
 */
public class TagFacade
    implements IFacade
{
    
    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /** log */
    private static Log LOG = LogFactory.getLog( TagFacade.class );

    /**
     * retrieves the wanted TagDTO with a given ID
     * 
     * @param pTagId ID of the wanted Tag
     * @return TagDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public static TagDTO getTag( long pTagId )
        throws JrafEnterpriseException
    {

        // Initialisation du retour
        TagDTO tagDTO = null;

        // Initialisation des variables temporaires
        TagBO tagBO = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagDAOImpl tagDAO = TagDAOImpl.getInstance();

            tagBO = (TagBO) tagDAO.get( session, pTagId );

            if ( tagBO != null )
            {
                tagDTO = TagTransform.bo2Dto( tagBO );
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, TagFacade.class.getName() + ".get" );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".get" );
        }

        return tagDTO;
    }

    /**
     * Retrieves the TagDTOs with their name starting with the given string
     * 
     * @param pStringFirstChars array containing the beginning of the names of the wanted TagDTOs
     * @return Collection of TagDTOs
     * @throws JrafEnterpriseException exception JRAF
     */
    public static Collection<TagDTO> getTagsByName( String[] pStringFirstChars )
        throws JrafEnterpriseException
    {

        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        Collection<TagDTO> tagsDTO = new ArrayList<TagDTO>();

        // Initialisation des variables temporaires
        Collection<TagBO> tagsBO = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagDAOImpl tagDAO = TagDAOImpl.getInstance();

            tagsBO = (Collection<TagBO>) tagDAO.findNamedTags( session, pStringFirstChars );

            if ( tagsBO != null && tagsBO.size()>0 )
            {
                for ( TagBO tagBO : tagsBO )
                {
                    tagsDTO.add( TagTransform.bo2Dto( tagBO ) );
                }
            }
            else
            {
                LOG.debug( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".getTagsByName", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".getTagsByName" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return tagsDTO;
    }
    
    /**
     * retrieves a collection of TagDTO objects
     * 
     * @return Collection of TagDTOs
     * @throws JrafEnterpriseException exception JRAF
     */
    public static Collection<TagDTO> getTags(  )
        throws JrafEnterpriseException
    {

        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        Collection<TagDTO> tagsDTO = new ArrayList<TagDTO>();

        // Initialisation des variables temporaires
        Collection<TagBO> tagsBO = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagDAOImpl tagDAO = TagDAOImpl.getInstance();

            tagsBO = (Collection<TagBO>) tagDAO.findTags( session );

            if ( tagsBO != null && tagsBO.size()>0 )
            {
                for ( TagBO tagBO : tagsBO )
                {
                    tagsDTO.add( TagTransform.bo2Dto( tagBO ) );
                }
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".getTags", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".getTags" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return tagsDTO;
    }
    
    /**
     * Retrieves the TagCategoryDTOs with their name starting with the given string
     * 
     * @param pStringFirstChars the beginning of the name of the wanted TagCategoryDTO
     * @return Collection of TagCategoryDTOs
     * @throws JrafEnterpriseException exception JRAF
     */
    public static Collection<TagCategoryDTO> getTagCategoriesByName( String pStringFirstChars )
        throws JrafEnterpriseException
    {

        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        Collection<TagCategoryDTO> tagCategoriesDTO = new ArrayList<TagCategoryDTO>();

        // Initialisation des variables temporaires
        Collection<TagCategoryBO> tagCategoriesBO = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagCategoryDAOImpl tagCatDAO = TagCategoryDAOImpl.getInstance();

            tagCategoriesBO = (Collection<TagCategoryBO>) tagCatDAO.findNamedTagCategories( session, pStringFirstChars );

            if ( tagCategoriesBO != null && tagCategoriesBO.size()>0 )
            {
                for ( TagCategoryBO tagCatBO : tagCategoriesBO )
                {
                    tagCategoriesDTO.add( TagCategoryTransform.bo2Dto( tagCatBO ) );
                }
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".getTagsByName", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".getTagsByName" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return tagCategoriesDTO;
    }
    
    /**
     * retrieves the collection of all the TagCategoryDTO in the database
     * 
     * @return Collection of TagCategoryDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public static Collection<TagCategoryDTO> getTagCategories()
        throws JrafEnterpriseException
    {

        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        Collection<TagCategoryDTO> tagCategoriesDTO = new ArrayList<TagCategoryDTO>();

        // Initialisation des variables temporaires
        Collection<TagCategoryBO> tagCategoriesBO = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagCategoryDAOImpl tagCatDAO = TagCategoryDAOImpl.getInstance();

            tagCategoriesBO = (Collection<TagCategoryBO>) tagCatDAO.findTagCategories( session );

            if ( tagCategoriesBO != null && tagCategoriesBO.size()>0 )
            {
                for ( TagCategoryBO tagCatBO : tagCategoriesBO )
                {
                    tagCategoriesDTO.add( TagCategoryTransform.bo2Dto( tagCatBO ) );
                }
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".getTags", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".getTags" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return tagCategoriesDTO;
    }
    
    /**
     * Retrieves the components posessing the wanted tag
     * 
     * @param pTag the wanted tag
     * @param pClass the class of the wanted component, <code>null</code> for any component
     * @return Collection of ComponentDTO
     * @throws JrafEnterpriseException exception JRAF
     */
    public static Collection getTaggedComponents(Class pClass, TagBO pTag)
        throws JrafEnterpriseException
    {
        LOG.debug( CommonMessages.getString( "method.entry" ) );
        final int limit = 1000;
        // Initialisation du retour
        Collection componentDTOs = new ArrayList( 0 );
        
        // Initialisation des variables temporaires
        TagBO tagBO = null; // Objet metier tag
        Collection componentBOs = null; // Collection des objets metiers taggés
        Long tagId = new Long (pTag.getId());
        
        //L'instance de TagDAOImpl
        TagDAOImpl tagDAO = TagDAOImpl.getInstance();
        
        ISession session = null;
        try{
            session = PERSISTENTPROVIDER.getSession();
            
            // Recupere les objets metiers par la DAO
            // On fait un tri suivant le paramètre pType qui indique quels types de composants on veut
            // et on ne récupère que les 1000 premiers pour éviter les "out of memory"
            
            tagBO = (TagBO) tagDAO.get( session, tagId );
            if ( tagBO != null )
            {
                componentBOs = tagDAO.findTaggedComponents( session, pTag, pClass );
            }
            // Manipulation de la collection pour la transformation en DTO
            for ( Iterator it = componentBOs.iterator(); it.hasNext(); )
            {
                AbstractComponentBO componentTemp = (AbstractComponentBO) it.next();
                componentDTOs.add( ComponentTransform.bo2Dto( componentTemp ) );
            }
        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".getTaggedComponents", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".getTaggedComponents" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return componentDTOs;
    }
    
    /**
     * creates a TagBO in the database from a given tagDTO
     * 
     * @param pTagDTO the tag to create in the database
     * @return the TagDTO once it is created
     * @throws JrafEnterpriseException exception JRAF
     */
    public static TagDTO createTag ( TagDTO pTagDTO )
        throws JrafEnterpriseException
    {
        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        TagDTO tagDTOCreated = null;
        
        //Variables temporaires
        TagBO tagBO = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagDAOImpl tagDAO = TagDAOImpl.getInstance();

            tagBO = tagDAO.createTag( session, TagTransform.dto2Bo( pTagDTO ) );

            if ( tagBO != null )
            {
                tagDTOCreated = TagTransform.bo2Dto( tagBO );
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".createTag", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".createTag" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return tagDTOCreated;
    }
    
    /**
     * modifies a TagBO in the database from a given tagDTO
     * 
     * @param pTagDTO the tag to modify in the database
     * @return the TagDTO once it is modified
     * @throws JrafEnterpriseException exception JRAF
     */
    public static TagDTO modifyTag ( TagDTO pTagDTO )
        throws JrafEnterpriseException
    {
        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        TagDTO tagDTOModified = null;
        
        //Variables temporaires
        TagBO tagBO = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagDAOImpl tagDAO = TagDAOImpl.getInstance();

            tagBO = tagDAO.modifyTag( session, TagTransform.dto2Bo( pTagDTO ) );

            if ( tagBO != null )
            {
                tagDTOModified = TagTransform.bo2Dto( tagBO );
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".modifyTag", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".modifyTag" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return tagDTOModified;
    }
    
    /**
     * deletes one or more TagBOs from the database with the naves given as a parameter
     * 
     * @param pNamesToDelete a collection of Strings of the names of the tags to delete
     * @return boolean if the number of removals from the database equals the number wanted
     * @throws JrafEnterpriseException exception JRAF
     */
    public static boolean deleteTags ( Collection<String> pNamesToDelete )
        throws JrafEnterpriseException
    {
        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        boolean ok = false;
        
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagDAOImpl tagDAO = TagDAOImpl.getInstance();

            ok = tagDAO.deleteTags( session, pNamesToDelete );

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".deleteTags", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".deleteTags" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return new Boolean(ok);
    }
    
    /**
     * deletes one or more TagCategoryBOs from the database with the naves given as a parameter
     * 
     * @param pNamesToDelete a collection of Strings of the names of the tags to delete
     * @return boolean if the number of removals from the database equals the number wanted
     * @throws JrafEnterpriseException exception JRAF
     */
    public static boolean deleteTagCategories ( Collection<String> pNamesToDelete )
        throws JrafEnterpriseException
    {
        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        boolean ok = false;
        
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagCategoryDAOImpl tagCategoryDAO = TagCategoryDAOImpl.getInstance();

            ok = tagCategoryDAO.deleteTagCategories( session, pNamesToDelete );

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".deleteTagCategories", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".deleteTagCategories" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return new Boolean(ok);
    }
    
    /**
     * creates a TagCategoryBO in the database from a given tagCategoryDTO
     * 
     * @param pTagCategoryDTO the tag to create in the database
     * @return the TagCategoryDTO once it is created
     * @throws JrafEnterpriseException exception JRAF
     */
    public static TagCategoryDTO createTagCategory ( TagCategoryDTO pTagCategoryDTO )
        throws JrafEnterpriseException
    {
        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        TagCategoryDTO tagCategoryDTOCreated = null;
        
        //Variables temporaires
        TagCategoryBO tagCategoryBO = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagCategoryDAOImpl tagCategoryDAO = TagCategoryDAOImpl.getInstance();

            tagCategoryBO = tagCategoryDAO.createTagCategory( session, TagCategoryTransform.dto2Bo( pTagCategoryDTO ) );

            if ( tagCategoryBO != null )
            {
                tagCategoryDTOCreated = TagCategoryTransform.bo2Dto( tagCategoryBO );
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".createTagCategory", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".createTagCategory" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return tagCategoryDTOCreated;
    }
    
    /**
     * modifies a TagCategoryBO in the database from a given tagCategoryDTO
     * 
     * @param pTagCategoryDTO the tag category to modify in the database
     * @return the TagCategoryDTO once it is modified
     * @throws JrafEnterpriseException exception JRAF
     */
    public static TagCategoryDTO modifyTagCategory ( TagCategoryDTO pTagCategoryDTO )
        throws JrafEnterpriseException
    {
        LOG.debug( CommonMessages.getString( "method.entry" ) );
        // Initialisation du retour
        TagCategoryDTO tagCategoryDTOModified = null;
        
        //Variables temporaires
        TagCategoryBO tagCategoryBOBefore = null;
        TagCategoryBO tagCategoryBOAfter = null;

        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();

            TagCategoryDAOImpl tagCategoryDAO = TagCategoryDAOImpl.getInstance();
            TagDAOImpl tagDAO = TagDAOImpl.getInstance();
            
            //use of a tagCategoryBefor and after to prevent the loss of associated tags with the tagcategory
            tagCategoryBOBefore = TagCategoryTransform.dto2Bo( pTagCategoryDTO );
            
            //we manualy attach the collection of tags possessing the current category
            tagCategoryBOBefore.setTags( tagDAO.findCategoryTags( session, tagCategoryBOBefore) );

            //the category is then modified in the database
            tagCategoryBOAfter = tagCategoryDAO.modifyTagCategory( session, tagCategoryBOBefore );

            if ( tagCategoryBOAfter != null )
            {
                tagCategoryDTOModified = TagCategoryTransform.bo2Dto( tagCategoryBOAfter );
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.tagfacade.get.tagnull" ) );
            }

        }
        catch ( JrafDaoException e )
        {
            LOG.error( TagFacade.class.getName() + ".modifyTagCategory", e );
        }
        finally
        {
            FacadeHelper.closeSession( session, TagFacade.class.getName() + ".modifyTagCategory" );
        }

        LOG.debug( CommonMessages.getString( "method.exit" ) );
        return tagCategoryDTOModified;
    }
}
