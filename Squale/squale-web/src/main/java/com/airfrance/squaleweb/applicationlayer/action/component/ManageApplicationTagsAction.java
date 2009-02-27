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

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.AuditDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ProjectConfDTO;
import com.airfrance.squalecommon.datatransfertobject.tag.TagDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squalecommon.util.SqualeCommonConstants;
import com.airfrance.squalecommon.util.SqualeCommonUtils;
import com.airfrance.squalecommon.util.mail.MailerHelper;
import com.airfrance.squaleweb.applicationlayer.action.ActionUtils;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.BaseDispatchAction;
import com.airfrance.squaleweb.applicationlayer.action.accessRights.ReaderAction;
import com.airfrance.squaleweb.applicationlayer.formbean.LogonBean;
import com.airfrance.squaleweb.applicationlayer.formbean.component.AuditForm;
import com.airfrance.squaleweb.applicationlayer.formbean.config.ServeurListForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.ApplicationRightsForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateApplicationForm;
import com.airfrance.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;
import com.airfrance.squaleweb.resources.WebMessages;
import com.airfrance.squaleweb.transformer.ApplicationConfTransformer;
import com.airfrance.squaleweb.transformer.AuditTransformer;
import com.airfrance.squaleweb.transformer.ProjectConfTransformer;
import com.airfrance.squaleweb.transformer.ServeurListTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;
import com.airfrance.welcom.struts.ajax.WHttpEasyCompleteResponse;
import com.airfrance.welcom.struts.bean.WActionForm;
import com.airfrance.welcom.struts.easycomplete.WEasyCompleteUtil;
import com.airfrance.welcom.struts.transformer.WTransformerException;
import com.airfrance.welcom.struts.transformer.WTransformerFactory;
import com.airfrance.welcom.struts.util.WConstants;

/**
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
    public ActionForward findTagForAutocomplete( final ActionMapping mapping, final ActionForm form,
                                                 final HttpServletRequest request, final HttpServletResponse response )
    {
        // TODO: finish this up

        // retrieves the string that the user has just typed in
        String stringFirstChars = request.getParameter( "ch" );
        stringFirstChars = WEasyCompleteUtil.filter( stringFirstChars );

        // create the response object
        WHttpEasyCompleteResponse easyComplete = new WHttpEasyCompleteResponse( response );

        // and fill it with the users' information

        /* -------------------------------------------------------------- */
        // This is a example code snippet used to test the suggest field
        // This must be encapsulated in a wider generic security API
        /* -------------------------------------------------------------- */
        // searching Tag is available only when the string is 3 characters long
        if ( stringFirstChars.length() > 2 )
        {
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
        }

        // TODO : need to see why only the 10 first results are displayed...
        /* ------------------------------------------------------ */

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
