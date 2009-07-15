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
package org.squale.squalix.tools.pmd;

import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;

import net.sourceforge.pmd.SourceType;

/**
 * Traitement PMD sur du code java
 */
public class JspPmdProcessing
    extends AbstractPmdProcessing
{
    /** Extensions autorisées */
    private String[] mExtensions = { ".jsp" };

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.tools.pmd.AbstractPmdProcessing#getLanguage()
     */
    protected String getLanguage()
    {
        return "jsp";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.tools.pmd.AbstractPmdProcessing#getSourceType()
     */
    protected SourceType getSourceType()
    {
        return SourceType.JSP;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.squale.squalix.tools.pmd.AbstractPmdProcessing#getExtensions()
     */
    protected String[] getExtensions()
    {
        return mExtensions;
    }

    /**
     * Obtention des sources
     * 
     * @param pProjectParams paramètres du projet
     * @return sources sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getSourcesDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.JSP );
    }

    /**
     * Obtention des répertoires exclus de la compilation
     * 
     * @param pProjectParams paramètres du projet
     * @return répertories exclus sous la forme de ListParameterBO(StringParameterBO)
     */
    protected ListParameterBO getExcludedDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.JSP_EXCLUDED_DIRS );
    }

}
