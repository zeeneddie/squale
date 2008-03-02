package com.airfrance.squalecommon.daolayer.stats;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.stats.SiteStatsDICTBO;

/**
 */
public class SiteStatsDAOImplTest
    extends SqualeTestCase
{

    /** le bo */
    private SiteStatsDICTBO siteAndProfil;

    /** */
    private final static int NB_APPLIS = 12;

    /** */
    private final static int NB_APPLIS_EXEC = 5;

    /** */
    private final static int NB_APPLIS_SUCCESSFUL = 4;

    /** */
    private final static int NB_APPLIS_WITHOUT_AUDITS = 3;

    /** */
    private final static int NB_APPLIS_TO_VALIDATE = 2;

    /** */
    private final static int NB_FACTORS_ACCEPTED = 19;

    /** */
    private final static int NB_FACTORS_ACCEPTED_RESERVES = 18;

    /** */
    private final static int NB_FACTORS_REFUSED = 17;

    /** */
    private final static int NB_AUDITS_FAILED = 16;

    /** */
    private final static int NB_AUDITS_SUCCESSFULS = 15;

    /** */
    private final static int NB_AUDITS_PARTIALS = 14;

    /** */
    private final static int NB_AUDITS_NOT_ATTEMPTED = 13;

    /** */
    private final static double ROI = 15.5;

    /** le serveur */
    private ServeurBO mServer;

    /**
     * Méthode mettant en place l'environnement de test
     * 
     * @throws Exception en cas d'échec
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        mServer = getComponentFactory().createServer( getSession() );
        siteAndProfil =
            new SiteStatsDICTBO( mServer.getServeurId(), NB_APPLIS, NB_APPLIS_EXEC, NB_APPLIS_SUCCESSFUL,
                                 NB_APPLIS_WITHOUT_AUDITS, NB_APPLIS_TO_VALIDATE, NB_FACTORS_ACCEPTED,
                                 NB_FACTORS_ACCEPTED_RESERVES, NB_FACTORS_REFUSED, NB_AUDITS_FAILED,
                                 NB_AUDITS_SUCCESSFULS, NB_AUDITS_PARTIALS, NB_AUDITS_NOT_ATTEMPTED, ROI );
    }

    /** teste la récupération par site */
    public void testFindBySite()
    {
        SiteStatsDICTDAOImpl dao = SiteStatsDICTDAOImpl.getInstance();
        try
        {
            // on enregistre le dao qu'on vient de créer
            getSession().beginTransaction();
            dao.create( getSession(), siteAndProfil );
            getSession().commitTransactionWithoutClose();
            // on vérifie qu'on peut le récupérer par la méthode findAll
            getSession().beginTransaction();
            Collection result = dao.findAll( getSession() );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 1 );
            // on vérifie qu'on peut le récupérer par la méthode findBySite
            getSession().beginTransaction();
            result = dao.findBySite( getSession(), mServer.getServeurId() );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 1 );
            SiteStatsDICTBO resultBo = (SiteStatsDICTBO) result.iterator().next();
            // Test sur les valeurs
            assertEquals( mServer.getServeurId(), resultBo.getServeurBO().getServeurId() );
            // Facteurs
            assertEquals( resultBo.getNbOfAcceptedFactors(), NB_FACTORS_ACCEPTED );
            assertEquals( resultBo.getNbOfAcceptedWithReservesFactors(), NB_FACTORS_ACCEPTED_RESERVES );
            assertEquals( resultBo.getNbOfRefusedFactors(), NB_FACTORS_REFUSED );
            // les applications
            assertEquals( resultBo.getNbTotalAppli(), NB_APPLIS );
            assertEquals( resultBo.getNbTotalAppliWithAudit(), NB_APPLIS_EXEC );
            assertEquals( resultBo.getNbTotalAppliWithSuccesfulAudit(), NB_APPLIS_SUCCESSFUL );
            assertEquals( resultBo.getNbTotalAppliWithoutAudit(), NB_APPLIS_WITHOUT_AUDITS );
            assertEquals( resultBo.getNbAppliToValidate(), NB_APPLIS_TO_VALIDATE );
            // les audits
            assertEquals( resultBo.getNbFailedAudits(), NB_AUDITS_FAILED );
            assertEquals( resultBo.getNbSuccessfulAudits(), NB_AUDITS_SUCCESSFULS );
            assertEquals( resultBo.getNbPartialAudits(), NB_AUDITS_PARTIALS );
            assertEquals( resultBo.getNbNotAttemptedAudit(), NB_AUDITS_NOT_ATTEMPTED );
            // ROI
            assertTrue( resultBo.getRoi() == ROI );
            // on vérifie qu'on ne récupère que celui enregistré
            getSession().beginTransaction();
            ServeurBO tls = getComponentFactory().createServer( getSession(), "tls" );
            result = dao.findBySite( getSession(), tls.getServeurId() );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 0 );
            // on vérifie qu'on ne récupère que celui enregistré
            getSession().beginTransaction();
            ServeurBO qvg = getComponentFactory().createServer( getSession(), "qvg" );
            result = dao.findBySite( getSession(), qvg.getServeurId() );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 0 );
        }
        catch ( JrafDaoException e )
        {
            fail( "unexpected Exception" );
            e.printStackTrace();
        }
    }

}
