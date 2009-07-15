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
package org.squale.squalecommon.daolayer.result;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.rule.SimpleFormulaBO;

/**
 *
 */
public class SimpleFormulaDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static SimpleFormulaDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new SimpleFormulaDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private SimpleFormulaDAOImpl()
    {
        initialize( SimpleFormulaBO.class );
        LOG = LogFactory.getLog( MarkDAOImpl.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static SimpleFormulaDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Récupère la formule du ROI qui doit être unique
     * 
     * @param pSession la session
     * @return la formule du ROI
     * @throws JrafDaoException si erreur
     */
    public SimpleFormulaBO getRoiFormula( ISession pSession )
        throws JrafDaoException
    {
        SimpleFormulaBO formula = null;
        String whereClause = " where ";
        whereClause += " 'roi' in elements(" + getAlias() + ".measureKinds)";
        List result = findWhere( pSession, whereClause );
        if ( result.size() > 0 )
        {
            formula = (SimpleFormulaBO) result.get( 0 );
        }
        return formula;
    }
}
