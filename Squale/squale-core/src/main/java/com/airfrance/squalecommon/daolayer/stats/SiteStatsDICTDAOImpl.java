package com.airfrance.squalecommon.daolayer.stats;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.stats.SiteStatsDICTBO;

/**
 */
public class SiteStatsDICTDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static SiteStatsDICTDAOImpl instance = null;

    /**
     * initialisation du singleton
     */
    static
    {
        instance = new SiteStatsDICTDAOImpl();
    }

    /**
     * Constructeur privé
     * 
     * @throws JrafDaoException
     */
    private SiteStatsDICTDAOImpl()
    {
        initialize( SiteStatsDICTBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static SiteStatsDICTDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * @param pSession la session
     * @param pSite l'id du site
     * @return une liste contenant les objets contenant les stats du site (normalement 1 seul objet)
     * @throws JrafDaoException en cas d'échecs
     */
    public Collection findBySite( ISession pSession, long pSite )
        throws JrafDaoException
    {
        Collection result = new ArrayList( 0 );
        String whereClause = "where " + getAlias() + ".serveurBO.serveurId = '" + pSite + "'";
        result = findWhere( pSession, whereClause );
        return result;
    }

    /**
     * Supprime les stats pour ce site
     * 
     * @param pSession la session
     * @param pSite l'id du site
     * @throws JrafDaoException en cas d'échecs
     */
    public void removeWhereSite( ISession pSession, long pSite )
        throws JrafDaoException
    {
        String whereClause = "where " + getAlias() + ".serveurBO.serveurId = '" + pSite + "'";
        removeWhere( pSession, whereClause );

    }

}
