package com.airfrance.squalecommon.datatransfertobject.result;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * 
 */
public class MarkDTO
{

    /** la note */
    private float mValue;

    /**
     * Composant possédant la note.
     */
    private ComponentDTO mComponent;

    /**
     * @return le composant
     */
    public ComponentDTO getComponent()
    {
        return mComponent;
    }

    /**
     * @return la note
     */
    public float getValue()
    {
        return mValue;
    }

    /**
     * @param pComponentDTO le composant
     */
    public void setComponent( ComponentDTO pComponentDTO )
    {
        mComponent = pComponentDTO;
    }

    /**
     * @param pMark la note
     */
    public void setValue( float pMark )
    {
        mValue = pMark;
    }

}
