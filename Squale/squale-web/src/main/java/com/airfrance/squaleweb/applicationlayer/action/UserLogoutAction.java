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
