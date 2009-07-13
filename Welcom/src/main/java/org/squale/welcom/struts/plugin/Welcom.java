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
 * Créé le 21 sept. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.RequestUtils;
import org.squale.welcom.addons.config.AddonsConfig;
import org.squale.welcom.addons.message.WMessageResourcesControleur;
import org.squale.welcom.outils.Util;
import org.squale.welcom.outils.WelcomConfigurator;
import org.squale.welcom.outils.jdbc.WConnectionPool;
import org.squale.welcom.outils.jdbc.WConnectionString;
import org.squale.welcom.outils.jdbc.wrapper.ConnectionPool;
import org.squale.welcom.outils.pdf.PDFFontUtil;
import org.squale.welcom.struts.util.WConstants;


/**
 * Classe d'appel aux fonctions d'initialisations de Welcom pour la compatibilité avec JRAF.
 */
public class Welcom
{
    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /**
     * Cherche le mode d'initilisation de Welcom
     * 
     * @param servlet : Servlet
     * @param useMode : Flag
     */
    public static void initUseMode( final ActionServlet servlet, final String useMode )
    {
        servlet.getServletContext().setAttribute( WConstants.WELCOM_USE_MODE, useMode );
        logStartup.info( "Utilisation de welcom en mode : " + useMode );
    }

    /**
     * Initilisation du module jdbc
     * 
     * @param dataSourceName : Nom de la data source
     * @param bdPassword : Password bd
     * @param bdUser : user bd
     */
    public static void initJdbc( final String dataSourceName, final String bdPassword, final String bdUser )
    {
        // Initialisation des paramètres de connection de la base de données
        ConnectionPool.initDataSource( dataSourceName, bdPassword, bdUser );
    }

    /**
     * Initilisation du module JDBC
     * 
     * @param connectionString : Chaine de connexion
     */
    public static void initJdbc( final WConnectionString connectionString )
    {
        // Initialisation des paramètres de connection de la base de données
        WConnectionPool.initDataSource( connectionString );
    }

    /**
     * Initisalistion du fichier de conf de welcom
     * 
     * @param propertiesFile Nom du fichier properties
     */
    public static void initWelcom( final String propertiesFile )
    {
        WelcomConfigurator.init( propertiesFile );
    }

    /**
     * Initisalise le repertoire de font pour le PDF
     * 
     * @param servlet : Servlet
     */
    public static void initFontPDF( final ActionServlet servlet )
    {
        if ( PDFFontUtil.init( servlet ) && PDFFontUtil.isSpecificRealFontPath() )
        {
            logStartup.info( "Ajout du chemin des polices pour le PDF : "
                + WelcomConfigurator.getMessage( WelcomConfigurator.FONT_PATH ) );
        }
    }

    /**
     * Ajoute la factory
     * 
     * @param messageResourceClass : Nom de la factory
     */
    public static void initMessageFactory( final String messageResourceClass )
    {
        if ( !org.apache.commons.validator.GenericValidator.isBlankOrNull( messageResourceClass ) )
        {
            WMessageResourcesControleur.setClassPersistance( messageResourceClass );

            logStartup.info( "Ajout de la classe de persitance pour les messages: " + messageResourceClass );
        }
    }

    /**
     * Ajoute le forward d'erreur
     * 
     * @param servlet : Servlet
     * @param config : Config Struts
     */
    public static void initForward( final ActionServlet servlet, final ModuleConfig config )
    {

        final String forwardError = WelcomConfigurator.getMessage( WelcomConfigurator.ERROR_PAGE_JSP );

        // Verification de l'existance de la page d'erreur
        final String fullPathError = servlet.getServletContext().getRealPath( forwardError );
        final File f = new File( fullPathError );
        if ( !f.exists() && !org.apache.commons.validator.GenericValidator.isBlankOrNull( forwardError ) )
        {
            logStartup.info( "ATTENTION pas de forward, page d'erreur non trouvé : " + forwardError );
        }
        else
        {

            final ActionForward fc = new ActionForward();
            fc.setRedirect( false );
            fc.setContextRelative( false );
            fc.setName( "error" );
            fc.setPath( forwardError );
            config.addForwardConfig( fc );
            logStartup.info( "Ajout des forwards 'error', pour gestion des erreurs natives sur " + forwardError );

        }
    }

    /**
     * Ajout des diffrents mapping de nos servlets
     * 
     * @param config : Config Struts
     */
    public static void initMapping( final ModuleConfig config )
    {
        initMapping( config, "" );

        final ActionConfig[] actionConfigs = config.findActionConfigs();
        final ArrayList listPath = new ArrayList();
        for ( int i = 0; i < actionConfigs.length; i++ )
        {
            final String path = actionConfigs[i].getPath();
            final int index = path.lastIndexOf( "/" );
            if ( index > 0 )
            {
                final String actionContext = path.substring( 0, index );
                if ( !listPath.contains( actionContext ) )
                {
                    listPath.add( actionContext );
                    initMapping( config, actionContext );
                }
            }
        }
    }

    /**
     * Ajout des diffrents mapping de nos servlets
     * 
     * @param config : Config Struts
     * @param actionContext : le context
     */
    private static void initMapping( final ModuleConfig config, final String actionContext )
    {

        try
        {
            Class.forName( config.getActionMappingClass() );

            ActionMapping mapping;

            try
            {
                // Table forward
                mapping = (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( actionContext + "/tableForward" );
                mapping.setType( "org.squale.welcom.struts.action.WForwardAction" );
                config.addActionConfig( mapping );
                logStartup.info( "Ajout du mapping '/tableForward.do' pour la navigation des listes" );

                mapping = (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( actionContext + "/css" );
                mapping.setType( "org.squale.welcom.struts.action.WGzipAction" );
                config.addActionConfig( mapping );

                mapping = (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( actionContext + "/js" );
                mapping.setType( "org.squale.welcom.struts.action.WGzipAction" );
                config.addActionConfig( mapping );

                mapping = (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( actionContext + "/img" );
                mapping.setType( "org.squale.welcom.struts.action.WGzipAction" );
                config.addActionConfig( mapping );

                mapping = (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( actionContext + "/html" );
                mapping.setType( "org.squale.welcom.struts.action.WGzipAction" );
                config.addActionConfig( mapping );
                logStartup.info( "Ajout du mapping '" + actionContext + "/css.do','" + actionContext + "/js.do','"
                    + actionContext + "/img.do','" + actionContext + "/html.do' pour la recherche des pages en interne" );

                mapping = (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( actionContext + "/printforward" );
                mapping.setType( "org.squale.welcom.struts.action.WForwardAction" );

                mapping.addForwardConfig( new ActionForward(
                                                             "apercuPDF",
                                                             WelcomConfigurator.getMessage( WelcomConfigurator.PATH_JSP_APERCU_PDF ),
                                                             false ) );
                mapping.addForwardConfig( new ActionForward(
                                                             "apercuPDFExterne",
                                                             WelcomConfigurator.getMessage( WelcomConfigurator.PATH_JSP_APERCU_PDFEXTERNE ),
                                                             false ) );
                mapping.addForwardConfig( new ActionForward(
                                                             "preparationImpressionPDF",
                                                             WelcomConfigurator.getMessage( WelcomConfigurator.PATH_JSP_PREPARATION_IMPRESSION_PDF ),
                                                             false ) );
                mapping.addForwardConfig( new ActionForward(
                                                             "success",
                                                             WelcomConfigurator.getMessage( WelcomConfigurator.PATH_JSP_SUCCESS ),
                                                             false ) );
                config.addActionConfig( mapping );
                logStartup.info( "Ajout du mapping '" + actionContext
                    + "/printforward.do' pour l'aperçu avant impression" );

                mapping = (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( actionContext + "/lazyLoading" );
                mapping.setType( "org.squale.welcom.struts.action.WLazyLoadingAction" );
                config.addActionConfig( mapping );
                logStartup.info( "Ajout du mapping '" + actionContext
                    + "/lazyLoading.do' pour le chargement a la volée" );

                mapping = (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( actionContext + "/easyComplete" );
                mapping.setType( "org.squale.welcom.struts.action.WEasyCompleteAction" );
                config.addActionConfig( mapping );
                logStartup.info( "Ajout du mapping '" + actionContext
                    + "/easyComplete.do' pour la complétion facile de la saisie" );

            }
            catch ( final InstantiationException e1 )
            {
                logStartup.error( e1, e1 );
            }
            catch ( final IllegalAccessException e1 )
            {
                logStartup.error( e1, e1 );
            }
        }
        catch ( final ClassNotFoundException e )
        {
            logStartup.error( e, e );
        }
    }

    /**
     * Ecrit la config de la JVM
     */
    public static void dumpConfigJVM()
    {

        if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.DEBUG_CONFIG_JVM ) ) )
        {
            final Properties p = System.getProperties();
            final Enumeration enumeration = p.keys();

            while ( enumeration.hasMoreElements() )
            {
                final String key = (String) enumeration.nextElement();
                logStartup.info( key + " : " + p.getProperty( key ) );
            }
        }

    }

    /**
     * Mise en place de la gestion d'exception par default
     * 
     * @param servlet : Servlet
     * @param config : Config Struts
     */
    public static void initException( final ActionServlet servlet, final ModuleConfig config )
    {
        final String forwardError = WelcomConfigurator.getMessage( WelcomConfigurator.ERROR_PAGE_JSP );

        // Verification de l'existance de la page d'erreur
        final String fullPathError = servlet.getServletContext().getRealPath( forwardError );
        final File f = new File( fullPathError );
        if ( !f.exists() && !org.apache.commons.validator.GenericValidator.isBlankOrNull( forwardError ) )
        {
            logStartup.info( "ATTENTION pas de gestion d'exception,page d'erreur non trouvé : " + forwardError );
        }
        else
        {
            try
            {
                // Ajout du mapping
                final ActionMapping mapping =
                    (ActionMapping) RequestUtils.applicationInstance( config.getActionMappingClass() );
                mapping.setPath( "/error" );
                mapping.setForward( forwardError );

                config.addActionConfig( mapping );
            }
            catch ( final InstantiationException e1 )
            {
                logStartup.error( e1, e1 );
            }
            catch ( final IllegalAccessException e1 )
            {
                logStartup.error( e1, e1 );
            }
            catch ( final ClassNotFoundException e )
            {
                logStartup.error( e, e );
            }

            // Ajout de l'erreur genérique
            ExceptionConfig ec;
            if ( config.findExceptionConfig( "java.lang.Exception" ) == null )
            {
                ec = new ExceptionConfig();
                ec.setType( "java.lang.Exception" );
                ec.setKey( "welcom.internal.error.msg" );
                ec.setPath( "/error.do" );
                config.addExceptionConfig( ec );
                logStartup.info( "Ajout de la gestion d'exception java.lang.Exception, sur /error.do" );
            }

            // Ajout de l'erreur Type TimeOut
            if ( config.findExceptionConfig( "org.squale.welcom.struts.action.TimeOutException" ) == null )
            {
                ec = new ExceptionConfig();
                ec.setType( "org.squale.welcom.struts.action.TimeOutException" );
                ec.setKey( "welcom.internal.error.msg" );
                ec.setPath( "/error.do" );
                config.addExceptionConfig( ec );

                logStartup.info( "Ajout de la gestion d'exception org.squale.welcom.struts.action.TimeOutException, sur /error.do" );
            }

        }
    }

    /**
     * Initialisation des addons
     * 
     * @param servlet : Servlet
     * @param config : Config Struts
     */
    public static void initAddons( final ActionServlet servlet, final ModuleConfig config )
    {
        AddonsConfig.init( servlet, config );
    }

    /**
     * detruit les addons
     */
    public static void destroyAddons()
    {
        AddonsConfig.destroy();
    }

    /**
     * Initialisation des addons
     * 
     * @param servlet : Servlet
     * @param config : Config Struts
     */
    public static void initParameter( final ActionServlet servlet, final ModuleConfig config )
    {

        final ActionConfig[] ac = config.findActionConfigs();

        for ( int i = 0; i < ac.length; i++ )
        {
            if ( org.apache.commons.validator.GenericValidator.isBlankOrNull( ac[i].getParameter() ) )
            {
                ac[i].setParameter( "action" );
            }
        }
        final MessageResourcesConfig mrcs[] = config.findMessageResourcesConfigs();
        for ( int i = 0; i < mrcs.length; i++ )
        {
            if ( ( mrcs[i].getFactory() != null ) && ( mrcs[i].getParameter() != null )
                && ( mrcs[i].getFactory().equals( "org.apache.struts.util.PropertyMessageResourcesFactory" ) )
                && ( mrcs[i].getKey().equals( Globals.MESSAGES_KEY ) ) )
            {

                mrcs[i].setFactory( "org.squale.welcom.struts.message.WPropertyMessageResourcesFactory" );
                final String factory = mrcs[i].getFactory();
                MessageResourcesFactory.setFactoryClass( factory );
                final MessageResourcesFactory factoryObject = MessageResourcesFactory.createFactory();

                final MessageResources resources = factoryObject.createResources( mrcs[i].getParameter() );
                resources.setReturnNull( mrcs[i].getNull() );

                servlet.getServletContext().removeAttribute( mrcs[i].getKey() + config.getPrefix() );
                servlet.getServletContext().setAttribute( mrcs[i].getKey() + config.getPrefix(), resources );
            }
        }

    }

    /**
     * Initilisation du request Processor, permet la gestion des dates
     * 
     * @param servlet : Servlet
     * @param config : config
     */
    public static void initRequestProcessor( final ActionServlet servlet, final ModuleConfig config )
    {
        if ( config.getControllerConfig().getProcessorClass().equals( "org.apache.struts.action.RequestProcessor" ) )
        {
            config.getControllerConfig().setProcessorClass( "org.squale.welcom.struts.action.WRequestProcessor" );
        }
        else
        {
            logStartup.warn( "Attention, vous n'utilisez pas le RequestProcessor de Welcom, risque possible de problème avec la gestion des dates, et des war multiple" );
        }
    }

    /**
     * Initialise les rederers de la charte graphique
     */
    public static void initRender()
    {
        logStartup.info( "Charte : " + WelcomConfigurator.getCharte() );

    }

    /**
     * Initilise les defaults
     */
    public static void initDefaults()
    {
        // Positionne les Dates en GMT, et le format Date/Heure
        if ( !Util.isEquals( WelcomConfigurator.getMessage( WelcomConfigurator.DEFAULT_TIMEZONE ), "none" ) )
        {
            logStartup.info( "Le TimeZone est forcé a la valeur (Clef : default.timezone dans le WelcomResource) : "
                + WelcomConfigurator.getMessage( WelcomConfigurator.DEFAULT_TIMEZONE ) );
            java.util.TimeZone.setDefault( java.util.TimeZone.getTimeZone( WelcomConfigurator.getMessage( WelcomConfigurator.DEFAULT_TIMEZONE ) ) );
        }
        // Init du package Util
        Util.initialize();
    }

    /**
     * @return retourne la version de Welcom.
     */
    public static String about()
    {

        return "Version  2.4.4 - 06/11/2007 09:19";

    }

}