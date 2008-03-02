package com.airfrance.squalix.tools.rsm;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalix.core.TaskData;

/**
 */
public class RSMConfigurationTest
    extends SqualeTestCase
{

    /**
     * Teste la récupération de la configuration.
     * 
     * @throws Exception en cas d'échec
     */
    public void testBuildJava()
        throws Exception
    {
        ISession session = getSession();
        session.beginTransaction();
        // Teste lorsque le projet est null;
        RSMConfiguration config = null;
        TaskData datas = new TaskData();
        datas.putData( TaskData.VIEW_PATH, "./" );
        try
        {
            config = RSMConfiguration.build( null, "config/RSM-config.xml", datas );
            fail( "exception expected" );
        }
        catch ( Exception e )
        {
            assertTrue( "exception expected", true );
        }
        session.commitTransactionWithoutClose();
        assertNull( config );

        // Lorsque le projet est correct et que le profil existe
        session.beginTransaction();
        ApplicationBO appli = getComponentFactory().createApplication( getSession() );
        ProjectBO sp = getComponentFactory().createProject( getSession(), appli, null );
        MapParameterBO params = new MapParameterBO();
        ListParameterBO listExcluded = new ListParameterBO();
        List list = new ArrayList();
        StringParameterBO dir1 = new StringParameterBO();
        dir1.setValue( "./data/samples/testWeb/JavaSource/testExcluded" );
        list.add( dir1 );
        listExcluded.setParameters( list );
        params.getParameters().put( ParametersConstants.EXCLUDED_DIRS, listExcluded );
        ListParameterBO listSrcs = new ListParameterBO();
        List list2 = new ArrayList();
        StringParameterBO dirSrc = new StringParameterBO();
        dirSrc.setValue( "./data/samples/testWeb/JavaSource" );
        list2.add( dirSrc );
        listSrcs.setParameters( list2 );
        params.getParameters().put( ParametersConstants.SOURCES, listSrcs );
        StringParameterBO dialectParam = new StringParameterBO();
        dialectParam.setValue( "1.4" );
        params.getParameters().put( ParametersConstants.DIALECT, dialectParam );
        sp.setParameters( params );
        sp.setName( "Mon projet" );
        ProjectProfileBO profile = getComponentFactory().createProjectProfile( session );
        profile.setName( "java" );
        sp.setProfile( profile );
        ProjectParameterDAOImpl.getInstance().save( session, params );
        ProjectProfileDAOImpl.getInstance().save( session, profile );
        ProjectDAOImpl.getInstance().save( session, sp );
        session.commitTransactionWithoutClose();

        config = RSMConfiguration.build( sp, "config/RSM-config.xml", datas );

        // assertions
        assertEquals( "D:\\temp\\rsm\\rsm", config.getExecCommand() );
        assertEquals( "D:\\temp\\rsm", config.getWorkspace().getPath() );
        assertEquals( "D:\\temp\\rsm\\rapport.csv", config.getReportPath() );
        assertEquals( "D:\\temp\\rsm\\rapports_Class_RSM.csv", config.getClassReportPath() );
        assertEquals( "D:\\temp\\rsm\\rapports_Methods_RSM.csv", config.getMethodsReportPath() );
        assertEquals( "D:\\temp\\rsm\\rapports_aux_RSM.csv", config.getAuxFile() );
        assertEquals( "D:\\temp\\rsm\\input.txt", config.getInputFile() );
        assertEquals( 1, config.getExtensions().length );
        assertEquals( 0, config.getHeaders().length );
        assertEquals( 5, config.getParseParameters().length );

        // Lorsque le projet est correct et que le profil n'existe pas
        config = null;
        profile.setName( "unknown" );
        sp.setProfile( profile );
        sp.setName( "Mon projet" );
        try
        {
            config = RSMConfiguration.build( sp, "config/RSM-config.xml", datas );
        }
        catch ( Exception e )
        {
            String error = RSMMessages.getString( "exception.no_profile" );
            assertTrue( e.getMessage().startsWith( error ) );
        }
        assertNull( config );
    }

    /**
     * Teste la récupération de la configuration.
     * 
     * @throws Exception en cas d'échec
     */
    public void testBuildCpp()
        throws Exception
    {
        ISession session = getSession();
        session.beginTransaction();
        // Teste lorsque le projet est null;
        RSMConfiguration config = null;
        TaskData datas = new TaskData();
        datas.putData( TaskData.VIEW_PATH, "./" );
        try
        {
            config = RSMConfiguration.build( null, "config/RSM-config.xml", datas );
            fail( "exception expected" );
        }
        catch ( Exception e )
        {
            assertTrue( "exception expected", true );
        }
        session.commitTransactionWithoutClose();
        assertNull( config );

        // Lorsque le projet est correct et que le profil existe
        session.beginTransaction();
        ApplicationBO appli = getComponentFactory().createApplication( getSession() );
        ProjectBO sp = getComponentFactory().createProject( getSession(), appli, null );
        MapParameterBO params = new MapParameterBO();
        ListParameterBO listExcluded = new ListParameterBO();
        List list = new ArrayList();
        params.getParameters().put( ParametersConstants.EXCLUDED_DIRS, listExcluded );
        ListParameterBO listSrcs = new ListParameterBO();
        List list2 = new ArrayList();
        StringParameterBO dirSrc = new StringParameterBO();
        dirSrc.setValue( "./data/samples/Project4McCabeCppTest" );
        list2.add( dirSrc );
        listSrcs.setParameters( list2 );
        params.getParameters().put( ParametersConstants.SOURCES, listSrcs );
        sp.setParameters( params );
        sp.setName( "Mon projet" );
        ProjectProfileBO profile = getComponentFactory().createProjectProfile( session );
        profile.setName( "cpp" );
        sp.setProfile( profile );
        ProjectParameterDAOImpl.getInstance().save( session, params );
        ProjectProfileDAOImpl.getInstance().save( session, profile );
        ProjectDAOImpl.getInstance().save( session, sp );
        session.commitTransactionWithoutClose();

        config = RSMConfiguration.build( sp, "config/RSM-config.xml", datas );

        // assertions
        assertEquals( "D:\\temp\\rsm\\rsm", config.getExecCommand() );
        assertEquals( "D:\\temp\\rsm", config.getWorkspace().getPath() );
        assertEquals( "D:\\temp\\rsm\\rapport.csv", config.getReportPath() );
        assertEquals( "D:\\temp\\rsm\\input.txt", config.getInputFile() );
        assertEquals( 5, config.getExtensions().length );
        assertEquals( 4, config.getHeaders().length );
        assertEquals( 5, config.getParseParameters().length );

        // Lorsque le projet est correct et que le profil n'existe pas
        config = null;
        profile.setName( "unknown" );
        sp.setProfile( profile );
        sp.setName( "Mon projet" );
        try
        {
            config = RSMConfiguration.build( sp, "config/RSM-config.xml", datas );
        }
        catch ( Exception e )
        {
            String error = RSMMessages.getString( "exception.no_profile" );
            assertTrue( e.getMessage().startsWith( error ) );
        }
        assertNull( config );
    }

}
