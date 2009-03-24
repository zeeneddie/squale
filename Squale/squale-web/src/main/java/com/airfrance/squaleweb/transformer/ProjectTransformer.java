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

import java.util.Collection;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ProjectForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformateurs DTO <-> Form pour les projets
 * 
 * @author M400842
 */
public class ProjectTransformer
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
        ProjectForm form = new ProjectForm();
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
        ProjectForm form = (ProjectForm) pForm;
        form.setId( dto.getID() );
        form.setProjectName( dto.getName() );
        form.setApplicationId( "" + dto.getIDParent() );
        form.setHasTerminatedAudit( dto.getHasResults() );
        form.setLanguage( dto.getLanguage() );
        // Si le tableau d'objets passé en paramètre contient 2 objets
        // alors le deuxième est la liste des applications stockées en session.
        // Cela arrive dans les cas où l'on veut récupérer le nom de l'application
        // associée au projet.
        if ( pObject.length == 2 )
        {
            Collection applications = (Collection) pObject[1];
            String applicationName = TransformerUtils.getApplicationName( dto.getIDParent(), applications );
            form.setApplicationName( applicationName );
        }
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
        ProjectForm form = (ProjectForm) pForm;
        ComponentDTO dto = (ComponentDTO) pObject[0];
        dto.setID( form.getId() );
        dto.setIDParent( form.getParentId() );
        dto.setName( form.getProjectName() );
        dto.setLanguage( form.getLanguage() );
    }
}
