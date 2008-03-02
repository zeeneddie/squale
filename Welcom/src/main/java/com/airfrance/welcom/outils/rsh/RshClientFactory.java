/*
 * Créé le 13 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.rsh;

import com.airfrance.welcom.outils.rsh.Impl.CmdRshClient;
import com.airfrance.welcom.outils.rsh.Impl.JavaRshClient;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class RshClientFactory
{
    /**
     * Contructeur
     */
    private RshClientFactory()
    {
        super();
    }

    /**
     * Retourne un rsh client en fonction de la plateforme.
     * 
     * @param serveur : Serveur
     * @param loginDistant : Logon Distant
     * @param loginLocal : Login Local
     * @return RshClient en fonction de l'os ... soit full java ou rsh
     */
    public static RshClient getRshClient( final String serveur, final String loginDistant, final String loginLocal )
    {
        if ( System.getProperty( "os.name" ).equals( "SunOS" ) )
        {
            return new CmdRshClient( serveur, loginDistant, loginLocal );
        }
        else
        {
            return new JavaRshClient( serveur, loginDistant, loginLocal );
        }
    }
}