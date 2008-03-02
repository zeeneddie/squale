package com.airfrance.squalecommon.daolayer.component;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ProjectParameterBO;

/**
 * DAO d'accès au ProjectParameter
 */
public class ProjectParameterDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static ProjectParameterDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new ProjectParameterDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private ProjectParameterDAOImpl()
    {
        initialize( ProjectParameterBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( ProjectParameterDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ProjectParameterDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Persiste un ProjectParameterBO
     * 
     * @param pSession une session hibernate
     * @param pProjectParamBO le ProjectParameterBO à faire persister
     * @throws JrafDaoException en cas d'erreur
     */
    public void create( ISession pSession, ProjectParameterBO pProjectParamBO )
        throws JrafDaoException
    {
        super.create( pSession, pProjectParamBO );
    }

    /**
     * @param pSession une session hibernate
     * @param pProjectParamBO le ProjectParameterBO à supprimer
     * @throws JrafDaoException en cas d'erreur
     */
    public void remove( ISession pSession, ProjectParameterBO pProjectParamBO )
        throws JrafDaoException
    {
        super.remove( pSession, pProjectParamBO );
    }

    /**
     * @param pSession une session hibernate
     * @param pParamId l'id des paramètres du projet
     * @return l'ensemble des ProjectParameters de ce projet
     * @throws JrafDaoException en cas d'erreur
     */
    public Collection findWhere( ISession pSession, Long pParamId )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".id=" + pParamId;

        Collection ret = findWhere( pSession, whereClause );

        return ret;

    }

    /**
     * Met à jour les paramètres d'un projet en supprimant ses anciens paramètres et en créant les nouveaux.
     * 
     * @param pSession une session hibernate
     * @param pParamId l'id des paramètres du projet
     * @param pParameters les paramères du projet
     * @throws JrafDaoException en cas d'erreur
     */
    public void removeAndCreateNew( ISession pSession, Long pParamId, ProjectParameterBO pParameters )
        throws JrafDaoException
    {
        // On supprime ses anciens paramètres dans une autre session
        this.removeParameters( pSession, pParamId );
        // On crée les nouveaux:
        this.create( pSession, pParameters );
    }

    /**
     * Supprime tous les paramètres d'un projet
     * 
     * @param pSession une session hibernate
     * @param pProjectId l'id du projet possédant ces paramètres
     * @throws JrafDaoException en cas d'erreur
     */
    public void removeParameters( ISession pSession, Long pProjectId )
        throws JrafDaoException
    {
        Collection parameters = this.findWhere( pSession, pProjectId );
        Iterator it = parameters.iterator();
        while ( it.hasNext() )
        {
            this.remove( pSession, (ProjectParameterBO) it.next() );
        }
    }

}