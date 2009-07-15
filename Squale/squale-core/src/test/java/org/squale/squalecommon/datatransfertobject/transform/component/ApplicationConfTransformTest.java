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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import org.squale.squalecommon.datatransfertobject.component.ProjectConfDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import org.squale.squalecommon.datatransfertobject.config.ServeurDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ServeurBO;

/**
 * @author M400841 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ApplicationConfTransformTest
    extends SqualeTestCase
{

    /**
     * Test la transformation de DTO en BO
     * 
     * @throws Exception en cas d'échec de la transformation
     */
    public void testDto2Bo()
        throws Exception
    {
        // Initialisation du retour
        ApplicationBO applicationBO = null;

        // Initialisation des parametres
        ApplicationConfDTO applicationConfDTO = new ApplicationConfDTO(); // à initialiser
        applicationConfDTO.setId( 13 );
        applicationConfDTO.setName( "bonjour" );
        applicationConfDTO.setAuditFrequency( 10 );
        applicationConfDTO.setResultsStorageOptions( 0 );
        ServeurDTO serverDTO = new ServeurDTO();
        serverDTO.setName( "QVI" );
        serverDTO.setServeurId( 1 );
        applicationConfDTO.setServeurDTO( serverDTO );
        applicationConfDTO.setStatus( 0 );

        // Collection des projets à initialiser
        Collection projects = new ArrayList();
        ProjectConfDTO project1 = new ProjectConfDTO();
        project1.setId( 12 );
        MapParameterDTO params = new MapParameterDTO();
        project1.setParameters( params );
        project1.setName( "project1" );
        project1.setLocation( "\\VOB\\Project1" );
        ProjectConfDTO project2 = new ProjectConfDTO();
        project2.setId( 12 );
        project2.setParameters( params );
        project2.setName( "project2" );
        project2.setLocation( "\\VOB\\Project2" );

        applicationConfDTO.setProjectConf( projects );

        // Transformation
        applicationBO = ApplicationConfTransform.dto2Bo( applicationConfDTO );

        // Tests d'identite
        assertEquals( applicationBO.getName(), applicationConfDTO.getName() );
        assertEquals( applicationBO.getId(), applicationConfDTO.getId() );
        assertEquals( applicationBO.getAuditFrequency(), applicationConfDTO.getAuditFrequency() );
        assertEquals( applicationBO.getServeurBO().getName(), applicationConfDTO.getServeurDTO().getName() );
        assertEquals( applicationBO.getResultsStorageOptions(), applicationConfDTO.getResultsStorageOptions() );
        assertEquals( applicationBO.getStatus(), applicationConfDTO.getStatus() );

    }

    /**
     * Test la transformation de BO en DTO
     * 
     * @throws Exception en cas d'échec de la transformation
     */
    public void testBo2Dto()
        throws Exception
    {
        // Initialisation du retour
        ApplicationConfDTO applicationConfDTO = null;

        // Initialisation des parametres
        ApplicationBO applicationBO = new ApplicationBO(); // à initialiser
        applicationBO.setId( 13 );
        applicationBO.setName( "bonjour" );
        applicationBO.setAuditFrequency( 10 );
        applicationBO.setLastUpdate();
        applicationBO.setResultsStorageOptions( 0 );
        ServeurBO serverBO = new ServeurBO();
        serverBO.setName( "QVI" );
        serverBO.setServeurId( 1 );
        applicationBO.setServeurBO( serverBO );
        applicationBO.setStatus( 0 );

        // Collection des projets à initialiser
        Collection projects = new ArrayList();
        ProjectBO project1 = new ProjectBO();
        project1.setId( 12 );
        project1.setParameters( new MapParameterBO() );
        project1.setName( "project1" );
        ProjectBO project2 = new ProjectBO();
        project2.setId( 12 );
        project2.setParameters( new MapParameterBO() );
        project2.setName( "project2" );

        Iterator it = projects.iterator();
        while ( it.hasNext() )
        {
            ProjectBO project = (ProjectBO) it.next();
            applicationBO.addComponent( project );
        }

        // Transformation
        applicationConfDTO = ApplicationConfTransform.bo2Dto( applicationBO );

        // Tests d'identite
        assertEquals( applicationBO.getName(), applicationConfDTO.getName() );
        assertEquals( applicationBO.getId(), applicationConfDTO.getId() );
        assertEquals( applicationBO.getAuditFrequency(), applicationConfDTO.getAuditFrequency() );
        assertEquals( applicationBO.getServeurBO().getName(), applicationConfDTO.getServeurDTO().getName() );
        assertEquals( applicationBO.getLastUpdate(), applicationConfDTO.getLastUpdate() );
        assertEquals( applicationBO.getResultsStorageOptions(), applicationConfDTO.getResultsStorageOptions() );
        assertEquals( applicationBO.getStatus(), applicationConfDTO.getStatus() );

        // TODO tester l'identite de la transformation des projets

    }

}
