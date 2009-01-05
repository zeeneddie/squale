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
 * Créé le 26 juil. 05
 *
 * Pour changer le modèle de ce fichier généré, allez à :
 * Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
package com.airfrance.welcom.addons.access.excel.object;

import com.airfrance.welcom.outils.Util;

/**
 * @author M327837 Pour changer le modèle de ce commentaire de type généré, allez à :
 *         Fenêtre&gt;Préférences&gt;Java&gt;Génération de code&gt;Code et commentaires
 */
public class ProfileAccessKey
{

    /** Valeur de la clef */
    private String accesskey = "";

    /** Id du profil */
    private String idProfile = "";

    /** Value */
    private String value = "";

    /**
     * @return IdProfile
     */
    public String getIdProfile()
    {
        return idProfile;
    }

    /**
     * @return key
     */
    public String getAccesskey()
    {
        return accesskey;
    }

    /**
     * @return value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param string Idprofile
     */
    public void setIdProfile( final String string )
    {
        idProfile = string;
    }

    /**
     * @param string key
     */
    public void setAccesskey( final String string )
    {
        accesskey = string;
    }

    /**
     * @param string value
     */
    public void setValue( final String string )
    {
        value = string;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals( final Object obj )
    {

        return Util.isEquals( this.getIdProfile(), ( (ProfileAccessKey) obj ).getIdProfile() )
            && Util.isEquals( this.getAccesskey(), ( (ProfileAccessKey) obj ).getAccesskey() )
            && Util.isEquals( this.getValue(), ( (ProfileAccessKey) obj ).getValue() );
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {

        return " ProfileAccessKey [ " + this.getIdProfile() + " - " + this.getAccesskey() + " - " + this.getValue()
            + " ]";
    }

}
