package com.airfrance.squalecommon.daolayer.result.rulechecking;

import java.util.HashMap;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.result.MeasureDAOImpl;
import com.airfrance.squalecommon.daolayer.rulechecking.ProjectRuleSetDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.RuleBO;

/**
 * Teste les opérations du la table des transgressions
 */
public class RuleCheckingTransgressionDAOImplTest
    extends SqualeTestCase
{

    /** L'audit */
    private AuditBO auditBO;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        ApplicationBO appli = getComponentFactory().createApplication( getSession() );
        QualityGridBO grid = getComponentFactory().createGrid( getSession() );
        ProjectBO projectBO = getComponentFactory().createProject( getSession(), appli, grid );
        auditBO = getComponentFactory().createAudit( getSession(), projectBO );
        PackageBO packageBO = getComponentFactory().createPackage( getSession(), projectBO );
        ProjectRuleSetBO ruleSetBO = new ProjectRuleSetBO();
        ruleSetBO.setName( "test" );
        ruleSetBO.setProject( projectBO );
        ProjectRuleSetDAOImpl ruleSetDao = ProjectRuleSetDAOImpl.getInstance();
        ruleSetDao.create( getSession(), ruleSetBO );
        HashMap rules = new HashMap();
        RuleBO ruleBO1 = new RuleBO();
        ruleBO1.setCode( "Code1" );
        ruleBO1.setCategory( "layering" );
        ruleBO1.setSeverity( "error" );
        ruleBO1.setRuleSet( ruleSetBO );
        rules.put( "Code1", ruleBO1 );
        RuleBO ruleBO2 = new RuleBO();
        ruleBO2.setCode( "Code2" );
        ruleBO2.setCategory( "layering" );
        ruleBO2.setSeverity( "error" );
        ruleBO2.setRuleSet( ruleSetBO );
        rules.put( "Code2", ruleBO2 );
        ruleSetBO.setRules( rules );
        ruleSetDao.save( getSession(), ruleSetBO );
        // Création de mesures
        RuleCheckingTransgressionBO transgression = new RuleCheckingTransgressionBO();
        transgression.setAudit( auditBO );
        transgression.setComponent( projectBO );
        transgression.setRuleSet( ruleSetBO );
        MeasureDAOImpl.getInstance().create( getSession(), transgression );
        getSession().commitTransactionWithoutClose();
    }

    /**
     * Teste la récupération des corrections de type rulechecking
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindNbErrorAndWarning()
        throws JrafDaoException
    {
        RuleCheckingTransgressionDAOImpl dao = RuleCheckingTransgressionDAOImpl.getInstance();
        int nbErrors = dao.findNbErrorAndWarning( getSession(), new Long( auditBO.getId() ) );
        assertEquals( 2, nbErrors );
    }

}
