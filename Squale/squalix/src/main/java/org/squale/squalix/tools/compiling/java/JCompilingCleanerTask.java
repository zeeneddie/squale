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
package org.squale.squalix.tools.compiling.java;

import java.io.File;
import java.io.FileInputStream;

import org.squale.squalix.core.AbstractTask;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.tools.compiling.java.configuration.EclipseCompilingConfiguration;
import org.squale.squalix.tools.compiling.java.configuration.JCompilingConfiguration;
import org.squale.squalix.util.file.FileUtility;

/**
 * Permet de supprimer les répertoires crées lors de la tâche de compilation
 */
public class JCompilingCleanerTask
    extends AbstractTask
{

    /**
     * Constructeur par défaut.
     */
    public JCompilingCleanerTask()
    {
        mName = "JCompilingCleanerTask";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.core.AbstractTask#execute()
     */
    public void execute()
        throws TaskException
    {
        try
        {
            JCompilingConfiguration jCompilingConf = new JCompilingConfiguration();
            // On supprime le répertoire des plugins eclipse
            FileUtility.deleteRecursively( new File( jCompilingConf.getEclipseBundleDir() ) );
            // On supprime le répertoire des librairies exportées
            FileUtility.deleteRecursively( jCompilingConf.getExportedLibsDir() );
            // On supprime le workspace
            EclipseCompilingConfiguration conf = new EclipseCompilingConfiguration();
            conf.parse( new FileInputStream( new File( "config/eclipsecompiling-config.xml" ) ) );
            FileUtility.deleteRecursively( new File( conf.getWorkspace() ) );
            FileUtility.deleteRecursively( conf.getEclipseHome() );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

}
