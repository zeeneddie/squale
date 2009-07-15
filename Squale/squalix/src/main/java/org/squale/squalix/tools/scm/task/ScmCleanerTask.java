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
package org.squale.squalix.tools.scm.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.squale.squalix.core.AbstractSourceTerminationTask;
import org.squale.squalix.core.TaskException;
import org.squale.squalix.core.exception.ConfigurationException;
import org.squale.squalix.util.file.FileUtility;

/**
 * Suppress the directory create by the source code recovering task 
 */
public class ScmCleanerTask
    extends AbstractSourceTerminationTask
{

    /**
     * Default constructor
     */
    public ScmCleanerTask()
    {
        mName = "ScmCleanerTask";
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
            // Recovering of the task configuration
            ScmConfig conf = new ScmConfig();
            conf.parse( new FileInputStream( "config/scm-config.xml" ) );
            // Removal of the directory
            File dir = new File( conf.getRootDirectory() );
            FileUtility.deleteRecursively( dir );
        }
        catch ( FileNotFoundException e )
        {
            throw new TaskException (e);
        }
        catch ( ConfigurationException e )
        {
            throw new TaskException (e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean doOptimization()
    {
        boolean doOptimisation = false;
        if ( ScmMessages.getString( "properties.task.optimization" ).equals("true") )
        {
            doOptimisation = true;
        }
        return doOptimisation;
    }

}
