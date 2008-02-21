package com.airfrance.squaleweb.connection;

import javax.servlet.http.HttpServletRequest;

import com.airfrance.squaleweb.connection.exception.ConnectionException;

/**
 * Interface pour accéder au bean utilisateur lors de la connexion
 */
public interface IUserBeanAccessor {
    
    /**
     * @param pRequest la requête permettant de récupérer le bean
     * @return l'utilisateur connecté
     * @throws ConnectionException si erreur de connexion
     */
    public IUserBean getUserBean(HttpServletRequest pRequest) throws ConnectionException;

}
