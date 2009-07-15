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
