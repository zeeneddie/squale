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

/**
 * Possible job status
 */
public enum JobStatus
{
    /** The job is Scheduled */
    SCHEDULED( "scheduled" ),

    /** The job is in progress */
    IN_PROGESS( "inProgress" ),

    /** The Job failed */
    FAILED( "failed" ),

    /** There was nothing to export */
    NOTHING_TO_EXPORT( "nothingToExport" ),

    /** The job succeed */
    SUCCESSFUL( "sucessful" );

    /** The enum label */
    protected String label;

    /**
     * Constructor
     * 
     * @param pLabel The label of the enum
     */
    private JobStatus( String pLabel )
    {
        this.label = pLabel;
    }

    /**
     * Getter method for the label of the enum
     * 
     * @return The label of the enum
     */
    public String getLabel()
    {
        return label;
    }

    /**
     * Compare the status label given in argument with the label of the current JobStatus
     * 
     * @param compStatus The label to compare
     * @return True if the String given in argument is equal to the label of the current JobStatus
     */
    public boolean same( String compStatus )
    {
        return compStatus.equals( label );
    }
}
