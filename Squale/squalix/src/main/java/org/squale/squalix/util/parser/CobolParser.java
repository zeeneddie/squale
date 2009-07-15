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
package org.squale.squalix.util.parser;

import java.util.List;

import org.squale.squalecommon.enterpriselayer.businessobject.component.ClassBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.PackageBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * Parse les noms de type Cobol et les remplace par les objets correspondants.
 */

public class CobolParser
{

    /**
     * Nom du package racine comportant l'ensemble des programmes.
     */
    private final String ROOT_PACKAGE = "source";

    /**
     * Nom donné par McCabe au module principal d'un programme.
     */
    private final String MAINLINE_TOKEN = "mainline";

    /**
     * Séparateur de programme et module.
     */
    private final String PRG_MODULE_SEPARATOR = "`";

    /**
     * Séparateur de programme et de son module principal.
     */
    private final String PRG_MAIN_MODULE_SEPARATOR = "_";

    /**
     * Séparateur de module et de paragraphe.
     */
    private final String MODULE_PARAGRAPH_SEPARATOR = ":";

    /**
     * Nom du module créé dans le cas d'un programme sans module.
     */
    private final String MAINPROGRAM_MODULE = "mainprogram";

    /** Projet sur lequel est réalisé l'analyse */
    private ProjectBO mProject;

    /**
     * Retourne le nom du projet sur lequel est réalisé l'analyse.
     * 
     * @return le nom du projet
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * Constructeur.
     * 
     * @param pProject le projet sur lequel est réalisé l'analyse
     */
    public CobolParser( final ProjectBO pProject )
    {
        mProject = pProject;
    }

    /**
     * Retourne un objet métier module portant le nom indiqué.
     * 
     * @param pModuleName le nom du module
     * @param pFilename le nom du fichier définissant le module
     * @param pPrgName le nom du programme définissant le module
     * @return l'objet métier module
     */
    public MethodBO getModule( final String pModuleName, final String pFilename, final ClassBO pPrgName )
    {
        MethodBO lMethodBO = new MethodBO( pModuleName );
        lMethodBO.setLongFileName( pFilename );
        lMethodBO.setParent( pPrgName );
        lMethodBO.setProject( mProject );
        return lMethodBO;
    }

    /**
     * Retourne un objet métier programme portant le nom indiqué.
     * 
     * @param pPrgName le nom du programme
     * @param pFilename le nom du fichier définissant le programme
     * @return l'objet métier programme
     */
    public ClassBO getProgram( final String pPrgName, final String pFilename )
    {
        // création d'un package racine comportant l'ensemble des programmes
        PackageBO lPackageBO = new PackageBO( ROOT_PACKAGE );
        lPackageBO.setParent( mProject );
        ClassBO lClassBO = new ClassBO( pPrgName );
        lClassBO.setParent( lPackageBO );
        lClassBO.setFileName( pFilename );
        return lClassBO;
    }

    /**
     * Retourne les noms du programme et du module Cobol à partir du nom complet du module tel qu'indiqué dans le
     * rapport McCabe. <br/> Le nom du programme et du module doivent être séparés par un simple guillemet.
     * 
     * @param pFullModuleName le nom complet du module
     * @param pPrgAndModuleNames les noms du programme et du module Cobol retournés par cette méthode si ceux-ci sont
     *            correctement séparés par un simple guillement. Cette liste doit être initiallement vide, et elle est
     *            retournée vide si le nom du module n'est pas bien formé.
     */
    public void getPrgAndModuleNamesForModule( final String pFullModuleName, final List<String> pPrgAndModuleNames )
    {
        // initialisation de la liste
        pPrgAndModuleNames.clear();

        // cas d'un module non principal
        int lIndexOfSeparator = pFullModuleName.indexOf( PRG_MODULE_SEPARATOR );
        if ( lIndexOfSeparator == -1 )
        {
            // cas du module principal du programme
            if ( pFullModuleName.contains( PRG_MAIN_MODULE_SEPARATOR + MAINLINE_TOKEN ) )
            {
                lIndexOfSeparator = pFullModuleName.indexOf( PRG_MAIN_MODULE_SEPARATOR );
            }
        }
        // décomposition du nom complet du module
        // cas d'un programme et d'un module
        if ( lIndexOfSeparator != -1 )
        {
            // ajout du nom du programme et du module dans la liste
            pPrgAndModuleNames.add( pFullModuleName.substring( 0, lIndexOfSeparator ) );
            pPrgAndModuleNames.add( pFullModuleName.substring( lIndexOfSeparator + 1 ) );
        }
        // cas d'un programme sans nom de module
        else
        {
            pPrgAndModuleNames.add( pFullModuleName );
            pPrgAndModuleNames.add( MAINPROGRAM_MODULE );
        }
    }

}
