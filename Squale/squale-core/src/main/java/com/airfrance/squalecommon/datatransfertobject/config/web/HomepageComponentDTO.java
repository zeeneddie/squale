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
package com.airfrance.squalecommon.datatransfertobject.config.web;

import com.airfrance.jraf.spi.dto.IDTO;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;

/**
 *  DTO class for the HomepageComponent
 *
 */
public class HomepageComponentDTO implements IDTO
{

    /**
     * The number of component displayable
     */
    public static final int ELEMENT = 5;

    // ELEMENT 1 The introduction part
    /**
     * The name in base of the introduction element
     */
    public static final String INTRO = "introduction";

    // ELEMENT 2 The news part
    /**
     * The name in base of the news element
     */
    public static final String NEWS = "news";

    // ELEMENT 3 The audits part
    /**
     * The name in base of the audit element
     */
    public static final String AUDIT = "audit";

    /**
     * The name in base of the auditDone element
     */
    public static final String AUDIT_DONE = "auditDone";

    /**
     * The name in base of the audit scheduled element
     */
    public static final String AUDIT_SCHEDULED = "auditScheduled";

    /**
     * The name in base of the audit successful element
     */
    public static final String AUDIT_SUCCESSFUL = "auditSucessful";

    /**
     * The name in base of the audit successful element
     */
    public static final String AUDIT_PARTIAL = "auditPartial";

    /**
     * The name in base of the audit partial element
     */
    public static final String AUDIT_FAILED = "auditFailed";

    /**
     * The number of day for display audit
     */
    public static final String AUDIT_NB_JOURS = "auditNbJours";
    
    /**Default nb jours */
    public static final String DEFAULT_AUDIT_NB_JOURS ="15";

    /**
     * The name in base of the audit done during the XXX last days element
     */
    public static final String AUDIT_SHOW_SEPARETELY = "auditShowSeparatelty";

    // ELEMENT 4 The results part
    /**
     * The name in base of the result element
     */
    public static final String RESULT = "result";

    /**
     * The name in base of the result by grid element
     */
    public static final String RESULT_BY_GRID = "resultByGrid";

    /**
     * The name in base of the result kiviat element
     */
    public static final String RESULT_KIVIAT = "resultKiviat";
    
    /** The width of one kiviat */
    public static final String KIVIAT_WIDTH = "kiviatWidth";
    
    /** Default width of one kiviat */
    public static final String DEFAULT_KIVIAT_WIDTH = "240";
    
    // ELEMENT 5 The statistics part
    /**
     * The name in base of the statistics element
     */
    public static final String STAT = "statistics";

    /**
     * Technical id
     */
    private Long id;

    /**
     * Name of the component which correspond to one of the static final variables define in this class
     */
    private String componentName;

    /**
     * The user link to this component
     */
    private UserDTO user;

    /**
     * position of the element 0 if the element has no position else 1,2,3,...
     */
    private int componentPosition;

    /**
     * value of the component parameter
     */
    private String componentValue;

    /**
     * Default constructor
     */
    public HomepageComponentDTO()
    {

    }

    /**
     * Constructor By default this constructor set the parameter position to 0
     * 
     * @param name Name of the component. This name should correspond to one of the static variable define in this class
     */
    public HomepageComponentDTO( String name )
    {
        componentName = name;
        componentPosition = 0;
    }

    /**
     * Constructor By default this constructor set the parameter position to 0
     * 
     * @param name Name of the component. This name should correspond to one of the static variable define in this class
     * @param value Value for the component
     */
    public HomepageComponentDTO( String name, String value )
    {
        componentName = name;
        componentValue = value;
        componentPosition = 0;
    }

    /**
     * Getter method for the ID
     * 
     * @return the ID of the object
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Setter method for the ID of the object
     * 
     * @param pId The new ID of the object
     */
    public void setId( Long pId )
    {
        id = pId;
    }

    /**
     * Getter method for the component of the object
     * 
     * @return the name of the component
     * @hibernate.property name="componentName" column="ComponentName" type="string" update="true" insert="true"
     *                     not-null="true"
     */
    public String getComponentName()
    {
        return componentName;
    }

    /**
     * Setter method for the component of the object
     * 
     * @param pComponentName The new name of the component
     */
    public void setComponentName( String pComponentName )
    {
        componentName = pComponentName;
    }

    /**
     * Getter method for the user parameter
     * 
     * @return the user
     */
    public UserDTO getUser()
    {
        return user;
    }

    /**
     * Setter method for the user parameter
     * 
     * @param pUser The new user
     */
    public void setUser( UserDTO pUser )
    {
        user = pUser;
    }

    /**
     * Getter method for the position of the object It returns 0 if the object has no position
     * 
     * @return The position of the object
     */
    public int getComponentPosition()
    {
        return componentPosition;
    }

    /**
     * Setter method for the position of the object
     * 
     * @param pComponentPosition The new position
     */
    public void setComponentPosition( int pComponentPosition )
    {
        componentPosition = pComponentPosition;
    }

    /**
     * Getter method for the value of the object
     * 
     * @return The value of the object
     */
    public String getComponentValue()
    {
        return componentValue;
    }

    /**
     * Setter method for the value of the object Value "true" and "false" are reserved for keep element of type checkbox
     * 
     * @param pComponentValue The new value
     */
    public void setComponentValue( String pComponentValue )
    {
        componentValue = pComponentValue;
    }

}
