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
package com.airfrance.squaleweb.taskconfig.qc;

import java.util.ArrayList;
import java.util.Collection;

import com.airfrance.squaleweb.taskconfig.AbstractConfigTask;
import com.airfrance.squaleweb.taskconfig.FieldInfoConfig;

/**
 * 
 */
public class ExtBugTrackingQCTaskConfig
    extends AbstractConfigTask
{

    /*
     * Definition des Parameters_Constants
     */

    public static final String TASK_NAME = "ExtBugTrackingQC";

    public static final String BUGTRACKINGQC_LOGIN = "btqcLogin";

    public static final String BUGTRACKINGQC_PWD = "btqcPwd";

    public static final String BUGTRACKINGQC_URL = "btqcURL";

    public static final String BUGTRACKINGQC_TRACE = "btqcTrace";

    /**
     * Definition de la liste des infos de configuration nécessaire
     */
    public void init()
    {
        Collection collec = new ArrayList();

        FieldInfoConfig QCLogin =
            new FieldInfoConfig( "project_creation.QC.BugTracking.Field.Login", "QCLogin", "true", "60", "TEXT" );
        collec.add( QCLogin );
        FieldInfoConfig QCPassword =
            new FieldInfoConfig( "project_creation.QC.BugTracking.Field.Pwd", "QCPassword", "true", "60", "PASSWORD" );
        collec.add( QCPassword );
        FieldInfoConfig QCUrl =
            new FieldInfoConfig( "project_creation.QC.BugTracking.Field.Url", "QCUrl", "true", "60", "TEXT" );
        collec.add( QCUrl );
        FieldInfoConfig QCTrace =
            new FieldInfoConfig( "project_creation.QC.Bugtracking.Field.Trace", "QCTrace", "false", "60", "TEXT" );
        collec.add( QCTrace );

        setInfoConfigTask( collec );
    }

    /**
     * Création de l'ensemble des infos nécessaire pour définir la page JSP de configuration
     */
    public ExtBugTrackingQCTaskConfig()
    {
        setTaskName( "ExtBugTrackingQCTask" );
        setHelpKeyTask( "project_creation.QCTask.BugTracking_conf.details" );
        init();
    }
}
