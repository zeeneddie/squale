/*
 * Créé le 19 juil. 05
 *
 */
package com.airfrance.squalecommon.daolayer.result.mccabe;

import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.result.mccabe.McCabeQAMetricsBO;

/**
 * @author M400843
 *
 */
public class McCabeMetricsDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static McCabeMetricsDAOImpl instance = null;
    
    /**
     * log
     */
//    private static final ILogger LOG = LoggingHelper.getInstance(McCabeMetricsDAOImpl.class);

    /** initialisation du singleton */
    static {
        instance = new McCabeMetricsDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private McCabeMetricsDAOImpl() {
        initialize(McCabeQAMetricsBO.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     * @deprecated
     */
    public static McCabeMetricsDAOImpl getInstance() {
        return instance;
    }
}
