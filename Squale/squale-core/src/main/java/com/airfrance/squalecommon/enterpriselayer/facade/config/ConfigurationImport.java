package com.airfrance.squalecommon.enterpriselayer.facade.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ProjectDAOImpl;
import com.airfrance.squalecommon.daolayer.config.AuditFrequencyDAOImpl;
import com.airfrance.squalecommon.daolayer.config.Profile_DisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.config.ProjectProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.config.SourceManagementDAOImpl;
import com.airfrance.squalecommon.daolayer.config.StopTimeDAOImpl;
import com.airfrance.squalecommon.daolayer.config.TaskDAOImpl;
import com.airfrance.squalecommon.daolayer.config.web.AbstractDisplayConfDAOImpl;
import com.airfrance.squalecommon.daolayer.rule.QualityGridDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.config.ProjectProfileDTO;
import com.airfrance.squalecommon.datatransfertobject.config.SourceManagementDTO;
import com.airfrance.squalecommon.datatransfertobject.config.SqualixConfigurationDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.config.AuditFrequencyTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.ProjectProfileTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.SourceManagementTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.config.StopTimeTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AbstractTasksUserBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.Profile_DisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.ProjectProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SourceManagementBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.SqualixConfigurationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.StopTimeBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.web.AbstractDisplayConfBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityGridBO;
import com.airfrance.squalecommon.enterpriselayer.facade.config.xml.SqualixConfigImport;

/**
 * Importation de la configuration Squalix
 */
public class ConfigurationImport
{

    /**
     * provider de persistence
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Importation de la configuration sans sauvegarde dans la base.
     * 
     * @param pStream flux de configuration
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return configuration importée sous la forme de SqualixConfigurationDTO
     * @throws JrafEnterpriseException si erreur
     */
    public static SqualixConfigurationDTO importConfig( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        SqualixConfigurationDTO configDTO = new SqualixConfigurationDTO();
        // Importation de la configuration
        SqualixConfigImport configImport = new SqualixConfigImport();
        SqualixConfigurationBO configBO = configImport.importConfig( pStream, pErrors );
        // Transformation en DTO
        // Les temps d'arrêt
        Collection stopTimesDTO = StopTimeTransform.bo2dto( configBO.getStopTimes() );
        configDTO.setStopTimes( stopTimesDTO );
        // Les fréquences
        Collection frequenciesDTO = AuditFrequencyTransform.bo2dto( configBO.getFrequencies() );
        configDTO.setFrequencies( frequenciesDTO );
        // Les récupérateurs de source
        Collection managersDTO = SourceManagementTransform.bo2dto( configBO.getSourceManagements() );
        configDTO.setSourceManagements( managersDTO );
        // Les profils
        Collection profilesDTO = ProjectProfileTransform.bo2dto( configBO.getProfiles() );
        configDTO.setProfiles( profilesDTO );
        return configDTO;
    }

    /**
     * Créer la configuration Squalix d'après un fichier de configuration Squalix en supprimant l'ancienne
     * 
     * @param pStream flux de configuration
     * @param pErrors erreurs de traitement ou vide si aucune erreur n'est rencontrée
     * @return configuration importée sous la forme de SqualixConfigurationDTO
     * @throws JrafEnterpriseException si erreur
     */
    public static SqualixConfigurationDTO createConfig( InputStream pStream, StringBuffer pErrors )
        throws JrafEnterpriseException
    {
        SqualixConfigurationDTO configDTO = new SqualixConfigurationDTO();
        Collection stopTimesDTO = new ArrayList();
        Collection frequenciesDTO = new ArrayList();
        SqualixConfigImport configImport = new SqualixConfigImport();
        // Importation de la configuration
        SqualixConfigurationBO configBO = configImport.importConfig( pStream, pErrors );
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            // On créer la configuration en supprimant les précédents
            // paramètres généraux
            stopTimesDTO = createStopTimes( session, configBO.getStopTimes() );
            configDTO.setStopTimes( stopTimesDTO );
            // les fréquences max d'audits
            frequenciesDTO = createFrequencies( session, configBO.getFrequencies() );
            configDTO.setFrequencies( frequenciesDTO );
            // On supprime les sources manager qui n'existent pas dans la liste
            // des nouveaux sources manager
            SourceManagementDAOImpl managerDAO = SourceManagementDAOImpl.getInstance();
            Collection managersToRemove = managerDAO.findOthers( session, configBO.getSourceManagements() );
            boolean canDeleteManagers = canDeleteSourceManagements( session, managersToRemove, pErrors );
            // On supprime les profils qui n'existent pas dans la liste
            // des nouveaux profils
            ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
            Collection profilesToRemove = profileDAO.findOthers( session, configBO.getProfiles() );
            boolean canDeleteProfiles = canDeleteProfiles( session, profilesToRemove, pErrors );
            if ( canDeleteManagers && canDeleteProfiles )
            {
                // On supprime toutes les tâches:
                removeTasks();
                // On supprime les sources managers qui ne sont pas présents dans la liste:
                removeOtherManagers( configBO.getSourceManagements() );
                // On supprime les profils qui ne sont pas présents dans la liste:
                removeOtherProfiles( configBO.getProfiles() );
                // On créer (ou on met à jour) les nouveaux sources managers:
                Collection managersDTO = createManagers( session, configBO.getSourceManagements() );
                configDTO.setSourceManagements( managersDTO );
                // On créer (ou on met à jour) les nouveaux profils:
                Collection profilesDTO = createProfiles( session, pErrors, configBO.getProfiles() );
                configDTO.setProfiles( profilesDTO );
            }
        }
        catch ( Exception e )
        {
            if ( session != null )
            {
                String message = ConfigMessages.getString( "config.creation.error", new Object[] { e.getMessage() } );
                pErrors.append( message );
                pErrors.append( '\n' );
                session.rollbackTransactionWithoutClose();
            }
        }
        finally
        {
            FacadeHelper.closeSession( session, ConfigurationImport.class.getName() + ".createConfig" );
        }
        return configDTO;
    }

    /**
     * Crée ou met à jour les fréquences d'audits
     * 
     * @param pSession la session hibernate
     * @param pFrequenciesBO les fréquences à insérer dans la base
     * @return la liste des fréquences sous la forme DTO
     * @throws JrafDaoException si erreur
     */
    private static Collection createFrequencies( ISession pSession, Collection pFrequenciesBO )
        throws JrafDaoException
    {
        AuditFrequencyDAOImpl frequencyDAO = AuditFrequencyDAOImpl.getInstance();
        // On supprime les anciens
        frequencyDAO.removeAll( pSession );
        // On crée ou on met à jour les nouveaux
        Iterator it = pFrequenciesBO.iterator();
        while ( it.hasNext() )
        {
            frequencyDAO.save( pSession, (AuditFrequencyBO) it.next() );
        }
        return AuditFrequencyTransform.bo2dto( pFrequenciesBO );
    }

    /**
     * Supprime les profils qui ne sont pas présent dans le fichier de configuration mais qui le sont en base.
     * 
     * @param pProfiles la collection des profils qui doivent être présent en base
     * @throws JrafEnterpriseException si erreur
     */
    private static void removeOtherProfiles( Collection pProfiles )
        throws JrafEnterpriseException
    {
        ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
        ISession session = null;
        try
        {
            // Récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            profileDAO.removeOthers( session, pProfiles );
        }
        catch ( Exception e )
        {
            FacadeHelper.convertException( e, ConfigurationImport.class.getName() + ".removeOtherProfiles" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ConfigurationImport.class.getName() + ".removeOtherProfiles" );
        }

    }

    /**
     * Supprime les sources managers qui ne sont pas présent dans le fichier de configuration mais qui le sont en base.
     * 
     * @param pManagers la collection des sources managers qui doivent être présent en base
     * @throws JrafEnterpriseException si erreur
     */
    private static void removeOtherManagers( Collection pManagers )
        throws JrafEnterpriseException
    {
        SourceManagementDAOImpl managerDAO = SourceManagementDAOImpl.getInstance();
        ISession session = null;
        try
        {
            // Récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            managerDAO.removeOthers( session, pManagers );
        }
        catch ( Exception e )
        {
            FacadeHelper.convertException( e, ConfigurationImport.class.getName() + ".removeOtherManagers" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ConfigurationImport.class.getName() + ".removeOtherManagers" );
        }

    }

    /**
     * Vérifie si les sources managers de la collection ne sont pas référencés par un projet.
     * 
     * @param pSession la session hibernate
     * @param pManagersToDelete les sources managers à vérifier
     * @param pErrors pour récupérer les noms des managers référencés
     * @throws JrafDaoException si erreur
     * @return true si aucun source manager n'est référencé
     */
    private static boolean canDeleteSourceManagements( ISession pSession, Collection pManagersToDelete,
                                                       StringBuffer pErrors )
        throws JrafDaoException
    {
        boolean canDelete = true;
        ProjectDAOImpl componentDAO = ProjectDAOImpl.getInstance();
        Iterator it = pManagersToDelete.iterator();
        SourceManagementBO managerBO;
        Collection projects;
        while ( it.hasNext() )
        {
            managerBO = (SourceManagementBO) it.next();
            projects = componentDAO.findWhereSourceManager( pSession, new Long( managerBO.getId() ) );
            if ( projects.size() > 0 )
            { // Le source manager est référencé par un projet
                canDelete = false;
                String message =
                    ConfigMessages.getString( "sourcemanagement.used", new Object[] { managerBO.getName() } );
                pErrors.append( message );
                pErrors.append( '\n' );
            }
        }
        return canDelete;
    }

    /**
     * Vérifie si les profils de la collection ne sont pas référencés par un projet.
     * 
     * @param pSession la session hibernate
     * @param pProfilesToDelete les profils à vérifier
     * @param pErrors pour récupérer les noms des profils référencés
     * @throws JrafDaoException si erreur
     * @return true si aucun profil n'est référencé
     */
    private static boolean canDeleteProfiles( ISession pSession, Collection pProfilesToDelete, StringBuffer pErrors )
        throws JrafDaoException
    {
        boolean canDelete = true;
        ProjectDAOImpl componentDAO = ProjectDAOImpl.getInstance();
        Iterator it = pProfilesToDelete.iterator();
        ProjectProfileBO profileBO;
        Collection projects;
        while ( it.hasNext() )
        {
            profileBO = (ProjectProfileBO) it.next();
            projects = componentDAO.findWhereProfile( pSession, new Long( profileBO.getId() ) );
            if ( projects.size() > 0 )
            { // Le profil est référencé par un projet
                canDelete = false;
                String message = ConfigMessages.getString( "profile.used", new Object[] { profileBO.getName() } );
                pErrors.append( message );
                pErrors.append( '\n' );
            }
        }
        return canDelete;
    }

    /**
     * Supprime toutes les tâches de la base
     * 
     * @throws JrafEnterpriseException si erreur
     */
    private static void removeTasks()
        throws JrafEnterpriseException
    {
        ISession session = null;
        try
        {
            // Récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            // Suppression de toutes les tâches asociées aux sources managers
            SourceManagementDAOImpl managerDAO = SourceManagementDAOImpl.getInstance();
            // On récupère tous les managers en base et pour chacun on supprime ses tâches
            Collection managers = managerDAO.findAll( session );
            removeAllTasks( managers );
            // Suppression de toutes les tâches asociées aux profils
            ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
            // On récupère tous les profils en base et pour chacun on supprime ses tâches
            Collection profiles = profileDAO.findAll( session );
            removeAllTasks( profiles );
            // Suppression des parents
            TaskDAOImpl taskDAO = TaskDAOImpl.getInstance();
            taskDAO.removeAllTasks( session );
        }
        catch ( Exception e )
        {
            FacadeHelper.convertException( e, ConfigurationImport.class.getName() + ".removeTasks" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ConfigurationImport.class.getName() + ".removeTasks" );
        }

    }

    /**
     * Supprime toutes les tâches associées aux utilisateurs de tâches.
     * 
     * @param pTasksUsers les utilisateurs de tâches
     */
    private static void removeAllTasks( Collection pTasksUsers )
    {
        AbstractTasksUserBO taskUser = null;
        // On parcours toutes la collection
        for ( Iterator it = pTasksUsers.iterator(); it.hasNext(); )
        {
            taskUser = (AbstractTasksUserBO) it.next();
            // suppression de toutes les tâches d'analyse
            taskUser.getAnalysisTasks().clear();
            // suppression de toutes les tâches de terminaison
            taskUser.getTerminationTasks().clear();
        }
    }

    /**
     * Crée ou met à jour les dates limites
     * 
     * @param pSession la session hibernate
     * @param pStopTimesBO les dates limites à insérer dans la base
     * @return la liste des dates limites sous la forme DTO
     * @throws JrafDaoException si erreur
     */
    private static Collection createStopTimes( ISession pSession, Collection pStopTimesBO )
        throws JrafDaoException
    {
        StopTimeDAOImpl stopTimeDAO = StopTimeDAOImpl.getInstance();
        // On supprime les anciens
        stopTimeDAO.removeAll( pSession );
        // On sauvegarde en base
        Iterator it = pStopTimesBO.iterator();
        while ( it.hasNext() )
        {
            stopTimeDAO.save( pSession, (StopTimeBO) it.next() );
        }
        return StopTimeTransform.bo2dto( pStopTimesBO );
    }

    /**
     * Crée ou met à jour les profils Squalix ainsi que leur tâches associées
     * 
     * @param pSession la session hibernate
     * @param pErrors le buffer des erreurs
     * @param pProfiles la liste des profils Squalix a créer ou à mettre à jour
     * @return la liste des profils crées sous forme DTO
     * @throws JrafDaoException si erreur
     * @throws JrafEnterpriseException si erreur
     */
    private static Collection createProfiles( ISession pSession, StringBuffer pErrors, Collection pProfiles )
        throws JrafDaoException, JrafEnterpriseException
    {
        Collection profilesDTO = new ArrayList();
        ProjectProfileDTO profileDTO = null;
        ProjectProfileBO profileBO = null;
        ProjectProfileBO existingProfileBO = null;
        ProjectProfileDAOImpl profileDAO = ProjectProfileDAOImpl.getInstance();
        // On supprime les liens profils-configuration
        Profile_DisplayConfDAOImpl.getInstance().removeAll( pSession );
        Iterator profilesIterator = pProfiles.iterator();
        while ( profilesIterator.hasNext() )
        {
            profileBO = (ProjectProfileBO) profilesIterator.next();
            // On vérifie que le profil n'existe pas déjà:
            existingProfileBO = profileDAO.findWhereName( pSession, profileBO.getName() );
            // On récupère les grilles
            profileBO.setGrids( getQualityGrids( pSession, profileBO.getGrids(), pErrors ) );
            try
            {
                if ( existingProfileBO != null )
                {
                    // Un profil porte déjà ce nom, on l'update:
                    existingProfileBO.setAnalysisTasks( profileBO.getAnalysisTasks() );
                    existingProfileBO.setTerminationTasks( profileBO.getTerminationTasks() );
                    existingProfileBO.setExportIDE( profileBO.getExportIDE() );
                    existingProfileBO.setGrids( profileBO.getGrids() );
                    profileDAO.save( pSession, existingProfileBO );
                    profileDTO = (ProjectProfileDTO) ProjectProfileTransform.bo2dto( existingProfileBO );
                    existingProfileBO.setProfileDisplayConfs( profileBO.getProfileDisplayConfs() );
                }
                else
                { // On le crée
                    existingProfileBO = profileBO;
                    profileDAO.create( pSession, existingProfileBO );
                    profileDTO = (ProjectProfileDTO) ProjectProfileTransform.bo2dto( profileBO );
                }
                // On fait un traitement particulier pour les configurations car on ne supprime
                // pas les configurations lors d'un update
                checkDisplayConfiguration( existingProfileBO );
                profileDAO.save( pSession, existingProfileBO );
                profilesDTO.add( profileDTO );
            }
            catch ( JrafDaoException e )
            {
                throw new JrafDaoException( "Le profil " + profileBO.getName()
                    + " n'a pas pu etre enregistré (erreur en base de données)" );
            }
        }
        return profilesDTO;
    }

    /**
     * @param pSession la session
     * @param pGrids les grilles avec usiquement le nom de renseigné
     * @param pErrors le buffer des erreurs
     * @return les grilles récupérées en base
     * @throws JrafDaoException si erreur Jraf
     */
    private static Set getQualityGrids( ISession pSession, Set pGrids, StringBuffer pErrors )
        throws JrafDaoException
    {
        Set persistent_grids = new HashSet( pGrids.size() );
        // On va récupérer les grilles une par une pour repérer les cas d'erreur
        for ( Iterator it = pGrids.iterator(); it.hasNext(); )
        {
            String gridName = ( (QualityGridBO) it.next() ).getName();
            QualityGridBO grid = QualityGridDAOImpl.getInstance().findWhereName( pSession, gridName );
            if ( null == grid )
            {
                // On affichera le message d'erreur dans squaleWeb car le fichier de configuration
                // est incorrect
                String message = ConfigMessages.getString( "grid.not_found", new Object[] { gridName } );
                pErrors.append( message );
                pErrors.append( '\n' );
            }
            else
            {
                persistent_grids.add( grid );
            }
        }
        return persistent_grids;
    }

    /**
     * Rend les configurations du profil persistantes
     * 
     * @param profileBO le profile à sauvegarder
     * @throws JrafDaoException si erreur
     * @throws JrafEnterpriseException si erreur
     */
    private static void checkDisplayConfiguration( ProjectProfileBO profileBO )
        throws JrafDaoException, JrafEnterpriseException
    {
        // Initialisation
        ISession session = null;
        Profile_DisplayConfDAOImpl profilConfDAO = Profile_DisplayConfDAOImpl.getInstance();
        // On récupère les configurations liées au profil
        Set confs = profileBO.getProfileDisplayConfs();
        // Initialisation du retour
        Set persistentConfs = new HashSet( confs.size() );
        // Pour chaque configuration, on vérifie qu'elle n'existe pas déjà.
        AbstractDisplayConfDAOImpl dao = AbstractDisplayConfDAOImpl.getInstance();
        // Si elle existe, on la rècupère et on l'affecte au profil
        // Sinon on la crée
        // Ce traitement permet de ne jamais supprimer une configuration tout en mettant
        // à jour les liens avec le profil.
        Profile_DisplayConfBO profile_conf;
        AbstractDisplayConfBO confBO = null;
        List confBOInDb;
        try
        {
            session = PERSISTENTPROVIDER.getSession();
            for ( Iterator it = confs.iterator(); it.hasNext(); )
            {
                profile_conf = (Profile_DisplayConfBO) it.next();
                profile_conf.setProfile( profileBO );
                confBO = profile_conf.getDisplayConf();
                confBOInDb = dao.findAllSubclass( session, confBO.getClass() );
                if ( confBOInDb.contains( confBO ) )
                {
                    // La configuration existe en base, on l'ajoute directement
                    profile_conf.setDisplayConf( (AbstractDisplayConfBO) confBOInDb.get( confBOInDb.indexOf( confBO ) ) );
                }
                else
                {
                    // On le crée
                    dao.create( session, confBO );
                    profile_conf.setDisplayConf( confBO );
                }
                // On crée le lien entre le profil et la configuration
                profilConfDAO.save( session, profile_conf );
                // On l'ajoute au profil
                persistentConfs.add( profile_conf );
            }
            profileBO.setProfileDisplayConfs( persistentConfs );
        }
        catch ( Exception e )
        {
            FacadeHelper.convertException( e, ConfigurationImport.class.getName() + ".checkDisplayConfiguration" );
        }
        finally
        {
            FacadeHelper.closeSession( session, ConfigurationImport.class.getName() + ".checkDisplayConfiguration" );
        }
    }

    /**
     * Crée les récupérateurs de sources ainsi que leurs tâches associées
     * 
     * @param pSession la session hibernate
     * @param pManagers la liste des récupérateurs de sources à créer
     * @return les récupérateurs de sources crées sous forme DTO
     * @throws JrafDaoException si erreur
     * @throws JrafEnterpriseException si erreur
     */
    private static Collection createManagers( ISession pSession, Collection pManagers )
        throws JrafDaoException, JrafEnterpriseException
    {
        Collection managersDTO = new ArrayList();
        SourceManagementDTO managerDTO = null;
        SourceManagementBO managerBO = null;
        SourceManagementBO existingManagerBO = null;
        SourceManagementDAOImpl managerDAO = SourceManagementDAOImpl.getInstance();
        Iterator managersIterator = pManagers.iterator();
        while ( managersIterator.hasNext() )
        {
            managerBO = (SourceManagementBO) managersIterator.next();
            // On vérifie que le manager n'existe pas déjà:
            existingManagerBO = managerDAO.findWhereName( pSession, managerBO.getName() );
            if ( existingManagerBO != null )
            {
                // Un récupérateur de source porte déjà ce nom, on l'update:
                existingManagerBO.setAnalysisTasks( managerBO.getAnalysisTasks() );
                existingManagerBO.setTerminationTasks( managerBO.getTerminationTasks() );
                managerDAO.save( pSession, existingManagerBO );
                managerDTO = (SourceManagementDTO) SourceManagementTransform.bo2dto( existingManagerBO );
            }
            else
            { // On le crée
                managerDAO.create( pSession, managerBO );
                managerDTO = (SourceManagementDTO) SourceManagementTransform.bo2dto( managerBO );
            }
            managersDTO.add( managerDTO );
        }
        return managersDTO;
    }
}
