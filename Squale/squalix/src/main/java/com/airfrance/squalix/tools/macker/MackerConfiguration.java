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
package com.airfrance.squalix.tools.macker;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking.ProjectRuleSetBO;
import com.airfrance.squalix.util.file.ExtensionFileFilter;
import com.airfrance.squalix.util.file.FileUtility;

/**
 * Configuration de la tâche Macker
 */
public class MackerConfiguration
{

    /** L'extension d'un fichier java */
    public static final String COMPILED_FILE_EXTENSION = ".class";

    /** Le chemin racine du projet */
    private String mRoot;

    /** Le chemin racine des .class des jsps */
    private String mJspRoot;

    /** Les fichiers à anlyser */
    private HashSet mFilesToAnalyze;

    /** Les sources du projet */
    private List mSources;

    /** Les jsps du projet */
    private List mJsps;

    /** La liste des noms absolus des fichiers inclus */
    private List mIncludedFiles;

    /** Le fichier de configuration Macker */
    private File mConfigFile;

    /** La liste des règles Macker */
    private ProjectRuleSetBO mRuleSet;

    /**
     * @return le fichier de configuration
     */
    public File getConfigFile()
    {
        return mConfigFile;
    }

    /**
     * @return la liste des fichiers inclus
     */
    public List getIncludedFiles()
    {
        return mIncludedFiles;
    }

    /**
     * @return la liste des fichier à analyser
     */
    public HashSet getFilesToAnalyze()
    {
        return mFilesToAnalyze;
    }

    /**
     * @return le chemin racine
     */
    public String getRoot()
    {
        return mRoot;
    }

    /**
     * @return la liste des règles
     */
    public ProjectRuleSetBO getRuleSet()
    {
        return mRuleSet;
    }

    /**
     * @return les sources du projet sous forme de <code>StringParameterBO</code>
     */
    public List getSources()
    {
        return mSources;
    }

    /**
     * @param pConfigFile le fichier de configuration
     */
    public void setConfigFile( File pConfigFile )
    {
        mConfigFile = pConfigFile;
    }

    /**
     * @param pIncludedFiles la liste des fichiers inclus
     */
    public void setIncludedFiles( List pIncludedFiles )
    {
        mIncludedFiles = pIncludedFiles;
    }

    /**
     * @param pFilesToAnalyze les fichiers à analyser
     */
    public void setFilesToAnalyze( HashSet pFilesToAnalyze )
    {
        mFilesToAnalyze = pFilesToAnalyze;
    }

    /**
     * @param pRoot le chemin racine
     */
    public void setRoot( String pRoot )
    {
        mRoot = pRoot;
    }

    /**
     * @param pRuleSet la liste des règles
     */
    public void setRuleSet( ProjectRuleSetBO pRuleSet )
    {
        mRuleSet = pRuleSet;
    }

    /**
     * @param pSources les sources sous forme de <code>StringParameterBO</code>
     */
    public void setSources( List pSources )
    {
        mSources = pSources;
    }

    /**
     * Modifie la liste des fichiers à analyser dans un répertoire donné et en appliquant un filtre précis.
     * 
     * @param classesDir le répertoire des fichiers à récupérer
     */
    public void setFilesToAnalyze( String classesDir )
    {
        setFilesToAnalyze( (HashSet) getFilesToAnalyze( classesDir ) );
    }

    /**
     * Retourne la liste des fichiers à analyser dans un répertoire donné en appliquant un filtre précis.
     * 
     * @param classesDir le répertoire des fichiers à récupérer
     * @return la liste des fichiers filtrés
     */
    public Set getFilesToAnalyze( String classesDir )
    {
        // Le filtre à utiliser pour récupérer les fichiers
        ExtensionFileFilter filter = new ExtensionFileFilter( COMPILED_FILE_EXTENSION );
        Set filesToAnalyse = new HashSet();
        filesToAnalyse = FileUtility.createRecursiveListOfFiles( new File( classesDir ), filter, filesToAnalyse );
        return filesToAnalyse;
    }

    /**
     * @return la liste des jsps
     */
    public List getJsps()
    {
        return mJsps;
    }

    /**
     * @param pJsps la liste des jsps
     */
    public void setJsps( List pJsps )
    {
        mJsps = pJsps;
    }

    /**
     * @return le répertoire contenant les .class des jsps
     */
    public String getJspRoot()
    {
        return mJspRoot;
    }

    /**
     * @param pJspRoot le répertoire contenant les .class des jsps
     */
    public void setJspRoot( String pJspRoot )
    {
        mJspRoot = pJspRoot;
    }

}
