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
package org.squale.squalecommon.datatransfertobject.transform.component;

import java.io.Serializable;
import java.util.ArrayList;

import org.squale.squalecommon.datatransfertobject.component.ComponentDTO;
import org.squale.squalecommon.datatransfertobject.tag.TagDTO;
import org.squale.squalecommon.datatransfertobject.transform.tag.TagTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ComponentType;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.tag.TagBO;

/**
 * @author M400841
 * @deprecated ComponentTransform doit etre utilisé a la place de ProjectTransform
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
     * @param pProjectDTO ProjectDTO à transformer
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
        if ( pProjectDTO.getTags() != null && pProjectDTO.getTags().size() > 0 )
        {
            for ( TagDTO tagDTO : pProjectDTO.getTags() )
            {
                projectBO.addTag( TagTransform.dto2Bo( tagDTO ) );
            }
        }
        else
        {
            projectBO.setTags( new ArrayList() );
        }
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
        if ( pProjectBO.getTags() != null && pProjectBO.getTags().size() > 0 )
        {
            for ( TagBO tagBO : pProjectBO.getTags() )
            {
                projectDTO.addTag( TagTransform.bo2Dto( tagBO ) );
            }
        }
        else
        {
            projectDTO.setTags( new ArrayList() );
        }

        return projectDTO;
    }
}
