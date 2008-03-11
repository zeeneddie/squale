package com.airfrance.squaleweb.connection;

import javax.servlet.http.HttpServletRequest;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squaleweb.connection.exception.ConnectionException;

/**
 * user Bean Access interface
 */
public interface IUserBeanAccessor
{

    /**
     * This method return the userBean of the bean accessor
     * 
     * @param pRequest the request used
     * @return the authenticated userBean of the bean accessor
     * @throws ConnectionException : Exception happen during the call for authentication
     */
    IUserBean getUserBean( HttpServletRequest pRequest )
        throws ConnectionException;


    /**
     * This method do the authentication of the user.
     * 
     * @param user object with only the identify and the password fill
     * @return true if the user is recognized
     */
    AuthenticationBean isUser( UserDTO user );

    

}
