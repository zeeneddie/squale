/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalecommon.daolayer.result;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.provider.persistence.hibernate.SessionImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.DAOMessages;
import org.squale.squalecommon.daolayer.DAOUtils;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;

/**
 * @author M400843
 */
public class QualityResultDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static QualityResultDAOImpl instance;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new QualityResultDAOImpl();
    }

    /**
     * Constructeur prive
     */
    private QualityResultDAOImpl()
    {
        initialize( QualityResultBO.class );
        LOG = LogFactory.getLog( MarkDAOImpl.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static QualityResultDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Supprime tous les résultats qualités liés à un projet
     * 
     * @param pSession la session
     * @param pProject le projet
     * @throws JrafDaoException si une erreur à lieu
     */
    public void removeWhereProject( ISession pSession, ProjectBO pProject )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = " + pProject.getId();

        removeWhere( pSession, whereClause );
    }

    /**
     * Permet de récupérer les notes en fonction d'une liste de composants
     * 
     * @param pSession session Hibernate
     * @param pProjectIDs liste des identifiants des composants
     * @param pAuditID identifiant de l'audit
     * @param pRuleId id de la règle qualité
     * @return liste des valeurs ordonnés par raopport a la liste des composants
     * @throws JrafDaoException exception DAO
     */
    public List findWhere( ISession pSession, List pProjectIDs, Long pAuditID, Long pRuleId )
        throws JrafDaoException
    {
        List marks = new ArrayList();
        Iterator it = pProjectIDs.iterator();
        while ( it.hasNext() )
        {
            Long componentId = (Long) it.next();
            marks.add( load( pSession, componentId, pAuditID, pRuleId ) );
        }
        return marks;
    }

    /**
     * Permet de récupérer les résultats de qualités en fonction d'une liste d'audits
     * 
     * @param pSession session Hibernate
     * @param pProjectID identifiant du composant
     * @param pAuditIDs liste des identifiants de l'audit
     * @param pRuleId id de la règle qualité
     * @return liste des valeurs ordonnés par raopport a la liste des composants
     * @throws JrafDaoException exception DAO
     */
    public List findWhere( ISession pSession, Long pProjectID, List pAuditIDs, Long pRuleId )
        throws JrafDaoException
    {
        List marks = new ArrayList();
        Iterator it = pAuditIDs.iterator();
        while ( it.hasNext() )
        {
            Long auditId = (Long) it.next();
            marks.add( load( pSession, pProjectID, auditId, pRuleId ) );
        }
        return marks;
    }

    /**
     * Permet de récupérer un résultat de qualité en fonction d'un d'audit, d'un composant et d'un TRE
     * 
     * @param pSession session Hibernate
     * @param pProjectID identifiant du composant
     * @param pAuditID identifiant de l'audit
     * @param pRuleId id du TRE
     * @return un résultat de qualité
     * @throws JrafDaoException exception DAO
     */
    public QualityResultBO load( ISession pSession, Long pProjectID, Long pAuditID, Long pRuleId )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".rule.id = " + pRuleId;
        whereClause += " and ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";

        QualityResultBO result = null;
        Collection col = findWhere( pSession, whereClause );
        if ( col.size() >= 1 )
        {
            result = (QualityResultBO) col.iterator().next();
            if ( col.size() > 1 )
            {
                String tab[] = { pAuditID.toString(), pProjectID.toString(), pRuleId.toString() };
                LOG.warn( DAOMessages.getString( "qualityresult.many.audit_project_tre", tab ) );
            }
        }
        return result;
    }

    /**
     * Obtention des résultats sur les facteurs
     * 
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @return résultats associés
     * @throws JrafDaoException si erreur
     */
    public Collection findWhere( ISession pSession, Long pProjectID, Long pAuditID )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".rule.class='FactorRule'";
        whereClause += " order by " + getAlias() + ".rule.name";

        QualityResultBO result = null;
        Collection col = findWhere( pSession, whereClause );
        return col;
    }

    /**
     * Obtention des résultats sur les pratiques
     * 
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @return résultats associés
     * @throws JrafDaoException si erreur
     */
    public Collection findPracticesWhere( ISession pSession, Long pProjectID, Long pAuditID )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".rule.class='PracticeRule'";
        whereClause += " order by " + getAlias() + ".rule.name";

        QualityResultBO result = null;
        Collection col = findWhere( pSession, whereClause );
        return col;
    }

    /**
     * Obtention des résultats d'un projet
     * 
     * @param pSession session
     * @param pProjectID projet
     * @param pAuditID audit
     * @param pRuleIDs ids des règles qualité
     * @return résultats du projet triés par nom de règle
     * @throws JrafDaoException si erreur
     */

    public Collection findWhere( ISession pSession, Long pProjectID, Long pAuditID, Collection pRuleIDs )
        throws JrafDaoException
    {
        // Initialisation
        Collection col = new ArrayList();
        // Protection du code car in () n'est pas valable
        if ( pRuleIDs.size() > 0 )
        {
            StringBuffer ruleIds = new StringBuffer();
            Iterator rules = pRuleIDs.iterator();
            while ( rules.hasNext() )
            {
                if ( ruleIds.length() > 0 )
                {
                    ruleIds.append( ',' );
                }
                ruleIds.append( ( (Long) rules.next() ).longValue() );
            }
            String whereClause = "where ";
            whereClause += getAlias() + ".project.id = '" + pProjectID + "'";
            whereClause += " and ";
            whereClause += getAlias() + ".audit.id = '" + pAuditID + "'";
            whereClause += " and ";
            whereClause +=
                getAlias() + ".rule.id in (" + ruleIds.toString() + ") order by " + getAlias() + ".rule.name";
            // Requête dans la base
            col = findWhere( pSession, whereClause );
            // On complète avec des résultats nuls dans ce cas
            if ( col.size() != pRuleIDs.size() )
            {
                col = adaptResults( pRuleIDs, col );
            }
        }
        return col;
    }

    /**
     * Obtention des résultats d'un projet
     * 
     * @param pSession session
     * @param pAcceptanceLevel le niveau (accepté, accepté avec réserves, refusés)
     * @param pSiteId l'id du site
     * @return résultats du projet triés par nom de règle
     * @throws JrafDaoException si erreur
     */
    public int countFactorsByAcceptanceLevelAndSite( ISession pSession, String pAcceptanceLevel, long pSiteId )
        throws JrafDaoException
    {
        int result = 0;
        String whereClause = "where " + getAlias() + ".class='FactorResult'";
        if ( pAcceptanceLevel.equals( QualityResultBO.ACCEPTED ) )
        {
            whereClause += " AND " + getAlias() + ".meanMark <= 3 AND " + getAlias() + ".meanMark >= 2 ";
        }
        else
        {
            if ( pAcceptanceLevel.equals( QualityResultBO.RESERVED ) )
            {
                whereClause += " AND " + getAlias() + ".meanMark < 2 AND " + getAlias() + ".meanMark >= 1 ";
            }
            else
            {
                if ( pAcceptanceLevel.equals( QualityResultBO.REFUSED ) )
                {
                    whereClause += " AND " + getAlias() + ".meanMark < 1 AND " + getAlias() + ".meanMark > 0 ";
                }
            }
        }
        if ( pSiteId != 0 )
        {
            whereClause += " AND " + getAlias() + ".project.parent.serveurBO.serveurId='" + pSiteId + "'";
        }
        // On ne prend que les applications non supprimées et les audits réussis
        // ou partiels
        whereClause += " AND " + getAlias() + ".project.parent.status!=" + ApplicationBO.DELETED;
        // whereClause += " AND " + getAlias()+ ".audit.id =(" + selectLastAudit + ")";
        whereClause += " AND (" + getAlias() + ".audit.status=" + AuditBO.TERMINATED;
        whereClause += " or " + getAlias() + ".audit.status=" + AuditBO.PARTIAL + ")";
        // On tri pour récupérer les résultats selon le dernier audit réussi ou partiel
        // si il n'y a aucun audit réussi
        whereClause += " order by " + getAlias() + ".project.id, ";
        whereClause += getAlias() + ".audit.status asc, coalesce(";
        whereClause += getAlias() + ".audit.historicalDate, ";
        whereClause += getAlias() + ".audit.date) desc";
        LOG.debug( "countFactorsByAcceptanceLevelAndSite:" + whereClause );
        // Requête dans la base
        List results = findWhere( pSession, whereClause );
        long lastProjectId = -1;
        long lastAuditId = -1;
        for ( int i = 0; i < results.size(); i++ )
        {
            QualityResultBO cur = (QualityResultBO) results.get( i );
            if ( cur.getProject().getId() != lastProjectId )
            {
                result++;
                lastAuditId = cur.getAudit().getId();
            }
            else if ( cur.getAudit().getId() == lastAuditId )
            {
                result++;
            }
            lastProjectId = cur.getProject().getId();
        }
        return result;
    }

    /**
     * Adaptation des résultats La liste des résultats peut être différente de celle des règles, dans ce cas on crée des
     * résultats vierges pour faire en sorte que la liste des résultats concorde avec celle des règles
     * 
     * @param pRuleIDs liste des règles
     * @param pResults liste des résultats
     * @return liste des résultats adaptée
     */
    private Collection adaptResults( Collection pRuleIDs, Collection pResults )
    {
        Collection result = new ArrayList();
        Iterator rules = pRuleIDs.iterator();
        Iterator results = pResults.iterator();
        QualityResultBO currentResult = null;
        // Initialisation du résultat courant
        if ( results.hasNext() )
        {
            currentResult = (QualityResultBO) results.next();
        } // Parcours de toutes les règles
        while ( rules.hasNext() )
        {
            Long rule = (Long) rules.next();
            // Si il n'existe pas de résultat correspondant à la règle, on
            // en crée un qui soit vierge avec une note de -1
            if ( ( currentResult == null ) || ( rule.longValue() != currentResult.getRule().getId() ) )
            {
                QualityResultBO res = new FactorResultBO( -1.0f, null, null, null );
                result.add( res );
            }
            else
            { // Le résultat est en concordance avec la règle
                result.add( currentResult ); // On passe au résultat suivant
                if ( results.hasNext() )
                {
                    currentResult = (QualityResultBO) results.next();
                }
                else
                {
                    currentResult = null;
                }
            }
        }
        return result;
    }

    /**
     * This method recover the last mark inserted (if there is one) for a manual practice
     * 
     * @param pSession hibernate session
     * @param pProjectID Id of the project
     * @param pRuleId Id of the practice rule
     * @return The PraticeresultBO linked to the last manual mark inserted for the rule id and the project id give in
     *         argument. This method return null if it finds nothing.
     * @throws JrafDaoException exception happen during the hibernate search
     */
    public PracticeResultBO findLastManualMark( ISession pSession, Long pProjectID, Long pRuleId )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() + ".rule.id = " + pRuleId );
        whereClause.append( " and " + getAlias() + ".project.id = " + pProjectID );
        whereClause.append( " and " + getAlias() + ".meanMark!= -1.0" );
        // This mark is not linked to an audit
        whereClause.append( " and " + getAlias() + ".audit.id is null" );
        whereClause.append( " order by  " + getAlias() + ".creationDate desc" );
        whereClause.append( " , " + getAlias() + ".id desc" );

        List col = findWhere( pSession, whereClause.toString() );

        PracticeResultBO result = null;
        if ( col.size() > 0 )
        {
            // Recovery the last inserted mark
            result = (PracticeResultBO) col.get( 0 );
        }

        return result;
    }

    /**
     * This method return the list of all the mark inserted for manual practice between the date give in argument and
     * today
     * 
     * @param pSession The hibernate session
     * @param pProjectID The ID of the project
     * @param pRuleId The rule ID
     * @param date The limit date
     * @return The list of mark
     * @throws JrafDaoException Exception happen during the search in the DB
     */
    public Collection<PracticeResultBO> findManualMarkSince( ISession pSession, Long pProjectID, Long pRuleId, Date date )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() + ".rule.id = " + pRuleId );
        whereClause.append( " and " + getAlias() + ".project.id = " + pProjectID );
        whereClause.append( " and " + getAlias() + ".meanMark!= -1.0" );
        whereClause.append( " and " + getAlias() + ".audit.id is null" );
        whereClause.append( " and " + getAlias() + ".creationDate > " + DAOUtils.makeQueryDate( date ) );
        whereClause.append( " order by  " + getAlias() + ".creationDate desc" );
        whereClause.append( " , " + getAlias() + ".id desc" );

        Collection<PracticeResultBO> col = findWhere( pSession, whereClause.toString() );
        return col;
    }
    
    /***
     * This method return the last manual mark inserted (if there is one) for a manual practice
     * and a specific audit 
     * 
     * @param pSession The hibernate session
     * @param pProjectId The ID of the project
     * @param pRuleId The rule ID
     * @param pAuditId The audit ID
     * @return The PracticeRsultBO linked to the last manual mark inserted for the rule, the project 
     * and the audit specified in argument. This method returns null if it finds nothing.
     * @throws JrafDaoException Exception happen during the hibernate search
     */
    public PracticeResultBO findLastManualMarkByAudit ( ISession pSession, Long pProjectId, Long pRuleId, Long pAuditId )
        throws JrafDaoException
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() + ".rule.id = " + pRuleId );
        whereClause.append( " and " + getAlias() + ".project.id = " + pProjectId );
        whereClause.append( " and " + getAlias() + ".audit.id = " + pAuditId );
        whereClause.append( " and " + getAlias() + ".meanMark!= -1.0" );
        
        List col = findWhere( pSession, whereClause.toString() );
        PracticeResultBO result = null;
        if ( col.size() > 0 )
        {
            // Recovery the last inserted mark for a specific audit
            result = (PracticeResultBO) col.get( 0 );
        }
        
        return result;
    }
}
