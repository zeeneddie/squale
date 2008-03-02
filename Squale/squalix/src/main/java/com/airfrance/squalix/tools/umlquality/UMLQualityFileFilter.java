package com.airfrance.squalix.tools.umlquality;

import java.io.File;
import java.io.FileFilter;

/**
 * Filtre de nom des rapports(fichiers resultat) génerer par l'outil umlquality.
 * 
 * @author m400842 (by rose)
 * @version 1.0
 */
public class UMLQualityFileFilter
    implements FileFilter
{

    /** Extensions permises */
    private String[] mExtensions;

    /**
     * Constructeur
     * 
     * @param pExtensions extensions admises
     */
    UMLQualityFileFilter( String[] pExtensions )
    {

        mExtensions = pExtensions;

    }

    /**
     * @param pFile le nom du fichier
     * @return vrai si le fichier doit être listé, faux sinon.
     * @roseuid 42D3CF0C0296
     */
    public boolean accept( File pFile )
    {
        boolean result = false;

        if ( pFile.isFile() )
        {
            // Si c'est un fichier
            // On récupère le nom du fichier
            String filename = pFile.getName();
            // On vérifie si l'extension est acceptée
            for ( int i = 0; i < mExtensions.length && !result; i++ )
            {
                if ( filename.endsWith( mExtensions[i] ) )
                {
                    result = true;
                }
            }
        }
        return result;
    }

}
