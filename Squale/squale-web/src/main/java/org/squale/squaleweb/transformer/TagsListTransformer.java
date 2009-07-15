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
package org.squale.squaleweb.transformer;

import java.util.ArrayList;
import java.util.Collection;

import org.squale.squalecommon.datatransfertobject.tag.TagCategoryDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squaleweb.applicationlayer.formbean.creation.CreateTagForm;
import org.squale.squaleweb.applicationlayer.formbean.tag.TagCategoryForm;
import org.squale.squaleweb.applicationlayer.formbean.tag.TagForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une liste de résultats en fonction de facteurs
 */
public class TagsListTransformer
    extends AbstractListTransformer
{

    /**
     * La transformation d'un Objet (DTO) vers le formulaire que l'on va instancier
     * 
     * @param pObject le tableau de ProjectDTO à transformer en formulaires.
     * @throws WTransformerException si un pb apparaît.
     * @return le formulaire associé
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        CreateTagForm form = new CreateTagForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * La transformation d'un Objet (DTO) vers le formulaire
     * 
     * @param pObject le tableau de ProjectDTO à transformer en formulaires.
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparaît.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        Collection<TagDTO> tags = (Collection<TagDTO>) pObject[0];
        Collection<TagCategoryDTO> tagCategories = (Collection<TagCategoryDTO>) pObject[1]; 
        Collection<TagForm> tagsForm = new ArrayList<TagForm>();
        Collection<TagCategoryForm> tagCategoriesForm = new ArrayList<TagCategoryForm>();
        CreateTagForm createTagForm = (CreateTagForm)pForm;
        for ( TagDTO tagDTO : tags )
        {
            tagsForm.add( (TagForm) WTransformerFactory.objToForm( TagTransformer.class, new Object[] { tagDTO } ) );
        }
        for ( TagCategoryDTO tagCategoryDTO : tagCategories )
        {
            tagCategoriesForm.add( (TagCategoryForm) WTransformerFactory.objToForm( TagCategoryTransformer.class, new Object[] { tagCategoryDTO } ) );
        }
        createTagForm.setTags( tagsForm );
        createTagForm.setTagCategories( tagCategoriesForm );
    }

    /**
     * La transformation d'un formulaire vers un object (DTO)
     * 
     * @param pForm le formulaire à lire.
     * @param pObject le tableau de ProjectDTO qui récupère les données du formulaire.
     * @throws WTransformerException si un pb apparaît.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
    }

}
