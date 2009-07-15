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
package org.squale.squalecommon.enterpriselayer.businessobject.result.umlquality;

import org.squale.squalecommon.enterpriselayer.businessobject.result.MeasureBO;

/**
 * @hibernate.subclass discriminator-value="UMLQualityMetrics"
 */

public class UMLQualityMetricsBO
    extends MeasureBO
    implements Comparable
{

    /**
     * Le nom complet(FQN)du composant Cet attribut n'est pas persistant, il représente le nom du composant auquel sont
     * liées les métriques.<br/> L'adaptateur utilise ce nom pour déterminer l'instance de AbstractComponentBO
     * correspondante et créer la relation.
     */

    private String mComponentName;

    /**
     * Constructeur par défaut.
     */
    public UMLQualityMetricsBO()
    {
        super();
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the component Name
     */
    public String getComponentName()
    {
        return mComponentName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pComponentName the new value of the component Name
     */
    public void setName( String pComponentName )
    {
        mComponentName = pComponentName;
    }

    /**
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable.compareTo
     */
    public int compareTo( Object o )
    {

        int result = 0;
        if ( o instanceof UMLQualityMetricsBO )
        {
            UMLQualityMetricsBO metric = (UMLQualityMetricsBO) o;
            if ( ( metric.getComponentName() != null ) && ( getComponentName() != null ) )
            {
                result = mComponentName.compareTo( metric.getComponentName() );
            }
        }
        return result;
    }
}
