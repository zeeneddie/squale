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
package org.squale.squaleweb.applicationlayer.formbean.sharedrepository;

import org.squale.squaleweb.applicationlayer.formbean.AbstractUploadForm;

/**
 * Form for the sharedRepository import page
 */
public class SharedRepositoryImportForm
    extends AbstractUploadForm
{
    /**
     * UID
     */
    private static final long serialVersionUID = -5517447058713400425L;

    /**
     * The current reference version
     */
    private Integer currentReferenceVersion;

    /**
     * Constructor
     */
    public SharedRepositoryImportForm()
    {
        super();
    }

    /**
     * Getter method for the attribute currentReferenceVersion
     * 
     * @return The current reference version insert in Squale
     */
    public Integer getCurrentReferenceVersion()
    {
        return currentReferenceVersion;
    }

    /**
     * Setter method for the attribute currentReferenceVersion
     * 
     * @param pCurrentReferenceVersion The new reference version insert in Squale
     */
    public void setCurrentReferenceVersion( Integer pCurrentReferenceVersion )
    {
        currentReferenceVersion = pCurrentReferenceVersion;
    }

}
