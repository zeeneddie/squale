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

import java.util.HashMap;
import java.util.Map;

/**
 * @hibernate.subclass discriminator-value="Map"
 */
public class MapParameterBO
    extends ProjectParameterBO
{

    /**
     * La map de paramètres
     */
    private Map mParameters = new HashMap();

    /**
     * @hibernate.map table="ProjectParameters" lazy="false" cascade="all" sort="unsorted"
     * @hibernate.index column="IndexKey" type="string"
     * @hibernate.key column="MapId"
     * @hibernate.one-to-many class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO"
     * @return la map de paramètres
     */
    public Map getParameters()
    {
        return mParameters;
    }

    /**
     * @param pMap la nouvelle map de parametres
     */
    public void setParameters( Map pMap )
    {
        mParameters = pMap;
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
        if ( obj instanceof MapParameterBO )
        {
            result = getParameters().equals( ( (MapParameterBO) obj ).getParameters() );
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
