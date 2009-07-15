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
package org.squale.squaleweb.applicationlayer.action.component;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import org.squale.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import org.squale.welcom.struts.ajax.WHttpEasyCompleteResponse;
import org.squale.welcom.struts.easycomplete.WEasyCompleteUtil;

/**
 * Struts action used to manage tags.
 */
public class ManageApplicationTagsAction
    extends ReaderAction
{

    /**
     * Method called to add a chosen tag to the application
     * 
     * @param pMapping the mapping.
     * @param pForm the form
     * @param pRequest the HTTP request.
     * @param pResponse the servlet response.
     * @return the chosen forward
     */
    public ActionForward addTag( final ActionMapping pMapping, final ActionForm pForm,
                                 final HttpServletRequest pRequest, final HttpServletResponse pResponse )
    {
        ActionMessages errors = new ActionMessages();
        ActionForward forward = null;

        ComponentDTO appli = (ComponentDTO) pRequest.getSession().getAttribute( BaseDispatchAction.APPLI_DTO );

        forward = pMapping.findForward( "success" );

        return forward;
    }

    /**
     * Method called to fill the suggest input field when adding a tag to an application. The "ch" parameter passed
     * along the request gives the string that the user has just typed in.
     * 
     * @param mapping the mapping.Method called to add a chosen tag to the application
     * @param form the form
     * @param request the HTTP request.
     * @param response the servlet response.
     * @return null (because the response is an XML stream)
     */
    @SuppressWarnings("unchecked")
    public ActionForward findTagForAutocomplete( final ActionMapping mapping, final ActionForm form,
                                                 final HttpServletRequest request, final HttpServletResponse response )
    {
        // retrieves the string that the user has just typed in
        String stringFirstChars = request.getParameter( "ch" );
        stringFirstChars = WEasyCompleteUtil.filter( stringFirstChars );

        // create the response object
        WHttpEasyCompleteResponse easyComplete = new WHttpEasyCompleteResponse( response );

        IApplicationComponent ac;
        try
        {
            ac = AccessDelegateHelper.getInstance( "TagAdmin" );
            Object[] paramIn = { new String[] { stringFirstChars } };
            Collection<TagDTO> tags = ( (Collection<TagDTO>) ac.execute( "getTagsByName", paramIn ) );
            for ( TagDTO tagDTO : tags )
            {
                String value = tagDTO.getName();
                String label = tagDTO.getName();
                easyComplete.addValueLabel( value, label );
            }
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
        }

        try
        {
            easyComplete.close();
        }
        catch ( IOException e )
        {
            // there's nothing we can do about it, forget it
        }

        return null;
    }

}
