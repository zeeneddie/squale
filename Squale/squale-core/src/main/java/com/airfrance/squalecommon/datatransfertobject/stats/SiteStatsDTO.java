package com.airfrance.squalecommon.datatransfertobject.stats;

import com.airfrance.squalecommon.datatransfertobject.config.ServeurDTO;

/**
 */
public class SiteStatsDTO
    extends CommonStatsDTO
{

    /**
     * Le nom du serveur
     */
    private ServeurDTO mServeurDTO;

    /**
     * Le nombre d'applications avec des audits réussis
     */
    private int nbAppliWithAuditsSuccessful;

    /**
     * Le nombre d'applications avec des audits mais aucun réussi
     */
    private int nbAppliWithoutSuccessfulAudits;

    /**
     * Le nombre d'applications validées (<=> sans audits)
     */
    private int nbValidatedApplis;

    /**
     * Le nombre d'applications à valider
     */
    private int nbAppliToValidate;

    /**
     * Constructeur par défaut
     */
    public SiteStatsDTO()
    {
        mServeurDTO = new ServeurDTO();
    }

    /**
     * @return le nombre d'appli à valider
     */
    public int getNbAppliToValidate()
    {
        return nbAppliToValidate;
    }

    /**
     * @return le nombre d'appli avec des audits réussis
     */
    public int getNbAppliWithAuditsSuccessful()
    {
        return nbAppliWithAuditsSuccessful;
    }

    /**
     * @return le nombre d'applis avec des audits mais aucun réussi
     */
    public int getNbAppliWithoutSuccessfulAudits()
    {
        return nbAppliWithoutSuccessfulAudits;
    }

    /**
     * @return le nombre d'applis validés
     */
    public int getNbValidatedApplis()
    {
        return nbValidatedApplis;
    }

    /**
     * @param pNbAppli le nombre d'applis à valider
     */
    public void setNbAppliToValidate( int pNbAppli )
    {
        nbAppliToValidate = pNbAppli;
    }

    /**
     * @param pNbAppli le nombre d'appli avec des audits réussis
     */
    public void setNbAppliWithAuditsSuccessful( int pNbAppli )
    {
        nbAppliWithAuditsSuccessful = pNbAppli;
    }

    /**
     * @param pNbAppli le nombre d'appli sans audits réussis
     */
    public void setNbAppliWithoutSuccessfulAudits( int pNbAppli )
    {
        nbAppliWithoutSuccessfulAudits = pNbAppli;
    }

    /**
     * @param pNbAppli le nombre d'applis validés
     */
    public void setNbValidatedApplis( int pNbAppli )
    {
        nbValidatedApplis = pNbAppli;
    }

    /**
     * @return le nom du serveur
     */
    public ServeurDTO getServeurDTO()
    {
        return mServeurDTO;
    }

    /**
     * @param pServeurDTO le nom du serveur
     */
    public void setServeurDTO( ServeurDTO pServeurDTO )
    {
        mServeurDTO = pServeurDTO;
    }

}
