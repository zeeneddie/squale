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
        HttpSession session = getThreadLocalRequest().getSession();

        LogonBean userLogonBean = (LogonBean) session.getAttribute( WConstants.USER_KEY );
        List<ApplicationForm> apps = userLogonBean.getApplicationsList();
        for ( ApplicationForm app : apps )
        {
            System.out.println( app.getId() + " - " + app.getApplicationName() );
        }

        String data =
            "[" + "['Apples',new Date (1988,0,1),1000,300,'East'],"
                + "['Oranges',new Date (1988,0,1),1150,200,'West'],"
                + "['Bananas',new Date (1988,0,1),300,250,'West']," + "['Apples',new Date (1988,5,9),200,100,'East'],"
                + "['Oranges',new Date (1988,5,9),250,100,'West']," + "['Bananas',new Date (1988,5,9),100,50,'West'],"
                + "['Apples',new Date (1989,6,1),1200,400,'East']," + "['Oranges',new Date (1989,6,1),750,150,'West'],"
                + "['Bananas',new Date (1989,6,1),788,617,'West']" + "]";
        return data;
    }

}
