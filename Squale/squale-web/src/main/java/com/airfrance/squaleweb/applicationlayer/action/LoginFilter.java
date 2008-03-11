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
 * This class defined a filter
 * This filter intercept the call to all file *.jsp and *.do
 * It verifies if the user is logged or not   
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
             * If the forward action is /ident.do, then we do the action directly without test
             * if the user is authenticate or not
             */ 
            
            String path = httpServletRequest.getServletPath();
            if ( path.indexOf( "ident.do" ) > 0 )
            {
                chain.doFilter( request, response );
                return;
            }

            if ( session != null )
            {
                // verify if the user is connected or not
                AuthenticationBean authent = (AuthenticationBean)session.getAttribute( "AuthenticatedUser" );
                if ( authent!=null )
                {
                    chain.doFilter( request, response );
                    return;
                }
                session.invalidate();
            }
            session = httpServletRequest.getSession();
            // if the user is not authenticate, we forward to the login jsp
            httpServletRequest.getRequestDispatcher( "/jsp/login.jsp" ).forward( request, response );
            return;
        }
    }

    /**
     * Method for initialization of the filter.
     * @param arg0 parameter for the initialization of the filter
     * @throws ServletException : exception happen during the initialization
     */
    public void init( FilterConfig arg0 )
        throws ServletException
    {

    }

}
