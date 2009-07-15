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
package org.squale.squaleweb.applicationlayer.action.component.parameters;

import javax.servlet.http.HttpServletRequest;

import org.squale.squaleweb.applicationlayer.formbean.creation.CreateProjectForm;

/**
 * Configuration des paramètres généraux d'un projet
 */
public class CreateGeneralParametersAction
    extends CreateParametersAction
{
    /**
     * @see org.squale.squaleweb.applicationlayer.action.component.parameters.CreateParametersAction#getTransformerParameters(org.squale.squaleweb.applicationlayer.formbean.creation.CreateProjectForm,
     *      javax.servlet.http.HttpServletRequest) {@inheritDoc}
     */
    public Object[] getTransformerParameters( CreateProjectForm pProject, HttpServletRequest pRequest )
        throws Exception
    {
        return new Object[] { pProject.getParameters(), pProject.getProfile() };
    }

}
