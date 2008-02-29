package com.airfrance.squaleweb.connection;

import javax.servlet.http.HttpSession;

/**
 * user bean
 */
public interface IUserBean {
	
	
    
	/**
	 * This method indicate if the authenticated user is a squale administrator 
	 * @return return true if the authenticated user is a squale administrator
	 */
    public boolean isAdmin();
    
    /**
     * This method return the identifier of the userBean
     * @return send the identifier of the userBean
     */
    public String getIdentifier();
    
    
    

}
