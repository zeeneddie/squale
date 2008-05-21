/*
 * Créé le 8 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.ErrorBO;

/**
 * @author M400843
 */
public class ErrorDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static ErrorDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new ErrorDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private ErrorDAOImpl()
    {
        initialize( ErrorBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static ErrorDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Supprime toutes les erreur liées à un audit
     * 
     * @param pSession la session
     * @param pAudit l'audit
     * @throws JrafDaoException si une erreur à lieu
     */
    public void removeWhereAudit( ISession pSession, AuditBO pAudit )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".audit.id = " + pAudit.getId();

        removeWhere( pSession, whereClause );
    }

    /**
     * Supprime toutes les erreurs liées à un projet
     * 
     * @param pSession la session
     * @param pProjet le projet
     * @throws JrafDaoException si une erreur à lieu
     */
    public void removeWhereProject( ISession pSession, ProjectBO pProjet )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = " + pProjet.getId();

        removeWhere( pSession, whereClause );
    }

    /**
     * Permet de recuperer une collection de ErrorBO pour un audit et une liste de noms de taches donnés
     * 
     * @param pSession session Hibernate
     * @param pAuditID identifiant de l'audit
     * @param pProjectId identifiant du projet
     * @param pTasks liste de nom de taches, si <code>null</code> recherche pour toutes les taches
     * @param pNbLignes nombre de lignes, si <code>null</code> retourne toutes les erreurs trouvées
     * @param pIndexDepart index de depart
     * @return Collection de ErrorDTO complete (vide mais instanciée si pas d'errorBO en base)
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhere( ISession pSession, Long pAuditID, Long pProjectId, Collection pTasks,
                                 Integer pNbLignes, Integer pIndexDepart )
        throws JrafDaoException
    {
        List errors;
        int startId = 0;
        if ( null != pIndexDepart )
        {
            startId = pIndexDepart.intValue();
        }
        // Création de la requete :
        // sélection pour l'audit demandé et le projet
        String whereClause = getWhereProjectAndAudit( pAuditID, pProjectId );
        if ( null == pTasks )
        {
            // si aucune tache n'est précisé on récupère les erreurs
            errors = (List) findWhere( pSession, whereClause );
        }
        else
        {
            // sinon on ajoute dans la requete le nom de la tache :
            whereClause += " and ";
            whereClause += getAlias() + ".taskName = ";
            errors = new ArrayList();
            Iterator it = pTasks.iterator();
            while ( it.hasNext() )
            {
                // Pour chaque tache : on récupère les erreurs concernées
                List col;
                String taskName = (String) it.next();
                String newWhereClause = whereClause + "'" + taskName + "'";
                // On tri par id --> ordre d'enregistrement
                whereClause += " order by " + getAlias() + ".id";
                if ( null != pNbLignes )
                {
                    col = (List) findWhereScrollable( pSession, newWhereClause, pNbLignes.intValue(), startId, false );
                }
                col = (List) findWhere( pSession, newWhereClause );
                if ( null != col && col.size() != 0 )
                {
                    // on ajoute les erreurs qui viennent d'etre récupérées à la collection
                    errors.addAll( col );
                }
            }
        }
        return errors;
    }

    /**
     * Permet de recuperer une collection de ErrorBO pour un audit et une liste de noms de taches donnés
     * 
     * @param pSession session Hibernate
     * @param pAuditID identifiant de l'audit
     * @param pProjetId identifiant du projet
     * @param pTasks liste de nom de taches, si <code>null</code> recherche pour toutes les taches
     * @return Collection de ErrorDTO complete
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereAuditAndTasks( ISession pSession, Long pAuditID, Long pProjetId, Collection pTasks )
        throws JrafDaoException
    {
        Collection ret = findWhere( pSession, pAuditID, pProjetId, pTasks, null, null );
        return ret;
    }

    /**
     * Crée l'erreur en vérifiant que son projet est bien relié à un Audit
     * 
     * @see com.airfrance.jraf.spi.persistence.IPersistenceDAO#create(com.airfrance.jraf.spi.persistence.ISession,
     *      java.lang.Object)
     */
    public void create( ISession pSession, ErrorBO pError )
        throws JrafDaoException
    {
        super.create( pSession, pError );
    }

    /**
     * @param pSession la session
     * @param pAuditId l'id de l'audit sur lequel on compte les erreurs
     * @param pProjectId l'id du projet sur lequel on compte les erreurs
     * @param pLevel le niveau des erreurs (peut être nul si on veut le nombre total des erreur)
     * @return le nombre d'erreurs par type
     * @throws JrafDaoException en cas d'échec
     */
    public Integer getNumberOfErrorsWhere( ISession pSession, Long pAuditId, Long pProjectId, String pLevel )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( getWhereProjectAndAudit( pAuditId, pProjectId ) );
        if ( null != pLevel )
        {
            whereClause.append( " and " );
            whereClause.append( getAlias() );
            whereClause.append( ".level='" );
            whereClause.append( pLevel );
            whereClause.append( "'" );
        }
        return countWhere( pSession, whereClause.toString() );
    }

    /**
     * @param pSession la session
     * @param pAuditId l'id de l'audit sur lequel on compte les erreurs
     * @param pProjectId l'id du projet sur lequel on compte les erreurs
     * @param pLevel le niveau des erreurs (peut être nul si on veut les noms des tâches en erreur)
     * @return les tâches qui ont au moins une erreur avec le niveau de criticité <code>pLevel</code>
     * @throws JrafDaoException en cas d'échec
     */
    public List getTasksNameWhere( ISession pSession, Long pAuditId, Long pProjectId, String pLevel )
        throws JrafDaoException
    {
        StringBuffer query = new StringBuffer( "select distinct " );
        query.append( getAlias() );
        query.append( ".taskName " );
        query.append( getRequete() );
        query.append( getWhereProjectAndAudit( pAuditId, pProjectId ) );
        if ( null != pLevel )
        {
            query.append( " and " );
            query.append( getAlias() );
            query.append( ".level='" );
            query.append( pLevel );
            query.append( "'" );
        }
        query.append( "order by " );
        query.append( getAlias() );
        query.append( ".taskName" );
        return find( pSession, query.toString() );
    }

    /**
     * @param pAuditId l'id de l'audit
     * @param pProjectId l'id du projet
     * @return les conditions par rapport à l'audit et au projet à mettre dans une clause Where
     */
    private String getWhereProjectAndAudit( Long pAuditId, Long pProjectId )
    {
        StringBuffer commonWhereClause = new StringBuffer( "where " );
        commonWhereClause.append( getAlias() );
        commonWhereClause.append( ".audit.id=" );
        commonWhereClause.append( pAuditId.longValue() );
        commonWhereClause.append( " and " );
        commonWhereClause.append( getAlias() );
        commonWhereClause.append( ".project.id=" );
        commonWhereClause.append( pProjectId );
        return commonWhereClause.toString();
    }

    /**
     * Get all errors for an audit, a project and a criticity (facultative)
     * @param pSession hibernate session
     * @param pAuditId audit id condition
     * @param pProjectId project id condition
     * @param pLevel level of error criticity (error, warning, info) or null if level has not have to be
     * a condition for search
     * @return list of ErrorBO occured while executing audit on the project
     * @throws JrafDaoException if error occured
     */
    public List findAllWhere( ISession pSession, Long pAuditId, Long pProjectId, String pLevel ) throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( getWhereProjectAndAudit( pAuditId, pProjectId ) );
        if ( pLevel != null )
        {
            whereClause.append( " and " );
            whereClause.append( getAlias() );
            whereClause.append( ".level='" );
            whereClause.append( pLevel );
            whereClause.append( "'" );
        }
        return findWhere( pSession, whereClause.toString() );
    }
}
