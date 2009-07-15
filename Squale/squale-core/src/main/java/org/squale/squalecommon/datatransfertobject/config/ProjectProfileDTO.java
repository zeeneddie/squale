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
package org.squale.squalecommon.datatransfertobject.config;

import java.util.List;

/**
 * Profile Squalix
 */
public class ProjectProfileDTO
    extends TasksUserDTO
{

    /** true si l'export IDE est possible */
    private boolean mExportIDE;

    /** Les grilles disponibles pour ce profil */
    private List mGrids;
    
    /** Le langage du profil du projet */
    private String mLanguage;

    /**
     * @return true si l'export IDE est possible
     */
    public boolean getExportIDE()
    {
        return mExportIDE;
    }

    /**
     * @param pExportIDE true si l'export IDE est possible
     */
    public void setExportIDE( boolean pExportIDE )
    {
        mExportIDE = pExportIDE;
    }

    /**
     * @return les grilles
     */
    public List getGrids()
    {
        return mGrids;
    }

    /**
     * @param pGrids les grilles
     */
    public void setGrids( List pGrids )
    {
        mGrids = pGrids;
    }
    
    /**
     * @return le langage du profil du projet
     */
    public String getLanguage()
    {
        return mLanguage;
    }

    /**
     * @param pLanguage le langage du profil du projet
     */
    public void setLanguage( String pLanguage )
    {
        mLanguage = pLanguage;
    }

}
