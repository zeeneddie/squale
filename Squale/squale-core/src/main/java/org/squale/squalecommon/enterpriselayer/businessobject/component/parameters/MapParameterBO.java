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
package org.squale.squalecommon.enterpriselayer.businessobject.component.parameters;

import java.util.HashMap;
import java.util.Map;

/**
 * The <code><b>MapParameterBO</b></code> class represents a Map type object. All parameters related to user inputs in
 * the presentation layer (JSP) are stored in instance(s) of this class.
 * 
 * <p>If you need to recover some parameters :</p>
 * <ol>
 * <li>recover the map related to the task of which you wish to recover the parameters using the constant name related to the task</li>
 * <blockquote><pre>e.g : MapParameterBO myTaskParams = (MapParameterBO) mProject.getParameter( ParametersConstants.myTask );</pre></blockquote>
 * <li>recover parameters one by one by getting the value using the related constant as key</li>
 * <blockquote><pre>e.g StringParameterBO myParameter = (StringParameterBO) myTaskParams.getParameters().get( ParametersConstants.myParameter );</pre></blockquote>
 * </ol>
 * @hibernate.subclass discriminator-value="Map"
 */
public class MapParameterBO
    extends ProjectParameterBO
{

    /**
     * The parameter(s) map related to a task in the project
     */
    private Map mParameters = new HashMap();

    /**
     * Getter of the parameter(s) map
     * 
     * @hibernate.map table="ProjectParameters" lazy="false" cascade="all" sort="unsorted"
     * @hibernate.index column="IndexKey" type="string"
     * @hibernate.key column="MapId"
     * @hibernate.one-to-many class="org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO"
     * @return The parameter(s) map
     */
    public Map getParameters()
    {
        return mParameters;
    }

    /**
     * Setter of the parameter(s) map
     * 
     * @param pMap the new map of parameter(s)
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
