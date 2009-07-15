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
package org.squale.squaleweb.taskconfig.qc;

import java.util.ArrayList;

import org.squale.squaleweb.applicationlayer.formbean.component.parameters.externalTask.qc.TestManagerQCForm;
import org.squale.squaleweb.taskconfig.AbstractConfigTask;
import org.squale.squaleweb.taskconfig.FieldInfoConfig;

/**
 * Configuration in order to build TestManagerQCTask's JASP page.
 */
public class TestManagerQCTaskConfig
    extends AbstractConfigTask
{

    /**
     * {@inheritdoc}
     * 
     * @see org.squale.squaleweb.taskconfig.AbstractConfigTask#init()
     */
    public void init()
    {
        ArrayList collec = new ArrayList();

        FieldInfoConfig QCLogin =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.login", "QCLogin", "true", "60", "TEXT" );
        collec.add( QCLogin );
        FieldInfoConfig QCPassword =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.password", "QCPassword", "true", "60",
                                 "PASSWORD" );
        collec.add( QCPassword );
        FieldInfoConfig QCUrl =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.url", "QCUrl", "true", "60", "TEXT" );
        collec.add( QCUrl );
        FieldInfoConfig QCDatabaseName =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.databaseName", "dataBaseName", "true", "60",
                                 "TEXT" );
        collec.add( QCDatabaseName );
        FieldInfoConfig QCAdminDatabaseName =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.adminDatabaseName", "adminDataBaseName",
                                 "true", "60", "TEXT" );
        collec.add( QCAdminDatabaseName );
        FieldInfoConfig QCPrefixDatabaseName =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.prefixDatabaseName", "prefixDataBaseName",
                                 "true", "60", "TEXT" );
        collec.add( QCPrefixDatabaseName );
        FieldInfoConfig QCClosedDefect =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.closedDefects", "QCClosedDefectStatus", "true",
                                 "90", "TEXT" );
        collec.add( QCClosedDefect );
        FieldInfoConfig QCCoveredReq =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.coveredReq", "QCCoveredReqStatus", "true",
                                 "90", "TEXT" );
        collec.add( QCCoveredReq );
        FieldInfoConfig QCOkRun =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.okRun", "QCOkRunStatus", "true", "90", "TEXT" );
        collec.add( QCOkRun );
        FieldInfoConfig QCOpenedReq =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.openedReq", "QCOpenedReqStatus", "true", "90",
                                 "TEXT" );
        collec.add( QCOpenedReq );
        FieldInfoConfig QCPassedStep =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.passedStep", "QCPassedStepStatus", "true",
                                 "90", "TEXT" );
        collec.add( QCPassedStep );
        FieldInfoConfig QCToValidateReq =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.toValidateReq", "QCToValidateReqStatus",
                                 "true", "90", "TEXT" );
        collec.add( QCToValidateReq );
        FieldInfoConfig QCIncludedReq =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.includedReq", "QCIncludedReq", "false", "90",
                                 "TEXT" );
        collec.add( QCIncludedReq );
        FieldInfoConfig QCIncludedTL =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.includedTL", "QCIncludedTestLab", "false",
                                 "90", "TEXT" );
        collec.add( QCIncludedTL );
        FieldInfoConfig QCIncludedTP =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.includedTP", "QCIncludedTestPlan", "false",
                                 "90", "TEXT" );
        collec.add( QCIncludedTP );
        FieldInfoConfig QCPrevDate =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.prevDate", "previousRelease", "false", "60",
                                 "DATE" );
        collec.add( QCPrevDate );
        FieldInfoConfig QCNextDate =
            new FieldInfoConfig( "project_creation.QC.testmanager.Field.nextDate", "nextRelease", "false", "60", "DATE" );
        collec.add( QCNextDate );

        setInfoConfigTask( collec );
    }

    /**
     * Constructor
     */
    public TestManagerQCTaskConfig()
    {
        setTaskName( new TestManagerQCForm().getTaskName() );
        setHelpKeyTask( "project_creation.QC.testmanager.help" );
        init();
    }

}
