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
 * Créé le 21 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.outils.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.squale.welcom.outils.jdbc.wrapper.ConnectionPool;


/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WConnectionPoolMagic
{
    /** logger */
    private static Log log = LogFactory.getLog( WConnectionPoolMagic.class );

    /** Signleton */
    private static WConnectionPoolMagic magicJDBCConnection = null;

    /** Type de connection */
    private int modeConnection = MODE_CONNECTION_UNKNOW;

    /** Connection Inconnu */
    private static final int MODE_CONNECTION_UNKNOW = 0;

    /** Connection Hybernate */
    private static final int MODE_CONNECTION_HYBERNATE = 1;

    /** Connection JDBC */
    private static final int MODE_CONNECTION_JDBC = 2;

    /** Connection MJDBC */
    private static final int MODE_CONNECTION_MJDBC = 4;

    /**
     * Retourne la connection de l'application
     * 
     * @return connection de l'application
     * @throws SQLException : Probleme SQL
     */
    public static Connection getJdbcConnection()
        throws SQLException
    {
        if ( magicJDBCConnection == null )
        {
            magicJDBCConnection = new WConnectionPoolMagic();
        }

        return magicJDBCConnection.getConnection();
    }

    /**
     * Cloture de la session
     * 
     * @param conn : Connection
     */
    public static void close( final Connection conn )
    {
        if ( magicJDBCConnection != null )
        {
            magicJDBCConnection.closeInternal( conn );
        }

    }

    /**
     * @return Retourne la connection de l'application Elle peut être : - Celle d'Hibernate pour JRAF - Celle de JDBC,
     *         utilisation de Welcom - Celle de MJDBC, utilisation de celle de Welcom
     * @throws SQLException : Probleme SQL
     */
    public Connection getConnection()
        throws SQLException
    {

        if ( modeConnection == MODE_CONNECTION_UNKNOW )
        {
            modeConnection = searchConnection();
        }

        if ( modeConnection == MODE_CONNECTION_UNKNOW )
        {
            throw new SQLException( "Aucune connection BD trouvé sur ce projet" );
        }

        return getConnection( modeConnection );

    }

    /**
     * @param pModeConnection : Mode de connection MODE_CONNECTION_HYBERNATE,MODE_CONNECTION_MJDBC,MODE_CONNECTION_JDBC
     * @return Retourne la connection si elle est trouve sinon null
     * @throws SQLException : Probleme SQL
     */
    private Connection getConnection( final int pModeConnection )
        throws SQLException
    {
        switch ( pModeConnection )
        {
            case MODE_CONNECTION_HYBERNATE:
                return getHibernateConnection();
            case MODE_CONNECTION_JDBC:
                return ConnectionPool.getConnection();
            case MODE_CONNECTION_MJDBC:
                return WConnectionPool.getConnection();
            default:
                return null;
        }
    }

    /**
     * @return Retourne le type de connection pour le projet
     */
    private int searchConnection()
    {
        Connection connection = null;

        try
        {
            // Test Hybernate
            connection = getHibernateConnection();
            if ( connection != null )
            {
                if ( !connection.isClosed() )
                {
                    connection.close();
                }
                WSessionHibernatePersitance.close( connection );
                return MODE_CONNECTION_HYBERNATE;
            }

            // Test JDBC
            if ( ConnectionPool.isInitalized() )
            {
                connection = ConnectionPool.getConnection();
                if ( connection != null )
                {
                    return MODE_CONNECTION_JDBC;
                }
            }
            // test MJDBC
            connection = WConnectionPool.getConnection();
            if ( connection != null )
            {
                return MODE_CONNECTION_MJDBC;
            }
            else
            {
                return MODE_CONNECTION_UNKNOW;
            }
        }
        catch ( final SQLException e )
        {
            log.error( e, e );
            return MODE_CONNECTION_UNKNOW;
        }
    }

    /**
     * Recuperation d'une session hibernate
     * 
     * @return connexion SQL
     * @throws SQLException : probleme SQL
     */
    private Connection getHibernateConnection()
        throws SQLException
    {
        // Verifie que le package hibernate et jraf existe
        try
        {
            Class.forName( "org.squale.jraf.helper.PersistenceHelper" );
            Class.forName( "org.squale.jraf.commons.exception.JrafPersistenceException" );
            Class.forName( "org.squale.jraf.bootstrap.locator.ProviderLocator" );
        }
        catch ( final Exception e )
        {
            return null;
        }
        try
        {
            Class.forName( "net.sf.hibernate.Session" );
            return WSessionHibernatePersitance.getHibernateConnection();
        }
        catch ( final Exception e )
        {
            try
            {
                Class.forName( "org.hibernate.Session" );
                return WSessionHibernatePersitance.getHibernateConnection();
            }
            catch ( final Exception e2 )
            {
                return null;
            }
        }

    }

    /**
     * Fermeture de la session
     * 
     * @param conn : Connection
     */
    public void closeInternal( final Connection conn )
    {
        if ( modeConnection == MODE_CONNECTION_HYBERNATE )
        {
            WSessionHibernatePersitance.close( conn );
        }
    }

}
