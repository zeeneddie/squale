package com.airfrance.squaleweb.connection;


/**
 * user bean
 */
public interface IUserBean
{

    /**
     * This method indicate if the authenticated user is a squale administrator
     * 
     * @return return true if the authenticated user is a squale administrator
     */
    public boolean isAdmin();

}
