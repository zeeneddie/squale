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

import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.GridForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;

/**
 * Transformation d'une grille qualité
 */
public class GridTransformer
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
        GridForm form = new GridForm();
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
        QualityGridDTO gridDTO = (QualityGridDTO) pObject[0];
        GridForm form = (GridForm) pForm;
        form.setId( gridDTO.getId() );
        form.setName( gridDTO.getName() );
        form.setUpdateDate( gridDTO.getUpdateDate() );
        // Texte pour un mail éventuel signifiant les changement de grille,
        // pas de texte par défaut (pourra etre rempli plus tard)
        form.setAdminText( "" );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new QualityGridDTO() };
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
        GridForm gridForm = (GridForm) pForm;
        QualityGridDTO dto = (QualityGridDTO) pObject[0];
        dto.setId( gridForm.getId() );
        dto.setName( gridForm.getName() );
        dto.setUpdateDate( gridForm.getUpdateDate() );
    }

}
