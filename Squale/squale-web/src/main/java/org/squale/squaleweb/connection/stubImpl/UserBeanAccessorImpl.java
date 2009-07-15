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
package com.airfrance.squaleweb.connection.stubImpl;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squaleweb.connection.AuthenticationBean;
import com.airfrance.squaleweb.connection.IUserBean;
import com.airfrance.squaleweb.connection.IUserBeanAccessor;
import com.airfrance.squaleweb.connection.exception.ConnectionException;

/**
 * This class is a stub implementation of the accessor for authentication
 */
public class UserBeanAccessorImpl
    implements IUserBeanAccessor
{

    /**
     * AuthenticatedUser
     */
    private IUserBean userBean;

    /**
     * Default Constructor
     */
    public UserBeanAccessorImpl()
    {
        super();
    }

    /**
     * One arguments constructor
     * 
     * @param pUserBean the user
     */
    public UserBeanAccessorImpl( IUserBean pUserBean )
    {
        userBean = pUserBean;
    }

    /**
     * This method return a userBean
     * 
     * @param request : the request
     * @return a userBean
     * @throws ConnectionException : not use in this implementation
     */
    public IUserBean getUserBean( HttpServletRequest request )
        throws ConnectionException
    {
        userBean = new UserBeanImpl();
        return userBean;
    }

    /**
     * Getter method for the userBean of this accessor
     * 
     * @return l'utilisateur
     */
    public IUserBean getUserBean()
    {
        return userBean;
    }

    /**
     * Setter method for the userBean of this accessor
     * 
     * @param pBean : The userBean of the user
     */
    public void setUserBean( IUserBean pBean )
    {
        userBean = pBean;
    }

    /**
     * This method return an authenticationBean with the user authenticated inside.
     * 
     * @param user : the userDto with the identifier and the password write in the login.jsp page by the user.
     * @return the AuthenticationBean of the authenticated user
     */
    public AuthenticationBean isUser( UserDTO user )
    {
        AuthenticationBean authent = new AuthenticationBean( "squaleuser", null );

        return authent;
    }

    /**
     * {@inheritDoc}
     */
    public Collection<UserDTO> getUsers( String idStart )
    {
        return new ArrayList<UserDTO>();
    }

}
