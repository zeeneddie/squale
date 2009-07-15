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
package com.airfrance.squalix.util.buildpath;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;

/**
 */
public class BuildProjectPath
{

    /** le séparateur UNIX */
    private final static String UNIX_SEPARATOR = "/";

    /**
     * Construit la liste des projectpath, un projectpath étant la concaténation du viewPath avec le srcPath du projet
     * concerné Méthode appelée par chaque tache qui a besoin de construire la liste des project path .
     * 
     * @param viewPath le chemin de la vue
     * @param srcList la liste des sources, c'est une liste de StringParameterBO
     * @return la liste des chemins
     */
    public static List buildProjectPath( String viewPath, List srcList )
    {
        List result = new ArrayList( 0 );
        String completViewPath = viewPath;
        String currentResult;
        /* si le buffer ne se termine pas par un "/" */
        if ( !viewPath.endsWith( UNIX_SEPARATOR ) )
        {
            /* on l'ajoute en fin de buffer */
            completViewPath += UNIX_SEPARATOR;
        }
        for ( int i = 0; i < srcList.size(); i++ )
        {
            // on récupère la valeur du paramètre courant de l'ensemble des src_path
            // et on l'ajoute au buffer
            currentResult = completViewPath + ( ( (StringParameterBO) srcList.get( i ) ).getValue() );
            if ( !currentResult.endsWith( UNIX_SEPARATOR ) )
            {
                /* on l'ajoute en fin de buffer */
                currentResult += UNIX_SEPARATOR;
            }
            result.add( currentResult );
        }
        // renvoie la liste
        return result;
    }

}
