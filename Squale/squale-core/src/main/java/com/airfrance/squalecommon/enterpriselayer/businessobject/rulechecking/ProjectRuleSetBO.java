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
package com.airfrance.squalecommon.enterpriselayer.businessobject.rulechecking;

import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * Ensemble de règles liées à un outil et spécifique à un projet
 * 
 * @hibernate.subclass discriminator-value="ProjectRuleSet"
 */
public class ProjectRuleSetBO
    extends RuleSetBO
{

    /** Projet sur lequel s'appliquent les règles */
    private ProjectBO mProject;

    /**
     * @return le projet sur lequel s'appliquent les règles
     * @hibernate.many-to-one class="com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO"
     *                        column="ProjectId" cascade="all" outer-join="auto" update="true" insert="true"
     */
    public ProjectBO getProject()
    {
        return mProject;
    }

    /**
     * Modifie mProject
     * 
     * @param pProject le projet sur lequel s'appliquent les règles
     */
    public void setProject( ProjectBO pProject )
    {
        mProject = pProject;
    }

}
