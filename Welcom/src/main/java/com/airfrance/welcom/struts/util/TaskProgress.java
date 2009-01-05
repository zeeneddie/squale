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
package com.airfrance.welcom.struts.util;

/**
 * Third-party code used for this class. Impossible to find original licence though. Copyright left to original author
 * if ever found in the future.
 */

public class TaskProgress
{
    /**
     * Current value of the progress.
     */
    private int progress;

    /**
     * Total length of the task.
     */
    private int length;

    /**
     * Timestamp. Used to remove old tasks.
     */
    private long creationDate;

    /**
     * Constructor
     * 
     * @param id taskID
     */
    public TaskProgress()
    {
        this.length = -1;
        this.progress = 0;
        creationDate = System.currentTimeMillis();
    }

    /**
     * Get the percentage of work complete.
     * 
     * @return % of work complete
     */
    public int getPercentComplete()
    {
        int result = 0;
        if ( length > -1 )
            result = (int) ( 100 * ( (double) ( progress ) / (double) length ) );

        if ( result > 100 )
            result = 100;

        return result;
    }

    public long getCreationDate()
    {
        return creationDate;
    }

    public void setProgress( int progress )
    {
        this.progress = progress;
    }

    public void complete()
    {
        this.progress = length;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength( int i )
    {
        length = i;
    }

    public String toString()
    {
        return progress + "/" + length;
    }

}
