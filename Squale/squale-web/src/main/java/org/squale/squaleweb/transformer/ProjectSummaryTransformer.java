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
package com.airfrance.squaleweb.transformer;

import java.util.List;
import java.util.Map;

import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectFactorsForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ProjectSummaryForm;
import com.airfrance.squaleweb.applicationlayer.formbean.results.ResultListForm;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squaleweb.transformer.ResultListTransformer;

/**
 * Conversion de la synth�se d'un projet
 */
public class ProjectSummaryTransformer
    implements WITransformer
{

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[]) {@inheritDoc}
     */
    public WActionForm objToForm( Object[] arg0 )
        throws WTransformerException
    {
        return null;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#objToForm(java.lang.Object[],
     *      com.airfrance.welcom.struts.bean.WForm) {@inheritDoc}
     */
    public void objToForm( Object[] arg0, WActionForm arg1 )
        throws WTransformerException
    {
        ProjectSummaryForm form = (ProjectSummaryForm) arg1;
        // Positionnement des facteurs
        ProjectFactorsForm factors =
            (ProjectFactorsForm) WTransformerFactory.objToForm( ProjectFactorsTransformer.class, new Object[] {
                arg0[0], arg0[1] } );
        form.setFactors( factors );
        form.setComparableAudits( factors.getComparableAudits() );

        // Positionnement de la volum�trie
        Map volumetryData = (Map) arg0[2];
        // R�cup�ration des clefs
        List measureKeys = (List) volumetryData.get( null );
        List measureValues = (List) volumetryData.get( arg0[0] );
        if ( measureValues != null )
        {
            Object obj[] = { measureKeys, measureValues };
            ComponentDTO project = (ComponentDTO) arg0[0];
            String programLanguage = project.getLanguage();
            ResultListForm volumetry = ResultListTransformer.objToFormWithLanguage( obj , programLanguage);
            form.setVolumetry( volumetry );
        }
        final int errors_id = 3;
        form.setHaveErrors( (Boolean) arg0[errors_id] );
        final int ide_id = 4;
        form.setExportIDE( (Boolean) arg0[ide_id] );
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WForm)
     *      {@inheritDoc}
     */
    public Object[] formToObj( WActionForm arg0 )
        throws WTransformerException
    {
        return null;
    }

    /**
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WForm,
     *      java.lang.Object[]) {@inheritDoc}
     */
    public void formToObj( WActionForm arg0, Object[] arg1 )
        throws WTransformerException
    {
    }

}