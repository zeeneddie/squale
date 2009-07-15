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
package com.airfrance.squaleweb.applicationlayer.action.stats;

import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.stats.SetOfStatsDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.action.export.xls.ExcelDataApplicationsStatsList;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.stats.SetOfStatsForm;
import com.airfrance.squaleweb.transformer.stats.SetOfStatsTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.outils.excel.ExcelFactory;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 */
public class StatsAction
    extends DefaultAction
{

    /**
     * Méthode redirigant vers la page des stats pour un utilisateur lambda
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward displayUser( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                      HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "displayUser" );
            populateForm( pForm, pRequest );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Méthode récupérant les stats et initialisant le form correspondant. Factorisation de code pour les 2 actions
     * renvoyant sur 2 jsps différentes suivant si l'utilisateur est admin ou pas
     * 
     * @param pForm le formulaire à initialiser
     * @param pRequest la requête
     * @throws JrafEnterpriseException en cas d'échec de récupération des données
     * @throws WTransformerException en cas de problème de transformation de l'objet
     */
    private void populateForm( ActionForm pForm, HttpServletRequest pRequest )
        throws JrafEnterpriseException, WTransformerException
    {

        Object[] params =
            { new Integer( ( (SetOfStatsForm) pForm ).getNbDaysForTerminated() ),
                new Integer( ( (SetOfStatsForm) pForm ).getNbDaysForAll() ) };
        // Préparation de l'appel à la couche métier
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Stats" );
        // On récupère l'objet regroupant les données stats sous forme de dto
        SetOfStatsDTO statsDto = (SetOfStatsDTO) ac.execute( "getStats", params );
        // On transforme le form
        WTransformerFactory.objToForm( SetOfStatsTransformer.class, (WActionForm) pForm, new Object[] { statsDto,
            pRequest.getLocale() } );
    }

    /**
     * Méthode redirigant vers la page des stats pour un utilisateur admin
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward displayAdmin( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                       HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "displayAdmin" );
            populateForm( pForm, pRequest );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Exporte les statistiques niveau application sous excel
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return null.
     * @throws ServletException si erreur durant le traitement
     */
    public ActionForward ApplicationsStatsExportExcel( ActionMapping pMapping, ActionForm pForm,
                                                       HttpServletRequest pRequest, HttpServletResponse pResponse )
        throws ServletException
    {
        try
        {
            LogonBean user = (LogonBean) getWILogonBean( pRequest );
            ExcelDataApplicationsStatsList data =
                new ExcelDataApplicationsStatsList( pRequest.getLocale(), getResources( pRequest ),
                                                    ( (SetOfStatsForm) pForm ).getListOfApplicationsStatsForm(),
                                                    user.getMatricule() );
            String today =
                SqualeWebActionUtils.getFormattedDate( pRequest.getLocale(), Calendar.getInstance().getTime(),
                                                       "date.format.simple.underscore" );
            ExcelFactory.generateExcelToHTTPResponse( data, pResponse, "squaleDetailReport" + today + ".xls" );
        }
        catch ( Exception e )
        {
            throw new ServletException( e );
        }
        return null;
    }
}
