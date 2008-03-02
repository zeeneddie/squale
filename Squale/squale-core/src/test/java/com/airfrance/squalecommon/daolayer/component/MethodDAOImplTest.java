package com.airfrance.squalecommon.daolayer.component;

import java.util.Collection;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 */
public class MethodDAOImplTest
    extends SqualeTestCase
{

    /**
     * Test de la méthode de récupération d'une méthode en fonction de son nom du nom de fichier et de l'id de l'audit
     */
    public void testFindMethodByName()
    {
        try
        {
            String method1Name = "Test";
            String method1FileName = "Test\file.java";
            getSession().beginTransaction();
            ApplicationBO application = getComponentFactory().createTestApplication();
            ProjectBO project = (ProjectBO) application.getChildren().iterator().next();
            PackageBO packageBo = getComponentFactory().createPackage( getSession(), project );
            ClassBO classBo = getComponentFactory().createClass( getSession(), packageBo );
            MethodBO method = getComponentFactory().createMethod( getSession(), classBo );
            method.setName( method1Name );
            method.setLongFileName( method1FileName );
            AuditBO audit = getComponentFactory().createAuditResult( application );
            getSession().commitTransactionWithoutClose();
            getSession().beginTransaction();
            MethodDAOImpl dao = MethodDAOImpl.getInstance();
            Collection methods = dao.findMethodeByName( getSession(), method1Name, method1FileName, audit.getId() );
            getSession().commitTransactionWithoutClose();
            assertTrue( methods.size() == 1 );
            MethodBO methodResult = (MethodBO) methods.iterator().next();
            assertEquals( method.getName(), method1Name );
            assertEquals( method.getLongFileName(), method1FileName );
        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }
}
