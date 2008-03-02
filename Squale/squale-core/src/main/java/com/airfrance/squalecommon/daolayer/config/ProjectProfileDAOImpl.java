package com.airfrance.squalecommon.daolayer.config;

import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;

/**
 * DAO pour ProjectProfileBO
 */
public class ProjectProfileDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static ProjectProfileDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new ProjectProfileDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private ProjectProfileDAOImpl()
    {
        initialize( ProjectProfileBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ProjectProfileDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Supprimer les profiles qui ne sont pas dans la collection
     * 
     * @param pSession la session hibernate
     * @param pProfiles les profiles qui doivent être présents en base
     * @throws JrafDaoException si erreur
     */
    public void removeOthers( ISession pSession, Collection pProfiles )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".name not in(" );
        Iterator profilesIt = pProfiles.iterator();
        // On ajoute les noms des profiles qui ne doivent pas être renvoyés
        boolean first = true;
        while ( profilesIt.hasNext() )
        {
            if ( first )
            {
                first = false;
            }
            else
            {
                whereClause.append( ", " );
            }
            whereClause.append( "'" + ( (ProjectProfileBO) profilesIt.next() ).getName() + "'" );
        }
        whereClause.append( ")" );
        Collection results = findWhere( pSession, whereClause.toString() );
        Iterator it = results.iterator();
        ProjectProfileBO profileBO = null;
        // Suppression de chaque profil
        while ( it.hasNext() )
        {
            profileBO = (ProjectProfileBO) it.next();
            remove( pSession, profileBO );
        }
    }

    /**
     * Retourne le profile dont le nom est pName
     * 
     * @param pSession la session hibernate
     * @param pName le nom du profile
     * @return le profile si il existe, null sinon
     * @throws JrafDaoException si erreur
     */
    public ProjectProfileBO findWhereName( ISession pSession, String pName )
        throws JrafDaoException
    {
        ProjectProfileBO result = null;
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".name = '" );
        whereClause.append( pName );
        whereClause.append( "'" );
        Collection results = this.findWhere( pSession, whereClause.toString() );
        Iterator it = results.iterator();
        // Il ne doit y avoir qu'un résultat:
        if ( it.hasNext() )
        {
            result = (ProjectProfileBO) it.next();
        }
        return result;
    }

    /**
     * Renvoit les profils présents en base mais non présents dans la liste donnée en paramètre
     * 
     * @param pSession la session courante
     * @param pProfiles la liste des profils
     * @return la liste des ProjectProfileBO
     * @throws JrafDaoException si erreur
     */
    public Collection findOthers( ISession pSession, Collection pProfiles )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".name not in(" );
        Iterator profilesIt = pProfiles.iterator();
        boolean first = true;
        while ( profilesIt.hasNext() )
        {
            if ( first )
            {
                first = false;
            }
            else
            {
                whereClause.append( ", " );
            }
            whereClause.append( "'" + ( (ProjectProfileBO) profilesIt.next() ).getName() + "'" );
        }
        whereClause.append( ")" );
        return findWhere( pSession, whereClause.toString() );
    }

    /**
     * Obtention des profils
     * 
     * @param pSession session
     * @return profils triés par nom
     * @throws JrafDaoException si erreur
     */
    public Collection findProfiles( ISession pSession )
        throws JrafDaoException
    {
        String whereClause = "";
        whereClause += "order by " + getAlias() + ".name";
        Collection col = findWhere( pSession, whereClause );
        return col;
    }
}
