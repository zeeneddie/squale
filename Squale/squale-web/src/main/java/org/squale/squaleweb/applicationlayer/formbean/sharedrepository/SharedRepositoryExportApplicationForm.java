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

import java.util.Date;

import org.squale.welcom.struts.bean.WActionFormSelectable;

/**
 * This form represent an selectable application for the {@link SharedRepositoryExportForm}
 */
public class SharedRepositoryExportApplicationForm
    extends WActionFormSelectable
{

    /**
     * UID
     */
    private static final long serialVersionUID = 3686628389255608416L;

    /**
     * Last export identifier
     */
    private long applicationLastExportId;

    /**
     * Id of the application.
     */
    private long applicationId;

    /**
     * Name of the application
     */
    private String applicationName;

    /**
     * Date of the last export. Null if no export was done
     */
    private Date lastExportDate;

    /**
     * Default constructor
     */
    public SharedRepositoryExportApplicationForm()
    {
        super();
    }
    
    /**
     * Full constructor
     * 
     * @param pApplicationLastExportId The applicationLastExport id
     * @param pApplicationId The id of the application
     * @param pApplicationName The name of the application
     * @param pLastExportDate The last export date of the application
     * @param selected Does the application is selected ?
     */
    public SharedRepositoryExportApplicationForm( long pApplicationLastExportId ,long pApplicationId, String pApplicationName, Date pLastExportDate,
                                                  boolean selected )
    {
        applicationLastExportId = pApplicationLastExportId;
        applicationId = pApplicationId;
        applicationName = pApplicationName;
        lastExportDate = pLastExportDate;
        setSelected( selected );
    }

    /**
     * Getter method for the attribute applicationLastExportId
     * 
     * @return The id of the applicationLastExport
     */
    public long getApplicationLastExportId()
    {
        return applicationLastExportId;
    }

    /**
     * Setter method for the attribute applicationLastExportId
     * 
     * @param pApplicationLastExportId The new Id of the applicationLastExport
     */
    public void setApplicationLastExportId( long pApplicationLastExportId )
    {
        applicationLastExportId = pApplicationLastExportId;
    }

    /**
     * Getter for the last export date
     * 
     * @return The last export date
     */
    public Date getLastExportDate()
    {
        return lastExportDate;
    }

    /**
     * Setter for the last export date
     * 
     * @param pLastExportDate The new export date
     */
    public void setLastExportDate( Date pLastExportDate )
    {
        lastExportDate = pLastExportDate;
    }

    /**
     * Setter for the application id
     * 
     * @param pId The new application id.
     */
    public void setApplicationId( long pId )
    {
        applicationId = pId;
    }

    /**
     * Getter for the application id
     * 
     * @return The id of the application.
     */
    public long getApplicationId()
    {
        return applicationId;
    }

    /**
     * Getter for the application name
     * 
     * @return The name of the application
     */
    public String getApplicationName()
    {
        return applicationName;
    }

    /**
     * Setter for the name of the application
     * 
     * @param pApplicationName The new name of the application
     */
    public void setApplicationName( String pApplicationName )
    {
        applicationName = pApplicationName;
    }

}
