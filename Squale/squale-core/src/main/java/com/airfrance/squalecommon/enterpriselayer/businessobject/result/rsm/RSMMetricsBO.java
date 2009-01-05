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
package com.airfrance.squalecommon.enterpriselayer.businessobject.result.rsm;

import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MeasureBO;

/**
 * @hibernate.subclass discriminator-value="RSMMetrics"
 */
public class RSMMetricsBO
    extends MeasureBO
{

    /**
     * Cet attribut n'est pas persistant, il représente le nom du composant auquel sont liées les données tel que
     * remonté par RSM.<br>
     * L'adaptateur utilise ce nom pour déterminer l'instance de AbstractComponentBO correspondante et créer la
     * relation.
     */
    protected String mComponentName;

    /**
     * Access method for the mComponentName property.
     * 
     * @return the current value of the mComponentName property
     * @roseuid 42C416B60319
     */
    public String getComponentName()
    {
        return mComponentName;
    }

    /**
     * Sets the value of the mComponentName property.
     * 
     * @param pComponentName the new value of the mComponentName property
     * @roseuid 42C416B60329
     */
    public void setComponentName( String pComponentName )
    {
        mComponentName = pComponentName;
    }

}
