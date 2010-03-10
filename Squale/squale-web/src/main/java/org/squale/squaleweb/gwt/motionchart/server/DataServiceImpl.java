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
import org.squale.squaleweb.applicationlayer.formbean.LogonBean;
import org.squale.squaleweb.applicationlayer.formbean.component.ApplicationForm;
import org.squale.squaleweb.gwt.motionchart.client.DataService;
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

            for ( ApplicationForm app : apps )
            {
                System.out.println( app.getId() + " - " + app.getApplicationName() );

                String requete =
                    "select component.id, component.name, audit.id "
                        + "from AbstractComponentBO component, AuditBO audit "
                        + "where component.class='Application' and audit.id in elements(component.audits)";
                Query query = session.createQuery( requete );
                List resultList = query.list();

                // ----- CREATION du tree Parent-Child -----
                for ( Object object : resultList )
                {
                    Object[] result = (Object[]) object;
                    long applicationId = (Long) result[0];
                    String applicationName = (String) result[1];
                    long auditId = (Long) result[2];

                    System.out.println( "\t Audit #" + auditId + " pour l'application " + applicationName + " #"
                        + applicationId );
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
