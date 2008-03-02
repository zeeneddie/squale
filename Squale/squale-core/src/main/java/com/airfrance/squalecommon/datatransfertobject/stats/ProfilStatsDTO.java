package com.airfrance.squalecommon.datatransfertobject.stats;

/**
 */
public class ProfilStatsDTO
    extends CommonStatsDTO
{

    /**
     * Le nom du profil
     */
    private String mProfileName;

    /**
     * @return le nom du profil
     */
    public String getProfileName()
    {
        return mProfileName;
    }

    /**
     * @param pProfileName le nouveau nom du profile
     */
    public void setProfileName( String pProfileName )
    {
        mProfileName = pProfileName;
    }

}
