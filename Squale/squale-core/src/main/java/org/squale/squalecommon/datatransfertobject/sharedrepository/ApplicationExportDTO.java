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
package org.squale.squalecommon.datatransfertobject.sharedrepository;

import java.util.Date;

import org.squale.jraf.spi.dto.IDTO;

/**
 * The DTO for the application export
 */
public class ApplicationExportDTO
    implements IDTO
{

    /**
     * Thecnical id
     */
    private long id = -1;

    /**
     * The application
     */
    private long applicationId;

    /**
     * The application name
     */
    private String applicationName;

    /**
     * The date of the last export for the application
     */
    private Date lastExportDate;

    /**
     * Does the application should be export
     */
    private boolean toExport;

    /**
     * Default Constructor
     */
    public ApplicationExportDTO()
    {

    }

    /**
     * Constructor
     * 
     * @param pId Application last export Id
     * @param pApplicationId Application id
     * @param pDate Last export date
     * @param pToExport does the application is to exportc ?
     */
    public ApplicationExportDTO( long pId, long pApplicationId, Date pDate, boolean pToExport )
    {

        id = pId;
        applicationId = pApplicationId;
        lastExportDate = pDate;
        toExport = pToExport;
    }

    /**
     * Getter for the thecnical id
     * 
     * @return the id of this ApplicationLastExport
     */
    public long getId()
    {
        return id;
    }

    /**
     * Setter for the technical id
     * 
     * @param pId The new technical id of this ApplicationLastExport
     */
    public void setId( long pId )
    {
        id = pId;
    }

    /**
     * Getter for the attribute application
     * 
     * @return the id of the application concern by the export
     */
    public long getApplicationId()
    {
        return applicationId;
    }

    /**
     * Getter method for the attribute applictionName
     * 
     * @return The application name
     */
    public String getApplicationName()
    {
        return applicationName;
    }

    /**
     * Setter method for the attribute applictionName
     * 
     * @param pApplicationName The new name of the application
     */
    public void setApplicationName( String pApplicationName )
    {
        applicationName = pApplicationName;
    }

    /**
     * Setter for the attribute application
     * 
     * @param pApplicationId The new application Id
     */
    public void setApplicationId( long pApplicationId )
    {
        applicationId = pApplicationId;
    }

    /**
     * Getter for the attribute date
     * 
     * @return The date of the last export for the application
     */
    public Date getLastExportDate()
    {
        return lastExportDate;
    }

    /**
     * Setter for the attribute date
     * 
     * @param pLastExportDate The new date of the last export
     */
    public void setLastExportDate( Date pLastExportDate )
    {
        lastExportDate = pLastExportDate;
    }

    /**
     * Getter for the attribute toExport
     * 
     * @return true if this application should be export
     */
    public boolean getToExport()
    {
        return toExport;
    }

    /**
     * Setter for the attribute toExport
     * 
     * @param pToExport The new export status for the application
     */
    public void setToExport( boolean pToExport )
    {
        toExport = pToExport;
    }

}
