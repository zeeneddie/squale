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
package org.squale.squaleweb.applicationlayer.formbean.sharedrepository;

import java.util.ArrayList;

import org.squale.squalecommon.datatransfertobject.job.JobDTO;
import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * This form includes a list of application {@link SharedRepositoryExportApplicationForm}. All the applications in the
 * list are exportable : that means they have at least one successful audit Each application is selectable, so that the
 * user can choose the application he wants to export
 */
public class SharedRepositoryExportForm
    extends RootForm
{
    /**
     * UID
     */
    private static final long serialVersionUID = -7877154729526494713L;

    /**
     * The list of selectable applications
     */
    private ArrayList<SharedRepositoryExportApplicationForm> listApp;

    /** The last export job */
    private JobDTO failedJob;

    /** The last successful export job */
    private JobDTO successfulJob;

    /** The last successful export date */
    private String lastSuccessfulDate;

    /** The path to the export file */
    private boolean exportFile;

    /** Is there a job Schedduled */
    private boolean scheduledJob;

    /** Is there a job in progress */
    private boolean inProgressJob;

    /** Is there at least one application selected */
    private boolean oneToExport;

    /** The list of selected application */
    private ArrayList<String> selectedApp = new ArrayList<String>();

    /**
     * Default constructor
     */
    public SharedRepositoryExportForm()
    {
        super();
    }

    /**
     * Set the attribute to their default value
     */
    public void init()
    {
        listApp = null;
        failedJob = null;
        successfulJob = null;
        lastSuccessfulDate = null;
        exportFile = false;
        scheduledJob = false;
        inProgressJob = false;
        oneToExport = false;
        selectedApp = new ArrayList<String>();
    }

    /**
     * Getter for the list of selectable applications
     * 
     * @return The list of selectable applications
     */
    public ArrayList<SharedRepositoryExportApplicationForm> getListApp()
    {
        return listApp;
    }

    /**
     * Setter for the list of selectable applications
     * 
     * @param pListApp The new list of selectable application
     */
    public void setListApp( ArrayList<SharedRepositoryExportApplicationForm> pListApp )
    {
        listApp = pListApp;
    }

    /**
     * Getter method for the attribute selectedApp
     * 
     * @return The list of selected application
     */
    public ArrayList<String> getSelectedApp()
    {
        return selectedApp;
    }

    /**
     * Setter method for the attribute selectedApp
     * 
     * @param pSelectedApp The list of selected application
     */
    public void setSelectedApp( ArrayList<String> pSelectedApp )
    {
        selectedApp = pSelectedApp;
    }

    /**
     * Getter method for the attribute scheduledJob
     * 
     * @return true if there is a scheduled job
     */
    public boolean getScheduledJob()
    {
        return scheduledJob;
    }

    /**
     * Setter method for the attribute scheduledJob
     * 
     * @param pScheduledJob The new state for the scheduled job
     */
    public void setScheduledJob( boolean pScheduledJob )
    {
        scheduledJob = pScheduledJob;
    }

    /**
     * Getter method for the attribute inProgressJob
     * 
     * @return true if a job is in progress
     */
    public boolean isInProgressJob()
    {
        return inProgressJob;
    }

    /**
     * Setter method for the attribute inProgressJob
     * 
     * @param pInProgressJob The state of the job
     */
    public void setInProgressJob( boolean pInProgressJob )
    {
        inProgressJob = pInProgressJob;
    }

    /**
     * Getter method for the attribute failedJob
     * 
     * @return The failed Job
     */
    public JobDTO getFailedJob()
    {
        return failedJob;
    }

    /**
     * Setter method for the attribute failedJob
     * 
     * @param pFailedJob The new Failed job
     */
    public void setFailedJob( JobDTO pFailedJob )
    {
        failedJob = pFailedJob;
    }

    /**
     * Getter method for the attribute successfulJob
     * 
     * @return The successful job
     */
    public JobDTO getSuccessfulJob()
    {
        return successfulJob;
    }

    /**
     * Setter method for the attribute successfulJob
     * 
     * @param pSuccesfullJob The new successful job
     */
    public void setSuccessfulJob( JobDTO pSuccesfullJob )
    {
        successfulJob = pSuccesfullJob;
    }

    /**
     * Getter method for the attribute lastSuccessfulDate
     * 
     * @return The date of the last successful job
     */
    public String getLastSuccessfulDate()
    {
        return lastSuccessfulDate;
    }

    /**
     * Setter method for the attribute lastSuccessfulDate
     * 
     * @param pLastSuccessfulDate The new date of the last successful job
     */
    public void setLastSuccessfulDate( String pLastSuccessfulDate )
    {
        lastSuccessfulDate = pLastSuccessfulDate;
    }

    /**
     * Getter method for the attribute oneToExport
     * 
     * @return true if there is at least one application to export
     */
    public boolean getOneToExport()
    {
        return oneToExport;
    }

    /**
     * Setter method for the attribute oneToExport
     * 
     * @param pOneToExport The new status of oneToExport
     */
    public void setOneToExport( boolean pOneToExport )
    {
        this.oneToExport = pOneToExport;
    }

    /**
     * Getter method for the attribute exportFile
     * 
     * @return true if there is an export file
     */
    public boolean getExportFilePath()
    {
        return exportFile;
    }

    /**
     * Setter method for the attribute exportFile
     * 
     * @param pExportFile the new status of exportFile
     */
    public void setExportFile( boolean pExportFile )
    {
        exportFile = pExportFile;
    }

}
