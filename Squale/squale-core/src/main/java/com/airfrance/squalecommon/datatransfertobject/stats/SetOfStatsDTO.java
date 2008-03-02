package com.airfrance.squalecommon.datatransfertobject.stats;

import java.util.List;

/**
 */
public class SetOfStatsDTO
{

    /**
     * La liste des données permettant d'avoir des stats par site
     */
    private List mListOfSiteStatsDTO;

    /**
     * La liste des données permettant d'avoir des stats par profil
     */
    private List mListOfProfilsStatsDTO;

    /**
     * Objet regroupant les stats sur les audits
     */
    private AuditsStatsDTO mAuditsStatsDTO;

    /**
     * Objet regroupant les stats sur les facteurs
     */
    private FactorsStatsDTO mFactorsStatsDTO;

    /**
     * La liste des statistiques par application
     */
    private List mListOfApplicationsStatsDTO;

    /**
     * @return La liste des données permettant d'avoir des stats par profil
     */
    public List getListOfProfilsStatsDTO()
    {
        return mListOfProfilsStatsDTO;
    }

    /**
     * @return La liste des données permettant d'avoir des stats par site
     */
    public List getListOfSiteStatsDTO()
    {
        return mListOfSiteStatsDTO;
    }

    /**
     * @param pList la nouvelle liste
     */
    public void setListOfProfilsStatsDTO( List pList )
    {
        mListOfProfilsStatsDTO = pList;
    }

    /**
     * @param pList la nouvelle liste
     */
    public void setListOfSiteStatsDTO( List pList )
    {
        mListOfSiteStatsDTO = pList;
    }

    /**
     * @return l'objet contenant les stats sur les audits
     */
    public AuditsStatsDTO getAuditsStatsDTO()
    {
        return mAuditsStatsDTO;
    }

    /**
     * @param pStatsDTO le nouvel objet contenant les stats sur les audits
     */
    public void setAuditsStatsDTO( AuditsStatsDTO pStatsDTO )
    {
        mAuditsStatsDTO = pStatsDTO;
    }

    /**
     * @return l'objet contenant les stats sur les facteurs
     */
    public FactorsStatsDTO getFactorsStatsDTO()
    {
        return mFactorsStatsDTO;
    }

    /**
     * @param statsDTO l'objet contenant les stats sur les facteurs
     */
    public void setFactorsStatsDTO( FactorsStatsDTO statsDTO )
    {
        mFactorsStatsDTO = statsDTO;
    }

    /**
     * @return la liste des statistiques par application
     */
    public List getListOfApplicationsStatsDTO()
    {
        return mListOfApplicationsStatsDTO;
    }

    /**
     * @param pList la liste des statistiques par application
     */
    public void setListOfApplicationsStatsDTO( List pList )
    {
        mListOfApplicationsStatsDTO = pList;
    }
}
