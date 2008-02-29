package com.airfrance.squaleweb.connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squaleweb.connection.exception.ConnectionException;

/**
 * user Bean Access interface
 */
public interface IUserBeanAccessor {
    
    /**
     * This method return the userBean of the bean accessor
     * @param pRequest the request used
     * @return the authenticated userBean of the bean accessor
     * @throws ConnectionException 
     */
    public IUserBean getUserBean(HttpServletRequest pRequest) throws ConnectionException;
    
    /**
     * This method return the userBean of the bean accessor
     * @return The userBean of the bean accessor
     */
    public IUserBean getUserBean();
    
    
    /**
     * This method do the authentication of the user.
     * @param user object with only the identify and the password fill
     * @return true if the user is recognized
     */
    public boolean isUser (UserDTO user);
    
    /**
     * This method indicate if the user is connected to the application or not
     * @return true if the user is connected
     */
    public boolean isConnect();
    
    
    
    /**This method disconnect the user from the application
     * @param session The session in used
     * */
    public void disConnect(HttpSession session);

}
