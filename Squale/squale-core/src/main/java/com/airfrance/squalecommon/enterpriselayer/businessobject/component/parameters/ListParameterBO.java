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
package com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters;

import java.util.ArrayList;
import java.util.List;

/**
 * @hibernate.subclass discriminator-value="List"
 */
public class ListParameterBO
    extends ProjectParameterBO
{

    /**
     * La liste de paramètres
     */
    private List mParameters = new ArrayList();

    /**
     * @hibernate.list table="ProjectParameter" cascade="all" lazy="false"
     * @hibernate.key column="ListId"
     * @hibernate.index column="Rank" type="long" length="19"
     * @hibernate.one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO"
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

    /**
     * {@inheritDoc}
     * 
     * @param obj
     * @return {@inheritDoc}
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( Object obj )
    {
        boolean result = false;
        if ( obj instanceof ListParameterBO )
        {
            result = getParameters().equals( ( (ListParameterBO) obj ).getParameters() );
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@inheritDoc}
     * @see java.lang.Object#hashCode()
     */
    public int hashCode()
    {
        return getParameters() == null ? super.hashCode() : getParameters().hashCode();
    }

}
