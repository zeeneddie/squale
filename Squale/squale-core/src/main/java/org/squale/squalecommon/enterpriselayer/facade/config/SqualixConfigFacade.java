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
package org.squale.squalecommon.enterpriselayer.facade.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.commons.exception.JrafEnterpriseException;
import org.squale.jraf.helper.PersistenceHelper;
import org.squale.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import org.squale.jraf.spi.enterpriselayer.IFacade;
import org.squale.jraf.spi.persistence.IPersistenceProvider;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.daolayer.config.AdminParamsDAOImpl;
import org.squale.squalecommon.daolayer.config.AuditFrequencyDAOImpl;
import org.squale.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import org.squale.squalecommon.daolayer.config.SourceManagementDAOImpl;
import org.squale.squalecommon.daolayer.config.StopTimeDAOImpl;
import org.squale.squalecommon.datatransfertobject.config.AdminParamsDTO;
import org.squale.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import org.squale.squalecommon.datatransfertobject.transform.config.AdminParamsTransform;
import org.squale.squalecommon.datatransfertobject.transform.config.AuditFrequencyTransform;
import org.squale.squalecommon.datatransfertobject.transform.config.ProjectProfileTransform;
import org.squale.squalecommon.datatransfertobject.transform.config.SourceManagementTransform;
import org.squale.squalecommon.datatransfertobject.transform.config.StopTimeTransform;
import org.squale.squalecommon.datatransfertobject.transform.config.TaskTransform;
import org.squale.squalecommon.enterpriselayer.businessobject.config.AdminParamsBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import org.squale.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;

/**
 * Facade pour la configuration Squalix
 */
public class SqualixConfigFacade
    implements IFacade
{

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Obtention de la configuration Squalix
     * 
     * @return la configuration Squalix
     * @throws JrafEnterpriseException si une erreur survient
     */
    public static SqualixConfigurationDTO getConfig()
        throws JrafEnterpriseException
    {
        SqualixConfigurationDTO result = new SqualixConfigurationDTO();
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            // On récupère les paramètres généraux:
            Collection stopTimesDTO = getStopTimes( session );
            result.setStopTimes( stopTimesDTO );
            Collection frequenciesDTO = getFrequencies( session );
            result.setFrequencies( frequenciesDTO );
            // On récupère les profils:
            Collection profilesDTO = getProfiles( session );
            result.setProfiles( profilesDTO );
            // On récupère les récupérateurs de sources:
            Collection managersDTO = getSourceManagements( session );
            result.setSourceManagements( managersDTO );
            // Recovering of the adminParams
            Collection<AdminParamsDTO> adminParamsDTOCollection = getAdminParams( session );
            result.setAdminParams( adminParamsDTOCollection );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".getConfig" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".getConfig" );
        }

        return result;
    }

    /**
     * Récupère les dates limites courantes
     * 
     * @param pSession la session hibernate
     * @return les dates limites sous forme DTO
     * @throws JrafDaoException si erreur
     */
    private static Collection getStopTimes( ISession pSession )
        throws JrafDaoException
    {
        StopTimeDAOImpl stopTimeDAO = StopTimeDAOImpl.getInstance();
        Collection stopTimesBO = stopTimeDAO.findAll( pSession );
        Collection stopTimesDTO = StopTimeTransform.bo2dto( stopTimesBO );
        return stopTimesDTO;
    }

    /**
     * Récupère les fréquences d'audit
     * 
     * @param pSession la session hibernate
     * @return lles fréquences d'audit sous forme DTO
     * @throws JrafDaoException si erreur
     */
    private static Collection getFrequencies( ISession pSession )
        throws JrafDaoException
    {
        AuditFrequencyDAOImpl stopTimeDAO = AuditFrequencyDAOImpl.getInstance();
        Collection frequenciesBO = stopTimeDAO.findAll( pSession );
        Collection frequencieDTO = AuditFrequencyTransform.bo2dto( frequenciesBO );
        return frequencieDTO;
    }

    /**
     * Récupère les profils courants
     * 
     * @return les profils sous forme DTO
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getProfiles()
        throws JrafEnterpriseException
    {
        ISession session = null;
        Collection result = new ArrayList();
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            result = getProfiles( session );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".getProfiles" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".getProfiles" );
        }
        return result;
    }

    /**
     * Récupère les profils courants
     * 
     * @param pSession la session hibernate
     * @return les profils sous forme DTO
     * @throws JrafDaoException si erreur
     */
    private static Collection getProfiles( ISession pSession )
        throws JrafDaoException
    {
        ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
        Collection profilesBO = profileDAO.findProfiles( pSession );
        Collection profilesDTO = ProjectProfileTransform.bo2dto( profilesBO );
        return profilesDTO;
    }

    /**
     * Récupère les récupérateurs de sources courants
     * 
     * @return les récupérateurs de sources sous forme DTO
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getSourceManagements()
        throws JrafEnterpriseException
    {
        ISession session = null;
        Collection result = new ArrayList();
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            result = getSourceManagements( session );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".getSourceManagements" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".getSourceManagements" );
        }
        return result;
    }

    /**
     * Récupère les récupérateurs de sources courants
     * 
     * @param pSession la session hibernate
     * @return les récupérateurs de sources sous forme DTO
     * @throws JrafDaoException si erreur
     */
    private static Collection getSourceManagements( ISession pSession )
        throws JrafDaoException
    {
        SourceManagementDAOImpl managerDAO = SourceManagementDAOImpl.getInstance();
        Collection managersBO = managerDAO.findSourceManagemements( pSession );
        Collection managersDTO = SourceManagementTransform.bo2dto( managersBO );
        return managersDTO;
    }

    /**
     * Récupère la liste des tâches configurables
     * 
     * @param pManagerName le nom du récupérateur de source
     * @param pProfileName le nom du profil
     * @return les tâches configurables
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getConfigurableTasks( String pManagerName, String pProfileName )
        throws JrafEnterpriseException
    {
        ISession session = null;
        Collection result = new ArrayList();
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            SourceManagementDAOImpl managerDAO = SourceManagementDAOImpl.getInstance();
            SourceManagementBO managerBO = (SourceManagementBO) managerDAO.findWhereName( session, pManagerName );
            result.addAll( getConfigurableTasks( managerBO.getAnalysisTasks() ) );
            result.addAll( getConfigurableTasks( managerBO.getTerminationTasks() ) );
            ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
            ProjectProfileBO profileBO = (ProjectProfileBO) profileDAO.findWhereName( session, pProfileName );
            result.addAll( getConfigurableTasks( profileBO.getAnalysisTasks() ) );
            result.addAll( getConfigurableTasks( profileBO.getTerminationTasks() ) );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, SqualixConfigFacade.class.getName() + ".getConfigurableTasks" );
        }
        finally
        {
            FacadeHelper.closeSession( session, SqualixConfigFacade.class.getName() + ".getConfigurableTasks" );
        }
        return result;
    }

    /**
     * Renvoit les tâches de la liste qui sont configurables
     * 
     * @param pTasks une liste de TaskBO
     * @return la collection des tâches configurables sous forme DTO
     */
    private static Collection getConfigurableTasks( List pTasks )
    {
        Collection tasks = new ArrayList();
        TaskRefBO task;
        for ( int i = 0; i < pTasks.size(); i++ )
        {
            task = (TaskRefBO) pTasks.get( i );
            if ( task.getTask().isConfigurable() )
            {
                tasks.add( TaskTransform.bo2dto( task ) );
            }
        }
        return tasks;
    }
    
    /**
     * return the collection of adminParamsDTO set in the database
     * 
     * @param session hiberntae session
     * @return The collection of all adminParmas record in the database
     * @throws JrafDaoException Exception happened during the search
     */
    private static Collection<AdminParamsDTO> getAdminParams (ISession session) throws JrafDaoException
    {
        AdminParamsDAOImpl dao = AdminParamsDAOImpl.getInstance();
        Collection<AdminParamsBO> adminParamsCollection= dao.findAll( session );
        return AdminParamsTransform.bo2dto( adminParamsCollection );
    }
}
