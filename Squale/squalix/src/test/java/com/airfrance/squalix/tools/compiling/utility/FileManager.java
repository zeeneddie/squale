/*
 * Créé le 26 sept. 05, par M400832.
 */
package com.airfrance.squalix.tools.compiling.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe "utilitaire" requise pour les tests de compilation.
 * 
 * @author M400832
 * @version 1.0
 */
public class FileManager
{

    /**
     * Constante ".".
     */
    private static final String DOT = ".";

    /**
     * Saut de ligne;
     */
    private static final String NEW_LINE = "\n";

    /**
     * Cette méthode retourne une liste de tous les fichiers se terminant par une extension donnée, au sein d'une
     * arborescence donnée.
     * 
     * @param pDir arborescence à parcourir.
     * @param pExt extension à "matcher".
     * @return la liste des fichiers se terminant par l'extension demandée dans l'arborescence demandée.
     */
    public static ArrayList checkFileNumber( final String pDir, final String pExt )
    {
        /* liste contenant les fichiers correspondant à l'extension cherchée */
        ArrayList list = new ArrayList();

        /* On vérifie que le répertoire existe */
        File dir = new File( pDir );
        if ( dir.isDirectory() )
        {

            /* On récupère la liste des fichiers / dossiers */
            File foundFiles[] = dir.listFiles();

            /* variable utilisée pour stocker l'extension du fichier */
            String fileExt;
            int i = 0;

            /* tant qu'il y a ds fichiers / dossiers */
            while ( i < foundFiles.length )
            {
                /* dossier */
                if ( foundFiles[i].isDirectory() )
                {
                    /* récursion : on ajoute la liste récupérée */
                    list.addAll( FileManager.checkFileNumber( foundFiles[i].getAbsolutePath(), pExt ) );

                    /* fichier */
                }
                else
                {
                    /* nom du fichier */
                    fileExt = foundFiles[i].getName();
                    /* si le fichier contient au moins un "." */
                    if ( fileExt.lastIndexOf( DOT ) > 0 )
                    {
                        /*
                         * on récupère la chaîne entre le dernier "." et la fin du nom du fichier
                         */
                        fileExt = fileExt.substring( fileExt.lastIndexOf( DOT ), fileExt.length() );
                    }

                    /* si c'est un .i */
                    if ( pExt.equals( fileExt ) )
                    {
                        /* on ajoute son chemin à la liste */
                        list.add( foundFiles[i].getAbsolutePath() );
                    }
                }
                /* on progresse dans la liste des fichiers */
                i++;
            }
        }
        return list;
    }

    /**
     * Cette méthode supprime les fichiers dont les chemins sont stockés dans la liste passée en paramètre.
     * 
     * @param pList liste de chemins de fichiers à supprimer.
     */
    public static void removeFiles( final ArrayList pList )
    {
        File iFile;
        Iterator it = pList.iterator();

        /* tant qu'il y a des fichiers dans la liste */
        while ( null != it && it.hasNext() )
        {
            /*
             * on construit un nouveau descripteur et on supprime le fichier.
             */
            iFile = new File( (String) ( it.next() ) );
            iFile.delete();
        }

        iFile = null;
    }

    /**
     * Cette méthode copie le fichier d'un emplacement à un autre.
     * 
     * @param pSource répertoire source.
     * @param pTarget répertoire de destination.
     * @throws Exception IOException.
     */
    public static void copyFile( final File pSource, final File pTarget )
        throws Exception
    {

        StringBuffer sbw = new StringBuffer();

        /* création d'un buffer de lecture du fichier */
        FileReader fr = new FileReader( pSource );
        BufferedReader br = new BufferedReader( fr );

        String myLine = "";
        while ( null != ( myLine = br.readLine() ) )
        {
            sbw.append( myLine );
            sbw.append( NEW_LINE );
        }

        /* filewriter : on écrit le buffer dans le fichier */
        FileWriter fw = new FileWriter( pTarget );
        BufferedWriter bw = new BufferedWriter( fw );
        bw.write( sbw.toString() );

        /* on ferme les flux */
        bw.close();
        br.close();

        /* ménage */
        bw = null;
        br = null;
        fw = null;
        fr = null;
        sbw = null;
    }
}
