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
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.SessionImpl;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
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

    public MotionChartData getData()
    {
        MotionChartData data = new MotionChartData();
        
        ServletContext context = getServletContext();
        HttpSession httpSession = getThreadLocalRequest().getSession();

        LogonBean userLogonBean = (LogonBean) httpSession.getAttribute( WConstants.USER_KEY );
        List<ApplicationForm> apps = userLogonBean.getApplicationsList();

        try
        {
            IPersistenceProvider persistenceProvider = PersistenceHelper.getPersistenceProvider();
            Session session = ( (SessionImpl) persistenceProvider.getSession() ).getSession();

            // let's find the factors that exist in the database
            String requete1 =
                "select distinct rule.name " + "from QualityRuleBO rule " + "where rule.class='FactorRule'";
            Query query1 = session.createQuery( requete1 );
            List<Object> factorsList = query1.list();
            System.out.println( "Factors: " );
            for ( Object factorInfos : factorsList )
            {
                String factorDatabaseName = (String) factorInfos;
                String factorName = WebMessages.getString( getThreadLocalRequest(), "rule." + factorDatabaseName );
                System.out.println( "\t- " + factorName );
                data.addFactor(factorDatabaseName, factorName);
            }

            // lets' now retrieve the data that is needed for the Motion Chart
            for ( ApplicationForm app : apps )
            {
                System.out.println( "---------------- " + app.getId() + " - " + app.getApplicationName()
                    + " ----------------" );
                Application applicationData = data.createApplication(app.getApplicationName());

                // Metriques                
                String requete =
                    "select component.id, component.name, audit.id, audit.historicalDate, audit.date, metric.name, metric"
                        + " from AbstractComponentBO component, AuditBO audit, MeasureBO measure, MetricBO metric"
                        + " where component.class='Project' and component.parent.id=" + app.getId()
                        + " and audit.id in elements(component.audits)" + " and audit.status=" + AuditBO.TERMINATED
                        + " and measure.audit.id=audit.id and measure.component.id=component.id"
                        + " and metric.measure.id=measure.id and metric.class='Int'"
                        + " and (metric.name='numberOfCodeLines' or metric.name='sumVg')"
                        + " order by audit.id, metric.name";

                Query query = session.createQuery( requete );
                List resultList = query.list();

                for ( Object object : resultList )
                {
                    Object[] result = (Object[]) object;
                    long projectId = (Long) result[0];
                    String projectName = (String) result[1];
                    long auditId = (Long) result[2];
                    Date auditHistoricalDate = (Date) result[3];
                    Date auditStartDate = (Date) result[4];
                    Date auditDate = ( auditHistoricalDate == null ) ? auditStartDate : auditHistoricalDate;
                    String metricName = (String) result[5];
                    int metricValue = (Integer) ( (IntegerMetricBO) result[6] ).getValue();

                    System.out.println( "\t Project " + projectName + ", audit #" + auditId + " - "
                        + DateFormat.getInstance().format( auditStartDate ) + " : " + metricName + "=" + metricValue );
                    
                    AuditValues audit = applicationData.getAudit(auditId, auditDate);
                    if (metricName.equals( "numberOfCodeLines" )) {
                        audit.addLinesOfCode(metricValue);
                    } else {
                        // this is sumVg for the moment
                        audit.addVg(metricValue);
                    }
                }

                // Factors
                requete =
                    "select component.id, component.name, audit.id, factorResult.rule.name, factorResult.meanMark"
                        + " from AbstractComponentBO component, AuditBO audit, QualityResultBO factorResult"
                        + " where component.class='Project' and component.parent.id=" + app.getId()
                        + " and audit.id in elements(component.audits)" + " and audit.status=" + AuditBO.TERMINATED
                        + " and factorResult.class='FactorResult' and factorResult.project.id=component.id"
                        + " and factorResult.audit.id=audit.id" + " order by audit.id, factorResult.rule.name";

                query = session.createQuery( requete );
                resultList = query.list();

                for ( Object object : resultList )
                {
                    Object[] result = (Object[]) object;
                    long projectId = (Long) result[0];
                    String projectName = (String) result[1];
                    long auditId = (Long) result[2];
                    String factorName = (String) result[3];
                    float factorValue = (Float) result[4];

                    System.out.println( "\t Project " + projectName + ", audit #" + auditId + " - " + factorName + "="
                        + factorValue );
                    
                    AuditValues audit = applicationData.getAudit(auditId);
                    audit.addFactorValue(factorName, factorValue);
                }

            }
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("=============================================================");
        Collection<Application> computedApps = data.getApplications();
        for ( Application application : computedApps )
        {
            System.out.println(application.getName());
            Collection<AuditValues> audits = application.getAuditValues();
            for ( AuditValues auditValues : audits )
            {
                System.out.println("\t" + DateFormat.getInstance().format( auditValues.getDate()));
                System.out.println("\t\tLOC : " + auditValues.getLinesOfCode());
                System.out.println("\t\tvG  : " + auditValues.getComplexity());
            }
        }
        
        return data;
    }

}
