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
package com.airfrance.squalecommon.enterpriselayer.facade.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.component.ProjectParameterDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.config.SourceManagementDAOImpl;
import com.airfrance.squalecommon.daolayer.rule.QualityGridDAOImpl;
import com.airfrance.squalecommon.daolayer.tag.TagDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.component.ApplicationConfDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ComponentDTO;
import com.airfrance.squalecommon.datatransfertobject.component.ProjectConfDTO;
import com.airfrance.squalecommon.datatransfertobject.tag.TagDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ProjectConfTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.component.parameters.MapParameterTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ProjectBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ListParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.MapParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.ParametersConstants;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.parameters.StringParameterBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.tag.TagBO;
import com.airfrance.squalecommon.enterpriselayer.facade.FacadeMessages;

/**
 */
public class ProjectFacade
    implements IFacade
{

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /** log */
    private static Log LOG = LogFactory.getLog( ProjectFacade.class );

    /**
     * Modifies a given project with a DTO object
     * 
     * @param pProjectConf the project to modify
     * @param pApplicationConf the associated application
     * @param pSession JRAF session
     * @return pProjectConfDTO if the update was correctly made, <code>null</code> otherwise
     * @throws JrafEnterpriseException JRAF exception
     */
    public static ProjectConfDTO update( ProjectConfDTO pProjectConf, ApplicationConfDTO pApplicationConf,
                                         ISession pSession )
        throws JrafEnterpriseException
    {
        // Initialization of the BO associated and application Id
        ProjectBO projectBO = null;
        ApplicationBO applicationBO = null; // loaded ProjectBO
        ProjectBO newProject = null; // ProjectBO after modification
        List qualityRules = new ArrayList(); // List of the qualityRules to calculate
        Long projectID = new Long( pProjectConf.getId() );
        Long applicationID = new Long( pApplicationConf.getId() );
        // indicates if the project needs to be saved or not
        boolean toUpdate = false;
        // value that will be returned by the method
        ProjectConfDTO result = null;

        try
        {
            if ( pSession == null )
            {
                //CHECKSTYLE:OFF
                pSession = PERSISTENTPROVIDER.getSession();
                //CHECKSTYLE:ON
            }

            // Initialization of the DAOs
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            ApplicationDAOImpl applicationDAO = ApplicationDAOImpl.getInstance();

            // the associated projects are loaded
            projectBO = (ProjectBO) projectDAO.get( pSession, projectID );
            // If the object does not exist in the database, it needs to be created
            if ( projectBO == null )
            {
                projectBO = new ProjectBO();
                toUpdate = true;
            }
            else
            {
                // If it already exists we check if it must be updated
                toUpdate = projectChanged( pProjectConf, projectBO );
            }
            // If the project has changed, it is transformed and saved
            if ( toUpdate )
            {
                // Transformation of the DTO to a BO if the project doesn't exist in the database
                ProjectConfTransform.dto2Bo( pProjectConf, projectBO );

                // Loading of the associated quality grid
                String gridName = pProjectConf.getQualityGrid().getName();
                QualityGridBO gridBO =
                    (QualityGridBO) QualityGridDAOImpl.getInstance().findWhereName( pSession, gridName );
                projectBO.setQualityGrid( gridBO );

                // Loading of the associated profil
                String profileName = pProjectConf.getProfile().getName();
                ProjectProfileBO profileBO =
                    (ProjectProfileBO) ProjectProfileDAOImpl.getInstance().findWhereName( pSession, profileName );
                projectBO.setProfile( profileBO );

                // Loading of the associated source manager
                String managerName = pProjectConf.getSourceManager().getName();
                SourceManagementBO managerBO =
                    (SourceManagementBO) SourceManagementDAOImpl.getInstance().findWhereName( pSession, managerName );
                projectBO.setSourceManager( managerBO );

                // The parameters are loaded
                ProjectParameterDAOImpl paramDAO = ProjectParameterDAOImpl.getInstance();
                MapParameterBO parameters = MapParameterTransform.dto2Bo( pProjectConf.getParameters() );
                Long paramId = new Long( projectBO.getParameters().getId() );
                paramDAO.removeAndCreateNew( pSession, paramId, parameters );
                projectBO.setParameters( parameters );

                // Loading of the associated application
                applicationBO = (ApplicationBO) applicationDAO.get( pSession, applicationID );

                // If the application exists and the project has been modified, the relation
                // is modified and saved
                if ( applicationBO != null )
                {
                    if ( projectBO.getParent() == null || projectBO.getParent().getId() == -1 )
                    {
                        // A project must never be linked to another application
                        projectBO.setParent( applicationBO );
                    }
                    newProject = projectDAO.save( pSession, projectBO );
                    // The associated application is saved to update the latest modifications
                    // on the informations
                    applicationBO.setLastUpdate( pApplicationConf.getLastUpdate() );
                    applicationBO.setLastUser( pApplicationConf.getLastUser() );
                    applicationDAO.save( pSession, applicationBO );
                }

                // the return is initialized
                if ( newProject != null )
                {
                    result = ProjectConfTransform.bo2Dto( projectBO );
                }
            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ProjectFacade.class.getName() + ".update" );
        }
        finally
        {
            FacadeHelper.closeSession( pSession, ProjectFacade.class.getName() + ".update" );
        }

        return result;

    }

    /**
     * Verifies if the project in the database needs to be updated
     * 
     * @param pProjectConf the modified project
     * @param projectBO the project from the database
     * @return true if the projects needs to be updated
     */
    private static boolean projectChanged( ProjectConfDTO pProjectConf, ProjectBO projectBO )
    {
        boolean toUpdate = false;
        toUpdate |= !pProjectConf.getName().equals( projectBO.getName() );
        // If the grid has been modified, the project needs to be saved
        toUpdate |= !projectBO.getQualityGrid().getName().equals( pProjectConf.getQualityGrid().getName() );
        // If the profil has been modified, the project needs to be saved
        toUpdate |= !projectBO.getProfile().getName().equals( pProjectConf.getProfile().getName() );
        // If the source manager has been changed, the project needs to be saved
        toUpdate |= !projectBO.getSourceManager().getName().equals( pProjectConf.getSourceManager().getName() );
        // The parameters are transformed for comparison
        MapParameterBO parameters = MapParameterTransform.dto2Bo( pProjectConf.getParameters() );
        toUpdate |= !projectBO.getParameters().equals( parameters );
        return toUpdate;
    }

    /**
     * deletes a selected project
     * 
     * @param pProjectId the ID of the project
     * @param pSession JRAF session
     * @return the modified project, null if an error occurs
     * @throws JrafEnterpriseException exception JRAF
     */
    public static ProjectConfDTO delete( Long pProjectId, ISession pSession )
        throws JrafEnterpriseException
    {
        // Initialization
        ProjectConfDTO result = null;
        try
        {
            // Initailization of the DAO
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            // The project is retrieved from the given Id
            ProjectBO projectBO = (ProjectBO) projectDAO.get( pSession, pProjectId );
            // Status is changed
            projectBO.setStatus( ProjectBO.DELETED );
            // update
            projectDAO.save( pSession, projectBO );
            // transformation
            result = ProjectConfTransform.bo2Dto( projectBO );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ProjectFacade.class.getName() + ".delete" );
        }
        finally
        {
            FacadeHelper.closeSession( pSession, ProjectFacade.class.getName() + ".delete" );
        }
        return result;
    }

    /**
     * Deletes a selected project
     * 
     * @param pProjectId the Id of the project
     * @param pSession session JRAF
     * @return the modified project, null if an error occurs
     * @throws JrafEnterpriseException exception JRAF
     */
    public static ProjectConfDTO disactiveOrReactiveProject( Long pProjectId, ISession pSession )
        throws JrafEnterpriseException
    {
        // Initialization
        ProjectConfDTO result = null;
        try
        {
            // Initailization of the DAO
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            // the project is retrievd from the given id
            ProjectBO projectBO = (ProjectBO) projectDAO.get( pSession, pProjectId );
            // the status is changed
            if ( projectBO.getStatus() == ProjectBO.ACTIVATED )
            {
                // deactivation of the project
                projectBO.setStatus( ProjectBO.DISACTIVATED );
            }
            else
            {
                // activation of the project
                projectBO.setStatus( ProjectBO.ACTIVATED );
            }
            // update
            projectDAO.save( pSession, projectBO );
            // transformation
            result = ProjectConfTransform.bo2Dto( projectBO );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ProjectFacade.class.getName() + ".delete" );
        }
        finally
        {
            FacadeHelper.closeSession( pSession, ProjectFacade.class.getName() + ".delete" );
        }
        return result;
    }

    /**
     * retrieves the object ProjectDTO from a given ID
     * 
     * @param pProjectConf ProjectDTO containing the id of the wanted projectDTO
     * @return ProjectDTO retrieved from the database
     * @throws JrafEnterpriseException JRAF exception
     * @roseuid 42CBFFB103E1
     */
    public static ProjectConfDTO get( ProjectConfDTO pProjectConf )
        throws JrafEnterpriseException
    {
        // Initialization of the BO associated and the Id
        ProjectBO projectBO = null; // project DTO
        List resultsCalculated = new ArrayList(); // list of the calculated results
        Long projectID = null; // identifiant du projet
        ProjectConfDTO newProjectConf = pProjectConf;
        ISession session = null;
        try
        {
            projectID = new Long( newProjectConf.getId() );
            session = PERSISTENTPROVIDER.getSession();
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            // loading of the associated BO
            projectBO = (ProjectBO) projectDAO.get( session, projectID );
            // Transformation of the BO to DTO
            if ( null != projectBO )
            {
                newProjectConf = ProjectConfTransform.bo2Dto( projectBO );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ProjectFacade.class.getName() + ".get" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ProjectFacade.class.getName() + ".get" );
        }

        return newProjectConf;

    }

    /**
     * Creates a project relatively to the application
     * 
     * @param pProjectConf the project to modify
     * @param pApplicationConf application to which it must be linked
     * @param pSession JRAF session
     * @throws JrafEnterpriseException JRAF exception
     */
    public static void insert( ProjectConfDTO pProjectConf, ApplicationConfDTO pApplicationConf, ISession pSession )
        throws JrafEnterpriseException
    {

        // Initialisation
        ProjectBO projectBO = null;
        ApplicationBO ApplicationBO = null; // retour de la ApplicationDAO
        Long projectID = new Long( pProjectConf.getId() );
        // id of the projet
        Long ApplicationID = new Long( pApplicationConf.getId() );

        try
        {
            if ( pSession == null )
            {
                //CHECKSTYLE:OFF
                pSession = PERSISTENTPROVIDER.getSession();
                //CHECKSTYLE:ON
            }

            // Initialization of the DAOs
            ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
            ApplicationDAOImpl ApplicationDAO = ApplicationDAOImpl.getInstance();

            // loading of ApplicationBO and ProjectBO
            ApplicationBO = (ApplicationBO) ApplicationDAO.get( pSession, ApplicationID );
            projectBO = (ProjectBO) projectDAO.get( pSession, projectID );

            // transformation from the DTO to the BO
            ProjectConfTransform.dto2Bo( pProjectConf, projectBO );

            // association of the project with the application
            projectBO.setParent( ApplicationBO );

            // creation of the project in the database
            projectBO = projectDAO.create( pSession, projectBO );

        }
        catch ( JrafDaoException e )
        {
            if ( projectBO == null )
            {
                LOG.error( FacadeMessages.getString( "facade.exception.projectfacade.existence.insert" ), e );
            }
            else
            {
                LOG.error( FacadeMessages.getString( "facade.exception.projectfacade.dao.insert" ), e );
            }
        }
        finally
        {
            FacadeHelper.closeSession( pSession, ProjectFacade.class.getName() + ".insert" );
        }

    }

    /**
     * Returns the list of attached projects to the quality grid with the name given in parameter
     * 
     * @param pQualityGridName the name of the quality grid
     * @return the list of attached projects
     * @throws JrafEnterpriseException if an error occurs
     */
    public static Collection findWhereQualityGrid( String pQualityGridName )
        throws JrafEnterpriseException
    {
        Collection result = new ArrayList( 0 );
        ISession session = null;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            Collection resultAux = ProjectDAOImpl.getInstance().findWhereQualityGrid( session, pQualityGridName );
            // Transformation of every projectBO into a componentDTO
            Iterator it = resultAux.iterator();
            while ( it.hasNext() )
            {
                result.add( ComponentTransform.bo2Dto( (ProjectBO) it.next() ) );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ProjectFacade.class.getName() + ".findWhereQualityGrid" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ProjectFacade.class.getName() + ".findWhereQualityGrid" );
        }
        return result;
    }

    /**
     * Empty constructor
     * 
     * @roseuid 42CBFFB103E3
     */
    private ProjectFacade()
    {
    }

    /**
     * Retrieves the project workspaces
     * 
     * @param pProjectId the Id of the project
     * @return The workspace of the project if it exists, null otherwise
     * @throws JrafEnterpriseException if an error occurs
     */
    public static String getProjectWorkspace( Long pProjectId )
        throws JrafEnterpriseException
    {
        // Initialisation
        String projectWorkspace = ""; // If there is no workspace, it is returned empty
        List workspaces = null;
        ISession session = null;
        ProjectDAOImpl projectDao = ProjectDAOImpl.getInstance();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            // The list of workspaces is retrieved
            ListParameterBO workspacesParams =
                (ListParameterBO) projectDao.getParameterWhere( session, pProjectId, ParametersConstants.WSAD );
            if ( null != workspacesParams )
            { 
                // The workspaces were found
                workspaces = workspacesParams.getParameters();
                // If there is only one, it will be chosen
                if ( 1 == workspaces.size() )
                {
                    projectWorkspace = ( (StringParameterBO) workspaces.get( 0 ) ).getValue();
                }
                else
                {
                    // The paths to the sources is retrieved
                    ListParameterBO sources =
                        (ListParameterBO) projectDao.getParameterWhere( session, pProjectId,
                                                                        ParametersConstants.SOURCES );
                    if ( null != sources )
                    { 
                        // The sources were found
                        // a get(0) is made without size verification because if it is empty an exception
                        // must be raised since it means that there is an error in the database
                        String source = ( (StringParameterBO) sources.getParameters().get( 0 ) ).getValue();
                        // The workspace of the project will be the one containing the sources
                        for ( int i = 0; i < workspaces.size() && projectWorkspace.length() == 0; i++ )
                        {
                            String curWsp = ( (StringParameterBO) workspaces.get( i ) ).getValue();
                            if ( source.startsWith( curWsp ) )
                            {
                                projectWorkspace = curWsp;
                            }
                        }
                    }
                }
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ProjectFacade.class.getName() + ".getProjectWorkspace" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ProjectFacade.class.getName() + ".getProjectWorkspace" );
        }
        return projectWorkspace;
    }

    /**
     * Verifies if the project can be exported to an IDE
     * 
     * @param pProjectId the projets ID
     * @return true if the export to an IDE is possible for the given projet
     * @throws JrafEnterpriseException if an error occurs
     */
    public static Boolean canBeExportedToIDE( Long pProjectId )
        throws JrafEnterpriseException
    {
        // Initialisation
        Boolean canExport = new Boolean( true );
        ISession session = null;
        ProjectDAOImpl projectDao = ProjectDAOImpl.getInstance();
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            // the project profile is retrieved
            ProjectBO project = (ProjectBO) projectDao.load( session, pProjectId );
            if ( null != project )
            { 
                // The project was found (defensive code)
                canExport = new Boolean( project.getProfile().getExportIDE() );
            }
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, ProjectFacade.class.getName() + ".canBeExportedToIDE" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ProjectFacade.class.getName() + ".canBeExportedToIDE" );
        }
        return canExport;
    }

    /**
     * Adds a Tag to the project
     * 
     * @param pSession the session
     * @param pProjectId the accessed project
     * @param pTag The tag that will be added
     * @throws JrafDaoException if an error occurs
     */
    public static void addTag( ISession pSession, Long pProjectId, TagDTO pTag )
        throws JrafDaoException
    {
        // the DAO is initialized
        ProjectDAOImpl projDAO = ProjectDAOImpl.getInstance();
        ProjectBO projBO = null;
        TagDAOImpl tagDAO = TagDAOImpl.getInstance();
        TagBO tagBO = null;
        // the project is retrieved
        projBO = (ProjectBO) projDAO.get( pSession, pProjectId );
        // the tag is retrieved
        tagBO = (TagBO) tagDAO.get( pSession, pTag.getId() );
        if ( null != projBO && null != tagBO )
        { 
            // defensive code
            projBO.addTag( tagBO );
            // The modified project is saved
            projDAO.save( pSession, projBO );
        }
    }

    /**
     * removes a tag from a project
     * 
     * @param pSession the current session
     * @param pProjectId project accessed
     * @param pTag The tag that will be removed from the project
     * @throws JrafDaoException if an error occurs
     */
    public static void removeTag( ISession pSession, Long pProjectId, TagDTO pTag )
        throws JrafDaoException
    {
        // The DAO is initialized
        ProjectDAOImpl projectDAO = ProjectDAOImpl.getInstance();
        ProjectBO projectBO = null;
        TagDAOImpl tagDAO = TagDAOImpl.getInstance();
        TagBO tagBO = null;
        // The project is retrieved
        projectBO = (ProjectBO) projectDAO.get( pSession, pProjectId );
        // The tag is retrieved
        tagBO = (TagBO) tagDAO.get( pSession, pTag.getId() );
        if ( null != projectBO && null != tagBO )
        {
            // defensive code
            projectBO.removeTag( tagBO );
            // The modified project is saved
            projectDAO.save( pSession, projectBO );
        }
    }
}
