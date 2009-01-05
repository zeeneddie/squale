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
import org.apache.maven.scm.provider.svn.svnexe.SvnExeScmProvider;

/**
 * Connection to a Svn repository
 */
public class RepositorySvn
    extends AbstractRepository
{

    /**
     * Constructor by default
     */
    public RepositorySvn()
    {
        this.setScmManager( new BasicScmManager() );
        this.getScmManager().setScmProvider( "svn", new SvnExeScmProvider() );
    }

    /**
     * Constructor
     * 
     * @param pPath path to check out
     * @param pTemporaryDirectory local directory where check out are performed
     * @param pLocation location (directory or module) to check out
     * @param pLogin user profile to connect to the repository
     * @param pPassword user profile's password to connect to the repository
     */
    public RepositorySvn( String pPath, String pTemporaryDirectory, String pLocation, String pLogin, String pPassword )
    {
        this.setScmManager( new BasicScmManager() );
        this.getScmManager().setScmProvider( "svn", new SvnExeScmProvider() );
        this.setScmTemporaryDirectory( pTemporaryDirectory );
        this.setLocation( pLocation );
        this.setLogin( pLogin );
        this.setPassword( pPassword );

    }
}
