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
package org.squale.squalecommon.enterpriselayer.facade.component;

import java.util.Map;

import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.SqualeTestCase;
import org.squale.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import org.squale.squalecommon.datatransfertobject.component.ProjectConfDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.MapParameterDTO;
import org.squale.squalecommon.datatransfertobject.component.parameters.StringParameterDTO;
import org.squale.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import org.squale.squalecommon.datatransfertobject.config.SourceManagementDTO;
import org.squale.squalecommon.datatransfertobject.rule.QualityGridDTO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;

/**
 * Test de la facade project Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProjectFacadeTest
    extends SqualeTestCase
{
    /**
     * provider de persistence
     */
    private static IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Test du get
     */
    public void testGet()
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            ApplicationBO application = getComponentFactory().createApplication( session );
            QualityGridBO grid = getComponentFactory().createGrid( session );
            ProjectProfileBO profile = getComponentFactory().createProjectProfile( session );
            SourceManagementBO manager = getComponentFactory().createSourceManagement( session );
            MapParameterBO parameters = getComponentFactory().createParameters( session );
            ProjectBO project =
                getComponentFactory().createProject( session, application, grid, profile, manager, parameters );
            ProjectConfDTO projectConf = new ProjectConfDTO();
            projectConf.setId( project.getId() );
            ProjectConfDTO out = ProjectFacade.get( projectConf );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

    /**
     * Test du update
     */
    public void testUpdate()
    {
        try
        {
            ISession session = PERSISTENTPROVIDER.getSession();
            ApplicationBO application = getComponentFactory().createApplication( session );
            QualityGridBO grid = getComponentFactory().createGrid( session );
            ProjectProfileBO profile = getComponentFactory().createProjectProfile( session );
            SourceManagementBO manager = getComponentFactory().createSourceManagement( session );
            MapParameterBO parameters = getComponentFactory().createParameters( session );
            ProjectBO project =
                getComponentFactory().createProject( session, application, grid, profile, manager, parameters );
            assertEquals( 0, project.getParameters().getParameters().size() );
            ApplicationConfDTO applicationConf = new ApplicationConfDTO();
            applicationConf.setId( application.getId() );
            ProjectConfDTO projectConf = new ProjectConfDTO();
            projectConf.setId( project.getId() );
            projectConf.setName( project.getName() );
            // Création des paramètres
            MapParameterDTO params = new MapParameterDTO();
            StringParameterDTO strParam = new StringParameterDTO( "strParam" );
            params.getParameters().put( "strParam", strParam );
            projectConf.setParameters( params );
            // La grille
            QualityGridDTO gridDTO = new QualityGridDTO();
            gridDTO.setName( grid.getName() );
            projectConf.setQualityGrid( gridDTO );
            // Le profil
            ProjectProfileDTO profileDTO = new ProjectProfileDTO();
            profileDTO.setName( profile.getName() );
            projectConf.setProfile( profileDTO );
            // Le source manager
            SourceManagementDTO managerDTO = new SourceManagementDTO();
            managerDTO.setName( manager.getName() );
            projectConf.setSourceManager( managerDTO );
            ProjectFacade.update( projectConf, applicationConf, getSession() );
            ProjectConfDTO projectconfGetting = ProjectFacade.get( projectConf );
            MapParameterDTO paramsDTOGetting = projectconfGetting.getParameters();
            Map mapGetting = paramsDTOGetting.getParameters();
            assertEquals( 1, mapGetting.size() );
            FacadeHelper.closeSession( session, "" );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            fail( "unexpected exception" );
        }
    }

}
