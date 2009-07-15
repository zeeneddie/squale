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
/*
 * Créé le 29 juil. 05, par m400832.
 */
package org.squale.squalix.tools.compiling.java.beans;

import org.apache.tools.ant.BuildListener;

/**
 * @author m400832
 * @version 1.0
 */
public class JProject
{

    /**
     * Chemin vers le projet.
     */
    private String mPath;

    /**
     * L'écouteur
     */
    private BuildListener listener;

    /**
     * Setter.
     * 
     * @param pPath Chemin vers le projet.
     * @since 1.0
     */
    public void setPath( String pPath )
    {
        mPath = pPath;
    }

    /**
     * Getter.
     * 
     * @return Chemin vers le projet.
     */
    public String getPath()
    {
        return mPath;
    }

    /**
     * @return le listener
     */
    public BuildListener getListener()
    {
        return listener;
    }

    /**
     * @param pListener le nouveau listener
     */
    public void setListener( BuildListener pListener )
    {
        listener = pListener;
    }

}
