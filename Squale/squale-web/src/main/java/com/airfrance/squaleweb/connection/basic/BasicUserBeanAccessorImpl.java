package com.airfrance.squaleweb.connection.basic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squaleweb.connection.AuthenticationBean;
import com.airfrance.squaleweb.connection.IUserBean;
import com.airfrance.squaleweb.connection.IUserBeanAccessor;
import com.airfrance.squaleweb.connection.exception.ConnectionException;

/**
 * This class is the basic implementation of the userBean accessor. This class contains methods for authentication.
 * In the basic implementation the authentication verification is done directly in the squale database (in the userBO table)
 */
public class BasicUserBeanAccessorImpl
    implements IUserBeanAccessor
{

    /**
     * The userBean
     */
    private IUserBean userBean;

    /**
     * Default constructor
     */
    public BasicUserBeanAccessorImpl()
    {
        super();
    }

    /**
     * This method do the authentication of the user.
     * This method return an authenticationBean with the user authenticated inside if the authentication succeed.
     * The AuthenticationBean returned is null if the authentication failed. 
     * @param user : Object with only the identifier and the password fill
     * @return The AuthenticationBean fill if the authentication succeed or null if it failed 
     */
    public AuthenticationBean isUser( UserDTO user )
    {
        AuthenticationBean authent = null;
        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Login" );
            Object[] paramIn = { user };
            UserDTO userInBase = (UserDTO) ac.execute( "userAuthentication", paramIn );
            if ( userInBase.getMatricule() != null && userInBase.getPassword() != null )
            {
                List profiles = new ArrayList();
                profiles.add( userInBase.getDefaultProfile().getName() );
                authent = new AuthenticationBean (userInBase.getMatricule(),profiles);
            }
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
        }
        return authent;
    }


    /**
     * This method return the userBean of the bean accessor
     * 
     * @param request the request used
     * @return the authenticated userBean of the bean accessor
     * @throws ConnectionException : not use in this implementation
     */
    public IUserBean getUserBean( HttpServletRequest request )
        throws ConnectionException
    {
        AuthenticationBean authent = (AuthenticationBean)request.getSession().getAttribute( "AuthenticatedUser" );
        userBean = new BasicUserBeanImpl(authent.getProfiles());
        return userBean;
    }

    
    /**
     * This method return the userBean of the bean accessor
     * 
     * @return The userBean of the bean accessor
     */
    public IUserBean getUserBean()
    {
        return userBean;
    }

    /**
     * Set the user bean for this accessor
     * 
     * @param pUserBean The bean corresponding to the user
     */
    public void setUserBean( IUserBean pUserBean )
    {
        userBean = pUserBean;
    }

}
