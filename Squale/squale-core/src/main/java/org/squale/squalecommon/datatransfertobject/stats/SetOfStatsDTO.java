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
package org.squale.squalecommon.datatransfertobject.stats;

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
