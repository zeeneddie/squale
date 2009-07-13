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
package org.squale.welcom.addons.message;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @created Jul 21, 2003
 * @version ApplicationModel.java
 */
public class WMessageResourcesControleur
{
    /** logger */
    private static Log log = LogFactory.getLog( WMessageResourcesControleur.class );

    /** Classe utilisé pour la persistance */
    private static String classPersistance = "org.squale.welcom.addons.message.persistence.WMessageResourcesJdbc";

    /** Verfie si on est seul */
    private static WMessageResourcesControleur selfApplication = null;

    /** Persistance */
    private WIMessageResourcesPersistence appPersistence;

    /**
     * Singleton -> Constructor will be private just one Application per JVM allowed
     */
    private WMessageResourcesControleur()
    {
        // will be set through properties
        final String persistenceModel = classPersistance;

        try
        {
            // load persistence class
            appPersistence = (WIMessageResourcesPersistence) Class.forName( persistenceModel ).newInstance();
        }
        catch ( final Exception e )
        {
            log.error( "Exception occured: " + e, e );
        }
    }

    /**
     * Singleton Application Class. The one and only ApplicationModel is stored in a static member. There can be only
     * one ApplicationModel per JVM.
     * 
     * @return les message controleur
     */
    public static WMessageResourcesControleur getWMessageResourcesControleur()
    {
        if ( selfApplication == null )
        {
            selfApplication = new WMessageResourcesControleur();
        }

        return selfApplication;
    }

    /**
     * business logic / configuration (global) follows here
     * 
     * @param stringLocale Locale
     * @return la liste des messages
     */
    public Vector getMessagesByLanguage( final String stringLocale )
    {
        return appPersistence.getAppMessagesByLocale( stringLocale );
    }

    /**
     * @see WIMessageResourcesPersistence#getavailableLocales()
     */
    public Vector getavailableLocales()
    {
        return appPersistence.getavailableLocales();
    }

    /**
     * @return classe de persistance
     */
    public static String getClassPersistance()
    {
        return classPersistance;
    }

    /**
     * @param string spécifie la classe de persistance
     */
    public static void setClassPersistance( final String string )
    {
        classPersistance = string;
    }
}