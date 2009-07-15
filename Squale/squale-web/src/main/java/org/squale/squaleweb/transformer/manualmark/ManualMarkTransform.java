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
package com.airfrance.squaleweb.transformer.manualmark;

import java.util.ArrayList;
import java.util.Locale;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.result.QualityResultDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.manualmark.ManualMarkElementForm;
import com.airfrance.squaleweb.applicationlayer.formbean.manualmark.ManualMarkForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformer class object <=> form for the ManualMark
 */
public class ManualMarkTransform
    implements WITransformer
{

    /**
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        Object[] object = { new ManualMarkForm() };
        formToObj( form, object );
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        ManualMarkForm currentForm = (ManualMarkForm) form;
        ArrayList<QualityResultDTO> list = new ArrayList<QualityResultDTO>();
        for ( ManualMarkElementForm elt : currentForm.getManualPracticeList() )
        {
            QualityResultDTO dto = (QualityResultDTO) WTransformerFactory.formToObj( ManualMarkElementTransform.class, elt )[0];
            ComponentDTO compo = new ComponentDTO();
            compo.setID( Long.parseLong( currentForm.getProjectId()) );
            dto.setProject( compo );
            list.add( dto );
        }
        object[0]=list;
    }

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ManualMarkForm form = new ManualMarkForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        Locale local = (Locale)object[1];
        ManualMarkForm currentForm = (ManualMarkForm) form;
        currentForm.setManualPracticeList( new ArrayList<ManualMarkElementForm>() );
        ArrayList<QualityResultDTO> list = (ArrayList<QualityResultDTO>) object[0];
        ManualMarkElementForm element;
        for ( QualityResultDTO dto : list )
        {
            element = new ManualMarkElementForm();
            WTransformerFactory.objToForm( ManualMarkElementTransform.class, element, new Object[] { dto, local } );
            currentForm.add( element );
        }
    }
}