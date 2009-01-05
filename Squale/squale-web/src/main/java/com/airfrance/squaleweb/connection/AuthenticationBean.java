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

import java.util.List;

/**
 * This class is the bean of the authenticated user.
 * When a user is authenticated, an AuthenticationBean is created and put in session
 * (see UserLoginAction)
 */

public class AuthenticationBean
{
    /**
     * The identifier of the authenticated user
     */
    private String identifier;
    
    
    /**
     * the profiles of the authenticated user
     */
    private List profiles;

    /**
     * Default constructor
     */
    public AuthenticationBean(){
        
    }
    
    /**
     * Contructor with 2 arguments
     * @param userIdentifier : The identifier of the authenticated user
     * @param userProfiles : The profiles of the authenticated user
     */
    public AuthenticationBean(String userIdentifier, List userProfiles){
        identifier = userIdentifier;
        profiles = userProfiles;
    }

    /**
     * Getter method for the identifier of the authenticated user 
     * @return the identifier of the authenticated user 
     */    
    public String getIdentifier()
    {
        return identifier;
    }

    /**
     * Setter method for the identifier of the authenticated user
     * @param userIdentifier : The identifier of the authenticated user
     */
    public void setIdentifier( String userIdentifier )
    {
        identifier = userIdentifier;
    }

    /**
     * Getter method for the profiles of the authenticated user
     * @return The profiles of the authenticated user
     */
    public List getProfiles()
    {
        return profiles;
    }

    
    /**
     * Setter method for the profiles of the authenticated user. 
     * @param userProfiles : The profiles of the authenticated user
     */
    public void setProfiles( List userProfiles )
    {
        profiles = userProfiles;
    }
    
   

}
