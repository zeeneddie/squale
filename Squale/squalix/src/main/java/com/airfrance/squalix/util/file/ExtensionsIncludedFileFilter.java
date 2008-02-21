package com.airfrance.squalix.util.file;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

/**
 * Filtre de recherche basé sur une extension et des répertoires exclus
 */
public class ExtensionsIncludedFileFilter implements FileFilter {
    /** Extensions autorisées */
    private String[] mExtensions;

    /** Fichiers autorisés sous la forme de chaîne */
    private List mIncludedDirs;

    /** Le répertoire de recherche */
    private File mBaseDir;

    /**
     * @param pExtensions extension
     * @param pIncludedDirs répertoires exclus
     */
    public ExtensionsIncludedFileFilter(String[] pExtensions, List pIncludedDirs) {
        mExtensions = new String[pExtensions.length];
        for (int i = 0; i < pExtensions.length; i++) {
            mExtensions[i] = pExtensions[i].toLowerCase();
        }
        mIncludedDirs = pIncludedDirs;
    }

    /** 
     * {@inheritDoc}
     * @see java.io.FileFilter#accept(java.io.File)
     */
    public boolean accept(File pFile) {
        boolean result = true;
        // Si il s'agit d'un fichier, on teste son extension
        if (pFile.isFile()) {
            result = checkExtension(pFile);
        }
        if (result) {
            String regexp = mBaseDir.getAbsolutePath().replace('/', File.separatorChar).replace('\\', File.separatorChar);
            regexp = regexp.replaceAll("\\\\", "\\\\\\\\");
            regexp += "[\\\\/]*";
            String filePath = pFile.getAbsolutePath().replaceFirst(regexp, "");
            // On vérifie que le nom de chemin n'est pas exclu
            result = mIncludedDirs.contains(filePath);
        }
        return result;
    }

    /**
     * Test de l'extension d'un fichier
     * @param pFile fichier à tester
     * @return true si le fichier satisfait aux critères d'extension
     */
    protected boolean checkExtension(File pFile) {
        boolean result = false;
        String fileName = pFile.getName();
        int index = fileName.lastIndexOf('.');
        if (index != -1) {
            String extension = fileName.substring(index).toLowerCase();
            // Parcours de chaque extension
            for (int i = 0;(i < mExtensions.length) && (!result); i++) {
                result = extension.equals(mExtensions[i]);
            }
        }
        return result;
    }

    /**
     * @param pDir le répertoire de recherche
     */
    public void setBaseDir(File pDir) {
        mBaseDir = pDir;
    }

}
