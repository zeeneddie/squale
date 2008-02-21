package com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters;

/**
 * @hibernate.class 
 * table="ProjectParameter"
 * mutable="true"
 * discriminator-value="ProjectParameter"
 * @hibernate.discriminator 
 * column="subclass"
 */
abstract public class ProjectParameterBO {

    /**
     * Identifiant de l'objet
     */
    private long mId = -1;

    /**
     * Access method for the mId property.
     * 
     * @return   the current value of the mId property
     * 
     * Note: unsaved-value An identifier property value that indicates that an instance 
     * is newly instantiated (unsaved), distinguishing it from transient instances that 
     * were saved or loaded in a previous session.  If not specified you will get an exception like this:
     * another object associated with the session has the same identifier
     * 
     * @hibernate.id generator-class="native"
     * type="long" 
     * column="ParameterId" 
     * unsaved-value="-1" 
     * length="19"
     * @hibernate.generator-param name="sequence" value="project_parameter_sequence"
     * 
     */
    public long getId() {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */
    public void setId(long pId) {
        mId = pId;
    }
}
