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
package org.squale.squaleweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.component.AuditDTO;
import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.component.ModuleLightDTO;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squalecommon.enterpriselayer.applicationcomponent.rest.RestComponentAccess;
import org.squale.squalerest.model.ApplicationRest;
import org.squale.squalerest.root.Applications;
import org.squale.squalerest.root.ByApplication;
import org.squale.squalerest.root.ByAudit;
import org.squale.squalerest.root.IRootObject;
import org.squale.squalerest.util.MimeType;
import org.squale.squaleweb.connection.AuthenticationBean;
import org.squale.squaleweb.connection.UserBeanAccessorHelper;
import org.squale.squaleweb.rest.util.TransformToXstreamObject;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

/**
 * LoginTest
 */
public class RestServlet
    extends HttpServlet
{
    /**
     * UID
     */
    private static final long serialVersionUID = 8329888787227913756L;

    /**
     * log
     */
    private static final Log LOG = LogFactory.getLog( RestComponentAccess.class );

    /**
     * Get method of http
     * 
     * @param request The http servlet request
     * @param response The http servlet response
     */
    public void doGet( HttpServletRequest request, HttpServletResponse response )
    {
        try
        {
            // Authentication of the user and retrieve of its informations
            UserDTO userDto = authent( request );
            // If the authentication failed then userDto has for id : -1L
            if ( userDto == null )
            {
                // If the user authentication failed, then the servlet return a 403 error page
                String s = "Basic realm=\"Login Test Servlet Users\"";
                response.setHeader( "WWW-Authenticate", s );
                response.setStatus( HttpStatus.SC_UNAUTHORIZED );
            }
            else
            {
                // Else the servlet execute the search
                String pathInfo = request.getPathInfo();
                MimeType type = returnType( request );
                Locale locale = request.getLocale();
                IRootObject dataToWrite = prepareResponse( pathInfo, userDto, locale );
                if ( dataToWrite != null )
                {
                    response.setCharacterEncoding( "UTF-8" );
                    writeResponse( response, type, dataToWrite );
                }
                else
                {
                    response.setStatus( HttpStatus.SC_NOT_IMPLEMENTED );
                }
            }
        }
        catch ( JrafEnterpriseException e )
        {
            LOG.error( "An error occurs during the rest execution", e );
            response.setStatus( HttpStatus.SC_INTERNAL_SERVER_ERROR );
        }
    }

    /**
     * This method select the action to do according to the pathInfo
     * 
     * @param pathInfo The information request by the user
     * @param userDto The authenticated user
     * @param locale The current locale
     * @return The information request
     * @throws JrafEnterpriseException Exception occurs during the search of the informations
     */
    private IRootObject prepareResponse( String pathInfo, UserDTO userDto, Locale locale )
        throws JrafEnterpriseException
    {
        IRootObject dataToReturn = null;
        if ( pathInfo != null )
        {
            if ( pathInfo.matches( "/applications(/)?+" ) )
            {
                dataToReturn = applicationsFullInfo( userDto, locale,false);
            }
            else if ( pathInfo.matches( "/applications_full(/)?+" ) )
            {
                dataToReturn = applicationsFullInfo( userDto, locale,true) ;
            }
            else if ( pathInfo.matches( "/application/(\\d)++(/)?+" ) )
            {
                String[] splitPathInfo = pathInfo.split( "/application/" );
                String info = cleanInfo( splitPathInfo[1] );
                dataToReturn = byApplication( userDto, info, locale );
            }
            else if ( pathInfo.matches( "/audit/(\\d)++(/)?+" ) )
            {
                String[] splitPathInfo = pathInfo.split( "/audit/" );
                String info = cleanInfo( splitPathInfo[1] );
                dataToReturn = byAudit( userDto, info, locale );
            }
        }
        return dataToReturn;
    }

    /**
     * This method removes the slash "/" at the end of the string given in argument if there is one
     * 
     * @param info The string to clean
     * @return The cleaning string
     */
    private String cleanInfo( String info )
    {
        String cleanInfo = info;
        if ( cleanInfo.endsWith( "/" ) )
        {
            cleanInfo = cleanInfo.substring( 0, info.length() - 1 );
        }
        return cleanInfo;
    }

    /**
     * This method retrieves the factor for the audit given in argument
     * 
     * @param userDto The authenticated user
     * @param auditId The audit id
     * @param locale The current locale
     * @return The data to write
     * @throws JrafEnterpriseException Exceptions occurs during the search
     */
    private ByApplication byAudit( UserDTO userDto, String auditId, Locale locale )
        throws JrafEnterpriseException
    {
        ByApplication dataToReturn = new ByApplication();
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "rest" );
        Object[] param = new Object[] { Long.parseLong( auditId ) };
        AuditDTO audit = (AuditDTO) ac.execute( "audit", param );
        if ( audit != null && audit.getID() != -1L )
        {
            ComponentDTO application = searchApp( userDto, Long.toString( audit.getApplicationId() ) );
            if ( application != null )
            {
                param = new Object[] { (Long) application.getID(), (Long) audit.getID() };
                List<ModuleLightDTO> moduleList = (List<ModuleLightDTO>) ac.execute( "moduleList", param );
                ApplicationRest applicationRest = TransformToXstreamObject.createFullApplicationRest( audit, application, moduleList, locale );
                dataToReturn.setApplication( applicationRest );
            }
        }
        return dataToReturn;
    }

    /**
     * This method retrieves the factor and the list of successful audit for the application which corresponding to the
     * appId given in argument If the user has no rights for the application or if the application doesn't exist, then
     * the method returns an empty ByApplication object
     * 
     * @param userDto The authenticated user
     * @param appId The application
     * @param locale The current locale
     * @return The factor and the list of successful audit for the application
     * @throws JrafEnterpriseException Exception occurs during the search
     */
    private ByApplication byApplication( UserDTO userDto, String appId, Locale locale )
        throws JrafEnterpriseException
    {
        ByApplication data = new ByApplication();
        ComponentDTO application = searchApp( userDto, appId );
        if ( application != null )
        {
            ApplicationRest applicationRest = transformFullApplication( application, locale ); 
            data.setApplication( applicationRest );
        }
        return data;
    }

    /**
     * @param application
     * @param locale
     * @return
     * @throws JrafEnterpriseException
     */
    private ApplicationRest transformFullApplication( ComponentDTO application, Locale locale )
        throws JrafEnterpriseException
    {
        Object[] param = new Object[] { (Long) application.getID() };
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "rest" );
        List<AuditDTO> allSuccessfullAudit = (List<AuditDTO>) ac.execute( "availableAudits", param );
        Long lastSuccessfulAuditId = (Long) allSuccessfullAudit.get( 0 ).getID();
        List<AuditDTO> allPartialAudit = (List<AuditDTO>) ac.execute( "partialAudits", param );
        List<AuditDTO> allFailedAudit = (List<AuditDTO>) ac.execute( "failedAudits", param );
        param = new Object[] { (Long) application.getID(), lastSuccessfulAuditId };
        List<ModuleLightDTO> moduleList = (List<ModuleLightDTO>) ac.execute( "moduleList", param );
        return TransformToXstreamObject.createApplicationRestWithAudits( application, allSuccessfullAudit, allPartialAudit,
                                                       allFailedAudit, moduleList, locale );
    }

    /**
     * This method tries to retrieve the application linked the technical given in argument. This method returns null if
     * the application is not found
     * 
     * @param userDto The current authentified user
     * @param appId The technical id of the application to search
     * @return The application found, null if it's not found
     * @throws JrafEnterpriseException exception occurs during the search
     */
    private ComponentDTO searchApp( UserDTO userDto, String appId )
        throws JrafEnterpriseException
    {
        ComponentDTO application = null;

        IApplicationComponent ac = AccessDelegateHelper.getInstance( "rest" );
        Object[] param = new Object[] { userDto };
        List<ComponentDTO> appList = (List<ComponentDTO>) ac.execute( "visibleApplication", param );
        Iterator<ComponentDTO> it = appList.iterator();
        boolean found = false;
        while ( it.hasNext() && !found )
        {
            application = it.next();
            if ( application.getID() == Long.parseLong( appId ) )
            {
                found = true;
            }
        }
        return application;
    }
    

    /**
     * This method retrieves the list of application available for the authenticated user and which has at least one
     * successful audit
     * 
     * @param userDto The authenticated user
     * @param locale The current locale
     * @param full All information for the application
     * @return The list of application available for the authenticated user and which has at least one successful audit
     * @throws JrafEnterpriseException Exceptions occurs during the search
     */
    private Applications applicationsFullInfo( UserDTO userDto, Locale locale, boolean full )
        throws JrafEnterpriseException
    {
        Applications data = new Applications();
        IApplicationComponent ac = AccessDelegateHelper.getInstance( "rest" );
        Object[] param = new Object[] { userDto };
        List<ComponentDTO> appList = (List<ComponentDTO>) ac.execute( "visibleApplication", param );
        ApplicationRest applicationRest = null;
        for ( ComponentDTO application : appList )
        {
            if(full)
            {
                applicationRest = transformFullApplication( application, locale );
            }
            else
            {
                applicationRest = TransformToXstreamObject.createApplicationRest( application );
            }
            data.addApplication( applicationRest );
        }
        return data;
    }

    /**
     * This method write the response
     * 
     * @param response The servlet response
     * @param type The mime type for the response
     * @param dataToWrite The data to write
     * @throws JrafEnterpriseException Exception occurs during the writing of the response
     */
    private void writeResponse( HttpServletResponse response, MimeType type, IRootObject dataToWrite )
        throws JrafEnterpriseException
    {
        try
        {
            PrintWriter out = response.getWriter();
            if ( type == MimeType.xml )
            {
                response.setContentType( MimeType.xml.getRealValue() );
                XStream xstream = new XStream();
                xstream.processAnnotations( dataToWrite.getClass() );
                xstream.toXML( dataToWrite, out );
            }
            else
            {
                response.setContentType( MimeType.json.getRealValue() );
                XStream xstream = new XStream( new JsonHierarchicalStreamDriver() );
                xstream.setMode( XStream.NO_REFERENCES );
                xstream.processAnnotations( dataToWrite.getClass() );
                xstream.toXML( dataToWrite, out );
            }
        }
        catch ( IOException e )
        {
            throw new JrafEnterpriseException( e );
        }

    }

    /**
     * This method define the type of response to return : xml or json (by default it's xml)
     * 
     * @param request The http servlet request
     * @return The mime type for the servlet response
     */
    private MimeType returnType( HttpServletRequest request )
    {
        MimeType type = MimeType.xml;
        String acceptHeader = request.getHeader( "Accept" );
        if ( acceptHeader != null && acceptHeader.equalsIgnoreCase( MimeType.json.getRealValue() ) )
        {
            type = MimeType.json;
        }
        return type;
    }

    /**
     * This method do the authentication. It returns an empty UserBO if the authentication failed else it returns the
     * UserBo corresponding the authenticated user.
     * 
     * @param request The http servlet request
     * @return an empty UserBO if the authentication failed, else the UserBO corresponding to the authenticated user
     * @throws JrafEnterpriseException Exception occurs during the authentication
     * @throws JrafDaoException
     */
    private UserDTO authent( HttpServletRequest request )
        throws JrafEnterpriseException
    {
        UserDTO userDto = null;
        String authHeader = request.getHeader( "Authorization" );
        if ( authHeader != null )
        {
            StringTokenizer st = new StringTokenizer( authHeader );
            if ( st.hasMoreTokens() )
            {
                String basic = st.nextToken();

                // We only handle HTTP Basic authentication
                if ( basic.equalsIgnoreCase( "Basic" ) )
                {
                    String credentials = st.nextToken();

                    Base64 deco = new Base64();
                    String userPass = new String( deco.decode( credentials.getBytes() ) );
                    userDto = authentSearch( userPass );
                }
            }
        }
        return userDto;

    }

    /**
     * This method search the user which corresponding to the credential given in argument. If no corresponding user is
     * found, an empty user object is return.
     * 
     * @param userPass The http header credential
     * @param session
     * @return The user found
     * @throws JrafEnterpriseException Exception occurs during the search
     * @throws JrafDaoException
     */
    private UserDTO authentSearch( String userPass )
        throws JrafEnterpriseException
    {
        UserDTO userToReturn = null;
        int p = userPass.indexOf( ":" );
        if ( p != -1 )
        {
            String userID = userPass.substring( 0, p );
            String password = userPass.substring( p + 1 );
            if ( ( !userID.trim().equals( "" ) ) && ( !password.trim().equals( "" ) ) )
            {
                UserDTO userDto = new UserDTO( userID, password );
                AuthenticationBean isUser = UserBeanAccessorHelper.getUserBeanAccessor().isUser( userDto );
                if ( isUser != null && isUser.getIdentifier() != null )
                {
                    IApplicationComponent ac = AccessDelegateHelper.getInstance( "Login" );
                    Object[] param = new Object[] { userDto };
                    userToReturn = (UserDTO) ac.execute( "getUserByIdentifier", param );
                }
            }
        }
        return userToReturn;
    }

}