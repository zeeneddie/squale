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
package org.squale.squalecommon.daolayer.component;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.squale.jraf.commons.exception.JrafDaoException;
import org.squale.jraf.provider.persistence.hibernate.AbstractDAOImpl;
import org.squale.jraf.spi.persistence.ISession;
import org.squale.squalecommon.enterpriselayer.businessobject.component.ApplicationBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.AuditBO;
import org.squale.squalecommon.enterpriselayer.businessobject.component.MethodBO;

/**
 */
public class MethodDAOImpl
    extends AbstractDAOImpl
{
    /**
     * Instance singleton
     */
    private static MethodDAOImpl instance = null;

    /** log */
    private static Log LOG;

    /** initialisation du singleton */
    static
    {
        instance = new MethodDAOImpl();
    }

    /**
     * Constructeur prive
     * 
     * @throws JrafDaoException
     */
    private MethodDAOImpl()
    {
        initialize( MethodBO.class );
        if ( null == LOG )
        {
            LOG = LogFactory.getLog( MethodDAOImpl.class );
        }
    }

    /**
     * Retourne un singleton du DAO
     * 
     * @return singleton du DAO
     */
    public static MethodDAOImpl getInstance()
    {
        return instance;
    }

    /**
     * @param pSession la session
     * @param pMethodName le nom de la méthode
     * @param pFileName le nom du fichier dans lequel se trouve la méthode
     * @param pAuditId l'id de l'audit associé
     * @return l'ensemble des méthodes dans ce fichier pour cet audit
     * @throws JrafDaoException en cas d'échec de récupération des données
     */
    public Collection findMethodeByName( ISession pSession, String pMethodName, String pFileName, long pAuditId )
        throws JrafDaoException
    {
        Collection result = new ArrayList( 0 );
        int index = ApplicationBO.class.getName().lastIndexOf( "." );
        String classAuditName = AuditBO.class.getName().substring( index + 1 );
        String auditAlias = classAuditName.toLowerCase();
        String className = getBusinessClass().getName().substring( index + 1 );
        // Jointure sur les composants d'audits
        String query =
            "select " + getAlias() + " from " + classAuditName + " as " + auditAlias + ", " + className + " as "
                + getAlias() + " ";
        String whereClause =
            "where " + getAlias() + ".longFileName='" + pFileName + "' AND " + getAlias() + ".name like '"
                + pMethodName + "%' AND " + getAlias() + " in elements(" + auditAlias + ".components) ";
        result = find( pSession, query + whereClause );
        return result;
    }

}
