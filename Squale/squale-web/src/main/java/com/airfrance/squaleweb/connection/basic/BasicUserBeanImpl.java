package com.airfrance.squaleweb.connection.basic;

import java.util.List;
import com.airfrance.squaleweb.connection.IUserBean;

/**
 * This class is the implementation of the basic userBean. The userBean is the bean for authentication. In this case,
 * the userBean contains the identifier and the profiles of the authenticated user. When there is no user authenticate,
 * identifier and profiles are null. In the basic implementation the authentication verification is done directly in the
 * squale database (in the userBO table)
 */
public class BasicUserBeanImpl
    implements IUserBean
{

    /** The administrator profile */
    private static final String ADMIN_PROFILE = "bo.profile.name.admin";

    /** The identifier of the userBean */
    protected String identifier;

    /** the profiles of the userBean */
    protected List profiles;

    /**
     * Default constructor
     */
    public BasicUserBeanImpl()
    {
    }

    /**
     * Constructor with two arguments
     * 
     * @param identifier The identifier of the authenticated user
     * @param profiles The profiles of the authenticated user
     */
    public BasicUserBeanImpl( String identifier, List profiles )
    {
        this.profiles = profiles;
        this.identifier = identifier;
    }

    /**
     * This method indicate if the authenticated user is a squale administrator. An authenticated user is a squale
     * administrator if its list of profiles contains the profile : bo.profile.name.admin
     * 
     * @return return true if the authenticated user is a squale administrator
     */
    public boolean isAdmin()
    {
        return profiles.contains( ADMIN_PROFILE );

    }

    /**
     * This method return the identifier of the userBean
     * 
     * @return return the identifier of the userBean
     */
    public String getIdentifier()
    {
        return identifier;
    }

    /**
     * This method return the profiles of the userBean
     * 
     * @return return the profiles of the userBean
     */
    public List getProfiles()
    {
        return profiles;
    }

}
