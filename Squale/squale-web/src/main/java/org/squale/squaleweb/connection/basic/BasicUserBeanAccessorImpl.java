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
package org.squale.squaleweb.connection.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.AccessDelegateHelper;
import org.squale.jraf.spi.accessdelegate.IApplicationComponent;
import org.squale.squalecommon.datatransfertobject.component.UserDTO;
import org.squale.squaleweb.connection.AuthenticationBean;
import org.squale.squaleweb.connection.IUserBean;
import org.squale.squaleweb.connection.IUserBeanAccessor;
import org.squale.squaleweb.connection.exception.ConnectionException;

/**
 * This class is the basic implementation of the userBean accessor. This class contains methods for authentication. In
 * the basic implementation the authentication verification is done directly in the squale database (in the userBO
 * table)
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
     * This method do the authentication of the user. This method return an authenticationBean with the user
     * authenticated inside if the authentication succeed. The AuthenticationBean returned is null if the authentication
     * failed.
     * 
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
                authent = new AuthenticationBean( userInBase.getMatricule(), profiles );
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
        AuthenticationBean authent = (AuthenticationBean) request.getSession().getAttribute( "AuthenticatedUser" );
        userBean = new BasicUserBeanImpl( authent.getProfiles() );
        return userBean;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<UserDTO> getUsers( String idStart )
    {
        Collection<UserDTO> foundUsers = null;
        try
        {
            IApplicationComponent ac = AccessDelegateHelper.getInstance( "Login" );
            Object[] paramIn = { idStart };
            foundUsers = (Collection<UserDTO>) ac.execute( "getUsersWithIdStartingBy", paramIn );
        }
        catch ( JrafEnterpriseException e )
        {
            e.printStackTrace();
        }
        return foundUsers;
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
