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
package com.airfrance.squalix.tools.compiling.java.beans;

/**
 * Projet RSA7
 */
public class JRSAProject
    extends JWSADProject
{

    /**
     * @param myProject le projet WSAD dont on copie les valeur
     */
    public JRSAProject( JWSADProject myProject )
    {
        setClasspath( myProject.getClasspath() );
        setClasspathExt( myProject.getClasspathExt() );
        setCompiled( myProject.isCompiled() );
        setDependsOnProjects( myProject.getDependsOnProjects() );
        setDestPath( myProject.getDestPath() );
        setExcludedDirs( myProject.getExcludedDirs() );
        setJavaVersion( myProject.getJavaVersion() );
        setListener( myProject.getListener() );
        setName( myProject.getName() );
        setPath( myProject.getPath() );
        setRequiredMemory( myProject.getRequiredMemory() );
        setSrcPath( myProject.getSrcPath() );
        setBundleDir( myProject.getBundleDir().getAbsolutePath() );
        setBootClasspath( myProject.getBootClasspath() );
    }

    /** Le nom du projet EAR associé */
    private String mEARProjectName = "";

    /**
     * @return le nom du projet EAR associé
     */
    public String getEARProjectName()
    {
        return mEARProjectName;
    }

    /**
     * @param pEARName le nom du projet EAR associé
     */
    public void setEARProjectName( String pEARName )
    {
        if ( pEARName != null )
        {
            mEARProjectName = pEARName;
        }
    }
}
