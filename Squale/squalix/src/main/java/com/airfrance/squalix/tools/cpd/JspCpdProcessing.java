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
package com.airfrance.squalix.tools.cpd;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;

/**
 * Détection de copier/coller en jsp
 */
public class JspCpdProcessing
    extends AbstractCpdProcessing
{
    /** Seuil de détection de copier/coller */
    private static final int JSP_THRESHOLD = 100;

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdTask#getTokenThreshold()
     */
    protected int getTokenThreshold()
    {
        return JSP_THRESHOLD;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdProcessing#getExtension()
     */
    protected String[] getExtensions()
    {
        return new String[] { ".jsp" };
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

    /**
     * {@inheritDoc}
     * 
     * @see com.airfrance.squalix.tools.cpd.AbstractCpdProcessing#getSourcesDirs(com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO)
     */
    protected ListParameterBO getSourcesDirs( MapParameterBO pProjectParams )
    {
        return (ListParameterBO) pProjectParams.getParameters().get( ParametersConstants.JSP );
    }

}
