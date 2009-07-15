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
package com.airfrance.squaleweb.applicationlayer.action.tag;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.tag.TagCategoryDTO;
import com.airfrance.squalecommon.datatransfertobject.tag.TagDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ServeurListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateTagForm;
import com.airfrance.squaleweb.applicationlayer.formbean.tag.TagCategoryForm;
import com.airfrance.squaleweb.applicationlayer.formbean.tag.TagForm;
import com.airfrance.squaleweb.transformer.ServeurListTransformer;
import com.airfrance.squaleweb.transformer.TagCategoryTransformer;
import com.airfrance.squaleweb.transformer.TagTransformer;
import com.airfrance.squaleweb.transformer.TagsListTransformer;
import com.airfrance.squaleweb.util.InputFieldDataChecker;
import com.airfrance.welcom.struts.ajax.WHttpEasyCompleteResponse;
import com.airfrance.welcom.struts.easycomplete.WEasyCompleteUtil;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 */
public class ManageTagsAction
    extends ReaderAction
{

    /** Constants for the form reset */
    private static final int TAGS = 0;

    /** Constants for the form reset */
    private static final int TAG_CATEGORIES = 1;

    /** Constants for the form reset */
    private static final int BOTH = 2;

    /**
     * Affiche une liste des tags courant du système
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward showTags( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                   HttpServletResponse pResponse )
    {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try
        {
            // On récupère la liste des tags en base
            resetForm( (CreateTagForm) pForm, BOTH );
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
            Object[] params = new Object[] {};
            Collection<TagDTO> tags = (Collection<TagDTO>) ac.execute( "getTags", params );

            Collection<TagCategoryDTO> tagCategories =
                (Collection<TagCategoryDTO>) ac.execute( "getTagCategories", params );

            WTransformerFactory.objToForm( TagsListTransformer.class, (CreateTagForm) pForm, new Object[] { tags,
                tagCategories } );

            forward = pMapping.findForward( "tag_list" );
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            // Transfert vers la page d'erreur
            forward = pMapping.findForward( "total_failure" );
        }
        if ( !errors.isEmpty() )
        {
            // On sauvegarde les erreurs pour les afficher sur la page initiale
            saveMessages( pRequest, errors );
            // On renvoie vers la page initiale avec message d'erreur
            try
            {
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "Serveur" );
                Collection lListeServeurDTO = (Collection) ac.execute( "listeServeurs" );
                ServeurListForm lListeServeurForm = new ServeurListForm();
                WTransformerFactory.objToForm( ServeurListTransformer.class, lListeServeurForm, lListeServeurDTO );
                pRequest.setAttribute( "listeServeur", lListeServeurForm );
                forward = pMapping.findForward( "config_application" );
            }
            catch ( Exception e )
            {
                // Traitement factorisé des exceptions
                handleException( e, errors, pRequest );
            }
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Creates a tag for future usage
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward addTag( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                 HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            String tagName = form.getName();
            String tagDescription = form.getDescription();
            String tagCategoryName = form.getCategoryName();

            Collection<TagDTO> formTags = modifyTagFormList( (Collection<TagForm>) form.getTags() );
            Collection<TagCategoryDTO> formTagCategories = modifyTagCategoryFormList( form.getTagCategories() );

            TagCategoryDTO tagCategory = retrieveTagCategory( tagCategoryName );

            TagDTO tagToCreate = new TagDTO();
            tagToCreate.setName( tagName );
            tagToCreate.setDescription( tagDescription );
            if ( tagCategory != null )
            {
                tagToCreate.setTagCategoryDTO( tagCategory );
            }
            else if ( tagCategory == null && tagCategoryName != null && !"".equals( tagCategoryName ) )
            {
                // If the category returned from the database is empty but the name wanted for the category to add on
                // the tag, is not, we have a probleme on the category name
                form.setPbTagCategory( true );
                form.setPbTagCategoryName( tagCategoryName );
            }
            if ( !form.isPbTagCategory() )
            {
                if ( tagName != null && !"".equals( tagName ) && InputFieldDataChecker.TAG_NAME.check( tagName ) )
                {
                    // The tag is created only if there is no problem on the tag category wanted
                    TagDTO createdTag = createTag( tagToCreate );

                    formTags.add( createdTag );

                    WTransformerFactory.objToForm( TagsListTransformer.class, (CreateTagForm) pForm, new Object[] {
                        formTags, formTagCategories } );

                    resetForm( form, TAGS );
                }
                else
                {
                    //The name of the tag is not valide user must choose another one
                    form.setPbTag( true );
                    form.setPbTagName( tagName );
                }
            }

            forward = pMapping.findForward( "reload" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Creates a tag for future usage
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward modifyTag( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                    HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            // the new values of the tag are retrieved
            String newTagName = form.getName();
            String newTagDescription = form.getDescription();
            String newTagCategoryName = form.getCategoryName();
            String tagIndex = form.getTagIndex();

            // the tag with the old values is also retrieved from the list of tags in the form
            TagForm tagForm2Modify = ( (ArrayList<TagForm>) form.getTags() ).get( Integer.parseInt( tagIndex ) );
            String oldTagName = tagForm2Modify.getName();

            Collection<TagDTO> formTags = modifyTagFormList( (Collection<TagForm>) form.getTags() );
            Collection<TagCategoryDTO> formTagCategories = modifyTagCategoryFormList( form.getTagCategories() );

            TagDTO tag2Modify = retrieveTag( oldTagName );

            tag2Modify.setName( newTagName );
            tag2Modify.setDescription( newTagDescription );
            TagCategoryDTO newTagCategory = findNewTagCategory( tagForm2Modify.getCategoryForm(), newTagCategoryName );
            // if the new Category is null but the input category is not null then the input category does not exist
            // => error
            if ( newTagCategory == null && newTagCategoryName != null && !"".equals( newTagCategory ) )
            {
                form.setPbTagCategory( true );
                form.setPbTagCategoryName( newTagCategoryName );
            }
            else
            {
                if ( newTagName != null && !"".equals( newTagName ) && InputFieldDataChecker.TAG_NAME.check( newTagName ) )
                {
                    tag2Modify.setTagCategoryDTO( newTagCategory );
    
                    TagDTO modifiedTag = modifyDatabaseTag( tag2Modify );
    
                    WTransformerFactory.objToForm( TagsListTransformer.class, (CreateTagForm) pForm, new Object[] {
                        formTags, formTagCategories } );
    
                    resetForm( form, TAGS );
                }
                else
                {
                    //The name of the tag is not valide, the user must choose another one
                    form.setPbTag( true );
                    form.setPbTagName( newTagName );
                }
            }

            forward = pMapping.findForward( "reload" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Fills the fields of the form with the values of the tag to modidy
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward selectTagToModify( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                            HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            int index = Integer.parseInt( (String) pRequest.getParameter( "index" ) );

            TagForm tagForm2Modify = ( (ArrayList<TagForm>) form.getTags() ).get( index );

            form.setName( tagForm2Modify.getName() );
            form.setDescription( tagForm2Modify.getDescription() );
            if ( tagForm2Modify.getCategoryForm() != null )
            {
                form.setCategoryName( tagForm2Modify.getCategoryForm().getName() );
            }
            form.setTagIndex( "" + index );

            // sets a boolean to change the form buttons
            form.setTagModified( true );

            forward = pMapping.findForward( "tag_list" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Creates a tagCategory for future usage
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward modifyTagCategory( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                            HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            // the new values of the tag are retrieved
            String newTagCategoryName = form.getTagCatName();
            String newTagCategoryDescription = form.getTagCatDescription();
            String tagCategoryIndex = form.getTagCategoryIndex();

            // the tag with the old values is also retrieved
            TagCategoryForm tagCategoryForm2Modify =
                ( (ArrayList<TagCategoryForm>) form.getTagCategories() ).get( Integer.parseInt( tagCategoryIndex ) );
            String oldTagCategoryName = tagCategoryForm2Modify.getName();

            Collection<TagDTO> formTags = modifyTagFormList( (Collection<TagForm>) form.getTags() );
            Collection<TagCategoryDTO> formTagCategories = modifyTagCategoryFormList( form.getTagCategories() );

            if ( newTagCategoryName != null && !"".equals( newTagCategoryName ) && InputFieldDataChecker.TAG_NAME.check( newTagCategoryName ) )
            {
                // the tagCategory to modify is retrieved from the database with old name
                TagCategoryDTO tagCategory2Modify = retrieveTagCategory( oldTagCategoryName );
    
                tagCategory2Modify.setName( newTagCategoryName );
                tagCategory2Modify.setDescription( newTagCategoryDescription );
    
                TagCategoryDTO modifiedTagCategory = modifyDatabaseTagCategory( tagCategory2Modify );
    
                // formTagCategories = replaceOldTagCategory( oldTagCategoryName, modifiedTagCategory, formTagCategories );
                // formTags = replaceOldTagCategoryName( oldTagCategoryName, modifiedTagCategory, formTags );
    
                WTransformerFactory.objToForm( TagsListTransformer.class, (CreateTagForm) pForm, new Object[] { formTags,
                    formTagCategories } );
    
                resetForm( form, TAG_CATEGORIES );
            }
            else
            {
                //The name of the category is not valide, the user must choose another one
                form.setPbCategory( true );
                form.setPbCategoryName( newTagCategoryName );
            }

            forward = pMapping.findForward( "reload" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Fills the fields of the form with the values of the tag category to modify
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward selectTagCategoryToModify( ActionMapping pMapping, ActionForm pForm,
                                                    HttpServletRequest pRequest, HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            int index = Integer.parseInt( (String) pRequest.getParameter( "index" ) );

            TagCategoryForm tagCategoryForm2Modify =
                ( (ArrayList<TagCategoryForm>) form.getTagCategories() ).get( index );

            form.setTagCatName( tagCategoryForm2Modify.getName() );
            form.setTagCatDescription( tagCategoryForm2Modify.getDescription() );

            form.setTagCategoryIndex( "" + index );

            // sets a boolean to change the form buttons
            form.setTagCategoryModified( true );

            forward = pMapping.findForward( "tag_list" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * resets the fields of the form with the values of the tag to modidy
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward resetModifyTag( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                         HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            resetForm( form, TAGS );

            forward = pMapping.findForward( "tag_list" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * resets the fields of the form with the values of the tag category to modify
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward resetModifyTagCategory( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                                 HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            resetForm( form, TAG_CATEGORIES );

            forward = pMapping.findForward( "tag_list" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Deletes a tag from the database
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward deleteTag( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                    HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            List<String> namesToSuppress = new ArrayList<String>();

            for ( TagForm tagForm : (Collection<TagForm>) form.getTags() )
            {
                if ( tagForm.isSelected() )
                {
                    namesToSuppress.add( tagForm.getName() );
                }
            }

            Collection<TagDTO> formTags = new ArrayList<TagDTO>();
            Collection<TagCategoryDTO> formTagCategories = modifyTagCategoryFormList( form.getTagCategories() );

            boolean ok = deleteDatabaseTags( namesToSuppress );

            if ( ok )
            {
                for ( TagForm tagForm : (Collection<TagForm>) form.getTags() )
                {
                    if ( !namesToSuppress.contains( tagForm.getName() ) )
                    {
                        formTags.add( (TagDTO) WTransformerFactory.formToObj( TagTransformer.class, tagForm )[0] );
                    }
                }

                WTransformerFactory.objToForm( TagsListTransformer.class, (CreateTagForm) pForm, new Object[] {
                    formTags, formTagCategories } );

                forward = pMapping.findForward( "tag_list" );
            }
            else
            {
                throw new Exception();
            }
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Deletes a tag category from the database and the tags that reference the category
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward deleteTagCategoryAndTags( ActionMapping pMapping, ActionForm pForm,
                                                   HttpServletRequest pRequest, HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            List<String> namesCategoriesToSuppress = new ArrayList<String>();
            List<String> namesTagsToSuppress = new ArrayList<String>();

            for ( TagCategoryForm tagCategoryForm : form.getTagCategories() )
            {
                if ( tagCategoryForm.isSelected() )
                {
                    // if the category has been selected to be deleted
                    namesCategoriesToSuppress.add( tagCategoryForm.getName() );
                }
            }
            for ( TagForm tagForm : (Collection<TagForm>) form.getTags() )
            {
                // if the tag posesses a category
                if ( tagForm.getCategoryForm() != null )
                {
                    // if the category of the tag is the one to be deleted
                    if ( namesCategoriesToSuppress.contains( tagForm.getCategoryForm().getName() ) )
                    {
                        // the tag is prepared to be deleted
                        namesTagsToSuppress.add( tagForm.getName() );
                    }
                }
            }

            // First the tags are deleted
            boolean ok = deleteDatabaseTags( namesTagsToSuppress );
            if ( ok )
            {
                // if ok, then the categories are deleted
                ok = deleteDatabaseTagCategories( namesCategoriesToSuppress );
            }

            if ( ok )
            {
                forward = pMapping.findForward( "reload" );
            }
            else
            {
                throw new Exception();
            }
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Deletes a tag category from the database but keeps the tags. Only the reference of the tag category is deleted
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward deleteTagCategoryNotTags( ActionMapping pMapping, ActionForm pForm,
                                                   HttpServletRequest pRequest, HttpServletResponse pResponse )
    {
        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            List<String> namesToSuppress = new ArrayList<String>();

            for ( TagCategoryForm tagCategoryForm : form.getTagCategories() )
            {
                if ( tagCategoryForm.isSelected() )
                {
                    namesToSuppress.add( tagCategoryForm.getName() );
                }
            }

            Collection<TagDTO> formTags = new ArrayList<TagDTO>();

            for ( TagForm tagForm : (Collection<TagForm>) form.getTags() )
            {
                if ( tagForm.getCategoryForm() != null )
                {
                    if ( namesToSuppress.contains( tagForm.getCategoryForm().getName() ) )
                    {
                        TagDTO tag = (TagDTO) WTransformerFactory.formToObj( TagTransformer.class, tagForm )[0];
                        // Manually remove the tag category from the tags
                        tag.setTagCategoryDTO( null );
                        formTags.add( tag );
                    }
                }
            }

            for ( TagDTO tagDTO : formTags )
            {
                // modify the database tags without the category
                modifyDatabaseTag( tagDTO );
            }
            // delete the categories from the database
            boolean ok = deleteDatabaseTagCategories( namesToSuppress );

            if ( ok )
            {
                forward = pMapping.findForward( "reload" );
            }
            else
            {
                throw new Exception();
            }
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Creates a tagCategory for future usage
     * 
     * @param pMapping the mapping.
     * @param pForm the Form to read.
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the action called.
     */
    public ActionForward addTagCategory( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                         HttpServletResponse pResponse )
    {

        ActionForward forward = null;

        try
        {
            CreateTagForm form = (CreateTagForm) pForm;
            resetFormErrors( form );
            String tagCatName = form.getTagCatName();
            String tagCatDescription = form.getTagCatDescription();

            Collection<TagDTO> formTags = modifyTagFormList( (Collection<TagForm>) form.getTags() );
            Collection<TagCategoryDTO> formTagCategories = modifyTagCategoryFormList( form.getTagCategories() );

            if ( tagCatName != null && !"".equals( tagCatName ) && InputFieldDataChecker.TAG_NAME.check( tagCatName ) )
            {
                TagCategoryDTO tagCategoryToCreate = new TagCategoryDTO();
                tagCategoryToCreate.setName( tagCatName );
                tagCategoryToCreate.setDescription( tagCatDescription );

                TagCategoryDTO createdTagCategory = createTagCategory(tagCategoryToCreate);

                formTagCategories.add( createdTagCategory );

                WTransformerFactory.objToForm( TagsListTransformer.class, (CreateTagForm) pForm, new Object[] {
                    formTags, formTagCategories } );

                resetForm( form, TAG_CATEGORIES );
            }
            else
            {
                //The name of the category is not valide, the user must choose another one
                form.setPbCategory( true );
                form.setPbCategoryName( tagCatName );
            }

            forward = pMapping.findForward( "reload" );
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Traitement factorisé des erreurs
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return forward;
    }

    /**
     * Method called to retrieved the new wanted tag category from the two possible categories set in parameter
     * 
     * @param pOldTagCategoryForm Category Form of the old tag
     * @param pNewTagCategory Name of the possibly new Tag Category
     * @return tagCategory the new wanted tag category or null if it was not found
     * @throws JrafEnterpriseException if an error occurs while accessing the database
     */
    private TagCategoryDTO findNewTagCategory( TagCategoryForm pOldTagCategoryForm, String pNewTagCategory )
        throws JrafEnterpriseException
    {

        String oldCategoryName = pOldTagCategoryForm == null ? "" : pOldTagCategoryForm.getName();
        String newCategoryName = pNewTagCategory == null ? "" : pNewTagCategory;

        // Comparing the two category names
        if ( newCategoryName.equals( oldCategoryName ) )
        {
            // if the names are empty
            if ( "".equals( oldCategoryName ) )
            {
                // returns null
                return null;
            }
            else
            {
                // return the retrieved tagCategory from the database
                return retrieveTagCategory( oldCategoryName );
            }
        }
        else
        {
            // returns the retrieved tagCategory of the newTagCategoryName
            return retrieveTagCategory( newCategoryName );
        }
    }

    /**
     * Method called to retrieved the wanted tag category from the collection obtained from the database
     * 
     * @param pTagCategoryName name of the wanted category
     * @return tagCategory the retrieved tag category or null if it was not found
     * @throws JrafEnterpriseException if an exception occurs
     */
    private TagCategoryDTO retrieveTagCategory( String pTagCategoryName )
        throws JrafEnterpriseException
    {

        TagCategoryDTO tagCategory = null;

        // On récupère la liste des tags en base
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
        Object[] params = new Object[] { pTagCategoryName };
        Collection<TagCategoryDTO> tagCategories =
            (Collection<TagCategoryDTO>) ac.execute( "getTagCategoriesByName", params );

        for ( TagCategoryDTO tagCategoryDTO : tagCategories )
        {
            if ( pTagCategoryName != null && pTagCategoryName.equals( tagCategoryDTO.getName() ) )
            {
                tagCategory = tagCategoryDTO;
            }
        }

        return tagCategory;
    }

    /**
     * Method called to retrieved the wanted tag from the collection obtained from the database
     * 
     * @param pTagName name of the wanted category
     * @return tag the retrieved tag or null if it was not found
     * @throws JrafEnterpriseException if an error occurs
     */
    private TagDTO retrieveTag( String pTagName )
        throws JrafEnterpriseException
    {

        TagDTO tag = null;
        // the tag to modify is retrieved from the database with old name
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
        Object[] params = new Object[] { new String[] { pTagName } };
        Collection<TagDTO> pTags = (Collection<TagDTO>) ac.execute( "getTagsByName", params );

        for ( TagDTO tagDTO : pTags )
        {
            if ( pTagName != null && pTagName.equals( tagDTO.getName() ) )
            {
                tag = tagDTO;
            }
        }

        return tag;
    }

    /**
     * Method called to create a Tag from a TagDTO
     * 
     * @param pTagDTO TagDTO to insert in the database
     * @return tagDTO corresponding to the object inserted in the database
     * @throws JrafEnterpriseException if an error occurs
     */
    private TagDTO createTag( TagDTO pTagDTO )
        throws JrafEnterpriseException
    {

        TagDTO tag = null;

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
        Object[] params = new Object[] { pTagDTO };
        tag = (TagDTO) ac.execute( "createTag", params );

        return tag;
    }
    
    /**
     * Method called to create a TagCategory from a TagCategoryDTO
     *  
     * @param pTagCategoryDTO TagCategoryDTO to insert in the database
     * @return tagCategoryDTO corresponding to the object inserted in the database
     * @throws JrafEnterpriseException if an error occurs
     */
    private TagCategoryDTO createTagCategory( TagCategoryDTO pTagCategoryDTO )
        throws JrafEnterpriseException
    {

        TagCategoryDTO tagCategory = null;

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
        Object[] params = new Object[] { pTagCategoryDTO };
        tagCategory = (TagCategoryDTO) ac.execute( "createTagCategory", params );

        return tagCategory;
    }

    /**
     * Method called to create a TagDTO list from a TagForm list
     * 
     * @param pTagFormList The list of TagForm contain in the CreateTagForm
     * @return tagDTOs corresponding list but of TagDTO objects
     * @throws WTransformerException if an error occurs
     */
    private Collection<TagDTO> modifyTagFormList( Collection<TagForm> pTagFormList )
        throws WTransformerException
    {

        Collection<TagDTO> tagDTOs = new ArrayList<TagDTO>();

        for ( TagForm tagForm : pTagFormList )
        {
            tagDTOs.add( (TagDTO) WTransformerFactory.formToObj( TagTransformer.class, tagForm )[0] );
        }

        return tagDTOs;
    }

    /**
     * Method called to create a TagCategoryDTO list from a TagCategoryForm list
     * 
     * @param pTagCategoryFormList The list of TagCategoryForm contain in the CreateTagForm
     * @return tagCatgoryDTOs corresponding list but of TagCategoryDTO objects
     * @throws WTransformerException if an error occurs
     */
    private Collection<TagCategoryDTO> modifyTagCategoryFormList( Collection<TagCategoryForm> pTagCategoryFormList )
        throws WTransformerException
    {

        Collection<TagCategoryDTO> tagCategoryDTOs = new ArrayList<TagCategoryDTO>();

        for ( TagCategoryForm tagCategoryForm : pTagCategoryFormList )
        {
            tagCategoryDTOs.add( (TagCategoryDTO) WTransformerFactory.formToObj( TagCategoryTransformer.class,
                                                                                 tagCategoryForm )[0] );
        }

        return tagCategoryDTOs;
    }

    /**
     * Method called to modify a tag in the database
     * 
     * @param pTagToModify the TagDTO
     * @return returnedTag tagDTO returned from the database once it has been modified
     * @throws JrafEnterpriseException if an error occurs
     */
    private TagDTO modifyDatabaseTag( TagDTO pTagToModify )
        throws JrafEnterpriseException
    {

        TagDTO returnedTag = new TagDTO();
        Object[] params = new Object[] { pTagToModify };

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
        returnedTag = (TagDTO) ac.execute( "modifyTag", params );

        return returnedTag;
    }

    /**
     * Method called to modify a tag Category in the database
     * 
     * @param pTagCategoryToModify the TagCategoryDTO
     * @return returnedTagCategory tagCategoryDTO returned from the database once it has been modified
     * @throws JrafEnterpriseException if an error occurs
     */
    private TagCategoryDTO modifyDatabaseTagCategory( TagCategoryDTO pTagCategoryToModify )
        throws JrafEnterpriseException
    {

        TagCategoryDTO returnedTagCategory = new TagCategoryDTO();
        Object[] params = new Object[] { pTagCategoryToModify };

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
        returnedTagCategory = (TagCategoryDTO) ac.execute( "modifyTagCategory", params );

        return returnedTagCategory;
    }

    /**
     * Method called to delete one or more tag from the database
     * 
     * @param pTagNamesToDelete the Tag names to delete
     * @return ok boolean indicating whether the delete in the database went well or not
     * @throws JrafEnterpriseException if an error occurs
     */
    private boolean deleteDatabaseTags( List<String> pTagNamesToDelete )
        throws JrafEnterpriseException
    {
        boolean ok = false;
        if (pTagNamesToDelete != null && pTagNamesToDelete.size()>0){
            Object[] params = new Object[] { pTagNamesToDelete };
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
            ok = (Boolean) ac.execute( "deleteTags", params );
        } else {
            ok = true;
        }

        return ok;
    }

    /**
     * Method called to delete one or more tagCategory from the database
     * 
     * @param pTagNamesToDelete the Tag Category names to delete
     * @return ok boolean indicating whether the delete in the database went well or not
     * @throws JrafEnterpriseException if an error occurs
     */
    private boolean deleteDatabaseTagCategories( List<String> pTagNamesToDelete )
        throws JrafEnterpriseException
    {
        boolean ok = false;
        if (pTagNamesToDelete != null && pTagNamesToDelete.size()>0){
            Object[] params = new Object[] { pTagNamesToDelete };
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "TagAdmin" );
            ok = (Boolean) ac.execute( "deleteTagCategories", params );
        } else {
            ok = true;
        }
        return ok;
    }

    /**
     * Method called to fill the suggest input field when adding a tag to an application. The "ch" parameter passed
     * along the request gives the string that the user has just typed in.
     * 
     * @param pMapping the mapping.Method called to add a chosen tag to the application
     * @param pForm the form
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return null (because the response is an XML stream)
     */
    public ActionForward findTagCategoryForAutocomplete( final ActionMapping pMapping, final ActionForm pForm,
                                                         final HttpServletRequest pRequest,
                                                         final HttpServletResponse pResponse )
    {
        // retrieves the string that the user has just typed in
        String stringFirstChars = pRequest.getParameter( "ch" );
        stringFirstChars = WEasyCompleteUtil.filter( stringFirstChars );

        // create the response object
        WHttpEasyCompleteResponse easyComplete = new WHttpEasyCompleteResponse( pResponse );

        // and fill it with the users' information

        /* -------------------------------------------------------------- */
        // This is a example code snippet used to test the suggest field
        // This must be encapsulated in a wider generic security API
        /* -------------------------------------------------------------- */
        // searching Tag is available only when the string is 3 characters long
        if ( stringFirstChars.length() > 2 )
        {
            IApplicationComponent ac;
            try
            {
                ac = AccessDelegateHelper.getInstance( "TagAdmin" );
                Object[] paramIn = { stringFirstChars };
                Collection<TagCategoryDTO> tagCategories =
                    ( (Collection<TagCategoryDTO>) ac.execute( "getTagCategoriesByName", paramIn ) );
                for ( TagCategoryDTO tagCategoryDTO : tagCategories )
                {
                    String value = tagCategoryDTO.getName();
                    String label = tagCategoryDTO.getName();
                    easyComplete.addValueLabel( value, label );
                }
            }
            catch ( JrafEnterpriseException e )
            {
                e.printStackTrace();
            }
        }

        // TODO : need to see why only the 10 first results are displayed...
        /* ------------------------------------------------------ */

        try
        {
            easyComplete.close();
        }
        catch ( IOException e )
        {
            // there's nothing we can do about it, forget it
        }

        return null;
    }

    // /**
    // * Method that replaces the tag just modified in a list with the old tags
    // *
    // * @param pOldNameTag the name of the tag to replace with the new one
    // * @param pNewTag the tagDTO that was just modified
    // * @param pTagCollection the collection of tag that needs to be modified
    // * @return the modified tagCollection
    // */
    // private Collection<TagDTO> replaceOldTag( String pOldNameTag, TagDTO pNewTag, Collection<TagDTO> pTagCollection )
    // {
    //
    // Collection<TagDTO> newTagCollection = new ArrayList<TagDTO>();
    // // the modified Tag will be added to the form, instead of the previous tag
    // for ( TagDTO oldTag : pTagCollection )
    // {
    // if ( pOldNameTag.equals( oldTag.getName() ) )
    // {
    // newTagCollection.add( pNewTag );
    // }
    // else
    // {
    // newTagCollection.add( oldTag );
    // }
    // }
    //
    // return newTagCollection;
    // }

    // /**
    // * Method that replaces the tag Category just modified in a list with the old tagCategories
    // *
    // * @param pOldNameTagCategory the name of the tagCategory to replace with the new one
    // * @param pNewTagCategory the tagCategoryDTO that was just modified
    // * @param pTagCategoryCollection the collection of tagCategories that needs to be modified
    // * @return the modified tagCategoryCollection
    // */
    // private Collection<TagCategoryDTO> replaceOldTagCategory( String pOldNameTagCategory,
    // TagCategoryDTO pNewTagCategory,
    // Collection<TagCategoryDTO> pTagCategoryCollection )
    // {
    //
    // Collection<TagCategoryDTO> newTagCategoryCollection = new ArrayList<TagCategoryDTO>();
    // // the modified Tag will be added to the form, instead of the previous tag
    // for ( TagCategoryDTO oldTagCategory : pTagCategoryCollection )
    // {
    // if ( pOldNameTagCategory.equals( oldTagCategory.getName() ) )
    // {
    // newTagCategoryCollection.add( pNewTagCategory );
    // }
    // else
    // {
    // newTagCategoryCollection.add( oldTagCategory );
    // }
    // }
    //
    // return newTagCategoryCollection;
    // }

    // /**
    // * Method that replaces the tag Category name of the tagCategory just modified in a list with the old tags
    // *
    // * @param pOldNameTagCategory the name of the tagCategory to replace with the new one
    // * @param pNewTagCategory the tagCategoryDTO that was just modified
    // * @param pTagCollection the collection of tags that needs to be modified
    // * @return the modified tagCollection with the tagCategory names possibly updated
    // */
    // private Collection<TagDTO> replaceOldTagCategoryName( String pOldNameTagCategory, TagCategoryDTO pNewTagCategory,
    // Collection<TagDTO> pTagCollection )
    // {
    //
    // Collection<TagDTO> newTagCollection = new ArrayList<TagDTO>();
    // // the modified Tag will be added to the form, instead of the previous tag
    // for ( TagDTO tag : pTagCollection )
    // {
    // if ( tag.getTagCategoryDTO() != null )
    // {
    // if ( tag.getTagCategoryDTO().getName() != null )
    // {
    // if ( pOldNameTagCategory.equals( tag.getTagCategoryDTO().getName() ) )
    // {
    // // Sets the new name of the category on the tag list
    // tag.getTagCategoryDTO().setName( pNewTagCategory.getName() );
    // newTagCollection.add( tag );
    // }
    // else
    // {
    // newTagCollection.add( tag );
    // }
    // }
    // else
    // {
    // tag.getTagCategoryDTO().setName( "-" );
    // newTagCollection.add( tag );
    // }
    // }
    // else
    // {
    // newTagCollection.add( tag );
    // }
    // }
    //
    // return newTagCollection;
    // }

    // /**
    // * Method that removes the tag Category just deleted from the list in the form
    // *
    // * @param pNamesToSuppress the names of the tagCategories to be removed from the list
    // * @param form the form containing the old list of tag categories
    // * @return the modified tagCategoryCollection
    // * @throws WTransformerException if an error occurs during the transformation
    // */
    // private Collection<TagCategoryDTO> removeOldTagCategories( List<String> pNamesToSuppress, CreateTagForm form )
    // throws WTransformerException
    // {
    //
    // Collection<TagCategoryDTO> newTagCategoryCollection = new ArrayList<TagCategoryDTO>();
    // // the removed Tag will not be added to the form
    // for ( TagCategoryForm tagCategoryForm : form.getTagCategories() )
    // {
    // if ( !pNamesToSuppress.contains( tagCategoryForm.getName() ) )
    // {
    // newTagCategoryCollection.add( (TagCategoryDTO) WTransformerFactory.formToObj(
    // TagCategoryTransformer.class,
    // tagCategoryForm )[0] );
    // }
    // }
    //
    // return newTagCategoryCollection;
    // }

    // /**
    // * Method that removes the tag Category name from the tag when the tagCategory has just been deleted
    // *
    // * @param pNamesToSuppress the names of the tagCategories to be removed from the list
    // * @param pTagCollection the collection of tags that needs to be modified
    // * @return the modified tagCollection with the tagCategory names possibly updated
    // */
    // private Collection<TagDTO> removesOldTagCategoryName( List<String> pNamesToSuppress,
    // Collection<TagDTO> pTagCollection )
    // {
    //
    // Collection<TagDTO> newTagCollection = new ArrayList<TagDTO>();
    // // the modified Tag will be added to the form, instead of the previous tag
    // for ( TagDTO tag : pTagCollection )
    // {
    // if ( tag.getTagCategoryDTO() != null )
    // {
    // if ( tag.getTagCategoryDTO().getName() != null )
    // {
    // if ( pNamesToSuppress.contains( tag.getTagCategoryDTO().getName() ) )
    // {
    // // Sets the new name of the category on the tag list
    // tag.getTagCategoryDTO().setName( "-" );
    // newTagCollection.add( tag );
    // }
    // else
    // {
    // newTagCollection.add( tag );
    // }
    // }
    // else
    // {
    // tag.getTagCategoryDTO().setName( "-" );
    // newTagCollection.add( tag );
    // }
    // }
    // else
    // {
    // newTagCollection.add( tag );
    // }
    // }
    //
    // return newTagCollection;
    // }

    // /**
    // * Method that places the character "-" if the name of the tagcategory is null
    // *
    // * @param pForm the current CreateTagForm
    // */
    // private void replaceNull( CreateTagForm pForm )
    // {
    // for ( TagForm tag : pForm.getTags() )
    // {
    // if ( tag.getCategoryForm() != null )
    // {
    // if ( tag.getCategoryForm().getName() == null )
    // {
    // tag.getCategoryForm().setName( "-" );
    // }
    // }
    // else
    // {
    // TagCategoryForm tagCatForm = new TagCategoryForm();
    // tagCatForm.setName( "-" );
    // tag.setCategoryForm( tagCatForm );
    // }
    // }
    // }

    /**
     * Method that resets the input errors
     * 
     * @param pForm the form to reset
     * @throws WTransformerException if an error occurs during the transformation
     */
    private void resetFormErrors( CreateTagForm pForm )
        throws WTransformerException
    {
        // the error on the category if it existed is removed
        pForm.setPbTagCategory( false );
        pForm.setPbTagCategoryName( "" );
        pForm.setPbTag( false );
        pForm.setPbTagName( "" );
        pForm.setPbCategory( false );
        pForm.setPbCategoryName( "" );
    }

    /**
     * Method that resets the form when needed (all the fields are set to "")
     * 
     * @param pForm the form to reset
     * @param pNumForm constant to indicate whiche form to reset 0 : tags 1 : tagCategories 2 : both
     * @throws WTransformerException if an error occurs during the transformation
     */
    private void resetForm( CreateTagForm pForm, int pNumForm )
        throws WTransformerException
    {

        if ( pNumForm == TAGS || pNumForm == BOTH )
        {
            // resets the tag values
            pForm.setName( "" );
            pForm.setDescription( "" );
            pForm.setCategoryName( "" );
            pForm.setTagIndex( "" );
            // sets a boolean to change the form buttons
            pForm.setTagModified( false );
        }

        if ( pNumForm == TAG_CATEGORIES || pNumForm == BOTH )
        {
            // resets the tag category values
            pForm.setTagCatName( "" );
            pForm.setTagCatDescription( "" );
            pForm.setTagCategoryIndex( "" );
            // sets a boolean to change the form buttons
            pForm.setTagCategoryModified( false );
        }

        // replaceNull( pForm );
    }
}
