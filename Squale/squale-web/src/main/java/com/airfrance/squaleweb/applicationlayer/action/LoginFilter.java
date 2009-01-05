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
package com.airfrance.squaleweb.applicationlayer.action;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.airfrance.squaleweb.connection.AuthenticationBean;

/**
 * This class defined a filter This filter intercept the call to all file *.jsp and *.do It verifies if the user is
 * logged or not
 */
public class LoginFilter
    implements Filter
{

    /**
     * Method for destroy the filter
     */
    public void destroy()
    {

    }

    /**
     * This filter determine if the user who do the request is authenticate or not
     * 
     * @param request : The http request
     * @param response : The servlet response
     * @param chain : The chain of filter
     * @throws ServletException : Exception happen during the redirection
     * @throws IOException : Exception happen during the redirection
     */

    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {

        if ( request instanceof HttpServletRequest )
        {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpSession session = httpServletRequest.getSession( false );

            /*
             * If the forward action is /ident.do, then we do the action directly without test if the user is
             * authenticate or not
             */

            String path = httpServletRequest.getServletPath();
            if ( path.indexOf( "ident.do" ) > 0 )
            {
                chain.doFilter( request, response );
                return;
            }

            // keep track of the requested page in order to forward the request
            // to it upon login completion
            String requestedPagePath = getRequestedPagePath( httpServletRequest );

            if ( session != null )
            {
                // verify if the user is connected or not
                AuthenticationBean authent = (AuthenticationBean) session.getAttribute( "AuthenticatedUser" );
                if ( authent != null )
                {
                    // user already authenticated: go on
                    storeRequestedPagePath( httpServletRequest, requestedPagePath );
                    chain.doFilter( request, response );
                    return;
                }
                session.invalidate();
            }
            // Go to login page (and redirect to requested page if required)
            storeRequestedPagePath( httpServletRequest, requestedPagePath );
            httpServletRequest.getRequestDispatcher( "/jsp/login.jsp" ).forward( request, response );
            return;
        }
    }

    /**
     * Returns the requested page path.
     * 
     * @param request The http request
     * @return the requested page path
     */
    private String getRequestedPagePath( HttpServletRequest request )
    {
        String requestedPagePath = request.getServletPath();

        if ( requestedPagePath.contains( "logout.do" ) || requestedPagePath.contains( "login.do" )
            || requestedPagePath.contains( "index.jsp" ) )
        {
            // we don't want to remember those requests
            requestedPagePath = null;
        }
        else
        {
            // let's find the full query
            String queryString = request.getQueryString();
            if ( queryString != null )
            {
                requestedPagePath += "?" + queryString;
            }
        }

        return requestedPagePath;
    }

    /**
     * Saves the requested page path in the session, under the "requestedPagePath" id.
     * 
     * @param request the Http request
     * @param requestedPagePath the requested path
     */
    private void storeRequestedPagePath( HttpServletRequest request, String requestedPagePath )
    {
        request.getSession().setAttribute( "requestedPagePath", requestedPagePath );
    }

    /**
     * Method for initialization of the filter.
     * 
     * @param arg0 parameter for the initialization of the filter
     * @throws ServletException : exception happen during the initialization
     */
    public void init( FilterConfig arg0 )
        throws ServletException
    {

    }

}
