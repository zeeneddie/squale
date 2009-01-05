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
 * Créé le 25 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;

import com.airfrance.jraf.bootstrap.locator.ProviderLocator;
import com.airfrance.jraf.commons.exception.JrafPersistenceException;
import com.airfrance.jraf.helper.PersistenceHelper;
import com.airfrance.jraf.provider.persistence.hibernate.SessionImpl;
import com.airfrance.jraf.spi.persistence.IPersistenceProvider;
import com.airfrance.jraf.spi.persistence.ISession;
import com.airfrance.jraf.spi.provider.IProvider;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WSessionHibernatePersitance
{
    /** logger */
    private static Log log = LogFactory.getLog( WSessionHibernatePersitance.class );

    /** La connection */
    private static Map hmConn = new HashMap();

    /**
     * Recupere la connection jdbc
     * 
     * @throws SQLException : Erreur SQL
     * @return connection
     */
    public static Connection getHibernateConnection()
        throws SQLException
    {
        // Recherche le jdbc dans le pacekge
        ISession session;
        Connection conn = null;
        try
        {
            session = getPersistenceProvider().getSession();
            final SessionImpl sessionImpl = (SessionImpl) session;
            final Object sessionHibernate =
                sessionImpl.getClass().getMethod( "getSession", (Class[]) null ).invoke( sessionImpl, (Object[]) null );
            conn =
                (Connection) sessionHibernate.getClass().getMethod( "connection", (Class[]) null ).invoke(
                                                                                                           sessionHibernate,
                                                                                                           (Object[]) null );

            hmConn.put( sessionHibernate, conn );

        }
        catch ( final Exception e1 )
        {
            log.error( e1, e1 );
            throw new SQLException( e1.getMessage() );
        }
        return conn;
    }

    /**
     * Recupere le provider
     * 
     * @return Le persisance provider
     * @throws JrafPersistenceException Probleme pour trouver le provider
     */
    private static IPersistenceProvider getPersistenceProvider()
        throws JrafPersistenceException
    {
        final String providerName =
            WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_CONFIG_HIBERNATE_PROVIDERPERSISTENCE );
        if ( GenericValidator.isBlankOrNull( providerName ) )
        {
            return PersistenceHelper.getPersistenceProvider();
        }
        else
        {

            final IProvider provider = ProviderLocator.getProvider( providerName );
            if ( provider instanceof IPersistenceProvider )
            {
                return (IPersistenceProvider) provider;
            }
            else
            {
                throw new JrafPersistenceException(
                                                    "Le provider '"
                                                        + providerName
                                                        + "' declaré sous 'addons.config.hibernate.providerpersistence' introuvable dans le dictionnaire JNDI" );
            }

        }
    }

    /**
     * Ferme la session Hybernate
     * 
     * @param conn : Connection jdbc
     */
    public static void close( final Connection conn )
    {

        Object hybernateSession = null;// hmConn.get(conn);
        for ( Iterator iter = hmConn.entrySet().iterator(); iter.hasNext(); )
        {
            Entry element = (Entry) iter.next();
            if ( element.getValue() == conn )
            {
                hybernateSession = element.getKey();
                hmConn.remove( hybernateSession );
            }

        }
        // hmConn.remove(conn);

        try
        {
            if ( hybernateSession != null )
            {
                final Boolean isOpen =
                    (Boolean) hybernateSession.getClass().getMethod( "isOpen", (Class[]) null ).invoke(
                                                                                                        hybernateSession,
                                                                                                        (Object[]) null );
                if ( isOpen.booleanValue() )
                {
                    hybernateSession.getClass().getMethod( "flush", (Class[]) null ).invoke( hybernateSession,
                                                                                             (Object[]) null );
                    hybernateSession.getClass().getMethod( "close", (Class[]) null ).invoke( hybernateSession,
                                                                                             (Object[]) null );
                }
                // hybernateSession.getClass().getMethod("disconnect", (Class[]) null).invoke(hybernateSession,
                // (Object[]) null);
            }
        }
        catch ( final Exception e )
        {
            log.error( e, e );
        }
    }

}
