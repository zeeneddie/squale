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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.squale.gwt.distributionmap.widget.data.Child;
import org.squale.gwt.distributionmap.widget.data.Parent;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.SessionImpl;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import org.squale.squaleweb.gwt.motionchart.client.DataService;
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

    public String getData()
    {
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
                "select rule.id, rule.name " + "from QualityRuleBO rule " + "where rule.class='FactorRule'";
            Query query1 = session.createQuery( requete1 );
            List<Object[]> factorsList = query1.list();
            System.out.println( "Factors: " );
            for ( Object[] factorInfos : factorsList )
            {
                long factorId = (Long) factorInfos[0];
                String factorName = WebMessages.getString( getThreadLocalRequest(), "rule." + (String) factorInfos[1] );
                System.out.println( "\t- " + factorName );
            }

            // lets' now retrieve the data that is needed for the Motion Chart
            for ( ApplicationForm app : apps )
            {
                System.out.println( app.getId() + " - " + app.getApplicationName() );

                String requete =
                    "select component.id, component.name, audit.id, audit.historicalDate, audit.date "
                        + "from AbstractComponentBO component, AuditBO audit " + "where component.class='Application' "
                        + "and component.id=" + app.getId() + " and audit.id in elements(component.audits)"
                        + " and audit.status=" + AuditBO.TERMINATED;
                Query query = session.createQuery( requete );
                List resultList = query.list();

                for ( Object object : resultList )
                {
                    Object[] result = (Object[]) object;
                    long applicationId = (Long) result[0];
                    String applicationName = (String) result[1];
                    long auditId = (Long) result[2];
                    Date auditHistoricalDate = (Date) result[3];
                    Date auditStartDate = (Date) result[4];
                    Date auditDate = ( auditHistoricalDate == null ) ? auditStartDate : auditHistoricalDate;

                    System.out.println( "\t Audit #" + auditId + " - "
                        + DateFormat.getInstance().format( auditStartDate ) );
                }

            }
        }
        catch ( Exception e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String data = "";
        return data;
    }

}
