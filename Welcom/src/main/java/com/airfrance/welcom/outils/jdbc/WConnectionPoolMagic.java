/*
 * Créé le 21 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.jdbc.wrapper.ConnectionPool;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WConnectionPoolMagic {
    /** logger */
    private static Log log = LogFactory.getLog(WConnectionPoolMagic.class);

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
     * @return connection de l'application
     * @throws SQLException : Probleme SQL
     */
    public static Connection getJdbcConnection() throws SQLException {
        if (magicJDBCConnection == null) {
            magicJDBCConnection = new WConnectionPoolMagic();
        }

        return magicJDBCConnection.getConnection();
    }

    /**
     * Cloture de la session
     * @param conn : Connection
     */
    public static void close(final Connection conn) {
        if (magicJDBCConnection != null) {
            magicJDBCConnection.closeInternal(conn);
        }

    }

    /**
     * @return Retourne la connection de l'application
     * Elle peut être :
     *  - Celle d'Hibernate pour JRAF
     *  - Celle de JDBC, utilisation de Welcom
     *  - Celle de MJDBC, utilisation de celle de Welcom
     * @throws SQLException : Probleme SQL
     */
    public Connection getConnection() throws SQLException {

        if (modeConnection == MODE_CONNECTION_UNKNOW) {
            modeConnection = searchConnection();
        }

        if (modeConnection == MODE_CONNECTION_UNKNOW) {
            throw new SQLException("Aucune connection BD trouvé sur ce projet");
        }

        return getConnection(modeConnection);

    }

    /**
     * @param pModeConnection : Mode de connection
     * MODE_CONNECTION_HYBERNATE,MODE_CONNECTION_MJDBC,MODE_CONNECTION_JDBC
     * @return Retourne la connection si elle est trouve sinon null
     * @throws SQLException : Probleme SQL
     */
    private Connection getConnection(final int pModeConnection) throws SQLException {
        switch (pModeConnection) {
            case MODE_CONNECTION_HYBERNATE :
                return getHibernateConnection();
            case MODE_CONNECTION_JDBC :
                return ConnectionPool.getConnection();
            case MODE_CONNECTION_MJDBC :
                return WConnectionPool.getConnection();
            default :
                return null;
        }
    }

    /**
     * 
     * @return Retourne le type de connection pour le projet
     */
    private int searchConnection() {
        Connection connection = null;

        try {
            // Test Hybernate
            connection = getHibernateConnection();
            if (connection != null) {
                if (!connection.isClosed()) {
                    connection.close();
                }
                WSessionHibernatePersitance.close(connection);
                return MODE_CONNECTION_HYBERNATE;
            }

            // Test JDBC
            if (ConnectionPool.isInitalized()) {
                connection = ConnectionPool.getConnection();
                if (connection != null) {
                    return MODE_CONNECTION_JDBC;
                }
            }
            // test MJDBC
            connection = WConnectionPool.getConnection();
            if (connection != null) {
                return MODE_CONNECTION_MJDBC;
            } else {
                return MODE_CONNECTION_UNKNOW;
            }
        } catch (final SQLException e) {
            log.error(e, e);
            return MODE_CONNECTION_UNKNOW;
        }
    }

    /**
     * Recuperation d'une session hibernate
     * @return connexion SQL
     * @throws SQLException : probleme SQL
     */
    private Connection getHibernateConnection() throws SQLException {
        // Verifie que le package hibernate et jraf existe
        try {
            Class.forName("com.airfrance.jraf.helper.PersistenceHelper");
            Class.forName("com.airfrance.jraf.commons.exception.JrafPersistenceException");
            Class.forName("com.airfrance.jraf.bootstrap.locator.ProviderLocator");
        } catch (final Exception e) {
            return null;
        }
        try {
            Class.forName("net.sf.hibernate.Session");
            return WSessionHibernatePersitance.getHibernateConnection();
        } catch (final Exception e) {
            try {
                Class.forName("org.hibernate.Session");
                return WSessionHibernatePersitance.getHibernateConnection();
            } catch (final Exception e2) {
                return null;
            }
        }

    }

    /**
     * Fermeture de la session
     * @param conn : Connection
     */
    public void closeInternal(final Connection conn) {
        if (modeConnection == MODE_CONNECTION_HYBERNATE) {
            WSessionHibernatePersitance.close(conn);
        }
    }

}
