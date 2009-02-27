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
import java.util.ArrayList;

import com.airfrance.squalecommon.datatransfertobject.component.ProjectConfDTO;
import com.airfrance.squalecommon.datatransfertobject.tag.TagDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.parameters.MapParameterTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.ProjectProfileTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.SourceManagementTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.rule.QualityGridTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.tag.TagTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProjectConfTransform
    implements Serializable
{
    /**
     * ProjectConfDTO --> ProjectBO
     * 
     * @param pProjectConfDTO ProjectDTO à transformer
     * @return ProjectBO
     */
    public static ProjectBO dto2Bo( ProjectConfDTO pProjectConfDTO )
    {
        // Initialisation du retour
        ProjectBO projectBO = new ProjectBO();

        // Initialisation des champs suivants
        projectBO.setId( pProjectConfDTO.getId() );
        projectBO.setName( pProjectConfDTO.getName() );
        projectBO.setParameters( MapParameterTransform.dto2Bo( pProjectConfDTO.getParameters() ) );
        projectBO.setProfile( ProjectProfileTransform.dto2bo( pProjectConfDTO.getProfile() ) );
        projectBO.setSourceManager( SourceManagementTransform.dto2bo( pProjectConfDTO.getSourceManager() ) );
        if ( pProjectConfDTO.getTags() != null && pProjectConfDTO.getTags().size() > 0 )
        {
            for ( TagDTO tagDTO : pProjectConfDTO.getTags() )
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
     * ProjectBO -> ProjectConfDTO
     * 
     * @param pProjectBO ProjectBO
     * @return ProjectConfDTO
     */
    public static ProjectConfDTO bo2Dto( ProjectBO pProjectBO )
    {
        // Initialisation du retour
        ProjectConfDTO projectConfDTO = new ProjectConfDTO();

        // Initialisation des valeurs
        projectConfDTO.setId( pProjectBO.getId() );
        projectConfDTO.setParameters( MapParameterTransform.bo2Dto( pProjectBO.getParameters() ) );
        projectConfDTO.setName( pProjectBO.getName() );
        projectConfDTO.setProfile( ProjectProfileTransform.bo2dto( pProjectBO.getProfile() ) );
        projectConfDTO.setSourceManager( SourceManagementTransform.bo2dto( pProjectBO.getSourceManager() ) );
        projectConfDTO.setQualityGrid( QualityGridTransform.bo2Dto( pProjectBO.getQualityGrid() ) );
        projectConfDTO.setParameters( MapParameterTransform.bo2Dto( pProjectBO.getParameters() ) );
        projectConfDTO.setStatus( pProjectBO.getStatus() );
        if ( pProjectBO.getTags() != null && pProjectBO.getTags().size() > 0 )
        {
            for ( TagBO tagBO : pProjectBO.getTags() )
            {
                projectConfDTO.addTag( TagTransform.bo2Dto( tagBO ) );
            }
        }
        else
        {
            projectConfDTO.setTags( new ArrayList() );
        }
        
        return projectConfDTO;
    }

    /**
     * Permet de modifier les valeurs souhaitées dans applicationDTO sans ecraser les relations
     * 
     * @param pProjectConfDTO ProjectConfDTO
     * @param pProjectBO ProjectBO
     */
    public static void dto2Bo( ProjectConfDTO pProjectConfDTO, ProjectBO pProjectBO )
    {

        // Initialisation des champs suivants
        pProjectBO.setId( pProjectConfDTO.getId() );
        pProjectBO.setName( pProjectConfDTO.getName() );
    }

}
