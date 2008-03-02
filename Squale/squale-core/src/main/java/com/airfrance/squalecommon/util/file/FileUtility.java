/*
 * Créé le 13 juil. 06
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.util.file;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utilitaire pour les flux.
 */
public class FileUtility
{

    /**
     * Utilitaire de lecture Stream -> byte[]. <br />
     * 
     * @param pIs flux à lire
     * @return byte[] correspondand au Blob
     * @throws IOException en cas de pb d'ecriture deans le Stream
     */
    public static byte[] toByteArrayImpl( InputStream pIs )
        throws IOException
    {
        final int MAXSIZE = 4000;
        byte[] buf = new byte[MAXSIZE];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try
        {
            int dataSize;
            do
            {
                // lecture du flux
                dataSize = pIs.read( buf );
                if ( dataSize != -1 )
                {
                    // ecriture
                    baos.write( buf, 0, dataSize );
                }
            }
            while ( dataSize != -1 );
        }
        finally
        {
            if ( baos != null )
            {
                try
                {
                    // fernmeture du flux
                    baos.close();
                }
                catch ( IOException ex )
                {
                    // ne rien faire dans le finally !
                }
            }
        }
        return baos.toByteArray();
    }

    /**
     * Transforme un tableau binaire en fichier
     * 
     * @param pValue La représentation binaire du fichier de configuration
     * @return file Le fichier de configuration
     * @throws IOException exception si le fichier n'est pas disponible
     */
    public static File byteToFile( byte[] pValue )
        throws IOException
    {

        File file = File.createTempFile( "checkstyle", ".xml" );

        FileOutputStream out = null;
        BufferedOutputStream buffer = null;
        try
        {
            out = new FileOutputStream( file );
            buffer = new BufferedOutputStream( out );
            buffer.write( pValue );
            buffer.flush();
        }
        finally
        {
            if ( buffer != null )
            {
                buffer.close();
            }
            if ( out != null )
            {
                out.close();
            }
        }
        return file;

    }
}
