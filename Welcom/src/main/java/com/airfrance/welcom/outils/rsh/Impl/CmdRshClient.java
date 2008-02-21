/*
 * Créé le 8 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.rsh.Impl;

import java.io.IOException;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.IOUtils;

import com.airfrance.welcom.outils.rsh.RshClient;

/**
 * @author M327837
 *
 * Simule du rsh
 */
public class CmdRshClient extends RshClient {
    /** programme rsh */
    private final static String RSH_FILE = "/bin/rsh";
    /** Valeur par defult de retour */
    private final static int DEFAULT_EXIT_VAL=35;
    /** Commande */
    private String rshCmd = null;

    /**
     * @param serveur : Serveur poru le rsh
     * @param loginDistant : login distant
     * @param loginLocal : login local
     */
    public CmdRshClient(final String serveur, final String loginDistant, final String loginLocal) {
        super(serveur, loginDistant, loginLocal);
        rshCmd = RSH_FILE + " -l " + loginDistant + " " + serveur + " ";
    }

    /**
     * Retourne le resultat d'un commande Unix en rsh
     * Attention : le buffer est limité a 1024.
     * @param cmd : Commande unix
     * @param buff : Ecrit dans l'entree standard le contenu
     * @throws IOException : Retourne le buffer rcp ou bien une erreur d'execution
     * @return resultat unix
     */
    public int executecmd(final String cmd, final byte buff[]) throws IOException {
        lastReturnStream = null;
        lastErrorStream = null;

        Process rsh = null;
        int exitVal = DEFAULT_EXIT_VAL;

        // Stocke ce que l'on a envoyer
        final String cmdsend = rshCmd + cmd;
        addMessage(cmdsend + "\n");

        try {
            rsh = Runtime.getRuntime().exec(cmdsend);

            // Si on a quelquechose dans le buffer
            if (buff != null) {
                CopyUtils.copy(buff, rsh.getOutputStream());
                addMessage(buff);
                rsh.getOutputStream().close();
            }

            exitVal = rsh.waitFor();

            if (rsh.getInputStream() != null) {
                lastReturnStream = IOUtils.toString(rsh.getInputStream());
                addMessage(lastReturnStream);
            }

            if (rsh.getErrorStream() != null) {
                lastErrorStream = IOUtils.toString(rsh.getErrorStream());

                if (lastErrorStream.length() > 0) {
                    addMessage(lastErrorStream);

                    return 1;
                }
            }
        } catch (final IOException ioe) {
            addMessage(ioe.getMessage());
            throw ioe;
        } catch (final InterruptedException e) {
            addMessage(e.getMessage());
            throw new IOException(e.getMessage());
        }

        if (exitVal == 0) {
            addMessage(">OK\n");
        }

        return exitVal;
    }
}