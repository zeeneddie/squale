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

    /** Commencement du nom de l'application associée au projet */
    private String mApplicationBeginningName;

    /** Commencement du nom du projet */
    private String mProjectBeginningName;

    /** Liste des projets trouvés associés à leur dernier audit */
    private Map mProjectForms; // De type ProjectForm -> auditForm

    /**
     * Constructeur par défaut
     */
    public SearchProjectForm()
    {
        mApplicationBeginningName = "";
        mProjectBeginningName = "";
        mProjectForms = null;
    }

    /**
     * @return le début du nom de l'application associée au projet
     */
    public String getApplicationBeginningName()
    {
        return mApplicationBeginningName;
    }

    /**
     * @return le début du nom du projet
     */
    public String getProjectBeginningName()
    {
        return mProjectBeginningName;
    }

    /**
     * @param pApplicationBeginningName le début du nom de l'application associée au projet
     */
    public void setApplicationBeginningName( String pApplicationBeginningName )
    {
        mApplicationBeginningName = pApplicationBeginningName;
    }

    /**
     * @param pProjectBeginningName le début du nom du projet
     */
    public void setProjectBeginningName( String pProjectBeginningName )
    {
        mProjectBeginningName = pProjectBeginningName;
    }

    /**
     * @return la liste des projets trouvés associés à leur dernier audit
     */
    public Map getProjectForms()
    {
        return mProjectForms;
    }

    /**
     * @param pProjectForms la liste des projets trouvés associés à leur dernier audit
     */
    public void setProjectForms( Map pProjectForms )
    {
        mProjectForms = pProjectForms;
    }

    /**
     * @return les projets
     */
    public Set getKeys()
    {
        return mProjectForms.keySet();
    }

    /**
     * @see com.airfrance.welcom.struts.bean.WActionForm#wValidate(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void wValidate( ActionMapping mapping, HttpServletRequest request )
    {
        // Suppression des espaces sur le nom de l'appli
        setApplicationBeginningName( getApplicationBeginningName().trim() );
        if ( !isAValidName( getApplicationBeginningName() ) && getApplicationBeginningName().length() > 0 )
        {
            addError( "applicationBeginningName", new ActionError( "error.name.containsInvalidCharacter" ) );
        }
        // Vérification du nom du projet
        setProjectBeginningName( getProjectBeginningName().trim() );
        if ( !isAValidName( getProjectBeginningName() ) && getProjectBeginningName().length() > 0 )
        {
            addError( "projectBeginningName", new ActionError( "error.name.containsInvalidCharacter" ) );
        }
    }
}
