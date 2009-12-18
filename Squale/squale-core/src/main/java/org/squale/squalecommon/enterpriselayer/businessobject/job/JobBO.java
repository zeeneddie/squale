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
package org.squale.squalecommon.enterpriselayer.businessobject.job;

import java.io.Serializable;
import java.util.Date;

/**
 * The job business object
 * 
 * @hibernate.class table="Job"
 */
public class JobBO
    implements Serializable
{

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 5806501786401940032L;

    /** The technical id */
    private long id = -1;

    /** Name of the job */
    private String jobName;

    /** Status of the job */
    private String jobStatus;

    /** Date of the job */
    private Date jobDate;

    /**
     * Default Constructor
     */
    public JobBO()
    {

    }

    /**
     * Constructor
     * 
     * @param pJobName Name of the job
     */
    public JobBO( String pJobName )
    {
        jobName = pJobName;
    }

    /**
     * Constructor
     * 
     * @param pJobName Name of the job
     * @param pJobStatus Status of the job
     */
    public JobBO( String pJobName, String pJobStatus )
    {
        jobName = pJobName;
        jobStatus = pJobStatus;
    }

    /**
     * Getter method for the attribute id
     * 
     * @return The technical id of the object *
     * @hibernate.id generator-class="native" type="long" column="JobId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="job_sequence"
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
     * @hibernate.property name="name" column="JobName" type="string" length="100" insert="true" update="true"
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
     * @hibernate.property name="status" column="JobStatus" type="string" length="100" insert="true" update="true"
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
     * @hibernate.property name="date" column="JobDate" type="timestamp" update="true" insert="true"
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
