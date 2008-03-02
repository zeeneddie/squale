package com.airfrance.squalecommon.daolayer.config;

import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.squalecommon.enterpriselayer.businessobject.config.AuditFrequencyBO;

/**
 * DAO pour les fréquences d'audits
 */
public class AuditFrequencyDAOImpl
    extends AbstractDAOImpl
{

    /**
     * Instance singleton
     */
    private static AuditFrequencyDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new AuditFrequencyDAOImpl();
    }

    /**
     * Constructeur prive
     */
    private AuditFrequencyDAOImpl()
    {
        initialize( AuditFrequencyBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static AuditFrequencyDAOImpl getInstance()
    {
        return instance;
    }
}
