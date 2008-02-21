/*
 * Créé le 7 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.interpel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.airfrance.welcom.outils.rsh.RshClient;
import com.airfrance.welcom.outils.rsh.RshClientFactory;

/**
 *
 * Mise en place d'un transfert de flux interpel
 * Cette solution repose sur un transfert RSH et utilise les commons-net de jakarta
 *
 * Exemple d'utilisation
 * <code>
 * ipc = new InterpelClient("com.airfrance.interpel.resources.Interpel"); // Initalisation avec un fichier de properties
 * ipc.sendFileStream("hello","je me marre","FLUX_NAME",Site.TLS);
 * ipc=null;
 * </code>
 * @deprecated
 * @author M327837
 */
public class InterpelClient {
    /** logger */
    private static Log log = LogFactory.getLog(InterpelClient.class);


    /** Configuration du mode Interpel */
    private InterpelConfig ipc = null;

    /** Trace de l'appel interpel */
    private String lastTrace = null;

    /** Nom de l'utilisateur locale de machine */
    private String userlocal = null;

    /**
     * Initilisation de l'interperlClient avec la config par defaut
     * Utilisation non recommandé, Utiliser plutot un fichier de config
     * @param ipc
     */
    public InterpelClient() {
        this.ipc = new InterpelConfig();
        init();
    }

    /**
     * Initilisation de l'interperlClient avec la config en parametre
     * @param pIpc : Config ...
     */
    public InterpelClient(final InterpelConfig pIpc) {
        this.ipc = pIpc;
        init();
    }

    /**
    * Initialisation de l'interperlClient via une stream, le fichier de config
    * @param is : Fichier properties de configuration
    * @throws InterpelConfigException : Probleme a la lecture la stream
    * @throws IOException : Probleme sur l'ecriture de la stream
    */
    public InterpelClient(final InputStream is) throws InterpelConfigException, IOException {
        if (is == null) {
            throw new InterpelConfigException("La stream est nulle");
        }

        final Properties prop = new Properties();
        prop.load(is);
        is.close();
        this.ipc = new InterpelConfig(prop);
        init();
    }

    /**
     * Initialisation de l'interperlClient avec une resource ou nom de fichier pour l'initialisation
     * @param ressource :Fichier de recources
     * @throws InterpelConfigException : Probleme sur la config
     * @throws IOException : Probleme sur l'ecriture de la stream
     */
    public InterpelClient(final String ressource) throws InterpelConfigException, IOException {
        if (ressource == null) {
            throw new InterpelConfigException("La ressource est nulle");
        }

        InputStream is = null;
        final Properties prop = new Properties();

        // Test s'il trouve le fichier
        final File f = new File(ressource);

        if (f.exists()) {
            is = new FileInputStream(f);

            if (is != null) {
                prop.load(is);
                is.close();
            }
        } else {
            String name = ressource.replace('.', '/');
            name = name + ".properties";

            try {
                is = getClass().getClassLoader().getResourceAsStream(name);

                if (is != null) {
                    prop.load(is);
                    is.close();
                }
            } catch (final Throwable throwable) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (final Throwable throwable1) {
                        ;
                    }
                }
            }
        }

        this.ipc = new InterpelConfig(prop);
        init();
    }

    /**
     * Init
     *
     */
    private void init() {
        userlocal = System.getProperty("user.name");
    }

    /**
     * Envoie d'un fichier interpel
     * @param fileName : Nom du fichier lors du depot  interpel
     * @param fileData : Données du fichier
     * @param fluxName : Nom du flux interpel
     * @param site : Définit sur quel site en desire envoyer le fichier
     * @throws IOException : Erreur lors dans l'ecriture dans la stream
     */
    public void sendFileStream(final String fileName, final byte fileData[], final String fluxName, final Site site) throws IOException {
        RshClient rsh = null;

        try {
            if ((userlocal == null) || (userlocal.length() == 0)) {
                throw new IOException("System Property 'user.name' not define\n");
            }

            if (site == null) {
                throw new IOException("La variable site est indefinit\n");
            }

            rsh = RshClientFactory.getRshClient(ipc.getHost(), ipc.getUser(), userlocal);

            // Initilisation du RshClient
            log.debug("Connection sur :" + ipc.getHost() + "-" + ipc.getUser() + "-" + userlocal);

            // Se postionne sur le repertoire interpel de depot de fichiers     
            int result = rsh.changeDirectory(ipc.getPath());

            if (result != 0) {
                if (rsh.isLastStreamNull()) {
                    throw new IOException("La commande a renvoyer un code erreur :" + result);
                } else {
                    throw new IOException(rsh.getLastStream());
                }
            }

            // Envoie le fichier
            result = rsh.sendFile(fileName, fileData);

            if (result != 0) {
                if (rsh.isLastStreamNull()) {
                    throw new IOException("La commande a renvoyer un code erreur :" + result);
                } else {
                    throw new IOException(rsh.getLastStream());
                }
            }

            // Lance la commande d'envoie
            result = rsh.executecmd(ipc.getRpx() + " " + fluxName + " " + site + " " + rsh.getFullPath(fileName));

            if (result != 0) {
                if (rsh.isLastStreamNull()) {
                    throw new IOException("La commande a renvoyer un code erreur :" + result);
                } else {
                    throw new IOException(rsh.getLastStream());
                }
            }
        } catch (final IOException ioe) {
            throw ioe;

            // Ne fais rien a ce niveau
        } finally {
            // Stocke la trace
            if (rsh != null) {
                lastTrace = rsh.dumpMessage();
            }
        }
    }

    /**
     * Envoie d'un fichier interpel
     * @param fileName : Nom du fichier lors du depot  interpel
     * @param fileData : Données du fichier
     * @param fluxName : Nom du flux interpel
     * @throws IOException : Probleme d'ecriture dans la stream
     */
    public void sendFileStream(final String fileName, final byte fileData[], final String fluxName) throws IOException {
        
        if (ipc.getSite() == null) {
            String str="La variable 'site' n'est pas definit dans le fichier de properties, ";
                   str+="Utilisez la fonction SendFile en precisiant le site si vous ne voulez pas la mettre dans le fichier de properties\n";
            throw new IOException(str);
        }

        sendFileStream(fileName, fileData, fluxName, ipc.getSite());
    }

    /**
     * Envoie d'un fichier interpel
     * @param fileName : Nom du fichier lors du depot  interpel
     * @param fileData : Données du fichier
     * @param fluxName : Nom du flux interpel
     * @throws IOException  : Probleme d'ecriture dans la stream
     */
    public void sendFileStream(final String fileName, final String fileData, final String fluxName) throws IOException {
        sendFileStream(fileName, fileData.getBytes(), fluxName);
    }

    /**
     * Envoie d'un fichier interpel
     * @param fileName : Nom du fichier lors du depot  interpel
     * @param fileData : Données du fichier
     * @param fluxName : Nom du flux interpel
     * @param site :  Définit sur quel site en desire envoyer le fichier
     * @throws IOException  : Probleme d'ecriture dans la stream
     */
    public void sendFileStream(final String fileName, final String fileData, final String fluxName, final Site site) throws IOException {
        sendFileStream(fileName, fileData.getBytes(), fluxName, site);
    }

    /**
     * 
     * @return Retourne la trace du transfert Interpel
     */
    public String getLastTrace() {
        return lastTrace;
    }

    /**
     * 
     * @param user Remplace la signature de l'utilisateur local
     * Attention ne fonnctionne pas sur UNIX
     */
    public void forceUserLocal(final String user) {
        userlocal = user;
    }
}