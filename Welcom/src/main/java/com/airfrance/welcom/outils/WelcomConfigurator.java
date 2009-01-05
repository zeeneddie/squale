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
 * Créé le 26 avr. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.util.MessageResources;

import com.airfrance.welcom.struts.plugin.WelcomContext;
import com.airfrance.welcom.taglib.aide.Aide;
import com.airfrance.welcom.taglib.aide.AideException;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WelcomConfigurator
    implements IWConfigurator
{

    /** instance */
    private static final HashMap htSingleton = new HashMap();

    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Message bundle de l'application, pour la configuration */
    private MessageResources message = null;

    /** Message bundle interne ... contient la conf de welcom par defaut */
    private static MessageResources messageInternal = null;

    /** Emplacement par default de la conf de Welcom dans son package */
    private final static String MSG_INTERNAL_KEY = "com.airfrance.welcom.resources.WelcomResources";

    /** Date de derniere modification de parametres */
    private Date lastDateFile = null;

    /** Nom du fichier de configuration du projet pour welcom */
    private String fileName = "";

    /** Le fichie de configuration est trouvé */
    private boolean goodFileResources = false;

    /** Version de la charte */
    private Charte charte = null;

    /**
     * @return Pseudo Singleton /recupere sous la clef JNDI : java:comp/env/welcomConfContext
     */
    public static WelcomConfigurator getInstance()
    {

        WelcomConfigurator wc;
        final String welcomContextName = WelcomContext.getWelcomContextName();

        synchronized ( htSingleton )
        {
            wc = (WelcomConfigurator) htSingleton.get( welcomContextName );
            if ( wc == null )
            {
                wc = new WelcomConfigurator();
                htSingleton.put( welcomContextName, wc );
            }
        }
        return wc;
    }

    /**
     * Initialise les propriétés du fichier de configuration - lastDate - isGoodResources
     * 
     * @param pFileName fichier de configuration
     */
    private void initializeFile( final String pFileName )
    {

        String realFileName = "";

        this.fileName = pFileName;

        InputStream is = null;

        if ( !GenericValidator.isBlankOrNull( fileName ) )
        {

            // Set up to load the property resource for this locale key, if we can
            realFileName = fileName.replace( '.', '/' ) + ".properties";

            goodFileResources = true;
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            // Recherche la date de derniere modification
            final URL url = classLoader.getResource( realFileName );
            URLConnection urlConn;
            try
            {
                urlConn = url.openConnection();
                urlConn.setUseCaches( false );
                urlConn.connect();
                urlConn.getLastModified();
                lastDateFile = new Date( urlConn.getLastModified() );
            }
            catch ( final Exception e )
            {
                goodFileResources = false;
            }

            // Regarde si le fichier n'est pas vide
            is = classLoader.getResourceAsStream( realFileName );
            goodFileResources = ( is != null );
        }

    }

    /**
     * Retourne la charte actuelle dans le fichier de config
     * 
     * @return la charte interne
     */
    public Charte getInternalCharte()
    {
        if ( charte == null )
        {
            String versionMajor = WelcomConfigurator.getMessage( WelcomConfigurator.CHARTE );
            String versionMinor = WelcomConfigurator.getMessage( "charte" + versionMajor + ".version" );
            String version = versionMajor + "." + versionMinor;
            if ( "v2.001".equalsIgnoreCase( version ) )
            {
                charte = Charte.V2_001;
            }
            else if ( "v2.002".equalsIgnoreCase( version ) )
            {
                charte = Charte.V2_002;
            }
            else if ( "v3.001".equalsIgnoreCase( version ) )
            {
                charte = Charte.V3_001;
            }
            else
            {
                /** Par default si ca merde ... */
                charte = Charte.V3_001;
                logStartup.warn( "Erreur sur la lecture de la charte passage en " + charte );
            }
        }
        return charte;
    }

    /**
     * @return Troune vrai si le fichier est trouvé
     */
    public boolean isGoodFileResources()
    {
        return goodFileResources;
    }

    /**
     * @return Retourne la date de derniere modification du fichier
     */
    public Date getLastDateFile()
    {
        return lastDateFile;
    }

    /**
     * initialise la configuration de Welcom
     * 
     * @param pFileName : File name
     */
    private void initialize( final String pFileName )
    {

        initializeFile( pFileName );

        if ( !GenericValidator.isBlankOrNull( pFileName ) )
        {

            if ( message != null )
            {
                logStartup.warn( "Double initialisation de Welcom" );
                logStartup.warn( "Seules les propriétés du dernier init seront prises en compte !" );
                logStartup.warn( "Spécifier votre welcomContext (Variable d'environnement du web.xml)" );
            }
            if ( isGoodFileResources() )
            {
                logStartup.info( "Initialisation de " + WelcomContext.getWelcomContextName() + " : " + fileName );
                message = MessageResources.getMessageResources( fileName );
            }
            else
            {
                logStartup.info( "ERREUR : fichier de config welcom non trouvé '" + fileName + "'" );
            }
        }
        else
        {
            logStartup.info( "Initialisation de Welcom avec configuration par default" );
        }

        messageInternal = MessageResources.getMessageResources( MSG_INTERNAL_KEY );

        // Init De l'aide
        initAide();

    }

    /*******************************************************************************************************************
     * Partie statique
     ******************************************************************************************************************/

    /**
     * Initilisation via le fichier de config
     * 
     * @param fileName : Nom de la resource locale du projet. Celle-cis surcharge les definition de welcom
     */
    public static void init( final String fileName )
    {

        getInstance().initialize( fileName );
    }

    /**
     * Initialisation par default si par de resource par l'utilisateur
     */
    private static void init()
    {
        getInstance().initialize( null );
    }

    /**
     * Initialisation de l'aide .... Utilisé seulement par agir / n'est pas propposé aux auters projets
     */
    private static void initAide()
    {
        if ( Util.isTrue( getMessage( "aide.active" ) ) )
        {
            final String aideRessource = getMessage( "aide.properties" );
            final String aidePath = getMessage( "aide.path.url" );

            if ( GenericValidator.isBlankOrNull( aideRessource ) )
            {
                logStartup.info( "Parametre Aide.properties : null" );
            }
            else
            {
                logStartup.info( "Fichier d'aide :" + aideRessource );

                try
                {
                    Aide.openAide( aideRessource, aidePath );
                }
                catch ( final AideException ae )
                {
                    logStartup.error( ae.getMessage(), ae );
                }
            }
        }
    }

    /**
     * Recuperation des messages de configuration .... L'internationnalisaion n'est pas necessaire
     * 
     * @param locale : locale
     * @param key : Clef a cherché
     * @param arg1 : attribut 1
     * @param arg2 : attribut 2
     * @param arg3 : attribut 3
     * @param arg4 : attribut 4
     * @return Valeur
     */
    private String getComputedMessage( final Locale locale, final String key, final Object arg1, final Object arg2,
                                       final Object arg3, final Object arg4 )
    {
        if ( ( message == null ) && ( messageInternal == null ) )
        {
            WelcomConfigurator.init();
        }

        if ( ( message != null ) && message.isPresent( locale, key ) )
        {
            return message.getMessage( locale, key, arg1, arg2, arg3, arg4 );
        }
        else
        {
            return messageInternal.getMessage( locale, key, arg1, arg2, arg3, arg4 );
        }
    }

    /**
     * Recuperation des messages de configuration .... L'internationnalisaion n'est pas necessaire
     * 
     * @param locale : locale
     * @param key : Clef a cherché
     * @param arg1 : attribut 1
     * @param arg2 : attribut 2
     * @param arg3 : attribut 3
     * @param arg4 : attribut 4
     * @return Valeur
     */
    private static String getMessage( final Locale locale, final String key, final Object arg1, final Object arg2,
                                      final Object arg3, final Object arg4 )
    {
        return getInstance().getComputedMessage( locale, key, arg1, arg2, arg3, arg4 );
    }

    /**
     * Recuperation des messages de configuration ....
     * 
     * @param partialKey : Clef a cherché
     * @return Valeur
     */
    public static String getMessageWithFullCfgChartePrefix( final String partialKey )
    {
        final Charte vcharte = WelcomConfigurator.getCharte();
        String prefix = vcharte.getWelcomConfigFullPrefix();
        return getMessage( (Locale) null, prefix + partialKey, null, null, null, null );
    }

    /**
     * Recuperation des messages de configuration ....
     * 
     * @param partialKey : Clef a cherché
     * @return Valeur
     */
    public static String getMessageWithCfgChartePrefix( final String partialKey )
    {
        final Charte vcharte = WelcomConfigurator.getCharte();
        String prefix = vcharte.getWelcomConfigPrefix();
        return getMessage( (Locale) null, prefix + partialKey, null, null, null, null );
    }

    /**
     * Recuperation des messages de configuration ....
     * 
     * @param key : Clef a cherché
     * @return Valeur
     */
    public static String getMessage( final String key )
    {
        return getMessage( (Locale) null, key, null, null, null, null );
    }

    /**
     * Recuperation des messages de configuration ....
     * 
     * @param key : Clef a cherché
     * @param arg1 : attribut 1
     * @return Valeur
     */
    public static String getMessage( final String key, final Object arg1 )
    {
        return getMessage( null, key, arg1, null, null, null );
    }

    /**
     * Recuperation des messages de configuration ....
     * 
     * @param key : Clef a cherché
     * @param arg1 : attribut 1
     * @param arg2 : attribut 2
     * @return Valeur
     */
    public static String getMessage( final String key, final Object arg1, final Object arg2 )
    {
        return getMessage( null, key, arg1, arg2, null, null );
    }

    /**
     * Recuperation des messages de configuration ....
     * 
     * @param key : Clef a cherché
     * @param arg1 : attribut 1
     * @param arg2 : attribut 2
     * @param arg3 : attribut 3
     * @return Valeur
     */
    public static String getMessage( final String key, final Object arg1, final Object arg2, final Object arg3 )
    {
        return getMessage( null, key, arg1, arg2, arg3, null );
    }

    /**
     * Recuperation des messages de configuration ....
     * 
     * @param key : Clef a cherché
     * @param arg1 : attribut 1
     * @param arg2 : attribut 2
     * @param arg3 : attribut 3
     * @param arg4 : attribut 4
     * @return Valeur
     */
    public static String getMessage( final String key, final Object arg1, final Object arg2, final Object arg3,
                                     final Object arg4 )
    {
        return getMessage( key, arg1, arg2, arg3, arg4 );
    }

    /**
     * @return Date de modification des fichiers
     */
    public static Date getLastDate()
    {
        return getInstance().getLastDateFile();
    }

    /**
     * Retourne la charte actuelle dans le fichier de config
     * 
     * @return la charte actuelle dans le fichier de config
     */
    public static Charte getCharte()
    {
        return getInstance().getInternalCharte();
    }

}