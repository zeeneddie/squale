package com.airfrance.squaleweb.connection.basic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.AccessDelegateHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.accessdelegate.IApplicationComponent;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.enterpriselayer.facade.component.UserFacade;
import com.airfrance.squaleweb.connection.IUserBean;
import com.airfrance.squaleweb.connection.IUserBeanAccessor;
import com.airfrance.squaleweb.connection.exception.ConnectionException;


/**
 * This class is the basic implementation of the userBean accessor.
 * This class contains methods for authentication, verify connection and disconnection.  
 * In the basic implementation the authentication verification is done 
 * directly in the squale database (in the userBO table) 
 */
public class BasicUserBeanAccessorImpl implements IUserBeanAccessor {
	
	 /**
     * The userBean
     */
    private IUserBean userBean;
	
    /**
     * Default constructor
     */
	public BasicUserBeanAccessorImpl() {
		super();
	}
	
	
	/**
     * This method do the authentication of the user.
     * @param user object with only the identify and the password fill
     * @return true if the user is recognized
     */	
    public boolean isUser (UserDTO user ) {
    	boolean isUser=false;
    	try {
			IApplicationComponent ac = AccessDelegateHelper.getInstance("Login");
			Object[] paramIn = { user };
			UserDTO userInBase = (UserDTO) ac.execute("userAuthentication", paramIn);
			if (userInBase.getMatricule()!=null && userInBase.getPassword()!=null){
				List profiles = new ArrayList();
				profiles.add(userInBase.getDefaultProfile().getName());	   
		        userBean = new BasicUserBeanImpl(userInBase.getMatricule(),profiles);
		        isUser=true;
		    } 			
		} catch (JrafEnterpriseException e) {
			e.printStackTrace();
		}
    	return isUser;
    }
    
    
    /**
     * This method indicate if the user is connected to the application or not
     * @return true if the user is connected
     */
    public boolean isConnect (){
    	boolean isConnect =false;
    	if (userBean.getIdentifier()!=null){
    		isConnect=true;
    	}
    	return isConnect;
    }
    
    
    /**This method disconnect the user from the application
     * @param session The session in used
     */
    public void disConnect (HttpSession session){
    	userBean = new BasicUserBeanImpl();
    	session.invalidate();
    }
    
    
    /**
     * This method return the userBean of the bean accessor
     * @param pRequest the request used
     * @return the authenticated userBean of the bean accessor
     * @throws ConnectionException 
     */
	public IUserBean getUserBean(HttpServletRequest request)
			throws ConnectionException {
		//userBean = new UserBeanImpl();
		return userBean;
	}
	
	/**
     * This method return the userBean of the bean accessor
     * @return The userBean of the bean accessor
     */
    public IUserBean getUserBean() {
        return userBean;
    }
}
