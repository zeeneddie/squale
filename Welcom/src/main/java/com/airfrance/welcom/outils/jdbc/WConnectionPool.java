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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.welcom.outils.Util;

/**
 * @author M327837 Gere un collection de connection lors du possage de Welcom en Mode : MJDBC (Multiple connexion JDBC)
 */
public class WConnectionPool
{
    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Liste de connexion disponible ... */
    private static Hashtable connectionPool = new Hashtable();

    /** Connection par defautl a inclure automatiquement dans les WAction, WDispatchAction */
    private static WConnectionPool defaultConnection = null;

    /** Chaine de connection */
    private WConnectionString connectionString;

    /**
     * Instancie une connection et verifie son bon fonctionnement
     * 
     * @param pConnectionString Chaine de connexion
     */
    private WConnectionPool( final WConnectionString pConnectionString )
    {
        this.connectionString = pConnectionString;
        testConnection();
    }

    /**
     * Ajoute une nouvelle connexion JDBC
     * 
     * @param connectionString : Chaine de connexion
     */
    public static void initDataSource( final WConnectionString connectionString )
    {
        final WConnectionPool wConnectionPool = new WConnectionPool( connectionString );
        connectionPool.put( connectionString.getName(), wConnectionPool );

        if ( Util.isTrue( connectionString.getParDefault() ) )
        {
            defaultConnection = wConnectionPool;
        }
    }

    /**
     * Retourne la connexion par default
     * 
     * @return Retourne la connection par default ou la premiere connnexion si aucune definit
     * @throws SQLException : Aucune connection disponnible
     */
    public static Connection getConnection()
        throws SQLException
    {
        if ( defaultConnection == null )
        {
            if ( connectionPool.size() > 0 )
            {
                defaultConnection = (WConnectionPool) connectionPool.elements().nextElement();
            }
            else
            {
                throw new SQLException( "Aucune connexion definit par default dans le struts-config.xml, plugin Welcom" );
            }
        }

        return defaultConnection.getInternalConnection();
    }

    /**
     * Retourne la connexion en fonction du NAME definit lors de la creation de la connexion
     * 
     * @param name nom de la connexion
     * @return Retourne la connexion
     * @throws SQLException : Aucune connexion disponnible sous ce nom
     */
    public static Connection getConnection( final String name )
        throws SQLException
    {
        if ( GenericValidator.isBlankOrNull( name ) )
        {
            return getConnection();
        }

        if ( !connectionPool.containsKey( name ) )
        {
            throw new SQLException( "Aucune dataSource declaré sous le nom de " + name
                + ", verifier votre struts config" );
        }

        return ( (WConnectionPool) connectionPool.get( name ) ).getInternalConnection();
    }

    /**
     * Verfie que le connection fonctionne correctiement
     * 
     * @return true si correcte et affiche les parametres de connexion
     */
    private boolean testConnection()
    {
        try
        {
            final Connection conn = getInternalConnection();
            logStartup.info( "URL : " + conn.getMetaData().getURL() );
            logStartup.info( "User : " + conn.getMetaData().getUserName() );

            return true;
        }
        catch ( final Exception e )
        {
            logStartup.error( "Impossible d'établir une connexion à la base de données sous le nom "
                + connectionString.getName() );
            logStartup.error( "La declaration dans le fichier struts-config est du type : " );
            logStartup.error( "   <set-property property=\"dataSource(0).connectionString\" value=\"URL=jdbc/vidcco;PASS=jdbc;USER=LOLO;NAME=popo;DESCRIPTION=Hello\"/>" );
            logStartup.error( e.getMessage(), e );

            return false;
        }
    }

    /**
     * Returns a JDBC connection from a connection pool or other resource, to be used and closed promptly.
     * <p>
     * 
     * @return JDBC connection from resource layer.
     * @exception SQLException on SQL or other errors. May wrap other exceptions depending on implementation. Will not
     *                return null.
     */
    private Connection getInternalConnection()
        throws SQLException
    {

        // la datasource genere
        DataSource ds = null;

        try
        {
            if ( ds == null )
            {
                // create parameter list to access naming system
                final java.util.Hashtable parms = new java.util.Hashtable();

                // Ajoute les parametres necessaire pour le jndi
                addJNDICompatabiliteMode( parms );

                if ( !GenericValidator.isBlankOrNull( connectionString.getProviderUrl() ) )
                {
                    parms.put( javax.naming.Context.PROVIDER_URL, connectionString.getProviderUrl() );
                }

                final javax.naming.Context ctx = new javax.naming.InitialContext( parms );

                ds = (javax.sql.DataSource) ctx.lookup( connectionString.getJndiDataSource() );
            }

            // use DataSource factory to get data server connection
            Connection conn = null;

            if ( !GenericValidator.isBlankOrNull( connectionString.getUserName() ) )
            {
                conn = ds.getConnection( connectionString.getUserName(), connectionString.getUserPassword() );
            }
            else
            {
                conn = ds.getConnection();
            }

            return conn;
        }
        catch ( final Exception ex )
        {
            logStartup.error( "WConnectionPool : Echec connexion : " + ex.getMessage() );

            if ( ( ex == null ) || ( ex.getMessage() == null ) )
            {
                logStartup.error( "Verifier la version de votre classes12.zip" );
                logStartup.error( " ou Verifier la version de votre odbcj14.jar" );
            }

            return null;
        }
    }

    /**
     * Ajoute dans les parametres pour recupere la connexion si on est en WAS 3.5
     * 
     * @param parms : Parametres pour rechercher la connexion dans le dictionnaire JNDI
     */
    private void addJNDICompatabiliteMode( final java.util.Hashtable parms )
    {
        try
        {
            Class.forName( "com.ibm.websphere.naming.WsnInitialContextFactory" );
            parms.put( javax.naming.Context.INITIAL_CONTEXT_FACTORY,
                       "com.ibm.websphere.naming.WsnInitialContextFactory" );
        }
        catch ( final ClassNotFoundException e )
        {
            logStartup.error( "Passage en mode compatibilité WAS 3.5 " );
        }
    }
}