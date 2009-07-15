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
/*
 * Créé le 6 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.profile;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;

public class UserDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static UserDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new UserDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private UserDAOImpl()
    {
        initialize( UserBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( UserDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static UserDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Permet de récupérer UserBO en fonction du matricule
     * 
     * @param pSession session Hibernate
     * @param pMatricule matricule de l'utilisateur
     * @return UserBO associé au matricule
     * @throws JrafDaoException exception DAO
     */
    public UserBO loadWithMatricule( ISession pSession, String pMatricule )
        throws JrafDaoException
    {
        UserBO user = null;
        String whereClause = "where ";
        whereClause += "lower(" + getAlias() + ".matricule) like lower('" + pMatricule + "')";
        Collection col = findWhere( pSession, whereClause );
        if ( col.size() != 1 )
        {
            if ( col.size() > 1 )
            {
                String tab[] = { pMatricule };
                LOG.warn( DAOMessages.getString( "user.many.matricule", tab ) );
            }
        }
        else
        {
            user = (UserBO) col.iterator().next();
        }
        return user;
    }

    /**
     * This method permit to search a user in the database according to its identifier and its password. If a user with
     * the identifier and the password, the method return this user. If the method found nothing it return a null user
     * 
     * @param pSession Hibernate session
     * @param pMatricule user identifier
     * @param pPassword user password
     * @return UserBO return the user found in the data base or null if it not found
     * @throws JrafDaoException exception happened during the search in the data base
     */
    public UserBO loadWithMatriculeAndPassword( ISession pSession, String pMatricule, String pPassword )
        throws JrafDaoException
    {
        UserBO user = null;
        String whereClause = "where ";
        whereClause +=
            "lower(" + getAlias() + ".matricule) like lower('" + pMatricule + "') AND " + getAlias() + ".password = '"
                + pPassword + "'";
        Collection col = findWhere( pSession, whereClause );
        if ( col.size() != 1 )
        {
            if ( col.size() > 1 )
            {
                String tab[] = { pMatricule };
                LOG.warn( DAOMessages.getString( "user.many.matricule", tab ) );
            }
            else if ( col.size() == 0 )
            {
                user = new UserBO();
            }
        }
        else
        {
            user = (UserBO) col.iterator().next();
        }
        return user;
    }

    /**
     * Permet de récupérer la liste des administrateurs SQUALE
     * 
     * @param pSession session Hibernate
     * @param needEmail un booléen indiquant si il faut prendre en compte dans la requete le fait que le mail doit etre
     *            défini ou pas
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return la liste des utilisateurs administrateur du portail
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereAdmin( ISession pSession, boolean needEmail, boolean pUnsubscribed )
        throws JrafDaoException
    {
        String whereClause = "where ";
        // profil admin ayant un email défini
        whereClause += getAlias() + ".defaultProfile.name = '" + ProfileBO.ADMIN_PROFILE_NAME + "'";
        if ( needEmail )
        {
            whereClause += " AND " + getAlias() + ".email is not null";
        }
        if ( !pUnsubscribed )
        {
            // On ne prend que les abonnés
            whereClause += " AND " + getAlias() + ".unsubscribed=false";
        }
        Collection ret = findWhere( pSession, whereClause );
        return ret;
    }

    /**
     * Permet de récupérer la liste des administrateurs SQUALE
     * 
     * @param pSession session Hibernate
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return la liste des utilisateurs administrateur du portail
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereAdminAndHaveEmails( ISession pSession, boolean pUnsubscribed )
        throws JrafDaoException
    {
        return findWhereAdmin( pSession, true, pUnsubscribed );
    }

    /**
     * Récupère tous les utilisateurs qui sont assigné au projet
     * 
     * @param pSession la session
     * @param pApplication l'application
     * @return une collection d'utilisateurs
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereApplication( ISession pSession, ApplicationBO pApplication )
        throws JrafDaoException
    {
        Collection retUsers = null;
        if ( null != pApplication )
        {
            retUsers = findWhere( pSession, getWhereApplicationClause( pApplication.getId() ) );

        }
        else
        {
            retUsers = new ArrayList();
        }
        return retUsers;
    }

    /**
     * @param session hivernate session
     * @param pAppliId application's id
     * @return number of users declared for this application
     * @throws JrafDaoException if error
     */
    public int countWhereApplication( ISession session, long pAppliId )
        throws JrafDaoException
    {
        return countWhere( session, getWhereApplicationClause( pAppliId ) ).intValue();
    }

    /**
     * @param pAppliId application's id
     * @return where clause for query with application id as condition
     */
    private String getWhereApplicationClause( long pAppliId )
    {
        StringBuffer whereClause = new StringBuffer( "where " );
        whereClause.append( pAppliId );
        whereClause.append( " in indices(" );
        whereClause.append( getAlias() );
        whereClause.append( ".rights)" );
        return whereClause.toString();
    }

    /**
     * Récupère tous les utilisateurs qui sont assigné au projet avec un profil particulier
     * 
     * @param pSession la session
     * @param pId l'id de l'application
     * @param pProfile profil recherché
     * @param needEmail un booléen indiquant si il faut prendre en compte dans la requete le fait que le mail doit etre
     *            défini ou pas
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return une collection d'utilisateurs
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereApplicationAndProfile( ISession pSession, Long pId, ProfileBO pProfile,
                                                      boolean needEmail, boolean pUnsubscribed )
        throws JrafDaoException
    {
        Collection retUsers = null;
        if ( null != pId )
        {
            String whereClause = "where ";
            whereClause +=
                pId + " in indices(" + getAlias() + ".rights)" + " AND " + getAlias() + ".rights[" + pId + "].name='"
                    + pProfile.getName() + "'";
            if ( needEmail )
            {
                whereClause += " AND " + getAlias() + ".email is not null";
            }
            if ( !pUnsubscribed )
            {
                // On ne prend pas les désabonnés
                whereClause += " AND " + getAlias() + ".unsubscribed=false";
            }
            retUsers = findWhere( pSession, whereClause );
        }
        else
        {
            retUsers = new ArrayList();
        }
        return retUsers;
    }

    /**
     * Permet de récupérer la liste des administrateurs SQUALE
     * 
     * @param pSession session Hibernate
     * @param pId l'id de l'application
     * @param pProfile profil recherché
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés de l'envoi
     *            automatique d'email.
     * @return la liste des utilisateurs administrateur du portail
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereApplicationAndProfileAndHaveEmails( ISession pSession, Long pId, ProfileBO pProfile,
                                                                   boolean pUnsubscribed )
        throws JrafDaoException
    {
        return findWhereApplicationAndProfile( pSession, pId, pProfile, true, pUnsubscribed );
    }

    /**
     * This method returns a list of UserBO whose IDs start by the given "idStart" parameter.
     * 
     * @param session session Hibernate
     * @param idStart the beginning of the user id
     * @return a collection of users whose IDs start by the given paramater
     * @throws JrafDaoException exception Dao
     */
    public Collection<UserBO> findWhereMatriculeStartsWith( ISession session, String idStart )
        throws JrafDaoException
    {
        Collection<UserBO> retUsers = null;
        if ( StringUtils.isNotEmpty( idStart ) )
        {
            String whereClause = "where ";
            whereClause += getAlias() + ".matricule like '" + idStart + "%'";
            retUsers = findWhere( session, whereClause );
        }
        else
        {
            retUsers = new ArrayList<UserBO>();
        }
        return retUsers;
    }

}
