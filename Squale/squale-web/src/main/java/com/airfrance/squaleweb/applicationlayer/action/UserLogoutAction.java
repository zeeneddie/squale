package com.airfrance.squaleweb.applicationlayer.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.DefaultAction;

/**
 * Action class for the disconnection of the user
 */
public class UserLogoutAction
    extends DefaultAction
{

    /**
     * This method disconnect the user and redirects it to the login.jsp page
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
        HttpSession session = request.getSession();
        session.invalidate();
        //session.setAttribute( "AuthenticatedUser", null );
        forward = mapping.findForward( "success" );
        return forward;
    }

}
