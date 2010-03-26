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

import org.squale.squalecommon.datatransfertobject.component.ApplicationLightDTO;
import org.squale.squalecommon.datatransfertobject.component.ModuleLightDTO;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.ApplicationLightForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.ModuleLightForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Transform an ApplicationLighDTO into an ApplicationLightForm
 */
public class ApplicationLightTransformer
    implements WITransformer
{

    /**
     * <b>Not implemented</b>
     * <br/><br/>
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        // Not implemented
        return null;
    }

    /**
     * <b>Not implemented</b>
     * <br/><br/>
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        // Not implemented

    }

    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        ApplicationLightForm form = new ApplicationLightForm();
        objToForm( object, form );
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        ApplicationLightForm currentForm = (ApplicationLightForm)form;
        ApplicationLightDTO dto = (ApplicationLightDTO)object[0];
        currentForm.setTechnicalId( Long.toString( dto.getTechnicalId()) );
        currentForm.setName( dto.getName() );
        for ( ModuleLightDTO moduleDto : dto.getModuleList() )
        {
            ModuleLightForm moduyleform = (ModuleLightForm)WTransformerFactory.objToForm( ModuleLightTransformer.class, moduleDto );
            currentForm.addModule( moduyleform );
        }

    }

}
