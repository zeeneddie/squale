package com.airfrance.squalecommon.enterpriselayer.facade.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.config.AuditFrequencyDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.config.SourceManagementDAOImpl;
import com.airfrance.squalecommon.daolayer.config.StopTimeDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.config.AuditFrequencyTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.ProjectProfileTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.SourceManagementTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.StopTimeTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.TaskTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.TaskRefBO;

/**
 * Facade pour la configuration Squalix
 */
public class SqualixConfigFacade implements IFacade {

    /**
      * provider de persistence
      */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
      * Obtention de la configuration Squalix
      * @return la configuration Squalix
      * @throws JrafEnterpriseException si une erreur survient
      */
    public static SqualixConfigurationDTO getConfig() throws JrafEnterpriseException {
        SqualixConfigurationDTO result = new SqualixConfigurationDTO();
        ISession session = null;
        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            //On récupère les paramètres généraux:
            Collection stopTimesDTO = getStopTimes(session);
            result.setStopTimes(stopTimesDTO);
            Collection frequenciesDTO = getFrequencies(session);
            result.setFrequencies(frequenciesDTO);
            //On récupère les profils:
            Collection profilesDTO = getProfiles(session);
            result.setProfiles(profilesDTO);
            //On récupère les récupérateurs de sources:
            Collection managersDTO = getSourceManagements(session);
            result.setSourceManagements(managersDTO);
        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, SqualixConfigFacade.class.getName() + ".getConfig");
        } finally {
            FacadeHelper.closeSession(session, SqualixConfigFacade.class.getName() + ".getConfig");
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
    private static Collection getStopTimes(ISession pSession) throws JrafDaoException {
        StopTimeDAOImpl stopTimeDAO = StopTimeDAOImpl.getInstance();
        Collection stopTimesBO = stopTimeDAO.findAll(pSession);
        Collection stopTimesDTO = StopTimeTransform.bo2dto(stopTimesBO);
        return stopTimesDTO;
    }

    /**
     * Récupère les fréquences d'audit
     * 
     * @param pSession la session hibernate
     * @return lles fréquences d'audit sous forme DTO
     * @throws JrafDaoException si erreur
     */
    private static Collection getFrequencies(ISession pSession) throws JrafDaoException {
        AuditFrequencyDAOImpl stopTimeDAO = AuditFrequencyDAOImpl.getInstance();
        Collection frequenciesBO = stopTimeDAO.findAll(pSession);
        Collection frequencieDTO = AuditFrequencyTransform.bo2dto(frequenciesBO);
        return frequencieDTO;
    }

    /**
     * Récupère les profils courants
     * 
     * @return les profils sous forme DTO
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getProfiles() throws JrafEnterpriseException {
        ISession session = null;
        Collection result = new ArrayList();
        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            result = getProfiles(session);
        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, SqualixConfigFacade.class.getName() + ".getProfiles");
        } finally {
            FacadeHelper.closeSession(session, SqualixConfigFacade.class.getName() + ".getProfiles");
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
    private static Collection getProfiles(ISession pSession) throws JrafDaoException {
        ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
        Collection profilesBO = profileDAO.findProfiles(pSession);
        Collection profilesDTO = ProjectProfileTransform.bo2dto(profilesBO);
        return profilesDTO;
    }

    /**
     * Récupère les récupérateurs de sources courants
     * 
     * @return les récupérateurs de sources sous forme DTO
     * @throws JrafEnterpriseException si erreur
     */
    public static Collection getSourceManagements() throws JrafEnterpriseException {
        ISession session = null;
        Collection result = new ArrayList();
        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            result = getSourceManagements(session);
        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, SqualixConfigFacade.class.getName() + ".getSourceManagements");
        } finally {
            FacadeHelper.closeSession(session, SqualixConfigFacade.class.getName() + ".getSourceManagements");
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
    private static Collection getSourceManagements(ISession pSession) throws JrafDaoException {
        SourceManagementDAOImpl managerDAO = SourceManagementDAOImpl.getInstance();
        Collection managersBO = managerDAO.findSourceManagemements(pSession);
        Collection managersDTO = SourceManagementTransform.bo2dto(managersBO);
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
    public static Collection getConfigurableTasks(String pManagerName, String pProfileName) throws JrafEnterpriseException {
        ISession session = null;
        Collection result = new ArrayList();
        try {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            SourceManagementDAOImpl managerDAO = SourceManagementDAOImpl.getInstance();
            SourceManagementBO managerBO = (SourceManagementBO) managerDAO.findWhereName(session, pManagerName);
            result.addAll(getConfigurableTasks(managerBO.getAnalysisTasks()));
            result.addAll(getConfigurableTasks(managerBO.getTerminationTasks()));
            ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
            ProjectProfileBO profileBO = (ProjectProfileBO) profileDAO.findWhereName(session, pProfileName);
            result.addAll(getConfigurableTasks(profileBO.getAnalysisTasks()));
            result.addAll(getConfigurableTasks(profileBO.getTerminationTasks()));
        } catch (JrafDaoException e) {
            FacadeHelper.convertException(e, SqualixConfigFacade.class.getName() + ".getConfigurableTasks");
        } finally {
            FacadeHelper.closeSession(session, SqualixConfigFacade.class.getName() + ".getConfigurableTasks");
        }
        return result;
    }

     /**
     * Renvoit les tâches de la liste qui sont configurables
     * 
     * @param pTasks une liste de TaskBO
     * @return la collection des tâches configurables sous forme DTO
     */
    private static Collection getConfigurableTasks(List pTasks) {
        Collection tasks = new ArrayList();
        TaskRefBO task;
        for (int i = 0; i < pTasks.size(); i++) {
            task = (TaskRefBO) pTasks.get(i);
            if (task.getTask().isConfigurable()) {
                tasks.add(TaskTransform.bo2dto(task));
            }
        }
        return tasks;
    }
}
