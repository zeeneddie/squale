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
import org.squale.squalix.tools.compiling.configuration.MockCompilingConf;
import org.squale.squalix.util.file.FileUtility;

/**
 * Supprime le répertoire de compilation des sources
 */
public class JavaMockCompilingCleanerTask
    extends AbstractTask
{

    /**
     * Constructeur
     */
    public JavaMockCompilingCleanerTask()
    {
        mName = "JavaMockCompilingCleanerTask";
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
            // On récupère la configuration de la tâche
            MockCompilingConf conf = new MockCompilingConf();
            conf.parse( new FileInputStream( "config/mockcompiling-config.xml" ) );
            // On supprime le répertoire
            File dir = new File( conf.getRootDirectory() );
            FileUtility.deleteRecursively( dir );
        }
        catch ( Exception e )
        {
            throw new TaskException( e );
        }
    }
}
