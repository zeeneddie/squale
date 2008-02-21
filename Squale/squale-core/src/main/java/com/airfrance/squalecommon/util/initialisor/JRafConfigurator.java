package com.airfrance.squalecommon.util.initialisor;

import java.io.File;
import java.util.HashMap;

import com.airfrance.jraf.bootstrap.initializer.Initializer;
import com.airfrance.jraf.bootstrap.locator.ProviderLocator;

/**
 * @author M400843
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class JRafConfigurator {
    /**
     * Instance singleton
     */
    private static JRafConfigurator instance = null;

    /** initialisation du singleton */
    static {
        instance = new JRafConfigurator();
    }

    /**
     * Constructeur prive
     * @throws JrafDaoException
     */
    private JRafConfigurator() {
        /** chemin racine repertoire - racine du classpath */
        File rootPath = new File(Thread.currentThread().getContextClassLoader().getResource("config/providers-config.xml").getPath()).getParentFile();
        
        /** chemin du fichier de configuration relatif au classpath */
        String configFile = "/providers-config.xml";
        
        Initializer init = new Initializer(rootPath.getAbsolutePath(), configFile);
        ProviderLocator.setProviderLocator(new ProviderLocator(new HashMap()));
        init.initialize();
    }

    /**
     * Retourne un singleton du DAO
     * @return singleton du DAO
     */
    public static JRafConfigurator initialize() {
        return instance;
    }
}
