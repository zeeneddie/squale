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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.squale.squalecommon.datatransfertobject.job.JobDTO;
import org.squale.squalecommon.datatransfertobject.sharedrepository.ApplicationExportDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.job.JobStatus;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SharedRepositoryExportApplicationForm;
import org.squale.squaleweb.applicationlayer.formbean.sharedrepository.SharedRepositoryExportForm;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WITransformer;
import org.squale.welcom.struts.transformer.WTransformerException;

/**
 * Transformer form <=> object implementation ({@link WITransformer} for the form {@link SharedRepositoryExportForm }
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
        Object[] object = { new ArrayList<ApplicationExportDTO>()};
        formToObj( form, object );
        return object;
    }

    /**
     * {@inheritDoc}
     */
    public void formToObj( WActionForm form, Object[] object )
        throws WTransformerException
    {
        ArrayList<ApplicationExportDTO> applications = (ArrayList<ApplicationExportDTO>) object[0];
        SharedRepositoryExportForm currentForm = (SharedRepositoryExportForm) form;
        ArrayList<SharedRepositoryExportApplicationForm> applicationList = currentForm.getListApp();
        ApplicationExportDTO appDto;
        for ( SharedRepositoryExportApplicationForm applicationForm : applicationList )
        {
            appDto =
                new ApplicationExportDTO( applicationForm.getApplicationLastExportId(),
                                          applicationForm.getApplicationId(), applicationForm.getLastExportDate(),
                                          applicationForm.isSelected() );
            if(applicationForm.isSelected())
            {
                currentForm.setOneToExport( true );
            }
            applications.add( appDto );
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
        
        ArrayList<SharedRepositoryExportApplicationForm> applicationWithResults =
            new ArrayList<SharedRepositoryExportApplicationForm>();
        SharedRepositoryExportForm currentForm = (SharedRepositoryExportForm) form;
        
        ArrayList<ApplicationExportDTO> allApplication = (ArrayList<ApplicationExportDTO>) object[0];
        ArrayList<String> selectedApplications = new ArrayList<String>();
        for ( ApplicationExportDTO application : allApplication )
        {
            applicationWithResults.add( new SharedRepositoryExportApplicationForm( application.getId(),
                                                                                   application.getApplicationId(),
                                                                                   application.getApplicationName(),
                                                                                   application.getLastExportDate(),
                                                                                   application.getToExport() ) );
            if ( application.getToExport() )
            {
                selectedApplications.add( application.getApplicationName() );
            }
        }
        currentForm.setListApp( applicationWithResults );
        currentForm.setSelectedApp( selectedApplications );
        List<JobDTO> job = (List<JobDTO>) object[1];
        for ( JobDTO jobDTO : job )
        {
            String currentJobStatus = jobDTO.getJobStatus();
            if( JobStatus.FAILED.same( currentJobStatus ))
            {
                currentForm.setFailedJob( jobDTO );
            }
            else if(JobStatus.NOTHING_TO_EXPORT.same( currentJobStatus ))
            {
                currentForm.setNothingToExportJob( jobDTO );
            }
            else if(JobStatus.SUCCESSFUL.same( currentJobStatus ))
            {
                currentForm.setSuccessfulJob( jobDTO );
                SimpleDateFormat formater = new SimpleDateFormat( "dd/MM/yyyy" );
                currentForm.setLastSuccessfulDate( formater.format( jobDTO.getJobDate() ) );
            }
            else if( JobStatus.SCHEDULED.same( currentJobStatus ))
            {
                currentForm.setScheduledJob( true );
            }
            else
            {
                currentForm.setInProgressJob( true );
            }
        }        
    }
}
