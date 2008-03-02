/*
 * Créé le 6 mars 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.struts.webServer;

import java.net.URL;
import java.util.Date;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class WebFile
{

    /** Date de derniere modification */
    private Date lastDate = null;

    /** Réelle URL */
    private URL url = null;

    /** class path */
    public static final int TYPE_CLASSPATH = 0;

    /** servlet */
    public static final int TYPE_SERVLET = 1;

    /** distant */
    public static final int TYPE_DISTANT = 2;

    /** type */
    private int type = 0;

    /**
     * contructeur
     * 
     * @param pType type TYPE_CLASSPATH / TYPE_SERVLET / TYPE_DISTANT
     */

    public WebFile( int pType )
    {
        this.type = pType;
    }

    /**
     * contructeur
     */

    public WebFile()
    {
    }

    /**
     * @return accesseur
     */
    public Date getLastDate()
    {
        return lastDate;
    }

    /**
     * @return accesseur
     */
    public URL getUrl()
    {
        return url;
    }

    /**
     * @param date date
     */
    public void setLastDate( Date date )
    {
        lastDate = date;
    }

    /**
     * @param pUrl url
     */
    public void setUrl( URL pUrl )
    {
        this.url = pUrl;
    }

}
