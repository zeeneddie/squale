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
 * @author M401540
 * @hibernate.subclass discriminator-value="Bin"
 */
public class BinaryMetricBO
    extends MetricBO
{
    /**
     * Valeur sous forme de chaine de bytes
     */
    private byte[] mValue;

    /**
     * Access method for the mValue property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="Value" column="Blob_val"
     *                     type="org.squale.jraf.provider.persistence.hibernate.BinaryBlobType" not-null="false"
     *                     unique="false" update="true" insert="true"
     */
    public Object getValue()
    {
        return mValue;
    }

    /**
     * @param pValue image sous forme de byte[]
     */
    public void setValue( Object pValue )
    {
        mValue = (byte[]) pValue;
    }

}
