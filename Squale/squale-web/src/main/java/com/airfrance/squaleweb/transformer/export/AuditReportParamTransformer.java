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
package com.airfrance.squaleweb.transformer.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.airfrance.squalecommon.datatransfertobject.result.ErrorDTO;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ErrorForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.ErrorListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.component.SetOfErrorsListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.export.AuditReportParamForm;
import com.airfrance.squaleweb.transformer.ErrorTransformer;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WITransformer;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Transformer for AuditReportParamForm
 */
public class AuditReportParamTransformer
    implements WITransformer
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
        AuditReportParamForm form;
        form = new AuditReportParamForm();
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
        List errorsMap = (List) pObject[0];
        AuditReportParamForm form = (AuditReportParamForm) pForm;
        // Transform errors for current audit
        form.setCurrentAuditErrors( buildSetOfErrorsList( (Map) errorsMap.get( 0 ) ) );
        if ( errorsMap.size() == 2 )
        {
            form.setPreviousAuditErrors( buildSetOfErrorsList( (Map) errorsMap.get( 1 ) ) );
        }
    }

    /**
     * Build the set of errors (one by project in map)
     * 
     * @param projectsErrors map of errors by project
     * @return list of SetOfErrorsListForm (one by project)
     * @throws WTransformerException if error
     */
    private List buildSetOfErrorsList( Map projectsErrors )
        throws WTransformerException
    {
        List setOfErrorsListForm = new ArrayList();
        // For each project, build the list of errors by task
        for ( Iterator keysIt = projectsErrors.keySet().iterator(); keysIt.hasNext(); )
        {
            SetOfErrorsListForm setOfError = new SetOfErrorsListForm();
            String projectName = (String) keysIt.next();
            setOfError.setProjectName( projectName );
            // Map to group errors by task
            HashMap groupByTask = new HashMap();
            for ( Iterator errorsIt = ( (List) projectsErrors.get( projectName ) ).iterator(); errorsIt.hasNext(); )
            {
                // Get current error
                ErrorDTO curError = (ErrorDTO) errorsIt.next();
                // Get map used to count same messages
                Map errors = (Map) groupByTask.get( curError.getTaskName() );
                if ( null == errors )
                {
                    errors = new HashMap();
                }
                ErrorForm error = (ErrorForm) WTransformerFactory.objToForm( ErrorTransformer.class, curError );
                // put in map with message for key
                if ( errors.get( error.getMessage() ) != null )
                {
                    error = (ErrorForm) errors.get( error.getMessage() );
                    error.setNbOcc( error.getNbOcc() + 1 );
                }
                errors.put( error.getMessage(), error );
                // add errorForm for this task
                groupByTask.put( curError.getTaskName(), errors );
            }
            // Create ErrorListForm (one by task)
            List errorListList = new ArrayList();
            for ( Iterator taskIt = groupByTask.keySet().iterator(); taskIt.hasNext(); )
            {
                ErrorListForm errorList = new ErrorListForm();
                errorList.setTaskName( (String) taskIt.next() );
                errorList.setList( new ArrayList( ( (Map) groupByTask.get( errorList.getTaskName() ) ).values() ) );
                errorListList.add( errorList );
            }
            setOfError.setSetOfErrors( errorListList );
            setOfErrorsListForm.add( setOfError );
        }
        return setOfErrorsListForm;
    }

    /**
     * {@inheritDoc}
     * 
     * @param pForm {@inheritDoc}
     * @return {@inheritDoc}
     * @throws WTransformerException {@inheritDoc}
     * @see com.airfrance.welcom.struts.transformer.WITransformer#formToObj(com.airfrance.welcom.struts.bean.WActionForm)
     */
    public Object[] formToObj( WActionForm pForm )
        throws WTransformerException
    {
        throw new WTransformerException( "not implemented" );
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
