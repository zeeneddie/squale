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

import org.squale.squalecommon.datatransfertobject.rulechecking.RuleCheckingItemDTO;
import org.squale.squaleweb.applicationlayer.formbean.results.RuleCheckingItemForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transforme un item de transgression.
 */
public class RuleCheckingItemTransformer
    implements WITransformer
{

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        RuleCheckingItemForm form = new RuleCheckingItemForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        RuleCheckingItemDTO dto = (RuleCheckingItemDTO) pObject[0];
        RuleCheckingItemForm form = (RuleCheckingItemForm) pForm;
        form.setMessage( dto.getMessage() );
        if ( null != dto.getComponent() )
        {
            form.setComponentId( dto.getComponent().getID() );
            form.setComponentName( dto.getComponent().getName() );
        }
        if ( null != dto.getComponentInvolved() )
        {
            form.setComponentInvolvedId( dto.getComponentInvolved().getID() );
            form.setComponentInvolvedName( dto.getComponentInvolved().getName() );
        }
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm arg0, Object[] arg1 )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

}
