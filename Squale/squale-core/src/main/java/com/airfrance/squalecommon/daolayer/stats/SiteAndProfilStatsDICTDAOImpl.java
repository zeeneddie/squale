package com.airfrance.squalecommon.daolayer.stats;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.stats.SiteAndProfilStatsDICTBO;

/**
 */
public class SiteAndProfilStatsDICTDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static SiteAndProfilStatsDICTDAOImpl instance = null;

    /**
     * initialisation du singleton
     */
    static
    {
        instance = new SiteAndProfilStatsDICTDAOImpl();
    }

    /**
     * Constructeur privé
     * 
     * @throws JrafDaoException
     */
    private SiteAndProfilStatsDICTDAOImpl()
    {
        initialize( SiteAndProfilStatsDICTBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static SiteAndProfilStatsDICTDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * @param pSession la session
     * @param pSite l'id du site
     * @param pProfile le profil
     * @return une liste contenant les objets contenant les stats du site (normalement 1 seul objet)
     * @throws JrafDaoException en cas d'échecs
     */
    public Collection findBySiteAndProfil( ISession pSession, long pSite, String pProfile )
        throws JrafDaoException
    {
        Collection result = new ArrayList( 0 );
        String whereClause = "where " + getAlias() + ".serveurBO.serveurId = '" + pSite + "'";
        whereClause += " AND " + getAlias() + ".profil = '" + pProfile + "'";
        result = findWhere( pSession, whereClause );
        return result;
    }

    /**
     * Supprime l'objet SiteAndProfilStatsDICTBO avec ce profil et ce nom
     * 
     * @param pSession la session
     * @param pSite l'id du site
     * @param pProfile le profil
     * @throws JrafDaoException en cas d'échecs
     */
    public void removeWhereSiteAndProfil( ISession pSession, long pSite, String pProfile )
        throws JrafDaoException
    {
        String whereClause = "where " + getAlias() + ".serveurBO.serveurId = '" + pSite + "'";
        whereClause += " AND " + getAlias() + ".profil = '" + pProfile + "'";
        removeWhere( pSession, whereClause );
    }

    /**
     * Supprime les objets SiteAndProfilStatsDICTBO pour ce site
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
