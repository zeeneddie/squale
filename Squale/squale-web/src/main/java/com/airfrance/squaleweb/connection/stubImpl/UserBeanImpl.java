package com.airfrance.squaleweb.connection.stubImpl;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.connection.IUserBean;

/**
 * This class is a stub implementation of userBean.
 * So in this case the method isAdmin always return true
 */
public class UserBeanImpl
    implements IUserBean
{

    /** Profiles list */
    protected List profiles;

    /**
     * Default constructor
     */
    public UserBeanImpl()
    {
        profiles = new ArrayList( 0 );
    }

    /**
     * In this implementation the authenticated user is always an admin
     * @return true
     */
    public boolean isAdmin()
    {
        return true;
    }



}
