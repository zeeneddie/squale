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
package com.airfrance.squaleweb.applicationlayer.formbean.creation;

import java.util.ArrayList;
import java.util.List;

import com.airfrance.squaleweb.applicationlayer.formbean.RootForm;

/**
 * @author M400841
 */
public class CreateProjectListForm
    extends RootForm
{

    /**
     * Liste de CreateProjectForm
     */
    private List mProjectList = new ArrayList();

    /**
     * objet CreateProjectForm courant en cours de manipulation
     */
    private CreateProjectForm mCurrentProject = null;

    /**
     * @return projet courant
     */
    public CreateProjectForm getCurrentProject()
    {
        return mCurrentProject;
    }

    /**
     * @return liste des projets relatifs à une application
     */
    public List getProjectList()
    {
        return mProjectList;
    }

    /**
     * @param pCurrentProject projet courant
     */
    public void setCurrentProject( CreateProjectForm pCurrentProject )
    {
        mCurrentProject = pCurrentProject;
    }

    /**
     * @param pProjectList liste des projets relatifs a une application
     */
    public void setProjectList( List pProjectList )
    {
        mProjectList = pProjectList;
    }

}
