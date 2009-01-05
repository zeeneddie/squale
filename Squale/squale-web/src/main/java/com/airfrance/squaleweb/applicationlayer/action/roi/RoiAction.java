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
package com.airfrance.squaleweb.applicationlayer.action.roi;

import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.result.GraphDTO;
import com.airfrance.squalecommon.datatransfertobject.result.RoiDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.AdminAction;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.RoiTransformer;
import com.airfrance.squaleweb.util.graph.AreaMaker;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;

/**
 * Action pour le calcul du retour sur investissement
 */
public class RoiAction
    extends AdminAction
{

    /**
     * Récupère le ROI.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward display( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                  HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        ActionForm theForm = pForm; 
        try
        {
            forward = pMapping.findForward( "show" );
            // Préparation de l'appel à la couche métier
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "ROI" );
            // On récupère le ROI sous forme de DTO
            RoiDTO roiDto = (RoiDTO) ac.execute( "getROI", new Object[] { new Long( -1 ) } );
            // On transforme le form
            theForm = WTransformerFactory.objToForm( RoiTransformer.class, roiDto );
            pRequest.getSession().setAttribute( "roiForm", theForm );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Recalcule le ROI avec une nouvelle valeur du j.h en K€.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward setKEuros( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                    HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            // juste pour recharger le form en session
            forward = pMapping.findForward( "show" );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Récupère le total du ROI pour une application.
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward changeApplication( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                            HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "show" );
            // Préparation de l'appel à la couche métier
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "ROI" );
            // On récupère le ROI sous forme de DTO
            RoiDTO roiDto = (RoiDTO) WTransformerFactory.formToObj( RoiTransformer.class, (WActionForm) pForm )[0];
            // On le modifie avec la nouvelle application
            roiDto = (RoiDTO) ac.execute( "getROI", new Object[] { new Long( roiDto.getApplicationId() ) } );
            // On transforme le form
            ActionForm newForm = WTransformerFactory.objToForm( RoiTransformer.class, roiDto );
            pRequest.getSession().setAttribute( "roiForm", newForm );
        }
        catch ( Exception e )
        {
            handleException( e, errors, pRequest );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Change la formule
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward updateFormula( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                        HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            forward = pMapping.findForward( "show" );
            // Préparation de l'appel à la couche métier
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "ROI" );
            RoiDTO roiDto = (RoiDTO) WTransformerFactory.formToObj( RoiTransformer.class, (WActionForm) pForm )[0];
            // Si la formule n'est pas définie, on redirige vers la page courante et un message le signalera
            if ( null != roiDto.getFormula() && !"".equals( roiDto.getFormula() ) )
            {
                StringBuffer acErrors = new StringBuffer();
                roiDto = (RoiDTO) ac.execute( "updateFormula", new Object[] { roiDto, acErrors } );
                // On transforme le form
                ActionForm newForm = WTransformerFactory.objToForm( RoiTransformer.class, roiDto );
                pRequest.getSession().setAttribute( "roiForm", newForm );
                if ( acErrors.length() > 0 )
                {
                    // Affichage des messages d'erreur
                    ActionMessage error = new ActionMessage( "welcom.internal.error.msg", acErrors.toString() );
                    errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                    forward = pMapping.findForward( "fail" );
                }
            }
        }
        catch ( Exception e )
        {
            // Traitement des exceptions
            handleException( e, errors, pRequest );
            forward = pMapping.findForward( "total_failure" );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
        }
        return forward;
    }

    /**
     * Visualisation du graphe représentant le ROI pour toutes les applications ou pour une application en particulier
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward viewRoiChart( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                       HttpServletResponse pResponse )
    {

        // Cette méthode est appelée via un tag html img avec
        // un src qui pointe vers une action struts
        ActionForward forward;
        try
        {
            // Préparation de l'appel à la couche métier
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Graph" );
            RoiDTO roiDto = (RoiDTO) WTransformerFactory.formToObj( RoiTransformer.class, (WActionForm) pForm )[0];
            String xLabel = WebMessages.getString( pRequest, "roi.graph.xTitle" );
            String yLabel = WebMessages.getString( pRequest, "roi.graph.yTitle" );
            Object[] paramIn = { roiDto };
            // Appel de la couche métier
            Map values = (Map) ac.execute( "getRoiGraph", paramIn );
            AreaMaker areaMaker = new AreaMaker( "ROI", xLabel, yLabel, pRequest.getLocale() );
            areaMaker.addCurve( "", values );
            GraphDTO graphDto = new GraphDTO();
            graphDto.setImage( areaMaker.getImageInBytes() );
            ServletOutputStream out = pResponse.getOutputStream();
            if ( null != graphDto && null != graphDto.getImage() )
            {
                pResponse.setContentType( "image/png" );
                // Ecriture de l'image
                out.write( graphDto.getImage() );
            }
            // Fermeture des flux
            out.flush();
            out.close();
            // Renvoi vers un forward null car la réponse est fermée
            forward = null;
        }
        catch ( Exception e )
        {
            ActionErrors errors = new ActionErrors();
            // Factorisation des exceptions
            handleException( e, errors, pRequest );
            // Sauvegarde des erreurs
            saveErrors( pRequest, errors );
            // Routage vers la page d'erreur
            forward = pMapping.findForward( "failure" );
        }
        return forward;
    }

}
