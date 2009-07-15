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

import org.squale.squalecommon.datatransfertobject.tag.TagCategoryDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squaleweb.applicationlayer.formbean.results.ResultListForm;
import org.squale.squaleweb.applicationlayer.formbean.tag.TagCategoryForm;
import org.squale.squaleweb.applicationlayer.formbean.tag.TagForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformateurs DTO <-> Form pour les applications
 * 
 * @author M400842
 */
public class TagTransformer
    implements WITransformer
{

    /**
     * Object --> Form
     * 
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        TagForm form = new TagForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * Object --> Form
     * 
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        TagDTO dto = (TagDTO) pObject[0];
        TagForm form = (TagForm) pForm;

        form.setId( dto.getId() );
        form.setName( "" + dto.getName() );
        form.setDescription( dto.getDescription() );
        if ( dto.getTagCategoryDTO() != null )
        {
            form.setCategoryForm( (TagCategoryForm) WTransformerFactory.objToForm(
                                                                                   TagCategoryTransformer.class,
                                                                                   new Object[] { dto.getTagCategoryDTO() } ) );
        }
    }

    /**
     * Form --> Object
     * 
     * @param pForm the form to read
     * @return The array of objects
     * @throws WTransformerException if a problem occurs
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new TagDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * Form --> Object
     * 
     * @param pObject the object to fill
     * @param pForm the form to read.
     * @throws WTransformerException if an error occurs.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        TagForm form = (TagForm) pForm;
        TagDTO dto = (TagDTO) pObject[0];
        dto.setId( form.getId() );
        dto.setName( form.getName() );
        dto.setDescription( form.getDescription() );
        if ( form.getCategoryForm() != null )
        {
            dto.setTagCategoryDTO( (TagCategoryDTO) WTransformerFactory.formToObj( TagCategoryTransformer.class,
                                                                                   form.getCategoryForm() )[0] );
        }
    }
}
