package com.airfrance.squalecommon.daolayer.stats;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ServeurBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.stats.SiteAndProfilStatsDICTBO;

/**
 */
public class SiteAndProfilStatsDICTDAOImplTest
    extends SqualeTestCase
{

    /** le bo */
    private SiteAndProfilStatsDICTBO siteAndProfil;

    /** le nom du profil */
    private final static String PROFIL = "JAVA";

    /** nombre de lignes de codes */
    private final static int LINES = 1200;

    /** nombre de projets */
    private static int NB_PROJECTS = 2;

    /** Le site */
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
        siteAndProfil = new SiteAndProfilStatsDICTBO( mServer.getServeurId(), PROFIL, LINES, NB_PROJECTS );
    }

    /** teste la récupération par site et profil */
    public void testFindBySiteAndProfil()
    {
        SiteAndProfilStatsDICTDAOImpl dao = SiteAndProfilStatsDICTDAOImpl.getInstance();
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
            result = dao.findBySiteAndProfil( getSession(), mServer.getServeurId(), PROFIL );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 1 );
            siteAndProfil = (SiteAndProfilStatsDICTBO) result.iterator().next();
            // Teste la bonne récupération des valeurs
            assertEquals( mServer.getServeurId(), siteAndProfil.getServeurBO().getServeurId() );
            assertEquals( siteAndProfil.getProfil(), PROFIL );
            assertEquals( siteAndProfil.getNbOfCodesLines(), LINES );
            assertEquals( siteAndProfil.getNbProjects(), NB_PROJECTS );
            // on vérifie qu'on ne récupère que celui enregistré
            getSession().beginTransaction();
            result = dao.findBySiteAndProfil( getSession(), mServer.getServeurId(), "cpp" );
            getSession().commitTransactionWithoutClose();
            assertTrue( result.size() == 0 );
            // on vérifie qu'on ne récupère que celui enregistré
            getSession().beginTransaction();
            ServeurBO qvg = getComponentFactory().createServer( getSession(), "qvg" );
            result = dao.findBySiteAndProfil( getSession(), qvg.getServeurId(), "java" );
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
