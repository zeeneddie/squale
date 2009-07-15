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

/**
 * @hibernate.subclass discriminator-value="String"
 */
public class StringParameterBO
    extends ProjectParameterBO
{
    /**
     * Constructeur
     */
    public StringParameterBO()
    {
        this( null );
    }

    /**
     * Constructor
     * 
     * @param pValue value of string
     */
    public StringParameterBO( String pValue )
    {
        mValue = pValue;
    }

    /**
     * La valeur du paramètre
     */
    private String mValue;

    /**
     * @hibernate.property name="Value" column="Value" length="1024" type="string" not-null="false" unique="false"
     *                     update="true" insert="true"
     * @return la valeur du paramètre
     */
    public String getValue()
    {
        return mValue;
    }

    /**
     * @param pValue la nouvelle valeur du paramètre
     */
    public void setValue( String pValue )
    {
        mValue = pValue;
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
        if ( obj instanceof StringParameterBO )
        {
            result = getValue().equals( ( (StringParameterBO) obj ).getValue() );
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
        return getValue() == null ? super.hashCode() : getValue().hashCode();
    }

}
