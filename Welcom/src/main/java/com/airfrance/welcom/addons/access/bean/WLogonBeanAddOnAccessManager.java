/*
 * Créé le 21 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.access.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.addons.access.exception.ProfileException;
import com.airfrance.welcom.outils.Access;
import com.airfrance.welcom.struts.bean.WILogonBeanSecurity;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WLogonBeanAddOnAccessManager implements WILogonBeanSecurity, Serializable {

    /** ID pour la serialization */
    static final long serialVersionUID = 540028229416425555L;

    /** logger */
    private static Log log = LogFactory.getLog(WLogonBeanAddOnAccessManager.class);

    /** Droit pour l'utilisateur */
    private HashMap htDroits = null;

    /** Liste des profils de l'utilisateur ... compatibilité LDAP ;) */
    private ArrayList listeProfile = null;

    /** Memorise si doit recharger le access */
    private boolean mustComputeAccess = false;

    /** Nom de l'utilisateur */
    private String userName = "";

    /** Droit par default si n'est pas dans la BD */
    private String defaultAccess = Access.NONE;

    /**
     * Gestion deu droit d'accés
     * @see com.airfrance.welcom.struts.bean.WILogonBeanSecurity#getAccess(java.lang.String)
     */
    public final String getAccess(final String accessKey) {

        if (mustComputeAccess == true) {
            computeAccess();
            mustComputeAccess = false;
        }

        // Regarde dans la tablea de Hashage
        if (htDroits.containsKey(accessKey)) {
            return ((AccessBean) htDroits.get(accessKey)).getValue();
        } else {
            return getDefaultAccess();
        }

    }

    /** 
     * Recharge les access
     */
    public void reloadAccess() {
        htDroits = null;
        if ((listeProfile != null) && (listeProfile.size() > 0)) {
            final Iterator iter = listeProfile.iterator();
            while (iter.hasNext()) {
                final ProfileBean profile = (ProfileBean) iter.next();
                profile.reloadAccessList();
            }
        }
        mustComputeAccess = true;
    }

    /**
     * Initialiste les droist d'accés
     *
     */
    protected void computeAccess() {
        htDroits = new HashMap();
        AccessBean droits, internalDroits;

        final Iterator iter = listeProfile.iterator();
        while (iter.hasNext()) {
            final ProfileBean profile = (ProfileBean) iter.next();
            final ArrayList listAccess = profile.getAccessList();
            final Iterator iterAccess = listAccess.iterator();
            while (iterAccess.hasNext()) {
                droits = (AccessBean) iterAccess.next();
                internalDroits = (AccessBean) htDroits.get(droits.getAccesskey());
                if (internalDroits != null) {
                    // Si posséde deja le droit en memoire, merge les droits
                    droits.setValue(Access.getMaxAccess(internalDroits.getValue(), droits.getValue()));
                }
                htDroits.put(droits.getAccesskey(), droits);
            }
        }
    }

    /**
     * @deprecated
     * @return Retourne le nom du profil pour cet utilisateur en position 0
     */
    public ProfileBean getProfile() {
        try {
            return (ProfileBean) listeProfile.get(0);
        } catch (final ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * @return Retourne la liste des profils
     */
    public ArrayList getProfiles() {
        return listeProfile;
    }

    /**
     * @param pProfile profil pour cet utilisateur
     */
    //    public void setProfile(ProfileBean pProfile) {
    //        profile = pProfile;
    //    }

    /**
     * Spécifie le profil de l'utilisateur .. 1 profil = 1 user.
     * RAZ des profils ...
     * @param string nom du profil pour cet utilisateur
     * @throws ProfileException Erreur à la recuperation du profil 
     */
    public void setIdProfile(final String string) throws ProfileException {
        try {
            listeProfile = new ArrayList();
            listeProfile.add(ProfileBean.getProfileBean(string));
        } catch (final Exception e) {
            log.error(e, e);
            throw new ProfileException(e);
        }
        mustComputeAccess = true;
    }

    /**
     * ajoute le profil de l'utilisateur .. n profil = 1 user.
     * @param idprofils tableau de profil pour cet utilisateur
     * @throws ProfileException Erreur à la recuperation du profil 
     */
    public void addIdProfile(final String[] idprofils) throws ProfileException {
        for (int i = 0; i < idprofils.length; i++) {
            addIdProfile(idprofils[i]);
        }
    }

    /**
     * ajoute le profil de l'utilisateur .. n profil = 1 user.
     * @param idprofil nom du profil pour cet utilisateur
     * @throws ProfileException Erreur à la recuperation du profil 
     */
    public void addIdProfile(final String idprofil) throws ProfileException {
        if (listeProfile == null) {
            listeProfile = new ArrayList();
        }
        try {
            listeProfile.add(ProfileBean.getProfileBean(idprofil));
        } catch (final Exception e) {
            log.error(e, e);
            throw new ProfileException(e);
        }
        mustComputeAccess = true;
    }

    /**
     * Recharge les profils ...
     */
    public void resetProfile() {
        listeProfile = null;
    }

    /**
     * @return Nom de l'utilisateur
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param string Nom de l'utilisateur
     */
    public void setUserName(final String string) {
        userName = string;
    }

    /**
     * @return Retourne le droit par defaut si non trouvé en BD (fichier excel)
     * READWRITE,READONLY,NONE
     */
    public String getDefaultAccess() {
        return defaultAccess;
    }

    /**
     * @param string droit par defaut si non trouvé en BD (fichier excel)
     * READWRITE,READONLY,NONE
     */
    public void setDefaultAccess(final String string) {
        defaultAccess = string;
    }

}
