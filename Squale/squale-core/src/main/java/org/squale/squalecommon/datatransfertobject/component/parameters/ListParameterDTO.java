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
package org.squale.squalecommon.datatransfertobject.component.parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the model of all instances of ListParameterDTO. All parameters contained in an object of type
 * ListParameterDTO are manipulated in the application layer.
 */
public class ListParameterDTO
{

    /**
     * A collection (a List) of the parameters
     */
    private List mParameters = new ArrayList();

    /**
     * An identifier for any instance of this class
     */
    private long mId = -1;

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */
    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Getter of the list
     * 
     * @return The list containing the parameters
     */
    public List getParameters()
    {
        return mParameters;
    }

    /**
     * Setter of the List
     * 
     * @param pList The list containing the new parameters
     */
    public void setParameters( List pList )
    {
        mParameters = pList;
    }

}
