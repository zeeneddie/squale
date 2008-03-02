package com.airfrance.squalecommon.datatransfertobject.component.parameters;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class MapParameterDTO
{

    /**
     * La map de paramètres
     */
    private Map mParameters = new HashMap();

    /**
     * Identifiant de l'objet
     */
    private long mId = -1;

    /**
     * Access method for the mId property.
     * 
     * @return the current value of the mId property
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
     * @return la map de paramètres
     */
    public Map getParameters()
    {
        return mParameters;
    }

    /**
     * @param pMap la nouvelle map de parametres
     */
    public void setParameters( Map pMap )
    {
        mParameters = pMap;
    }

}
