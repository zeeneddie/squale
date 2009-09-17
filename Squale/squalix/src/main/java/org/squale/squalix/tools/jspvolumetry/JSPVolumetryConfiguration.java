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
package org.squale.squalix.tools.jspvolumetry;

import java.io.File;

import org.w3c.dom.Node;

import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalix.configurationmanager.ConfigUtility;
import org.squale.squalix.core.TaskData;

/**
 */
public class JSPVolumetryConfiguration
{

    /**
     * Projet à analyser
     */
    private ProjectBO mProject = null;

    /**
     * Espace de travail autorisé (voire réservé) à RSM : il permet d'accueillir tous les fichiers générés par RSM
     */
    private File mWorkspace = null;

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
        return config;
    }

}
