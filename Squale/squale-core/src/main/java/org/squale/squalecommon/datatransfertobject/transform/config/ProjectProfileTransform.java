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
package com.airfrance.squalecommon.datatransfertobject.transform.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.airfrance.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import com.airfrance.squalecommon.datatransfertobject.config.TaskDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Transforme un profile Squalix
 */
public class ProjectProfileTransform
{

    /**
     * Convertit un ProjectProfileBO en ProjectProfileDTO
     * 
     * @param pProfile l'objet à convertir
     * @return le résultat de la conversion
     */
    public static ProjectProfileDTO bo2dto( ProjectProfileBO pProfile )
    {
        ProjectProfileDTO result = new ProjectProfileDTO();
        result.setId( pProfile.getId() );
        result.setName( pProfile.getName() );

        // Les grilles
        List gridsDTO = new ArrayList();
        for ( Iterator gridsIt = pProfile.getGrids().iterator(); gridsIt.hasNext(); )
        {
            // On ne récupère que le nom de la grille
            gridsDTO.add( ( (QualityGridBO) gridsIt.next() ).getName() );
        }
        result.setGrids( gridsDTO );

        // Les tâches
        List analysisTasksDTO = new ArrayList();
        Iterator analysisTasks = pProfile.getAnalysisTasks().iterator();
        while ( analysisTasks.hasNext() )
        {
            analysisTasksDTO.add( TaskTransform.bo2dto( (TaskRefBO) analysisTasks.next() ) );
        }
        result.setAnalysisTasks( analysisTasksDTO );
        List terminationTasksDTO = new ArrayList();
        Iterator terminationTasks = pProfile.getTerminationTasks().iterator();
        while ( terminationTasks.hasNext() )
        {
            terminationTasksDTO.add( TaskTransform.bo2dto( (TaskRefBO) terminationTasks.next() ) );
        }
        result.setTerminationTasks( terminationTasksDTO );

        result.setExportIDE( pProfile.getExportIDE() );
        result.setLanguage( pProfile.getLanguage() );
        return result;
    }

    /**
     * Convertit une liste de ProjectProfileBO en liste de ProjectProfileDTO
     * 
     * @param pProfiles la liste des profiles à convertir
     * @return le résultat de la conversion
     */
    public static Collection bo2dto( Collection pProfiles )
    {
        Collection profilesDTO = new ArrayList();
        ProjectProfileDTO profileDTO;
        ProjectProfileBO profileBO;
        Iterator it = pProfiles.iterator();
        while ( it.hasNext() )
        {
            profileBO = (ProjectProfileBO) it.next();
            profileDTO = bo2dto( profileBO );
            profilesDTO.add( profileDTO );
        }
        return profilesDTO;
    }

    /**
     * Convertit un ProjectProfileDTO en ProjectProfileBO
     * 
     * @param pProfileDTO l'objet à convertir
     * @return l'objet transformé
     */
    public static ProjectProfileBO dto2bo( ProjectProfileDTO pProfileDTO )
    {
        ProjectProfileBO profileBO = new ProjectProfileBO();
        profileBO.setId( pProfileDTO.getId() );
        profileBO.setName( pProfileDTO.getName() );
        List analysisTasksBO = new ArrayList();
        Iterator analysisTasks = pProfileDTO.getAnalysisTasks().iterator();
        while ( analysisTasks.hasNext() )
        {
            analysisTasksBO.add( TaskTransform.dto2bo( (TaskDTO) analysisTasks.next() ) );
        }
        profileBO.setAnalysisTasks( analysisTasksBO );
        List terminationTasksDTO = new ArrayList();
        Iterator terminationTasks = pProfileDTO.getTerminationTasks().iterator();
        while ( terminationTasks.hasNext() )
        {
            terminationTasksDTO.add( TaskTransform.dto2bo( (TaskDTO) terminationTasks.next() ) );
        }
        profileBO.setTerminationTasks( terminationTasksDTO );
        profileBO.setExportIDE( pProfileDTO.getExportIDE() );
        profileBO.setLanguage( pProfileDTO.getLanguage() );
        return profileBO;
    }
}
