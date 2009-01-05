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
import org.apache.commons.validator.GenericValidator;

/**
 * @author M327837 Gestion d'un jdbc Mutltiple (@link com.airfrance.welcom.outils.jdbc.WJdbc)
 */
public class WMJdbc
    extends WJdbc
{
    /** logger */
    private static Log log = LogFactory.getLog( WMJdbc.class );

    /** Chaine de connexion */
    private String connectionName = "";

    /**
     * @param pUserName Nom de l'utilisateur
     * @throws SQLException probleme sql
     */
    public WMJdbc( final String pUserName )
        throws SQLException
    {
        this( pUserName, null );
    }

    /**
     * @param pUserName Nom de l'utilisateur
     * @param pConnectionName Nom de la connexion
     * @throws SQLException Probleme SQL
     */
    public WMJdbc( final String pUserName, final String pConnectionName )
        throws SQLException
    {
        super();
        this.userName = pUserName;
        this.connectionName = pConnectionName;
        this.init();
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
            if ( !GenericValidator.isBlankOrNull( connectionName ) )
            {
                conn = WConnectionPool.getConnection( connectionName );
            }
            else
            {
                conn = WConnectionPool.getConnection();
            }

            if ( conn != null )
            {
                if ( conn.isClosed() )
                {
                    log.error( "2004-critical-Database--BD close/Relancer le serveur de BD" );
                }

                // Enleve l'autocommit
                conn.setAutoCommit( false );
            }
        }
        catch ( final SQLException e )
        {
            throw e;
        }
    }
}