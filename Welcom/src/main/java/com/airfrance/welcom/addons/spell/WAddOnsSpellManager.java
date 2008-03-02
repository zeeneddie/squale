/*
 * Créé le 26 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.spell;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

import com.airfrance.welcom.addons.config.AddonsException;
import com.airfrance.welcom.addons.config.WAddOns;
import com.airfrance.welcom.addons.config.WIAddOns;
import com.airfrance.welcom.addons.spell.engine.WSpellChecker;
import com.airfrance.welcom.addons.spell.exception.WSpellCheckerNotFound;
import com.airfrance.welcom.outils.LocaleUtil;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WAddOnsSpellManager
    extends WAddOns
{

    /** logger */
    private static Log logStartup = LogFactory.getLog( "Welcom" );

    /** Singleton */
    private static WAddOnsSpellManager me = null;

    /** Addons SpellManager */
    public final static String ADDONS_SPELL_NAME = "ADDONS_SPELL_MANAGER";

    /** Addons MessageManager */
    private final static String ADDONS_SPELL_DISPLAYNAME = "Verification de l'orthographe";

    /** Addons MessageManager */
    private final static String ADDONS_SPELL_VERSION = "2.2";

    /**
     * @return Affichage
     */
    public String getDisplayName()
    {
        return ADDONS_SPELL_DISPLAYNAME;
    }

    /**
     * @return nom de l'addon
     */
    public String getName()
    {
        return ADDONS_SPELL_NAME;
    }

    /**
     * @return Version
     */
    public String getVersion()
    {
        return ADDONS_SPELL_VERSION;
    }

    /**
     * @see com.airfrance.welcom.addons.config.WIAddOns#init(org.apache.struts.action.ActionServlet,
     *      org.apache.struts.config.ModuleConfig)
     */
    public void init( final ActionServlet servlet, final ModuleConfig config )
        throws AddonsException
    {

        // initialise les dictionnaires
        initDico( servlet );

        // Initialise les mappings;
        initMappings( servlet, config );

        setLoaded( true );
    }

    /**
     * Verification de la presence des dictionnaires
     * 
     * @param servlet : Stockage dans les servlet des dicos
     * @throws AddonsException : Pb sur l'init de l'adddons
     */
    public void initDico( final ActionServlet servlet )
        throws AddonsException
    {

        logStartup.info( "Chargement differé des dictionnaires ... " );

        final Runnable runnable = new Runnable()
        {
            /**
             * @see java.lang.Runnable#run()
             */
            public void run()
            {

                ArrayList locales = LocaleUtil.getAvailableLocales();
                Iterator iter = locales.iterator();

                while ( iter.hasNext() )
                {
                    String locale = (String) iter.next();
                    try
                    {
                        WSpellChecker.getSpellChecker( locale );
                    }
                    catch ( WSpellCheckerNotFound e )
                    {
                        logStartup.fatal( "Impossible de charger le dictionnaire : " + locale );
                    }

                }

            }
        };

        // Demarrage du thread de chargement difeere
        final Thread th = new Thread( runnable );
        th.start();

    }

    /**
     * Initialise le mapping du profil et du profilsaccesskey
     * 
     * @param servlet : Servlet
     * @param config : Module Config
     * @throws AddonsException : Erreur a l'init
     */
    public void initMappings( final ActionServlet servlet, final ModuleConfig config )
        throws AddonsException
    {
        // Liste des messages
        // initFormBeanConfig(config, "wSpellWait", "com.airfrance.welcom.addons.message.bean.wSpellWaiBean");
        // logStartup.info("Ajout du bean 'wMessagesBean' pour l'action '/wMessageList.do'");
        // initFormBeanConfig(config,"wSpellWaitBean","com.airfrance.welcom.addons.spell.bean.WSpellWaitBean");
        // logStartup.info("Ajout du bean 'wSpellWaitBean' pour l'action '/wSpellWait.do'");
        initFormBeanConfig( config, "wSpellCheckerBean", "com.airfrance.welcom.addons.spell.bean.WSpellCheckerBean" );
        logStartup.info( "Ajout du bean 'wSpellCheckerBean' pour l'action '/wSpellChecker.do'" );

        initMappings( servlet, config, "wSpellCheckerBean", "addons.spellManager.jsp.wait", "/wSpellWait",
                      "com.airfrance.welcom.addons.spell.action.WSpellWaitAction", "request" );
        logStartup.info( "Ajout du mapping '/wSpellWait.do' pour afficher la page d'attente de validation d'orthographe" );
        initMappings( servlet, config, "wSpellCheckerBean", "addons.spellManager.jsp.chercker", "/wSpellChecker",
                      "com.airfrance.welcom.addons.spell.action.WSpellCheckerAction", "request" );
        logStartup.info( "Ajout du mapping '/wSpellChecker.do' pour afficher la page du checker" );

    }

    /**
     * Termine le Thread de mise a jour des libellés
     * 
     * @see com.airfrance.welcom.addons.config.WIAddOns#destroy()
     */
    public void destroy()
    {

    }

    /**
     * @return Retourne le sigleton
     */
    public static WIAddOns getAddOns()
    {
        if ( me == null )
        {
            me = new WAddOnsSpellManager();
        }
        return me;
    }

}
