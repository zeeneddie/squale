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

import java.util.ArrayList;

import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SharedRepositoryExportApplicationForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SharedRepositoryExportForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 *  Transformer form <=> object implementation ({@link WITransformer} for the form {@link SharedRepositoryExportForm }
 */
public class ExportTransformer
    implements WITransformer
{

    /**
     * {@inheritDoc}
     */
    public Object[] formToObj( WActionForm form )
        throws WTransformerException
    {
        Object[] object = { new ArrayList<Long>() };
        formToObj( form, object );
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        //List for selected application
        ArrayList<Long> applicationsToExport = (ArrayList<Long>) object[0];
        //list for all application
        ArrayList<Long> allApplications = (ArrayList<Long>) object[1];
        
        SharedRepositoryExportForm currentForm = (SharedRepositoryExportForm)form;
        ArrayList<SharedRepositoryExportApplicationForm> applicationList = currentForm.getListApp();
        
        // For each application in the form
        for ( SharedRepositoryExportApplicationForm application : applicationList )
        {
            // We add the id of the application to the "all applications" list
            allApplications.add( application.getApplicationId() );
            
            // If the current application is selected then we add its id to the list of selected applications 
            if (application.isSelected())
            {
                applicationsToExport.add( application.getApplicationId() );
            }
        }

    }
    
    /**
     * {@inheritDoc}
     */
    public WActionForm objToForm( Object[] object )
        throws WTransformerException
    {
        SharedRepositoryExportForm form = new SharedRepositoryExportForm();
        
        return form;
    }

    /**
     * {@inheritDoc}
     */
    public void objToForm( Object[] object, WActionForm form )
        throws WTransformerException
    {
        ArrayList<SharedRepositoryExportApplicationForm> applicationWithResults = new ArrayList<SharedRepositoryExportApplicationForm>();
        SharedRepositoryExportForm currentForm = (SharedRepositoryExportForm)form;
        ArrayList<ApplicationForm> allApplication =(ArrayList<ApplicationForm>)object[0];
        for ( ApplicationForm application : allApplication )
        {
            if (application.getHasResults())
            {
                applicationWithResults.add( new SharedRepositoryExportApplicationForm( application.getId(), application.getApplicationName(), application.getLastExportDate() ) );
            }
        }
        currentForm.setListApp( applicationWithResults );
    }

}
