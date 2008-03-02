package com.airfrance.squalix.tools.mccabe;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalix.core.TaskData;

/**
 * Teste la création du fichier de configuration d'un projet au sens McCabe. UNIT_PENDING : Long à modifier. Il faudrait
 * rajouter des asserts sur le contenu du fichier pcf crée.
 */
public class McCabePCFFileTest
    extends SqualeTestCase
{

    /** la configuration McCabe */
    private McCabeConfiguration config = null;

    /** le projet */
    private ProjectBO sp = null;

    /** l'objet contenant les paramètres temporaires */
    private TaskData mDatas = new TaskData();

    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        sp = new ProjectBO();
        // Ajout des paramètres au projet
        MapParameterBO parameters = new MapParameterBO();
        ProjectProfileBO profileBO = getComponentFactory().createProjectProfile( getSession() );
        profileBO.setName( "java" );
        ProjectProfileDAOImpl.getInstance().save( getSession(), profileBO );
        StringParameterBO dialect = new StringParameterBO();
        dialect.setValue( ParametersConstants.JAVA1_4 );
        parameters.getParameters().put( ParametersConstants.DIALECT, dialect );
        mDatas.putData( TaskData.VIEW_PATH,
                        new File( "data/Project4McCabeTest/" ).getAbsolutePath().replaceAll( "\\\\", "/" ) + "/" );
        mDatas.putData( TaskData.CLASSPATH, "data/Project4McCabeTest/bin;./data/Project4McCabeTest/lib/log4j-1.2.8.jar" );
        StringParameterBO exclud = new StringParameterBO();
        exclud.setValue( "test" );
        ListParameterBO excludedDirs = new ListParameterBO();
        excludedDirs.getParameters().add( exclud );
        parameters.getParameters().put( ParametersConstants.EXCLUDED_DIRS, excludedDirs );
        ArrayList srcPath = new ArrayList();
        StringParameterBO value = new StringParameterBO();
        value.setValue( "src" );
        srcPath.add( value );
        ListParameterBO srcs = new ListParameterBO();
        srcs.setParameters( srcPath );
        parameters.getParameters().put( ParametersConstants.SOURCES, srcs );
        sp.setParameters( parameters );
        sp.setProfile( profileBO );
        sp.setName( "Mon projet de test McCabe" );
        try
        {
            config = McCabeConfiguration.build( sp, "config/mccabe-config.xml", mDatas );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * Teste la construction du fichier
     */
    public void testBuild()
    {
        boolean test = true;
        try
        {
            long timeBefore = System.currentTimeMillis();
            McCabePCFFile pcff = new McCabePCFFile( config, mDatas );
            pcff.build();
            assertTrue( pcff.getPcfFile().exists() );
            assertTrue( pcff.getPcfFile().lastModified() > timeBefore );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
            test = false;
        }
        assertTrue( test );
    }
}
