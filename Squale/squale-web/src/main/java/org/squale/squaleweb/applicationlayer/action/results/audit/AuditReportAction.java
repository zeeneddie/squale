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
package org.squale.squaleweb.applicationlayer.action.results.audit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.enterpriselayer.businessobject.result.ErrorBO;
import org.squale.squaleweb.applicationlayer.action.ActionUtils;
import org.squale.squaleweb.applicationlayer.action.accessRights.AdminAction;
import org.squale.squaleweb.applicationlayer.action.export.ppt.AuditReportPPTData;
import org.squale.squaleweb.applicationlayer.action.export.ppt.PPTData;
import org.squale.squaleweb.applicationlayer.action.export.ppt.PPTFactory;
import org.squale.squaleweb.applicationlayer.action.export.ppt.PPTGeneratorException;
import org.squale.squaleweb.applicationlayer.formbean.RootForm;
import org.squale.squaleweb.applicationlayer.formbean.export.AuditReportParamForm;
import org.squale.squaleweb.resources.WebMessages;
import org.squale.squaleweb.transformer.export.AuditReportParamTransformer;
import org.squale.squaleweb.util.SqualeWebActionUtils;
import org.squale.welcom.struts.bean.WActionForm;
import org.squale.welcom.struts.transformer.WTransformerException;
import org.squale.welcom.struts.transformer.WTransformerFactory;

/**
 * Generation and setting of audit report
 */
public class AuditReportAction
    extends AdminAction
{
    /** Number of top to retrieve in report */
    private static final int DEFAULT_NB_TOP = 10;
    /** worst component for top have to have a score < 2*/
    private static final float DEFAULT_MAX_SCORE = 2;

    /**
     * Display audit report parameters
     * 
     * @param pMapping mapping.
     * @param pForm AuditReportParamForm
     * @param pRequest HTTP request.
     * @param pResponse HTTP response.
     * @return action to do.
     */
    public ActionForward param( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            // Get warnings for current audit
            // Get audits in session (not null)
            List auditsDTO = ActionUtils.getCurrentAuditsAsDTO( pRequest );
            String comparableStr = pRequest.getParameter( "comparable" );
            boolean comparable = false;
            if ( null != comparableStr && comparableStr.equalsIgnoreCase( "true" ) && auditsDTO.size() == 2) {
            	comparable = true;
            }
            ((RootForm)pForm).setComparableAudits( comparable );
            if ( ! comparable && auditsDTO.size() == 2)  {
                auditsDTO.remove( 1 );
            } 
            // Call application component layer
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Error" );
            List errorsMaps = (List) ac.execute( "getAllErrors", new Object[] { auditsDTO, ErrorBO.CRITICITY_WARNING } );
            WTransformerFactory.objToForm( AuditReportParamTransformer.class, (WActionForm) pForm, errorsMaps );
            forward = pMapping.findForward( "param" );
        }
        catch ( WTransformerException wte )
        {
            handleException( wte, errors, pRequest );
        }
        catch ( JrafEnterpriseException jee )
        {
            handleException( jee, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "total_failure" );
        }
        return forward;
    }

    /**
     * Generate audit report
     * 
     * @param pMapping mapping.
     * @param pForm AuditReportParamForm
     * @param pRequest HTTP request.
     * @param pResponse HTTP response.
     * @return action to do.
     */
    public ActionForward generateReport( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                         HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            AuditReportParamForm auditReportForm = (AuditReportParamForm) pForm;
            // Get list of results
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Results" );
            Long appliId = new Long( auditReportForm.getApplicationId() );
            Long auditId = new Long( auditReportForm.getCurrentAuditId() );
            Long prevAuditId = null;
            // Get previous results only if it's possible
            if ( auditReportForm.getComparableAudits() )
            {
				try {
					prevAuditId = new Long( auditReportForm.getPreviousAuditId() );
				} catch (NumberFormatException e) {
					// No previous audit available
					prevAuditId = (long) 0;
				}
            }
            // Get max score for top
            float maxScore;
            try {
                maxScore = Float.parseFloat( WebMessages.getString( "export.audit_report.max_score_top" ));
            } catch (NumberFormatException nfe) {
                maxScore = DEFAULT_MAX_SCORE;
            }
            // Get number of worst component to retrieve (default = 10)
            int nbTop;
            try {
                nbTop = Integer.parseInt( WebMessages.getString( "export.audit_report.nb_top" ));
            } catch (NumberFormatException nfe) {
                nbTop = DEFAULT_NB_TOP;
            }
            List results = (List) ac.execute( "getProjectReports", new Object[] { appliId, auditId, prevAuditId, new Integer(nbTop), new Float(maxScore) } );
            AuditReportPPTData data = new AuditReportPPTData( pRequest, auditReportForm, results );
            String reportName = "SQUALE_" + auditReportForm.getApplicationName() + "_" + SqualeWebActionUtils.getFormattedDate( Calendar.getInstance().getTime(), pRequest.getLocale() )
            + ".ppt" ;
            PPTFactory.generatePPTtoHTTPResponse( data, pResponse, reportName );
        }
        catch ( JrafEnterpriseException jee )
        {
            handleException( jee, errors, pRequest );
        }
        catch ( PPTGeneratorException e )
        {
            ActionMessage error = new ActionMessage( "export.audit_report.generation.error", e.getMessage() );
            errors.add( ActionMessages.GLOBAL_MESSAGE, error );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        return forward;
    }

    /**
     * Upload dtd for mapping file
     * 
     * @param pMapping mapping.
     * @param pForm AuditReportParamForm
     * @param pRequest HTTP request.
     * @param pResponse HTTP response.
     * @return action to do.
     */
    public ActionForward uploadMappingDTD( ActionMapping pMapping, ActionForm pForm, HttpServletRequest pRequest,
                                           HttpServletResponse pResponse )
    {
        ActionForward forward = null;
        ActionErrors errors = new ActionErrors();
        try
        {
            InputStream dtd = getClass().getResourceAsStream( PPTData.DTD_LOCATION );
            pResponse.setContentType( "text/plain" );
            final int availableBytes = dtd.available();
            ByteArrayOutputStream out = new ByteArrayOutputStream( availableBytes );
            byte[] buffer = new byte[availableBytes];
            int len;

            while ( ( len = dtd.read( buffer ) ) >= 0 )
            {
                out.write( buffer, 0, len );
            }

            dtd.close();
            out.close();
            pResponse.getWriter().write( new String(out.toByteArray()) );
            pResponse.getWriter().close();
        }
        catch ( IOException ioe )
        {
            handleException( ioe, errors, pRequest );
        }
        if ( !errors.isEmpty() )
        {
            saveMessages( pRequest, errors );
            forward = pMapping.findForward( "failure" );
        }
        return forward;
    }
}
