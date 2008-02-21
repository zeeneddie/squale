package com.airfrance.squalix.tools.jdepend;

import java.io.File;
import java.io.FileFilter;

/**
 * Filtre de nom utilisé pour filtrer les fichiers à analyser.
 */
public class JDependFilter implements FileFilter {

    
    /**
     * @param pFile le nom du fichier
     * @return vrai si le fichier est un répertoire, pour construire les packages
     * seul les répertoires nous intéressent
     * @roseuid 42D3CF0C0296
     */
    public boolean accept(File pFile) {
        return pFile.isDirectory();
    }
}
