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

import java.util.ArrayList;

import com.airfrance.squalecommon.SqualeTestCase;
import com.airfrance.squalecommon.datatransfertobject.component.ProjectConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import com.airfrance.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import com.airfrance.squalecommon.datatransfertobject.config.SourceManagementDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProjectConfTransformTest
    extends SqualeTestCase
{

    /**
     * teste la transformation d'un ProjectDTO en projectBO
     * 
     * @throws Exception en cas d'échec de la transformation
     */
    public void testDto2Bo()
        throws Exception
    {

        // Initialisation du retour
        ProjectBO projectBO = null;

        ProjectConfDTO projectConfDTO = new ProjectConfDTO(); // à initialiser
        projectConfDTO.setId( 1 );
        projectConfDTO.setLocation( "\\VOB\\squale" );
        projectConfDTO.setName( "squale" );
        projectConfDTO.setParameters( new MapParameterDTO() );
        projectConfDTO.setSourceManager( new SourceManagementDTO() );
        ProjectProfileDTO profil = new ProjectProfileDTO();
        profil.setAnalysisTasks( new ArrayList() );
        profil.setTerminationTasks( new ArrayList() );
        profil.setName( "profil de test" );
        profil.setId( 1 );
        projectConfDTO.setProfile( profil );
        SourceManagementDTO manager = new SourceManagementDTO();
        manager.setAnalysisTasks( new ArrayList() );
        manager.setTerminationTasks( new ArrayList() );
        projectConfDTO.setSourceManager( manager );

        projectBO = ProjectConfTransform.dto2Bo( projectConfDTO );

        assertEquals( projectBO.getName(), projectConfDTO.getName() );
        assertEquals( projectBO.getId(), projectConfDTO.getId() );
        assertEquals( projectBO.getProfile().getId(), projectConfDTO.getProfile().getId() );
        assertEquals( projectBO.getParameters().getId(), projectConfDTO.getParameters().getId() );
    }

    /**
     * teste la transformation d'un ProjectBO en projectDTO
     * 
     * @throws Exception en cas d'échec de la transformation
     */

    public void testBo2Dto()
        throws Exception
    {

        // Initialisation du retour
        ProjectConfDTO projectConfDTO = null;

        ProjectBO projectBO = new ProjectBO(); // à initialiser
        projectBO.setId( 1 );
        projectBO.setName( "squale" );
        projectBO.setParameters( new MapParameterBO() );
        ProjectProfileBO profil = getComponentFactory().createProjectProfile( getSession() );
        projectBO.setProfile( profil );
        SourceManagementBO manager = getComponentFactory().createSourceManagement( getSession() );
        projectBO.setSourceManager( manager );
        projectConfDTO = ProjectConfTransform.bo2Dto( projectBO );

        assertEquals( projectBO.getName(), projectConfDTO.getName() );
        assertEquals( projectBO.getId(), projectConfDTO.getId() );
        assertEquals( projectBO.getProfile().getId(), projectConfDTO.getProfile().getId() );
        assertEquals( projectBO.getParameters().getId(), projectConfDTO.getParameters().getId() );

    }

}
