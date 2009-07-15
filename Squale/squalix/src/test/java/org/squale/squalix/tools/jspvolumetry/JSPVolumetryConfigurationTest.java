/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.squale.squalix.tools.jspvolumetry;

import java.util.ArrayList;
import java.util.List;

import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.daolayer.component.ProjectDAOImpl;
import org.squale.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import org.squale.squalix.core.TaskData;

/**
 */
public class JSPVolumetryConfigurationTest
    extends SqualeTestCase
{

    /**
     * Teste la récupération de la configuration.
     * 
     * @throws Exception en cas d'échec
     */
    public void testBuild()
        throws Exception
    {
        ISession session = getSession();
        session.beginTransaction();
        // Teste lorsque le projet est null;
        JSPVolumetryConfiguration config = null;
        TaskData datas = new TaskData();
        datas.putData( TaskData.VIEW_PATH, "./" );
        try
        {
            config = JSPVolumetryConfiguration.build( null, "config/jspvolumetry-config.xml", datas );
            // fail("exception expected");
        }
        catch ( Exception e )
        {
            assertTrue( "exception expected", true );
        }
        session.commitTransactionWithoutClose();
        // assertNull(config);

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
        // Positionne le chemin vers les JSPS
        StringParameterBO jspsSources = new StringParameterBO();
        jspsSources.setValue( "/data/samples/testWeb/WebContent/jsp" );
        ListParameterBO listJsps = new ListParameterBO();
        listJsps.getParameters().add( jspsSources );
        params.getParameters().put( ParametersConstants.JSP, listJsps );
        sp.setParameters( params );
        sp.setName( "Mon projet" );
        ProjectParameterDAOImpl.getInstance().save( session, params );
        ProjectDAOImpl.getInstance().save( session, sp );
        session.commitTransactionWithoutClose();

        config = JSPVolumetryConfiguration.build( sp, "config/jspvolumetry-config.xml", datas );

        // assertions
        assertEquals( "D:\\temp\\resultFile.txt", config.getResultFilePath() );
        assertEquals( "D:\\temp\\script.sh", config.getScriptPath() );
        assertEquals( "D:\\temp", config.getWorkspace().getPath() );
    }
}
