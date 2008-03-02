package com.airfrance.squaleweb.connection.stubImpl;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.connection.IUserBean;

public class UserBeanImpl
    implements IUserBean
{

    private static final String ADMIN_PROFILE = "P_SQUALE_ADMIN-TECH";

    /** */
    protected String matricule;

    /** Les profils */
    protected List profiles;

    /**
     * Constructeur par défaut
     */
    public UserBeanImpl()
    {
        profiles = new ArrayList( 0 );
        profiles.add( ADMIN_PROFILE );
    }

    public boolean isAdmin()
    {
        return true;
    }

    public String getIdentifier()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
