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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.squale.squalecommon.datatransfertobject.rulechecking.RuleCheckingItemDTO;
import org.squale.squaleweb.applicationlayer.formbean.results.RuleCheckingItemForm;
import org.squale.squaleweb.applicationlayer.formbean.results.RuleCheckingItemsListForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transforme un dto en form un RuleCheckingItemsList.
 */
public class RuleCheckingItemsListTransformer
    implements WITransformer
{

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        RuleCheckingItemsListForm items = new RuleCheckingItemsListForm();
        objToForm( pObject, items );
        return items;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        // Récupération des paramètres
        String ruleName = (String) pObject[0];
        Collection details = (Collection) pObject[1];
        RuleCheckingItemsListForm form = (RuleCheckingItemsListForm) pForm;
        RuleCheckingItemTransformer transformer = new RuleCheckingItemTransformer();
        ArrayList detailsForm = new ArrayList();
        boolean hasComponentLink = false;
        for ( Iterator it = details.iterator(); it.hasNext(); )
        {
            RuleCheckingItemDTO itemDTO = (RuleCheckingItemDTO) it.next();
            RuleCheckingItemForm itemForm = (RuleCheckingItemForm) transformer.objToForm( new Object[] { itemDTO } );
            detailsForm.add( itemForm );
            // On teste met le booléen à true dès qu'un item possède un lien vers un composant
            if ( -1 != itemForm.getComponentId() || -1 != itemForm.getComponentInvolvedId() )
            {
                hasComponentLink = true;
            }
        }
        form.setComponentLink( hasComponentLink );
        form.setDetails( detailsForm );
        form.setRuleName( ruleName );
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        throw new WTransformerException( "not yet implemented" );
    }
}
