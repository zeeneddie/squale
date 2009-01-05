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
 * Créé le 24 mars 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.jdbc;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Gestion d'un jdbc Mutltiple (@link com.airfrance.welcom.outils.jdbc.WJdbc)
 */
public class WJdbcMagic
    extends WJdbc
{
    /** logger */
    private static Log log = LogFactory.getLog( WJdbcMagic.class );

    /**
     * @param user : nom du user
     * @throws SQLException probleme sql
     */
    public WJdbcMagic( final String user )
        throws SQLException
    {
        super();
        this.userName = user;
        if ( Util.isNonEqualsIgnoreCase( WelcomConfigurator.getMessage( WelcomConfigurator.DEBUG_CONFIG_JDBC ), "true" ) )
        {
            WJdbc.setEnabledTrace( false );
        }
        this.init();
    }

    /**
     * @throws SQLException probleme sql
     */
    public WJdbcMagic()
        throws SQLException
    {
        this( "MAGIC" );
    }

    /**
     * Surcharge de la methode init
     * 
     * @throws SQLException Probleme SQL
     */
    protected void init()
        throws SQLException
    {

        try
        {
            conn = WConnectionPoolMagic.getJdbcConnection();

            if ( conn != null )
            {
                if ( conn.isClosed() )
                {
                    log.error( "2004-critical-Database--BD close/Relancer le serveur de BD" );
                }

                // Enleve l'autocommit
                conn.setAutoCommit( false );

                // conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            }
        }
        catch ( final SQLException e )
        {
            throw e;
        }
    }

    /**
     * @see com.airfrance.welcom.outils.jdbc.WJdbc#close()
     */
    public void close()
    {

        super.close();

        WConnectionPoolMagic.close( conn );
    }

    /**
     * Effectue un commit de modifcation dur la BD
     * 
     * @throws SQLException probleme sql
     */
    public void commit()
        throws SQLException
    {
        if ( ( conn != null ) && !conn.isClosed() )
        {
            conn.commit();
        }
    }

    /**
     * Effectue un roolback
     * 
     * @throws SQLException Probleme sql
     */
    public void rollback()
        throws SQLException
    {
        if ( ( conn != null ) && !conn.isClosed() )
        {
            conn.rollback();
        }
    }

    /**
     * Termine proprement
     * 
     * @throws Throwable : Leve une exception Throwable
     */
    protected void finalize()
        throws Throwable
    {
        if ( ( conn != null ) && !conn.isClosed() )
        {
            close();
        }
        super.finalize();
    }

}