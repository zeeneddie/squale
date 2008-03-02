/*
 * Créé le 7 oct. 04
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.outils.interpel;

import java.util.Properties;

/**
 * Classe de config pour transfert Interpel <br>
 * La classe peut etre autonome en utilisant des valeur par defaut.<br>
 * Cependant nous conseillons l'utilisation d'un fichier de properties<br>
 * permettant une meilleure souplesse pour l'avenir.<br>
 * Les informations connues dans ce fichier sont definit par le BT<br>
 * <br>
 * 
 * <pre>
 * Fichier properties :
 * host=xfiles.france.airfrance.fr
 * rpx=/home/pelican/peliunix/run_time/etc/rpx
 * path=/dsk1/pelican/data
 * user=peluser
 * </pre>
 * 
 * @deprecated
 * @author M327837
 */
public class InterpelConfig
{
    /** Machine Interpel par defaut */
    private String host = "xfiles.france.airfrance.fr";

    /** Executable interpel par default */
    private String rpx = "/home/pelican/peliunix/run_time/etc/rpx";

    /** Site a deployer */
    private Site site = null;

    /** Repertoire a postionner pour Interpel */
    private String path = "/dsk1/pelican/data";

    /** Nom d'utilisateur pour effectuer le rsh */
    private String user = "peluser";

    /**
     * Initialise la config pour Interperl Utilise les parametres en dur : host : xfiles.france.airfrance.fr rpx :
     * /home/pelican/peliunix/run_time/etc/rpx path : /dsk1/pelican/data user : peluser
     */
    public InterpelConfig()
    {
    }

    /**
     * Initialise la config pour Interperl
     * 
     * @param prop : Fichier de poerpties de configuration d'Interpel
     * @throws InterpelConfigException : Probleme de configuration dans le fichier perperties
     */
    public InterpelConfig( final Properties prop )
        throws InterpelConfigException
    {
        if ( prop.containsKey( "host" ) )
        {
            host = prop.getProperty( "host" );
        }
        else
        {
            throw new InterpelConfigException( "La variable 'host' n'est pas definit\n" );
        }

        if ( prop.containsKey( "rpx" ) )
        {
            rpx = prop.getProperty( "rpx" );
        }
        else
        {
            throw new InterpelConfigException( "La variable 'rpx' n'est pas definit\n" );
        }

        if ( prop.containsKey( "site" ) )
        {
            try
            {
                site = Site.getSite( prop.getProperty( "site" ) );
            }
            catch ( final SiteException e )
            {
                throw new InterpelConfigException( e.getMessage() );
            }
        }

        if ( prop.containsKey( "path" ) )
        {
            path = prop.getProperty( "path" );
        }
        else
        {
            throw new InterpelConfigException( "La variable 'path' n'est pas definit\n" );
        }

        if ( prop.containsKey( "user" ) )
        {
            user = prop.getProperty( "user" );
        }
        else
        {
            throw new InterpelConfigException( "La variable 'user' n'est pas definit\n" );
        }
    }

    /**
     * @return Retourne la machine pour lequel Interpel est lancé
     */
    public String getHost()
    {
        return host;
    }

    /**
     * @return Retourne le chamin pour lancer Interpel
     */
    public String getPath()
    {
        return path;
    }

    /**
     * @return retourne l'executable qui est lancé pour Interpel
     */
    public String getRpx()
    {
        return rpx;
    }

    /**
     * @return retourne le site Interpel pour transefrer le fichier
     */
    public Site getSite()
    {
        return site;
    }

    /**
     * @return Recupere le user Utliser pour Interpel
     */
    public String getUser()
    {
        return user;
    }

    /**
     * @param string Machine Interpel
     */
    public void setHost( final String string )
    {
        host = string;
    }

    /**
     * @param string Chemin pour poser le fichier
     */
    public void setPath( final String string )
    {
        path = string;
    }

    /**
     * @param string Executable pour lancer le transfert
     */
    public void setRpx( final String string )
    {
        rpx = string;
    }

    /**
     * @param pSite : Site Interpel
     */
    public void setSite( final Site pSite )
    {
        this.site = pSite;
    }

    /**
     * @param string : User pour le transfert Interpel
     */
    public void setUser( final String string )
    {
        user = string;
    }
}