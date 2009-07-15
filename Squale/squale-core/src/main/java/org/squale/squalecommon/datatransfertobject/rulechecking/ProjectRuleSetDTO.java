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
package org.squale.squalecommon.datatransfertobject.rulechecking;

import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;

/**
 * DTO pour ProjectRuleSetBO
 */
public class ProjectRuleSetDTO
    extends RuleSetDTO
{

    /** Projet sur lequel s'appliquent les règles */
    private ComponentDTO mProject;

    /**
     * @return le projet sur lequel s'appliquent les règles
     */
    public ComponentDTO getProject()
    {
        return mProject;
    }

    /**
     * Modifie mProject
     * 
     * @param pProject le projet sur lequel s'appliquent les règles
     */
    public void setProject( ComponentDTO pProject )
    {
        mProject = pProject;
    }

}
