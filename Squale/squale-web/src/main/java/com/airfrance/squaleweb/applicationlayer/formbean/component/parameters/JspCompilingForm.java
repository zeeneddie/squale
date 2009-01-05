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
package com.airfrance.squaleweb.applicationlayer.formbean.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.JspCompilingConfTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 * Bean pour la configuration de la compilation des JSPs
 */
public class JspCompilingForm
    extends AbstractParameterForm
{

    /**
     * Nom des répertoires à exclure de la compilation
     */
    private String[] mExcludeJspDir = new String[0];

    /** Version du j2ee utilisée */
    private String mJ2eeVersion = "";

    /** Chemin vers le répertoire d'application web */
    private String mWebAppPath = "";

    /** Les spécifications j2ee disponibles */
    private String[] mJ2eeVersions = ParametersConstants.J2EE_VERSIONS;

    /**
     * @return Nom des répertoires à exclure de la compilation
     */
    public String[] getExcludeJspDir()
    {
        return mExcludeJspDir;
    }

    /**
     * @param pExcludeDirectories Nom des répertoires à exclure de la compilation
     */
    public void setExcludeJspDir( String[] pExcludeDirectories )
    {
        mExcludeJspDir = pExcludeDirectories;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     *      {@inheritDoc}
     */
    public Class getTransformer()
    {
        return JspCompilingConfTransformer.class;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     *      {@inheritDoc}
     */
    public String getNameInSession()
    {
        return "jspCompilingForm";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     *      {@inheritDoc}
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.WEB_APP, ParametersConstants.J2EE_VERSION,
            ParametersConstants.JSP_EXCLUDED_DIRS };
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "JspCompilingTask";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        // On nettoie les exclusions de compilation
        setExcludeJspDir( SqualeWebActionUtils.cleanValues( getExcludeJspDir() ) );
        // Version des servlets obligatoire
        if ( getJ2eeVersion().length() == 0 )
        {
            addError( "j2eeVersion", "project_creation.jsp.j2ee_version.required" );
        }
        // Chemin vers le répertoire d'application Web
        setWebAppPath( getWebAppPath().trim() );
        if ( getWebAppPath().length() == 0 )
        {
            addError( "webAppPath", "project_creation.jsp.web_app_path.required" );
        }
        // Clean excluded directories
        setExcludeJspDir( SqualeWebActionUtils.cleanValues( getExcludeJspDir() ) );
    }

    /**
     * @return la version du j2ee
     */
    public String getJ2eeVersion()
    {
        return mJ2eeVersion;
    }

    /**
     * @param pJ2eeVersion la version du j2ee
     */
    public void setJ2eeVersion( String pJ2eeVersion )
    {
        mJ2eeVersion = pJ2eeVersion;
    }

    /**
     * @return les spécifications j2ee disponibles
     */
    public String[] getJ2eeVersions()
    {
        return mJ2eeVersions;
    }

    /**
     * @param pJ2eeVersions les spécifications j2ee disponibles
     */
    public void setJ2eeVersions( String[] pJ2eeVersions )
    {
        mJ2eeVersions = pJ2eeVersions;
    }

    /**
     * @return le chemin vers le répertoire d'application Web
     */
    public String getWebAppPath()
    {
        return mWebAppPath;
    }

    /**
     * @param pWebAppPath le chemin vers le répertoire d'application Web
     */
    public void setWebAppPath( String pWebAppPath )
    {
        mWebAppPath = pWebAppPath;
    }

}
