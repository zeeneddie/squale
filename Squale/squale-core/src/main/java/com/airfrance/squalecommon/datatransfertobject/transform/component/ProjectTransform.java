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
package com.airfrance.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;

import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;

/**
 * @author M400841
 * @deprecated ComponentTransform doit etre utilis� a la place de ProjectTransform
 */
public class ProjectTransform
    implements Serializable
{

    /**
     * Constructeur prive
     */
    private ProjectTransform()
    {
    }

    /**
     * ProjectDTO -> ProjectBO
     * 
     * @param pProjectDTO ProjectDTO � transformer
     * @return ProjectBO
     */
    public static ProjectBO dto2Bo( ComponentDTO pProjectDTO )
    {
        // Initialisation du retour
        ProjectBO projectBO = new ProjectBO();

        projectBO.setId( pProjectDTO.getID() );
        projectBO.setName( pProjectDTO.getName() );

        projectBO.setExcludedFromActionPlan( pProjectDTO.getExcludedFromActionPlan() );
        projectBO.setJustification( pProjectDTO.getJustification() );
        return projectBO;
    }

    /**
     * ProjectBO -> ProjectDTO
     * 
     * @param pProjectBO ProjectBO
     * @return ProjectDTO
     */
    public static ComponentDTO bo2Dto( ProjectBO pProjectBO )
    {
        // Initialisation du retour
        ComponentDTO projectDTO = new ComponentDTO();

        projectDTO.setID( pProjectBO.getId() );
        projectDTO.setName( pProjectBO.getName() );
        projectDTO.setType( ComponentType.PROJECT );
        projectDTO.setTechnology( pProjectBO.getProfile().getName() );
        projectDTO.setExcludedFromActionPlan( pProjectBO.getExcludedFromActionPlan() );
        projectDTO.setJustification( pProjectBO.getJustification() );

        return projectDTO;
    }
}
