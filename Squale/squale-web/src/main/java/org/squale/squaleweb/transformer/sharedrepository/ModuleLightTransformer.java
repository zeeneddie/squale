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

import org.squale.squalecommon.datatransfertobject.component.ModuleLightDTO;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.ModuleLightForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * transform a ModuleLightDTO into a ModuleLightForm
 */
public class ModuleLightTransformer
    implements WITransformer
{

    /**
     * <b>Not implemented</b> <br/>
     * <br/> {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        // not implemented
        return null;
    }

    /**
     * <b>Not implemented</b> <br/>
     * <br/> {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        // not implemented

    }

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ModuleLightForm form = new ModuleLightForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        ModuleLightForm currentForm = (ModuleLightForm) form;
        ModuleLightDTO dto = (ModuleLightDTO) object[0];
        currentForm.setTechnicalId( Long.toString( dto.getTechnicalId() ) );
        currentForm.setName( dto.getName() );
    }

}
