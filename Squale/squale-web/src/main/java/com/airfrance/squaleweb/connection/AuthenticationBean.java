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
