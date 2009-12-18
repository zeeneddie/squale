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
package org.squale.squaleweb.applicationlayer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;

import org.squale.squalecommon.datatransfertobject.component.UserDTO;

import org.squale.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.UserForm;
import org.squale.squaleweb.connection.AuthenticationBean;
import org.squale.squaleweb.connection.IUserBeanAccessor;
import org.squale.squaleweb.connection.UserBeanAccessorHelper;
import org.squale.squaleweb.connection.exception.ConnectionException;
import org.squale.squaleweb.servlet.UserSqualeSessionContext;
import org.squale.squaleweb.transformer.LogonBeanTransformer;
import org.squale.squaleweb.transformer.UserTransformer;
import org.squale.squaleweb.util.ExceptionWrapper;

import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;
import org.squale.welcom.struts.util.WConstants;

/**
 * Struts Action that handles login into Squale.
 * 
 * @version 1.0
 * @author
 */
public class LoginAction
    extends DefaultAction
{
    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( LoginAction.class );

    /**
     * ${@inheritDoc}
     */
    @Override
    public ActionForward execute( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                  HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        boolean sessionOk = initUserInSession( pRequest );
        if ( sessionOk )
        {

            // On redirige vers la gestion de son compte si l'email OU le nom n'est pas renseigné
            // Sinon on redirige vers la page d'accueil
            LogonBean logonBean = (LogonBean) pRequest.getSession().getAttribute( WConstants.USER_KEY );
            if ( logonBean.getEmail().length() == 0 || logonBean.getUsername() == null
                || logonBean.getUsername().length() == 0 )
            {
                forward = pMapping.findForward( "email" );
            }
            else
            {
                String requestedPagePath = (String) pRequest.getSession().getAttribute( "requestedPagePath" );
                if ( requestedPagePath != null )
                {
                    // a specific page was required prior to login: go there
                    forward = new ActionForward( requestedPagePath, true );
                }
                else
                {
                    // normal login: go to main page
                    forward = pMapping.findForward( "success" );
                }
            }
        }
        else
        {
            forward = pMapping.findForward( "total_failure" );
        }

        return forward;
    }

    /**
     * Inits the user in session
     * 
     * @param pRequest la requete
     * @return true si l'utilisateur a pu être initialisé et enregistré en session
     */
    public boolean initUserInSession( HttpServletRequest pRequest )
    {
        ActionMessages errors = new ActionMessages();
        boolean success;
        try
        {

            LogonBean logonBeanSecurity = getUser( pRequest );

            pRequest.getSession().setAttribute( WConstants.USER_KEY, logonBeanSecurity );
            success = true;

            // Set the Squale's administrator mailing list
            configAdminMailingList( pRequest.getSession() );
            configSahredRepository(pRequest.getSession());
        }
        catch ( Exception e )
        {
            // On ecrit l'erreur et on la sauvegarde pour affichage dans la jsp
            log.error( e, e );
            ExceptionWrapper.saveException( pRequest, e );
            // On invalide la session
            pRequest.getSession().invalidate();
            ActionMessage error = new ActionMessage( "error.cannot_act" );
            errors.add( ActionMessages.GLOBAL_MESSAGE, error );
            saveMessages( pRequest, errors );
            success = false;
        }
        return success;
    }

    /**
     * Returns the bean that stands for the authenticated user.
     * 
     * @param pRequest la requête
     * @return l'utilisateur connecté sous forme de LoginBean
     * @throws ConnectionException si erreur de récupération de l'utilisateur connecté
     * @throws JrafEnterpriseException si erreur Jraf
     * @throws WTransformerException si erreur lors de la transformation
     */
    public LogonBean getUser( HttpServletRequest pRequest )
        throws ConnectionException, JrafEnterpriseException, WTransformerException
    {
        // Obtention des informations sur l'utilisateur connecté

        AuthenticationBean authent = (AuthenticationBean) pRequest.getSession().getAttribute( "AuthenticatedUser" );
        String name = authent.getIdentifier();
        IUserBeanAccessor userBeanAccessor = UserBeanAccessorHelper.getUserBeanAccessor();
        boolean isAdmin = userBeanAccessor.getUserBean( pRequest ).isAdmin();
        UserDTO user = new UserDTO();
        user.setMatricule( name );

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Login" );
        Object[] paramIn = { user, Boolean.valueOf( isAdmin ) };

        user = (UserDTO) ac.execute( "verifyUser", paramIn );
        UserForm userForm = (UserForm) WTransformerFactory.objToForm( UserTransformer.class, user );
        LogonBean logonBeanSecurity = new LogonBean();
        WTransformerFactory.formToObj( LogonBeanTransformer.class, userForm, new Object[] { logonBeanSecurity,
            Boolean.valueOf( isAdmin ) } );

        return logonBeanSecurity;
    }

    /**
     * This method set in session a variable which contains Squale's administrator mailing list. The information set in
     * this variable come from the database
     * 
     * @param session The http session
     * @throws JrafEnterpriseException Error happen during the search in the database
     */
    private void configAdminMailingList( HttpSession session )
        throws JrafEnterpriseException
    {
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "Mail" );
        String mailingList = ac.execute( "getAdminMailingList" ).toString();
        UserSqualeSessionContext sessionContext = UserSqualeSessionContext.getContext( session );
        sessionContext.setSqualeAdminsMailingList( mailingList );
        UserSqualeSessionContext.setContext( session, sessionContext );
    }
    
    /**
     * This method set in session a variable which indicate if the shared repository is configured
     * 
     * @param session The http session
     * @throws JrafEnterpriseException Error happen during the search in the database
     */
    private void configSahredRepository( HttpSession session ) throws JrafEnterpriseException
    {
        
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "SqualixConfig" );
        //We search in the db the adminParamsBo which define to the squalix server to perform the export
        Object sharedRepositoryServerConfig = ac.execute( "getSharedRepositoryExportServer" );
        //If we found it that means the the shared repository is configured
        if (sharedRepositoryServerConfig != null)
        {
            UserSqualeSessionContext sessionContext = UserSqualeSessionContext.getContext( session );
            sessionContext.setSharedRepositoryConfigured( "true" );
            UserSqualeSessionContext.setContext( session, sessionContext );
        }
    }

}
