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

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import org.squale.squaleweb.applicationlayer.formbean.UserLoginForm;
import org.squale.squaleweb.connection.AuthenticationBean;
import org.squale.squaleweb.connection.UserBeanAccessorHelper;

/**
 * Action class for the login. This action is call by the login.jsp when a user send his login and password for
 * authentication.
 */
public class UserLoginAction
    extends DefaultAction
{

    /**
     * This method try to do the connection of the user. If the authentication succeed then the action login.do is call.
     * If the authentication failed then the user is redirect to the login.jsp page
     * 
     * @param mapping : The mapping
     * @param form : The form to read
     * @param request : The http request
     * @param response : The servlet response
     * @return return the action to do
     */
    public ActionForward execute( ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                  HttpServletResponse response )
    {
        ActionForward forward = null;
        UserLoginForm loginForm = (UserLoginForm) form;
        String login = loginForm.getLogin();
        String password = loginForm.getPass();
        UserDTO user = new UserDTO();
        user.setMatricule( login );
        user.setPassword( password );
        AuthenticationBean isUser = UserBeanAccessorHelper.getUserBeanAccessor().isUser( user );
        if ( isUser != null && isUser.getIdentifier() != null)
        {
            HttpSession session = request.getSession();
            session.setAttribute( "AuthenticatedUser", isUser );
            forward = mapping.findForward( "success" );
        }else
        {
            forward = mapping.findForward( "failure" );
            loginForm.setPass( null );
            ActionErrors error = new ActionErrors();
            ActionMessage message = new ActionMessage( "authentication.error" );
            error.add( "failed", message );
            request.setAttribute( Globals.ERROR_KEY, error );
        }
        return forward;
    }
}
