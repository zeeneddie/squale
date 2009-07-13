/**
 * Copyright (C) 2008-2010, Squale Project - http://www.squale.org
 *
 * This file is part of Squale.
 *
 * Squale is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or any later version.
 *
 * Squale is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Squale.  If not, see <http://www.gnu.org/licenses/>.
 */
/*
 * Créé le 6 mars 07
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package org.squale.welcom.struts.webServer;

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
