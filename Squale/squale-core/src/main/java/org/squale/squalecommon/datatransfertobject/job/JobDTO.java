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
package org.squale.squalecommon.datatransfertobject.job;

import java.io.Serializable;
import java.util.Date;

import org.squale.jraf.spi.dto.IDTO;

/**
 * The job DTO
 */
public class JobDTO implements IDTO, Serializable
{

    /**
     * UID
     */
    private static final long serialVersionUID = 3985368386014783752L;

    /** The technical id */
    private long id = -1;

    /** Name of the job */
    private String jobName;

    /** Status of the job */
    private String jobStatus;

    /** Date of the job */
    private Date jobDate;
    
    /**
     * Default constructor
     */
    public JobDTO()
    {
        
    }
    
    /**
     * Constructor
     * 
     * @param pJobName Name of the job
     */
    public JobDTO(String pJobName)
    {
        jobName = pJobName;
    }

    /**
     * Constructor
     * 
     * @param pJobName Name of the job
     * @param pJobStatus Status of  the job
     */
    public JobDTO(String pJobName,String pJobStatus)
    {
        jobName = pJobName;
        jobStatus = pJobStatus;
    }
    
    /**
     * Getter method for the attribute id
     * 
     * @return The technical id of the object *
     */
    public long getId()
    {
        return id;
    }

    /**
     * Setter method for the attribute id
     * 
     * @param pId The new technical id of the object
     */
    public void setId( long pId )
    {
        id = pId;
    }

    /**
     * Getter method for the attribute jobName
     * 
     * @return The name of the job
     */
    public String getJobName()
    {
        return jobName;
    }

    /**
     * Setter method for the attribute jobName
     * 
     * @param pJobName The new name of the job
     */
    public void setJobName( String pJobName )
    {
        jobName = pJobName;
    }

    /**
     * Getter method for the attribute jobStatus
     * 
     * @return The status of the job
     */
    public String getJobStatus()
    {
        return jobStatus;
    }

    /**
     * Setter method for the attribute jobStatus
     * 
     * @param pJobStatus The new status of the job
     */
    public void setJobStatus( String pJobStatus )
    {
        jobStatus = pJobStatus;
    }

    /**
     * Getter method for the attribute jobDate
     * 
     * @return The date of the job
     */
    public Date getJobDate()
    {
        return jobDate;
    }

    /**
     * Setter method for the attribute jobDate
     * 
     * @param pJobDate The new date of the job
     */
    public void setJobDate( Date pJobDate )
    {
        jobDate = pJobDate;
    }

}
