package com.airfrance.squaleweb.applicationlayer.formbean.component;

import java.util.Map;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Représente un profil d'utilisateur sur une application.
 * 
 * @author M400842
 */
public class ProfileForm
    extends RootForm
{

    /**
     * Nom du profil
     */
    private String mName;

    /**
     * Les droits atomiques
     */
    private Map mRights;

    /**
     * @return le nom
     */
    public String getName()
    {
        return mName;
    }

    /**
     * @return les droits atomiques
     */
    public Map getRights()
    {
        return mRights;
    }

    /**
     * @param pName le nom du profil
     */
    public void setName( String pName )
    {
        mName = pName;
    }

    /**
     * @param pRights les droits atomiques
     */
    public void setRights( Map pRights )
    {
        mRights = pRights;
    }

}
