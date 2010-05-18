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
 * Créé le 19 juil. 05
 *
 */
package org.squale.squalecommon.daolayer.result;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.provider.persistence.hibernate.SessionImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.result.FactorResultBO;

/**
 * @author M400843
 */
public class FactorResultDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static FactorResultDAOImpl instance = null;

    /** initialisation du singleton */
    static
    {
        instance = new FactorResultDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private FactorResultDAOImpl()
    {
        initialize( FactorResultBO.class );
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static FactorResultDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * Returns the names of all existing factors registered in the database.
     * 
     * @param session The hibernate session
     * @return a list of String, which are the identifiers of the factors in the database
     * @throws JrafDaoException if the method fails to retrieve the factor names
     */
    @SuppressWarnings( "unchecked" )
    public List<String> findFactorNames( ISession session )
        throws JrafDaoException
    {
        List<String> result = new ArrayList<String>();
        try
        {
            String requete =
                "select distinct rule.name " + "from QualityRuleBO rule " + "where rule.class='FactorRule'";
            Query query = ( (SessionImpl) session ).getSession().createQuery( requete );
            result = query.list();
        }
        catch ( HibernateException e )
        {
            throw new JrafDaoException( "Database problem while retrieving data for " + getClass().getName()
                + ".findFactorNames", e );
        }

        return result;
    }

    /**
     * Returns factor results that will be used by the Motion Chart. <br>
     * The raw data that is returned is a list of arrays, each array containing the following data:
     * <ul>
     * <li>0 - the project ID [long]</li>
     * <li>1 - the project name [String]</li>
     * <li>2 - the audit ID [long]</li>
     * <li>3 - the factor name [String]</li>
     * <li>4 - the factor value [float]</li>
     * </ul>
     * 
     * @param session The hibernate session
     * @param applicationId the application DB identifier
     * @return a list of object arrays, each array corresponding to the data described above
     * @throws JrafDaoException if the method fails to retrieve the data
     */
    @SuppressWarnings( "unchecked" )
    public List<Object[]> findFactorsForMotionChart( ISession session, long applicationId )
        throws JrafDaoException
    {
        List<Object[]> result = new ArrayList<Object[]>();
        try
        {
            String requete =
                "select component.id, component.name, audit.id, factorResult.rule.name, factorResult.meanMark"
                    + " from AbstractComponentBO component, AuditBO audit, QualityResultBO factorResult"
                    + " where component.class='Project' and component.parent.id=" + applicationId
                    + " and audit.id in elements(component.audits)" + " and audit.status=" + AuditBO.TERMINATED
                    + " and factorResult.class='FactorResult' and factorResult.project.id=component.id"
                    + " and factorResult.audit.id=audit.id" + " order by audit.id, factorResult.rule.name";
            Query query = ( (SessionImpl) session ).getSession().createQuery( requete );
            result = query.list();
        }
        catch ( HibernateException e )
        {
            throw new JrafDaoException( "Database problem while retrieving data for " + getClass().getName()
                + ".findFactorsForMotionChart", e );
        }

        return result;
    }
}
