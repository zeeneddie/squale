package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;

/**
 * Module d'une règle checkstyle
 * 
 * @hibernate.class table="Module" mutable="true"
 */

public class CheckstyleModuleBO
    implements Serializable
{
    /**
     * Identifiant (au sens technique) de l'objet
     */
    protected long mId = -1;

    /**
     * Version
     */
    protected String mName;

    /**
     * Version
     */
    protected String mMessage;

    /**
     * Règle du module
     */
    protected RuleBO mRule;

    /**
     * Severite de la Règle. cet attribut n'est pas persister c'est celui de RuleBO.
     */
    protected String mSeverity;

    /**
     * Constucteur par défaut
     */
    public CheckstyleModuleBO()
    {
        mId = -1;

    }

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property Note: unsaved-value An identifier property value that indicates
     *         that an instance is newly instantiated (unsaved), distinguishing it from transient instances that were
     *         saved or loaded in a previous session. If not specified you will get an exception like this: another
     *         object associated with the session has the same identifier
     * @hibernate.id generator-class="native" type="long" column="ModuleId" unsaved-value="-1" length="19"
     * @hibernate.generator-param name="sequence" value="Module_sequence"
     */
    public long getId()
    {
        return mId;
    }

    /**
     * Sets the value of the mId property.
     * 
     * @param pId the new value of the mId property
     */

    public void setId( long pId )
    {
        mId = pId;
    }

    /**
     * Access method for the mMessage property.
     * 
     * @return the current value of the mMessage property
     * @hibernate.property name="Message" column="Message" type="string" not-null="false" unique="false"
     */
    public String getMessage()
    {
        return mMessage;
    }

    /**
     * Sets the value of the mMessage property.
     * 
     * @param pMessage the new value of the mMessage property
     */
    public void setMessage( String pMessage )
    {
        mMessage = pMessage;
    }

    /**
     * Access method for the mName property.
     * 
     * @return the current value of the mName property
     * @hibernate.property name="Name" column="Name" type="string" not-null="false" unique="false"
     */

    public String getName()
    {
        return mName;
    }

    /**
     * Sets the value of the mName property.
     * 
     * @param pName the new value of the mName property
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * Access method for the mRule property.
     * 
     * @return the current Rule
     * @hibernate.many-to-one name="rule" column="RuleId"
     *                        class="com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.checkstyle.CheckstyleRuleBO"
     *                        cascade="save-update" not-null="true"
     */
    public RuleBO getRule()
    {
        return mRule;
    }

    /**
     * Sets the value of the mRule property.
     * 
     * @param pRule the new value of the mRule property
     */
    public void setRule( RuleBO pRule )
    {
        mRule = pRule;
    }

    /**
     * Access method for the mSeverity property.
     * 
     * @return the current value of the mSeverity property
     */
    public String getSeverity()
    {
        return mSeverity;
    }

    /**
     * Sets the value of the mSeverity property.
     * 
     * @param pSeverity the new value of the mSeverity property
     */
    public void setSeverity( String pSeverity )
    {
        mSeverity = pSeverity;
    }

    /**
     * Rajoute les propriétes (severity,message) du module
     * 
     * @param pNameProperty nom de la proprité
     * @param pValueProperty la valeur de la propriété
     */
    public void addProperty( String pNameProperty, String pValueProperty )
    {

        if ( pNameProperty.equals( "severity" ) )
        {
            setSeverity( pValueProperty );

        }
        else if ( pNameProperty.equals( "message" ) )
        {
            setMessage( pValueProperty );

        }
        else if ( pNameProperty.equals( "maximumMessage" ) )
        {
            setMessage( pValueProperty );
        }
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     * @roseuid 42E617A903AE
     */
    public boolean equals( Object pObj )
    {
        boolean ret = false;
        CheckstyleModuleBO component = null;
        if ( pObj instanceof CheckstyleModuleBO )
        {
            component = (CheckstyleModuleBO) pObj;
            EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append( mName, component.getName() );
            equalsBuilder.append( mId, component.getId() );
            equalsBuilder.append( mMessage, component.getMessage() );
            ret = equalsBuilder.isEquals();

        }
        return ret;
    }

    /**
     * @see java.lang.Object#hashCode()
     * @roseuid 42E617A903BD
     */
    public int hashCode()
    {
        HashCodeBuilder hashBuilder = new HashCodeBuilder();
        hashBuilder.append( mName );
        hashBuilder.append( mId );
        hashBuilder.append( mMessage );
        return hashBuilder.toHashCode();
    }

}
