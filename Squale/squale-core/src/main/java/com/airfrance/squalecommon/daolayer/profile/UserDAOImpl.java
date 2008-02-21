/*
 * Créé le 6 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.profile;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.daolayer.DAOMessages;
import com.airfrance.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.ProfileBO;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.UserBO;

/**
 * @author M400843
 */
public class UserDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static UserDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static {
        instance = new UserDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private UserDAOImpl() {
        initialize(UserBO.class);
        if (null == LOG) {
            LOG = LogFactory.getLog(UserDAOImpl.class);
        }
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static UserDAOImpl getInstance() {
        return instance;
    }

    /**
     * Permet de récupérer UserBO en fonction du matricule
     * @param pSession session Hibernate
     * @param pMatricule matricule de l'utilisateur
     * @return UserBO associé au matricule
     * @throws JrafDaoException exception DAO
     */
    public UserBO loadWithMatricule(ISession pSession, String pMatricule) throws JrafDaoException {
        UserBO user = null;
        String whereClause = "where ";
        whereClause += getAlias() + ".matricule = '" + pMatricule + "'";
        Collection col = findWhere(pSession, whereClause);
        if (col.size() != 1) {
            if (col.size() > 1) {
                String tab[] = { pMatricule };
                LOG.warn(DAOMessages.getString("user.many.matricule", tab));
            }
        } else {
            user = (UserBO) col.iterator().next();
        }
        return user;
    }

    /**
     * Permet de récupérer la liste des administrateurs SQUALE
     * @param pSession session Hibernate
     * @param needEmail un booléen indiquant si il faut prendre en compte
     * dans la requete le fait que le mail doit etre défini ou pas
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés
     * de l'envoi automatique d'email.
     * @return la liste des utilisateurs administrateur du portail
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereAdmin(ISession pSession, boolean needEmail, boolean pUnsubscribed) throws JrafDaoException {
        String whereClause = "where ";
        // profil admin ayant un email défini
        whereClause += getAlias() + ".defaultProfile.name = '" + ProfileBO.ADMIN_PROFILE_NAME + "'";
        if (needEmail) {
            whereClause += " AND " + getAlias() + ".email is not null";
        }
        if(!pUnsubscribed) {
            // On ne prend que les abonnés
            whereClause += " AND " + getAlias() + ".unsubscribed=false";
        }
        Collection ret = findWhere(pSession, whereClause);
        return ret;
    }

    /**
     * Permet de récupérer la liste des administrateurs SQUALE
     * @param pSession session Hibernate
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés
     * de l'envoi automatique d'email.
     * @return la liste des utilisateurs administrateur du portail
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereAdminAndHaveEmails(ISession pSession, boolean pUnsubscribed) throws JrafDaoException {
        return findWhereAdmin(pSession, true, pUnsubscribed);
    }

    /**
     * Récupère tous les utilisateurs qui sont assigné au projet
     * @param pSession la session
     * @param pApplication l'application
     * @return une collection d'utilisateurs
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereApplication(ISession pSession, ApplicationBO pApplication) throws JrafDaoException {
        Collection retUsers = null;
        if (null != pApplication) {
            String whereClause = "where ";
            whereClause += pApplication.getId() + " in indices(" + getAlias() + ".rights)";
            retUsers = findWhere(pSession, whereClause);
        } else {
            retUsers = new ArrayList();
        }
        return retUsers;
    }

    /**
     * Récupère tous les utilisateurs qui sont assigné au projet avec un profil particulier
     * @param pSession la session
     * @param pId l'id de l'application
     * @param pProfile profil recherché
     * @param needEmail un booléen indiquant si il faut prendre en compte
     * dans la requete le fait que le mail doit etre défini ou pas
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés
     * de l'envoi automatique d'email.
     * @return une collection d'utilisateurs
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereApplicationAndProfile(ISession pSession, Long pId, ProfileBO pProfile, boolean needEmail, boolean pUnsubscribed) throws JrafDaoException {
        Collection retUsers = null;
        if (null != pId) {
            String whereClause = "where ";
            whereClause += pId + " in indices(" + getAlias() + ".rights)" + " AND " + getAlias() + ".rights[" + pId + "].name='" + pProfile.getName() + "'";
            if (needEmail) {
                whereClause += " AND " + getAlias() + ".email is not null";
            }
            if(!pUnsubscribed) {
                // On ne prend pas les désabonnés
                whereClause += " AND " + getAlias() + ".unsubscribed=false";
            }
            retUsers = findWhere(pSession, whereClause);
        } else {
            retUsers = new ArrayList();
        }
        return retUsers;
    }

    /**
     * Permet de récupérer la liste des administrateurs SQUALE
     * @param pSession session Hibernate
     * @param pId l'id de l'application
     * @param pProfile profil recherché
     * @param pUnsubscribed true si on veut récupérer aussi les utilisateur qui se sont désabonnés
     * de l'envoi automatique d'email.
     * @return la liste des utilisateurs administrateur du portail
     * @throws JrafDaoException exception Dao
     */
    public Collection findWhereApplicationAndProfileAndHaveEmails(ISession pSession, Long pId, ProfileBO pProfile, boolean pUnsubscribed) throws JrafDaoException {
        return findWhereApplicationAndProfile(pSession, pId, pProfile, true, pUnsubscribed);
    }

}
