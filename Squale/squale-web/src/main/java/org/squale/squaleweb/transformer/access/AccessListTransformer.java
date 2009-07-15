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

import java.util.ArrayList;

import org.squale.squaleweb.applicationlayer.formbean.access.AccessListForm;
import org.squale.squaleweb.transformer.AbstractListTransformer;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformArrayList;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * 
 */
public class AccessListTransformer
    extends AbstractListTransformer
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
        AccessListForm form = new AccessListForm();
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
        ArrayList listDTO = (ArrayList) pObject[0];
        AccessListForm listForm = (AccessListForm) pForm;
        WITransformer transformer = WTransformerFactory.getSingleTransformer( AccessTransformer.class );
        ArrayList list = WTransformArrayList.objToForm( listDTO, transformer );
        listForm.setList( list );

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
