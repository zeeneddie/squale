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

import java.util.List;
import java.util.Map;

import org.squale.squaleweb.applicationlayer.formbean.results.ProjectFactorsForm;
import org.squale.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm;
import org.squale.squaleweb.applicationlayer.formbean.results.ResultListForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squaleweb.transformer.ResultListTransformer;

/**
 * Conversion de la synthèse d'un projet
 */
public class ProjectSummaryTransformer
    implements WITransformer
{

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] arg0 )
        throws WTransformerException
    {
        return null;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      org.squale.welcom.struts.bean.WForm) {@inheritDoc}
     */
    public void objToForm( Object[] pObject, WActionForm pForm )
        throws WTransformerException
    {
        ProjectSummaryForm form = (ProjectSummaryForm) pForm;
        // Positionnement des facteurs
        ProjectFactorsForm factors =
            (ProjectFactorsForm) WTransformerFactory.objToForm( ProjectFactorsTransformer.class, new Object[] {
                pObject[0], pObject[1]} );
        form.setFactors( factors );
        form.setComparableAudits( factors.getComparableAudits() );

        // Positionnement de la volumétrie
        Map volumetryData = (Map) pObject[2];
        // Récupération des clefs
        List measureKeys = (List) volumetryData.get( null );
        List measureValues = (List) volumetryData.get( pObject[0] );
        if ( measureValues != null )
        {
            Object obj[] = { measureKeys, measureValues };
            ComponentDTO project = (ComponentDTO) pObject[0];
            String programLanguage = project.getLanguage();
            ResultListForm volumetry = ResultListTransformer.objToFormWithLanguage( obj , programLanguage);
            form.setVolumetry( volumetry );
        }
        final int errors_id = 3;
        form.setHaveErrors( (Boolean) pObject[errors_id] );
        final int ide_id = 4;
        form.setExportIDE( (Boolean) pObject[ide_id] );
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm arg0 )
        throws WTransformerException
    {
        return null;
    }

    /**
     * @see org.squale.welcom.struts.transformer.WITransformer#formToObj(org.squale.welcom.struts.bean.WForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm arg0, Object[] arg1 )
        throws WTransformerException
    {
    }

}
