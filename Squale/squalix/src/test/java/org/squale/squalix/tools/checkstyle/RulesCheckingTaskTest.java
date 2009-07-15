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
package com.airfrance.squalix.tools.checkstyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.result.rulechecking.RuleCheckingTransgressionDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.rulechecking.CheckstyleDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.IntegerMetricBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.rulechecking.RuleCheckingTransgressionBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.facade.checkstyle.CheckstyleFacade;
import com.airfrance.squalix.core.AbstractTask;
import com.airfrance.squalix.core.TaskData;
import com.airfrance.squalix.tools.ruleschecking.RulesCheckingTask;

/**
 * Test pour la tâche checkstyle.
 */
public class RulesCheckingTaskTest
    extends SqualeTestCase
{

    /** Projet */
    private ProjectBO sp = null;

    /** Audit */
    private AuditBO audit = null;

    /** Application */
    private ApplicationBO appl = null;

    /** Paramètres temporaires */
    private TaskData mDatas = new TaskData();

    /**
     * Chemin absolu vers la vue.
     */
    private static final String VIEW_PATH = ".";

    /**
     * Chemin relatif vers les sources depuis la racine de la vue.
     */
    private static final String SRC_DIR = "data/samples/testBatch/testbatch/";

    /**
     * @see TestCase#setUp()
     */
    protected void setUp()
        throws Exception
    {
        super.setUp();
        getSession().beginTransaction();
        InputStream stream = new FileInputStream( new File( "data/checkstyle/checkstyle_parsing.xml" ) );
        StringBuffer errors = new StringBuffer();
        // parsing du contenu du fichier
        CheckstyleDTO versionRes = CheckstyleFacade.importCheckstyleConfFile( stream, errors );
        appl = getComponentFactory().createApplication( getSession() );

        QualityGridBO grid = getComponentFactory().createGrid( getSession() );

        sp = getComponentFactory().createProject( getSession(), appl, grid );

        mDatas.putData( TaskData.VIEW_PATH, VIEW_PATH );

        MapParameterBO projectMap = new MapParameterBO();
        StringParameterBO ck = new StringParameterBO();
        ck.setValue( versionRes.getName() );
        projectMap.getParameters().put( ParametersConstants.CHECKSTYLE_RULESET_NAME, ck );
        StringParameterBO dialect = new StringParameterBO();
        dialect.setValue( ParametersConstants.JAVA1_4 );
        projectMap.getParameters().put( ParametersConstants.DIALECT, dialect );
        List paths = new ArrayList( 0 );
        StringParameterBO src = new StringParameterBO();
        src.setValue( SRC_DIR );
        paths.add( src );
        ListParameterBO srcs = new ListParameterBO();
        srcs.setParameters( paths );
        projectMap.getParameters().put( ParametersConstants.SOURCES, srcs );
        ProjectParameterDAOImpl.getInstance().create( getSession(), projectMap );
        sp.setParameters( projectMap );
        ProjectDAOImpl.getInstance().save( getSession(), sp );
        audit = getComponentFactory().createAudit( getSession(), sp );
        // On fait le commit pour permettre l'accès aux données dans une autre session
        getSession().commitTransactionWithoutClose();

    }

    /**
     * Vérifie la correcte exécution de la tâche Checkstyle.
     */
    public void testRun()
    {

        RulesCheckingTask task = new RulesCheckingTask();

        task.setAuditId( new Long( audit.getId() ) );
        task.setProjectId( new Long( sp.getId() ) );
        task.setApplicationId( new Long( appl.getId() ) );
        task.setStatus( AbstractTask.NOT_ATTEMPTED );
        task.setData( mDatas );
        task.run();
        assertEquals( "tâche terminée correctement", AbstractTask.TERMINATED, task.getStatus() );
        try
        {
            Collection coll =
                RuleCheckingTransgressionDAOImpl.getInstance().load( getSession(), new Long( sp.getId() ),
                                                                     new Long( audit.getId() ) );
            assertEquals( 1, coll.size() );

            Iterator it = coll.iterator();
            Map metrics = ( (RuleCheckingTransgressionBO) it.next() ).getMetrics();
            final int errorsNb = 21;
            assertEquals( "il y a 21 transgressions", errorsNb, metrics.size() );
            IntegerMetricBO metric = (IntegerMetricBO) metrics.get( "COM01" );
            assertNotNull( "on récupère la règle de code COM01", metric );
            final int errorsCOM01 = 11;
            assertEquals( "il y a 11 transgressions de la règle COM01", errorsCOM01,
                          ( (Integer) metric.getValue() ).intValue() );

        }
        catch ( JrafDaoException e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
