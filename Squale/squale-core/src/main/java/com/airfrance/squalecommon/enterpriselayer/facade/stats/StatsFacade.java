package com.airfrance.squalecommon.enterpriselayer.facade.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.stats.SiteAndProfilStatsDICTDAOImpl;
import com.airfrance.squalecommon.daolayer.stats.SiteStatsDICTDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.stats.SetOfStatsDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.stats.ApplicationStatsTransformer;
import com.airfrance.squalecommon.datatransfertobject.transform.stats.StatsTransform;

/**
 */
public class StatsFacade
{

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * @param pDaysForTerminatedAudit le nombre de jours max pour lesquels il doit y avoir au moins un audit réussi pour
     *            que l'application soit active (sert pour les statistiques par application)
     * @param pDaysForAllAudits le nombre de jours défini pour compter les audits (sert pour les staistiques par
     *            application)
     * @return l'objet regroupant les données statistiques ainsi que les statistiques par application
     * @throws JrafEnterpriseException en cas d'échec
     */
    public static SetOfStatsDTO getStats( int pDaysForTerminatedAudit, int pDaysForAllAudits )
        throws JrafEnterpriseException
    {
        SiteAndProfilStatsDICTDAOImpl siteAndProfilDAO = SiteAndProfilStatsDICTDAOImpl.getInstance();
        SiteStatsDICTDAOImpl siteDAO = SiteStatsDICTDAOImpl.getInstance();
        ISession session = null;
        SetOfStatsDTO result = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            StatsTransform transform = new StatsTransform();
            Collection siteAndProfilStats = siteAndProfilDAO.findAll( session );
            Collection siteStats = siteDAO.findAll( session );
            result = transform.bo2dto( siteAndProfilStats, siteStats );
            // On récupère les statistiques par application
            List applisStats = getApplicationsStats( pDaysForTerminatedAudit, pDaysForAllAudits );
            // On ajoute au set des stats
            result.setListOfApplicationsStatsDTO( applisStats );
        }
        catch ( JrafDaoException jde )
        {
            FacadeHelper.convertException( jde, StatsFacade.class.getName() + ".getStats" );
        }
        finally
        {
            FacadeHelper.closeSession( session, StatsFacade.class.getName() + ".getStats" );
        }
        return result;
    }

    /**
     * Récupération des statistiques par applications
     * 
     * @param pDaysForTerminatedAudit le nombre de jours max pour lesquels il doit y avoir au moins un audit réussi pour
     *            que l'application soit active
     * @param pDaysForAllAudits le nombre de jours défini pour compter les audits
     * @return la liste des staistiques de toutes les applications non supprimée
     * @throws JrafEnterpriseException si erreur
     */
    private static List getApplicationsStats( int pDaysForTerminatedAudit, int pDaysForAllAudits )
        throws JrafEnterpriseException
    {

        // Initialisation
        ISession session = null;
        ApplicationDAOImpl appliDAO = ApplicationDAOImpl.getInstance();
        List results = new ArrayList();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            // On récupère toutes les applications non supprimées triées par ordre alphabétique
            List allApplis = appliDAO.findNotDeleted( session );
            // On effectue la transformation en statistiques
            results.addAll( ApplicationStatsTransformer.bo2Dto( allApplis, pDaysForTerminatedAudit, pDaysForAllAudits ) );

        }
        catch ( JrafDaoException jde )
        {
            FacadeHelper.convertException( jde, StatsFacade.class.getName() + ".getApplicationsStats" );
        }
        finally
        {
            FacadeHelper.closeSession( session, StatsFacade.class.getName() + ".getApplicationsStats" );
        }

        return results;
    }

}
