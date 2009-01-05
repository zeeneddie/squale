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

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMapping;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squaleweb.transformer.component.parameters.GeneralParametersTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 * Bean des paramètres généraux
 */
public class GeneralParametersForm
    extends AbstractParameterForm
{

    /** Indication de la nécessite des sources JSP */
    private boolean mJspSourcesRequired;

    /**
     * Listes des chemins vers les src à auditer du projet
     */
    private String[] mJspSources;

    /**
     * Listes des chemins vers les src à auditer du projet
     */
    private String[] mSources;

    /**
     * Expressions régulières à exclure de l'analyse
     */
    private String[] mExcludePatterns;

    /**
     * Expressions régulières à inclure de l'analyse
     */
    private String[] mIncludePatterns;

    /**
     * Constructeur par défaut
     */
    public GeneralParametersForm()
    {
        mExcludePatterns = new String[0];
        mIncludePatterns = new String[0];
        mSources = new String[0];
        mJspSources = new String[0];
    }

    /**
     * Getter.
     * 
     * @return emplacement des sources.
     */
    public String[] getSources()
    {
        return mSources;
    }

    /**
     * Setter.
     * 
     * @param pSources emplacement des sources.
     */
    public void setSources( String[] pSources )
    {
        mSources = pSources;
    }

    /**
     * @return les expressions régulières à exclure de l'analyse
     */
    public String[] getExcludePatterns()
    {
        return mExcludePatterns;
    }

    /**
     * @param pExcludePatterns les expressions régulières à exclure de l'analyse
     */
    public void setExcludePatterns( String[] pExcludePatterns )
    {
        mExcludePatterns = pExcludePatterns;
    }

    /**
     * @return les expressions régulières à inclure de l'analyse
     */
    public String[] getIncludePatterns()
    {
        return mIncludePatterns;
    }

    /**
     * @param pIncludePatterns les expressions régulières à inclure de l'analyse
     */
    public void setIncludePatterns( String[] pIncludePatterns )
    {
        mIncludePatterns = pIncludePatterns;
    }

    /**
     * Getter.
     * 
     * @return emplacement des sources.
     */
    public String[] getJspSources()
    {
        return mJspSources;
    }

    /**
     * Setter.
     * 
     * @param pJspSources emplacement des sources.
     */
    public void setJspSources( String[] pJspSources )
    {
        mJspSources = pJspSources;
    }

    /**
     * @return true si les sources JSP sont requises
     */
    public boolean isJspSourcesRequired()
    {
        return mJspSourcesRequired;
    }

    /**
     * @param pJspSourcesRequired indicateur de sources JSP requises
     */
    public void setJspSourcesRequired( boolean pJspSourcesRequired )
    {
        mJspSourcesRequired = pJspSourcesRequired;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     *      {@inheritDoc}
     */
    public Class getTransformer()
    {
        return GeneralParametersTransformer.class;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     *      {@inheritDoc}
     */
    public String getNameInSession()
    {
        return "generalParametersForm";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     *      {@inheritDoc}
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.SOURCES, ParametersConstants.EXCLUDED_PATTERNS,
            ParametersConstants.INCLUDED_PATTERNS, ParametersConstants.JSP };
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setJspSourcesRequired( false );
        setJspSources( new String[0] );
        setExcludePatterns( new String[0] );
        setIncludePatterns( new String[0] );
        setSources( new String[0] );
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "GeneralParameters";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        // Traitement des sources
        setSources( SqualeWebActionUtils.cleanValues( getSources() ) );
        if ( getSources().length == 0 )
        {
            addError( "sources", new ActionError( "error.field.required" ) );
        }
        // Vérification des sources JSP si requis
        if ( isJspSourcesRequired() )
        {
            setJspSources( SqualeWebActionUtils.cleanValues( getJspSources() ) );
            if ( getJspSources().length == 0 )
            {
                addError( "jspSources", new ActionError( "error.field.required" ) );
            }
        }
        // Traitement des exclusions
        setExcludePatterns( SqualeWebActionUtils.cleanValues( getExcludePatterns() ) );
        // Pas de nombre minimal pour les exclusions
        // Traitement des inclusions
        setIncludePatterns( SqualeWebActionUtils.cleanValues( getIncludePatterns() ) );
        // Pas de nombre minimal pour les inclusions
    }

}
