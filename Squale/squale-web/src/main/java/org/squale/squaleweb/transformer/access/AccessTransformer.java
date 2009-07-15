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
package org.squale.squaleweb.transformer.access;

import org.squale.squalecommon.datatransfertobject.access.UserAccessDTO;
import org.squale.squaleweb.applicationlayer.formbean.access.AccessForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformer pour les accès utilisateur
 */
public class AccessTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     * 
     * @param pObject {@inheritDoc}
     * @return {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        AccessForm form = new AccessForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pObject {@inheritDoc}
     * @param pForm {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm)
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        UserAccessDTO dto = (UserAccessDTO) pObject[0];
        AccessForm form = (AccessForm) pForm;
        form.setDate( dto.getDate() );
        form.setMatricule( dto.getMatricule() );
    }

    /**
     * {@inheritDoc}
     * 
     * @param pForm {@inheritDoc}
     * @return {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        throw new WTransformerException( "not implemented" );
    }

    /**
     * {@inheritDoc}
     * 
     * @param pForm {@inheritDoc}
     * @param pObject {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        throw new WTransformerException( "not implemented" );
    }

}
