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
package com.airfrance.squaleweb.connection;

import java.util.Collection;

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

    /**
     * This method returns a list of UserDTO whose IDs start by the given "idStart" parameter.
     * <br>
     * <u>Note:</u> depending on the implementation (database, LDAP, xml file, ...) and its amount of
     * stored data, it can be useful to limit the search of users when you implement this method (for
     * instance, if idStart is less than 2 characters, then you may want to return an empty collection). 
     * 
     * @param idStart the beginning of the user id
     * @return a collection of users whose IDs start by the given paramater
     */
    Collection<UserDTO> getUsers( String idStart );

}
