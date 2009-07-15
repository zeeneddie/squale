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
package org.squale.squalecommon.enterpriselayer.businessobject.result;

/**
 * @hibernate.subclass discriminator-value="Float"
 */
public class FloatMetricBO
    extends NumberMetricBO
{

    /**
     * Valeur continue du mérique
     */
    protected Float mValue;

    /**
     * Access method for the mValue property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="Value" column="Number_val" type="float" not-null="false" unique="false" update="true"
     *                     insert="true"
     */
    public Object getValue()
    {
        return mValue;
    }

    /**
     * @see org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO#setValue(java.lang.Object)
     */
    public void setValue( Object pValue )
    {
        mValue = (Float) pValue;
    }

    /**
     * Sets the value of the mValue property as a float.
     * 
     * @param pValue the new value of the mValue property
     */
    public void setValue( float pValue )
    {
        mValue = new Float( pValue );
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.squale.squalecommon.enterpriselayer.businessobject.result.MetricBO#isPrintable()
     */
    public boolean isPrintable()
    {
        return true;
    }

}
