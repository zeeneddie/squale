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
package com.airfrance.squalix.tools.compiling.java.parser.wsad;

import java.io.File;

import org.w3c.dom.Node;

import com.airfrance.squalix.configurationmanager.ConfigUtility;

/**
 */
public class JWSADDotProjectParser
{

    /** le fichier .project du projet */
    private File dotProject;

    /** une constante servant en cas d'échec */
    private static final String UNKNOWN = "Fichier .project introuvable";

    /**
     * Constructeur
     * 
     * @param dotProjectPath le chemin du .project du projet courant
     */
    public JWSADDotProjectParser( String dotProjectPath )
    {
        // on ajoute éventuellement un "/" ) la fin
        // si il n'est pas déjà présent
        if ( !dotProjectPath.endsWith( "/" ) )
        {
            dotProject = new File( dotProjectPath + "/.project" );
        }
        else
        {
            dotProject = new File( dotProjectPath + ".project" );
        }
    }

    /**
     * parse le fichier pour récupérer le nom du projet
     * 
     * @return le nom du projet
     */
    public String retrieveName()
    {
        String result = UNKNOWN;
        if ( dotProject.exists() )
        {
            // on parse le fichier
            /* on récupère le noeud racine */
            Node root;
            try
            {
                root = ConfigUtility.getRootNode( dotProject.getPath(), "projectDescription" );
                Node name = ConfigUtility.getNodeByTagName( root, "name" );
                // Il ne doit y avoir qu'un seul enfant à ce noeud
                if ( Node.TEXT_NODE == name.getFirstChild().getNodeType() )
                {
                    result = name.getFirstChild().getNodeValue();
                }
            }
            catch ( Exception e )
            {
                e.printStackTrace();
            }

        }
        return result;
    }

}
