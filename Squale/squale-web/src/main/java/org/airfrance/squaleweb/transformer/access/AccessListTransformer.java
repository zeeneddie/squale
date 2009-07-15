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
package com.airfrance.squaleweb.transformer.access;

import java.util.ArrayList;

import com.airfrance.squaleweb.applicationlayer.formbean.access.AccessListForm;
import com.airfrance.squaleweb.transformer.AbstractListTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformArrayList;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

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
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
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
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WActionForm)
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
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        throw new WTransformerException( "not implemented" );
    }

}
