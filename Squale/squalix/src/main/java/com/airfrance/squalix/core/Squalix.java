//Source file: D:\\cc_views\\squale_v0_0_act\\squale\\src\\squalix\\src\\com\\airfrance\\squalix\\core\\Squalix.java

package com.airfrance.squalix.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.jraf.bootstrap.initializer.Initializer;
import com.airfrance.squalix.messages.Messages;

/**
 * Lance l'application Squalix.
 * <br />
 * Ceci consiste en quelques opérations simples :
 * <ul>
 * <li>initialisation du socle JRAF,</li>
 * <li>lecture des paramètres de lancement,</li>
 * <li>instanciation d'un scheduler si l'exécution peut-être lancée.</li>
 * </ul>
 * <br />
 * Le lancement du scheduler est synchrone, ce qui bloque l'application tant que le scheduler tourne.
 * <br />
 * Pour plus de renseignements sur le fonctionnement du moteur de tâches, reportez-vous à la 
 * javadoc des classes <code>Scheduler</code> et <code>ResourcesManager</code>.
 * 
 * @see com.airfrance.squalix.core.Scheduler
 * @see com.airfrance.squalix.core.ResourcesManager
 * 
 * @author m400842
 * @version 1.0
 */
public class Squalix {

    /**
     * Logger
     */
    private static Log mLOGGER = null;

    /**
     * Clé du site hébergeant l'application
     */
    private static String mSite = null;

    /**
     * Chemin du fichier de configuration
     */
    private static String mConfigFile = null;

    /**
     * Méthode main.
     * @param pArgs arguments de lancement.
     * @roseuid 42918D3702A5
     */
    public static void main(final String pArgs[]) {
        // lancement de l’initialisation JRAF
        String rootPath = pArgs[0];
        String configFile = "/config/providers-config.xml";
        Initializer init = new Initializer(rootPath, configFile);
        init.initialize(); 
        // Maintenant que le socle JRAF est initialisé, on peut créer un logger
        mLOGGER = LogFactory.getLog(Squalix.class);
        if (null != pArgs && pArgs.length > 0) {
            try {
                // On trie les paramètres
                getParameters(pArgs);
                Scheduler scheduler = null;
                if (null == mSite) {
                    // Si aucun site n'est spécifié, on quitte l'application
                    mLOGGER.fatal(Messages.getString("main.missing_parameters"));
                } else {
                    scheduler = new Scheduler(new Long(mSite).longValue());
                    // on lance le scheduleur
                    scheduler.start();
                }
            } catch (Exception e) {
                // Si un problème apparaît dans la récupération de la configuration
                // ou des paramètres, on loggue et on quitte
                mLOGGER.fatal("Fatal error", e);
            }
        } else {
            mLOGGER.fatal(Messages.getString("main.missing_parameters"));
        }
    }

    /**
     * Récupère les paramètres de la ligne de commande 
     * @param pArgs liste des paramètres.
     * @roseuid 42CE4F340204
     */
    private static void getParameters(final String pArgs[]) {
        for (int i = 0; i < pArgs.length; i++) {
            if (pArgs[i].equals(Messages.getString("main.site_parameter"))) {
                mSite = pArgs[++i];
            } else if (pArgs[i].equals(Messages.getString("main.configFile_parameter"))) {
                mConfigFile = pArgs[++i];
            }
        }
    }
}
