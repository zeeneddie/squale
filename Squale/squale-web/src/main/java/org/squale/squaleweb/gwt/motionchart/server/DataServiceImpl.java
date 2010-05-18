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
package org.squale.squaleweb.gwt.motionchart.server;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.squalecommon.enterpriselayer.facade.quality.FactorResultFacade;
import org.squale.squalecommon.enterpriselayer.facade.quality.QualityResultFacade;
import org.squale.squalecommon.enterpriselayer.facade.quality.FactorResultFacade.MotionChartApplicationFactorData;
import org.squale.squalecommon.enterpriselayer.facade.quality.QualityResultFacade.MotionChartApplicationMetricData;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import org.squale.squaleweb.gwt.motionchart.client.DataService;
import org.squale.squaleweb.gwt.motionchart.client.data.Application;
import org.squale.squaleweb.gwt.motionchart.client.data.AuditValues;
import org.squale.squaleweb.gwt.motionchart.client.data.MotionChartData;
import org.squale.squaleweb.resources.WebMessages;
import org.squale.welcom.struts.util.WConstants;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service that provides data to the motion chart.
 * 
 * @author Fabrice BELLINGARD
 */
public class DataServiceImpl
    extends RemoteServiceServlet
    implements DataService
{
    private static final long serialVersionUID = -8491108470852437054L;

    /**
     * Logger
     */
    private static Log log = LogFactory.getLog( DataServiceImpl.class );

    public MotionChartData getData()
    {
        MotionChartData data = new MotionChartData();

        try
        {
            // let's find the factors that exist in the database
            List<String> factorsList = FactorResultFacade.findFactorNames();
            for ( String factorDatabaseName : factorsList )
            {
                String factorName = WebMessages.getString( getThreadLocalRequest(), "rule." + factorDatabaseName );
                data.addFactor( factorDatabaseName, factorName );
            }

            // lets' now retrieve the data that is needed for the Motion Chart
            for ( ApplicationForm app : findApplicationsForUser() )
            {
                Application applicationData = data.createApplication( app.getApplicationName() );

                // Metriques
                MotionChartApplicationMetricData metricsAppData =
                    QualityResultFacade.findMetricsForMotionChart( app.getId() );
                for ( Iterator<Object[]> iterator = metricsAppData.iterator(); iterator.hasNext(); )
                {
                    Object[] metricData = (Object[]) iterator.next();

                    AuditValues audit =
                        applicationData.getAudit( metricsAppData.getAuditId( metricData ),
                                                  metricsAppData.getAuditDate( metricData ) );
                    if ( metricsAppData.getMetricName( metricData ).equals( "numberOfCodeLines" ) )
                    {
                        audit.addLinesOfCode( metricsAppData.getMetricValue( metricData ) );
                    }
                    else
                    {
                        // this is sumVg for the moment
                        audit.addVg( metricsAppData.getMetricValue( metricData ) );
                    }
                }

                // Factors
                MotionChartApplicationFactorData factorsAppData =
                    FactorResultFacade.findFactorsForMotionChart( app.getId() );
                for ( Iterator<Object[]> iterator = factorsAppData.iterator(); iterator.hasNext(); )
                {
                    Object[] factorData = (Object[]) iterator.next();

                    AuditValues audit = applicationData.getAudit( factorsAppData.getAuditId( factorData ) );
                    audit.addFactorValue( factorsAppData.getFactorName( factorData ),
                                          factorsAppData.getFactorValue( factorData ) );
                }
            }
        }
        catch ( JrafEnterpriseException e )
        {
            log.warn( "Error while generating data needed for the Motion Chart:" + e.getMessage(), e );
        }

        // just for debug purposes
        printMotionChartDataInLog( data );

        return data;
    }

    /**
     * Finds the application that the user can see
     * 
     * @return the list of applications
     */
    @SuppressWarnings( "unchecked" )
    private List<ApplicationForm> findApplicationsForUser()
    {
        HttpSession httpSession = getThreadLocalRequest().getSession();

        LogonBean userLogonBean = (LogonBean) httpSession.getAttribute( WConstants.USER_KEY );
        List<ApplicationForm> apps = userLogonBean.getApplicationsList();
        return apps;
    }

    /**
     * Just for debugging purposes
     * 
     * @param data the data generated for the Motion Chart
     */
    private void printMotionChartDataInLog( MotionChartData data )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "=============================================================" );
            Collection<Application> computedApps = data.getApplications();
            for ( Application application : computedApps )
            {
                log.debug( application.getName() );
                Collection<AuditValues> audits = application.getAuditValues();
                for ( AuditValues auditValues : audits )
                {
                    log.debug( "\t" + DateFormat.getInstance().format( auditValues.getDate() ) );
                    log.debug( "\t\tLOC : " + auditValues.getLinesOfCode() );
                    log.debug( "\t\tvG  : " + auditValues.getComplexity() );
                }
            }
            log.debug( "=============================================================" );
        }
    }

}
