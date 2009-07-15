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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squaleweb.applicationlayer.formbean.search.SearchProjectForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * transformer pour la recherche d'un projet
 */
public class SearchProjectTransformer
    implements WITransformer
{

    /**
     * Obj --> form
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] pObject )
        throws WTransformerException
    {
        SearchProjectForm form = new SearchProjectForm();
        objToForm( pObject, form );
        return form;
    }

    /**
     * Obj --> form
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WActionForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        SearchProjectForm form = (SearchProjectForm) pForm;
        Map projectsWithAudit = new HashMap();
        Map projectsDto = (Map) pObject[0];
        List applications = (List) pObject[1];
        for ( Iterator it = projectsDto.keySet().iterator(); it.hasNext(); )
        {
            ComponentDTO dto = (ComponentDTO) it.next();
            AuditDTO auditDTO = (AuditDTO) projectsDto.get( dto );
            if ( null != auditDTO )
            {
                projectsWithAudit.put( WTransformerFactory.objToForm( ProjectTransformer.class, new Object[] { dto,
                    applications } ), WTransformerFactory.objToForm( AuditTransformer.class, new Object[] { auditDTO } ) );
            }
            else
            {
                projectsWithAudit.put( WTransformerFactory.objToForm( ProjectTransformer.class, new Object[] { dto,
                    applications } ), null );
            }
        }
        form.setProjectForms( projectsWithAudit );

    }

    /**
     * form --> Obj
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        Object[] obj = new Object[3];
        formToObj( pForm, obj );
        return obj;
    }

    /**
     * form --> Obj
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WActionForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm pForm, Object[] pObject )
        throws WTransformerException
    {
        SearchProjectForm form = (SearchProjectForm) pForm;
        pObject[0] = form.getApplicationBeginningName();
        pObject[1] = form.getProjectBeginningName();
        pObject[2] = form.getTagList();
    }

}
