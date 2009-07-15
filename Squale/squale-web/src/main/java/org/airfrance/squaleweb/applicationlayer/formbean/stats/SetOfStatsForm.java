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
package com.airfrance.squaleweb.applicationlayer.formbean.stats;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Form regroupant toutes les applications
 */
public class SetOfStatsForm
    extends RootForm
{

    /** Nombre de jours par défaut pour les statistiques sur les audits terminés */
    public static final int NB_DAYS_FOR_TERMINATED_AUDITS = 90;

    /** Nombre de jours par défaut pour les statistiques sur les audits exécutés */
    public static final int NB_DAYS_FOR_ALL_AUDITS = 10;

    /**
     * La liste des données permettant d'avoir des stats par site
     */
    private List mListOfSiteStatsForm;

    /**
     * La liste des données permettant d'avoir des stats par profil
     */
    private List mListOfProfilsStatsForm;

    /**
     * Objet regroupant les stats sur les audits
     */
    private AuditsStatsForm mAuditsStatsForm;

    /**
     * Objet regroupant les stats sur les facteurs
     */
    private FactorsStatsForm mFactorsStatsForm;

    /**
     * La liste des statistiques par application
     */
    private List mListOfApplicationsStatsForm;

    /** Nombre de jours par défaut pour les statistiques sur les audits exécutés */
    private int mNbDaysForAll = NB_DAYS_FOR_ALL_AUDITS;

    /** Nombre de jours par défaut pour les statistiques sur les audits exécutés */
    private int mNbDaysForTerminated = NB_DAYS_FOR_TERMINATED_AUDITS;

    /**
     * @return La liste des données permettant d'avoir des stats par profil
     */
    public List getListOfProfilsStatsForm()
    {
        return mListOfProfilsStatsForm;
    }

    /**
     * @return La liste des données permettant d'avoir des stats par site
     */
    public List getListOfSiteStatsForm()
    {
        return mListOfSiteStatsForm;
    }

    /**
     * @param pList la nouvelle liste
     */
    public void setListOfProfilsStatsForm( List pList )
    {
        mListOfProfilsStatsForm = pList;
    }

    /**
     * @param pList la nouvelle liste
     */
    public void setListOfSiteStatsForm( List pList )
    {
        mListOfSiteStatsForm = pList;
    }

    /**
     * @return le form contenant les données sur les audits
     */
    public AuditsStatsForm getAuditsStatsForm()
    {
        return mAuditsStatsForm;
    }

    /**
     * @param form le nouveau form
     */
    public void setAuditsStatsForm( AuditsStatsForm form )
    {
        mAuditsStatsForm = form;
    }

    /**
     * @return le form contenant les données sur les audits
     */
    public FactorsStatsForm getFactorsStatsForm()
    {
        return mFactorsStatsForm;
    }

    /**
     * @param form le nouveau form
     */
    public void setFactorsStatsForm( FactorsStatsForm form )
    {
        mFactorsStatsForm = form;
    }

    /**
     * @return les statistiques par application
     */
    public List getListOfApplicationsStatsForm()
    {
        return mListOfApplicationsStatsForm;
    }

    /**
     * @param pList les statistiques par application
     */
    public void setListOfApplicationsStatsForm( List pList )
    {
        mListOfApplicationsStatsForm = pList;
    }

    /**
     * @return le nombre de jours par défaut pour les statistiques sur les audits exécutés
     */
    public int getNbDaysForAll()
    {
        return mNbDaysForAll;
    }

    /**
     * @return le nombre de jours par défaut pour les statistiques sur les audits exécutés
     */
    public int getNbDaysForTerminated()
    {
        return mNbDaysForTerminated;
    }

    /**
     * @param pDays le nombre de jours par défaut pour les statistiques sur les audits exécutés
     */
    public void setNbDaysForAll( int pDays )
    {
        mNbDaysForAll = pDays;
    }

    /**
     * @param pDays nombre de jours par défaut pour les statistiques sur les audits exécutés
     */
    public void setNbDaysForTerminated( int pDays )
    {
        mNbDaysForTerminated = pDays;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest)
     */
    public void wValidate( ActionMapping arg0, HttpServletRequest arg1 )
    {
        // On teste si les entrées utilisateur sont correctes
        if ( getNbDaysForAll() <= 0 )
        {
            addError( "nbDaysForAll", "stat.nbDaysForAll.error" );
        }
        if ( getNbDaysForTerminated() <= 0 )
        {
            addError( "nbDaysForTerminated", "stat.nbDaysTerminated.error" );
        }
    }

}
