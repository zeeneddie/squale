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
package com.airfrance.squalix.tools.umlquality;

import java.io.File;
import java.io.FileFilter;

/**
 * Filtre de nom des rapports(fichiers resultat) génerer par l'outil umlquality.
 * 
 * @author m400842 (by rose)
 * @version 1.0
 */
public class UMLQualityFileFilter
    implements FileFilter
{

    /** Extensions permises */
    private String[] mExtensions;

    /**
     * Constructeur
     * 
     * @param pExtensions extensions admises
     */
    UMLQualityFileFilter( String[] pExtensions )
    {

        mExtensions = pExtensions;

    }

    /**
     * @param pFile le nom du fichier
     * @return vrai si le fichier doit être listé, faux sinon.
     * @roseuid 42D3CF0C0296
     */
    public boolean accept( File pFile )
    {
        boolean result = false;

        if ( pFile.isFile() )
        {
            // Si c'est un fichier
            // On récupère le nom du fichier
            String filename = pFile.getName();
            // On vérifie si l'extension est acceptée
            for ( int i = 0; i < mExtensions.length && !result; i++ )
            {
                if ( filename.endsWith( mExtensions[i] ) )
                {
                    result = true;
                }
            }
        }
        return result;
    }

}
