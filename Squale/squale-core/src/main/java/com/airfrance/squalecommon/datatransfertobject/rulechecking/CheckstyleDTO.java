package com.airfrance.squalecommon.datatransfertobject.rulechecking;

import java.io.Serializable;

/**
 * @author S Porto-Rico
 */
public class CheckstyleDTO
    extends RuleSetDTO
    implements Serializable
{
    /**
     * Contenu au format binaire du fichier de confuguration
     */
    protected byte[] mBytes;

    /**
     * Access method for the mBytes property.
     * 
     * @return the current value of the FileName property
     */
    public byte[] getBytes()
    {
        return mBytes;
    }

    /**
     * Sets the value of the mBytes property.
     * 
     * @param pBytes the new value of the mByte property
     */
    public void setBytes( byte[] pBytes )
    {
        mBytes = pBytes;
    }
}
