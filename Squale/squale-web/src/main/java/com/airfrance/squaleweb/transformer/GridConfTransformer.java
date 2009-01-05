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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.squalecommon.datatransfertobject.rule.QualityGridConfDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.FactorListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.FactorRuleForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.GridConfigForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformation d'une grille qualité
 */
public class GridConfTransformer
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
        GridConfigForm form = new GridConfigForm();
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
        QualityGridConfDTO gridDTO = (QualityGridConfDTO) pObject[0];
        GridConfigForm form = (GridConfigForm) pForm;
        form.setId( gridDTO.getId() );
        form.setName( gridDTO.getName() );
        form.setUpdateDate( gridDTO.getUpdateDate() );
        // Positionnement des facteurs
        Iterator factorsIt = gridDTO.getFactors().iterator();
        ArrayList factors = new ArrayList();
        while ( factorsIt.hasNext() )
        {
            // Conversion de chacun des facteurs
            factors.add( WTransformerFactory.objToForm( FactorTransformer.class, factorsIt.next() ) );
        }
        FactorListForm factorForms = new FactorListForm();
        factorForms.setList( factors );
        form.setFactors( factorForms );
    }

    /**
     * @param pForm le formulaire à lire.
     * @throws WTransformerException si un pb apparait.
     * @return le tableaux des objets.
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = { new QualityGridConfDTO() };
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
        GridConfigForm gridForm = (GridConfigForm) pForm;
        QualityGridConfDTO dto = (QualityGridConfDTO) pObject[0];
        dto.setId( gridForm.getId() );
        dto.setName( gridForm.getName() );
        // Positionnement des facteurs
        Iterator factors = gridForm.getFactors().getList().iterator();
        Collection factorDTO = new ArrayList();
        while ( factors.hasNext() )
        {
            // Conversion de chacun des facteurs
            factorDTO.add( WTransformerFactory.formToObj( FactorTransformer.class, (FactorRuleForm) factors.next() )[0] );
        }
        dto.setFactors( factorDTO );
    }

}
