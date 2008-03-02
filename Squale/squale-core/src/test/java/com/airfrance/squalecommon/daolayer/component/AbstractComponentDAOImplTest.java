package com.airfrance.squalecommon.daolayer.component;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * @author M400843
 */
public class AbstractComponentDAOImplTest
    extends SqualeTestCase
{

    /**
     * Test de findProjectChildren
     */
    public void testFindProjectChildren()
    {
        try
        {
            ApplicationBO application = getComponentFactory().createTestApplication();
            ProjectBO project = (ProjectBO) application.getChildren().iterator().next();
            AuditBO audit = getComponentFactory().createAuditResult( application );
            AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
            Collection coll = dao.findProjectChildren( getSession(), project, audit, PackageBO.class );
            assertEquals( 1, coll.size() );
            coll = dao.findProjectChildren( getSession(), project, audit, ClassBO.class );
            assertEquals( 1, coll.size() );
            coll = dao.findProjectChildren( getSession(), project, audit, MethodBO.class );
            assertEquals( 1, coll.size() );
            // Cas particulier de l'application, le projet est renvoyé
            coll = dao.findProjectChildren( getSession(), project, audit, ApplicationBO.class );
            assertEquals( 1, coll.size() );
            assertEquals( project, coll.iterator().next() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de remove
     */
    public void testRemove()
    {
        try
        {
            ApplicationBO application = getComponentFactory().createTestApplication();
            AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
            getSession().beginTransaction();
            assertTrue( dao.count( getSession() ).intValue() > 0 );
            getSession().commitTransactionWithoutClose();
            getSession().beginTransaction();
            dao.remove( getSession(), application );
            getSession().commitTransactionWithoutClose();
            getSession().beginTransaction();
            assertEquals( 0, dao.count( getSession() ).intValue() );
            getSession().commitTransactionWithoutClose();
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de findProjectChildren
     */
    public void testFindChild()
    {
        try
        {
            getSession().beginTransaction();
            ApplicationBO application = getComponentFactory().createApplication( getSession() );
            QualityGridBO grid = getComponentFactory().createGrid( getSession() );
            ProjectBO project = getComponentFactory().createProject( getSession(), application, grid );
            PackageBO packageBO1 = getComponentFactory().createPackage( getSession(), project );
            PackageBO packageBO2 = getComponentFactory().createPackage( getSession(), "package2", packageBO1 );
            PackageBO packageBO3 = getComponentFactory().createPackage( getSession(), "package2", packageBO2 );
            getSession().commitTransactionWithoutClose();
            // On recherche le package package2 qui a comme parent packageBO1:
            AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
            PackageBO packageResult = (PackageBO) dao.findChild( getSession(), packageBO1, "package2" );
            assertTrue( packageResult.getId() > -1 );
            assertEquals( "package2", packageResult.getName() );
            // On recherche le package package2 qui a comme parent packageBO2:
            PackageBO packageResult2 = (PackageBO) dao.findChild( getSession(), packageBO2, "package2" );
            assertTrue( packageResult.getId() > -1 );
            assertEquals( "package2", packageResult.getName() );
            assertEquals( packageBO2.getId(), packageResult2.getParent().getId() );
            // On recherche un composant qui n'existe pas
            ClassBO classBO = (ClassBO) dao.findChild( getSession(), packageBO1, "class" );
            assertNull( classBO );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test de findProjectChildren
     */
    public void testFindChildrenWhere()
    {
        try
        {
            ApplicationBO application = getComponentFactory().createTestApplication();
            ProjectBO project = (ProjectBO) application.getChildren().iterator().next();
            AuditBO audit = getComponentFactory().createAuditResult( application );
            AbstractComponentDAOImpl dao = AbstractComponentDAOImpl.getInstance();
            Collection coll =
                dao.findChildrenWhere( getSession(), new Long( project.getId() ), new Long( audit.getId() ),
                                       ComponentType.PACKAGE, "" );
            assertEquals( 1, coll.size() );
            Collection col2 =
                dao.findChildrenWhere( getSession(), new Long( project.getId() ), new Long( audit.getId() ),
                                       ComponentType.PACKAGE, "test" );
            assertEquals( 0, col2.size() );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
