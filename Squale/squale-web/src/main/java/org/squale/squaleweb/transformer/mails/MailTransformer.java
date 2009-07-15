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
package org.squale.squaleweb.transformer.mails;

import java.util.ArrayList;

import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationListForm;
import org.squale.squaleweb.applicationlayer.formbean.mails.MailForm;
import org.squale.squaleweb.transformer.ApplicationListTransformer;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformeur de mail
 */
public class MailTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        MailForm form = new MailForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm)
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        ArrayList listDTO = (ArrayList) pObject[0];
        MailForm mailForm = (MailForm) pForm;
        mailForm.setApplicationFormsList( (ApplicationListForm) WTransformerFactory.objToForm(
                                                                                               ApplicationListTransformer.class,
                                                                                               listDTO ) );
    }

    /**
     * {@inheritDoc}
     * 
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
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm arg0, Object[] arg1 )
        throws WTransformerException
    {
        throw new WTransformerException( "not implemented" );
    }

}
