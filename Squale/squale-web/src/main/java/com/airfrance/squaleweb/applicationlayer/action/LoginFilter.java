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

import com.airfrance.squaleweb.connection.UserBeanAccessorHelper;

/**
 * 
 *
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
     * @throws ServletException, IOException
     */

    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {

        if ( request instanceof HttpServletRequest )
        {

            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpSession session = httpServletRequest.getSession( false );

            // If the forward action is /ident.do, then we do the action directly without authentication
            String path = httpServletRequest.getServletPath();
            if ( path.indexOf( "ident.do" ) > 0 )
            {
                chain.doFilter( request, response );
                return;
            }

            if ( session != null )
            {
                // verify if the user is connected or not
                if ( UserBeanAccessorHelper.getUserBeanAccessor().isConnect() )
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
     * 
     */
    public void init( FilterConfig arg0 )
        throws ServletException
    {
        // TODO Auto-generated method stub

    }

}
