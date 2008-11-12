package com.airfrance.squalecommon.daolayer.result;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOUtils;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MetricBO;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 */
public class MetricDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static MetricDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new MetricDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private MetricDAOImpl()
    {
        initialize( MetricBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static MetricDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Retourne la volumétrie d'un projet
     * 
     * @param pSession le session
     * @param pProjectId l'id du projet
     * @return la volumétrie des applis du site
     * @throws JrafDaoException en cas d'échec
     */
    public int getVolumetry( ISession pSession, Long pProjectId )
        throws JrafDaoException
    {
        String whereClause = " where " + getAlias() + ".name='sloc'";
        whereClause += " AND " + getAlias() + ".measure.component=" + pProjectId;
        Collection coll = findWhere( pSession, whereClause );
        int counter = 0;
        Iterator it = coll.iterator();
        while ( it.hasNext() )
        {
            IntegerMetricBO value = (IntegerMetricBO) it.next();
            counter += ( (Integer) value.getValue() ).intValue();
        }
        return counter;
    }

    /**
     * Retourne la volumétrie d'un site
     * 
     * @param pSession le session
     * @param pSiteId l'id du site
     * @return la volumétrie des applis du site
     * @throws JrafDaoException en cas d'échec
     */
    public int getVolumetryBySite( ISession pSession, long pSiteId )
        throws JrafDaoException
    {
        String whereClause = " where " + getAlias() + ".name='sloc'";
        whereClause += " AND " + getAlias() + ".measure.component.parent.serveurBO.serveurId='" + pSiteId + "'";
        Collection coll = findWhere( pSession, whereClause );
        int counter = 0;
        Iterator it = coll.iterator();
        while ( it.hasNext() )
        {
            IntegerMetricBO value = (IntegerMetricBO) it.next();
            counter += ( (Integer) value.getValue() ).intValue();
        }
        return counter;
    }

    /**
     * Retourne la volumétrie des derniers audits réussis par rapport à un site et à un profil pour les derniers audits
     * exécutés (réussis ou partiels)
     * 
     * @param pSession le session
     * @param pSiteId l'id du site
     * @param pProfile le profil du projet (java, cpp...)
     * @return la volumétrie des applis du site
     * @throws JrafDaoException en cas d'échec
     */
    public int getVolumetryBySiteAndProfil( ISession pSession, long pSiteId, String pProfile )
        throws JrafDaoException
    {
        // TODO : Il faudrait gérer en configuration les métriques nécessaires au calcul de la volumétrie
        // On sélectionne le nom de la métrique correspondant au nombre de lignes
        String whereClause = " where (" + getAlias() + ".name='sloc'";
        // ansi que le nombre de lignes JSP
        whereClause += "or " + getAlias() + ".name='numberOfJSPCodeLines')";
        // critère du site
        whereClause += " AND " + getAlias() + ".measure.component.parent.serveurBO.serveurId='" + pSiteId + "'";
        // on ne prend pas en compte les applications supprimées
        whereClause += " AND " + getAlias() + ".measure.component.parent.status!=" + ApplicationBO.DELETED;
        // critère du profil
        whereClause += " AND " + getAlias() + ".measure.component.profile.name='" + pProfile + "'";
        // Les audits doivent être supprimés ou partiels
        whereClause += " AND (" + getAlias() + ".measure.audit.status=" + AuditBO.TERMINATED;
        whereClause += " or " + getAlias() + ".measure.audit.status=" + AuditBO.PARTIAL + ")";
        whereClause += " order by " + getAlias() + ".measure.component.id, ";
        whereClause += getAlias() + ".measure.audit.status asc, coalesce(";
        whereClause += getAlias() + ".measure.audit.historicalDate, ";
        whereClause += getAlias() + ".measure.audit.date) desc";
        List results = findWhere( pSession, whereClause );
        int result = 0;
        long lastProjectId = -1;
        long lastAuditId = -1;
        for ( int i = 0; i < results.size(); i++ )
        {
            MetricBO cur = (MetricBO) results.get( i );
            if ( cur.getMeasure().getComponent().getId() != lastProjectId )
            {
                // Il s'agit d'entiers
                result += ( (Integer) cur.getValue() ).intValue();
                lastAuditId = cur.getMeasure().getAudit().getId();
            }
            else if ( cur.getMeasure().getAudit().getId() == lastAuditId )
            {
                // On ajoute la valeur car il s'agit d'une volumétrie différente
                result += ( (Integer) cur.getValue() ).intValue();
            }
            lastProjectId = cur.getMeasure().getComponent().getId();
        }
        return result;
    }

    /**
     * Récupération des mesures sur un audit
     * 
     * @param pSession session Hibernate
     * @param pAppliID identifiant de l'appli
     * @param pSince la date a partir de laquelle on veut calculer les ROI
     * @return ensemble des mesures à l'exclusion des transgressions
     * @throws JrafDaoException exception DAO
     */
    public Collection findROIWhereApplicationSinceDate( ISession pSession, Long pAppliID, Date pSince )
        throws JrafDaoException
    {
        String whereClause = "where ";
        whereClause += getAlias() + ".measure.component.id = '" + pAppliID + "'";
        whereClause += " and ";
        whereClause += getAlias() + ".measure.class = 'Roi'";
        whereClause += " and ";
        whereClause += getAlias() + ".measure.audit.date > " + DAOUtils.makeQueryDate( pSince );
        // Un nom de subclass correspondant à une transgression doit finir par 'Transgression'
        // TODO : Il faudra sûrement considérer les transgressions comme des mesures à part...
        whereClause += " and " + getAlias() + ".class not like '%Transgression'";
        Collection col = findWhere( pSession, whereClause );
        return col;
    }

    /**
     * Retourne les valeur distinct de mesure de type Integer données par leur TRE
     * 
     * @param pSession Session Jraf
     * @param pProjectId Id du projet
     * @param pAuditId Id de l'audit courant
     * @param pTreKey Liste des Tre à recuperer / trié
     * @return Liste de masures distinctes (Collection de Object[])
     * @throws JrafDaoException si pb de BD
     */
    public IntegerMetricBO findIntegerMetricWhere( ISession pSession, long pProjectId, long pAuditId, String pTreKey )
        throws JrafDaoException
    {
        IntegerMetricBO result = null;
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( getAlias() );
        whereClause.append( ".measure.audit.id=" );
        whereClause.append( pAuditId );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".measure.component.id=" );
        whereClause.append( pProjectId );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".measure.class=" );
        whereClause.append( Mapping.getMetricClass( pTreKey ).getName() );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".name='" );
        whereClause.append( pTreKey.substring( pTreKey.lastIndexOf( '.' ) + 1 ) );
        whereClause.append( "'" );
        whereClause.append( " and " );
        whereClause.append( getAlias() );
        whereClause.append( ".class='Int'" );
        List results = findWhere( pSession, whereClause.toString() );
        if ( results.size() > 0 )
        {
            result = (IntegerMetricBO) results.get( 0 );
        }
        return result;
    }

}
