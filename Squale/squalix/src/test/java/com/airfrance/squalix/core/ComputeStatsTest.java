package com.airfrance.squalix.core;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.stats.SiteAndProfilStatsDICTDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalix.stats.ComputeStats;

/**
 */
// UNIT_KO : Un requête hibernate utilise la comparaison oracle des dates.
// Il faudrait mettre la méthode privée 'calculateGlobalROI' en protected pour pouvoir
// créer une classe stub la surchargeant permettant ainsi de tester la méthode 'computeDICTStats'
// sans prendre en compte le calcul du ROI.
public class ComputeStatsTest
    extends SqualeTestCase
{

    /** le nom de la grille */
    private final static String GRID_NAME = "java";

    /**
     * Méthode créant l'environnement
     * 
     * @throws Exception en cas d'échecs
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        ApplicationBO appli1 = getComponentFactory().createApplicationWithSite( getSession(), "qvi" );
        QualityGridBO grid1 = getComponentFactory().createGrid( getSession() );
        grid1.setName( GRID_NAME );
        ProjectProfileBO profile = getComponentFactory().createProjectProfileWithName( getSession(), grid1.getName() );
        ProjectBO project = getComponentFactory().createProject( getSession(), appli1, grid1 );
        project.setProfile( profile );
        AuditBO audit1 = getComponentFactory().createAuditResult( appli1 );
        audit1.setStatus( AuditBO.TERMINATED );
        AuditBO audit2 = getComponentFactory().createAuditResult( appli1 );
        audit2.setStatus( AuditBO.FAILED );
        AuditBO audit3 = getComponentFactory().createAuditResult( appli1 );
        audit3.setStatus( AuditBO.NOT_ATTEMPTED );
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Teste le bon calcul des stats et la bonne récupération avec l'environnement défini
     * 
     * @throws JrafDaoException en cas d'échecs
     */
    public void testComputeStats()
        throws JrafDaoException
    {
        ComputeStats computer = new ComputeStats();
        getSession().beginTransaction();
        computer.computeDICTStats();
        getSession().commitTransactionWithoutClose();
        getSession().beginTransaction();
        Collection siteAndProfilsColl = SiteAndProfilStatsDICTDAOImpl.getInstance().findAll( getSession() );
        // 1 seul profil et un seul site
        assertEquals( 1, siteAndProfilsColl.size() );

    }
}
