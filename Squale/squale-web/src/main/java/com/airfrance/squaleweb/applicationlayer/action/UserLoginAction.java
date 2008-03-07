package com.airfrance.squaleweb.applicationlayer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;
import com.airfrance.squaleweb.applicationlayer.formbean.UserLoginForm;
import com.airfrance.squaleweb.connection.UserBeanAccessorHelper;

/**
 * Action class for the login. This action is call by the login.jsp when a user send his login and password for
 * authentication.
 */
public class UserLoginAction
    extends DefaultAction
{

    /**
     * This method try to do the connection of the user if the authentication succeed then the action login.do is call
     * if the authentication failed then the user is redirect to the login.jsp page
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
        boolean isUser = UserBeanAccessorHelper.getUserBeanAccessor().isUser( user );
        if ( isUser )
        {
            forward = mapping.findForward( "success" );
        }
        else
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
