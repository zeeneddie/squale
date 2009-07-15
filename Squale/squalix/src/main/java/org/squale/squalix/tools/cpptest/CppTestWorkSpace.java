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
package com.airfrance.squalix.tools.cpptest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalix.util.file.FileUtility;

/**
 * Espace de travail utilisé pour la génération CppTest CppTest utilise des répertoires pendant son exécution, un
 * répertoire servant à générer les données du projet CppTest, l'autre servant à générer les rapports. Ces répertoires
 * sont transitoires.
 */
public class CppTestWorkSpace
{
    /** Extension d'un rapport CppTest */
    static final String REPORT_EXTENSION = ".xml";

    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog( CppTestWorkSpace.class );

    /** Répertoire racine */
    private File mRootDir;

    /** Répertoire des rapports */
    private File mReportDirectory;

    /** Project CppTest */
    private File mProjectFile;

    /**
     * Constructeur
     * 
     * @param pRootDir répertoire racine
     * @throws IOException si erreur de création des répertoires
     */
    public CppTestWorkSpace( File pRootDir )
        throws IOException
    {
        // Création du répertoire correspondant
        mRootDir = pRootDir;
        createDirectory( pRootDir );
        // Création du répertoire pour le projet
        File projectDir = new File( mRootDir, "project" );
        createDirectory( projectDir );
        mProjectFile = new File( projectDir, "project.cpf" );
        // Création du répertoire pour le rapport
        mReportDirectory = new File( mRootDir, "report" );
        createDirectory( mReportDirectory );
    }

    /**
     * Création du répertoire
     * 
     * @param pDirectory répertoire à créer
     * @throws IOException si la création ne peut se faire
     */
    private void createDirectory( File pDirectory )
        throws IOException
    {
        if ( false == pDirectory.mkdirs() )
        {
            String message = CppTestMessages.getString( "error.create_directory", pDirectory.getAbsolutePath() );
            LOGGER.error( message );
            throw new IOException( message );
        }
    }

    /**
     * Nettoyage des répértoires Le répertoire racine est détruit récursivement
     */
    public void cleanup()
    {
        // Effacement du répertoire de rapport
        FileUtility.deleteRecursively( mRootDir );
    }

    /**
     * Recherche des rapports
     * 
     * @return rapports au format XML générés par CppTest
     */
    public Collection getReportFiles()
    {
        // Recherche des rapports
        Collection files = FileUtility.findFilesWithSuffix( getReportDirectory(), REPORT_EXTENSION );
        return files;
    }

    /**
     * @return projet CppTest
     */
    public File getProjectFile()
    {
        return mProjectFile;
    }

    /**
     * @return répertoire de génération de rapport
     */
    public File getReportDirectory()
    {
        return mReportDirectory;
    }

}