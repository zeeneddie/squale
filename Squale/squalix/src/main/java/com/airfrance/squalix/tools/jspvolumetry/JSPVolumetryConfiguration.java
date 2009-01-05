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
package com.airfrance.squalix.tools.jspvolumetry;

import java.io.File;

import org.w3c.dom.Node;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalix.configurationmanager.ConfigUtility;
import com.airfrance.squalix.core.TaskData;

/**
 */
public class JSPVolumetryConfiguration
{

    /** le chemin du fichier où sont écrits les résultats */
    private File mResultFile;

    /**
     * Projet à analyser
     */
    private ProjectBO mProject = null;

    /**
     * Espace de travail autorisé (voire réservé) à RSM : il permet d'accueillir tous les fichiers générés par RSM
     */
    private File mWorkspace = null;

    /** l'emplacement su script */
    private String mScriptPath;

    /**
     * @return le workspace
     */
    public File getWorkspace()
    {
        return mWorkspace;
    }

    /**
     * @param pProject le projet en cours d'analyse
     * @param pFile le fichier de config
     * @param pData les paramètres
     * @return la configuration
     * @throws Exception en cas d'échec
     */
    public static JSPVolumetryConfiguration build( ProjectBO pProject, String pFile, TaskData pData )
        throws Exception
    {
        JSPVolumetryConfiguration config = new JSPVolumetryConfiguration();
        config.mProject = pProject;
        // Recuperation de la configuration
        Node root = ConfigUtility.getRootNode( pFile, JSPVolumetryMessages.getString( "configuration.root" ) );
        // Workspace
        config.mWorkspace =
            new File(
                      ConfigUtility.filterStringWithSystemProps( ConfigUtility.getNodeByTagName(
                                                                                                 root,
                                                                                                 JSPVolumetryMessages.getString( "configuration.workspace" ) ).getFirstChild().getNodeValue().trim() ) );
        // Emplacement du fichier de résultat
        config.mResultFile =
            new File(
                      ConfigUtility.filterStringWithSystemProps( ConfigUtility.getNodeByTagName(
                                                                                                 root,
                                                                                                 JSPVolumetryMessages.getString( "configuration.resultfile" ) ).getFirstChild().getNodeValue().trim() ) );
        // Emplacement du fichier de résultat
        config.mScriptPath =
            ConfigUtility.filterStringWithSystemProps( ConfigUtility.getNodeByTagName(
                                                                                       root,
                                                                                       JSPVolumetryMessages.getString( "configuration.script" ) ).getFirstChild().getNodeValue().trim() );
        return config;
    }

    /**
     * @return le chemin du fichier ou sont écrits les résultats
     */
    public File getResultFile()
    {
        return mResultFile;
    }

    /**
     * @return le chemin du fichier ou sont écrits les résultats
     */
    public String getResultFilePath()
    {
        return mResultFile.getPath();
    }

    /**
     * @return l'emplacement du script
     */
    public String getScriptPath()
    {
        return mScriptPath;
    }

    /**
     * @param pScritpPath le nouveau chemin
     */
    public void setScriptPath( String pScritpPath )
    {
        mScriptPath = pScritpPath;
    }

}
