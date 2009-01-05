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
package com.airfrance.squaleweb.applicationlayer.action.config;

import java.io.InputStream;

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
import com.airfrance.squalecommon.enterpriselayer.facade.config.adminParams.MailConfigFacade;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.AdminAction;
import com.airfrance.squaleweb.applicationlayer.formbean.UploadFileForm;
import com.airfrance.squaleweb.servlet.UserSqualeSessionContext;

/**
 * Importation d'une configuratiopn Squalix
 */
public class ConfigCreationAction
    extends AdminAction
{

    /**
     * Importation d'un fichier de configuration Squalix
     * 
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward execute( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                  HttpServletResponse pResponse )
    {
        // On va créer la configuration en traitant les erreurs lors du paring ou de l'enregistrement en base.
        ActionErrors errors = new ActionErrors();
        ActionForward forward;
        try
        {
            UploadFileForm form = (UploadFileForm) pForm;
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "SqualixConfig" );
            StringBuffer acErrors = new StringBuffer();
            InputStream is = form.getInputStream();
            // On importe la configuration définie dans le fichier
            ac.execute( "importConfig", new Object[] { is, acErrors } );
            if ( acErrors.length() > 0 )
            {
                // Affichage des messages d'erreur
                ActionMessage error = new ActionMessage( "config_import.errors", acErrors.toString() );
                errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                forward = pMapping.findForward( "fail" );
            }
            else
            {
                is = form.getInputStream();
                // On crée la configuration
                ac.execute( "createConfig", new Object[] { is, acErrors } );
                if ( acErrors.length() > 0 )
                {
                    // Affichage des messages d'erreur
                    ActionMessage error = new ActionMessage( "config_insert_data.errors", acErrors.toString() );
                    errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                    forward = pMapping.findForward( "fail" );
                }
                else
                {
                    IApplicationComponent ac1 = AccessDelegateHelper.getInstance( "Mail" );
                    String mailingList = ac1.execute( "getAdminMailingList" ).toString();
                    UserSqualeSessionContext sessionContext = UserSqualeSessionContext.getContext( pRequest.getSession() );
                    sessionContext.setSqualeAdminsMailingList( mailingList );
                    UserSqualeSessionContext.setContext( pRequest.getSession(), sessionContext );
                    forward = pMapping.findForward( "success" );
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
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );
        return forward;
    }
}
