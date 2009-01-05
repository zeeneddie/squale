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
package com.airfrance.squaleweb.transformer;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ComponentForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformer ComponentDTO <-> ComponentForm
 * 
 * @author M400842
 */
public class ComponentTransformer
    implements WITransformer
{

    /**
     * @param pObject l'objet à transformer
     * @throws WTransformerException si un pb apparait.
     * @return le formulaire.
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        ComponentForm form = new ComponentForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @param pObject l'objet à transformer
     * @param pForm le formulaire à remplir.
     * @throws WTransformerException si un pb apparait.
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        ComponentDTO dto = (ComponentDTO) pObject[0];
        ComponentForm form = (ComponentForm) pForm;
        form.setId( dto.getID() );
        form.setName( dto.getName() );
        form.setParentId( dto.getIDParent() );
        form.setType( dto.getType() );
        form.setFileName( dto.getFileName() );
        form.setExcludedFromActionPlan( dto.getExcludedFromActionPlan() );
        form.setJustification( dto.getJustification() );
        form.setFullName( dto.getFullName() );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new ComponentDTO() };
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * @param pObject l'objet à remplir
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        ComponentForm form = (ComponentForm) pForm;
        ComponentDTO dto = (ComponentDTO) pObject[0];
        dto.setID( form.getId() );
        dto.setIDParent( form.getParentId() );
        dto.setType( form.getType() );
        dto.setName( form.getName() );
        dto.setIDParent( form.getParentId() );
        dto.setFileName( form.getFileName() );
        dto.setExcludedFromActionPlan( form.getExcludedFromActionPlan() );
        dto.setJustification( form.getJustification() );
    }

}
