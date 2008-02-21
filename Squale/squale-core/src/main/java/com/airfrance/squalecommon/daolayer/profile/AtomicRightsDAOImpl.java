/*
 * Créé le 6 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.profile;

import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.profile.AtomicRightsBO;

/**
 * @author M400843
 *
 */
public class AtomicRightsDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static AtomicRightsDAOImpl instance = null;

    /** initialisation du singleton */
    static {
        instance = new AtomicRightsDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private AtomicRightsDAOImpl() {
        initialize(AtomicRightsBO.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static AtomicRightsDAOImpl getInstance() {
        return instance;
    }
}
