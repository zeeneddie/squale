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
package com.airfrance.squalix.tools.scm.task;

import org.apache.maven.scm.manager.BasicScmManager;
import org.apache.maven.scm.provider.local.LocalScmProvider;

/**
 * Connection to a local repository
 */
public class RepositoryLocal
    extends AbstractRepository
{

    /** Constructor by default */
    public RepositoryLocal()
    {
        this.setScmManager( new BasicScmManager() );
        this.getScmManager().setScmProvider( "local", new LocalScmProvider() );
    }

    /**
     * Constructor
     * 
     * @param pPath path to data to check out
     * @param pTemporaryDirectory local directory where check out are performed
     * @param pLocation location (module or directory) to check out
     * @param pLogin user profile to connect to the repository
     * @param pPassword user profile's password to access the repository
     */
    public RepositoryLocal( String pPath, String pTemporaryDirectory, String pLocation, String pLogin, String pPassword )
    {
        this.setScmManager( new BasicScmManager() );
        this.getScmManager().setScmProvider( "local", new LocalScmProvider() );
        this.setScmTemporaryDirectory( pTemporaryDirectory );
        this.setLocation( pLocation );
        this.setLogin( pLogin );
        this.setPassword( pPassword );

    }
}
