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
package com.airfrance.squaleweb.applicationlayer.action.component;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.util.SqualeCommonConstants;
import com.airfrance.squalecommon.util.SqualeCommonUtils;
import com.airfrance.squalecommon.util.mail.MailerHelper;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ServeurListForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ApplicationConfTransformer;
import com.airfrance.squaleweb.transformer.ServeurListTransformer;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

import java.util.Collection;

/**
 * @version 1.0
 * @author
 */
public class CreateApplicationAction
    extends DefaultAction
{

    /**
     * @param pMapping le mapping.
     * @param pForm le formulaire à lire.
     * @param pRequest la requête HTTP.
     * @param pResponse la réponse de la servlet.
     * @return l'action à réaliser.
     */
    public ActionForward create( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                 HttpServletResponse pResponse )
    {

        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        try
        {
            // On récupère le formulaire de création de l'application
            CreateApplicationForm form = (CreateApplicationForm) pForm;
            // On vérifie que le nom de l'application est valide
            if ( null != form.getApplicationName() && form.getApplicationName().trim().length() > 0 )
            {
                ApplicationConfDTO application = new ApplicationConfDTO();
                // On change le nom de l'utilisateur
                application.setLastUser( ( (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY ) ).getMatricule() );
                application.setLastUpdate( Calendar.getInstance().getTime() );
                application.setName( form.getApplicationName() );

                // L'utilisateur peut ne pas exister dans la base, on le crée donc si besoin
                // Recupération du user pour sa creation eventuelle dans la base
                UserDTO user = getUserAsDTO( pRequest );
                IApplicationComponent acLogin = AccessDelegateHelper.getInstance( "Login" );
                Object[] paramInLogin = { user, Boolean.valueOf( isUserAdmin( pRequest ) ) };
                user = (UserDTO) acLogin.execute( "verifyUser", paramInLogin );
                IApplicationComponent ac = AccessDelegateHelper.getInstance( "ApplicationAdmin" );
                Object[] paramIn = { application, user };

                // Création de l'application
                application = ( (ApplicationConfDTO) ac.execute( "createApplication", paramIn ) );

                if ( null == application )
                {
                    ActionMessage error = new ActionMessage( "application_creation.name_already_exists" );
                    errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                    forward = pMapping.findForward( "failure" );
                    saveMessages( pRequest, errors );
                }
                else
                {
                    // met l'id de l'application nouvellement crée en requete
                    // car on en a besoin pour les actions suivantes
                    pRequest.setAttribute( "applicationId", "" + application.getId() );
                    // On met à jour le form
                    Object[] paramIn2 = { application };
                    CreateApplicationForm form2 =
                        (CreateApplicationForm) WTransformerFactory.objToForm( ApplicationConfTransformer.class,
                                                                               paramIn2 );
                    // On ne met à jour que l'id, les droits et le serveur sinon les valeurs par défaut
                    // attribuées sur la form sont écrasées avec les valeurs par défaut de java
                    form.setApplicationId( form2.getApplicationId() );
                    form.setRights( form2.getRights() );
                    form.setServeurForm( form2.getServeurForm() );

                    // Mise à jour de la form dans la session
                    pRequest.getSession().setAttribute( "createApplicationForm", form );
                    // On recharge les profils de l'utilisateur
                    ActionUtils.refreshUser( pRequest );
                    // chargement de la liste des serveurs
                    IApplicationComponent acServeur = AccessDelegateHelper.getInstance( "Serveur" );
                    Collection lListeServeurDTO = (Collection) acServeur.execute( "listeServeurs" );
                    ServeurListForm lListeServeurForm = new ServeurListForm();
                    WTransformerFactory.objToForm( ServeurListTransformer.class, lListeServeurForm, lListeServeurDTO );
                    pRequest.setAttribute( "listeServeur", lListeServeurForm );
                    // initialisation en session du prochain audit de suivi programmé
                    pRequest.getSession().setAttribute( "auditForm2", null );

                    // Redirection vers la page de configuration
                    forward = pMapping.findForward( "config_application" );
                    // Envoi d'un mail aux administrateurs pour leur signaler qu'une application
                    // supplémentaire a été créée et doit donc etre validée
                    String sender = WebMessages.getString( getLocale( pRequest ), "mail.sender.squale" );
                    String header = WebMessages.getString( getLocale( pRequest ), "mail.headerForAdmin" );
                    String object = sender + WebMessages.getString( pRequest, "mail.appli.to_valid.object" );
                    SimpleDateFormat formator = new SimpleDateFormat( "yyyy-MM-dd" );
                    String today = formator.format( Calendar.getInstance().getTime() );
                    Object[] params = { application.getName(), today, user.getMatricule() };
                    String content =
                        header
                            + MessageFormat.format( WebMessages.getString( pRequest, "mail.appli.to_valid.content" ),
                                                    params );
                    SqualeCommonUtils.notifyByEmail( MailerHelper.getMailerProvider(),
                                                     SqualeCommonConstants.ONLY_ADMINS, null, object, content, false );
                }
            }
            else
            {
                ActionMessage error = new ActionMessage( "error.invalid_name" );
                errors.add( ActionMessages.GLOBAL_MESSAGE, error );
                forward = pMapping.findForward( "failure" );
                saveMessages( pRequest, errors );
            }
        }
        catch ( Exception e )
        {
            // Traitement factorisé des exceptions et transfert vers la page d'erreur
            handleException( e, errors, pRequest );
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        // On est passé par un menu donc on réinitialise le traceur
        resetTracker( pRequest );

        return ( forward );
    }

}
