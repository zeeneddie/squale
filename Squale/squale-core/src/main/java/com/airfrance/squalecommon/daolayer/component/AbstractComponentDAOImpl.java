/*
 * Créé le 8 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * @author M400843
 */
public class AbstractComponentDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static AbstractComponentDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new AbstractComponentDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private AbstractComponentDAOImpl()
    {
        initialize( AbstractComponentBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( AbstractComponentDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static AbstractComponentDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Récupére les enfants de pProjet selon un audit et un type
     * 
     * @param pSession la session
     * @param pProjet le projet dont on veut les enfants
     * @param pAudit l'audit
     * @param pClass la classe du type dont on veut les enfants
     * @return une collection d'enfants du projet du type demandé
     * @throws JrafDaoException si une erreur à lieu
     */
    public Collection findProjectChildren( ISession pSession, ProjectBO pProjet, AuditBO pAudit, Class pClass )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) );
        Collection children = new ArrayList();

        // si la type d'enfant demandé est ApplicationBO, on retourne le projet
        if ( pClass.isAssignableFrom( ApplicationBO.class ) )
        {
            children.add( pProjet );
        }
        else
        {
            // Récupération de tout les composants étant rattaché à l'audit concerné
            String whereClause = "where ";
            whereClause += pAudit.getId() + " in elements(" + getAlias() + ".audits)";

            Iterator it = findWhere( pSession, whereClause ).iterator();
            while ( it.hasNext() )
            {
                // Pour chaque enfant
                AbstractComponentBO child = (AbstractComponentBO) it.next();
                if ( pClass.isInstance( child ) )
                {
                    /*
                     * Si l'enfant est du type souhaité, on vérifie qu'il est bien enfant du projet demandé
                     * (indispensable si l'application à plusieurs projets)
                     */
                    AbstractComponentBO tmpChild = child.getParent();
                    // tant que l'on a pas trouvé le projet et qu'il existe un parent
                    while ( ( null != tmpChild ) )
                    {
                        if ( tmpChild.equals( pProjet ) )
                        {
                            // si on a trouvé le projet, on ajoute l'enfant à la collection de retour
                            children.add( child );
                            // et on met tmpChild pour stopper le while
                            tmpChild = null;
                        }
                        else
                        {
                            // sinon on récupère le parent suivant (parent du parent...)
                            tmpChild = tmpChild.getParent();
                        }
                    }
                }
            }
        }
        LOG.debug( DAOMessages.getString( "dao.exit_method" ) );
        return children;
    }

    /**
     * Récupére le composant dont le nom est pName et dont le parent est pParent.
     * 
     * @param pSession la session
     * @param pParent le parent du composant que l'on veut
     * @param pName le nom du composant à chercher
     * @return le composant recherché ou null si il n'est pas présent en base.
     * @throws JrafDaoException si une erreur à lieu
     */
    public AbstractComponentBO findChild( ISession pSession, AbstractComponentBO pParent, String pName )
        throws JrafDaoException
    {
        LOG.debug( DAOMessages.getString( "dao.entry_method" ) + " findChild" );
        AbstractComponentBO child = null;
        String whereClause = "where ";
        whereClause += getAlias() + ".parent=" + pParent.getId();
        whereClause += "and " + getAlias() + ".name='" + pName + "'";
        Iterator it = findWhere( pSession, whereClause ).iterator();
        if ( it.hasNext() )
        {
            child = (AbstractComponentBO) it.next();
        }
        LOG.debug( DAOMessages.getString( "dao.exit_method" ) + " findChild" );
        return child;
    }

    /**
     * @param pSession la session
     * @return les composants exclus du plan d'action
     * @throws JrafDaoException en cas d'échec
     */
    public Collection getExcludedFromPlan( ISession pSession )
        throws JrafDaoException
    {
        Collection components = new ArrayList( 0 );
        String whereClause = "where " + getAlias() + ".excludedFromActionPlan=1";
        components = findWhere( pSession, whereClause );
        return components;
    }

    /**
     * @param pSession la session
     * @param pParentId l'id du parent
     * @param pAuditId l'id de l'audit, peut être <code>null</code>
     * @param pType le type de composants, peut être <code>null</code>
     * @param pFilter le filtre sur le nom, peut être <code>null</code>
     * @return les enfants (1000 au max) dont le parent a l'id <code>pParentId</code> de type <code>pType</code>
     *         rattachés à l'audit d'id <code>pAuditId</code> et dont le nom correspond au pattern
     *         <code>*pFilter*</code>
     * @throws JrafDaoException si erreur
     */
    public Collection findChildrenWhere( ISession pSession, Long pParentId, Long pAuditId, String pType, String pFilter )
        throws JrafDaoException
    {
        final int nbLines = 1000;
        StringBuffer whereClause =
            new StringBuffer( getCommonWhereChildrenRequest( pParentId, pAuditId, pType, pFilter ) );
        whereClause.append( " order by " );
        whereClause.append( getAlias() );
        whereClause.append( ".name" );
        LOG.warn( "findChildrenWhere: " + whereClause );
        return (Collection) findWhereScrollable( pSession, whereClause.toString(), nbLines, 0, false );
    }

    /**
     * @param pSession la session
     * @param pParentId l'id du parent
     * @param pAuditId l'id de l'audit, peut être <code>null</code>
     * @param pType le type de composants, peut être <code>null</code>
     * @param pFilter le filtre sur le nom, peut être <code>null</code>
     * @return le nombre d'enfants dont le parent a l'id <code>pParentId</code> de type <code>pType</code> rattachés
     *         à l'audit d'id <code>pAuditId</code> et dont le nom correspond au pattern <code>*pFilter*</code>
     * @throws JrafDaoException si erreur
     */
    public Integer countChildrenWhere( ISession pSession, Long pParentId, Long pAuditId, String pType, String pFilter )
        throws JrafDaoException
    {
        return countWhere( pSession, getCommonWhereChildrenRequest( pParentId, pAuditId, pType, pFilter ) );
    }

    /**
     * @param pParentId l'id du parent
     * @param pAuditId l'id de l'audit, peut être <code>null</code>
     * @param pType le type de composants, peut être <code>null</code>
     * @param pFilter le filtre sur le nom, peut être <code>null</code>
     * @return la clause where de recherche des enfants selon les critères passés en paramètre de type
     *         <code>pType</code> rattachés à l'audit d'id <code>pAuditId</code> et dont le nom correspond au
     *         pattern <code>*pFilter*</code>
     */
    private String getCommonWhereChildrenRequest( Long pParentId, Long pAuditId, String pType, String pFilter )
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() + ".parent.id=" + pParentId );
        if ( null != pAuditId )
        {
            whereClause.append( " and " );
            whereClause.append( pAuditId + " in elements (" + getAlias() + ".audits)" );
        }
        if ( null != pType )
        {
            // On récupère le nom de la classe
            String className = Mapping.getComponentMappingName( pType );
            whereClause.append( " and " );
            whereClause.append( getAlias() + ".class='" + className + "'" );
        }
        if ( null != pFilter )
        {
            whereClause.append( " and " );
            whereClause.append( getAlias() + ".name like '%" + pFilter + "%'" );
        }
        return whereClause.toString();
    }
}
