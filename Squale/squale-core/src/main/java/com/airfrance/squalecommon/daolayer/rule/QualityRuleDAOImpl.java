/*
 * Créé le 29 juin 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.squalecommon.daolayer.rule;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.QualityRuleBO;

/**
 * @author M400843
 */
public class QualityRuleDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static QualityRuleDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new QualityRuleDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private QualityRuleDAOImpl()
    {
        initialize( QualityRuleBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static QualityRuleDAOImpl getInstance()
    {
        return instance;
    }
}
