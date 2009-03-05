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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\facade\\UserFacade.java
package com.airfrance.squalecommon.enterpriselayer.facade.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.facade.FacadeHelper;
import com.airfrance.jraf.spi.enterpriselayer.IFacade;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.component.ApplicationDAOImpl;
import com.airfrance.squalecommon.daolayer.profile.ProfileDAOImpl;
import com.airfrance.squalecommon.daolayer.profile.UserDAOImpl;
import com.airfrance.squalecommon.datatransfertobject.component.ProfileDTO;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ComponentTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.component.ProfileTransform;
import com.airfrance.squalecommon.datatransfertobject.transform.component.UserTransform;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;

/**
 */
public class UserFacade
    implements IFacade
{

    /**
     * Persistence provider
     */
    private static final IPersistenceProvider PERSISTENTPROVIDER = PersistenceHelper.getPersistenceProvider();

    /**
     * Constructeur vide
     * 
     * @roseuid 42CBFFB20158
     */
    private UserFacade()
    {
    }

    /**
     * retourne une collection de application pour un utilisateur donné
     * 
     * @param pUser utilisateur donné
     * @return Collection de applicationDTO (lastAudit ne sera pas instancié)
     * @throws JrafEnterpriseException exception JRAF
     * @deprecated l'utilisateur logué recoit l'objet UserDTO qui contient la liste de application.
     * @roseuid 42CBFFB2014C
     */
    public static Collection getApplicationList( UserDTO pUser )
        throws JrafEnterpriseException
    {

        // Initialisation du retour et des BO
        Collection applicationDTOs = null;
        UserBO userBO = new UserBO();
        Set keyCollection = null;

        ISession session = null;

        try
        {

            session = PERSISTENTPROVIDER.getSession();

            UserDAOImpl userDAO = UserDAOImpl.getInstance();

            userBO = (UserBO) userDAO.loadWithMatricule( session, pUser.getMatricule() );

            // Recuperation des cles qui sont des applicationBO
            keyCollection = ( userBO.getRights() ).keySet();

            // ajout au retour des BO transformes en DTO
            Iterator iterator = keyCollection.iterator();
            while ( iterator.hasNext() )
            {

                applicationDTOs.add( ComponentTransform.bo2Dto( (ApplicationBO) iterator.next() ) );

            }

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, UserFacade.class.getName() + ".getApplicationList" );
        }
        finally
        {
            FacadeHelper.closeSession( session, UserFacade.class.getName() + ".getApplicationList" );
        }

        return applicationDTOs;
    }

    /**
     * Retourne le UserDTO en renseignant l'identifiant
     * 
     * @param pUser utilisateur donné
     * @param pAdmin indique si le privilège admin est placé
     * @return UserDTO avec l'identifiant
     * @throws JrafEnterpriseException exception JRAF
     */
    public static UserDTO getUser( UserDTO pUser, Boolean pAdmin )
        throws JrafEnterpriseException
    {

        // Initialisation
        UserBO userBO = null; // retour de la DAO
        UserDTO userDTO = null; // retour de la facade

        ISession session = null;

        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();

            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            ProfileDAOImpl profileDAO = ProfileDAOImpl.getInstance();

            // traitement different si on initialise l'identifiant dans le UserDTO :
            // si l'id est connu, l'utilisateur est déjà authentifié, donc pas besoin de vérifier
            // son mot de passe
            if ( pUser.getID() < 0 )
            {
                // récupération du UserBO à partir du matricule
                userBO = (UserBO) userDAO.loadWithMatricule( session, pUser.getMatricule() );
            }
            else
            {
                // récupération du UserBO à partir de l'id
                Long userID = new Long( pUser.getID() );
                userBO = (UserBO) userDAO.get( session, userID );
            }

            // Traitement du cas du user guest : on affecte un profil en fonction
            // du privilège administrateur
            if ( userBO == null )
            {
                userBO = createDefaultUser( session, pUser.getMatricule(), pAdmin );
            }
            else
            {
                // We update the user profil (case of non admin gest who becomes an admin later)
                if ( pAdmin.booleanValue() && !ProfileBO.ADMIN_PROFILE_NAME.equals( userBO.getDefaultProfile() ) )
                {
                    userBO.setDefaultProfile( profileDAO.loadByKey( session, ProfileBO.ADMIN_PROFILE_NAME ) );
                    userDAO.save( session, userBO );
                }
            }

            // transformation du UserBO en UserDTO
            userDTO = UserTransform.bo2Dto( userBO );
            // ajout des droits de l'utilisateurs sur les différentes applications
            setRights( session, userDTO, userBO, pAdmin );

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, UserFacade.class.getName() + ".getApplicationList" );
        }
        finally
        {
            FacadeHelper.closeSession( session, UserFacade.class.getName() + ".getApplicationList" );
        }

        return userDTO;
    }

    /**
     * This method do the search of a user by his identifier and his password This method return in a userDTO all
     * information concerning the user who corresponding to the identifier and the password. if there is no user
     * corresponding, the method return a null userDTO.
     * 
     * @param pUser The userDTO with the identifier and the password inside
     * @return UserDTO the user found or null if he is not found
     * @throws JrafEnterpriseException exception intervened during the search of the user in the data base
     */
    public static UserDTO getUserByMatriculeAndPassword( UserDTO pUser )
        throws JrafEnterpriseException
    {

        UserBO userBO = null;
        UserDTO userDTO = null;

        ISession session = null;

        try
        {

            session = PERSISTENTPROVIDER.getSession();

            UserDAOImpl userDAO = UserDAOImpl.getInstance();

            userBO = (UserBO) userDAO.loadWithMatriculeAndPassword( session, pUser.getMatricule(), pUser.getPassword() );

            userDTO = UserTransform.bo2Dto( userBO );

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, UserFacade.class.getName() );
        }
        finally
        {
            FacadeHelper.closeSession( session, UserFacade.class.getName() );
        }

        return userDTO;
    }

    /**
     * This method do the search of a user by his identifier This method return in a userDTO all information concerning
     * the user who corresponding to the identifier . if there is no user corresponding, the method return a null
     * userDTO.
     * 
     * @param pUser The userDTO with the identifier
     * @return UserDTO the user found or null if he is not found
     * @throws JrafEnterpriseException exception intervened during the search of the user in the data base
     */
    public static UserDTO getUserByMatricule( UserDTO pUser )
        throws JrafEnterpriseException
    {

        UserBO userBO = null;
        UserDTO userDTO = null;

        ISession session = null;

        try
        {

            session = PERSISTENTPROVIDER.getSession();

            UserDAOImpl userDAO = UserDAOImpl.getInstance();

            userBO = (UserBO) userDAO.loadWithMatricule( session, pUser.getMatricule() );

            userDTO = UserTransform.bo2Dto( userBO );

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, UserFacade.class.getName() );
        }
        finally
        {
            FacadeHelper.closeSession( session, UserFacade.class.getName() );
        }

        return userDTO;
    }

    /**
     * Permet d'ajouter des droits au UserDTO Dans le cas d'un utilisateur avec un privilège admin, tous les
     * applications validés sont ajoutés à l'utilisateur
     * 
     * @param pSession session hibernate
     * @param pUserDTO UserDTO de destination
     * @param pUserBO UserBO contenant les droits
     * @param pAdmin indique si l'utilisateur a les droits admin
     * @return UserDTO renseigné entierement
     * @throws JrafEnterpriseException exception Jraf
     */
    private static UserDTO setRights( ISession pSession, UserDTO pUserDTO, UserBO pUserBO, Boolean pAdmin )
        throws JrafEnterpriseException
    {

        // Initialisation
        UserDTO userDTO = pUserDTO; // retour de la methode
        Map rights = new HashMap(); // map des droits sur un utilisateur
        List publicApplicationsBO = new ArrayList();
        List publicApplicationsDTO = new ArrayList();
        List applicationsInCreation = new ArrayList();
        ApplicationDAOImpl applicationDAO = ApplicationDAOImpl.getInstance(); // initialisation de Dao

        try
        {
            // Un administrateur a accès à toutes les applications confirmées
            if ( pAdmin.booleanValue() )
            {
                ApplicationBO rightKey = null;
                Long applicationId = null;
                Iterator applicationIterator = null;
                // on ne veut pas les applications à supprimer
                // Récupération de toutes les applications validées
                applicationIterator = applicationDAO.findWhereStatus( pSession, ApplicationBO.VALIDATED ).iterator();
                // Affectation des droits admin sur les applications validées
                fillRights( pSession, applicationIterator, ProfileBO.ADMIN_PROFILE_NAME, rights, null );
                // Récupération de toutes les applications a valider
                applicationIterator = applicationDAO.findWhereStatus( pSession, ApplicationBO.IN_CREATION ).iterator();
                // Affectation des droits pour l'utilisateur courant admin sur les applications à valider
                fillRights( pSession, applicationIterator, ProfileBO.ADMIN_PROFILE_NAME, rights, null );
            }
            else
            {
                // Initialisation de la map à partir des droits utilisateur
                // L'utilisation d'une map permet de surcharger si besoin les droits par défaut accordés
                // sur l'application publique
                Set rightBOs = pUserBO.getRights().keySet();
                Iterator applicationIterator = rightBOs.iterator();
                ApplicationBO rightKey = null;
                Long applicationId = null;
                // La liste des ids des applications appartenant à l'utilisateur
                String userAppli = "";
                while ( applicationIterator.hasNext() )
                {
                    // Chargement de l'application
                    applicationId = new Long( ( (ApplicationBO) applicationIterator.next() ).getId() );
                    rightKey = (ApplicationBO) applicationDAO.get( pSession, applicationId );
                    userAppli += ", " + applicationId;
                    // visibilité uniquement sur les applications validées ou en cours de création
                    if ( rightKey.getStatus() == ApplicationBO.VALIDATED )
                    {
                        rights.put( ComponentTransform.bo2Dto( rightKey ),
                                    ProfileTransform.bo2Dto( (ProfileBO) pUserBO.getRights().get( rightKey ) ) );
                    }
                    else if ( rightKey.getStatus() == ApplicationBO.IN_CREATION )
                    {
                        rights.put( ComponentTransform.bo2Dto( rightKey ),
                                    ProfileTransform.bo2Dto( (ProfileBO) pUserBO.getRights().get( rightKey ) ) );
                        applicationsInCreation.add( ComponentTransform.bo2Dto( rightKey ) );
                    }
                }
                // Recupération des applications publiques qui n'appartiennent pas à l'utilisateur
                if ( userAppli.length() > 0 )
                {
                    // On supprime le première virgule inutile
                    userAppli = userAppli.substring( 1 );
                }
                publicApplicationsBO = applicationDAO.findPublic( pSession, userAppli );
                applicationIterator = publicApplicationsBO.iterator();
                // Affectation des droits reader
                fillRights( pSession, applicationIterator, ProfileBO.READER_PROFILE_NAME, rights, publicApplicationsDTO );

            }
            userDTO.setProfiles( rights );
            userDTO.setApplicationsInCreation( applicationsInCreation );
            userDTO.setPublicApplications( publicApplicationsDTO );

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, UserFacade.class.getName() + ".setRights" );
        }

        return userDTO;
    }

    /**
     * Affectation des droits Le paramètre pRights est mis à jour pour contenir l'ensemble des applications avec leur
     * droit associé
     * 
     * @param pSession session
     * @param pApplicationIterator applications concernées
     * @param pProfile profil à utiliser
     * @param pRights droits à renseigner
     * @param pPublicAppli la liste des applications publiques ayant des résultats
     * @throws JrafDaoException si erreur
     */
    static private void fillRights( ISession pSession, Iterator pApplicationIterator, String pProfile, Map pRights,
                                    List pPublicAppli )
        throws JrafDaoException
    {
        // Affectation dans la Map pour chaque application du profil administrateur
        ProfileDAOImpl profileDAO = ProfileDAOImpl.getInstance();
        ProfileDTO adminProfile = ProfileTransform.bo2Dto( (ProfileBO) profileDAO.loadByKey( pSession, pProfile ) );
        ApplicationDAOImpl applicationDAO = ApplicationDAOImpl.getInstance();
        while ( pApplicationIterator.hasNext() )
        {
            // Chargement de l'application
            Long applicationId = new Long( ( (ApplicationBO) pApplicationIterator.next() ).getId() );
            ApplicationBO rightKey = (ApplicationBO) applicationDAO.get( pSession, applicationId );
            if ( rightKey.getStatus() != ApplicationBO.DELETED )
            {
                pRights.put( ComponentTransform.bo2Dto( rightKey ), adminProfile );
                // La liste des applications publiques peut être nulle dans le cas
                // où l'utilisateur est administrateur car dans ce cas on ne les différencie pas
                if ( pPublicAppli != null && rightKey.getPublic() )
                {
                    pPublicAppli.add( ComponentTransform.bo2Dto( rightKey ) );
                }
            }
        }
    }

    /**
     * Création ou mise à jour de l'utilisateur La création se fait avec un profil minimal, la mise à jour ne concerne
     * que l'adresse email ou le nom
     * 
     * @param pUser utilisateur donné
     * @param pAdmin indique si le privilège admin est placé
     * @return UserDTO avec l'identifiant
     * @throws JrafEnterpriseException exception JRAF
     */
    public static UserDTO createOrUpdateUser( UserDTO pUser, Boolean pAdmin )
        throws JrafEnterpriseException
    {
        // Initialisation
        UserBO userBO = null; // retour de la DAO
        UserDTO userDTO = null; // retour de la facade
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            // traitement different si on initialise l'identifiant dans le UserDTO :
            // si l'id est connu, l'utilisateur est déjà authentifié, donc pas besoin de vérifier
            // son mot de passe
            if ( pUser.getID() < 0 )
            {
                // récupération du UserBO à partir du matricule
                userBO = (UserBO) userDAO.loadWithMatricule( session, pUser.getMatricule() );
            }
            else
            {
                // récupération du UserBO à partir de l'id
                Long userID = new Long( pUser.getID() );
                userBO = (UserBO) userDAO.get( session, userID );
            }
            // Traitement du cas du user guest : on affecte un profil en fonction
            // du privilège administrateur
            if ( userBO == null )
            {
                userBO = createDefaultUser( session, pUser.getMatricule(), pAdmin );
            }
            // Mise à jour des infomations du BO en fonction du DTO
            userBO.setEmail( pUser.getEmail() );
            userBO.setFullName( pUser.getFullName() );
            userBO.setUnsubscribed( pUser.isUnsubscribed() );
            // Sauvegarde dans la base
            userDAO.save( session, userBO );
            // transformation du UserBO en UserDTO
            userDTO = UserTransform.bo2Dto( userBO );
            // ajout des droits de l'utilisateurs sur les différentes applications
            setRights( session, userDTO, userBO, pAdmin );
        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, UserFacade.class.getName() + ".getApplicationList" );
        }
        finally
        {
            FacadeHelper.closeSession( session, UserFacade.class.getName() + ".getApplicationList" );
        }
        return userDTO;
    }

    /**
     * Création d'un utilisateur par défaut L'utilisateur est crée avec le matricule passé en paramètre et le profil par
     * défaut en fonction du droit admin (soit admin soit default)
     * 
     * @param pSession session
     * @param pMatricule matricule
     * @param pAdmin indique si le profil admin est requis
     * @return user créé
     * @throws JrafDaoException si erreur
     */
    static private UserBO createDefaultUser( ISession pSession, String pMatricule, Boolean pAdmin )
        throws JrafDaoException
    {
        UserBO userBO = new UserBO();
        userBO.setMatricule( pMatricule );
        ProfileDAOImpl profileDAO = ProfileDAOImpl.getInstance();
        UserDAOImpl userDAO = UserDAOImpl.getInstance();
        ProfileBO defaultProfile;
        if ( pAdmin.booleanValue() )
        {
            defaultProfile = (ProfileBO) profileDAO.loadByKey( pSession, ProfileBO.ADMIN_PROFILE_NAME );
        }
        else
        {
            defaultProfile = (ProfileBO) profileDAO.loadByKey( pSession, ProfileBO.DEFAULT_PROFILE_NAME );
        }
        userBO.setDefaultProfile( defaultProfile );
        // Sauvegarde de l'utilisateur
        userDAO.save( pSession, userBO );
        return userBO;
    }

    /**
     * @param pId l'id de l'application
     * @param pProfile le profil utilisateur recherché pour cette application
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return l'ensemble des utilisateurs ayant <code>pProfile</code> comme droit sur l'application
     * @throws JrafEnterpriseException en cas d'échecs
     */
    public static Collection getUsersWithEmails( Long pId, String pProfile, boolean pUnsubscribed )
        throws JrafEnterpriseException
    {
        Collection result = new ArrayList(); // retour de la facade
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            // Obtention des users avec le profil pProfile sur l'application
            ProfileBO profileBO = ProfileDAOImpl.getInstance().loadByKey( session, pProfile );
            Collection applicationUsers =
                userDAO.findWhereApplicationAndProfileAndHaveEmails( session, pId, profileBO, pUnsubscribed );
            Iterator it = applicationUsers.iterator();
            // Transformation des userBO en DTO
            while ( it.hasNext() )
            {
                UserBO userBO = (UserBO) it.next();
                UserDTO userDTO = UserTransform.bo2Dto( userBO );
                result.add( userDTO );
            }
        }
        catch ( JrafDaoException e )
        {
            // Renvoi d'une exception
            FacadeHelper.convertException( e, UserFacade.class.getName() + ".getManagers" );
        }
        finally
        {
            // Fermeture de la session
            FacadeHelper.closeSession( session, UserFacade.class.getName() + ".getManagers" );
        }

        return result;
    }

    /**
     * Obtention des utilisateurs avec le profil administrateur
     * 
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return utilisateurs avec le profil administrateur ou ayant le profil manager sur l'application concernée
     * @throws JrafEnterpriseException si erreur
     */
    static public Collection getAdminsWithEmails( boolean pUnsubscribed )
        throws JrafEnterpriseException
    {
        Collection result = new ArrayList(); // retour de la facade
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            // Obtention des utilisateurs avec le profil admin
            Collection adminUsers = userDAO.findWhereAdminAndHaveEmails( session, pUnsubscribed );
            Iterator it = adminUsers.iterator();
            // transformation du UserBO en UserDTO
            while ( it.hasNext() )
            {
                UserDTO userDTO = UserTransform.bo2Dto( (UserBO) it.next() );
                result.add( userDTO );
            }
        }
        catch ( JrafDaoException e )
        {
            // Renvoi d'une exception
            FacadeHelper.convertException( e, UserFacade.class.getName() + ".getAdminUsers" );
        }
        finally
        {
            // Fermeture de la session
            FacadeHelper.closeSession( session, UserFacade.class.getName() + ".getAdminUsers" );
        }
        return result;
    }

    /**
     * This method returns a list of UserDTO whose IDs start by the given "idStart" parameter.
     * 
     * @param idStart the beginning of the user id
     * @return a collection of users whose IDs start by the given paramater
     * @throws JrafEnterpriseException en cas d'échecs
     */
    public static Collection<UserDTO> getUsersWithIdStartingBy( String idStart )
        throws JrafEnterpriseException
    {
        ArrayList<UserDTO> result = new ArrayList<UserDTO>();
        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            Collection<UserBO> foundUsers = userDAO.findWhereMatriculeStartsWith( session, idStart );
            for ( UserBO userBO : foundUsers )
            {
                result.add( UserTransform.bo2Dto( userBO ) );
            }
        }
        catch ( JrafDaoException e )
        {
            // Renvoi d'une exception
            FacadeHelper.convertException( e, UserFacade.class.getName() + ".getUsersWithIdStartingBy" );
        }
        finally
        {
            // Fermeture de la session
            FacadeHelper.closeSession( session, UserFacade.class.getName() + ".getUsersWithIdStartingBy" );
        }

        return result;
    }

    /**
     * Création ou mise à jour de l'utilisateur La création se fait avec un profil minimal, la mise à jour ne concerne
     * que l'adresse email ou le nom
     * 
     * @param pUser utilisateur donné
     * @throws JrafEnterpriseException exception JRAF
     */
    public static void removeUser( UserDTO pUser )
        throws JrafEnterpriseException
    {
        // Initialisation
        UserBO userBO = null; // retour de la DAO

        ISession session = null;
        try
        {
            // récupération d'une session
            session = PERSISTENTPROVIDER.getSession();
            UserDAOImpl userDAO = UserDAOImpl.getInstance();
            userBO = (UserBO) userDAO.loadWithMatricule( session, pUser.getMatricule() );
            userDAO.remove( session, userBO );

        }
        catch ( JrafDaoException e )
        {
            FacadeHelper.convertException( e, UserFacade.class.getName() + ".removeUser" );
        }
        finally
        {
            FacadeHelper.closeSession( session, UserFacade.class.getName() + ".removeUser" );
        }

    }
}
