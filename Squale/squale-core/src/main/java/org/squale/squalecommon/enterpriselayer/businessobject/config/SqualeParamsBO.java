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
package org.squale.squalecommon.enterpriselayer.businessobject.config;

import java.io.Serializable;

/**
 *<p>
 * BO for the squale-params. Squale-params are parameters which should be persist.
 *</p>
 *<p>
 * The parameters which come from the squale-config.xml are not managed by this BO but by {@link AdminParamsBO}
 *</p>
 * 
 * @hibernate.class table="squale_params" lazy="true"
 */
public class SqualeParamsBO
    implements Serializable
{

    /**
     * UID
     */
    private static final long serialVersionUID = -4103480889159318754L;

    /**
     * List of params
     */
    public enum SqualeParams
    {
        /**
         * The id of the current company
         */
        entityId,

        /**
         * The version of the last reference from the shared repository inserted
         */
        referenceVersion;
    }

    /**
     * Object ID
     */
    private long id;

    /**
     * The parameter key
     */
    private String paramKey;

    /**
     * The parameter value
     */
    private String paramValue;

    /**
     * Constructor
     */
    public SqualeParamsBO()
    {
        id = -1;
        paramKey = "";
        paramValue = "";
    }

    /**
     * Getter method for the paramKey variable.
     * 
     * @return the key of the parameter
     * @hibernate.property name="paramKey" column="paramKey" type="string" not-null="true" update="true" insert="true"
     */
    public String getParamKey()
    {
        return paramKey;
    }

    /**
     * Setter method for the paramKey variable
     * 
     * @param pParamKey The new parameter key value
     */
    public void setParamKey( String pParamKey )
    {
        paramKey = pParamKey;
    }

    /**
     * Getter method for the paramValue variable
     * 
     * @return the value of the parameter
     * @hibernate.property name="paramValue" column="paramaValue" type="string" not-null="true" update="true"
     *                     insert="true"
     */

    public String getParamValue()
    {
        return paramValue;
    }

    /**
     * Setter method for the paramValue variable
     * 
     * @param pParamValue The new value of the parameter
     */
    public void setParamValue( String pParamValue )
    {
        paramValue = pParamValue;
    }

    /**
     * Getter method for the id of the object
     * 
     * @return the ID of the object
     * @hibernate.id generator-class="native" type="long" column="SqualeParamsId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="squaleparams_sequence"
     */
    public long getId()
    {
        return id;
    }

    /**
     * Setter method for the id of the object
     * 
     * @param pId the new id of the object
     */
    public void setId( long pId )
    {
        id = pId;
    }

    /**
     * This method set new values for paramKey and paramVlaue
     * 
     * @param key The new value of paramKey
     * @param value The new value of paramValue
     */
    public void setSqualeParam( String key, String value )
    {
        paramKey = key;
        paramValue = value;
    }

}
