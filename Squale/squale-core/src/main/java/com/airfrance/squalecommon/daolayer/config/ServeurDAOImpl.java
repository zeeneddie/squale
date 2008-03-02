package com.airfrance.squalecommon.daolayer.config;

import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;

import java.util.Collection;

/**
 * DAO du Serveur d'exécution de Squalix
 */
public class ServeurDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static ServeurDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new ServeurDAOImpl();
    }

    /**
     * Constructeur privé
     */
    private ServeurDAOImpl()
    {
        initialize( ServeurBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ServeurDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Donne la liste de tous les serveurs
     * 
     * @param pSession la session hibernate
     * @return la liste des serveurs
     * @throws JrafDaoException si une erreur survient
     */
    public Collection listeServeurs( ISession pSession )
        throws JrafDaoException
    {
        Collection pCollection = super.findAll( pSession );
        return pCollection;
    }

}
