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
package com.airfrance.squaleweb.applicationlayer.formbean.search;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * Bean pour la recherche d'un projet
 */
public class SearchProjectForm
    extends RootForm
{

    /** Beginning of the Name of the application associated with the project */
    private String mApplicationBeginningName;

    /** Beginning of the Name of the project */
    private String mProjectBeginningName;

    /** List of the found projects associated with their last audit */
    private Map mProjectForms; // De type ProjectForm -> auditForm

    /** List of tags separated by spaces that the project must have */
    private String mTagList;

    /**
     * Default constructor
     */
    public SearchProjectForm()
    {
        mApplicationBeginningName = "";
        mProjectBeginningName = "";
        mTagList = "";
        mProjectForms = null;
    }

    /**
     * retrieves the value of the mApplicationBeginningName property
     * 
     * @return the beginning of the name of the application associated with the project
     */
    public String getApplicationBeginningName()
    {
        return mApplicationBeginningName;
    }

    /**
     * Retrieves the value of the mProjectBeginningName property
     * 
     * @return the beginning of the project name
     */
    public String getProjectBeginningName()
    {
        return mProjectBeginningName;
    }

    /**
     * sets the value of the mApplicationBeginningName property
     * 
     * @param pApplicationBeginningName the beginning of the name of the application associated with the project
     */
    public void setApplicationBeginningName( String pApplicationBeginningName )
    {
        mApplicationBeginningName = pApplicationBeginningName;
    }

    /**
     * sets the value of the mProjectBeginningName property
     * 
     * @param pProjectBeginningName the beginning of the project name
     */
    public void setProjectBeginningName( String pProjectBeginningName )
    {
        mProjectBeginningName = pProjectBeginningName;
    }

    /**
     * Retrieves the value of the mProjectForms property
     * 
     * @return the list of the found projects associated with their last audit
     */
    public Map getProjectForms()
    {
        return mProjectForms;
    }

    /**
     * sets the value of the mProjectForms property
     * 
     * @param pProjectForms the list of the found projects associated with their last audit
     */
    public void setProjectForms( Map pProjectForms )
    {
        mProjectForms = pProjectForms;
    }

    /**
     * Retrieves the keys of the project map
     * 
     * @return the projects
     */
    public Set getKeys()
    {
        return mProjectForms.keySet();
    }

    /**
     * Retrieves the value of the mTagList property
     * 
     * @return List of tags separated by spaces that the project must have
     */
    public String getTagList()
    {
        return mTagList;
    }

    /**
     * sets the value of the mTagList property
     * 
     * @param pTagList List of tags separated by spaces that the project must have
     */
    public void setTagList( String pTagList )
    {
        mTagList = pTagList;
    }

    /**
     * Validation method
     * 
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void wValidate( ActionMapping mapping, HttpServletRequest request )
    {
        // Take away spaces from the application name
        setApplicationBeginningName( getApplicationBeginningName().trim() );
        if ( !isAValidName( getApplicationBeginningName() ) && getApplicationBeginningName().length() > 0 )
        {
            addError( "applicationBeginningName", new ActionError( "error.name.containsInvalidCharacter" ) );
        }
        // Project name verification
        setProjectBeginningName( getProjectBeginningName().trim() );
        if ( !isAValidName( getProjectBeginningName() ) && getProjectBeginningName().length() > 0 )
        {
            addError( "projectBeginningName", new ActionError( "error.name.containsInvalidCharacter" ) );
        }
    }
}
