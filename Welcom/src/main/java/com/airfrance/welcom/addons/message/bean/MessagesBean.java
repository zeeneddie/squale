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
 * Créé le 25 août 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.message.bean;

import java.util.ArrayList;

import com.airfrance.welcom.struts.bean.WActionForm;

/**
 *
 */
public class MessagesBean
    extends WActionForm
{

    /**
     * 
     */
    private static final long serialVersionUID = 5510456587043354722L;

    /** Liste des message */
    private ArrayList listMessages = new ArrayList();

    /** liest des locales */
    private ArrayList listLocales = new ArrayList();

    /** clef de filtre */
    private String messageKeyFiltre = "";

    /** message de filtre */
    private String messageFiltre = "";

    /**
     * @return accesseur
     */
    public ArrayList getListMessages()
    {
        return listMessages;
    }

    /**
     * @param list accesseur
     */
    public void setListMessages( final ArrayList list )
    {
        listMessages = list;
    }

    /**
     * @return accesseur
     */
    public ArrayList getListLocales()
    {
        return listLocales;
    }

    /**
     * @param list accesseur
     */
    public void setListLocales( final ArrayList list )
    {
        listLocales = list;
    }

    /**
     * @return accesseur
     */
    public String getMessageKeyFiltre()
    {
        return messageKeyFiltre;
    }

    /**
     * @param string accesseur
     */
    public void setMessageKeyFiltre( final String string )
    {
        messageKeyFiltre = string;
    }

    /**
     * @return accesseur
     */
    public String getMessageFiltre()
    {
        return messageFiltre;
    }

    /**
     * @param string accesseur
     */
    public void setMessageFiltre( final String string )
    {
        messageFiltre = string;
    }

}
