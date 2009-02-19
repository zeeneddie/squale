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
//Source file: D:\\CC_VIEWS\\SQUALE_V0_0_ACT\\SQUALE\\SRC\\squaleCommon\\src\\com\\airfrance\\squalecommon\\enterpriselayer\\applicationcomponent\\LoginApplicationComponentAccess.java
package com.airfrance.squalecommon.enterpriselayer.applicationcomponent.administration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.airfrance.jraf.commons.exception.JrafEnterpriseException;
import com.airfrance.jraf.provider.accessdelegate.DefaultExecuteComponent;
import com.airfrance.squalecommon.datatransfertobject.component.UserDTO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squalecommon.enterpriselayer.facade.component.UserFacade;

/**
 * <p>
 * Title : LoginApplicationComponentAccess.java
 * </p>
 * <p>
 * Description : Application component login
 * </p>
 * <p>
 * Copyright : Copyright (c) 2005
 * </p>
 * <p>
 * Company : AIRFRANCE
 * </p>
 */
public class LoginApplicationComponentAccess
    extends DefaultExecuteComponent
{

    /**
     * Permet d'authentifier une utilisateur au sein d'AF Vérifie aussi si l'utilisateur est administrateur portail
     * 
     * @param pUser objet UserDTO avec login et mot de passe à vérifier
     * @param pAdmin indique si l'utilisateur a un privilège administrateur
     * @return <code>true</code> si l'utilisateur est correctement authentifié, sinon <code>false</code>
     * @throws JrafEnterpriseException exception JRAF
     * @roseuid 42CBFC0001EE
     */
    public UserDTO verifyUser( UserDTO pUser, Boolean pAdmin )
        throws JrafEnterpriseException
    {

        UserDTO userDTO = null; // Initialisation du retour
        userDTO = UserFacade.getUser( pUser, pAdmin );
        return userDTO;
    }

    /**
     * This method authenticate a user if the user is authenticate this method return the user with all his information
     * in the userDTO if the user is not authenticate, the method return a null userDTO
     * 
     * @param pUser : userDTO containing only identifier and password
     * @return userDTO return a user with all his information if he is authenticated, otherwise the method return a null
     *         userDTO
     * @throws JrafEnterpriseException exception happened during the search in the data base
     */
    public UserDTO userAuthentication( UserDTO pUser )
        throws JrafEnterpriseException
    {

        UserDTO userDTO = null; // Initialisation du retour
        userDTO = UserFacade.getUserByMatriculeAndPassword( pUser );
        return userDTO;
    }

    /**
     * Permet de mettre à jour les informations d'un utilisateur Crée l'utilisateur dans la base de données le cas
     * échéant
     * 
     * @param pUser objet UserDTO avec nom et email éventuellementy mis à jour
     * @param pAdmin indique si l'utilisateur a un privilège administrateur
     * @return <code>true</code> si l'utilisateur est correctement authentifié, sinon <code>false</code>
     * @throws JrafEnterpriseException exception JRAF
     */
    public UserDTO createOrUpdateUser( UserDTO pUser, Boolean pAdmin )
        throws JrafEnterpriseException
    {

        UserDTO userDTO = null; // Initialisation du retour
        userDTO = UserFacade.createOrUpdateUser( pUser, pAdmin );
        return userDTO;
    }

    /**
     * Obtention des utilisateurs avec le profil administrateur
     * 
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return utilisateurs avec le profil administrateur
     * @throws JrafEnterpriseException en cas de pb JRAF
     */
    public Collection getAdminsWithEmails( Boolean pUnsubscribed )
        throws JrafEnterpriseException
    {
        return UserFacade.getAdminsWithEmails( pUnsubscribed.booleanValue() );
    }

    /**
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return les emails des utilisateurs avec le profil manager pour l'application concernée
     * @throws JrafEnterpriseException en cas de pb JRAF
     */
    public Collection getAdminsEmails( Boolean pUnsubscribed )
        throws JrafEnterpriseException
    {
        return getEmails( getAdminsWithEmails( pUnsubscribed ) );
    }

    /**
     * Obtention des utilisateurs avec le profil manager pour l'application
     * 
     * @param pId l'application concernée
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return utilisateurs avec le profil manager pour l'application concernée
     * @throws JrafEnterpriseException en cas de pb JRAF
     */
    public Collection getManagersWithEmails( Long pId, Boolean pUnsubscribed )
        throws JrafEnterpriseException
    {
        return UserFacade.getUsersWithEmails( pId, ProfileBO.MANAGER_PROFILE_NAME, pUnsubscribed.booleanValue() );
    }

    /**
     * @param pId l'application concernée
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return les emails des utilisateurs avec le profil manager pour l'application concernée
     * @throws JrafEnterpriseException en cas de pb JRAF
     */
    public Collection getManagersEmails( Long pId, Boolean pUnsubscribed )
        throws JrafEnterpriseException
    {
        return getEmails( getManagersWithEmails( pId, pUnsubscribed ) );
    }

    /**
     * Obtention des utilisateurs avec le profil manager pour l'application
     * 
     * @param pId l'application concernée
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return utilisateurs avec le profil manager pour l'application concernée
     * @throws JrafEnterpriseException en cas de pb JRAF
     */
    public Collection getReadersWithEmails( Long pId, Boolean pUnsubscribed )
        throws JrafEnterpriseException
    {
        return UserFacade.getUsersWithEmails( pId, ProfileBO.READER_PROFILE_NAME, pUnsubscribed.booleanValue() );
    }

    /**
     * @param pId l'application concernée
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return les emails des utilisateurs avec le profil manager pour l'application concernée
     * @throws JrafEnterpriseException en cas de pb JRAF
     */
    public Collection getReadersEmails( Long pId, Boolean pUnsubscribed )
        throws JrafEnterpriseException
    {
        return getEmails( getReadersWithEmails( pId, pUnsubscribed ) );
    }

    /**
     * récupère une liste d'emails sur une collection d'utilisateurs
     * 
     * @param collUsers la collection des utilisateurs
     * @return la liste des mails défini pour ces utilisateurs
     */
    private Collection getEmails( Collection collUsers )
    {
        Collection result = new ArrayList( 0 );
        Iterator it = collUsers.iterator();
        while ( it.hasNext() )
        {
            result.add( ( (UserDTO) it.next() ).getEmail() );
        }
        return result;
    }

    /**
     * This method returns a list of UserDTO whose IDs start by the given "idStart" parameter.
     * 
     * @param idStart the beginning of the user id
     * @return a collection of users whose IDs start by the given paramater
     * @throws JrafEnterpriseException en cas de pb JRAF
     */
    public Collection<UserDTO> getUsersWithIdStartingBy( String idStart )
        throws JrafEnterpriseException
    {
        return UserFacade.getUsersWithIdStartingBy( idStart );
    }

    /**
     * Constructeur par défaut
     * 
     * @roseuid 42CBFC00039C
     */
    public LoginApplicationComponentAccess()
    {
    }
}
