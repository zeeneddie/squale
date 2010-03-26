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
package org.squale.squaleweb.transformer.sharedrepository;

import org.squale.squalecommon.datatransfertobject.sharedrepository.segment.SegmentDTO;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SegmentForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformer SegmentDTO <=> SegmentForm 
 *
 */
public class SegmentTransformer
    implements WITransformer
{
    /**
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        Object[] obj = {new SegmentDTO(), new Boolean( false )};
        formToObj( form, obj );
        return obj;
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        SegmentForm currentForm = (SegmentForm) form;
        SegmentDTO dto = (SegmentDTO) object[0];
        dto.setSegmentName( currentForm.getName());
        dto.setTechnicalId( Long.valueOf( currentForm.getIdentifier()) );
        object[1] = currentForm.isSelected();
    }

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        SegmentForm currentForm = new SegmentForm();
        objToForm( object, currentForm );
        return currentForm;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        SegmentForm currentForm = (SegmentForm) form;
        SegmentDTO dto = (SegmentDTO) object[0];
        Boolean selected = (Boolean) object[1];
        currentForm.setName( dto.getFullKey());
        currentForm.setIdentifier( Long.toString( dto.getTechnicalId() ) );
        currentForm.setSelected( selected );
        currentForm.setDeprecated( dto.isDeprecated() );
    }

}
