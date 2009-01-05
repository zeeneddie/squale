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
package com.airfrance.squalecommon.datatransfertobject.component.parameters;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class ListParameterDTO
{

    /**
     * La liste de paramètres
     */
    private List mParameters = new ArrayList();

    /**
     * Identifiant de l'objet
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
     * @return la liste de paramètres
     */
    public List getParameters()
    {
        return mParameters;
    }

    /**
     * @param pList la nouvelle liste de paramètres
     */
    public void setParameters( List pList )
    {
        mParameters = pList;
    }

}
