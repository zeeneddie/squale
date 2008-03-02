package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Form bean for a Struts application.
 * 
 * @version 1.0
 * @author
 */
public class ApplicationRightsForm
    extends RootForm
{
    /**
     * Matricule de l'utilisateur
     */
    private String[] mMatricule = null;

    /**
     * Profil associé de l'utilisateur
     */
    private String[] mRightProfile = null;

    /**
     * @return le matricule
     */
    public String[] getMatricule()
    {
        return mMatricule;
    }

    /**
     * @return le profil
     */
    public String[] getRightProfile()
    {
        return mRightProfile;
    }

    /**
     * @param pMatricule le matricule
     */
    public void setMatricule( String[] pMatricule )
    {
        mMatricule = pMatricule;
    }

    /**
     * @param pRightProfile le profil
     */
    public void setRightProfile( String[] pRightProfile )
    {
        mRightProfile = pRightProfile;
    }

}
