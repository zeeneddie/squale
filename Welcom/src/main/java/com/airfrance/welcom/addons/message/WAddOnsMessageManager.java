/*
 * Créé le 26 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.message;

import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.MessageResourcesConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;

import com.airfrance.welcom.addons.config.AddonsException;
import com.airfrance.welcom.addons.config.WAddOns;
import com.airfrance.welcom.addons.config.WIAddOns;
import com.airfrance.welcom.addons.message.bd.BdMessageManager;
import com.airfrance.welcom.addons.message.synchro.TaskCheckUpdateMessageRessources;
import com.airfrance.welcom.outils.Util;
import com.airfrance.welcom.outils.WelcomConfigurator;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WAddOnsMessageManager
    extends WAddOns
{

    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Singleton */
    private static WAddOnsMessageManager me = null;

    /** Addons MessageManager */
    public final static String ADDONS_MESSAGEMANAGER_NAME = "ADDONS_MessageMANAGEMENT";

    /** Addons MessageManager */
    private final static String ADDONS_MESSAGEMANAGER_DISPLAYNAME = "Gestion des messages";

    /** Addons MessageManager */
    private final static String ADDONS_MESSAGEMANAGER_VERSION = "2.2";

    /** Thread de mise a jour des libs */
    private Timer synchroTimer = null;

    /**
     * @return Affichage
     */
    public String getDisplayName()
    {
        return ADDONS_MESSAGEMANAGER_DISPLAYNAME;
    }

    /**
     * @return nom de l'addon
     */
    public String getName()
    {
        return ADDONS_MESSAGEMANAGER_NAME;
    }

    /**
     * @return Version
     */
    public String getVersion()
    {
        return ADDONS_MESSAGEMANAGER_VERSION;
    }

    /**
     * @see com.airfrance.welcom.addons.config.WIAddOns#init(org.apache.struts.action.ActionServlet,
     *      org.apache.struts.config.ModuleConfig)
     */
    public void init( final ActionServlet servlet, final ModuleConfig config )
        throws AddonsException
    {

        final MessageResourcesConfig mrcs[] = config.findMessageResourcesConfigs();
        for ( int i = 0; i < mrcs.length; i++ )
        {
            if ( ( mrcs[i].getFactory() != null )
                && ( mrcs[i].getParameter() != null )
                && ( mrcs[i].getFactory().equals( "com.airfrance.welcom.struts.message.WPropertyMessageResourcesFactory" ) )
                && ( mrcs[i].getKey().equals( Globals.MESSAGES_KEY ) ) )
            {

                mrcs[i].setFactory( "com.airfrance.welcom.addons.message.MessageResourcesFactoryAddons" );
                final String factory = mrcs[i].getFactory();
                MessageResourcesFactory.setFactoryClass( factory );
                final MessageResourcesFactory factoryObject = MessageResourcesFactory.createFactory();

                final MessageResources resources = factoryObject.createResources( mrcs[i].getParameter() );
                resources.setReturnNull( mrcs[i].getNull() );

                servlet.getServletContext().removeAttribute( mrcs[i].getKey() + config.getPrefix() );
                servlet.getServletContext().setAttribute( mrcs[i].getKey() + config.getPrefix(), resources );
            }
        }

        BdMessageManager.getInstance().init();

        // Ajout des mappings
        initMappings( servlet, config );

        // Init de la syncho du clonnage ....
        initSynchroUpdate( servlet );

        setLoaded( true );
    }

    /**
     * Termine le Thread de mise a jour des libellés
     * 
     * @see com.airfrance.welcom.addons.config.WIAddOns#destroy()
     */
    public void destroy()
    {

        if ( synchroTimer != null )
        {
            synchroTimer.cancel();
            logStartup.info( " Arrêt de la synchonisation des messages sur les clonnes" );
        }
    }

    /**
     * Initilise tle thread de verification de mise a jour des libellé si on est clonné.
     * 
     * @param servlet : servlet
     */
    public void initSynchroUpdate( final ActionServlet servlet )
    {

        if ( Util.isTrue( WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_MESSAGE_MANAGER_CLONNAGE_ENABLED ) ) )
        {

            synchroTimer = new Timer( true );

            long s = 0;
            try
            {
                s =
                    Long.parseLong( WelcomConfigurator.getMessage( WelcomConfigurator.ADDONS_MESSAGE_MANAGER_REFRESH_DELAY_MS ) );
            }
            catch ( final NumberFormatException e )
            {
                logStartup.warn( "Probleme sur l'initialisation du timer de synchro, passage sur 5mn" );
                s = 300000; // 5mn
            }

            synchroTimer.scheduleAtFixedRate( new TaskCheckUpdateMessageRessources( servlet.getServletContext() ), 0, s );

            logStartup.info( "Initilisation de la synchronisation des libéllés sur les serveurs clonnés" );

        }

    }

    /**
     * Initialise le mapping du profil et du profilsaccesskey
     * 
     * @param servlet : Servlet
     * @param config : Module Config
     * @throws AddonsException Probleme sur l'initalisation des mappings
     */
    public void initMappings( final ActionServlet servlet, final ModuleConfig config )
        throws AddonsException
    {
        // Liste des messages
        initFormBeanConfig( config, "wMessagesBean", "com.airfrance.welcom.addons.message.bean.MessagesBean" );
        logStartup.info( "Ajout du bean 'wMessagesBean' pour l'action '/wMessageList.do'" );
        initMappings( servlet, config, "wMessagesBean", "addons.messageManager.jsp.messages", "/wMessageList",
                      "com.airfrance.welcom.addons.message.action.WMessageListeAction" );
        logStartup.info( "Ajout du mapping '/wMessageList.do' pour afficher la liste des messages" );

    }

    /**
     * @return Retourne le sigleton
     */
    public static WIAddOns getAddOns()
    {
        if ( me == null )
        {
            me = new WAddOnsMessageManager();
        }
        return me;
    }

}
