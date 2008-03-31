package com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters;

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
     * @hibernate.property name="Value" column="Value" type="string" not-null="false" unique="false"
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
