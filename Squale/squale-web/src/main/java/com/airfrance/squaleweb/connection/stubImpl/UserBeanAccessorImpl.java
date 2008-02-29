package com.airfrance.squaleweb.connection.stubImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squaleweb.connection.IUserBean;
import com.airfrance.squaleweb.connection.IUserBeanAccessor;
import com.airfrance.squaleweb.connection.exception.ConnectionException;

public class UserBeanAccessorImpl implements IUserBeanAccessor {
	
	  /**
     * L'utilisateur connecté
     */
    private IUserBean userBean;
	
    /**
     * Constructeur par défaut
     */
	public UserBeanAccessorImpl() {
		super();
	}

	/**
     * Constructeur
     * @param pUserBean l'utilisateur
     */
    public UserBeanAccessorImpl(IUserBean pUserBean) {
        userBean = pUserBean;
    }
	
    
    
    /**
     * 
     */
	public IUserBean getUserBean(HttpServletRequest request)
			throws ConnectionException {
		userBean = new UserBeanImpl();
		return userBean;
	}
	
	 /**
     * @return l'utilisateur
     */
    public IUserBean getUserBean() {
        return userBean;
    }

    /**
     * @param pBean l'utilisateur
     */
    public void setUserBean(IUserBean pBean) {
        userBean = pBean;
    }

	public boolean isConnect() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isUser(UserDTO user) {
		// TODO Auto-generated method stub
		return false;
	}

	public void disConnect(HttpSession session) {
		// TODO Auto-generated method stub
		
	}
}
