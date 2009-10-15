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
package org.squale.squaleweb.applicationlayer.action.sharedrepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.squale.squaleweb.applicationlayer.action.accessRights.AdminAction;

public class SharedRepositoryImportAction
    extends AdminAction
{
    
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response)
    {
        ActionForward forward = mapping.findForward( "enter_import" );
        return forward;
    }
    
    public ActionForward impor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
                                HttpServletResponse response)
    {
        ActionForward forward = mapping.findForward( "enter_import" );
        return forward;
    }
    
}
