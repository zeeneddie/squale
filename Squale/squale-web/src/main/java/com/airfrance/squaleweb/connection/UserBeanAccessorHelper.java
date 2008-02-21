package com.airfrance.squaleweb.connection;

import com.airfrance.jraf.bootstrap.locator.SpringLocator;

/**
 * Accès au provider de sécurité
 */
public class UserBeanAccessorHelper {


    /**
     * Retourne le bean permettant d'accèder à l'utilisateur authentifié
     * @return l'accesseur de bean
     */
    public final static IUserBeanAccessor getUserBeanAccessor() {
        IUserBeanAccessor accessor = null;
        SpringLocator springLocator = SpringLocator.getInstance();
        accessor = (IUserBeanAccessor) springLocator.getBean("userBeanAccessor");
        return accessor;
    }

}
