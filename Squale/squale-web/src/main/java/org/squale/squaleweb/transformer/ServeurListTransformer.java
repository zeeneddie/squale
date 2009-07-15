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

import org.squale.squalecommon.datatransfertobject.config.ServeurDTO;
import org.squale.squaleweb.applicationlayer.formbean.config.ServeurListForm;

import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Transformer de liste de serveur
 */
public class ServeurListTransformer
    extends AbstractListTransformer
{

    /**
     * {@inheritDoc}
     * 
     * @param object le tableau des objets nécessaires à la transformation
     * @return le formulaire transformé
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[])
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ServeurListForm form = new ServeurListForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     * 
     * @param object {@inheritDoc} - la liste des serveurs sous forme DTO
     * @param form le formulaire de liste de serveur
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm)
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        ArrayList lServeurListDTO = (ArrayList) object[0];
        ServeurListForm lServeurListForm = (ServeurListForm) form;
        ArrayList ar = new ArrayList();
        Iterator it = lServeurListDTO.iterator();
        while ( it.hasNext() )
        {
            ServeurDTO lServeurDTO = (ServeurDTO) it.next();
            ar.add( WTransformerFactory.objToForm( ServeurTransformer.class, lServeurDTO ) );
        }
        lServeurListForm.setServeurs( ar );
    }

    /**
     * Méthode non implémentée {@inheritDoc}
     * 
     * @param form {@inheritDoc}
     * @param object {@inheritDoc}
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[])
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        // méthode non utilisée
    }

}
