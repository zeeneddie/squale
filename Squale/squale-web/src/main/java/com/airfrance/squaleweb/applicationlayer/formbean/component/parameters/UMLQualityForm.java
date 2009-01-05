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
import com.airfrance.squaleweb.transformer.component.parameters.UMLQualityConfTransformer;
import com.airfrance.squaleweb.util.SqualeWebActionUtils;

/**
 * @author E6400802 Formulaire pour la configuration de la tâche UMLQuality
 */
public class UMLQualityForm
    extends AbstractParameterForm
{

    /** Chemin relatif ou absolu vers le fichier XMI */
    private String mXmiFile;

    /**
     * expressions régulières des composants à exclure de l'analyse
     */
    private String[] mExcludeUMLPatterns;

    /**
     * Constructeur par défaut
     */
    public UMLQualityForm()
    {
        mXmiFile = "";
        mExcludeUMLPatterns = new String[0];
    }

    /**
     * @return le chemin du fichier XMI
     */
    public String getXmiFile()
    {
        return mXmiFile;
    }

    /**
     * Modifie le chemin du fichier XMI
     * 
     * @param pXmiFile le nouveau chemin du fichier XMI
     */
    public void setXmiFile( String pXmiFile )
    {
        mXmiFile = pXmiFile;
    }

    /**
     * @return patterns des classes à exclure de l'analyse
     */
    public String[] getExcludeUMLPatterns()
    {
        return mExcludeUMLPatterns;
    }

    /**
     * @param pExcludePatterns patterns des classes à exclure de l'analyse
     */
    public void setExcludeUMLPatterns( String[] pExcludePatterns )
    {
        mExcludeUMLPatterns = pExcludePatterns;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTransformer()
     *      {@inheritDoc}
     */
    public Class getTransformer()
    {
        return UMLQualityConfTransformer.class;
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getNameInSession()
     *      {@inheritDoc}
     */
    public String getNameInSession()
    {
        return "umlQualityForm";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getParametersConstants()
     *      {@inheritDoc}
     */
    public String[] getParametersConstants()
    {
        return new String[] { ParametersConstants.UMLQUALITY };
    }

    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public void reset( ActionMapping mapping, HttpServletRequest request )
    {
        setXmiFile( "" );
        setExcludeUMLPatterns( new String[0] );
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#getTaskName()
     *      {@inheritDoc}
     */
    public String getTaskName()
    {
        return "UMLQualityTask";
    }

    /**
     * @see com.airfrance.squaleweb.applicationlayer.formbean.component.parameters.AbstractParameterForm#validateConf(org.apache.struts.action.ActionMapping,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    protected void validateConf( ActionMapping pMapping, HttpServletRequest pRequest )
    {
        setExcludeUMLPatterns( SqualeWebActionUtils.cleanValues( getExcludeUMLPatterns() ) );
        getXmiFile().trim();
        if ( getXmiFile().length() == 0 )
        {
            addError( "xmiFile", new ActionError( "error.field.required" ) );
        }
    }

}
