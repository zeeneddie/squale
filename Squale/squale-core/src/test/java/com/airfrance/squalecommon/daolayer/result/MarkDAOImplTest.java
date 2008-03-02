package com.airfrance.squalecommon.daolayer.result;

import java.util.Collection;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.rule.QualityRuleDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AbstractComponentBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.MarkBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.PracticeResultBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.PracticeRuleBO;
import com.airfrance.squalecommon.util.mapping.Mapping;

/**
 * @author M400843
 */
public class MarkDAOImplTest
    extends SqualeTestCase
{

    /** Nombre de filtres possibles */
    private static final int NB_FILTER = 3;

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

    /** la note */
    private MarkBO mMark;

    /** le résultat de la pratique */
    private PracticeResultBO mPracticeResult;

    /** le DAOImpl pour manipuler des notes */
    private MarkDAOImpl mMarkDAO;

    /**
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp()
        throws Exception
    {
        super.setUp();
        ISession session = getSession();
        session.beginTransaction();
        mMarkDAO = MarkDAOImpl.getInstance();
        mAppli = getComponentFactory().createApplication( session );
        mProject = getComponentFactory().createProject( session, mAppli, null );
        mPkg = getComponentFactory().createPackage( session, mProject );
        mAudit = getComponentFactory().createAudit( session, mProject );
        mRule = new PracticeRuleBO();
        QualityRuleDAOImpl.getInstance().create( session, mRule );
        mPracticeResult = new PracticeResultBO();
        mPracticeResult.setRule( mRule );
        mPracticeResult.setProject( mProject );
        mPracticeResult.setAudit( mAudit );
        QualityResultDAOImpl.getInstance().create( session, mPracticeResult );
        mMark = new MarkBO();
        mMark.setComponent( mPkg );
        mMark.setPractice( mPracticeResult );
        mMark.setValue( 2 );
        session.commitTransactionWithoutClose();
    }

    /**
     * @see MarkDAOImpl#removeWhereComponent(ISession, AbstractComponentBO)
     */
    public void testRemoveWhereComponent()
        throws JrafDaoException
    {
        ISession session = getSession();
        session.beginTransaction();
        mMarkDAO.create( session, mMark );
        List col = mMarkDAO.findWhere( session, "where markbo.component.id = " + mPkg.getId() );
        assertEquals( 1, col.size() );
        mMarkDAO.removeWhereComponent( session, mPkg );
        col = mMarkDAO.findWhere( session, "where markbo.component.id = " + mPkg.getId() );
        session.commitTransactionWithoutClose();
        assertTrue( col.isEmpty() );
    }

    /**
     * @see MarkDAOImpl#removeWhereProject(ISession pSession, ProjectBO pProject)
     */
    public void testRemoveWhereProject()
        throws JrafDaoException
    {
        ISession session = getSession();
        session.beginTransaction();

        mMark.getPractice().setProject( mProject );
        mMark.getPractice().setAudit( mAudit );
        mMark.getPractice().setRule( mRule );
        mMarkDAO.create( session, mMark );
        List col = mMarkDAO.findWhere( session, "where markbo.practice.project.id = " + mProject.getId() );
        assertEquals( 1, col.size() );
        mMarkDAO.removeWhereProject( session, mProject );
        col = mMarkDAO.findWhere( session, "where markbo.practice.project.id = " + mProject.getId() );
        session.commitTransactionWithoutClose();
        assertTrue( col.isEmpty() );

    }

    /**
     * Teste la remontée du nombre de corrections pour le calcul du ROI lorsque correction=composant qui passe de 0 à >
     * 0.
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindProgressions()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        // On crée un nouvel audit
        AuditBO audit = getComponentFactory().createAudit( getSession(), mProject );
        // une nouvelle pratique
        PracticeResultBO practice = new PracticeResultBO();
        practice.setRule( mRule );
        practice.setProject( mProject );
        practice.setAudit( audit );
        QualityResultDAOImpl.getInstance().create( getSession(), practice );
        MarkBO mark = new MarkBO();
        mark.setComponent( mPkg );
        mark.setPractice( practice );
        // On met la note à zéro pour qu'il y ait une progression entre les audits
        mark.setValue( 0 );
        mMarkDAO.create( getSession(), mark );
        mMarkDAO.create( getSession(), mMark );

        // On va récupérer la progression
        int nbProgressions =
            mMarkDAO.findCorrectionsWithProgessions( getSession(), new Long( mAudit.getId() ), new Long( audit.getId() ) );
        getSession().commitTransactionWithoutClose();

        assertEquals( 1, nbProgressions );
    }

    /**
     * Teste la remontée du nombre de corrections pour le calcul du ROI lorsque correction=composant qui était à 0 et
     * qui n'existe plus.
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindSuppressions()
        throws JrafDaoException
    {
        ISession session = getSession();
        session.beginTransaction();
        // La note était à zéro pour le package mPackage
        mMark.setValue( 0 );
        mMark.getPractice().setRule( mRule );
        mMarkDAO.create( session, mMark );
        // On crée un nouvel audit sans composant
        AuditBO audit = getComponentFactory().createAudit( getSession(), mProject );

        // On va récupérer la suppression
        int nbProgressions =
            mMarkDAO.findCorrectionsWithSuppressions( getSession(), new Long( audit.getId() ),
                                                      new Long( mAudit.getId() ) );
        getSession().commitTransactionWithoutClose();
        assertEquals( 1, nbProgressions );

    }

    /**
     * Teste la méthode findWhere
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindWhere()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        // On crée une nouvelle note
        MarkBO mark = new MarkBO();
        // La note est sur un nouveau package
        PackageBO newPkg = getComponentFactory().createPackage( getSession(), mProject );
        mark.setComponent( newPkg );
        mark.setPractice( mPracticeResult );
        // On met une note qui doit être accepté avec réserve
        final float between = 1.25698f;
        mark.setValue( between );
        mMarkDAO.create( getSession(), mark );
        mMarkDAO.create( getSession(), mMark );
        Long auditId = new Long( mAudit.getId() );
        Long projectId = new Long( mProject.getId() );
        Long treId = new Long( mPracticeResult.getRule().getId() );
        Integer pIndex = new Integer( PracticeResultBO.NEARLY_ACCEPTED_MIN );
        Integer max = new Integer( 2 );
        /* On récupère les composants acceptés avec réserve */
        Collection components = mMarkDAO.findWhere( getSession(), auditId, projectId, treId, pIndex, max );
        // Il doit y en avoir un (newPkg)
        assertEquals( 1, components.size() );
        MarkBO nearlyAccepted = (MarkBO) components.iterator().next();
        assertEquals( newPkg.getId(), nearlyAccepted.getComponent().getId() );
        /* On récupère les composants acceptés */
        pIndex = new Integer( PracticeResultBO.ACCEPTED_MIN );
        Collection accepetedComponents = mMarkDAO.findWhere( getSession(), auditId, projectId, treId, pIndex, max );
        // Il doit y en avoir un (mPkg)
        assertEquals( 1, accepetedComponents.size() );
        MarkBO accepted = (MarkBO) accepetedComponents.iterator().next();
        assertEquals( mPkg.getId(), accepted.getComponent().getId() );

    }

    /**
     * Teste la récupération des nouveaux composants entre deux audits
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindNewComponents()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        // On crée un nouvel audit
        AuditBO audit = getComponentFactory().createAudit( getSession(), mProject );
        // une nouvelle pratique
        PracticeResultBO practice = new PracticeResultBO();
        practice.setRule( mRule );
        practice.setProject( mProject );
        practice.setAudit( audit );
        QualityResultDAOImpl.getInstance().create( getSession(), practice );
        MarkBO mark = new MarkBO();
        // La note est sur un nouveau package
        PackageBO newPkg = getComponentFactory().createPackage( getSession(), mProject );
        mark.setComponent( newPkg );
        mark.setPractice( practice );
        // On crée les notes
        mMarkDAO.create( getSession(), mark );
        mMarkDAO.create( getSession(), mMark );
        getSession().commitTransactionWithoutClose();
        // On va récupérer les nouveaux packages entre mAudit et audit
        Collection t1 = mMarkDAO.findWhere( getSession(), new Long( mPkg.getId() ), new Long( mAudit.getId() ) );
        Collection t2 = mMarkDAO.findWhere( getSession(), new Long( newPkg.getId() ), new Long( audit.getId() ) );
        String type = Mapping.getComponentClass( ComponentType.PACKAGE ).getName();
        Collection pkgs =
            mMarkDAO.findDeletedComponents( getSession(), new Long( audit.getId() ), new Long( mAudit.getId() ),
                                            new Long( mProject.getId() ), new Object[NB_FILTER], 1 );
        // Il doit y avoir un seul nouveau package (newPkg)
        assertEquals( 1, pkgs.size() );
    }

    /**
     * Teste la récupération des notes différentes entre deux audits pour un même composant et une même pratique
     * 
     * @throws JrafDaoException si erreur
     */
    public void testFindChangedComponent()
        throws JrafDaoException
    {
        getSession().beginTransaction();
        // On crée un nouvel audit
        AuditBO audit = getComponentFactory().createAudit( getSession(), mProject );
        // une nouvelle pratique
        PracticeResultBO practice = new PracticeResultBO();
        practice.setRule( mRule );
        practice.setProject( mProject );
        practice.setAudit( audit );
        QualityResultDAOImpl.getInstance().create( getSession(), practice );
        MarkBO mark = new MarkBO();
        mark.setComponent( mPkg );
        mark.setPractice( practice );
        // On met la note à zéro pour qu'il y ait une modification entre les audits
        mark.setValue( 0 );
        mMarkDAO.create( getSession(), mark );
        mMarkDAO.create( getSession(), mMark );

        // On va récupérer la modification
        String type = Mapping.getComponentClass( ComponentType.PACKAGE ).getName();
        Collection pkgs =
            mMarkDAO.findChangedComponentWhere( getSession(), new Long( mAudit.getId() ), new Long( audit.getId() ),
                                                new Long( mProject.getId() ), new Object[NB_FILTER], 1 );
        getSession().commitTransactionWithoutClose();

        assertEquals( 1, pkgs.size() );
    }
}
