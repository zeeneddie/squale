package com.airfrance.squalecommon.daolayer.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskBO;

/**
 * DAO pour TaskBO
 */
public class TaskDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static TaskDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new TaskDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private TaskDAOImpl()
    {
        initialize( TaskBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static TaskDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Supprime toutes les tâches
     * 
     * @param pSession la session hibernate
     * @throws JrafDaoException si une erreur survient
     */
    public void removeAllTasks( ISession pSession )
        throws JrafDaoException
    {
        Collection param = super.findAll( pSession );
        if ( param != null )
        {
            Iterator it = param.iterator();
            while ( it.hasNext() )
            {
                super.remove( pSession, (TaskBO) it.next() );
            }
        }
    }

    /**
     * Liste toutes les taches
     * 
     * @param pSession la session hibernate
     * @return la collection des taches
     * @throws JrafDaoException si une erreur survient
     */
    public Collection listAllTasks( ISession pSession )
        throws JrafDaoException
    {
        // pour faire un cast
        Collection param = super.findAll( pSession );
        Collection result = null;
        if ( param != null && param.size() != 0 )
        {
            result = new ArrayList();
            Iterator it = param.iterator();
            while ( it.hasNext() )
            {
                result.add( (TaskBO) it.next() );
            }
        }
        return result;
    }
}
