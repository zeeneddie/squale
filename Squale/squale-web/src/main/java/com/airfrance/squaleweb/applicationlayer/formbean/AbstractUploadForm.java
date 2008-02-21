package com.airfrance.squaleweb.applicationlayer.formbean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.struts.upload.FormFile;

/**
 * Formulaire comportant un fichier d'upload
 */
public abstract class AbstractUploadForm extends RootForm implements UploadFileForm {
    /** Fichier d'upload */
    private FormFile mFile;
    /**
     * @return fichier
     */
    public FormFile getFile() {
        return mFile;
    }

    /**
     * @param pFile fichier
     */
    public void setFile(FormFile pFile) {
        mFile = pFile;
    }
    
    /**
     * Returns an input stream for this file. The caller must close the
     * stream when it is no longer needed.
     *
     * @exception FileNotFoundException if the uploaded file is not found.
     * @exception IOException           if an error occurred while reading the
     *                                  file.
     * @return stream
     * @see com.airfrance.squaleweb.applicationlayer.formbean.UploadFileForm#getInputStream()
     */
    public InputStream getInputStream() throws FileNotFoundException, IOException {
        return mFile.getInputStream();
    }

}
