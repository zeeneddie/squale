package com.airfrance.squalecommon.daolayer.result;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.commons.exception.JrafDaoException;
import com.airfrance.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;

/**
 *
 */
public class SimpleFormulaDAOImpl extends AbstractDAOImpl {
    /**
     * Instance singleton
     */
    private static SimpleFormulaDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static {
        instance = new SimpleFormulaDAOImpl();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private SimpleFormulaDAOImpl() {
        initialize(SimpleFormulaBO.class);
        LOG = LogFactory.getLog(MarkDAOImpl.class);
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static SimpleFormulaDAOImpl getInstance() {
        return instance;
    }
    
    /**
     * Récupère la formule du ROI qui doit être unique
     * @param pSession la session
     * @return la formule du ROI
     * @throws JrafDaoException si erreur
     */
    public SimpleFormulaBO getRoiFormula(ISession pSession) throws JrafDaoException {
        SimpleFormulaBO formula = null;
        String whereClause = " where ";
        whereClause += " 'roi' in elements(" + getAlias() + ".measureKinds)";
        List result = findWhere(pSession, whereClause);
        if(result.size() > 0) {
            formula = (SimpleFormulaBO) result.get(0);
        }
        return formula;
    }
}
