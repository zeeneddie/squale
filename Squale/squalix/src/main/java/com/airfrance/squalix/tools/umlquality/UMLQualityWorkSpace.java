

package com.airfrance.squalix.tools.umlquality;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.squalix.util.file.FileUtility;

/**
 * Espace de travail utilisé pour la génération des rapport UMLQuality
 * UMLQuality utilise un répertoire pendant son exécution, ce répertoire
 * sert  à générer les rapports.
 * Le répertoire est transitoire.
 */
public class UMLQualityWorkSpace {
   
    /**
     * Logger
     */
    private static final Log LOGGER = LogFactory.getLog(UMLQualityWorkSpace.class);
   
    /** Répertoire des rapports */
    private File mReportDirectory;
    
    
    /**
     * Constructeur
     * @param pRootDir répertoire racine
     * @throws IOException si erreur de création des répertoires
     */
    public UMLQualityWorkSpace(File pRootDir) throws IOException {
        // Création du répertoire correspondant
        mReportDirectory = pRootDir;
        createDirectory(mReportDirectory);
          
    }
   
    /**
     * Création du répertoire
     * @param pDirectory répertoire à créer
     * @throws IOException si la création ne peut se faire
     */
    private void createDirectory(File pDirectory) throws IOException {
        if (false==pDirectory.mkdirs()) {
            String message = UMLQualityMessages.getString("error.create_directory", pDirectory.getAbsolutePath());
            LOGGER.error(message);
            throw new IOException(message);
        }
    }
    
    /**
     * Nettoyage des répértoires
     * Le répertoire racine est détruit récursivement
     */
    public void cleanup() {
        // Effacement du répertoire de rapport
        FileUtility.deleteRecursively(mReportDirectory);
    }

    /**
     * Recherche des rapports
     * @return rapports au format XML générés par CppTest
     */
    public Collection getReportFiles() {    
        // Recherche des rapports
        
        String []suffixsReportName=(UMLQualityMessages.getString("reports.end.file.name")).split(",");
        
        UMLQualityFileFilter filter =new UMLQualityFileFilter(suffixsReportName);
        HashSet fileList = new HashSet();
        Collection files = FileUtility.createRecursiveListOfFiles(getReportDirectory(),filter,fileList);
        return files;
    }
    
    /**
     * @return répertoire de génération de rapport
     */
    public File getReportDirectory() {
        return mReportDirectory;
    }


}
