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
                      ConfigUtility.getNodeByTagName( root, JSPVolumetryMessages.getString( "configuration.workspace" ) ).getFirstChild().getNodeValue().trim() );
        // Emplacement du fichier de résultat
        config.mResultFile =
            new File(
                      ConfigUtility.getNodeByTagName( root, JSPVolumetryMessages.getString( "configuration.resultfile" ) ).getFirstChild().getNodeValue().trim() );
        // Emplacement du fichier de résultat
        config.mScriptPath =
            ConfigUtility.getNodeByTagName( root, JSPVolumetryMessages.getString( "configuration.script" ) ).getFirstChild().getNodeValue().trim();
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
