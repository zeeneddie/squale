package com.airfrance.squalecommon.enterpriselayer.businessobject.config.web;

import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;

/**
 * BO class for the HomepageComponent
 * 
 * @hibernate.class table="HomepageComponent" mutable="true"
 */
public class HomepageComponentBO
{

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
    private UserBO user;

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
    public HomepageComponentBO()
    {

    }

    /**
     * Getter method for the ID
     * 
     * @return the ID of the object
     * @hibernate.id generator-class="native" type="long" column="HomepageComponentId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="HomepageComponent_sequence"
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
     * @return the user link to this object
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO"
     *                        column="UserBO" not-null="true" cascade="none" outer-join="auto" update="true"
     *                        insert="true"
     */
    public UserBO getUser()
    {
        return user;
    }

    /**
     * Setter method for the user parameter
     * 
     * @param pUser The new user
     */
    public void setUser( UserBO pUser )
    {
        user = pUser;
    }

    /**
     * Getter method for the position of the object It returns 0 if the object has no position
     * 
     * @return The position of the object
     * @hibernate.property name="componentPosition" column="ComponentPosition" type="int" update="true" insert="true"
     *                     not-null="true"
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
     * @hibernate.property name="componentValue" column="ComponentValue" type="string" update="true" insert="true"
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
