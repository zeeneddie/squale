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
package org.squale.squalerest.client;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.squale.squalerest.exception.SqualeRestException;
import org.squale.squalerest.root.Applications;
import org.squale.squalerest.root.ByApplication;

import com.thoughtworks.xstream.XStream;

/**
 * The squale rest http client
 * 
 * @author bfranchet
 */
public class SqualeRestHttpClient
{

    /**
     * the user login
     */
    private String user;

    /**
     * The user password
     */
    private String password;

    /**
     * The server host
     */
    private String host;

    /**
     * The server port
     */
    private int port;

    /**
     * The server contextroot
     */
    private String contextRoot;

    /**
     * Constructor
     */
    public SqualeRestHttpClient()
    {

    }

    /**
     * Initialize the http client
     * 
     * @param pUser The user login
     * @param pPassword The user password
     * @param pHost The server host
     * @param pPort The server port
     * @param pContextRoot The server contextRoot
     */
    public void initialize( String pUser, String pPassword, String pHost, int pPort, String pContextRoot )
    {
        user = pUser;
        password = pPassword;
        host = pHost;
        port = pPort;
        if ( pContextRoot != null && pContextRoot.startsWith( "/" ) )
        {
            contextRoot = pContextRoot;
        }
        else
        {
            contextRoot = "/" + pContextRoot;
        }
    }

    /**
     * This method recovers the applications available for the current user. It returns only applications with at least
     * one successful audit. Moreover, the user should have rights on the application or the application should be
     * public
     * 
     * @return The list of available applications
     * @throws SqualeRestException Exception occurs during the search
     */
    public Applications getApplications()
        throws SqualeRestException
    {
        String path = contextRoot + "/rest/applications";
        XStream xstream = new XStream();
        xstream.processAnnotations( Applications.class );
        Applications applications = (Applications) execute( path, xstream );
        return applications;
    }

    /**
     * This method recovers the applications available for the current user. It returns only applications with at least
     * one successful audit. Moreover, the user should have rights on the application or the application should be
     * public. This method retrieves many informations on the applications
     * 
     * @return The list of available applications
     * @throws SqualeRestException Exception occurs during the search
     */
    public Applications getApplicationsFull()
        throws SqualeRestException
    {
        String path = contextRoot + "/rest/applications_full";
        XStream xstream = new XStream();
        xstream.processAnnotations( Applications.class );
        Applications applications = (Applications) execute( path, xstream );
        return applications;
    }

    /**
     * This method recovers the data of the application linked to the application id given in argument. The current user
     * should have rights on this application (or the application should be public) in order to have the informations.
     * The informations returns are
     * <ul>
     * <li>The list of all the successful audit for this application</li>
     * <li>For the last successful audit, the module involve in this audit and their factors mark.</li>
     * </ul>
     * 
     * @param applicationId The application id
     * @return The data linked to the application
     * @throws SqualeRestException Exception occurs during the search
     */
    public ByApplication getApplication( int applicationId )
        throws SqualeRestException
    {
        String path = contextRoot + "/rest/application/" + applicationId;
        XStream xstream = new XStream();
        xstream.processAnnotations( ByApplication.class );
        ByApplication application = (ByApplication) execute( path, xstream );
        return application;
    }

    /**
     * This method returns the data linked to the audit id given in argument. That means the application and the modukle
     * and their factors marks for the current audit. The current user should have rights on the application linked to
     * the audit (or the application should be public) in order to have the informations.
     * 
     * @param auditId The audit id
     * @return The data linked to the audit
     * @throws SqualeRestException Exception occurs during the search
     */
    public ByApplication getAudit( int auditId )
        throws SqualeRestException
    {
        String path = contextRoot + "/rest/audit/" + auditId;
        XStream xstream = new XStream();
        xstream.processAnnotations( ByApplication.class );
        ByApplication app = (ByApplication) execute( path, xstream );
        return app;
    }

    /**
     * This method executes the search
     * 
     * @param path The query
     * @param xstream The xstream processor
     * @return The object result of the query
     * @throws SqualeRestException Exception occurs during the serach
     */
    private Object execute( String path, XStream xstream )
        throws SqualeRestException
    {
        Object objectToReturn = null;
        DefaultHttpClient httpclient = null;
        try
        {
            httpclient = new DefaultHttpClient();

            // Create credentials
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials( user, password );
            httpclient.getCredentialsProvider().setCredentials( AuthScope.ANY, creds );

            // Define the host
            HttpHost targetHost = new HttpHost( host, port );

            // Define the get method
            HttpGet httpget = new HttpGet( path );

            // Execute the request
            HttpResponse response = httpclient.execute( targetHost, httpget );
            if ( response.getStatusLine().getStatusCode() == HttpStatus.SC_OK )
            {
                HttpEntity entity = response.getEntity();
                if ( entity != null )
                {
                    InputStream is = null;
                    try
                    {
                        // Transform the xml stream into java object
                        is = entity.getContent();
                        objectToReturn = xstream.fromXML( is );
                    }
                    finally
                    {
                        // In all case close the input stream
                        if ( is != null )
                        {
                            is.close();
                        }
                    }
                }
            }
            else
            {
                throw new SqualeRestException( response.getStatusLine().getStatusCode() + " : "
                    + response.getStatusLine().getReasonPhrase() );
            }
        }
        catch ( ClientProtocolException e )
        {
            throw new SqualeRestException( e );
        }
        catch ( IOException e )
        {
            throw new SqualeRestException( e );
        }
        finally
        {
            httpclient.getConnectionManager().shutdown();
        }
        return objectToReturn;
    }

}
