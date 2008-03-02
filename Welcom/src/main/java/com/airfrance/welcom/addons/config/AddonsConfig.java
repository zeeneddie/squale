/*
 * Créé le 25 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import com.airfrance.welcom.addons.access.WAddOnsAccessManager;
import com.airfrance.welcom.addons.message.WAddOnsMessageManager;
import com.airfrance.welcom.addons.spell.WAddOnsSpellManager;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;
import com.airfrance.welcom.outils.jdbc.WJdbc;
import com.airfrance.welcom.outils.jdbc.WJdbcMagic;
import com.airfrance.welcom.outils.jdbc.WStatement;
import com.airfrance.welcom.outils.jdbc.wrapper.ResultSetUtils;
import com.airfrance.welcom.struts.plugin.WelcomContext;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class AddonsConfig
{

    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Sql code de table not found */
    public final static int SQL_CODE_TABLE_NOT_FOUND = 942;

    /** Table pour la gestion des plugins */
    public final static String WEL_ADDONS = WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_TABLE_NAME );

    /** Table de droits d'accés */
    public final static String WEL_ACCESSKEY =
        WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_ACCESS_MANAGER_TABLE_ACCESSKEY );

    /** Table de profil */
    public final static String WEL_PROFILE =
        WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_ACCESS_MANAGER_TABLE_PROFILE );

    /** Table de droits d'accées par profil (referentiel) */
    public final static String WEL_PROFILE_ACCESSKEY_INT =
        WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_ACCESS_MANAGER_TABLE_PROFILEACCESSKEYINT );

    /** Table de droits d'accées par profil (utilisateur) */
    public final static String WEL_PROFILE_ACCESSKEY =
        WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_ACCESS_MANAGER_TABLE_PROFILEACCESSKEY );

    /** Table de droits d'accées par profil (utilisateur) */
    public final static String WEL_MSGLIBELLE =
        WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_MESSAGE_MANAGER_TABLE_LIBELLES );

    /** Liste des addons */
    private final ArrayList listAddons = new ArrayList();

    /** pseudo singleton pour le clonnage */
    private static HashMap htSingleton = new HashMap();

    /**
     * @return Pseudo Singleton /recupere sous la clef JNDI : java:comp/env/welcomConfContext
     */
    public static AddonsConfig getInstance()
    {

        AddonsConfig ac;
        final String welcomContextName = WelcomContext.getWelcomContextName();

        synchronized ( htSingleton )
        {
            ac = (AddonsConfig) htSingleton.get( welcomContextName );
            if ( ac == null )
            {
                ac = new AddonsConfig();
                htSingleton.put( welcomContextName, ac );
            }
        }
        return ac;

    }

    /**
     * @return la liste des addons
     */
    private ArrayList getListAddons()
    {
        return listAddons;
    }

    /**
     * @see com.airfrance.welcom.WIAddOns#init()
     */
    public static void init( final ActionServlet servlet, final ModuleConfig config )
    {

        // Initialise les differents Addons
        final String accessManagerEnabled =
            WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_ACCESS_MANAGER_ENABLED );
        if ( Util.isTrue( accessManagerEnabled ) )
        {
            try
            {
                AddonsConfig.getInstance().initAddons( WAddOnsAccessManager.class, servlet, config );
            }
            catch ( final AddonsException e )
            {
                logStartup.info( "Erreur du le chargement de l'addons : " + e.getMessage() );
            }
        }
        final String messageManagerEnabled =
            WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_MESSAGE_MANAGER_ENABLED );
        if ( Util.isTrue( messageManagerEnabled ) )
        {
            try
            {
                AddonsConfig.getInstance().initAddons( WAddOnsMessageManager.class, servlet, config );
            }
            catch ( final AddonsException e )
            {
                logStartup.info( "Erreur du le chargement de l'addons : " + e.getMessage() );
            }
        }
        final String spellManagerEnabled =
            WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_SPELL_MANAGER_ENABLED );
        if ( Util.isTrue( spellManagerEnabled ) )
        {
            try
            {
                AddonsConfig.getInstance().initAddons( WAddOnsSpellManager.class, servlet, config );
            }
            catch ( final AddonsException e )
            {
                logStartup.info( "Erreur du le chargement de l'addons : " + e.getMessage() );
            }
        }
    }

    /**
     * Initialisation de l'addons
     * 
     * @param addonsClass : Classe de l'addons
     * @param servlet : Servlet
     * @param config : Config
     * @throws AddonsException : Probleme de chargement
     */
    public void initAddons( final Class addonsClass, final ActionServlet servlet, final ModuleConfig config )
        throws AddonsException
    {

        WIAddOns addons = null;
        try
        {
            addons = (WIAddOns) addonsClass.newInstance();
        }
        catch ( final InstantiationException e1 )
        {
            throw new AddonsException( e1.getMessage() );
        }
        catch ( final IllegalAccessException e1 )
        {
            throw new AddonsException( e1.getMessage() );
        }

        logStartup.info( "-> Init Addons : " + addons.getDisplayName() );

        saveAddons( addons );
        addons.init( servlet, config );
        listAddons.add( addons );
    }

    /**
     * (
     * 
     * @see com.airfrance.welcom.addons.config.WIAddOns#registredAddons(com.airfrance.welcom.outils.jdbc.WJdbc)
     */
    public void registredAddons( final WJdbc jdbc, final WIAddOns addons )
        throws SQLException
    {
        final WStatement sta = jdbc.getWStatement();
        sta.add( "INSERT INTO " + WEL_ADDONS + " (NAME,DISPLAYNAME,VERSION)" );
        sta.add( "values (" );
        sta.addParameter( "?,", addons.getName() );
        sta.addParameter( "?,", addons.getDisplayName() );
        sta.addParameter( "?", addons.getVersion() );
        sta.add( ")" );
        sta.executeUpdate();
        sta.close();
    }

    /**
     * Recupere la config d'un addons
     * 
     * @param addons : Classe Addons
     * @throws AddonsException : Probleme de chargement
     */
    public void saveAddons( final WIAddOns addons )
        throws AddonsException
    {

        WJdbcMagic jdbc = null;
        try
        {
            jdbc = new WJdbcMagic();
            saveAddons( jdbc, addons );

        }
        catch ( final SQLException sqle )
        {
            throw new AddonsException( sqle.getMessage() );
        }
        finally
        {
            if ( ( jdbc != null ) && !jdbc.isClosed() )
            {
                jdbc.close();
            }
        }
    }

    /**
     * Recupere la config d'un addons
     * 
     * @param jdbc : connexion jdbc
     * @param addons : Classe Addons
     * @throws AddonsException : Probleme de chargement
     */
    public void saveAddons( final WJdbc jdbc, final WIAddOns addons )
        throws AddonsException
    {

        try
        {
            final WStatement sta = jdbc.getWStatement();
            /*
             * sta.add("drop table WEL_ADDONS"); sta.executeUpdate(); sta.close(); sta = jdbc.getWStatement();
             */
            sta.add( "select * from " + WEL_ADDONS );
            sta.addParameter( "where NAME=?", addons.getName() );
            final ResultSet rs = sta.executeQuery();
            if ( ( rs != null ) && rs.next() )
            {
                ResultSetUtils.populate( addons, rs );
            }
            else
            {
                registredAddons( jdbc, addons );
                jdbc.commit();
            }
            sta.close();
        }
        catch ( final SQLException sqle )
        {
            // 1146 : MYSQL , 20000 : DERBY
            if ( ( sqle.getErrorCode() == SQL_CODE_TABLE_NOT_FOUND ) || ( sqle.getErrorCode() == 1146 )
                || ( sqle.getErrorCode() == 20000 ) )
            {
                try
                {
                    createTablesAddons( jdbc );
                }
                catch ( final SQLException e )
                {
                    throw new AddonsException( e.getMessage() );
                }
                saveAddons( jdbc, addons );
            }
            else
            {
                throw new AddonsException( sqle.getMessage() );
            }
        }
    }

    /**
     * CREATE TABLE WEL_ADDONS ( NAME VARCHAR2 (30), DISPLAYNAME VARCHAR2 (255), VERSION VARCHAR2 (6), DATE DATETIME) ;
     * 
     * @param jdbc : Connection JDBC
     * @throws SQLException : Probleme sql
     */
    public void createTablesAddons( final WJdbc jdbc )
        throws SQLException
    {
        final WStatement sta = jdbc.getWStatement();
        sta.add( "CREATE TABLE " + WEL_ADDONS + " (" );
        sta.add( "NAME         VARCHAR (30)," );
        sta.add( "DISPLAYNAME  VARCHAR (255)," );
        sta.add( "VERSION      VARCHAR (6)," );
        sta.add( "PARAMETERS   VARCHAR (255))" );
        sta.executeUpdate();
        sta.close();
    }

    /**
     * Appelle la methode destroy sur tout les addons de welcom
     * 
     * @see com.airfrance.welcom.WIAddOns#destroy()
     */
    public static void destroy()
    {
        final Iterator iter = AddonsConfig.getInstance().getListAddons().iterator();
        while ( iter.hasNext() )
        {
            final WIAddOns element = (WIAddOns) iter.next();
            logStartup.info( "-> Arret de l'addons '" + element.getDisplayName() + "'" );
            element.destroy();
        }
    }

}
