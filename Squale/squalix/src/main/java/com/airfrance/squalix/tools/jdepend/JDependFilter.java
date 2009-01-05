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
package com.airfrance.squalix.tools.jdepend;

import java.io.File;
import java.io.FileFilter;

/**
 * Filtre de nom utilisé pour filtrer les fichiers à analyser.
 */
public class JDependFilter
    implements FileFilter
{

    /**
     * @param pFile le nom du fichier
     * @return vrai si le fichier est un répertoire, pour construire les packages seul les répertoires nous intéressent
     * @roseuid 42D3CF0C0296
     */
    public boolean accept( File pFile )
    {
        return pFile.isDirectory();
    }
}
