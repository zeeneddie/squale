package com.airfrance.squalecommon.daolayer.result;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.rule.QualityRuleDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;

/**
 * Test du DAO PracticeResultDAOImpl
 */
public class PracticeResultDAOImplTest extends SqualeTestCase {

    /** l'application */
    private ApplicationBO mAppli;
    
    /** le projet */
    private ProjectBO mProject;
    
    /** le package qui sert d'exemple de component */
    private PackageBO mPkg;
    
    /** l'audit */
    private AuditBO mAudit;
    
    /** les regles */
    private PracticeRuleBO mRule;
    
    /** le résultat de la pratique1 */
    private PracticeResultBO mPracticeResult1;
    
    /** le résultat de la pratique2 */
    private PracticeResultBO mPracticeResult2;
    
    /**
     *  (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() throws Exception {
        super.setUp();
        ISession session = getSession();
        session.beginTransaction();
        mAppli = getComponentFactory().createApplication(session);
        mProject = getComponentFactory().createProject(session, mAppli, null);
        mPkg = getComponentFactory().createPackage(session, mProject);
        mAudit = getComponentFactory().createAudit(session, mProject);
        mRule = new PracticeRuleBO();
        QualityRuleDAOImpl.getInstance().create(session, mRule);
        // pratique 1
        mPracticeResult1 = new PracticeResultBO();
        mPracticeResult1.setRule(mRule);
        mPracticeResult1.setProject(mProject);
        mPracticeResult1.setAudit(mAudit);
        mPracticeResult1.incrementRepartition(1.0f);
        mPracticeResult1.setMeanMark(2.0f);
        QualityResultDAOImpl.getInstance().create(session, mPracticeResult1);
        // pratique 2
        mPracticeResult2 = new PracticeResultBO();
        mPracticeResult2.setRule(mRule);
        mPracticeResult2.setProject(mProject);
        mPracticeResult2.setAudit(mAudit);
        final float excellentMark = 3.0f;
        mPracticeResult2.incrementRepartition(excellentMark);
        mPracticeResult2.setMeanMark(1.0f);
        QualityResultDAOImpl.getInstance().create(session, mPracticeResult2);
        session.commitTransactionWithoutClose();
    }
    
    /**
     * Teste la recherche des pratiques pour le plan d'action
     * @throws JrafDaoException si erreur
     */
    public void testFindPracticesForActionPlan() throws JrafDaoException {
        PracticeResultDAOImpl dao = PracticeResultDAOImpl.getInstance();
        Collection results = dao.findPracticesForActionPlan(getSession(), ""+mProject.getId(), ""+mAudit.getId());
        assertEquals(2, results.size());
    }

}
