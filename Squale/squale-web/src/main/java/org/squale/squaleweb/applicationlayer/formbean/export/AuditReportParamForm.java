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
package org.squale.squaleweb.applicationlayer.formbean.export;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import org.squale.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean for the audit report parameters
 */
public class AuditReportParamForm
    extends RootForm
{

    /** id for serialize class */
    private static final long serialVersionUID = 1L;

    /** ppt's model to use */
    private FormFile model;

    /** Presentation to modify */
    private FormFile presentation;

    /** XML mapping indicating modifications */
    private FormFile mapping;

    /**
     * List of SetOfErrorListForm List all errors launched during current audit for each project
     */
    private List currentAuditErrors;

    /**
     * List of SetOfErrorListForm List all errors launched during previous audit for each project
     */
    private List previousAuditErrors;

    /**
     * Getter for model file
     * 
     * @return model
     */
    public FormFile getModel()
    {
        if ( model == null || model.getFileName().length() == 0 || model.getFileName().equals( presentation.getFileName() ))
        {
            this.model = this.presentation;
        }
        return model;
    }

    /**
     * Set model file
     * 
     * @param pModel model to set
     */
    public void setModel( FormFile pModel )
    {
        this.model = pModel;
    }

    /**
     * Getter for presentation
     * 
     * @return presentation
     */
    public FormFile getPresentation()
    {
        return presentation;
    }

    /**
     * Set presentation file
     * 
     * @param pPresentation file to set
     */
    public void setPresentation( FormFile pPresentation )
    {
        this.presentation = pPresentation;
    }

    /**
     * Getter for mapping file
     * 
     * @return mapping file
     */
    public FormFile getMapping()
    {
        return mapping;
    }

    /**
     * Set mapping file
     * 
     * @param pMapping xml file
     */
    public void setMapping( FormFile pMapping )
    {
        this.mapping = pMapping;
    }

    /**
     * Returns an input stream for file <code>pFile</code>. The caller must close the stream when it is no longer
     * needed.
     * 
     * @param pFile concerned file
     * @exception FileNotFoundException if the uploaded file is not found.
     * @exception IOException if an error occurred while reading the file.
     * @return stream
     * @see org.squale.squaleweb.applicationlayer.formbean.UploadFileForm#getInputStream()
     */
    public InputStream getInputStream( FormFile pFile )
        throws FileNotFoundException, IOException
    {
        return pFile.getInputStream();
    }

    /**
     * Getter for current audit errors
     * 
     * @return list of errors
     */
    public List getCurrentAuditErrors()
    {
        return currentAuditErrors;
    }

    /**
     * Set current audit errors
     * 
     * @param pCurrentAuditErrors new errors
     */
    public void setCurrentAuditErrors( List pCurrentAuditErrors )
    {
        this.currentAuditErrors = pCurrentAuditErrors;
    }

    /**
     * Getter for previous audit errors
     * 
     * @return list of errors
     */
    public List getPreviousAuditErrors()
    {
        return previousAuditErrors;
    }

    /**
     * Set previous audit errors
     * 
     * @param pPreviousAuditErrors new errors
     */
    public void setPreviousAuditErrors( List pPreviousAuditErrors )
    {
        this.previousAuditErrors = pPreviousAuditErrors;
    }

    /**
     * {@inheritDoc}
     */
    public void wValidate( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        if ( presentation.getFileName().length() == 0 )
        {
            addError( "presentation", "export.audit_report.presentation.error.mandatory" );
            pRequest.setAttribute( "presentationError", "true" );
        } 
        if ( mapping.getFileName().length() == 0 )
        {
            addError( "mapping", "export.audit_report.mapping.error.mandatory" );
            pRequest.setAttribute( "mappingError", "true" );
        }
    }
}
