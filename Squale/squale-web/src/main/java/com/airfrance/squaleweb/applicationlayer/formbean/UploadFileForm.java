package com.airfrance.squaleweb.applicationlayer.formbean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Interface d'upload pour le chargement des fichiers
 */
public interface UploadFileForm
{

    /**
     * Returns an input stream for this file. The caller must close the stream when it is no longer needed.
     * 
     * @exception FileNotFoundException if the uploaded file is not found.
     * @exception IOException if an error occurred while reading the file.
     * @return stream
     */
    public InputStream getInputStream()
        throws FileNotFoundException, IOException;
}
