/*
 * Créé le 8 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.rsh;

import java.io.IOException;

/**
 * @author M327837
 *
 * Pour changer le modèle de ce commentaire de type généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public abstract class RshClient {
    
    /** Buffer */
    protected final static int MAX_BUF_RSH = 1024;
    /** Repertoire */
    protected String directory = null;
    /** String buffer pour la trace */
    private final StringBuffer sb = new StringBuffer();
    /** Serveur */
    protected String serveur = null;
    /** login distant */
    protected String loginDistant = null;
    /** Login local */
    protected String loginLocal = null;
    /** Valeur de retour de l'appel de la fonction */
    protected String lastReturnStream = null;
    /** Derniere erreur recontré */
    protected String lastErrorStream = null;

    /**
     *
     * @param pServeur : Serveur
     * @param pLoginDistant : Logon Distant
     * @param pLoginLocal : Login Local
     */
    public RshClient(final String pServeur, final String pLoginDistant, final String pLoginLocal) {
        this.serveur = pServeur;
        this.loginDistant = pLoginDistant;
        this.loginLocal = pLoginLocal;
    }

    /**
     * Retourne le chemin complet du fichier
     * @param filename : Nom du fichier
     * @return Le chemin du fichier a partir du change directory
     */
    public String getFullPath(final String filename) {
        if ((directory == null) || ((directory != null) && (directory.length() == 0))) {
            return filename;
        } else {
            if (!directory.endsWith("/")) {
                return directory + "/" + filename;
            } else {
                return directory + filename;
            }
        }
    }

    /**
     * Change le repertoire courant par celui spécifié en parmetre
     * Fonctione seulement en absolu
     * @param pDirectory Chemin
     * @throws IOException probleme sur l'execution de la commande
     * @return retour de commande unix
     */
    public int changeDirectory(final String pDirectory) throws IOException {
        final int result = executecmd("cd " + pDirectory);

        // si le repertoire existe sur le serveur 
        this.directory = pDirectory;

        return result;
    }

    /**
     * Supprime le fichier passé en parametre
     * @param fileName : Nom du fichier
     * @throws IOException probleme sur l'execution de la commande
     * @return retour de commande unix
     */
    public int removeFile(final String fileName) throws IOException {
        return executecmd("/bin/rm " + getFullPath(fileName));
    }

    /**
     * Envoie d'un fichier en RSH
     * @param fileName : nom du fichier poru le serveur
     * @param data : donnée
     * @throws IOException : Retourn s'il y a une erreur
     * @return retour de commande unix
     */
    public int sendFile(final String fileName, final String data) throws IOException {
        return sendFile(fileName, data.getBytes());
    }

    /**
     * Retourne le resultat d'un commande Unix en rsh
     * Attention : le buffer est limité a 1024.
     * @param cmd : Commande unix
     * @throws IOException : Retourne le buffer rcp ou bien une erreur d'executio
     * @return retour de commande unix
     */
    public int executecmd(final String cmd) throws IOException {
        return executecmd(cmd, null);
    }

    /**
     * Retourne le resultat d'un commande Unix en rsh
     * @param cmd : Commande unix
     * @param buff : Ecrit dans l'entree standard le contenu
     * @throws IOException : Retourne le buffer rcp ou bien une erreur d'executio
     * @return retour de commande unix
     */
    public abstract int executecmd(String cmd, byte buff[]) throws IOException;

    /**
     * Retourne la liste des message echangé avec le serveur
     * @return Trace
     */
    public String dumpMessage() {
        return sb.toString();
    }

    /**
     * Retoune le retour sur la sortie standard
     * @return dernier erreur
     */
    public String getLastReturnStream() {
        return lastReturnStream;
    }

    /**
     * Retoune le retour sur la sortie d'erreur
     * @return Retoune le retour sur la sortie d'erreur
     */
    public String getLastErrorStream() {
        return lastErrorStream;
    }

    /**
     * Retoune si les streams LastError et LastReturn sont nulle
     * @return Vrais si les streams LastError et LastReturn sont nulle
     */
    public boolean isLastStreamNull() {
        return getLastStream() == null;
    }

    /**
     * Retourne ce qui est sortie sur la console
     * @return ce qui est sortie sur la console
     */
    public String getLastStream() {
        String lastStream = null;

        if ((getLastErrorStream() != null) && (getLastReturnStream() != null)) {
            lastStream = getLastErrorStream() + getLastReturnStream();
        } else {
            if (getLastErrorStream() != null) {
                lastStream = getLastErrorStream();
            } else {
                lastStream = getLastReturnStream();
            }
        }

        return lastStream;
    }

    /**
     * Ajoute un message
     * @param msg : Ajoute un message pour la trace
     */
    public void addMessage(final byte msg[]) {
        addMessage(new String(msg));
    }

    /**
     * Ajoute un message
     * @param msg : Ajoute un message pour la trace
     */
    public void addMessage(final String msg) {
        if ((sb.length() > 1) && (sb.charAt(sb.length() - 1) != '\n')) {
            sb.append("\n");
        }

        sb.append(msg);
    }

    /**
     * Envoi un fichier
     * @param fileName : Nom du fichier
     * @param fileData : Données
     * @return  retour de commande unix
     * @throws IOException : Retourne s'il y a une erreur 
     */
    public int sendFile(final String fileName, final byte fileData[]) throws IOException {
        // le >! permet de n pas verifier que le fichier existe e l'ecras autoamtiquement
        final String cmd = "/bin/cat - >! " + getFullPath(fileName);

        return executecmd(cmd, fileData);
    }
}