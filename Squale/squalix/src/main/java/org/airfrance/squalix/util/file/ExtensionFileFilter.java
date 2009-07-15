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
package com.airfrance.squalix.util.file;

import java.io.File;
import java.io.FileFilter;

/**
 * Filtre de nom utilisé pour filtrer les fichiers selon leur extension.
 */
public class ExtensionFileFilter
    implements FileFilter
{

    /** L'extension à filter */
    private String mExtension = "";

    /**
     * Constructeur
     * 
     * @param pExtension l'extension à utiliser
     */
    public ExtensionFileFilter( String pExtension )
    {
        mExtension = pExtension;
    }

    /**
     * @return l'extension
     */
    public String getExtension()
    {
        return mExtension;
    }

    /**
     * Modifie l'extension
     * 
     * @param pExtension l'extension
     */
    public void setExtension( String pExtension )
    {
        mExtension = pExtension;
    }

    /**
     * @param pFile le nom du fichier
     * @return vrai si le fichier doit être listé, faux sinon.
     */
    public boolean accept( File pFile )
    {
        boolean result = false;
        if ( pFile.isFile() )
        {
            // Si c'est un fichier
            // On récupère le nom du fichier
            String fileName = pFile.getName();
            // On vérifie qu'il s'agit bien d'un fichier jar
            result = fileName.endsWith( mExtension );
        }
        else if ( pFile.isDirectory() )
        {
            result = true;
        }
        return result;
    }

}
