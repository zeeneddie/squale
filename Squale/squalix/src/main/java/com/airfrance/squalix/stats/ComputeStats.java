package com.airfrance.squalix.stats;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.AuditDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.result.MetricDAOImpl;
import com.airfrance.squalecommon.daolayer.result.QualityResultDAOImpl;
import com.airfrance.squalecommon.daolayer.result.SimpleFormulaDAOImpl;
import com.airfrance.squalecommon.daolayer.stats.SiteAndProfilStatsDICTDAOImpl;
import com.airfrance.squalecommon.daolayer.stats.SiteStatsDICTDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.config.ServeurDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.QualityResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.stats.SiteAndProfilStatsDICTBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.stats.SiteStatsDICTBO;
import com.airfrance.squalecommon.enterpriselayer.facade.roi.RoiFacade;
import com.airfrance.squalecommon.enterpriselayer.facade.rule.FormulaException;
import com.airfrance.squalecommon.enterpriselayer.facade.component.ServeurFacade;
import com.airfrance.squalix.core.CoreMessages;

/**
 */
public class ComputeStats
{

    // TODO voir avec laurent et nicolas comment le moteur d'indicateur SQUALE
    // fournit ce paramètre (SEB)
    /** Le nombre de mois précédents pris en compte */
    private final static int NB_MONTHS = 2;

    /** Le nombre de chiffres pour l'arrondi */
    private final static int NB_DIGITS = 2;

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( ComputeStats.class );

    /**
     * Provider de persistance
     */
    private static final IPersistenceProvider PERSISTANT_PROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Session de travail
     */
    private ISession mSession;

    /** Constructeur */
    public ComputeStats()
    {
        try
        {
            mSession = PERSISTANT_PROVIDER.getSession();
            // on instancie les différents DAO nécessaires
            profileDao = ProjectProfileDAOImpl.getInstance();
            appliDao = ApplicationDAOImpl.getInstance();
            resultDao = QualityResultDAOImpl.getInstance();
            measureDao = MeasureDAOImpl.getInstance();
            formulaDAO = SimpleFormulaDAOImpl.getInstance();
            metricDao = MetricDAOImpl.getInstance();
            siteAndProfilDao = SiteAndProfilStatsDICTDAOImpl.getInstance();
            siteDao = SiteStatsDICTDAOImpl.getInstance();
            auditDao = AuditDAOImpl.getInstance();
            projectDao = ProjectDAOImpl.getInstance();
        }
        catch ( JrafPersistenceException e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
    }

    /** les différents DAO nécessaires au calcul */

    /** pour les stats concernant les profils */
    private ProjectProfileDAOImpl profileDao;

    /** pour le nombre de projets */
    private ProjectDAOImpl projectDao;

    /** pour les applications */
    private ApplicationDAOImpl appliDao;

    /** pour la volumétrie */
    private MetricDAOImpl metricDao;

    /** pour les audits */
    private AuditDAOImpl auditDao;

    /** pour les stats facteurs */
    private QualityResultDAOImpl resultDao;

    /** pour le roi */
    private MeasureDAOImpl measureDao;

    /** pour la formule du roi */
    private SimpleFormulaDAOImpl formulaDAO;

    /** Les DAO pour l'enregistrement en base */

    /** DAO pour enregistrer les SiteStatsDICTBO */
    private SiteAndProfilStatsDICTDAOImpl siteAndProfilDao;

    /** DAO pour enregistrer les StatsDICTBO */
    private SiteStatsDICTDAOImpl siteDao;

    /**
     * Calcule et enregistre en base les différents statistiques nécessaires au calcul des indicateurs SQUALE pour DICT
     * Ces statistiques sont: Le nombre total d'appli (par site) Le nombre total d'appli avec un audit exécuté depuis n
     * mois Le nombre total d'appli avec un audit réussi depuis n mois Le nombre de lignes de codes par site et par
     * profil Le nombre de facteurs acceptés / acceptés avec réserves / refusés Le ROI en ke par site
     */
    public void computeDICTStats()
    {
        try
        {
            // la liste des StatsDICTBO, un par site
            List siteStatsList = new ArrayList( 0 );
            // la liste des SiteStatsDICTBO, il y en a
            // un par site et par profil, soit nbSites*nbProfils
            List siteProfilStatsList = new ArrayList( 0 );
            // on récupère la liste des profils disponibles
            Collection profiles = profileDao.findAll( mSession );
            // Pour chaque site, on crée un SiteStatsDICTBO
            // Pour chaque combinaison site/profil, on crée un SiteStatsDICTBO
            Collection lServeurList = new ArrayList();
            try
            {
                lServeurList = ServeurFacade.listeServeurs();
            }
            catch ( Exception e )
            {
            }
            Iterator lServeurListIt = lServeurList.iterator();
            for ( int i = 0; i < lServeurList.size(); i++ )
            {
                ServeurDTO lServeurDTO = (ServeurDTO) lServeurListIt.next();
                long lSiteId = lServeurDTO.getServeurId();

                int nbAppli = appliDao.countWhereSite( mSession, lSiteId );

                int nbAppliWithAudit = appliDao.countWhereHaveOnlyFailedAudit( mSession, lSiteId );
                int nbAppliWithAuditSuccessful =
                    appliDao.countWhereHaveAuditByStatus( mSession, lSiteId, new Integer( AuditBO.TERMINATED ), null );
                int nbAppliWithoutAudits = appliDao.countWhereHaveNoAudits( mSession, lSiteId );
                int nbApplisToValidate = appliDao.countNotValidate( mSession, lSiteId );
                int nbFactorsAcc =
                    resultDao.countFactorsByAcceptanceLevelAndSite( mSession, QualityResultBO.ACCEPTED, lSiteId );
                int nbFactorsRes =
                    resultDao.countFactorsByAcceptanceLevelAndSite( mSession, QualityResultBO.RESERVED, lSiteId );
                int nbFactorsRef =
                    resultDao.countFactorsByAcceptanceLevelAndSite( mSession, QualityResultBO.REFUSED, lSiteId );
                int nbAuditsFailed = auditDao.countWhereStatusAndSite( mSession, lSiteId, AuditBO.FAILED );
                int nbAuditsSuccessfuls = auditDao.countWhereStatusAndSite( mSession, lSiteId, AuditBO.TERMINATED );
                int nbAuditsPartials = auditDao.countWhereStatusAndSite( mSession, lSiteId, AuditBO.PARTIAL );
                int nbAuditsNotAttempted = auditDao.countWhereStatusAndSite( mSession, lSiteId, AuditBO.NOT_ATTEMPTED );
                double roi = calculateGlobalROI( lSiteId );
                siteStatsList.add( new SiteStatsDICTBO( lSiteId, nbAppli, nbAppliWithAudit, nbAppliWithAuditSuccessful,
                                                        nbAppliWithoutAudits, nbApplisToValidate, nbFactorsAcc,
                                                        nbFactorsRes, nbFactorsRef, nbAuditsFailed,
                                                        nbAuditsSuccessfuls, nbAuditsPartials, nbAuditsNotAttempted,
                                                        roi ) );
                // Les stats concernant les profils et les sites
                Iterator it = profiles.iterator();
                while ( it.hasNext() )
                {
                    String profileName = ( (ProjectProfileBO) it.next() ).getName();
                    int nbLines = metricDao.getVolumetryBySiteAndProfil( mSession, lSiteId, profileName );
                    int nbProjects = projectDao.countBySiteAndProfil( mSession, lSiteId, profileName );
                    siteProfilStatsList.add( new SiteAndProfilStatsDICTBO( lSiteId, profileName, nbLines, nbProjects ) );
                }
            }
            // enregistre les résultats en base
            storeResults( siteStatsList, siteProfilStatsList );
            LOGGER.info( "Statistics 'calculation done" );
        }
        catch ( JrafDaoException e )
        {
            LOGGER.error( CoreMessages.getString( "exception" ), e );
        }
    }

    /**
     * @return la date courante moins le nombre de mois définis
     */
    private Date getBeginDate()
    {
        GregorianCalendar cal = new GregorianCalendar();
        long today = Calendar.getInstance().getTimeInMillis();
        cal.add( GregorianCalendar.MONTH, 0 - NB_MONTHS );
        return cal.getTime();
    }

    /**
     * Enregistre les résultats en base
     * 
     * @param siteStatsList la liste des StatsDICTBO, un par site
     * @param siteProfilStatsList // la liste des SiteStatsDICTBO, un par site et par profil
     * @throws JrafDaoException en cas d'échec de l'enregistrement en base des stats
     */
    private void storeResults( List siteStatsList, List siteProfilStatsList )
        throws JrafDaoException
    {
        // on crée une transaction car il faut d'abord enregister les objets
        // de type SiteStatsDICTBO , committer et ensuite enregister les
        // objets SiteAndProfilStatsDICTBO
        for ( int i = 0; i < siteStatsList.size(); i++ )
        {
            SiteStatsDICTBO site = (SiteStatsDICTBO) siteStatsList.get( i );
            // on commence par supprimer toutes les stats concernant ce site
            // évite les doublons et permet d'éviter le test est-ce que ca existe déjà ou pas
            // pour savoir si il faut faire un update ou un create
            mSession.beginTransaction();
            // suppression
            // commence par supprimer les stats annexes sur le site
            siteAndProfilDao.removeWhereSite( mSession, site.getServeurBO().getServeurId() );
            siteDao.removeWhereSite( mSession, site.getServeurBO().getServeurId() );
            mSession.commitTransactionWithoutClose();
            mSession.beginTransaction();
            // création
            siteDao.create( mSession, site );
            mSession.commitTransactionWithoutClose();
        }

        // maintenant ont peut enregistrer les SiteAndProfilStatsDICTBO
        for ( int i = 0; i < siteProfilStatsList.size(); i++ )
        {
            SiteAndProfilStatsDICTBO siteAndProfil = (SiteAndProfilStatsDICTBO) siteProfilStatsList.get( i );
            // on commence par supprimer toutes les stats concernant ce site et ce profil
            // évite les doublons et permet d'éviter le test est-ce que ca existe déjà ou pas
            // pour savoir si il faut faire un update ou un create
            mSession.beginTransaction();
            // suppression
            siteAndProfilDao.removeWhereSiteAndProfil( mSession, siteAndProfil.getServeurBO().getServeurId(),
                                                       siteAndProfil.getProfil() );
            mSession.commitTransactionWithoutClose();
            mSession.beginTransaction();
            // création
            siteAndProfilDao.create( mSession, siteAndProfil );
            mSession.commitTransactionWithoutClose();
        }

    }

    /**
     * Calcul le roi sur l'ensemble des applications du site
     * 
     * @param pSiteId l'id du site
     * @return le roi global du site
     * @throws JrafDaoException en cas d'échec
     */
    private double calculateGlobalROI( long pSiteId )
        throws JrafDaoException
    {
        int result = 0;
        Collection appliList = appliDao.findWhereSite( mSession, pSiteId );
        // Pour toutes les applis, on récupère tous les différents nombres de corrections
        // sur les audits fait depuis la plage donnée
        Iterator it = appliList.iterator();
        while ( it.hasNext() )
        {
            ApplicationBO appli = (ApplicationBO) it.next();
            Collection rois =
                metricDao.findROIWhereApplicationSinceDate( mSession, new Long( appli.getId() ), getBeginDate() );
            Iterator itRois = rois.iterator();
            while ( itRois.hasNext() )
            {
                IntegerMetricBO roiBo = (IntegerMetricBO) itRois.next();
                result += ( (Integer) roiBo.getValue() ).intValue();
            }
        }
        // pour l'instant le résultat est en nombre de corrections
        // et on doit le calculer en nombre de MHI
        // on enregistre la valeur -1 en cas de problèmes avec le calcul du roi
        double ke = -1;
        try
        {
            ke = computeRoiMHI( result );
        }
        catch ( FormulaException e )
        {
            LOGGER.error( "Problems encountered while calculating squale's stats: roi formula not found or uncorrect" );
        }
        return ke;
    }

    /**
     * @param pNbCorrections le nombre total de corrections sur toutes les applications
     * @return le roi en ke en fonction du nombre de corrections et de la formule courante
     * @throws JrafDaoException en cas d'échec
     * @throws FormulaException si erreur ai niveau de la formule
     */
    private double computeRoiMHI( int pNbCorrections )
        throws JrafDaoException, FormulaException
    {
        double result = 0;
        // On récupère l'unique formule du ROI en base
        SimpleFormulaBO roiFormula = formulaDAO.getRoiFormula( mSession );
        float MhiRoi = RoiFacade.calculateROI( pNbCorrections, roiFormula );
        // arrondi à NB_DIGITS chiffres après la virgule
        int number = (int) Math.pow( 10, NB_DIGITS );
        result = ( (int) ( MhiRoi * number ) ) / number;
        return result;

    }
}