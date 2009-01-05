/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
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
